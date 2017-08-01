package assignment1;

import java.time.Duration;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class MemoryPool {

	/* Initialize Attributes involved in Memory Pool */
	static LinkedList.Node start;
	static LinkedList.Node newStartBlock;
	static LinkedList.Node[] pool = new LinkedList.Node[11];
	LinkedList blockLinkList = new LinkedList();
	static int counter = 0;
	static int fragments = 0;

	/*Create Memory Pool */
	public void createMemoryPool(int numOfNodes, int memPower) {

		int memSize = 1;
		for (int i = 0; i < memPower; i++) {
			memSize = 2 * memSize;
		}

		LinkedList out = new LinkedList();
		LinkedList.Node everyTime = out.new Node(memSize);
		start = out.new Node(memSize, everyTime);

		LinkedList l1 = new LinkedList();
		LinkedList.Node p = start;

		for (int i = 2; i < numOfNodes; i++) {
			start = l1.insert(start, memSize);
		}
		start = p;
		int j = 0;

		for (int i = 0; i < 11; i++) {
			if (pool[i] != null) {

			} else {
				j = i;
				break;
			}
		}
		pool[j] = start;

	}
	/*Get blocks of Bigger size */
	public void getBlocksMovingUpwards(int tempIndex) {
		//System.out.println("Asked block index " + tempIndex);
		int origIndex = tempIndex;
		while (pool[tempIndex] == null) {
			if (tempIndex > 0) {
				tempIndex--;
				int mergedBlockSize = 0;
				mergedBlockSize = removeBlocks(tempIndex, origIndex, mergedBlockSize);
				newStartBlock = blockLinkList.new Node(mergedBlockSize, newStartBlock);
				break;
			} else {
				counter++;
				System.out.println("No blocks available moving upwards");
				break;
			}
		}
		
	}

	/* Function to traverse to lower index regions and get memory */
	public int removeBlocks(int tempIndex, int origIndex, int mergedBlockSize) {
		int origReq = (int) Math.pow(2, origIndex);
		int currBlockSize = (int) Math.pow(2, tempIndex);
		int currentNeedInLowerIndex = (origReq - mergedBlockSize) / currBlockSize;
		while (currentNeedInLowerIndex > 0) {
			if (pool[tempIndex] != null) {
				mergedBlockSize += pool[tempIndex].data;
				pool[tempIndex] = pool[tempIndex].next;
			} else {
				if (--tempIndex > 0) {
					mergedBlockSize += removeBlocks(tempIndex, origIndex, mergedBlockSize);
					return mergedBlockSize;
				} else {
					counter++;
					//System.out.println("reached end of index");
					break;
				}
			}

			currentNeedInLowerIndex--;
		}

		return mergedBlockSize;
	}

	/*Function to allocate block of requested size*/
	public void getBlock(int size) {

		int blockSize = 2, index = 0;
		if (size == 1) {
			index = 0;
		} else if (size == 2) {
			index = 1;
		} else {
			for (int i = 0; i < 10; i++) {
				if (size == blockSize) {
					index = i;
					index++;
					break;
				}
				blockSize = 2 * blockSize;
			}
		}

		LinkedList.Node block = pool[index];
		LinkedList.Node trav = block;
		int data = 0;
		int tempIndex = 0, calPower = 0;
		if (pool[index] == null) {
			tempIndex = index;

			if (index < 10) {
				while (pool[index] == null) {
					if (index < 10) {
						index++;
					} else {
						//System.out.println("System is out of memory !!!");
						getBlocksMovingUpwards(tempIndex);
						break;
					}
				}
				if (pool[index] != null) {
					data = pool[index].data;
					calPower = (index - tempIndex) * 2;
					LinkedList out = new LinkedList();
					start = out.new Node(data / calPower, start);
					pool[tempIndex] = start;
					fragments++;
					trav = pool[tempIndex];
					trav.next = null;
					pool[index] = pool[index].next;
					newStartBlock = blockLinkList.new Node(trav.data, newStartBlock);
				}
			} else {
				System.out.println("in else System is out of memory !!!");
			}
		} else {
			trav = pool[index];

			pool[index] = pool[index].next;
			trav.next = null;
			// System.out.println(trav.data);

			newStartBlock = blockLinkList.new Node(trav.data, newStartBlock);
		}

	}
	/*Function to print the status of Memory Pool*/
	public void printMemoryPool() {
		LinkedList.Node p = start;
		for (int i = 0; i < 11; i++) {
			if (pool[i] != null) {
				p = pool[i];
				System.out.print("Memory Pool[" + i + "] - ");
				while (p != null) {
					System.out.print("|" + p.data + "| ->");
					p = p.next;
				}
				System.out.print("null");
				System.out.println();
			} else {
				System.out.println("Memory Pool[" + i + "] - " + "No more blocks available");
			}
		}
	}
	/* Main Function that triggers the program*/
	public static void main(String args[]) {

		LinkedList out = new LinkedList();

		LinkedList l1 = new LinkedList();

		MemoryPool mp = new MemoryPool();
		mp.createMemoryPool(200, 0);
		mp.createMemoryPool(100, 1);
		mp.createMemoryPool(50, 2);
		mp.createMemoryPool(50, 3);
		mp.createMemoryPool(50, 4);
		mp.createMemoryPool(50, 5);
		mp.createMemoryPool(50, 6);
		mp.createMemoryPool(50, 7);
		mp.createMemoryPool(50, 8);
		mp.createMemoryPool(50, 9);
		mp.createMemoryPool(50, 10);

		System.out.println("////////////////Initial Memory Pool////////////////");
		mp.printMemoryPool();
		System.out.println("");
		System.out.println("");
		Random ran = new Random();
		long startTime = System.nanoTime();
		// Stopwatch stopwatch = Stopwatch.createStarted();
		int iter = 1000;
		for (int i = 0; i < iter; i++) {
			int number = ran.nextInt(10);
			while (number < 0) {
				number = ran.nextInt(10);
			}
			int input = (int) Math.pow(2, number);
			
			mp.getBlock(input);
			if(i%250 == 0){
				System.out.println("Status after: "+i);
				mp.printMemoryPool();
				System.out.println("");
				System.out.println("");
			}
		}
		LinkedList.Node p2 = newStartBlock;
		
		
		System.out.println("null");
		System.out.println("////////////////Final Memory Pool////////////////");
		mp.printMemoryPool();
		System.out.println("The Number of Failed Requests : " + counter);
		long endTime = System.nanoTime();
	//	System.out.println("Totla running time for " + iter + " is:" + (endTime - startTime) / 1000000);
		System.out.println("Final Linked List: ");
		int count = 0;
		while (p2 != null) {
			count++;
			System.out.print(p2.data + "->");
			p2 = p2.next;
			if(count%100 == 0)
			System.out.println("");
		}
		
		System.out.println("Number of Fragments: "+fragments);
	}
}
