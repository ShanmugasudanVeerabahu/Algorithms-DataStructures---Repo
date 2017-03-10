package com.assignment4.sorting;

public class InsertionSort implements Sortable {

	public int[] sort(int[] ar){
		 int temp;
	        for (int i = 1; i < ar.length; i++) {
	            for(int j = i ; j > 0 ; j--){
	                if(ar[j] < ar[j-1]){
	                    temp = ar[j];
	                    ar[j] = ar[j-1];
	                    ar[j-1] = temp;
	                }
	            }
	        }
	        return ar;
	
	        }
}
