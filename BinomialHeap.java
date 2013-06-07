//package datastructures;

/**
 * Implements min binomial heap
 */
public class BinomialHeap {

	public HeapNode minElement=null;
	public int numberOfNodes = 0;
	public int displayed = 0;
	
	public int maxLevel=1;

	
	/**
	 * Inserts an integer data into min binomial heap
	 */
	public void insert(int data) {
		HeapNode newNode = new HeapNode(data);

		//if heap is empty, making new node as minelment(heap root)
		if (minElement == null) {
			minElement = newNode;
			newNode.sibling = newNode; 
		} 
		//heap is not empty, so inserting new node into the circular linked list
		else {
			HeapNode temp = minElement.sibling;
			minElement.sibling = newNode;
			newNode.sibling = temp;
			// if new node as minimum value, updating minelement
			if (newNode.data<minElement.data)
				minElement = newNode;
		}
		numberOfNodes++;
	}

	/**
	 * Performs meld operation on min binomial heaps n1 and n2 and returns the
	 * melded heap
	 */
	public HeapNode meld(HeapNode list1, HeapNode list2) {
		HeapNode tempList = list1.sibling;
		list1.sibling = list2.sibling;
		list2.sibling = tempList;

		if (list1.data < list2.data)
			tempList=list1;
		else
			tempList=list2;
		return tempList;
	}

	/**
	 * Removes min element from min binomial heap
	 */
	public void removeMin() {

		// Remove min tree
		HeapNode child = minElement.child;

		//if minelement doesn't have child, simply copy minelement's sibling data to min element
		if(child==null) {
			if (minElement.sibling != null) {
				minElement.copyData(minElement.sibling); // Trick
				minElement = pairwiseCombine(minElement);
			} 
		}
		//if minlelement has child
		else{
			HeapNode tempChild = child;
			HeapNode smallest = child;
			//find minelement in child's circualr linked list
			while (child.sibling != tempChild) {
				if (child.sibling.data < smallest.data)
					smallest = child.sibling;
				child = child.sibling;
			}
			child = smallest;

			if (minElement != minElement.sibling) {
				minElement.copyData(minElement.sibling); 
				minElement = meld(minElement, child);
				minElement = pairwiseCombine(minElement);
			} else {
				minElement = child;
				minElement = pairwiseCombine(minElement);
			}
		} 
		numberOfNodes--;
	}

	/**
	 * Performs the pairwise combine operation
	 */
	public HeapNode pairwiseCombine(HeapNode tempHead) {

		HeapNode listNode = tempHead;
		HeapNode[] table = new HeapNode[50];

		// First remove pointer pointing to temp
		while (listNode.sibling != tempHead) {
			listNode = listNode.sibling;
		}
		listNode.sibling = null;

		if (tempHead == null)
			System.out.println("Something wrong");

		HeapNode nodesToInsert = tempHead;
		// temp points to elements that have not yet inserted in the table
		while (nodesToInsert != null) {
			listNode = nodesToInsert;

			// removing the front node from the linked list
			if (nodesToInsert.sibling != null) {
				HeapNode temp = nodesToInsert;
				nodesToInsert = nodesToInsert.sibling;
				temp.sibling = null;
			} else
				nodesToInsert = null;
			//if no node is present in the table at degree level
			if (table[listNode.degree] == null)
				table[listNode.degree] = listNode;
			//node is present in the table, so combine list node and table node
			else {
				while (true) {
					HeapNode tableNode = table[listNode.degree];
					table[tableNode.degree] = null;

					if (tableNode.data < listNode.data) {

						if (tableNode.child == null) {
							tableNode.child = listNode;
							listNode.sibling = listNode;
							tableNode.degree = 1;
						} 
						else {
							HeapNode temp = tableNode.child.sibling;
							tableNode.child.sibling = listNode;
							listNode.sibling = temp;
							tableNode.degree++;
						}

						listNode = tableNode; 

						if (table[listNode.degree] == null) {
							table[listNode.degree] = listNode;
							break;
						} 
						else
							continue;
					}
					else {
						if (listNode.child == null) {
							listNode.child = tableNode;
							tableNode.sibling = tableNode;
							listNode.degree = 1;
						} else {
							HeapNode temp3 = listNode.child.sibling;
							listNode.child.sibling = tableNode;
							tableNode.sibling = temp3;
							listNode.degree++;
						}

						if (table[listNode.degree] == null) {
							table[listNode.degree] = listNode;
							break;
						} 
						else 
							continue;
					}
				}

			}
		}// end of while

		// Creating a circular list of the nodes in the table
		listNode = null;
		HeapNode temp1 = null;
		HeapNode temp2 = null;
		for (int i = 0; i < 50; i++) {
			if (table[i] == null) {
				//No node in the table, do nothing
			}
			else{
				if (listNode == null) {
					listNode = table[i];
					temp1 = table[i];
					temp2 = table[i]; // This doesnt move
					table[i] = null;
				} else {
					if (table[i].data < temp1.data) {
						temp1 = table[i];
					}

					listNode.sibling = table[i];
					listNode = listNode.sibling;
				}
			}
		}

		listNode.sibling = temp2;

		return temp1;
	}

	/**
	 * Displays the nodes of min binomial heap using level order traversal
	 */
	public void display() {
		if (numberOfNodes <= 0)
			System.out.println("No nodes in the heap to print");
		else{
			String[] s=new String[50];
			for(int i=0;i<50;i++){
				s[i] = "Level " + i + ": [";	
			}	

			displayed = 0;
			s=saveLevelNodes(minElement, 1,s);

			for(int i=1;i<maxLevel;i++)
				System.out.println(s[i].substring(0, s[i].length()-1)+"]");
		}

	}

	/**
	 * Saves the node values into the string array and returns it
	 */
	public String[] saveLevelNodes(HeapNode tempHead, int level, String[] levelValues) {
		if(maxLevel<level)
			maxLevel=level;

		if (displayed <= numberOfNodes) {
			HeapNode temp = tempHead;

			do {
				if (temp != null) {
					levelValues[level] += temp.data+" ,";
					displayed++;
					temp = temp.sibling;
				}

			} while (temp != tempHead);

			temp = tempHead;
			do {
				if (temp != null) {
					saveLevelNodes(temp.child, level + 1,levelValues);
					temp = temp.sibling;
				}
			} while (temp != tempHead);
		}
		return levelValues;
	}


}

	/**
	 * Node structure of the Binomial Heap
	 */
class HeapNode {
	public int data;
	public int degree;
	public HeapNode child;
	public HeapNode sibling;
	/**
	 * Constructor to create a node
	 */
	public HeapNode(int data) {
		this.data = data;
		this.degree = 0;
		this.child = null;
		this.sibling = null;
	}

	/**
	 * Copies the node properties to another node
	 */
	public void copyData(HeapNode tempHeapNode) {
		this.degree = tempHeapNode.degree;
		this.data = tempHeapNode.data;
		this.child = tempHeapNode.child;
		this.sibling = tempHeapNode.sibling;
	}
}