package com.pool.memory.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BinaryTree {

	public static Node root;
	public static HashMap<Integer, String> lookUp = new HashMap<Integer, String>();
	private static int counter = 0;

	/* parameterized constructor to create a Memory Pool */
	public BinaryTree(int height) {
		int size = (int) Math.pow(2, height);
		root = createMemoryPool(height, size);
		setIndexValues(root);
	}

	/* Function to create a Tree */
	public Node createMemoryPool(int height, int size) {
		if (height < 0)
			return null;
		Node currNode = new Node(size);
		if (height > 0) {
			currNode.left = createMemoryPool(height - 1, size / 2);
			currNode.right = createMemoryPool(height - 1, size / 2);
		}
		return currNode;
	}

	/* Function to create height of Tree */
	int maxDepth(Node node) {
		if (node == null)
			return 0;
		else {
			/* compute the depth of each subtree */
			int lDepth = maxDepth(node.left);
			int rDepth = maxDepth(node.right);

			/* use the larger one */
			if (lDepth > rDepth)
				return (lDepth + 1);
			else
				return (rDepth + 1);
		}
	}

	/* Inner Class node */
	class Node {

		int data;
		int uId;
		Node left;
		Node right;
		String occupiedFlag;

		public Node(int data) {
			this.data = data;
			left = null;
			right = null;
			occupiedFlag = "NO";
		}
	}

	/* Using Breadth First Search to set index values for nodes */
	public void setIndexValues(Node myNode) {

		Queue<Node> queue = new LinkedList<Node>();
		if (myNode == null)
			return;

		queue.clear();
		queue.add(myNode);
		int index = -1;
		while (!queue.isEmpty()) {
			Node node = queue.remove();
			node.uId = index;
			index--;
			if (node.left != null)
				queue.add(node.left);
			if (node.right != null)
				queue.add(node.right);
		}
	}

	/* Performing level order traversal to print node attributes */
	void LevelOrder(Node root) {
		if (root == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);

		int size[] = new int[11];

		for (int i = 10; i >= 0; i--) {
			size[i] = (int) Math.pow(2, i);
		}

		int i = 10;
		while (!queue.isEmpty()) {
			Node node = queue.remove();
			if (size[i] == node.data) {
				System.out.print(node.occupiedFlag + " ");
			} else {
				System.out.println(" ");
				System.out.print(node.occupiedFlag + " ");
				i--;
			}

			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}

	}

	/* Find a memory block for the given request */
	public boolean find(int reqSize) {
		Node current = root;
		int level = 1;
		boolean leftNodeStatus = true;
		boolean rightNodeStatus = true;
		while (current != null) {
			if (current.data == reqSize) {
				if (current.occupiedFlag.equalsIgnoreCase("no")) {
					boolean childFlagStatus = checkChildFlags(current, leftNodeStatus, rightNodeStatus);
					if (childFlagStatus) {
						current.occupiedFlag = "YES";
						boolean status = setFlagsOfChild(current);
						lookUp.put(current.uId, current.occupiedFlag);
						counter++;
						return status;
					} else {
						Node first = current;
						Node temp = counter % 2 != 0 ? findMyNode(current, level) : findNextNode(current, level);
						while (first.uId == temp.uId) {
							first = temp;
							int myUid;
							if (counter % 2 == 0) {
								myUid = temp.uId - 1;
							} else {
								myUid = temp.uId + 1;
							}
							temp = getMeThisNode(myUid, root);
						}
						current = temp;
					}
				} else {

					current = counter % 2 != 0 ? findMyNode(current, level) : findNextNode(current, level);
				}
			} else if (reqSize < current.data) {
				current = current.left;
				level++;
			} else {
				current = current.right;
				level++;
			}

		}
		return false;
	}

	/* Check if any of the child nodes are set Occupied */
	public boolean checkChildFlags(Node current, boolean leftNodeStatus, boolean rightNodeStatus) {
		while (current != null) {
			if (current.occupiedFlag.equalsIgnoreCase("no")) {

				leftNodeStatus = checkChildFlags(current.left, leftNodeStatus, rightNodeStatus);
				rightNodeStatus = checkChildFlags(current.right, leftNodeStatus, rightNodeStatus);
				return (leftNodeStatus == true && rightNodeStatus == true) ? true : false;
			} else {
				return false;
			}
		}
		return true;
	}

	/* Find my next node */
	public Node findNextNode(Node current, int level) {
		int range = (int) Math.pow(2, level);
		int lowRange = range / 2;
		int checkThisUid;
		checkThisUid = current.uId - 1;
		current = lookUpNode(current, checkThisUid, range, level);
		return current;
	}

	/* Find my next node */
	public Node findMyNode(Node current, int level) {
		int range = (int) Math.pow(2, level);
		int lowRange = range / 2;
		Node testAgainst = getMeThisNode(-(range + lowRange) / 2, root);
		int checkThisUid;

		if (current.uId > testAgainst.uId) { //
			checkThisUid = current.uId - 1;
		} else {
			int pos = -(current.uId + (lowRange - 1));
			checkThisUid = -(range - pos);
		}
		Node temp = lookUpNode(current, checkThisUid, range, level);
		return temp;

	}

	/* Look up if the node is blocked by previous requests*/
	public Node lookUpNode(Node current, int checkThisUid, int range, int level) {

		if (checkThisUid > (-range) && checkThisUid < (-range / 2)) {
			String status = (String) lookUp.get(checkThisUid);
			if(status == null)  {

				current = getMeThisNode(checkThisUid, root);
				return current;
				
			}else {
				if (counter % 2 == 0) {
					Node temp = getMeThisNode(checkThisUid - 1, root);
					if (temp != null) {
						current = lookUpNode(current, temp.uId, range, level);
					} else {
						return null;
					}
				} else {
					Node temp = getMeThisNode(checkThisUid - 1, root);
					if (temp != null) {
						current = lookUpNode(current, temp.uId, range, level);
					} else
						return null;
				}
			} 

			return current;
		} else {
			return null;
		}
	}
	

	/* Get a node for a given index */
	public Node getMeThisNode(int thisUid, Node start) {

		if (start != null) {
			if (start.uId == thisUid) {
				return start;
			} else {
				Node findIt = getMeThisNode(thisUid, start.left);
				if (findIt == null) {
					findIt = getMeThisNode(thisUid, start.right);
				}
				return findIt;
			}

		} else {
			return null;
		}

	}

	/* Set status of children nodes to occupied */
	public boolean setFlagsOfChild(Node current) {

		while (current != null) {
			current.occupiedFlag = "YES";
			boolean leftStatus = setFlagsOfChild(current.left);
			boolean rightStatus = setFlagsOfChild(current.right);
			boolean childStatus = leftStatus == rightStatus ? true : false;
			return childStatus;
		}
		return true;
	}

}
