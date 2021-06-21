import java.util.Random;

public class mesurments {
    public static void main(String[] args) {
        for (int i=6; i<22; i++) {
            System.out.println("==="+i+"===");
            test((int)Math.pow(2,i));
        }
        //test((int)Math.pow(2,6));
    }

    public static void test(int n) {
        Graph.Node[] nodes = new Graph.Node[n];
        for (int i =0; i< nodes.length; i ++) {
            nodes[i] = new Graph.Node(i, 1);
        }
        Graph g = new Graph(nodes);
        Random random = new Random();
        int i=0;
        while (i<n) {
            Graph.Node a;
            do {
                a = g.getNode(random.nextInt(n));
            } while (a.getNeighborhoodWeight()==n);

            Graph.Node b;
            do {
                b = g.getNode(random.nextInt(n));
            } while (b.getNeighbors().findNode(b)!=null);

            g.addEdge(a.getId(),b.getId());
            i++;
        }

        System.out.println("nodes: "+g.getNumNodes());
        System.out.println("edges: "+g.getNumEdges());
        System.out.println("max degree: "+g.maxNeighborhoodWeight().getNeighborhoodWeight());
    }


}
