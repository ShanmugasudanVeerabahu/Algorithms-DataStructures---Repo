package com.assignment4.sorting;

public class HeapSort implements Sortable{

	public int[] sort(int[] ar){
		
		int arraySize=ar.length;
		for(int i=arraySize/2-1;i>=0;i--)
			ar=heapify(ar,arraySize,i);
		for(int i=arraySize-1;i>=0;i--){
			int temp=ar[0];
			ar[0]=ar[i];
			ar[i]=temp;
			ar=heapify(ar,i,0);
		}
		return ar;
	}
	
	public int[] heapify(int[] ar,int arraySize,int index){
		int largest=index;
		int left=2*index+1;
		int right=2*index+2;
		
		if(left < arraySize && ar[left] > ar[largest])
			largest=left;
		if(right < arraySize && ar[right] > ar[largest])
			largest=right;
		
		if(largest !=index){
			int swap=ar[largest];
			ar[largest]=ar[index];
			ar[index]=swap;
			ar=heapify(ar,arraySize,largest);
		}
		return ar;
	}
}
