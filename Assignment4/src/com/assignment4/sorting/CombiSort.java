package com.assignment4.sorting;

public class CombiSort implements Sortable {

	public int[] sort(int[] arr){
		int[] statusCode=new int[1];
		 if(arr.length< 500000){
			statusCode[0]=1;
			return statusCode;	// Call Merge Sort
		}else{
			statusCode[0]=2;
			return statusCode;	// Call Quick Sort
		
		}
	}
}
