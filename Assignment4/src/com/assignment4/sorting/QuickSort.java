package com.assignment4.sorting;

public class QuickSort implements Sortable {
	
	
	public int[] sort(int[] arr){
		int lowerIndex = 0;
		int higherIndex = arr.length - 1;
 
		arr=this.quickSort(arr, lowerIndex, higherIndex);
		return arr;
	}

	public  int[] quickSort(int[] arr, int lowerIndex, int higherIndex) {
 
		if (lowerIndex >= higherIndex)
			return arr;
 
		// pick the pivot
		int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
		int pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = lowerIndex, j = higherIndex;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (lowerIndex < j)
			quickSort(arr, lowerIndex, j);
 
		if (higherIndex > i)
			quickSort(arr, i, higherIndex);
		
		return arr;
	}
}
