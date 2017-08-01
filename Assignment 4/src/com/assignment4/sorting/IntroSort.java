package com.assignment4.sorting;

public class IntroSort implements Sortable{

  private static int size_threshold = 16;

  public int[]  sort(int[] inputArray)
    {
      inputArray=this.introsort_loop(inputArray, 0, inputArray.length, 2*floor_lg(inputArray.length));
      return inputArray;
    }

  public  void sort(int[] a, int begin, int end)
    {
      if (begin < end)
	  {
	    this.introsort_loop(a, begin, end, 2*floor_lg(end-begin));
	  }
    }

  /*
   * Quicksort algorithm modified for Introsort
   */
  private  int[] introsort_loop (int[] a, int lo, int hi, int depth_limit)
    {
      while (hi-lo > size_threshold)
	  {
	    if (depth_limit == 0)
		{
		  heapsort(a, lo, hi);
		  return a;
		}
	    depth_limit=depth_limit-1;
	    int p=partition(a, lo, hi, medianof3(a, lo, lo+((hi-lo)/2)+1, hi-1));
	    introsort_loop(a, p, hi, depth_limit);
	    hi=p;
	  }
      a=insertionsort(a, lo, hi);
      return a;
    }

  private static int partition(int[] a, int lo, int hi, int x)
    {
      int i=lo, j=hi;
      while (true)
	  {
	    while (a[i] < x) i++;
	    j=j-1;
	    while (x < a[j]) j=j-1;
	    if(!(i < j))
	      return i;
	    exchange(a,i,j);
	    i++;
	  }
    }

  private static int medianof3(int[] a, int lo, int mid, int hi)
    {
      if (a[mid] < a[lo])
	  {
            if (a[hi] < a[mid])
	      return a[mid];
            else
		{
		  if (a[hi] < a[lo])
		    return a[hi];
		  else
		    return a[lo];
		}
	  }
      else
	  {
            if (a[hi] < a[mid])
		{
		  if (a[hi] < a[lo])
		    return a[lo];
		  else
		    return a[hi];
		}
            else
	      return a[mid];
	  }
    }

  /*
   * Heapsort algorithm
   */
  private static void heapsort(int[] a, int lo, int hi)
    {
      int n = hi-lo;
      for (int i=n/2; i>=1; i=i-1)
	  {
	    downheap(a,i,n,lo);
	  }
      for (int i=n; i>1; i=i-1)
	  {
	    exchange(a,lo,lo+i-1);
	    downheap(a,1,i-1,lo);
	  }
    }

  private static void downheap(int[] a, int i, int n, int lo)
    {
      int d = a[lo+i-1];
      int child;
      while (i<=n/2)
	  {
	    child = 2*i;
	    if (child < n && a[lo+child-1] < a[lo+child])
		{
		  child++;
		}
	    if (d >= a[lo+child-1]) break;
	    a[lo+i-1] = a[lo+child-1];
	    i = child;
	  }
      a[lo+i-1] = d;
    }

  /*
   * Insertion sort algorithm
   */
  private int[]  insertionsort(int[] a, int lo, int hi)
    {
      int i,j;
      int t;
      for (i=lo; i < hi; i++)
	  {
            j=i;
	    t = a[i];
	    while(j!=lo && t < a[j-1])
		{
		  a[j] = a[j-1];
		  j--;
		}
	    a[j] = t;
	  }
      return a;
    }

  /*
   * Common methods for all algorithms
   */
  private static void exchange(int[] a, int i, int j)
    {
      int t=a[i];
      a[i]=a[j];
      a[j]=t;
    }

  private static int floor_lg(int a)
    {
      return (int)(Math.floor(Math.log(a)/Math.log(2)));
    }


}
