/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

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
	private Set<Node> allNodeInGraph;
	private int [] MaximumHeap;
	private Consumer<Integer> innerhash; 
	final int prime = (int) Math.pow(10, 9) + 9;
	private  Consumer<Integer> hashFunc;
	private List<Node> [] hashTable;
	
	
    public Graph(Node [] nodes){
    	int N = nodes.length;
    	this.allNodeInGraph = new HashSet<Node>(Array.asList(nodes));
    	this.numEdges =0;
    	this.innerhash = (k) -> (Math.floorMod(k, prime));////// need to check this hash function!!
    	this.hashFunc = (K,i) -> (Math.floorMod(innerhash(K) +i, N)); ////// need to check this hash function!!
    	this.MaximumHeap = new int[N+1];
    	this.hashTable =  = new List<Node> [N+1];
    	this.numNodes=1;
    	int indexfornodeinhashTable;
    	for (Node node:nodes) {
    		indexfornodeinhashTable = hashFunc(node.getId(), this.numNodes);
    		this.hashTable[indexfornodeinhashTable].add(node);
    		insertToMaximumHeap(indexfornodeinhashTable);
    	}
    }

    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){ // O(1)
        return this.hashTable[this.MaximumHeap[1]];
    }
    
    
    @Override 
    public String toString() {
    	String output = "All nodes = " + this.allNodeInGraph + "\t" + 
    					"HashTable = " + this.hashTable + "\t" +
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
    	return this.hashTable[this.hashFunc(node_id)].getNeighborhootWeight();
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
        //TODO: implement this method.
    	Node node1 = this.hashTable[this.hashFunc(node1_id)];
    	Node node2 = this.hashTable[this.hashFunc(node2_id)];
    	if(!this.allNodeInGraph.contains(node1) || !this.allNodeInGraph.contains(node2))
    		return false;
    	// both nodes in the graph
    	
    	DoublyLinkedListNode node2Innode1list = node1.getneighbors().addNode(node2);
    	node1.setNeighborhoodWeight(node2.getWeight(), "+");
    	DoublyLinkedListNode node1Innode2list = node2.getneighbors().addNode(node1);
    	node2.setNeighborhoodWeight(node1.getWeight(), "+");
    	node2Innode1list.setEdge(node1Innode2list);
    	node1Innode2list.setEdge(node2Innode1list);
    	
    	// need to correct naximum heap!!!!!!!
    	
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
    	this.numNodes--;
        return false;
    }
    
    public int getNumNodes() {return this.numNodes; }
    public int getNumEdges() { return this.numEdges; }

    
    public void insertToMaximumHeap(int indexOfNodeInHashTable) { // O(logn)
    	this.MaximumHeap[this.numNodes] = indexOfNodeInHashTable;
    	HeapifyUp(this.numNodes);
    	this.numNodes ++;
    }
    
    public int Parent(int index) { return (int)Math.floor(index/2); }
    public int LeftSon(int index) {	return index*2;}
    public int RightSon(int index) {return index*2 +1;}
    
    public void switchvaluesByindexes(int index1, int index2) {
    	int temp = this.MaximumHeap[index1];
    	this.MaximumHeap[index1] = this.MaximumHeap[index2];
    	this.MaximumHeap[index2] = temp;
    }
    
    public void HeapifyUp(int indexOfNodeInMaximumHeap) { // O(logn)
    	int temp, parent = Parent(indexOfNodeInMaximumHeap);
    	while(indexOfNodeInMaximumHeap > 1 &&
    			this.hashTable[this.MaximumHeap[indexOfNodeInMaximumHeap]].getNeighborhootWeight() > this.hashTable[this.MaximumHeap[parent]].getNeighborhootWeight()) {
    		switchvaluesByindexes(indexOfNodeInMaximumHeap,parent);
    		indexOfNodeInMaximumHeap = parent;
    		parent = Parent(indexOfNodeInMaximumHeap);
    	}
    }
    
    public void HeapifyDown(int indexOfNodeInMaximumHeap) {
    	int left = LeftSon(indexOfNodeInMaximumHeap);
    	int right = RightSon(indexOfNodeInMaximumHeap);
    	int bigger = indexOfNodeInMaximumHeap;
    	if(left < this.numNodes && this.hashTable[this.MaximumHeap[left]].getNeighborhootWeight()> this.hashTable[this.MaximumHeap[bigger]].getNeighborhootWeight() ) {
    		bigger = left;
    	}
    	if (right < this.numNodes && this.hashTable[this.MaximumHeap[right]]> this.hashTable[this.MaximumHeap[bigger]]) {
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
    public class Node{
        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
    	private int id;
    	private int weight;
    	private int neighborhoodWeight;
    	private DoublyLinkedList neighbors;
    	
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
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
        
        public int getNeighborhootWeight() {return this.neighborhoodWeight; }
        
        public void setNeighborhoodWeight(int change, String sign) {
        	if(sign == "+")
        		this.neighborhoodWeight+= change;
        	else
        		this.neighborhoodWeight -= change;
        }
        
        public DoublyLinkedList getneighbors() { return this.neighbors; }
        
    }
    
    public class DoublyLinkedList {    
        //A node class for doubly linked list
    	
    	//Initially, heade and tail is set to null
        private DoublyLinkedListNode head, tail;  
        public DoublyLinkedList() { head, tail = null} // constractor of DoublyLinkedList;
    	
    	public class DoublyLinkedListNode{  
            
        	private Node node;  
            private DoublyLinkedListNode previous;  
            private DoublyLinkedListNode next;  
            private DoublyLinkedListNode Egde;
           
            public DoublyLinkedListNode(Node node) { this.node = node; } // constractor of DoublyLinkedListNode
            public void setEdge(DoublyLinkedListNode node) {this.Egde = node; }
            public int getEdge() {this.Egde.node.getId(); }   
        }  
        
        
       
        //add a node to the end of the list  
        public DoublyLinkedListNode addNode(Node node) {  
            //Create a new node  
        	DoublyLinkedListNode newNode = new DoublyLinkedListNode(node);  
       
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
        public boolean deleteNode(Node node) {
        	// check if node in list - binarySerch
        	

        	
        	return true;
        }
    }
    
    public class void main(String[] args) {

    	Node [] nodes = new Node[5];
    	for (int i =0; i< nodes.length; i ++) {
    		nodes[i] = new Node(i, 10*i);
    	}
    	
    	Graph graph = new Graph(nodes);
    	System.out.println(graph);
    }
}


