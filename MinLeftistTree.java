//package datastructures;

public class MinLeftistTree {

	
	public static Node root=null;

	/**
	 * Takes an integer as input and insert a node in to the Min Leftist Tree by creating a new 	node.
	 */
	public void insert(int data){
		Node x=new Node();
		x.value=data;
		root=meld(root,x);
	}
	
	/**
	 * Removes the root of the Min Leftist Tree and melds its left child and right child.
	 */
	public void removeMin(){
		if(root!=null){
			root=meld(root.lChild,root.rChild);
		}
	}

	/**
	 * Melds the given two nodes by recursive call to meld. Compares the s values of 	resulting tree and swaps left child and right child if necessary.
	 */
	public Node meld(Node node1, Node node2){
		if(node1==null && node2==null)
			return node1;
		else if (node1==null)
			return node2;
		else if(node2==null)
			return node1;
		else{
			if(node1.value<=node2.value){
				Node temp=meld(node1.rChild,node2);
				node1.rChild=temp;
				if(sValue(node1.lChild)<sValue(node1.rChild))
					swapChilds(node1);
				return node1;
			}
			else {
				Node temp=meld(node2.rChild,node1);
				node2.rChild=temp;
				if(sValue(node2.lChild)<sValue(node2.rChild))
					swapChilds(node2);
				return node2;
			}	
		}

	}

	/**
	 * Finds the s value of the given node and returns it to the calling function.
	 */
	public int sValue(Node node){
		if(node==null)
			return 0;
		else if(node.rChild==null)
			return 1;
		else if(node.lChild==null){
			swapChilds(node);
			return 1;
		}
		else 
			return node.lChild.s<node.rChild.s?node.lChild.s+1:node.rChild.s+1;
	}

	/**
	 * Swaps left child and right child of a given node.
	 */
	public void swapChilds(Node node){
		Node temp=node.lChild;
		node.lChild=node.rChild;
		node.rChild=temp;
	}

	/**
	 * Displays node values of Min Leftist Tree on console by using Queue data structure, 	after the given operations are performed on the tree.
	 */
	public void displayTree()
	{
		try
		{
			if(root != null)
			{
				QueueStructure queue = new QueueStructure();
				Node temp = root;
				String s=new String();
				
				Node negativeNode = new Node();
				negativeNode.value = -1;
				s=s+"["+temp.value+",";
				
				queue.insert(temp);
				queue.insert(negativeNode);

				while(!queue.isQueueEmpty()) {
					temp = queue.remove();
					if(temp.value == -1) {
						if(queue.isQueueEmpty())
							continue;
						else 
							queue.insert(negativeNode);							
					}
					else {
						//Print the Children of the node and insert them into the Queue.
						if( temp.lChild != null) {
							s=s+temp.lChild.value + ",";					
							queue.insert(temp.lChild);					
						}
						if(temp.rChild != null) {
							s=s+temp.rChild.value + ",";							
							queue.insert(temp.rChild);
						}						
					}					
				}
				System.out.println(s.substring(0, s.length()-1)+"]");
			}
			else 
				System.out.print("Min leftist tree is empty");
		}
		catch (Exception e)	{
			System.err.print("Error : " + e.getMessage());
		}
	}
}

class Node {
	int value=0;
	int s=1;
	Node lChild=null;
	Node rChild=null;
}

class QueueStructure {

	public QueueNode queueHead=null;

	/**
	 * Inserts a queue node at  rear of the queue
	 */
	public void insert(Node node){
		if(queueHead == null){
			queueHead = new QueueNode();
			queueHead.object=node;
		}
		else {
			QueueNode temp=queueHead;

			while(temp.link!=null){
				temp=temp.link;
			}
			QueueNode newNode=new QueueNode();
			newNode.object=node;
			temp.link=newNode;
		}
	}

	/**
	 * the front node of the queue and returns it to the calling function
	 */
	public Node remove(){
		Node temp=queueHead.object;
		queueHead=queueHead.link;
		return temp;
	}
	
	/**
	 * Returns boolean value true if queue is empty
	 */
	public boolean isQueueEmpty(){
		if(queueHead==null)
			return true;
		else
			return false;
	}
	
	class QueueNode {
		public Node object=null;
		public QueueNode link=null;
	}
}
