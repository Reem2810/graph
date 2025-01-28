package nl.han.asd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UnweightedGraphPerformanceTest {

    public static void main(String[] args) {


        int initialVertices = 0;           // We'll start each graph with 0 vertices
        int totalVerticesToAdd = 1000;     // number of vertices to add
        int verticesToRemove = 200;        // number of vertices to remove
        int edgesToAdd = 2000;            // number of edges to add
        int edgesToRemove = 500;          // number of edges to remove

        // 1) Create empty adjacency-list and adjacency-matrix graphs
        UnweightedGraphAdjList listGraph = new UnweightedGraphAdjList(initialVertices);
        UnweightedGraphMatrix matrixGraph = new UnweightedGraphMatrix(initialVertices);

        // ----------------------------------------------------------
        // A) ADD VERTICES
        // ----------------------------------------------------------
        long startListAddV = System.currentTimeMillis();
        for (int i = 0; i < totalVerticesToAdd; i++) {
            listGraph.addVertex();
        }
        long endListAddV = System.currentTimeMillis();
        long listAddVTime = endListAddV - startListAddV;

        long startMatrixAddV = System.currentTimeMillis();
        for (int i = 0; i < totalVerticesToAdd; i++) {
            matrixGraph.addVertex();
        }
        long endMatrixAddV = System.currentTimeMillis();
        long matrixAddVTime = endMatrixAddV - startMatrixAddV;

        System.out.println("Add Vertex (AdjList)  : " + listAddVTime + " ms");
        System.out.println("Add Vertex (AdjMatrix): " + matrixAddVTime + " ms");

        // ----------------------------------------------------------
        // B) REMOVE VERTICES
        // ----------------------------------------------------------
        // We'll remove random existing vertices from each graph.
        // Just keep track so we don't remove beyond range.
        Random rnd = new Random();

        // For adjacency list
        long startListRemoveV = System.currentTimeMillis();
        for (int i = 0; i < verticesToRemove; i++) {
            // pick a random vertex in [0 .. currentSize-1]
            int v = rnd.nextInt(listGraph.getVertexCount());
            listGraph.removeVertex(v);
        }
        long endListRemoveV = System.currentTimeMillis();
        long listRemoveVTime = endListRemoveV - startListRemoveV;

        // For adjacency matrix
        long startMatrixRemoveV = System.currentTimeMillis();
        for (int i = 0; i < verticesToRemove; i++) {
            int v = rnd.nextInt(matrixGraph.getVertexCount());
            matrixGraph.removeVertex(v);
        }
        long endMatrixRemoveV = System.currentTimeMillis();
        long matrixRemoveVTime = endMatrixRemoveV - startMatrixRemoveV;

        System.out.println("\nRemove Vertex (AdjList)  : " + listRemoveVTime + " ms");
        System.out.println("Remove Vertex (AdjMatrix): " + matrixRemoveVTime + " ms");

        // ----------------------------------------------------------
        // C) ADD EDGES
        // ----------------------------------------------------------
        // We'll gather random edges (src, dst) pairs from the current
        // graph range, then add them all for both graphs
        List<int[]> randomEdges = generateRandomEdges(
                Math.min(listGraph.getVertexCount(), matrixGraph.getVertexCount()),
                edgesToAdd
        );

        // Adjacency list - add edges
        long startListAddE = System.currentTimeMillis();
        for (int[] edge : randomEdges) {
            listGraph.addEdge(edge[0], edge[1]);
        }
        long endListAddE = System.currentTimeMillis();
        long listAddETime = endListAddE - startListAddE;

        // Adjacency matrix - add edges
        long startMatrixAddE = System.currentTimeMillis();
        for (int[] edge : randomEdges) {
            matrixGraph.addEdge(edge[0], edge[1]);
        }
        long endMatrixAddE = System.currentTimeMillis();
        long matrixAddETime = endMatrixAddE - startMatrixAddE;

        System.out.println("\nAdd Edges (AdjList)  : " + listAddETime + " ms");
        System.out.println("Add Edges (AdjMatrix): " + matrixAddETime + " ms");

        // ----------------------------------------------------------
        // D) REMOVE EDGES
        // ----------------------------------------------------------
        // We'll gather random edges (src, dst) from the same range.
        // Some of them might not exist, but that's okay for demonstration.
        List<int[]> randomEdgesToRemove = generateRandomEdges(
                Math.min(listGraph.getVertexCount(), matrixGraph.getVertexCount()),
                edgesToRemove
        );

        // Adjacency list - remove edges
        long startListRemoveE = System.currentTimeMillis();
        for (int[] edge : randomEdgesToRemove) {
            listGraph.removeEdge(edge[0], edge[1]);
        }
        long endListRemoveE = System.currentTimeMillis();
        long listRemoveETime = endListRemoveE - startListRemoveE;

        // Adjacency matrix - remove edges
        long startMatrixRemoveE = System.currentTimeMillis();
        for (int[] edge : randomEdgesToRemove) {
            matrixGraph.removeEdge(edge[0], edge[1]);
        }
        long endMatrixRemoveE = System.currentTimeMillis();
        long matrixRemoveETime = endMatrixRemoveE - startMatrixRemoveE;

        System.out.println("\nRemove Edges (AdjList)  : " + listRemoveETime + " ms");
        System.out.println("Remove Edges (AdjMatrix): " + matrixRemoveETime + " ms");
    }

    // Helper method to generate random (src, dst) pairs
    private static List<int[]> generateRandomEdges(int vertexCount, int howMany) {
        Random rand = new Random();
        List<int[]> edges = new ArrayList<>(howMany);
        for (int i = 0; i < howMany; i++) {
            int src = rand.nextInt(vertexCount);
            int dst = rand.nextInt(vertexCount);
            edges.add(new int[]{src, dst});
        }
        return edges;
    }
}
