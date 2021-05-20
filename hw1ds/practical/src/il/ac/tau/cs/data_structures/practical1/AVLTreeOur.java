package il.ac.tau.cs.data_structures.practical1;


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

public class AVLTreeOur {

	private final AVlRoot root;
	private int size;
	private AVLNode minNode;
	private AVLNode maxNode;
		

    /**
     * This constructor creates an empty AVLTree.
     * O(1)
     */  
    public AVLTreeOur(){
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
        return this.getRoot().getValue() == null;
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
    	while(x.getValue() != null) {
    		if(k == x.getKey()) 
    			return x.getValue();
    		else if (k<x.getKey())
    			x = x.getLeft();
    		else
    			x = x.getRight();
    	}

    	return null; // k is not a key in the tree
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
        
        Boolean addLeft = null;
       //find the correct place to add node
        AVLNode x = this.getRoot();
        while(x.getValue() != null && addLeft == null) {
          if(k == x.getKey())
              return -1;
          else if (k < x.getKey()) {
              if (x.getLeft().isRealNode())
                x = x.getLeft();
              else
                  addLeft = true;

            } else {
            if (x.getRight().isRealNode())
                x = x.getRight();
            else
                addLeft = false;
          }
        } 
        //update size
        this.size+=1;

        //add the new node
    	int balance, counter =0;
        AVLNode externalLeaf = new AVLNode();
    	AVLNode newNode = new AVLNode(i);

    	newNode.setKey(k);
    	newNode.setHeight(0);
    	newNode.setLeft(externalLeaf);
    	newNode.setRight(externalLeaf);
    	newNode.setParent(x);

        if (this.empty()) {
            this.root.setSon(newNode);
            this.minNode = newNode;
            return 0;
        } else if (addLeft) {
    	    x.setLeft(newNode);
        } else {
            x.setRight(newNode);
        }

        //update max/min
        if (k < this.minNode.getKey()) {
//        	System.out.println("node.key in min = " + newNode.getKey());
            this.minNode = newNode;
//            System.out.println("this.min = " + this.minNode.getKey());
        } 
        if (k>this.maxNode.getKey()) {
//        	System.out.println("node.key in max = " + newNode.getKey());
            this.maxNode = newNode;
//            System.out.println("this.max = " + this.maxNode.getKey());
        }

        //balance tree
        while (x.isRealNode()) {
           balance = x.getBalance();
           if (balance>1 || balance<-1) {
               counter += rotate(balance, x);
           }
           if (x.updateHeight()) {
               counter+=1;
               x = x.parent;
           } else {
               return counter;
           }
        }

        return counter;
    }
    
    
    private int rotate(int balance, AVLNode node) {
        // Left Right Case
        if (balance > 1 && node.getLeft().getBalance()<0) {
//        	System.out.println("left then right ");
            node.getLeft().leftRotate();
            node.rightRotate();
            return 1;
        }
 
        // Right Left Case
        else if (balance < -1 && node.getRight().getBalance()>0) {
//        	System.out.println("right then left");
            node.getRight().rightRotate();
            node.leftRotate();
            return 1;
        }

        //Left Left Case
        else if (balance > 1) {
            node.rightRotate();
            return 1;
        }

        // Right Right Case
        else if (balance < -1) {
            node.leftRotate();
            return 1;
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
        AVLNode x = this.getRoot();
        while(x.getValue() != null && x.getKey()!=k) {
            if (k<x.getKey())
                x = x.getLeft();
            else
                x = x.getRight();
        }
        if (x.getValue() == null)
            return -1;

        //update size
        this.size -= 1;

        // update min/max
        if(x.getKey() == this.minNode.getKey()) {
            this.minNode = x.getRight();
            if (!minNode.isRealNode())
                this.minNode = x.getParent();
        }
        if(x.getKey() == this.maxNode.getKey()){
            this.maxNode = x.getLeft();
            if (!maxNode.isRealNode())
                this.maxNode = x.getParent();
        }


        AVLNode balanceNode;
        int balance = 0;
        int counter = 0;

        if (x.getRight().isRealNode() || x.getRight().isRealNode()) {
            //replace node if not leaf
            AVLNode replacementNode = this.successor(x);

            //old children
            balanceNode = replacementNode.getParent();
            if (balanceNode == x) {
                balanceNode = replacementNode;

                //new children
                replacementNode.setLeft(x.getLeft());

                //update parent
                replacementNode.setParent(x.getParent());
                if (x.getParent().getLeft() == x)
                    x.getParent().setLeft(replacementNode);
                else
                    x.getParent().setRight(replacementNode);
            } else {
                //old children
                balanceNode.setLeft(replacementNode.getRight());
                //balanceNode.updateHeight();

                //new children
                replacementNode.setLeft(x.getLeft());
                replacementNode.setRight(x.getRight());

            }
            //update parent
            replacementNode.setParent(x.getParent());
            if (x.getParent().getLeft()==x)
                x.getParent().setLeft(replacementNode);
            else
                x.getParent().setRight(replacementNode);

            //update heights
            replacementNode.updateHeight();
            replacementNode.getParent().updateHeight();

        } else {
            //delete node
            balanceNode = x.getParent();
            if (balanceNode.getLeft()==x)
                balanceNode.setLeft(new AVLNode());
            else
                balanceNode.setRight(new AVLNode());
            //balanceNode.updateHeight();
        }

        // balance tree
        while (balanceNode.isRealNode()) {
            balance = balanceNode.getBalance();
            if (balance>1 || balance<-1) {
                counter += rotate(balance, balanceNode);
            }
            if (balanceNode.updateHeight()) {
                counter += 1;
            }
            balanceNode = balanceNode.getParent();
        }

        return counter;
    }

    /**
     * public Boolean min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     * O(1)
     */
    public Boolean min() {
        return(empty()) ? null : this.minNode.getValue();
    }

    public AVLNode getMinNode() {
        return this.minNode;
    }

    /**
     * public Boolean max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     * O(1)
     */
    public Boolean max() {
        return(empty()) ? null: this.maxNode.getValue();
    }

    public AVLNode getMaxNode(){
        return this.maxNode;
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
    	int [] index = {0};
    	return inOrderKeysArray(arr,this.getRoot(),index);

    }
    
    private int [] inOrderKeysArray(int [] arr, AVLNode node, int [] index) {
    	if (node.getLeft().getValue() != null)
    		inOrderKeysArray(arr,node.getLeft(), index);
    	arr[index[0]] = node.getKey();
    	index[0] = index[0] +1;
    	if(node.getRight().getValue() != null)
    		inOrderKeysArray(arr, node.getRight(),index);
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
    	int [] index = {0};
    	return inOrderValueArray(arr,this.getRoot(),index);
    }
    private boolean [] inOrderValueArray(boolean [] arr, AVLNode node, int [] index) {
    	if (node.getLeft().getValue() != null)
    		inOrderValueArray(arr,node.getLeft(), index);
    	arr[index[0]] = node.getValue();
    	index[0] = index[0] +1;
    	if(node.getRight().getValue() != null)
    		inOrderValueArray(arr, node.getRight(),index);
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
    public AVLNode FindNodeByKey(int key) { //O(logn)
    	AVLNode node = this.getRoot();
    	while(node.isRealNode()) {
    		if(key == node.getKey()) 
    			return node;
    		else if (key < node.getKey())
    			node = node.getLeft();
    		else
    			node = node.getRight();
    	}
    	return null; // never gets here 
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
    	boolean xor =  false;

        while (knode.isRealNode()) { // O(logn)
            if (knode.getKey()<=k){
                xor =  knode.info  ^ xor;
                if (knode.getLeft().isRealNode())
                    xor = xor ^ knode.getLeft().subTreeXor; // O(1)
            }
            knode = knode.getParent();
        }

        return xor;
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
    	if(node.getKey() == this.maxNode.getKey() && node.getValue() == this.maxNode.getValue()) // if node == this.maxNode than it doesn't have successor
    		return null;
        if (node.getRight().getValue() != null) {
        	curr = curr.getRight();
        	while(curr.getLeft().getValue() != null)
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
    	int trueCounter =0;
    	while(node != null && node.getKey() <= k) {
    		if(node.getValue())
    			trueCounter++;
    		node = successor(node);
    	}
        return trueCounter % 2 == 1;
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
    	private AVLNode leftSon;
    	private AVLNode rightSon;
    	private AVLNode parent;
    	private int height;
    	private boolean subTreeXor; // keeps the xor between all key is node's sub tree

        public AVLNode() {
            this.subTreeXor =false;
            this.info=null;
        }

    	public AVLNode(Boolean info) {
            this.subTreeXor = info;
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
            this.leftSon = node;
            node.setParent(this);
        }

        //returns left child
		//if called for virtual node, return value is ignored.
        public AVLNode getLeft() {
            return this.leftSon;

        }

        //sets right child
        public void setRight(AVLNode node) {
            this.rightSon = node;
            node.setParent(this);
        }

        //returns right child 
		//if called for virtual node, return value is ignored.
        public AVLNode getRight() {
            return this.rightSon;
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
            return this.info != null;
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
        
        public boolean getSubTreeXor() {
            return this.subTreeXor;
        }

        public int getBalance() {
        	if(this.isRealNode())
                return this.getLeft().getHeight() - this.getRight().getHeight();
            return 0;
        }

        // updates the height and sub_tree_xor of the node
        // returns true if the height has changed
        // returns false otherwise
        public boolean updateHeight() {
            int height = this.getHeight();
            if (this.isRealNode()) {
                this.subTreeXor = this.getValue() ^ (this.getLeft().subTreeXor ^ this.getRight().subTreeXor);
                this.setHeight(Math.max(this.getLeft().getHeight(), this.getRight().getHeight()) + 1);
            }
            return height != this.getHeight();
        }
        
        private void rightRotate() {
            AVLNode replacementNode = this.leftSon;
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
            this.updateHeight();
            replacementNode.updateHeight();
            nodeParent.updateHeight();
        }

        private void leftRotate() {
            AVLNode replacementNode = this.rightSon;
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
            this.updateHeight();
            replacementNode.updateHeight();
            nodeParent.updateHeight();
        }

        @Override
        public String toString() {
            return "(" +
                    "key=" + key +
                    ", value=" + info +
                    ", height=" + height +
                    ", xor=" + subTreeXor +
                    ')';
        }
    }

    public class AVlRoot extends AVLNode{
        private AVLNode son = new AVLNode();

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





