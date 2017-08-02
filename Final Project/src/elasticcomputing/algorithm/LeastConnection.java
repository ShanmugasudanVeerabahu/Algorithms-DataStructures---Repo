package elasticcomputing.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import elasticcomputing.exceptions.ResourceExhaustedException;
import elasticcomputing.model.Dispatcher;
import elasticcomputing.model.Machine;
import elasticcomputing.model.MachineFactory;
import elasticcomputing.model.MachineSize;
import elasticcomputing.model.Request;

public class LeastConnection implements LoadBalancerAlgorithm {

	private Dispatcher dispatcher;
	// private HashSet<Machine> machineSet;
	private Map<Integer, HashSet<Machine>> exhaustedMachines;
	

	public LeastConnection() {
		// this.machineSet = new HashSet<Machine>();
		this.exhaustedMachines = new HashMap<Integer, HashSet<Machine>>();
	}

	@Override
	/* Looks for availability of Services via LeastConnections method */
	public Machine lookForAvailability(Dispatcher dp, int rq) throws ResourceExhaustedException {
		this.dispatcher = dp;
		this.SortMachines();
		return bestPossibleMachine(dp.getMachinePool(), rq);
	}

	/* Find the best machine using this Algorithm */
	public Machine bestPossibleMachine(List<Machine> mp, int rq) throws ResourceExhaustedException {
		// int poolSize = mp.size();
		Machine service = mp.get(0); // poolSize / 2
		// if (service.getUsedCapacity() < rq && poolSize > 2) {
		// return bestPossibleMachine(mp.subList(0, poolSize / 2), rq);
		// } else
		if (((service.getProcessingCapacity() - service.getUsedCapacity()) / rq) > 0) {
			return service;
		}
		this.SortMachines();
		int maxAvailableUsedCapacityInstanceValue = this.dispatcher.getMachinePool().get(0).getUsedCapacity();
		try {
			boolean isMaxAvailableUsedCapacityInstanceValue = checkMaxAvailableUsedCapacityInstanceValue(
					maxAvailableUsedCapacityInstanceValue, rq);
		} catch (ResourceExhaustedException e) {
			service = this.lookUpLeftOverSpace(rq);
			if (service != null)
				return service;
			throw e;
		}
		service = this.dispatcher.getMachinePool().get(0);

		return service;
	}

	/* Checks for remaining space in the the instances */
	public Machine lookUpLeftOverSpace(int rq) {
		Set<Machine> macSet = this.exhaustedMachines.get(rq);
		if (macSet != null) {
			Machine mc = macSet.iterator().next();
			return mc;
		}
		return null;
	}

	/*
	 * checks Max Available UsedCapacity of Best Instance present in the
	 * Dispatcher
	 */
	public boolean checkMaxAvailableUsedCapacityInstanceValue(int maxAvailableUsedCapacityInstanceValue, int rq)
			throws ResourceExhaustedException {
		if (maxAvailableUsedCapacityInstanceValue > rq) {
			// this.dispatcher.getMachinePool().get(0).setMachineAvailable(false);
			this.scanAllMachines();
			return true;
		}
		return false;
	}

	/* Scan all the machines to perform the operation */
	public void scanAllMachines() throws ResourceExhaustedException {
		float maxUsedCapacity = 0;
		float maxProcessingCapacity = (float) this.dispatcher.getDispatcherMaxCapacity();
		// System.out.println("Disp. Pool size is: "+machinePool.size());
		for (Machine mc : this.dispatcher.getMachinePool()) {
			maxUsedCapacity += mc.getUsedCapacity();
		}
		double filledRatio = maxUsedCapacity / maxProcessingCapacity;
		// System.out.println("Filled ratio is: " + filledRatio);
		boolean fillCapacityFlag = checkFillRatio(filledRatio);
	}

	/* Method to verify if all the machines with Dispatcher is filled up */
	public boolean checkFillRatio(double filledRatio) throws ResourceExhaustedException {
		if (filledRatio > 0.72) {
			System.out.println("Filled ratio is: " + filledRatio);
			try {
				this.requestMachinesFromMachinePool();
			} catch (ResourceExhaustedException e) {
				if (exhaustedMachines.isEmpty())
					this.addResourcesToNewMap();
				throw e;
			}
			this.SortMachines();
			return true;
		}
		return false;
	}

	/* Aggregate the left over capacities of All machines and utilize them */
	public void addResourcesToNewMap() {
		int lowRequest = Request.LOW.getRequestTime();
		int mediumRequest = Request.MEDIUM.getRequestTime();
		int highRequest = Request.HIGH.getRequestTime();
		HashSet<Machine> tempSet = null;
		for (Entry<Machine, Integer> temp : Dispatcher.lookUp.entrySet()) {
			Machine mc = temp.getKey();
			Integer leftOverCapacity = mc.getProcessingCapacity() - temp.getValue();
			if (leftOverCapacity >= highRequest) {
				tempSet = checkAndAddRequestInRespectiveSpace(highRequest, mc);
				exhaustedMachines.put(highRequest, tempSet);
			} else if (leftOverCapacity >= mediumRequest) {
				tempSet = checkAndAddRequestInRespectiveSpace(mediumRequest, mc);
				exhaustedMachines.put(mediumRequest, tempSet);
			}
			tempSet = checkAndAddRequestInRespectiveSpace(lowRequest, mc);
			exhaustedMachines.put(lowRequest, tempSet);
		}
	}

	/*
	 * Checks for the request size the machine could take and maintains a look
	 * up for next iteration
	 */
	public HashSet<Machine> checkAndAddRequestInRespectiveSpace(int requestTime, Machine mc) {
		HashSet<Machine> tempSet = exhaustedMachines.get(requestTime);
		if (tempSet == null)
			tempSet = new HashSet<Machine>();
		tempSet.add(mc);
		return tempSet;
	}

	@Override
	/* Method that requests MachinePool for more resources */
	public void requestMachinesFromMachinePool() throws ResourceExhaustedException {
		Set<Machine> macNames = null;
		try {
			macNames = MachineFactory.getMachinesForDispatcher(0, 0, 0, 1);
			if (macNames.isEmpty())
				macNames = MachineFactory.getMachinesForDispatcher(0, 0, 1, 0);
			if (macNames.isEmpty())
				macNames = MachineFactory.getMachinesForDispatcher(0, 1, 0, 0);
			if (macNames.isEmpty())
				macNames = MachineFactory.getMachinesForDispatcher(1, 0, 0, 0);
			if (macNames.isEmpty())
				throw new ResourceExhaustedException(" Resources Exhausted.. Load more machines buddy!! ");
		} catch (ResourceExhaustedException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		this.dispatcher
				.setMachinePool(this.dispatcher.AssignedToDispatcher(this.dispatcher.getMachinePool(), macNames));
		this.dispatcher.updateHashMap(this.dispatcher.getMachinePool());
		System.out.println("Current size of DP: " + this.dispatcher.getMachinePool().size());
		// return disptacherPool;
	}

	/* Sort the machines based on usedCapacity Java 8 */
	public void SortMachines() {
		this.dispatcher.getMachinePool().sort((m1, m2) -> this.compare(m1, m2));
	}

	@Override
	public int compare(Machine m1, Machine m2) {
		// TODO Auto-generated method stub
		int myCapacity = m1.getProcessingCapacity() - m1.getUsedCapacity();
		int thatMachineCapacity = m2.getProcessingCapacity() - m2.getUsedCapacity();
		if (myCapacity < thatMachineCapacity)
			return 1;
		else if (myCapacity > thatMachineCapacity)
			return -1;
		else
			return 0;
	}  
}
