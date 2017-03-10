package com.assignment4.sorting;

import java.util.Arrays;

public class MergeSort implements Sortable{
	
	private int[]tempArr;
	private int[] array;
	
	public void createTempArr(int arraySize){
		this.tempArr=new int[arraySize];
	}
	
	public int[] getTempArr(){
		return this.tempArr;
	}
	
	public void setArray(int[] arr){
		this.array=Arrays.copyOf(arr, arr.length);
	}
	
	public int[] getArray(){
		return this.array;
	}
	public int[] sort(int[] arr){
		 this.setArray(arr);
		 this.createTempArr(arr.length);
		 splitArray(0, arr.length-1);
		 return this.getArray();
		
	}
	
	public void splitArray(int lowerIndex, int higherIndex){
		if(lowerIndex <higherIndex){
			int middleIndex=lowerIndex+(higherIndex-lowerIndex)/2;
			
			splitArray(lowerIndex,middleIndex);
			splitArray(middleIndex+1,higherIndex);
			
			mergeParts(lowerIndex,middleIndex,higherIndex);
		}
	}
	
	public void mergeParts(int lowerIndex,int middleIndex,int higherIndex){
		 int[] m_tempArr=this.getTempArr();
		int[] m_arr=this.getArray();
		for(int i= lowerIndex;i <= higherIndex;i++){
			m_tempArr[i]=m_arr[i];
		}
		int m_lowerIndex=lowerIndex;
		int m_middleIndx=middleIndex+1;
		int m_actLowerIndex=lowerIndex;
		
		while (m_lowerIndex <= middleIndex && m_middleIndx <= higherIndex) {
            if (m_tempArr[m_lowerIndex] <= m_tempArr[m_middleIndx]) {
            	m_arr[m_actLowerIndex] = m_tempArr[m_lowerIndex];
                m_lowerIndex++;
            } else {
            	m_arr[m_actLowerIndex] = m_tempArr[m_middleIndx];
                m_middleIndx++;
            }
            m_actLowerIndex++;
        }
        while (m_lowerIndex <= middleIndex) {
        	m_arr[m_actLowerIndex] = m_tempArr[m_lowerIndex];
            m_actLowerIndex++;
            m_lowerIndex++;
        }
        
        
	}

}
