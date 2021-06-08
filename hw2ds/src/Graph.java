/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

import java.util.*;

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {
    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
	private int numNodes;
	private int numEdges;
	private Node [] MaximumHeap;
	final int prime = (int) Math.pow(10, 9) + 9;
	final int a;
	final int b;
	private List<Node> [] hashTable;
	private int N;
	
	
    public Graph(Node [] nodes){
    	//init sizes
    	this.numEdges=0;
		this.numNodes=0;
		this.N=nodes.length;

		//init numbers for the hash function
		this.a = new Random().ints(1,prime).findFirst().getAsInt();
		this.b = new Random().ints(0,prime).findFirst().getAsInt();

		//init data structures
		this.MaximumHeap = new int[N+1];
    	this.hashTable = new LinkedList[N+1];

    	//add nodes
    	int indexForNodeInHashTable;
    	for (Node node:nodes) {
    		indexForNodeInHashTable = hashFunc(node.getId());
    		if (this.hashTable[indexForNodeInHashTable]==null)
				this.hashTable[indexForNodeInHashTable]=new LinkedList<Node>();
    		this.hashTable[indexForNodeInHashTable].add(node);
    		insertToMaximumHeap(getNode(node.id));
    		this.numNodes++;
    	}
    }


	private int hashFunc(int k) {
		return Math.floorMod(Math.floorMod((a*k+b),prime), N);
	}


	public Node getNode(int k) {
		for (Node node: hashTable[hashFunc(k)])
			if (node.id==k)
				return node;
		return null;
	}


	/**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){ // O(1)
        return (Node) this.hashTable[this.MaximumHeap[1]];
    }
    
    
    @Override 
    public String toString() {
    	return "HashTable = " + this.hashTable + "\t" +
    					"MaximunHeap = " + this.MaximumHeap;
    }

    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
    	return getNode(node_id).getNeighborhoodWeight();
    }


    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     *
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     */
    public boolean addEdge(int node1_id, int node2_id){
    	Node node1 = getNode(node1_id);
    	Node node2 = getNode(node1_id);
    	if(node1 == null || node2 == null)
    		return false;

    	// both nodes in the graph
		EdgeList.addEdge(node1,node2);
		//correct MaximumHeap
		HeapifyUp(node1.getindexinMaximumHeap());
		HeapifyUp(node2.getindexinMaximumHeap());
    	
    	this.numEdges ++;
        return false;
    }

    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        //TODO: implement this method.
		Node node = getNode(node_id);
		EdgeList.EdgeNode x = node.getNeighbors().getHead();
		while (x!=null){ // לסדר את זה
			numEdges--;
			x.getConnectedNode().deleteNode();
			//heap here
			x = x.getNext();
		}
    	this.numNodes--;
        return false;
    }
    
    public int getNumNodes() {return this.numNodes; }
    public int getNumEdges() { return this.numEdges; }

    
    public void insertToMaximumHeap(Node newNode) { // O(logn)
    	this.MaximumHeap[this.numNodes] = newNode;
    	HeapifyUp(this.numNodes);
    }
    
    public int Parent(int index) { return (int)Math.floor(index/2); }
    public int LeftSon(int index) {	return index*2;}
    public int RightSon(int index) {return index*2 +1;}
    
    public void switchvaluesByindexes(int index1, int index2) {
    	Node temp = this.MaximumHeap[index1];
    	this.MaximumHeap[index1] = this.MaximumHeap[index2];
    	this.MaximumHeap[index1].setindexinMaximumHeap(this.MaximumHeap[index2].getindexinMaximumHeap());
    	this.MaximumHeap[index2] = temp;
    	this.MaximumHeap[index2].setindexinMaximumHeap(temp.getindexinMaximumHeap());
    }
    
    public void HeapifyUp(int indexOfNodeInMaximumHeap) { // O(logn)
    	int temp, parent = Parent(indexOfNodeInMaximumHeap);
    	while(indexOfNodeInMaximumHeap > 1 && this.MaximumHeap[indexOfNodeInMaximumHeap].getNeighborhoodWeight() 
						< this.MaximumHeap[parent].getNeighborhoodWeight()) {
    		switchvaluesByindexes(indexOfNodeInMaximumHeap,parent);
    		indexOfNodeInMaximumHeap = parent;
    		parent = Parent(indexOfNodeInMaximumHeap);
    	}
    }
    
    public void HeapifyDown(int indexOfNodeInMaximumHeap) {
    	int left = LeftSon(indexOfNodeInMaximumHeap);
    	int right = RightSon(indexOfNodeInMaximumHeap);
    	int bigger = indexOfNodeInMaximumHeap;
    	if(left < this.numNodes && this.MaximumHeap[left].getNeighborhoodWeight()
				> this.MaximumHeap[bigger].getNeighborhoodWeight() ) {
    		bigger = left;
    	}
    	if (right < this.numNodes && this.MaximumHeap[right].getNeighborhoodWeight()
				> this.MaximumHeap[bigger].getNeighborhoodWeight()) {
    		bigger = right;
    	}
    	if (bigger > indexOfNodeInMaximumHeap) {
    		switchvaluesByindexes(indexOfNodeInMaximumHeap, bigger);
    		HeapifyDown(bigger);
    	}
    }


    /**
     * This class represents a node in the graph.
     */
    public static class Node{
        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
    	private int id;
    	private int weight;
    	private int neighborhoodWeight;
    	private EdgeList neighbors;
    	private int indexinMaximumHeap;
    	
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.neighborhoodWeight = this.weight;
            this.neighbors = new EdgeList();
        }

        /**
         * Returns the id of the node.
         * @return the id of the node.
         */
        public int getId(){ return this.id; }

        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight(){return this.weight;}
        
        public int getNeighborhoodWeight() {return this.neighborhoodWeight; }

		public void setNeighborhoodWeight(int nWeight) { this.neighborhoodWeight = nWeight; }

		public EdgeList getNeighbors() { return this.neighbors; }

		public int getindexinMaximumHeap() { return this.indexinMaximumHeap;}
		public void setindexinMaximumHeap(int newindex) { this.indexinMaximumHeap = newindex;}
    }
    

    public void test() {
    	Node [] nodes = new Node[5];
		for (int i =0; i< nodes.length; i ++) {
    		nodes[i] = new Node(i, 10*i);
    	}
    	
    	Graph graph = new Graph(nodes);
    	System.out.println(graph);
    }

    public static void main(String[] args) { // אני בטוח שיש דרך יותר טובה לעשות את זה
		Node [] nodes = new Node[0];
		Graph graph = new Graph(nodes);
		graph.test();
	}
}

