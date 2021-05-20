package il.ac.tau.cs.data_structures.practical1;

import java.util.Arrays;

public class Test_AVL extends AVLTreeOur {

    public static void main(String[] args) {
        AVLTreeOur tree_our = new AVLTreeOur();
        AVLTreeOur tree2_our = new AVLTreeOur();
        if (!tree_our.empty())
            System.out.println("erorr empty()");

        int[] keys = {5, 10, 8, 13, 7};
        boolean[] vals = {true, true, false, false, true};

        int x;
        for (int i = 0; i < keys.length; i++) {
            x = tree_our.insert(keys[i], vals[i]);
        }

        AVLTreeOur tree3_our = new AVLTreeOur();
        int x20;
        for (int i = 0; i < keys.length; i++) {
            x20 = tree3_our.insert(keys[i], vals[i]);
        }

        AVLTreeOur tree4_our = new AVLTreeOur();
        int x21;
        for (int i = 0; i < keys.length; i++) {
            x21 = tree4_our.insert(keys[i], vals[i]);
        }
        x21 = tree4_our.insert(6, false);

        System.out.println("~~~~~~~~~~~~~~~~~~~~ begin manual check ~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
        check_empty(tree_our);
        check_search(tree_our);
        tree_our = check_insert(tree_our);
        tree_our = check_min(tree_our, tree2_our);
        tree_our = check_max(tree_our, tree2_our);
        tree_our = check_keysToArray(tree_our, tree2_our);
        check_infoToArray(tree_our, tree2_our, tree3_our);
        AVLTreeOur tree5_our = tree_our;
        check_getRoot(tree_our, tree2_our, tree3_our);
        check_successor(tree_our);
        check_prefixXor(tree_our);
        check_succPrefixXor(tree_our);
        AVLTreeOur tree6_our = check_size(tree_our, tree2_our,tree3_our, tree4_our); 
        tree5_our = check_delete(tree5_our, tree2_our);
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~ end manual check ~~~~~~~~~~~~~~~~~~~~");
    }
    
    public static void check_empty(AVLTreeOur tree_our) {
    	check_massege("empty()");
        Boolean e = tree_our.empty();
        AVLTreeOur tree2_our = new AVLTreeOur();
        Boolean e2 = tree2_our.empty();
        if (e) System.out.println("error empty- not empty");
        else if (!e2) System.out.println("error empty- empty");
        else printdone("empty()");
    	
    }
    
	public static void check_search(AVLTreeOur tree_our) {
		check_massege("search(int key)");
        Boolean s1 = tree_our.search(5);
        Boolean s2 = tree_our.search(10);
        Boolean s3 = tree_our.search(8);
        Boolean s4 = tree_our.search(13);
        Boolean s5 = tree_our.search(0);
        Boolean s6 = tree_our.search(20);
        if (!s1) System.out.println("error search 5");
        else if (!s2) System.out.println("error search 10");
        else if (s3) System.out.println("error search 8");
        else if (s4) System.out.println("error search 13");
        else if (s5 != null) System.out.println("error search 0");
        else if (s6 != null) System.out.println("error search 20");
        else printdone("search(int key)");
	    	
	    }
	
	public static AVLTreeOur check_insert(AVLTreeOur tree_our) {
		check_massege("inset(int key, boolean value)");
        int x1 = tree_our.insert(4, false);
        int x2 = tree_our.insert(25, true);
        int x3 = tree_our.insert(5, true);
        int x4 = tree_our.insert(4, false);
        int x5 = tree_our.insert(9, false);
        int x6 = tree_our.insert(15, false);
        int x7 = tree_our.insert(18, false);
        int x8 = tree_our.insert(40, false);
        int x9 = tree_our.insert(3, true);
        int x10 = tree_our.insert(0, false);

        if (x1 != 0) System.out.println("error insert - 4");
        else if (x2 != 2) System.out.println("error insert - 25");
        else if (x3 != -1) System.out.println("error insert - 5 in tree + differ value");
        else if (x4 != -1) System.out.println("error insert - 4 in tree");
        else if (x5 != 3) System.out.println("error insert - 9 ");
        else if (x6 != 1) System.out.println("error insert - 15 ");
        else if (x7 != 2) System.out.println("error insert - 18 ");
        else if (x8 != 4) System.out.println("error insert - 40 ");
        else if (x9 != 4) System.out.println("error insert - 3 ");
        else if (x10 != 2) System.out.println("error insert - 0");
        else if (tree_our.getRoot().getKey() != 13) System.out.println("error insert- 40");
        else if (tree_our.FindNodeByKey(0).getParent().getKey() != 3) System.out.println("error insert- 0");
        else if (tree_our.FindNodeByKey(4).getParent().getKey() != 3) System.out.println("error insert- 4");
        else if (tree_our.FindNodeByKey(8).getParent().getKey() != 13) System.out.println("error insert- 8");
        else if (tree_our.FindNodeByKey(18).getParent().getKey() != 13) System.out.println("error insert- 18");
        else printdone("inset(int key, boolean value)");
        return tree_our;
	}

	
	public static AVLTreeOur check_min(AVLTreeOur tree_our, AVLTreeOur tree2_our) {
		check_massege("min");
        Boolean m = tree_our.min();
        if (m) System.out.println("error min - 0");
        int w = tree_our.delete(0);
        Boolean m1 = tree_our.min();
        if (!m1) System.out.println("error min - 3");
        Boolean m2 = tree2_our.min();
        if (m2 != null) System.out.println("error min - null");
        printdone("min()");
        return tree_our;
	}
	
	public static AVLTreeOur check_max(AVLTreeOur tree_our, AVLTreeOur tree2_our) {
		check_massege("max");
        Boolean l = tree_our.max();
        if (l) System.out.println("error max - 40");
        int w2 = tree_our.delete(40);

        Boolean l1 = tree_our.max();
        if (!l1) System.out.println("error max - 25");
        Boolean l2 = tree2_our.max();
        if (l2 != null) System.out.println("error max - null");
        printdone("max()");
        return tree_our;
	}
	
	public static AVLTreeOur check_keysToArray(AVLTreeOur tree_our, AVLTreeOur tree2_our) {
		check_massege("KeysToArrays()");
        int[] arr = tree_our.keysToArray();
        int[] c = {3, 4, 5, 7, 8, 9, 10, 13, 15, 18, 25};
        if (!Arrays.equals(arr, c)) System.out.println("error keysToArray 1");
      
        int u = tree_our.insert(2, false);
        int u2 = tree_our.delete(7);

        int[] arr2 = tree_our.keysToArray();
        int[] c2 = {2, 3, 4, 5, 8, 9, 10, 13, 15, 18, 25};
        if (!Arrays.equals(arr2, c2)) System.out.println("error keysToArray 2");
        int[] c3 = new int[0];
        int[] arr3 = tree2_our.keysToArray();
        if (!Arrays.equals(arr3, c3)) System.out.println("error keysToArray 3");
        printdone("KeysToArrays()");
        return tree_our;
	}
	
	public static void check_infoToArray(AVLTreeOur tree_our, AVLTreeOur tree2_our, AVLTreeOur tree3_our) {
		check_massege("infoToArrays()");
        boolean[] arr4 = tree3_our.infoToArray();
        boolean[] arr7 = {true, true, false, true, false};
        boolean[] arr5 = tree_our.infoToArray();
        boolean[] arr6 = {false, true, false, true, false, false, true, false, false, false, true};
        boolean[] c4 = new boolean[0];
        if (!Arrays.equals(arr4, arr7)) System.out.println("error infoToArray - 1");
        else if (!Arrays.equals(arr5, arr6)) System.out.println("error infoToArray - 2");
        else if (!Arrays.equals(tree2_our.infoToArray(), c4)) System.out.println("error infoToArray - empty tree");
        else printdone("InfoToArrays()");
	}
	
	public static AVLTreeOur check_size(AVLTreeOur tree_our, AVLTreeOur tree2_our, AVLTreeOur tree3_our, AVLTreeOur tree4_our) {
		check_massege("Size()");
        if (tree_our.size() != 11) System.out.println("erroe size - 1");
        else if (tree3_our.size() != 5) System.out.println("erroe size - 2");
        else if (tree2_our.size() != 0) System.out.println("erroe size - empty tree");
        else if (tree4_our.size() != 6) System.out.println("erroe size - 3");
        
        int x = tree_our.insert(50, false);
        int x2 = tree_our.insert(55, false);
        if (tree_our.size()!= 13) System.out.println("error size - insert tree 1");
        int x3 = tree_our.delete(55);
        if (tree_our.size()!= 12) System.out.println("error size - delete tree 1");
        
        int y = tree2_our.insert(1,true);
        if (tree2_our.size() != 1) System.out.println("erroe size - insert empty tree");
        int y1 = tree2_our.delete(1);
        if (tree2_our.size() != 0) System.out.println("erroe size - delete tree 2");
        printdone("Size()");
        return tree_our;
	}
	
	public static void check_getRoot(AVLTreeOur tree_our, AVLTreeOur tree2_our, AVLTreeOur tree3_our) {
		check_massege("GetRoot()");
        AVLNode a3 = tree3_our.getRoot();
        AVLNode a = tree_our.getRoot();
        AVLNode a2 = tree2_our.getRoot();
        if (a3.getValue() || a3.getKey() != 8) System.out.println("error getRoot - tree3");
        else if (a.getValue() || a.getKey() != 8) System.out.println("error getRoot - 2");
        else if (a2.getValue() != null && a2.getKey() != -1) System.out.println("error getRoot - empty tree");
        else printdone("GetRoot()");
	}
	
	public static void check_successor(AVLTreeOur tree_our) {
		check_massege("Successor(AVLNode node)");
        AVLNode e1 = tree_our.FindNodeByKey(3);
        AVLNode e11 = tree_our.successor(e1);
        AVLNode e3 = tree_our.FindNodeByKey(9);
        AVLNode e4 = tree_our.FindNodeByKey(13);
        AVLNode e5 = tree_our.FindNodeByKey(4);
        AVLNode e6 = tree_our.FindNodeByKey(25);
        AVLNode e33 = tree_our.successor(e3);
        AVLNode e44 = tree_our.successor(e4);
        AVLNode e55 = tree_our.successor(e5);
        AVLNode e66 = tree_our.successor(e6);
        if (e11.getValue() || e11.getKey() != 4) System.out.println("error successor - 3");
        else if (!e33.getValue() || e33.getKey() != 10) System.out.println("error successor - 9");
        else if (e44.getValue() || e44.getKey() != 15) System.out.println("error successor - 13");
        else if (!e55.getValue() || e55.getKey() != 5) System.out.println("error successor - 4");
        else if (e66 != null) System.out.println("error successor - max successor");
        else printdone("Successor(AVLNode node)");
	}
	
	public static void check_prefixXor(AVLTreeOur tree_our) {
		
	}
	
	public static void check_succPrefixXor(AVLTreeOur tree_our) {
		check_massege("succPrefixXor(int k)");
        boolean y1 = tree_our.succPrefixXor(3); // true
        boolean y2 = tree_our.succPrefixXor(5); // false
        boolean y3 = tree_our.succPrefixXor(10);// true
        boolean y4 = tree_our.succPrefixXor(8);// false
        boolean y5 = tree_our.succPrefixXor(25);// false
        boolean y6 = tree_our.succPrefixXor(13);// true
        if (!y1) System.out.println("error succPrefixXor - 3");
        else if (y2) System.out.println("error succPrefixXor - 5");
        else if (!y3) System.out.println("error succPrefixXor - 10");
        else if (y4) System.out.println("error succPrefixXor - 8");
        else if (y5) System.out.println("error succPrefixXor - 25");
        else if (!y6) System.out.println("error succPrefixXor - 13");
        else printdone("succPrefixXor(int k)");

	}
	
	public static AVLTreeOur check_delete(AVLTreeOur tree_our, AVLTreeOur tree2_our) {
		check_massege("delete(int key)");
		printTree_our(tree_our);
        int y11 = tree_our.delete(6);
        printTree_our(tree_our);
        int y22 = tree_our.delete(8);
        printTree_our(tree_our);
        int y33 = tree2_our.delete(8);

        int higth10 =  tree_our.FindNodeByKey(10).getHeight();
        int y44 = tree_our.delete(13);
        printTree_our(tree_our);

        higth10 =  tree_our.FindNodeByKey(10).getHeight();
        int y55 = tree_our.delete(50);
        printTree_our(tree_our);

        int y66 = tree_our.delete(9);
        System.out.println("y11 = " + y11 + ", y22 = " + y22 + ", y33 = " + y33 + ", y44 = " + y44 + ", y55 = " + y55 + ", y66 = " + y66);
        if (y11 != -1) System.out.println("error delete - 6 not in tree");
        if (y22 != 2) System.out.println("error delete - 8");
        if (y33 != -1) System.out.println("error delete - tree2 empty");
        
        if (y44 != 0) System.out.println("error delete - 13 - change the higth of 10");
        if (y55 != 0) System.out.println("error delete - 50 - chenge the higth of 18");
        if (y66 != 2) System.out.println("error delete - 9 - one rotation");
        if (tree_our.getRoot().getKey() != 10) System.out.println("error delete - root");
        if (tree_our.getRoot().getLeft().getKey() != 3) System.out.println("error delete - left son");
        if (tree_our.getRoot().getRight().getKey() != 18) System.out.println("error delete - right son");
        printdone("delete(int key))");
        return tree_our;
	}
	
	
    public static void printTree_our(AVLTreeOur treeOur) {
        System.out.println("===[SIZE:"+treeOur.size()+"]===");
        printTree_our(treeOur.getRoot(), "", false);
        System.out.println("===[MIN:"+treeOur.getMinNode()+"]===");
        System.out.println("===[MAX:"+treeOur.getMaxNode()+"]===");
        System.out.println("============");
        System.out.println("");
    }

    public static void printTree_our(AVLNode currPtr, String indent, boolean last) {
        if (currPtr.isRealNode()) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            printNode(currPtr);
            printTree_our(currPtr.getLeft(), indent, false);
            printTree_our(currPtr.getRight(), indent, true);
        }
    }
    

    public static void inorder(AVLNode r) {
        if (r.getValue() != null) {
            inorder(r.getLeft());
            System.out.println("node = " + r.getKey() + ", value = " + r.getValue() + " left son = " + r.getLeft().getKey() + " rigth son = " + r.getRight().getKey());
            inorder(r.getRight());
        }
    }

    public static void printNode(AVLNode r) {
        System.out.println(r);
    }

    public static void check_massege(String method) {
        System.out.println("~~~~~~~~~~~~~ check " + method + " ~~~~~~~~~~~~~");
    }

    public static void printdone(String method) {
        System.out.println("~~~~~~~~~~~~~ Done  " + method + " ~~~~~~~~~~~~~");
        System.out.println();
    }
}

