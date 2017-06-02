/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ruchirapatil
 */
public class Dispatcher {

	private RequestQueue requestList; /* Current Request List for Dispatcher */
	private ArrayList<Machine> machinePool; /*
											 * Active Directory of MachinePool
											 * of Dispatcher
											 */
	public static HashMap<Machine, Integer> lookUp = new HashMap<Machine, Integer>(); /*
																						 * LookUp
																						 * for
																						 * Machine
																						 * limits
																						 */

	/* Getters and Setters of Member variables */
	public RequestQueue getRequestList() {
		return requestList;
	}

	public void setRequestList(RequestQueue requestList) {
		this.requestList = requestList;
	}

	public ArrayList<Machine> getMachinePool() {
		return machinePool;
	}

	public void setMachinePool(ArrayList<Machine> machinePool) {
		this.machinePool = machinePool;
	}

	/* Default Constructor marked private */
	public Dispatcher() {
		this.requestList = new RequestQueue();
		this.machinePool = newMachinesFromMachinePool();
		// createNewMachines(machinePool);
		initializeHashMap(machinePool);
	}

	/*
	 * Parameterized Constructor to add a RequestQueue public
	 * Dispatcher(RequestQueue rq) { this.requestList = rq;
	 * 
	 * }
	 */

	/* List of Machines that are given to Dispatcher */
	public static ArrayList<Machine> newMachinesFromMachinePool() {
		ArrayList<Machine> disptacherPool = new ArrayList<Machine>();
		for (Machine mc : MachinePool.machinePoolList) {
			if (!mc.isMachineInMachinePool())
				disptacherPool.add(mc);
		}
		return disptacherPool;
	}

	/* Initialize Look Up table */
	public void initializeHashMap(ArrayList<Machine> machinePool) {
		for (int i = 0; i < machinePool.size(); i++) {
			lookUp.put(machinePool.get(i), machinePool.get(i).getUsedCapacity());
		}
	}

	/* Update values of machines on HashMap */
	public void updateHashMap(ArrayList<Machine> machinePool) {
		for (int i = 0; i < machinePool.size(); i++) {
			lookUp.put(machinePool.get(i), machinePool.get(i).getUsedCapacity());
		}
	}

	/* Processes User Requests */
	public boolean processRequests() {
		boolean result = false;
		while (this.requestList.getCurrentSize() > 0) {
			Request rq = this.requestList.dequeue();
			Machine service = lookForAvailability(machinePool, rq.getRequestTime());
			result = service.addToProcess(rq);
			System.out.println("The status would be: " + result);
			this.SortMachines();
		}
		this.updateHashMap(machinePool);
		return result;
	}

	/* Looks for availability of Services */
	public Machine lookForAvailability(List<Machine> mp, int rq) {
		int poolSize = mp.size();
		Machine service = mp.get(poolSize / 2);
		if (service.getUsedCapacity() < rq && poolSize > 2) {
			return lookForAvailability(mp.subList(0, poolSize / 2), rq);
		} else if (service.getUsedCapacity() > rq && poolSize > 2) {
			return lookForAvailability(mp.subList((poolSize / 2), poolSize), rq);
		} else if (((service.getProcessingCapacity() - service.getUsedCapacity()) / rq) > 0) {
			return service;
		} 
		service = mp.get(poolSize);
		return service;

	}

	/* Sort the machines based on usedCapacity */
	public void SortMachines() {
		machinePool.sort((m1, m2) -> m1.compareTo(m2));
	}
}