class EdgeList {
	//A node class for doubly linked list
	//Initially, head and tail is set to null
	private EdgeNode head, tail;

	public EdgeList() { head = tail = null; }

	public EdgeNode getHead() {
		return head;
	}

	public class EdgeNode {
		final Graph.Node node;
		private EdgeNode previous;
		private EdgeNode next;
		private EdgeNode connectedNode;

		public EdgeNode(Graph.Node node) { this.node = node; }

		public EdgeNode(Graph.Node node, Graph.Node con) {
			this.node = node;
			this.connectedNode = new EdgeNode(con);
			this.connectedNode.setConnectedNode(this);
		}

		public EdgeNode getNext() {
			return next;
		}

		public void setConnectedNode(EdgeNode node) {
			this.connectedNode = node;
		}

		public EdgeNode getConnectedNode() { return this.connectedNode; }

		public void deleteNode() {
			// update connections
			if (next!=null)
				next.previous=previous;
			if (previous!=null)
				previous.next=next;

			// remove weight from the connected node's neighborhood
			connectedNode.node.setNeighborhoodWeight(
					connectedNode.node.getNeighborhoodWeight()-node.getWeight());
		}
	}

	//add an edge between two nodes
	public static void addEdge(Graph.Node node1, Graph.Node node2) {
		EdgeNode eNode2 = node1.getNeighbors().addNode(node2);
		EdgeNode eNode1 = node2.getNeighbors().addNode(node1);
		eNode1.setConnectedNode(eNode2);
		eNode2.setConnectedNode(eNode1);
		node1.setNeighborhoodWeight(node1.getNeighborhoodWeight()+node2.getWeight());
		node2.setNeighborhoodWeight(node2.getNeighborhoodWeight()+node1.getWeight());
	}

	//add a node to the end of the list
	public EdgeNode addNode(Graph.Node node) {
		//Create a new node
		EdgeNode newNode = new EdgeNode(node);

		//if list is empty, head and tail points to newNode
		if(head == null) {
			head = tail = newNode;
			//head's previous will be null
			head.previous = null;
			//tail's next will be null
			tail.next = null;
		}
		else {
			//add newNode to the end of list. tail->next set to newNode
			tail.next = newNode;
			//newNode->previous set to tail
			newNode.previous = tail;
			//newNode becomes new tail
			tail = newNode;
			//tail's next point to null
			tail.next = null;
		}
		return newNode;
	}

	// delete node from the list
	public boolean deleteNode(Graph.Node node) {
		// check if node in list - binarySearch [[<<???>> HOW?]]
		EdgeNode x = head;
		while (node!=x.node && x!=null)
			x=x.next;

		//node not found
		if (x==null)
			return false;

		//update tail and head if necessary
		if (x==head)
			head=x.next;
		if (x==tail)
			tail=x.previous;

		//delete nodes
		x.getConnectedNode().deleteNode();
		x.deleteNode();

		return true;
	}
}



