/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.xml.internal.txw2.output.StreamSerializer;

import elasticcomputing.algorithm.LeastConnection;
import elasticcomputing.algorithm.LoadBalancerAlgorithm;
import elasticcomputing.algorithm.LoadBalancerAlgortithmFactory;
import elasticcomputing.exceptions.ResourceExhaustedException;

/**
 *
 * @author shanmugasudanv
 */
public class Dispatcher {

	private RequestQueue requestList; /* Current Request List for Dispatcher */
	/* Active Directory of MachinePool of Dispatcher */
	private List<Machine> machinePool;
	private int dispatcherMaxCapacity;
	/* LookUp for Machine limits */
	public static HashMap<Machine, Integer> lookUp = new HashMap<Machine, Integer>();

	private int successfulRequests;
	private int weightedSuccessfulRequests;
	private int weightedFailureRequests;
	private int failedRequests;
	private LoadBalancerAlgorithm loadBalancerAlgorithm;

	/* Getters and Setters of Member variables */
	public RequestQueue getRequestList() {
		return requestList;
	}

	public void setRequestList(RequestQueue requestList) {
		this.requestList = requestList;
	}

	public List<Machine> getMachinePool() {
		return machinePool;
	}

	public void setMachinePool(List<Machine> machinePool) {
		this.machinePool = machinePool;
	}

	public int getDispatcherMaxCapacity() {
		return dispatcherMaxCapacity;
	}

	public void setDispatcherMaxCapacity(int dispatcherMaxCapacity) {
		this.dispatcherMaxCapacity = dispatcherMaxCapacity;
	}

	public int getSuccessfulRequests() {
		return successfulRequests;
	}

	public void setSuccessfulRequests(int successfulRequests) {
		this.successfulRequests = successfulRequests;
	}

	public int getFailedRequests() {
		return failedRequests;
	}

	public void setFailedRequests(int failedRequests) {
		this.failedRequests = failedRequests;
	}

	public int getWeightedSuccessfulRequests() {
		return weightedSuccessfulRequests;
	}

	public void setWeightedSuccessfulRequests(int weightedSuccessfulRequests) {
		this.weightedSuccessfulRequests = weightedSuccessfulRequests;
	}

	public int getWeightedFailureRequests() {
		return weightedFailureRequests;
	}

	public void setWeightedFailureRequests(int weightedFailureRequests) {
		this.weightedFailureRequests = weightedFailureRequests;
	}

	/* Default Constructor marked private */
	public Dispatcher(Set<Machine> macNames) {
		this.requestList = new RequestQueue();
		this.dispatcherMaxCapacity = 0;
		this.successfulRequests = 0;
		this.failedRequests = 0;
		this.weightedFailureRequests = 0;
		this.weightedSuccessfulRequests = 0;
		this.machinePool = this.newMachinesFromMachinePool(macNames);
		initializeHashMap(machinePool);
	}

	/* List of Machines that are given to Dispatcher */
	public List<Machine> newMachinesFromMachinePool(Set<Machine> macNames) {
		List<Machine> disptacherPool = new ArrayList<Machine>();
		disptacherPool = this.AssignedToDispatcher(disptacherPool, macNames);
		return disptacherPool;
	}

	/* set Machines assigned to Dispatcher */
	public List<Machine> AssignedToDispatcher(List<Machine> dispatcherPool, Set<Machine> macNames) {
		for (Machine mc : macNames) {
			dispatcherPool.add(mc);
			this.dispatcherMaxCapacity += mc.getProcessingCapacity();
		}
		return dispatcherPool;
	}

	/* Initialize Look Up table Java 8*/
	public void initializeHashMap(List<Machine> machinePool) {
		machinePool.forEach(mc -> lookUp.put(mc, mc.getUsedCapacity()));
/*		for (Machine mc : machinePool) {
			lookUp.put(mc, mc.getUsedCapacity());
		}   */
	}

	/* Update values of machines on HashMap  Java 8 */
	public void updateHashMap(List<Machine> machinePool) {
		
		machinePool.forEach(mc -> lookUp.put(mc, mc.getUsedCapacity()));
/*		for (Machine mc : machinePool) {
			lookUp.put(mc, mc.getUsedCapacity());
		}   */
	}

	/* Processes User Requests */
	public boolean processRequests() throws ResourceExhaustedException {
		boolean result = false, newResult = false;
		while (this.requestList.getCurrentSize() > 0) {
			Request rq = this.requestList.dequeue();
			try {
				Machine service = pickAnAlgorithm(rq);
				result = service.addToProcess(rq);
				newResult = workOnResultStatus(result, rq);
				newResult = false;
				this.updateHashMap(machinePool);
			} catch (ResourceExhaustedException e) {
				this.requestList.enqueue(rq);
				this.updateFailedRequests(this.requestList);
				throw e;
			}

			// System.out.println("The status would be: " + newResult);

		}
		return result;
	}

	public void updateFailedRequests(RequestQueue rq) {
		
		while (!rq.isEmpty()) {
			++this.failedRequests;
			this.weightedFailureRequests += rq.next().getRequestTime();
		}
	}

	/* Picks up a load balancing Algorithm at run time */
	public Machine pickAnAlgorithm(Request rq) throws ResourceExhaustedException {
		if (this.loadBalancerAlgorithm == null)
			this.loadBalancerAlgorithm = LoadBalancerAlgortithmFactory
					.getLoadBalancerAlgorithmInstance("LeastConnection");
		Machine service = loadBalancerAlgorithm.lookForAvailability(this, rq.getRequestTime());
		return service;
	}

	/*
	 * Picks up a load balancing Algorithm method overloading to switch
	 * different algorithm at run time
	 */
	public Machine pickAnAlgorithm(LoadBalancerAlgorithm lba, Request rq) throws ResourceExhaustedException {

		Machine service = loadBalancerAlgorithm.lookForAvailability(this, rq.getRequestTime());
		return service;
	}

	/*
	 * Works on the result returned by the machine. If the result is negative,
	 * Redo the search for a new machine
	 */
	public boolean workOnResultStatus(boolean result, Request rq) throws ResourceExhaustedException {
		if (result) {
			++this.successfulRequests;
			this.weightedSuccessfulRequests += rq.getRequestTime();
			return true;
		}
		Machine service = pickAnAlgorithm(this.loadBalancerAlgorithm, rq);
		result = service.addToProcess(rq);
		result = workOnResultStatus(result, rq);
		return result;
	}

}
