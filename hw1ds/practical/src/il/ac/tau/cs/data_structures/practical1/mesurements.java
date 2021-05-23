package il.ac.tau.cs.data_structures.practical1;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class mesurements {
    Random random =  new Random();

    public static void main(String[] args) {
        mesurements m = new mesurements();
        m.first();
    }

    public void first() {
        for (int i=1; i<=5; i++) {
            AVLTreeOur treeOur = new AVLTreeOur();
            long timeElapsed = 0;
            long succTimeElapsed = 0;
            long start;
            long finish;
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
}
