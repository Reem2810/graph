package nl.han.asd;

import java.util.Random;

public class GraphPerformanceTest {

    public static void main(String[] args) {
        // Choose a graph size that is large enough to see clear performance differences.
        // For example, 10,000 might be large but can also be quite big for an adjacency matrix (10000x10000).
        // For demonstration, let's use a smaller size like 1000 or 2000, but you can adjust as needed.
        int numVertices = 2000;

        // 1. Build an adjacency-list–based graph
        UnweightedGraphAdjList listGraph = new UnweightedGraphAdjList(numVertices);

        // 2. Build an adjacency-matrix–based graph
        UnweightedGraphMatrix matrixGraph = new UnweightedGraphMatrix(numVertices);

        // 3. Insert random edges into both graphs
        //    We'll insert roughly (numVertices * 5) edges, for example.
        //    Adjust the factor to control sparsity or density.

        int edgesToInsert = numVertices * 5;
        Random random = new Random();

        System.out.println("Inserting " + edgesToInsert + " edges in both representations...");
        long startListInsert = System.currentTimeMillis();
        for (int i = 0; i < edgesToInsert; i++) {
            int src = random.nextInt(numVertices);
            int dst = random.nextInt(numVertices);
            listGraph.addEdge(src, dst);
        }
        long endListInsert = System.currentTimeMillis();

        long startMatrixInsert = System.currentTimeMillis();
        random = new Random(); // re-seed or not, up to you
        for (int i = 0; i < edgesToInsert; i++) {
            int src = random.nextInt(numVertices);
            int dst = random.nextInt(numVertices);
            matrixGraph.addEdge(src, dst);
        }
        long endMatrixInsert = System.currentTimeMillis();

        long listInsertTime = endListInsert - startListInsert;
        long matrixInsertTime = endMatrixInsert - startMatrixInsert;

        System.out.println("Adjacency List edge insertion time: " + listInsertTime + " ms");
        System.out.println("Adjacency Matrix edge insertion time: " + matrixInsertTime + " ms");

        // 4. Now let's measure hasEdge() lookups
        int checks = numVertices * 10;
        System.out.println("\nPerforming " + checks + " random hasEdge() checks...");

        long startListCheck = System.currentTimeMillis();
        for (int i = 0; i < checks; i++) {
            int src = random.nextInt(numVertices);
            int dst = random.nextInt(numVertices);
            listGraph.hasEdge(src, dst);
        }
        long endListCheck = System.currentTimeMillis();

        long startMatrixCheck = System.currentTimeMillis();
        for (int i = 0; i < checks; i++) {
            int src = random.nextInt(numVertices);
            int dst = random.nextInt(numVertices);
            matrixGraph.hasEdge(src, dst);
        }
        long endMatrixCheck = System.currentTimeMillis();

        long listCheckTime = endListCheck - startListCheck;
        long matrixCheckTime = endMatrixCheck - startMatrixCheck;

        System.out.println("Adjacency List hasEdge() check time: " + listCheckTime + " ms");
        System.out.println("Adjacency Matrix hasEdge() check time: " + matrixCheckTime + " ms");
    }
}
