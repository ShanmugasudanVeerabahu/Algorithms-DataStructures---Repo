package com.pool.memory.tree;

import static com.pool.memory.tree.BinaryTree.root;

import java.util.Map;
import java.util.Random;

public class MemoryPool {
	private String value;
	
	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public MemoryPool(String result){
		this.setValue(result);
	}
	

	public static void main(String args[]) {
		BinaryTree bst = new BinaryTree(10);
//		bst.LevelOrder(root);
		System.out.println();
		System.out.println("Depth of tree: " + bst.maxDepth(root));
		Random ran = new Random();
		long startTime = System.nanoTime();
		int iter = 20;
		for (int i = 0; i < iter; i++) {
			int number = ran.nextInt(3);
			int input = (int) Math.pow(2, number);
//			bst.find(input);
			System.out.println("The set status is: " + bst.find(input));
		}
	//	bst.LevelOrder(root);
		System.out.println();
//		for (Map.Entry<Integer, String> temp : BinaryTree.lookUp.entrySet()) {
//			System.out.print("The key is: " + temp.getKey());
//			System.out.print("The value is: " + temp.getValue());
//			System.out.println();
//		}
		long endTime = System.nanoTime();
		System.out.println("Total" + " running time for " + BinaryTree.lookUp.size() + " is:" + (endTime - startTime) / 1000000);
		
		
	}
	public String printMessage(){
	      System.out.println(this.getValue());
	      return this.getValue();
	   }   
}
