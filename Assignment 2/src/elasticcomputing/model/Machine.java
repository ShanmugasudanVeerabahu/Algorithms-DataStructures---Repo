/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing.model;

/**
 *
 * @author shanmugasudanv
 */
public class Machine {

	private int processingCapacity; /*
									 * Actual Processing Capacity of the machine
									 */
	private RequestQueue processingQueue; /* Processing Queue of the machine */
	private int usedCapacity; /* Used Capacity of the machine */
	private boolean isMachineAvailable; /* Availability of the machine */
	private boolean isMachineInMachinePool;
	public static int macCount = 0;
	private String macName;

	/* Getters and Setters for member variables */
	public int getProcessingCapacity() {
		return processingCapacity;
	}

	public void setProcessingCapacity(int processingCapacity) {
		this.processingCapacity = processingCapacity;
	}

	public RequestQueue getProcessingQueue() {
		return processingQueue;
	}

	public void setProcessingQueue(RequestQueue processingQueue) {
		this.processingQueue = processingQueue;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public boolean isMachineAvailable() {
		return isMachineAvailable;
	}

	public void setMachineAvailable(boolean isMachineAvailable) {
		this.isMachineAvailable = isMachineAvailable;
	}

	public boolean isMachineInMachinePool() {
		return isMachineInMachinePool;
	}

	public void setMachineInMachinePool(boolean isMachineInMachinePool) {
		this.isMachineInMachinePool = isMachineInMachinePool;
	}

	public String getMacNo() {
		return macName;
	}

	public void setMacNo(String macNo) {
		this.macName = macNo;
	}

	/* Default Constructor */
	private Machine() {
	}

	/* Parameterized Constructor to initialize attributes of Instance */
	public Machine(int capacity, String macName) {
		this.processingQueue = new RequestQueue();
		this.processingCapacity = capacity;
		this.isMachineAvailable = true;
		this.usedCapacity = 0;
		this.isMachineInMachinePool = true;
		macCount++;
		this.macName = macName + macCount;

	}

	public boolean isMachineFull() {
		if ((this.processingCapacity - this.usedCapacity) == 0) {
			this.isMachineAvailable = false;
			return true;
		}
		return false;
	}

	/* Add a incoming reuqest to Processing Queue */
	public boolean addToProcess(Request rq) {
		return isQueueFull(rq) ? false : true;
	}

	/* Checks if the Queue is full and adds a new Request to Processing Queue */
	public boolean isQueueFull(Request rq) {
		if ((this.processingCapacity - (this.usedCapacity + rq.getRequestTime())) >= 0) {
			this.usedCapacity += rq.getRequestTime();
			this.processingQueue.enqueue(rq);
			return false;
		}
		return isMachineFull();
	}

	@Override
	public String toString() {
		return this.macName;
	}

}
