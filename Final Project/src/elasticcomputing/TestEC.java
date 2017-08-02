package elasticcomputing;

import java.util.Map.Entry;

import elasticcomputing.exceptions.ResourceExhaustedException;
import elasticcomputing.model.Dispatcher;
import elasticcomputing.model.Machine;
import elasticcomputing.model.MachineFactory;
import elasticcomputing.model.Request;
import elasticcomputing.model.RequestQueue;

import java.util.Set;

public class TestEC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		MachineFactory.createNewMachines();
		Set<Machine> macNames = MachineFactory.getMachinesForDispatcher(1, 0, 0, 0);
		Dispatcher myDispatcher = new Dispatcher(macNames);
		RequestQueue rq = myDispatcher.getRequestList();
		int reqCount = 220;
		for (int i = 0; i < reqCount; i++) {
			rq.enqueue(Request.LOW);
			rq.enqueue(Request.MEDIUM);
			rq.enqueue(Request.HIGH);
		}
		
		int totalReqCount = reqCount
				* (Request.LOW.getRequestTime() + Request.HIGH.getRequestTime() + Request.MEDIUM.getRequestTime());
		System.out.println("Total Volume requests made to Dispatcher: " + totalReqCount);
		System.out.println("Initial processing capacity at Dispatcher: " + myDispatcher.getDispatcherMaxCapacity()); // myDispatcher.getMachinePool().
		try {
			System.out.println("The requests are processed :" + myDispatcher.processRequests());
		} catch (ResourceExhaustedException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		System.out.println("**** Printing status of All the machines currently at Dispatcher *****");
		for (Entry<Machine, Integer> mp : Dispatcher.lookUp.entrySet()) {
			System.out.println("The status of:" + mp.getKey().getMacNo() + " of size :"
					+ mp.getKey().getProcessingCapacity() + " is " + mp.getValue());
		}
		System.out.println("**** Printing status reports of Dispatcher ****");
		System.out.println("Number of Successful Dispatcher Results     : " + myDispatcher.getSuccessfulRequests());
		System.out.println("Number of Failed Dispatcher Results    		: " + myDispatcher.getFailedRequests());
		System.out.println(
				"Volume of Successful Requests          		: " + myDispatcher.getWeightedSuccessfulRequests());
		System.out.println(
				"Volume of Failed Requests              		: " + myDispatcher.getWeightedFailureRequests());
		System.out.println("Total available instances in Dispatcher are : " + myDispatcher.getMachinePool().size());
		System.out
				.println("Filled Ratio: " + (float) myDispatcher.getWeightedSuccessfulRequests() / (float) myDispatcher.getDispatcherMaxCapacity());
		
		long endTime = System.nanoTime();
		System.out.println("Total time is: "+(endTime - startTime)/1000000);
	}

}
