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

	private AVlRoot root;
	private int size;
	private AVLNode minNode;
	private AVLNode maxNode;
	
	

    /**
     * This constructor creates an empty AVLTree.
     * O(1)
     */  
    public AVLTree(){
    	this.root = new AVlRoot();
    	this.size =0;
    	this.minNode = this.getRoot();
    	this.maxNode= this.getRoot();
        
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     * O(1)
     */
    public boolean empty() {
        return (this.getRoot().info == null) ? true : false;
    }

    /**
     * public boolean search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     * O(logn)
     */
    public Boolean search(int k) {
    	AVLNode x = this.getRoot();
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
        /** 
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
    		parent.height_change_insert = true;
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
    		System.out.println("left height = " + parent.getLeft().getHeight() + " right hight = " + parent.getRight().getHeight());
    		if (Math.abs(balance) < 2 && !parent.height_change_insert) {
    			break;
    		}
    		else if(Math.abs(balance) < 2 && parent.height_change_insert)
    			parent = parent.getParent();
    		else // Math.abs(balance) == 2 
    			counter = rotate(balance,new_node.getKey(),parent,0);
    	}
    	this.size ++;
       return counter;
       */

        Boolean add_left = null;
       //find the correct place to add node
        AVLNode x = this.getRoot();
        while(x.info != null && add_left == null) {
          if(k == x.getKey())
              return -1;
          else if (k<x.getKey()) {
              if (x.getLeft().isRealNode())
                x = x.getLeft();
              else
                  add_left = true;

            } else {
            if (x.getRight().isRealNode())
                x = x.getRight();
            else
                add_left = false;
          }
        } 

        //add the new node
    	int balance, counter =0;
        AVLNode external_leaf = new AVLNode(null);
    	AVLNode new_node = new AVLNode(i);

    	new_node.setKey(k);
    	new_node.setHeight(0);
    	new_node.setLeft(external_leaf);
    	new_node.setRight(external_leaf);
    	new_node.setParent(x);

        if (this.empty()) {
            this.root.setSon(new_node);
            return 0;
        } else if (add_left) {
    	    x.left_son=new_node;
        } else {
            x.right_son=new_node;
        }

        //update max/min
        if (k < this.minNode.key) {
            this.minNode = new_node;
        } 
        if (k>this.maxNode.key) {
            this.maxNode = new_node;
        }

        //balance tree
        while (x.isRealNode()) {
           balance = x.getbalanced();
           if (balance>1 || balance<-1) {
               return rotate(balance, x.key, x);
           }
           x.height+=1;
           x = x.parent;
        }

        return 0;
    }
    
    
    private int rotate(int balance, int key, AVLNode node) {
    	System.out.println();
    	System.out.println("------------------rotate------------------");
    	System.out.println("balance = " + balance + " key that inserted = "+ key);
    	System.out.println();
    	if (balance > 1 && key < node.getLeft().getKey()) {
            node.rightRotate();
            return 1;
        }
 
        // Right Right Case
        else if (balance < -1 && key > node.getRight().getKey()) {
        	node.leftRotate();
            return 1;
        }
 
        // Left Right Case
        else if (balance > 1 && key > node.getLeft().getKey()) {
        	System.out.println("left then rigth ");
            node.left_son.leftRotate();
            node.rightRotate();
            return 2;
        }
 
        // Right Left Case
        else if (balance < -1 && key < node.getRight().getKey()) {
        	System.out.println("rigth then left");
            node.right_son.rightRotate();
            node.leftRotate();
            return 2;
        }
        return 0;
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
     * O(1)
     */
    public Boolean min() {
    	if(empty())
    		return null;
        return this.minNode.getValue(); 
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     * O(1)
     */
    public Boolean max() {
    	if(empty())
    		return null;
        return this.maxNode.getValue();
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     * O(n)
     */
    public int[] keysToArray() {
    	if(empty())
    		return new int [0];
    	int[] arr = new int[this.size]; 
    	int [] keys = in_order_keys_array(arr,this.getRoot(),0);
        return keys;             
    }
    
    private int [] in_order_keys_array (int [] arr, AVLNode node, int index) {
    	if (node.getValue() != null)
    		in_order_keys_array(arr,node.getLeft(), index);
    	arr[index++] = node.getKey();
    	if(node.getRight().getValue() != null)
    		in_order_keys_array(arr, node.getRight(),index);
    	return arr;
    	
    }

    /**
     * public boolean[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     * O(n)
     */
    public boolean[] infoToArray() {
    	if(empty())
    		return new boolean [0];
    	boolean [] arr = new boolean[this.size]; 
    	boolean [] info = in_order_value_array(arr,this.getRoot(),0);
        return info;
    }
    private boolean [] in_order_value_array (boolean [] arr, AVLNode node, int index) {
    	if (node.getValue() != null)
    		in_order_value_array(arr,node.getLeft(), index);
    	arr[index++] = node.getValue();
    	if(node.getRight().getValue() != null)
    		in_order_value_array(arr, node.getRight(),index);
    	return arr;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * O(1)
     */
    public int size() {
        return this.size;
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     * O(1)
     */
    public AVLNode getRoot() {
        return this.root.getSon();
    }

    
    /**
     * @pre key in tree
     * O(logn)
     */
    private AVLNode FindNodeByKey(int key) { //O(logn)
    	AVLNode node = this.getRoot();
    	while(node.info != null) {
    		if(key == node.getKey()) 
    			return node;
    		else if (key < node.getKey())
    			node = node.getLeft();
    		else
    			node = node.getRight();
    	}
    	return null; // never get here 
    }
    
    /**
     * public boolean prefixXor(int k)
     *
     * Given an argument k which is a key in the tree, calculate the xor of the values of nodes whose keys are
     * smaller or equal to k.
     *
     * precondition: this.search(k) != null
     *O(logn)
     */
    public boolean prefixXor(int k){
    	AVLNode knode = FindNodeByKey(k); // O(logn)
    	return knode.sub_tree_xor;// O(1)
    }

    /**
     * public AVLNode successor
     *
     * given a node 'node' in the tree, return the successor of 'node' in the tree (or null if successor doesn't exist)
     *
     * @param node - the node whose successor should be returned
     * @return the successor of 'node' if exists, null otherwise
     * O(logn + 1)
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
        
    
//    private int binary_search(int[] arr, int k) {
//    	int left =0, right = arr.length-1 , mid = 0;
//    	while( left <= right) {
//    		mid = (left +(right -1)) / 2;
//    		if (arr[mid] == k)
//    			return mid;
//    		else if( arr[mid] < k)
//    			left = mid +1;
//    		else 
//    			right = mid -1;
//    	}
//    	return -1; // k not in arr
//    }

    /**
     * public boolean succPrefixXor(int k)
     *
     * This function is identical to prefixXor(int k) in terms of input/output. However, the implementation of
     * succPrefixXor should be the following: starting from the minimum-key node, iteratively call successor until
     * you reach the node of key k. Return the xor of all visited nodes.
     *
     * precondition: this.search(k) != null
     * O(n)
     */
    public boolean succPrefixXor(int k){
    	AVLNode node = this.minNode;
    	int tcounter =0;
    	while(node.key <=k) {
    		if(node.getValue())
    			tcounter++;
    		node = successor(node);
    	}
        return (tcounter % 2 == 1) ? true: false;
    }

    
	public static void main(String[] args) {
		
		AVLTree tree = new AVLTree();
        if(!tree.empty())
        	System.out.println("erorr empty()");
        System.out.println("initial root = " + tree.getRoot().getValue());
        
        
		int[] keys = {5, 10, 8, 3, 7};
        boolean[] vals = {true, true, false, false, true};
        
       int x;
        for (int i = 0; i < keys.length; i++) {
           x =  tree.insert(keys[i], vals[i]);
           System.out.println("number of rotation = " + x);
//           printnode(tree.root);
           System.out.println("the tree =");
           inorder(tree.getRoot());
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
            root.height_change_insert = false;
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
    	private int height;
    	private boolean sub_tree_xor; // keeps the xor between all key is node's sub tree
    	public boolean height_change_insert = false;

        public AVLNode() {
            this.info=null;
        }

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
            node.setParent(this);
        }

        //returns left child
		//if called for virtual node, return value is ignored.
        public AVLNode getLeft() {
            return this.left_son;

        }

        //sets right child
        public void setRight(AVLNode node) {
            this.right_son = node;
            node.setParent(this);
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
            return this.parent;
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() {
            return (this.info != null) ? true : false; 
        }

        // sets the height of the node
        public void setHeight(int height) {
            this.height = height;
        }

        // Returns the height of the node (-1 for virtual nodes)
        public int getHeight() {
            if(info == null)
            	return -1;
            return this.height;
        }
        
        
        public int getbalanced() {
        	if(this.info == null)
        		return 0;
        	return this.getLeft().getHeight() - this.getRight().getHeight();
        }
        
        private AVLNode rightRotate() {
            AVLNode replacementNode = this.left_son;
            AVLNode nodeParent = this.parent;
            
        	// Perform rotation
            if (nodeParent.getLeft()==this) {
                nodeParent.setLeft(replacementNode);
            } else {
                nodeParent.setRight(replacementNode);
            }
            this.setLeft(replacementNode.getRight());
            replacementNode.setRight(this);

            // Update heights
            this.setHeight(Math.max(nodeParent.getLeft().getHeight(), nodeParent.getRight().getHeight()));
            replacementNode.setHeight(Math.max(replacementNode.getLeft().getHeight(), replacementNode.getRight().getHeight()));
            nodeParent.setHeight(Math.max(nodeParent.getRight().getHeight(), nodeParent.getLeft().getHeight()));
            return replacementNode;
        }

        private AVLNode leftRotate() {
            AVLNode replacementNode = this.right_son;
            AVLNode nodeParent = this.parent;
            
        	// Perform rotation
            if (nodeParent.getLeft()==this) {
                nodeParent.setLeft(replacementNode);
            } else {
                nodeParent.setRight(replacementNode);
            }

            this.setRight(replacementNode.getLeft());
            replacementNode.setLeft(this);

            // Update heights
            this.setHeight(Math.max(this.getRight().getHeight(), this.getLeft().getHeight()));
            replacementNode.setHeight(Math.max(replacementNode.getRight().getHeight(), replacementNode.getLeft().getHeight()));
            nodeParent.setHeight(Math.max(nodeParent.getRight().getHeight(), nodeParent.getLeft().getHeight()));
            return replacementNode;
        }
        
    }

    public class AVlRoot extends AVLNode{
        final Boolean info = null;
        private AVLNode son = new AVLNode(null);
        private AVLNode parent;

        public AVlRoot() { }

        public AVLNode getSon() {
            return this.son;
        }

        public AVLNode getRight() {
            return this.getSon();
        }

        public AVLNode getLeft() {
            return this.getSon();
        }

        public void setSon(AVLNode son) {
            this.son = son;
            son.setParent(this);
        }

        public void setLeft(AVLNode son) {
           this.setSon(son);
        }

        public void setRight(AVLNode son) {
            this.setSon(son);
        }

        public int getHeight () {
            return -1;
        }
    }

}


