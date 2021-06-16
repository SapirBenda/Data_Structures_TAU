import java.util.*;

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
	final int a = new Random().ints(1,prime).findFirst().getAsInt();;
	final int b = new Random().ints(0,prime).findFirst().getAsInt();;
	private DoubleLinkedList<Node> [] hashTable;
	private int N;
	
	
    public Graph(Node [] nodes){
    	//init sizes
    	this.numEdges=0;
		this.numNodes=0;
		this.N=nodes.length;
		
		//init data structures
		this.MaximumHeap = new Node[N + 1];
    	this.hashTable = new DoubleLinkedList[N+1];
    	
    	//add nodes
    	int indexForNodeInHashTable;
    	for (Node node:nodes) {
    		indexForNodeInHashTable = hashFunc(node.getId());
    		if (this.hashTable[indexForNodeInHashTable]==null) 
				this.hashTable[indexForNodeInHashTable]= new DoubleLinkedList< Node >();
    		this.hashTable[indexForNodeInHashTable].addNode(node);
    		this.numNodes++;
    		insertToMaximumHeap(node);
    	}
    	System.out.println("HashTable: ");
		PrintHashTable();
		System.out.print("Heap: ");
		PrintHeap();
    	System.out.println("----- finish init graph -----");
    	System.out.println();
    }
    
    public void test() {
    	Node [] nodes = new Node[5];
		for (int i =0; i< nodes.length; i ++) {
    		nodes[i] = new Node(i, 10*i);
    	}
				
    	Graph graph = new Graph(nodes);
//    	for(Node node: nodes) {
//			node.PrintNode();
//			System.out.println();
//		}
//    	graph.PrintAllEdgeByNode();
    	graph.addEdge(1, 2);
    	

    }

    public static void main(String[] args) {
		System.out.println("begin test");
    	Node [] nodes = new Node[0];
		Graph graph = new Graph(nodes);
		graph.test();
	}


	private int hashFunc(int k) {
		return Math.floorMod(Math.floorMod((a*k+b),prime), N);
	}


	public Node getNode(int k) {
		DoubleLinkedList.LinkedNode<Node> x = hashTable[hashFunc(k)].getHead();
		while (x!=null)
			if (x.getNode().getId()==k) return x.getNode();
			x = x.getNext();
		return null;
	}


	/**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){ // O(1)
        return this.MaximumHeap[1];
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
        return true;
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
		EdgeList.Edge<Node> x = (EdgeList.Edge< Node>) node.getNeighbors().getHead();
		while (x!=null){ // לסדר את זה
			numEdges--;
			x.getCon().unlinkNode();
			//heap here
			x = (EdgeList.Edge< Node >) x.getNext();
		}
    	this.numNodes--;
        return false;
    }
    
    public int getNumNodes() {return this.numNodes; }
    public int getNumEdges() { return this.numEdges; }

    
    public void insertToMaximumHeap(Node newNode) { // O(logn)
    	this.MaximumHeap[this.numNodes] = newNode;
    	newNode.setindexinMaximumHeap(this.numNodes);
    	HeapifyUp(this.numNodes);
    }
    
    public int Parent(int index) { return (int)Math.floor(index/2); }
    public int LeftSon(int index) {	return index*2;}
    public int RightSon(int index) {return index*2 +1;}
    
    public void switchvaluesByindexes(int index1, int index2) {
    	this.MaximumHeap[index1].setindexinMaximumHeap(index2);
    	this.MaximumHeap[index2].setindexinMaximumHeap(index1);
    	Node temp = this.MaximumHeap[index1];
    	this.MaximumHeap[index1] = this.MaximumHeap[index2];
    	this.MaximumHeap[index2] = temp;

    }
    
    public void HeapifyUp(int indexOfNodeInMaximumHeap) { // O(logn)
    	int parent = Parent(indexOfNodeInMaximumHeap);
    	while(indexOfNodeInMaximumHeap > 1 && this.MaximumHeap[indexOfNodeInMaximumHeap].getNeighborhoodWeight() 
						> this.MaximumHeap[parent].getNeighborhoodWeight()) {
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
    
    public void PrintHeap() {
//    	System.out.println("The Heap = ");
    	System.out.print("[");
    	for(int index =0; index< this.MaximumHeap.length; index++) {
    		if (this.MaximumHeap[index] == null)
    			System.out.print("-1, ");
    		else {
    			Node node = this.MaximumHeap[index];
    			System.out.print("("+ node.getId() + ", " + node.getNeighborhoodWeight()+ "),");
    		}
    	}
 
    	System.out.println("]");
    }
    
    public void PrintHashTable() {
    	for(int index=0; index< this.hashTable.length;index++) {
    		System.out.print("[ " + index + " ---> ");
    		if (this.hashTable[index] == null)
    			System.out.print("#");
    		else {
    			DoubleLinkedList.LinkedNode<Graph.Node> x = this.hashTable[index].getHead();
    			while(x != null) {
    				System.out.print("("+ x.getNode().getId() + ", " + x.getNode().getNeighborhoodWeight() + "),");
    				x = x.getNext();
    			}
    		}
    		System.out.println(" ]");
    	}
    }
    
    public void PrintEgdeListForNode(DoubleLinkedList.LinkedNode<Graph.Node> x2) {
    	DoubleLinkedList<Graph.Node> y = x2.getNeighbors();
    	System.out.print("{");
    	if (y != null) {
    		DoubleLinkedList.LinkedNode<Graph.Node> x = y.getHead();
	    	while(x != null) {
	    		System.out.print(x.getNode().getId()+ ",");
	    		x = x.getNext();
	    	}
    	}
    	System.out.println("}");
    }
    
    public void PrintAllEdgeByNode() {
    	for(int index =0; index< this.hashTable.length; index++) {
    		DoubleLinkedList<Graph.Node> x = this.hashTable[index];
//    		System.out.println("index = " + index);
    		if (x!= null) {
    			DoubleLinkedList.LinkedNode<Graph.Node> y =x.getHead();
    			System.out.print("Edges for node " + y.getIdLinkedNode() + "{");
	    		while(y!= x.getTail()) {
	    			System.out.print(y.getIdLinkedNode() + ": ");
	    			PrintEgdeListForNode(y);
	    			y = y.getNext();
	    		}
	    		System.out.println("}");
    		}
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
    	private EdgeList<Graph.Node> neighbors;
    	private int indexinMaximumHeap;
    	
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.neighborhoodWeight = this.weight;
            this.neighbors = new EdgeList<Graph.Node>();
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
        
        public int getNeighborhoodWeight() {return (this!= null) ? this.neighborhoodWeight: 0; }

		public void setNeighborhoodWeight(int nWeight) { this.neighborhoodWeight = nWeight; }

		public EdgeList<Node> getNeighbors() { return this.neighbors; }

		public int getindexinMaximumHeap() { return this.indexinMaximumHeap;}
		public void setindexinMaximumHeap(int newindex) { this.indexinMaximumHeap = newindex;}
		public void  PrintNode() {
			System.out.println("id = " +this.id + ",\n" + "weight= " + this.weight + ",\n"+ "neighboorsum= " +  this.neighborhoodWeight + ",\n"+ "index in heap = " + this.indexinMaximumHeap);
		}
    }
}

    class DoubleLinkedList <T> {
		protected LinkedNode< T > head, tail;
		
		public DoubleLinkedList(){ this.head = this.tail = null;}
	
		public LinkedNode< T > getHead() { return (this.head != null) ? this.head : null; }
	
		public LinkedNode< T > getTail() {return (this.tail != null) ? this.tail : null;}
	
		public LinkedNode< T > findNode(T node) {
			LinkedNode< T > x = head;
			while (x != null) {
				if (x.getNode() == node) return x;
				x = x.getNext();
			}
			return null;
		}
		
		
		//add a node to the end of the list
		public LinkedNode< T > addNode(T node) {
			//Create a new node
			LinkedNode< T > newNode = new LinkedNode< T >(node);
			
	
			//if list is empty, head and tail points to newNode
			if (head == null) {
				head = tail = newNode;
				//head's previous will be null
				head.setPrevious(null);
				//tail's next will be null
				tail.setNext(null);
			} else {
				//add newNode to the end of list. tail->next set to newNode
				tail.setNext(newNode);
				//newNode->previous set to tail
				newNode.setPrevious(tail);
				//newNode becomes new tail
				tail = newNode;
				//tail's next point to null
				tail.setNext(null);
			}
			return newNode;
		}
		
		public boolean removeNode(T node) {
			LinkedNode< T > x = findNode(node);
	
			//node not found
			if (x == null)
				return false;
	
			//update tail and head if necessary
			if (x == head)
				head = x.getNext();
			if (x == tail)
				tail = x.getPrevious();
	
			x.unlinkNode();
	
			return true;
		}
	
	
		static class LinkedNode<Node> {
			protected LinkedNode<Node> previous = null;
			protected LinkedNode<Node> next = null;
			final Node node;
	
			public LinkedNode(Node node) {
				this.node = node;
			}
	
			public int getIdLinkedNode() {
				return ((Graph.Node) this.node).getId() ;
			}
	
			public DoubleLinkedList<Graph.Node> getNeighbors() {
				// TODO Auto-generated method stub
				return ((Graph.Node) this.node).getNeighbors();
			}
	
			public LinkedNode<Node> getNext() {
				return this.next;
			}
	
			public void setNext(LinkedNode<Node> next) {
				this.next = next;
			}
	
			public LinkedNode<Node> getPrevious() {
				return this.previous;
			}
	
			public void setPrevious(LinkedNode<Node> previous) {
				this.previous = previous;
			}
	
			public Node getNode() {
				return this.node;
			}
			
			@Override
			public String toString() {
				Graph.Node n = (Graph.Node) this.node;
				Integer s = n.getId();
				return s.toString();
			}
	
			public void unlinkNode() {
				// update connections
				if (next != null)
					next.previous = previous;
				if (previous != null)
					previous.next = next;
			}
	
		}
    }

	class EdgeList<T extends Graph.Node> extends DoubleLinkedList<T> {
		//add an edge between two nodes
		
		
		public static void addEdge(Graph.Node node1, Graph.Node node2) {
			Edge<Graph.Node> eNode2 = (Edge< Graph.Node >) node1.getNeighbors().addNode(node2);
			Edge<Graph.Node> eNode1 = (Edge< Graph.Node >) node2.getNeighbors().addNode(node1);
			eNode1.setCon(eNode2);
			eNode2.setCon(eNode1);
			node1.setNeighborhoodWeight(node1.getNeighborhoodWeight()+node2.getWeight());
			node2.setNeighborhoodWeight(node2.getNeighborhoodWeight()+node1.getWeight());
		}
	
	
		static class Edge<E extends Graph.Node > extends DoubleLinkedList.LinkedNode<E> {
			
			private Edge<E> con;
			
			public Edge(E node) {
				super(node);
			}
			
			public Edge(E node,E con) {
				super(node);
				this.con=new Edge(con);
				this.con.setCon(this);
			}
	
			public Edge<E> getCon() {
				return con;
			}
	
			public void setCon(Edge<E> con) {
				this.con = con;
			}
	
			@Override
			public void unlinkNode() {
				super.unlinkNode();
				// remove weight from the connected node's neighborhood
				getNode().setNeighborhoodWeight(getNode().getNeighborhoodWeight()-getCon().getNode().getWeight());
			}
		}
	}

