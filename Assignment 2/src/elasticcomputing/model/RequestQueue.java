/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elasticcomputing.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author shanmugasudanv
 */
public class RequestQueue implements Iterator<Request> {

	private Node front, rear; /* Pointers to access the Request */
	private int currentSize; /* Number of Requests in Queue */

	public synchronized int getCurrentSize() {
		return currentSize;
	}

	public synchronized void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	/* class to define linked node */
	private class Node {
		Request currentRequest;
		Node next;
	}

	/* Zero argument constructor */
	public RequestQueue() {
		front = null;
		rear = null;
		currentSize = 0;
	}

	/* Check if the Queue is empty */
	public synchronized boolean isEmpty() {
		return (currentSize == 0);
	}

	/* Remove item from the beginning of the list */
	public synchronized Request dequeue() {

		Request currentRequest = front.currentRequest;
		front = front.next;
		if (isEmpty()) {
			rear = null;
		}
		currentSize--;
		return currentRequest;
	}

	/* Add data to the end of the list */
	public synchronized void enqueue(Request currentRequest) {
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
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return isEmpty();
	}

	@Override
	public Request next() {
		// TODO Auto-generated method stub

		if (!hasNext()) {
			return dequeue();
		}
		throw new NoSuchElementException();
	}
}
