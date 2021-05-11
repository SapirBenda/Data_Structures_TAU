package il.ac.tau.cs.data_structures.practical1;


import java.util.Arrays;

/**
 * public class AVLNode
 * <p>
 * This class represents an AVLTree with integer keys and boolean values.
 * <p>
 * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
 * arguments. Changing these would break the automatic tester, and would result in worse grade.
 * <p>
 * However, you are allowed (and required) to implement the given functions, and can add functions of your own
 * according to your needs.
 */

public class AVLTree {

	private AVLNode root;
	private int size;
	
	

    /**
     * This constructor creates an empty AVLTree.
     */  
    public AVLTree(){
    	this.root= new AVLNode(null);
    	this.size =0;
        
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return (this.root.info == null) ? true : false; 
    }

    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public Boolean search(int k) {
    	AVLNode x = this.root;
    	while(x.info != null) {
    		if(k == x.getKey()) 
    			return x.getValue();
    		else if (k<x.getKey())
    			x = x.getLeft();
    		else
    			x = x.getRight();
    	}
    	return null;
    }

    /**
     * public int insert(int k, boolean i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
	 * returns the number of nodes which require rebalancing operations (i.e. promotions or rotations).
	 * This always includes the newly-created node.
     * returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, boolean i) {
    	AVLNode External_leaf = new AVLNode(null);
    	int balance, counter =0;
    	AVLNode new_node = new AVLNode(i);
    	new_node.setKey(k);
    	new_node.setHeight(0);
    	new_node.setLeft(External_leaf);
    	new_node.setRight(External_leaf);
    	AVLNode parent = new AVLNode(null);
    	AVLNode curr = this.root;
    	
    	clear_check_height(this.root);
    	
    	while( curr.getValue() != null) {
    		parent = curr;
    		if(k < curr.getKey())
    			curr = curr.getLeft();
    		else
    			curr = curr.getRight();
    		parent.setHeight(parent.getHeight()+1);
    		parent.hieght_change_insert = true;
    	}
    	
    	new_node.setParent(parent);
  
    	if (parent.getValue() == null) { // the tree was empty
    		this.root =new_node;
			this.root.setHeight(1);
			this.root.setLeft(External_leaf);
			this.root.setRight(External_leaf);
			this.root.setParent(parent);
			return 0;
			
    	}
    	else if(k < parent.getKey())
    		parent.setLeft(new_node);
    	else {
    		parent.setRight(new_node);
    	}
    	
    	
    	while(parent.getValue() != null) {
    		System.out.println("tree in check = ");
    		inorder(this.root);
    		balance = parent.getbalanced();
    		System.out.println(parent.getKey() + " --->   balance = " + balance);
    		System.out.println("left hieght = " + parent.getLeft().getHeight() + " right hight = " + parent.getRight().getHeight());
    		if (Math.abs(balance) < 2 && !parent.hieght_change_insert) {
    			break;
    		}
    		else if(Math.abs(balance) < 2 && parent.hieght_change_insert)
    			parent = parent.getParent();
    		else // Math.abs(balance) == 2 
    			counter = rotate(balance,new_node.getKey(),parent,0);
    	}
    	this.size ++;
       return counter;
    }
    
    
    private int rotate(int balance, int key, AVLNode node, int counter) {
    	System.out.println();
    	System.out.println("------------------rotate------------------");
    	System.out.println("balance = " + balance + " key that inserted = "+ key);
    	System.out.println();
    	if (balance > 1 && key < node.getLeft().getKey()) {
        	counter++;
            AVLNode ll = node.rightRotate(node);
        }
 
        // Right Right Case
        if (balance < -1 && key > node.getRight().getKey()) {
        	counter++;
        	AVLNode rr = node.leftRotate(node);
        }
 
        // Left Right Case
        if (balance > 1 && key > node.getLeft().getKey()) {
        	System.out.println("left then rigth ");
        	counter+=2;
            AVLNode lr= node.rightRotate(node);
            AVLNode x = lr.leftRotate(lr);
        }
 
        // Right Left Case
        if (balance < -1 && key < node.getRight().getKey()) {
        	System.out.println("rigth then left");
        	counter+=2;
            node.setRight(node.getRight().rightRotate(node.getRight()));
            AVLNode rl = node.leftRotate(node);
        }
        return counter;
    }
    
    

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of nodes which required rebalancing operations (i.e. demotions or rotations).
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
        return 42;    // to be replaced by student code
    }

    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
    public Boolean min() {
    	if(this.root.getValue() == null)
    		return null;
    	AVLNode x =  this.root;
    	while(x.getLeft().info != null)
    		x = x.getLeft();
        return x.getValue(); 
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public Boolean max() {
    	if(this.root.getValue() == null)
    		return null;
    	AVLNode x =  this.root;
    	while(x.getRight().info != null)
    		x = x.getRight();
        return x.getValue();
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
    	if(this.root.getValue() == null)
    		return new int [0];
    	int[] arr = new int[this.size]; 
    	int [] keys = in_order(arr,this.root,0);
    	System.out.println("keys = " + Arrays.toString(keys));
        return keys;              // to be replaced by student code
    }
    
    private int [] in_order (int [] arr, AVLNode node, int index) {
    	if (node.getValue() != null)
    		in_order(arr,node.getLeft(), index);
    	arr[index++] = node.getKey();
    	if(node.getRight().getValue() != null)
    		in_order(arr, node.getRight(),index);
    	return arr;
    	
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public boolean[] infoToArray() {
        boolean[] arr = new boolean[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     */
    public int size() {
        return this.size;
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     */
    public AVLNode getRoot() {
        return this.root;
    }

    /**
     * public boolean prefixXor(int k)
     *
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     *
     * precondition: this.search(k) != null
     *
     */
    public boolean prefixXor(int k){
        return false;
    }

    /**
     * public AVLNode successor
     *
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     */
    public AVLNode successor(AVLNode node){
    	AVLNode curr = node;
        if (node.getRight().info != null) {
        	curr = curr.getRight();
        	while(curr.getLeft().info != null)
        		curr = curr.getLeft();
            return curr; 
        	}
        AVLNode parent = curr.getParent();
        while(parent.getValue() != null && curr == parent.getRight()) {
        	curr = parent;
        	parent = curr.getParent();
        }
        return parent;
        }
        
    
    
    
    private int binary_search(int[] arr, int k) {
    	int left =0, right = arr.length-1 , mid = 0;
    	while( left <= right) {
    		mid = (left +(right -1)) / 2;
    		if (arr[mid] == k)
    			return mid;
    		else if( arr[mid] < k)
    			left = mid +1;
    		else 
    			right = mid -1;
    	}
    	return -1; // k not in arr
    }

    /**
     * public boolean succPrefixXor(int k)
     *
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     *
     * precondition: this.search(k) != null
     */
    public boolean succPrefixXor(int k){
        return false;
    }

    
	public static void main(String[] args) {
		
		AVLTree tree = new AVLTree();
        if(!tree.empty())
        	System.out.println("erorr empty()");
//        System.out.println("initial root = " + tree.root.getValue());
        
        
		int[] keys = {5, 10, 8, 3, 7};
        boolean[] vals = {true, true, false, false, true};
        
       int x;
        for (int i = 0; i < keys.length; i++) {
           x =  tree.insert(keys[i], vals[i]);
           System.out.println("number of rotation = " + x);
//           printnode(tree.root);
//           System.out.println("the tree =");
//           inorder(tree.root);
           System.out.println();
        }
        
//        inorder(tree.root);
        
        
        
	}

    public static void inorder(AVLNode r) {
        if (r.getValue() != null)        {
            inorder(r.getLeft());
            System.out.println(r.getKey() +" ");
            inorder(r.getRight());
        }
    }
    public static void printnode(AVLNode r) {
        System.out.println("info = " + r.getValue() + " key = " + r.getKey());
        }
    
    public static void clear_check_height(AVLNode root) {
        if (root.getValue() != null)        {
        	clear_check_height(root.getLeft());
            root.hieght_change_insert = false;
            clear_check_height(root.getRight());
        }
    }
	

    /**
     * public class AVLNode
     * <p>
     * This class represents a node in the AVL tree.
     * <p>
     * IMPORTANT: do not change the signatures of any function (i.e. access modifiers, return type, function name and
     * arguments. Changing these would break the automatic tester, and would result in worse grade.
     * <p>
     * However, you are allowed (and required) to implement the given functions, and can add functions of your own
     * according to your needs.
     */
    public class AVLNode {
    	
    	private Boolean info;
    	private int key;
    	private AVLNode left_son;
    	private AVLNode right_son;
    	private AVLNode parent;
    	private int hieght;
    	public boolean hieght_change_insert = false;
    	
    	public AVLNode(Boolean info) {
    		this.info = info;
    	}

        //returns node's key (for virtual node return -1)
        public int getKey() {
        	if(info == null)
        		return -1;
            return this.key; 
        }

        //returns node's value [info] (for virtual node return null)
        public Boolean getValue() {
            return this.info; 
        }
        public void setValue(Boolean val) {
            this.info = val; 
        }
        public void setKey(int key) {
            this.key = key; 
        }

        //sets left child
        public void setLeft(AVLNode node) {
            this.left_son = node;
        }

        //returns left child
		//if called for virtual node, return value is ignored.
        public AVLNode getLeft() {
            return this.left_son; 
        }

        //sets right child
        public void setRight(AVLNode node) {
            this.right_son = node;
        }

        //returns right child 
		//if called for virtual node, return value is ignored.
        public AVLNode getRight() {
            return this.right_son;
        }

        //sets parent
        public void setParent(AVLNode node) {
        	this.parent = node;
        }

        //returns the parent (if there is no parent return null)
        public AVLNode getParent() {
            return this.parent; // to be replaced by student code
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() {
            return (this.info != null) ? true : false; 
        }

        // sets the height of the node
        public void setHeight(int height) {
            this.hieght = height;
        }

        // Returns the height of the node (-1 for virtual nodes)
        public int getHeight() {
            if(info == null)
            	return -1;
            return this.hieght;
        }
        
        
        public int getbalanced() {
        	if(this.info == null)
        		return 0;
        	return this.getLeft().getHeight() - this.getRight().getHeight();
        }
        
        private AVLNode rightRotate(AVLNode parent) {
        	System.out.println();
        	System.out.println("right rotation");
        	AVLNode insered = parent.getLeft();
        	AVLNode inserted_right_son = insered.getRight();
//        	System.out.println("x = : ");
//        	printnode(insered);
//        	System.out.println("y = : ");
//        	printnode(parent);
//        	System.out.println("T2 = : ");
//        	printnode(inserted_right_son);
            
        	// Perform rotation
            insered.setRight(parent) ;
            parent.setLeft(inserted_right_son);

            // Update heights
            parent.setHeight(Math.max(parent.getLeft().getHeight(), parent.getRight().getHeight()) +1 );
            insered.setHeight(Math.max(insered.getLeft().getHeight(), insered.getRight().getHeight()) +1 );
            return insered;
        }
        private AVLNode leftRotate(AVLNode future_left_son) {
        	System.out.println();
        	System.out.println("~~~~~left rotation~~~~~");
        	AVLNode inserded = future_left_son.getRight();
        	AVLNode T2 = future_left_son.getParent();

        	System.out.println("future_left_son = : ");
        	printnode(future_left_son);
        	System.out.println("inserded = : ");
        	printnode(inserded);
        	System.out.println("T2 = : ");
        	printnode(T2);
        	
            // Perform rotation
            inserded.setLeft(future_left_son);
            future_left_son.setParent(inserded);
            future_left_son.setRight(new AVLNode(null));
            inserded.setParent(T2);

            //  Update heights
            future_left_son.setHeight(Math.max(future_left_son.getLeft().getHeight(), future_left_son.getRight().getHeight())+1); 
            inserded.setHeight(Math.max(inserded.getLeft().getHeight(), inserded.getRight().getHeight()) +1);

            return inserded;
        }
        
    }

}


