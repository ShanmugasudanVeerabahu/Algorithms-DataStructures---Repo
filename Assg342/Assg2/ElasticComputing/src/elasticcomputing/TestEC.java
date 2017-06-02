package elasticcomputing;

import java.util.Map.Entry;
import java.util.Set;

public class TestEC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MachinePool.createNewMachines();
		MachinePool.getMachinesForDispatcher(2, 2, 2, 2);
		Dispatcher myDispatcher = new Dispatcher();
		RequestQueue rq = myDispatcher.getRequestList();
		rq.enqueue(Request.LOW);
		rq.enqueue(Request.LOW);
		rq.enqueue(Request.HIGH);
		rq.enqueue(Request.MEDIUM);
		System.out.println("The requests are processed :"+myDispatcher.processRequests());
		for(Entry<Machine,Integer> mp:Dispatcher.lookUp.entrySet()){
			System.out.println("The status of:"+mp.getKey()+" of size :"+mp.getKey().getProcessingCapacity()+" is "+mp.getValue());
		}
	}

}
