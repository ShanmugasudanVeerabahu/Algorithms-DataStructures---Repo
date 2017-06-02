/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing;

/**
 *
 * @author ruchirapatil
 */
public class RequestQueue {

	private Node front, rear; /* Pointers to access the Request */
	private int currentSize; /*  Number of Requests in Queue */

	
	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	/* class to define linked node  */
	private class Node {
		Request currentRequest;
		Node next;
	}

	/* Zero argument constructor  */
	public RequestQueue() {
		front = null;
		rear = null;
		currentSize = 0;
	}

	/* Check if the Queue is empty */
	public boolean isEmpty() {
		return (currentSize == 0);
	}

	/* Remove item from the beginning of the list */
	public Request dequeue() {
		
		Request currentRequest = front.currentRequest;
		front = front.next;
		if (isEmpty()) {
			rear = null;
		}
		currentSize--;
		// System.out.println(data+ " removed from the queue");
		return currentRequest;
	}

	/* Add data to the end of the list */
	public void enqueue(Request currentRequest) {
		Node oldRear = rear;
		rear = new Node();
		rear.currentRequest = currentRequest;
		rear.next = null;
		if (isEmpty()) {
			front = rear;
		} else {
			oldRear.next = rear;
		}
		currentSize++;
		// System.out.println(data+ " added to the queue");
	}
/*
	public static void main(String a[]) {

		RequestQueue queue = new RequestQueue();

		queue.enqueue(Request.LOW);
		queue.enqueue(Request.LOW);
		queue.enqueue(Request.HIGH);
		queue.enqueue(Request.MEDIUM);

		Dispatcher myDispatcher = new Dispatcher(queue);

		 while(!myDispatcher.requestList.isEmpty())
		 {
		 System.out.println(myDispatcher.requestList.dequeue().requestTime);
		 }

	}  */

}
