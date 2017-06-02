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
public enum Request {
	LOW(5), MEDIUM(8), HIGH(10);
	private int requestTime;

	private Request(int requestTime) {
		this.requestTime = requestTime;
	}

	public int getRequestTime() {
		return requestTime;
	}

}
