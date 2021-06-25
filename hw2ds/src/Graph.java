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
	final int a = new Random().ints(1,prime).findFirst().getAsInt();
	final int b = new Random().ints(0,prime).findFirst().getAsInt();
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
    	
    	//add nodes to hash table & heap
    	int indexForNodeInHashTable;
    	for (Node node:nodes) { // (N)
    		indexForNodeInHashTable = hashFunc(node.getId());
    		if (this.hashTable[indexForNodeInHashTable]==null) 
				this.hashTable[indexForNodeInHashTable]= new DoubleLinkedList< Node >();
    		this.hashTable[indexForNodeInHashTable].addNode(node); // O(1) amortized
    		this.numNodes++;
    		LazyInsertToMaximumHeap(node); // O(1)
    	}

    	// build heap from an array O(N)
    	int firstparent = (int) Math.floor((this.N - 1)/2);
    	for(int parent = firstparent; parent >=1; parent--) {
    		HeapifyDownForInit(parent);
    	}

    }
    
    public void test() {
    	Node [] nodes = new Node[7];
		for (int i =0; i< nodes.length; i ++) {
    		nodes[i] = new Node(i, 10*i);
    	}
				
    	Graph graph = new Graph(nodes);
    	checkifallNodesarethedsame(graph);
    	System.out.println("_________add_________");
    	testaddedge(graph,1,2);
    	testaddedge(graph,3,2);
    	testaddedge(graph,3,4);
    	testaddedge(graph,2,4);
    	testaddedge(graph,0,1);
    	testaddedge(graph,0,2);
    	testaddedge(graph,5,2);
    	testaddedge(graph,0,6);
    	testaddedge(graph,10,10);
		graph.PrintAllEdgeByNode();
		checkifallNodesarethedsame(graph);
		System.out.println("heap after add edges");
		graph.PrintHeap();
		System.out.println();

		System.out.println("_________delete_________");
		testdelete(graph);

    }
    
    public void testaddedge( Graph graph ,int id1, int id2) {
    	
    	Node node1 = graph.getNode(id1);
    	Node node2 = graph.getNode(id2);
    	boolean re =graph.addEdge(id1, id2);
    	if (node1 == null && re)
    		System.out.println("addEge error! -- " + id1 + " not in graph && add addEdge is true");
    	if (node2 == null && re)
    		System.out.println("addEge error! -- " + id2 + " not in graph && add addEdge is true");	
    }
    public void testdelete( Graph graph) {
		Random rnd = new Random();
		for(int i=0; i< graph.MaximumHeap.length; i++) {
			int x = rnd.nextInt(graph.hashTable.length);
//			int x = i;
//			if (x!=4 ) {
				Node node = graph.getNode(x);
				boolean b = graph.deleteNode(x);
				System.out.println("delete " + x + " after delete = ");
//				graph.PrintHashTable();
				graph.PrintHeap();
//				graph.PrintAllEdgeByNode();
				if(b &&  node == null)
					System.out.println(x + " not in graph && delete is true");
				if(!b && node != null) {
					System.out.println(x + " in graph && delete is false");
//				}
			}
			System.out.println();	
	    }
    }
    public void testmaximumheap(Graph graph) {
    	
    }
    	
    
    public void checkifallNodesarethedsame(Graph graph) {
    	for(int i = 1; i< graph.MaximumHeap.length; i++) {
    		Node nodeinheap = graph.MaximumHeap[i];
    		Node nodeinhash = graph.getNode(nodeinheap.getId());
    		if(nodeinheap !=nodeinhash )
    			System.out.println("nodes are not in the same place ");
    		if(nodeinheap.getNeighborhoodWeight() != nodeinhash.getNeighborhoodWeight())
    			System.out.println("id = " + nodeinhash.getId() + "neighboor weight in hash = " + nodeinhash.getNeighborhoodWeight() + " in heap = " + nodeinheap.getNeighborhoodWeight());
    		if(nodeinheap.getindexinMaximumHeap() != nodeinhash.getindexinMaximumHeap())
    			System.out.println("id = " + nodeinhash.getId() + " index in heap : in hash is " + nodeinhash.getindexinMaximumHeap() + " in heap is " + nodeinheap.getindexinMaximumHeap() );
    	}
    	
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

	
	public DoubleLinkedList.LinkedNode<Node> getWrappedNode(int k) {
		DoubleLinkedList<Graph.Node> y= hashTable[hashFunc(k)];
		if (y == null)
			return null;
		DoubleLinkedList.LinkedNode<Graph.Node> x = y.getHead();
		while (x!=null) {
			if (x.getNode().getId() == k) return x;
			x = x.getNext();
		}
		return null;
	}


	public Node getNode(int k) {
		DoubleLinkedList<Graph.Node> y =hashTable[hashFunc(k)];
		if (y== null)
			return null;
		DoubleLinkedList.LinkedNode<Graph.Node> x = y.getHead();
		while (x!=null) {
			if (x.getNode().getId() == k) return x.getNode();
			x = x.getNext();
		}
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
    	System.out.println("node_id= "  + node_id);
    	Node n = getNode(node_id);
    	System.out.println("Node = " + n.getId() + ", " + n.getWeight() +", " + n.getNeighborhoodWeight());
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
    	Node node2 = getNode(node2_id);
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
		DoubleLinkedList.LinkedNode<Node> node = getWrappedNode(node_id);
		if (node==null) return false;
		// remove from other node's relationship lists
		EdgeList.Edge<Node> x = (EdgeList.Edge< Node>) node.getNode().getNeighbors().getHead();
		while (x!=null){
			x.getNode().getNeighbors().removeNode(x.getCon());
			x.getNode().setNeighborhoodWeight(x.getNode().getNeighborhoodWeight()-node.getNode().getWeight());
			//heap here
//			System.out.println("heapifiyinf " +x.getNode().getindexinMaximumHeap() );
			HeapifyDown(x.getNode().getindexinMaximumHeap());
			x = (EdgeList.Edge< Node >) x.getNext();
			numEdges--;

		}

		// remove the node
		removeFromMaximumHeap(node.getNode());
		hashTable[hashFunc(node_id)].removeNode(node);
        return true;
    }
    
    
    public int getNumNodes() {return this.numNodes; }
    public int getNumEdges() { return this.numEdges; }

    
    public void LazyInsertToMaximumHeap(Node newNode) { 
    	this.MaximumHeap[this.numNodes] = newNode;
    	newNode.setindexinMaximumHeap(this.numNodes);
    }

    public void removeFromMaximumHeap(Node node) { 
		switchValuesByindexes(node.getindexinMaximumHeap(),numNodes);
		MaximumHeap[numNodes] = null;
		this.numNodes--;
		HeapifyDown(node.getindexinMaximumHeap());
	}
    
    public int Parent(int index) { return (int)Math.floor(index/2); }
    public int LeftSon(int index) {	return index*2;}
    public int RightSon(int index) {return index*2 +1;}
    
    public void switchValuesByindexes(int index1, int index2) {
//    	System.out.println("swict " + index1 + ", "+ index2);
    	this.MaximumHeap[index1].setindexinMaximumHeap(index2);
    	this.MaximumHeap[index2].setindexinMaximumHeap(index1);
    	Node temp = this.MaximumHeap[index1];
    	this.MaximumHeap[index1] = this.MaximumHeap[index2];
    	this.MaximumHeap[index2] = temp;
    }
    
    public void HeapifyUp(int indexOfNodeInMaximumHeap) { 
    	int parent = Parent(indexOfNodeInMaximumHeap);
    	while(indexOfNodeInMaximumHeap > 1 && this.MaximumHeap[indexOfNodeInMaximumHeap].getNeighborhoodWeight() 
				> this.MaximumHeap[parent].getNeighborhoodWeight()) {
    		switchValuesByindexes(indexOfNodeInMaximumHeap,parent);
    		indexOfNodeInMaximumHeap = parent;
    		parent = Parent(indexOfNodeInMaximumHeap);
    	}
    }
    
    public void HeapifyDownForInit(int indexOfNodeInMaximumHeap) {
    	int left = LeftSon(indexOfNodeInMaximumHeap);
    	int right = RightSon(indexOfNodeInMaximumHeap);
    	int bigger = indexOfNodeInMaximumHeap;
    	int len = this.MaximumHeap.length;
    	if(left < len && this.MaximumHeap[left].getNeighborhoodWeight()
				> this.MaximumHeap[bigger].getNeighborhoodWeight() ) {
    		bigger = left;
    	}
    	if (bigger > indexOfNodeInMaximumHeap) {
    		switchValuesByindexes(indexOfNodeInMaximumHeap, bigger);
    		HeapifyDownForInit(bigger);
    	}
    	if (right <len && this.MaximumHeap[right].getNeighborhoodWeight()
				> this.MaximumHeap[indexOfNodeInMaximumHeap].getNeighborhoodWeight()) {
    		bigger = right;
    	}
    	if (bigger > indexOfNodeInMaximumHeap) {
    		switchValuesByindexes(indexOfNodeInMaximumHeap, bigger);
    		HeapifyDownForInit(bigger);
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
    		switchValuesByindexes(indexOfNodeInMaximumHeap, bigger);
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
    	DoubleLinkedList<Graph.Node> y = x2.getNode().getNeighbors();
    	if (y != null) {
    		DoubleLinkedList.LinkedNode<Graph.Node> x = y.getHead();
	    	while(x != null) {
				System.out.print("<");
				System.out.print(x2.getNode().getId()+ ",");
				System.out.print(x.getNode().getId());
				System.out.print(">");
				x = x.getNext();
	    	}
    	}
    }
    
    public void PrintAllEdgeByNode() {
    	for(int index =0; index< this.hashTable.length; index++) {
    		DoubleLinkedList<Graph.Node> x = this.hashTable[index];
//    		System.out.println("index = " + index);
    		if (x!= null) {
    			DoubleLinkedList.LinkedNode<Graph.Node> y =x.getHead();
	    		while(y!= null) {
					System.out.print("Edges for node " + y.getNode().getId() + " {");
	    			PrintEgdeListForNode(y);
	    			y = y.getNext();
					System.out.println("}");
				}
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
    }


    static class DoubleLinkedList <T> {
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


		//find and remove a node
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

		//remove a node directly without searching for it
		public boolean removeNode(LinkedNode<T> x) {
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
	
			public LinkedNode(Node node) {this.node = node;	}
	
			public LinkedNode<Node> getNext() {	return this.next;}
	
			public void setNext(LinkedNode<Node> next) {this.next = next;}
	
			public LinkedNode<Node> getPrevious() {	return this.previous;}
	
			public void setPrevious(LinkedNode<Node> previous) {this.previous = previous;}
	
			public Node getNode() {	return this.node;}
			
//			@Override
//			public String toString() {
//				Graph.Node n = (Graph.Node) this.node;
//				Integer s = n.getId();
//				return s.toString();
//			}
			public void unlinkNode() {
				// update connections
				if (next != null)
					next.previous = previous;
				if (previous != null)
					previous.next = next;
			}
		}
    }

	static class EdgeList<T extends Graph.Node> extends DoubleLinkedList<T> {
		
		public Edge addNode(T node) {
			//Create a new node
			Edge< T > newNode = new Edge<T>(node);
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

		//add an edge between two nodes
		public static void addEdge(Graph.Node node1, Graph.Node node2) {
			Edge<Graph.Node> eNode2 = node1.getNeighbors().addNode(node2);
			Edge<Graph.Node> eNode1 = node2.getNeighbors().addNode(node1);
			eNode1.setCon(eNode2);
			eNode2.setCon(eNode1);
			node1.setNeighborhoodWeight(node1.getNeighborhoodWeight()+node2.getWeight());
			node2.setNeighborhoodWeight(node2.getNeighborhoodWeight()+node1.getWeight());
		}
	
	
		static class Edge<E extends Graph.Node > extends DoubleLinkedList.LinkedNode<E> {
			
			private Edge<E> con;
			
			public Edge(E node) {super(node);}
			
			public Edge(E node,E con) {
				super(node);
				this.con=new Edge(con);
				this.con.setCon(this);
			}
	
			public Edge<E> getCon() {return con;}
	
			public void setCon(Edge<E> con) {this.con = con;}
		}
	}
}