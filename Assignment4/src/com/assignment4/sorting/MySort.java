package com.assignment4.sorting;

import java.util.Arrays;
import java.util.Random;

public class MySort  {

	public static final Random gen = new Random();
	public static int[] inputArray= new int[100];
	public Sortable sortMethod;
	
	public void setSortMethod(Sortable sortType){
		this.sortMethod=sortType;
	}
	
	public Sortable getSortMethod(){
		return this.sortMethod;
	}
	
	public static void main(String[] args){
		MySort sortObj= new MySort();
		generateInputArray();
		sortMyArray(sortObj);		
	}
	
	/*Function to initiate Quick Sort */
	public void initiateQuickSort(int[] myArray){
		System.out.println("Quick Sort");
		int[] quickSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, quickSortInput,0, myArray.length);
		this.setSortMethod(new QuickSort());
		long startTime=0l,endTime=0l;long runTime=0l;
//		Arrays.sort(quickSortInput);
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 quickSortInput=this.getSortMethod().sort(quickSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Quick Sort");
		
		}
		System.out.println(runTime/10);  //"Average Run time for Quick Sort"+
	}
	
	/*Function to initiate Heap Sort */
	public void initiateHeapSort(int[] myArray){	
		System.out.println("Heap Sort");
		int[] heapSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, heapSortInput,0, myArray.length);
//		Arrays.sort(heapSortInput);
		this.setSortMethod(new HeapSort());
		long startTime=0l,endTime=0l;long runTime=0l;
		
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 heapSortInput=this.getSortMethod().sort(heapSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Heap Sort");
		
		}
		System.out.println(runTime/10);  //"Average Run time for Heap Sort"+
	}
	
	/*Function to initiate Merge Sort */
	public void initiateMergeSort(int[] myArray){	
		System.out.println("Merge Sort");
		int[] mergeSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, mergeSortInput,0, myArray.length);
		
		this.setSortMethod(new MergeSort());
		long startTime=0l,endTime=0l;long runTime=0l;
		
//		Arrays.sort(mergeSortInput);
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 mergeSortInput=this.getSortMethod().sort(mergeSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Merge Sort");
		
		}
		System.out.println(runTime/10); //"Average Run time for Merge Sort"
	}
	
	/*Function to initiate Insertion Sort */
	public void initiateInsertionSort(int[] myArray){	
		System.out.println("Insertion Sort");
		int[] insertionSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, insertionSortInput,0, myArray.length);
		
		this.setSortMethod(new InsertionSort());
		long startTime=0l,endTime=0l;long runTime=0l;
		
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 insertionSortInput=this.getSortMethod().sort(insertionSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Insertion Sort");
		
		}
		System.out.println(runTime/10);  //"Average Run time for Insertion Sort"
	}
	
	/*Function to initiate Selection Sort */
	public void initiateSelectionSort(int[] myArray){	
		System.out.println("Selection Sort");
		int[] selectionSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, selectionSortInput,0, myArray.length);
		
		this.setSortMethod(new SelectionSort());
		long startTime=0l,endTime=0l;long runTime=0l;
		
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 selectionSortInput=this.getSortMethod().sort(selectionSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Selection Sort");
		
		}
		System.out.println(runTime/10); //"Average Run time for Selection Sort"
	}
	
	/*Function to initiate Intro Sort */
	public void initiateIntroSort(int[] myArray){	
		System.out.println("Intro Sort");
		int[] introSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, introSortInput,0, myArray.length);
		
		this.setSortMethod(new IntroSort());
		long startTime=0l,endTime=0l;long runTime=0l;
	//	Arrays.sort(introSortInput);
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 introSortInput=this.getSortMethod().sort(introSortInput);
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Intro Sort");
		
		}
		System.out.println(runTime/10); //"Average Run time for Intro Sort"+
	}
	
	/*Function to initiate Combi Sort */
	public void initiateCombiSort(int[] myArray){
		System.out.println("Quick Sort");
		int[] combiSortInput= new int[myArray.length];
		System.arraycopy(myArray, 0, combiSortInput,0, myArray.length);
		
		this.setSortMethod(new CombiSort());
		long startTime=0l,endTime=0l;long runTime=0l;
		
		for(int counter=0;counter<10;counter++){
		 startTime=System.nanoTime();
		 int[] statusCode=this.getSortMethod().sort(combiSortInput);
		 if(statusCode[0]==1){
			 this.setSortMethod(new MergeSort());
			 combiSortInput=this.getSortMethod().sort(combiSortInput);
		 }
		 else{
			 this.setSortMethod(new QuickSort());
			 combiSortInput=this.getSortMethod().sort(combiSortInput);
		 }
		 endTime=System.nanoTime();
		 runTime+=(endTime-startTime)/1000000;
//		System.out.println("Total running time for "+myArray.length+" inputs:"+
//				(endTime-startTime)/1000000 + " : Combi Sort");
		
		}
		System.out.println(runTime/10);  //"Average Run time for Quick Sort"+
	}
	
	/* Function to initiate sorting Method with array as input SortMyArray */
	public static void sortMyArray(MySort sortObj){
		for(int index=0;index<inputArray.length;index++){
			int count=inputArray[index];
			int[] myArray=createArray(count);
			shuffle(myArray);
			System.out.println("****** For Input Size "+count+" *******");
			sortObj.initiateQuickSort(myArray);
			sortObj.initiateHeapSort(myArray);
			sortObj.initiateMergeSort(myArray);
			sortObj.initiateInsertionSort(myArray);
			sortObj.initiateSelectionSort(myArray);
			sortObj.initiateIntroSort(myArray);
			sortObj.initiateCombiSort(myArray);
			
		}
	}
	
	/*Generate input size*/
	public static void generateInputArray(){
		int startSize=10000;
		for(int index=0;index<inputArray.length;index++){
			inputArray[index]=startSize;
			startSize+=10000;
		}
	}
	
	/*print array contents */
	public static void printArray(int[] array){
		System.out.println(Arrays.toString(array));
	}
	
	/* create an array */
	public static int[] createArray(int count){
		int[] temp= new int[count];
		for(int i=0;i<count;i++){
			temp[i]=gen.nextInt(count);
		}
		return temp;
	}
	
	/*knuth shuffle */
	public static void shuffle (int[] array) {
	    int n = array.length;
	    while (n > 1) {
	        int k = gen.nextInt(n--); //decrements after using the value
	        int temp = array[n];
	        array[n] = array[k];
	        array[k] = temp;
	    }
	}
	

}
