package il.ac.tau.cs.data_structures.practical1;

import java.util.Random;
import java.util.stream.IntStream;

public class mesurements {
    Random random =  new Random();

    public static void main(String[] args) {
        mesurements m = new mesurements();
        m.first();
        System.out.println("=====================");
        m.second();
    }

    public void first() {
        for (int i=5; i>0; i--) {
            AVLTree treeOur = new AVLTree();
            double timeElapsed = 0;
            double succTimeElapsed = 0;
            double start;
            double finish;
            while (treeOur.size()<500*i) {
                treeOur.insert(random.nextInt(1000000000), random.nextBoolean());
            }
            int[] numbers = treeOur.keysToArray();

            // 100 succ
            start = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                treeOur.succPrefixXor(numbers[j]);
            }
            finish = System.nanoTime();
            succTimeElapsed += finish - start;
            start = System.nanoTime();

            // 100 pref
            for (int j = 0; j < 100; j++) {
                treeOur.prefixXor(numbers[j]);

            }
            finish = System.nanoTime();
            timeElapsed += finish - start;

            System.out.println(i+": first 100: succ:"+succTimeElapsed/100);
            System.out.println(i+": first 100: pref:"+timeElapsed/100);


            // all succ
            start = System.nanoTime();
            for (int j = 0; j < 500*i; j++) {
                treeOur.succPrefixXor(numbers[j]);
            }
            finish = System.nanoTime();
            succTimeElapsed += finish - start;
            start = System.nanoTime();

            // all  pref
            for (int j = 0; j < 500*i; j++) {
                treeOur.prefixXor(numbers[j]);

            }
            finish = System.nanoTime();
            timeElapsed += finish - start;

            System.out.println(i+": all: succ:"+succTimeElapsed/(500*i));
            System.out.println(i+": all: pref:"+timeElapsed/(500*i));
        }
    }

    public  void second() {
        for (int i=1; i<=5; i++) {
            AVLTree treeOur;
            unbalancedAVL unbalancedTree;

            double timeElapsed = 0;
            double start;
            double finish;

            int[] heshbon  = IntStream.rangeClosed(0, 1000*i).toArray();

            int[] balanced  = new int[1000*i];
            int j = 2;
            int layer = 2;
            balanced[1] = 1 + 500*i;
            while (j<1000*i) {
                for (int k = 1; k <= layer; k++) {
                    balanced[j] = (int) (balanced[j/2] + Math.pow(-1,k)*(500/layer));
                    j++;
                    if (j>=1000*i)
                        break;
                }
                layer *= 2;
            }

            int[] rndm  = random.ints(1000*i, 1, Integer.MAX_VALUE ).toArray();



            // balanced heshbon
            treeOur = new AVLTree();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                treeOur.insert(heshbon[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": balanced: heshbon:"+timeElapsed/(1000*i));

            // unbalanced heshbon
            unbalancedTree = new unbalancedAVL();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                unbalancedTree.insert(heshbon[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": unbalanced: heshbon:"+timeElapsed/(1000*i));



            // balanced balanced
            treeOur = new AVLTree();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                treeOur.insert(balanced[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": balanced: balanced:"+timeElapsed/(1000*i));

            // unbalanced balanced
            unbalancedTree = new unbalancedAVL();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                unbalancedTree.insert(balanced[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": unbalanced: balanced:"+timeElapsed/(1000*i));



            // balanced rndm
            treeOur = new AVLTree();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                treeOur.insert(rndm[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": balanced: rndm:"+timeElapsed/(1000*i));

            // unbalanced rndm
            unbalancedTree = new unbalancedAVL();
            start = System.nanoTime();
            for (int k = 1; j < 1000*i; j++) {
                unbalancedTree.insert(rndm[k],true);
            }
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(i+": unbalanced: rndm:"+timeElapsed/(1000*i));

        }
    }
}
