package nl.han.asd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedGraphPerformanceTest {

    public static void main(String[] args) {
        // Both implementations start with 1000 vertices.
        WeightedGraphAdjList listGraph = new WeightedGraphAdjList(1000);
        WeightedGraphMatrix matrixGraph = new WeightedGraphMatrix(1000);

        // We'll measure these operations:
        // A) Add 200 vertices
        // B) Remove 100 vertices
        // C) Add 2000 edges
        // D) Remove 500 edges

        int verticesToAdd = 200;
        int verticesToRemove = 100;
        int edgesToAdd = 2000;
        int edgesToRemove = 500;

        // --------------------------------------------------
        // 1) Add vertices (in both)
        // --------------------------------------------------
        long startListAddV = System.nanoTime();
        for (int i = 0; i < verticesToAdd; i++) {
            listGraph.addVertex();
        }
        long listAddVTime = System.nanoTime() - startListAddV;

        long startMatrixAddV = System.nanoTime();
        for (int i = 0; i < verticesToAdd; i++) {
            matrixGraph.addVertex();
        }
        long matrixAddVTime = System.nanoTime() - startMatrixAddV;

        System.out.println("Add Vertex (AdjList)   : " + listAddVTime   + " ns");
        System.out.println("Add Vertex (AdjMatrix) : " + matrixAddVTime + " ns\n");

        // --------------------------------------------------
        // 2) Remove vertices (in both)
        // --------------------------------------------------
        Random rand = new Random();

        long startListRemoveV = System.nanoTime();
        for (int i = 0; i < verticesToRemove; i++) {
            if (listGraph.getVertexCount() > 0) {
                int v = rand.nextInt(listGraph.getVertexCount());
                listGraph.removeVertex(v);
            }
        }
        long listRemoveVTime = System.nanoTime() - startListRemoveV;

        long startMatrixRemoveV = System.nanoTime();
        for (int i = 0; i < verticesToRemove; i++) {
            if (matrixGraph.getVertexCount() > 0) {
                int v = rand.nextInt(matrixGraph.getVertexCount());
                matrixGraph.removeVertex(v);
            }
        }
        long matrixRemoveVTime = System.nanoTime() - startMatrixRemoveV;

        System.out.println("Remove Vertex (AdjList)   : " + listRemoveVTime   + " ns");
        System.out.println("Remove Vertex (AdjMatrix) : " + matrixRemoveVTime + " ns\n");

        // --------------------------------------------------
        // 3) Add edges (in both)
        // --------------------------------------------------
        int listCount   = listGraph.getVertexCount();
        int matrixCount = matrixGraph.getVertexCount();
        int commonCount = Math.min(listCount, matrixCount);
        if (commonCount <= 0) {
            System.out.println("No vertices to add edges. Test aborted.");
            return;
        }
        List<EdgeInfo> edgesToAddList = generateRandomEdges(commonCount, edgesToAdd);

        long startListAddE = System.nanoTime();
        for (EdgeInfo e : edgesToAddList) {
            listGraph.addEdge(e.src, e.dest, e.weight);
        }
        long listAddETime = System.nanoTime() - startListAddE;

        long startMatrixAddE = System.nanoTime();
        for (EdgeInfo e : edgesToAddList) {
            matrixGraph.addEdge(e.src, e.dest, e.weight);
        }
        long matrixAddETime = System.nanoTime() - startMatrixAddE;

        System.out.println("Add Edges (AdjList)   : " + listAddETime   + " ns");
        System.out.println("Add Edges (AdjMatrix) : " + matrixAddETime + " ns\n");

        // --------------------------------------------------
        // 4) Remove edges (in both)
        // --------------------------------------------------
        List<EdgeInfo> edgesToRemoveList = generateRandomEdges(commonCount, edgesToRemove);

        long startListRemoveE = System.nanoTime();
        for (EdgeInfo e : edgesToRemoveList) {
            listGraph.removeEdge(e.src, e.dest);
        }
        long listRemoveETime = System.nanoTime() - startListRemoveE;

        long startMatrixRemoveE = System.nanoTime();
        for (EdgeInfo e : edgesToRemoveList) {
            matrixGraph.removeEdge(e.src, e.dest);
        }
        long matrixRemoveETime = System.nanoTime() - startMatrixRemoveE;

        System.out.println("Remove Edges (AdjList)   : " + listRemoveETime   + " ns");
        System.out.println("Remove Edges (AdjMatrix) : " + matrixRemoveETime + " ns");
    }

    private static class EdgeInfo {
        int src;
        int dest;
        int weight;
        EdgeInfo(int s, int d, int w) {
            src = s;
            dest = d;
            weight = w;
        }
    }

    private static List<EdgeInfo> generateRandomEdges(int maxVerts, int howMany) {
        Random r = new Random();
        List<EdgeInfo> edges = new ArrayList<>(howMany);
        for (int i = 0; i < howMany; i++) {
            int src = r.nextInt(maxVerts);
            int dst = r.nextInt(maxVerts);
            int weight = 1 + r.nextInt(100); // random weight in [1..100]
            edges.add(new EdgeInfo(src, dst, weight));
        }
        return edges;
    }
}
