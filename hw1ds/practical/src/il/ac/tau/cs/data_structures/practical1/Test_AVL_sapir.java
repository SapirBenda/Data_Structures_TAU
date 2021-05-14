package il.ac.tau.cs.data_structures.practical1;

import java.util.Arrays;

import il.ac.tau.cs.data_structures.practical1.AVLTree.AVLNode;

public class Test_AVL_sapir extends AVLTree {
	
	public static void main(String[] args) {
		
		AVLTree tree = new AVLTree();
        if(!tree.empty())
        	System.out.println("erorr empty()");
        
        int[] keys = {5, 10, 8, 13, 7};
        boolean[] vals = {true, true, false, false, true};
        
       int x;
        for (int i = 0; i < keys.length; i++) {
           x =  tree.insert(keys[i], vals[i]);  
        }
        
        check_massege("empty()");
        Boolean e = tree.empty();
        AVLTree tree2 = new AVLTree();
        Boolean e2 = tree2.empty();
        if(e) System.out.println("error empty- not empty");
        else if(!e2) System.out.println("error empty- empty");
        else printdone("empty()");
        
        check_massege("search(int key)");
        Boolean s1 = tree.search(5);
        Boolean s2 = tree.search(10);
        Boolean s3 = tree.search(8);
        Boolean s4 = tree.search(13);
        Boolean s5 = tree.search(0);
        Boolean s6 = tree.search(20);
        if(!s1) System.out.println("error search 5");
        else if(!s2) System.out.println("error search 10");
        else if(s3) System.out.println("error search 8");
        else if(s4) System.out.println("error search 13");
        else if(s5 != null) System.out.println("error search 0");
        else if(s6 != null) System.out.println("error search 20");
        else printdone("search(int key)");
        
        check_massege("inset(int key, boolean value)");
        int x1 = tree.insert(4, false);///
        int x2 = tree.insert(25, true);////
        int x3 = tree.insert(5, true);
        int x4 = tree.insert(4, false);
        int x5 = tree.insert(9, false);///
        int x6 = tree.insert(15, false);///
        int x7 = tree.insert(18, false);///
        int x8 = tree.insert(40, false);//
        int x9 = tree.insert(3, true);//
        int x10 = tree.insert(0, false);//
        if(x1!=0) System.out.println("error insert - 4");
        else if(x2!=1) System.out.println("error insert - 25");
        else if(x3!=-1) System.out.println("error insert - 5 in tree + differ value");
        else if(x4!=-1) System.out.println("error insert - 4 in tree");
        else if(tree.getRoot().getKey() != 13) System.out.println("error insert- 40");
        else if(tree.FindNodeByKey(0).getParent().getKey()!= 3) System.out.println("error insert- 0");
        else if(tree.FindNodeByKey(4).getParent().getKey()!= 3) System.out.println("error insert- 4");
        else if(tree.FindNodeByKey(8).getParent().getKey()!= 13) System.out.println("error insert- 8");
        else if(tree.FindNodeByKey(18).getParent().getKey()!= 13) System.out.println("error insert- 18");
        else printdone("inset(int key, boolean value)");
	
//        System.out.println("tree = ");
//        inorder(tree.getRoot());
//        System.out.println();
        
//        check_massege("min");
//        Boolean m = tree.min();
//        if(m) System.out.println("error min - 0");
//        int w = tree.delete(0);
//        Boolean m1 = tree.min();
//        if(!m1) System.out.println("error min - 3");
//        Boolean m2 = tree2.min();
//        if(m2 != null) System.out.println("error min - null");
//        printdone("min()");
//        
//        check_massege("max");
//        Boolean l = tree.max();
//        if(l) System.out.println("error max - 40");
//        int w2 = tree.delete(40);
//        Boolean l1 = tree.max();
//        if(!l1) System.out.println("error max - 25");
//        Boolean l2 = tree2.max();
//        if(l2 != null) System.out.println("error max - null");
//        printdone("max()");
        

        check_massege("delete(int key)");
        int y1 = tree.delete(6);
        int y2 = tree.delete(8);
        int y3 = tree2.delete(8);
        int y4 = tree.delete(13);
        int y5 = tree.delete(50);
        int y6 = tree.delete(9);
        if(y1 != -1) System.out.println("error delete - 6 not in tree");
        else if(y2 != 2) System.out.println("error delete - 8");
        else if(y3 != -1) System.out.println("error delete - tree2 empty");
        else if(y4 != 2) System.out.println("error delete - 13");
        else if(tree.getRoot().getKey() != 10) System.out.println("error delete - root");
        else if(tree.getRoot().getLeft().getKey() != 5) System.out.println("error delete - left son");
        else if(tree.getRoot().getRight().getKey() != 18) System.out.println("error delete - right son");
        else if(y5 != -1) System.out.println("error delete - 50");
        else if(y6 != 0) System.out.println("error delete - 9");
        else printdone("delete(int key))");
        
        
     
        
        
        
        
        
        
        
        
        
	}
	
	
	
	
	
	
	
	public static void inorder(AVLNode r) {
        if (r.getValue() != null)        {
            inorder(r.getLeft());
            System.out.println(r.getKey() +" left son = " + r.getLeft().getKey() + " rigth son = " + r.getRight().getKey());
            inorder(r.getRight());
        }
    }
    public static void printnode(AVLNode r) {
        System.out.println("info = " + r.getValue() + ", key = " + r.getKey());
        }
    public static void check_massege(String method) {
        System.out.println("~~~~~~~~~~~~~ check " + method + " ~~~~~~~~~~~~~");
        }
    public static void printdone(String method) {
        System.out.println("~~~~~~~~~~~~~ Done  " + method + " ~~~~~~~~~~~~~");
        System.out.println();
        }
}

