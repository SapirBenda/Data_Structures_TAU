package il.ac.tau.cs.data_structures.practical1;

public class Test_AVL_sapir {
	
	public static void main(String[] args) {
		
		AVLTree tree = new AVLTree();
        if(!tree.empty())
        	System.out.println("erorr empty()");
        
        
		int[] keys = {5, 10, 8, 3, 7};
        boolean[] vals = {true, true, false, false, true};
        
       
       for (int i = 0; i < keys.length; i++) {
           tree.insert(keys[i], vals[i]);
       }

		tree.delete(8);


	}
}

