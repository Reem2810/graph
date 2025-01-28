package nl.han.asd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {

    // Method to load the dataset from JSON
    public Map<String, Object> loadDataset() {
        // Locate and load the dataset file
        InputStream inputStream = getClass().getResourceAsStream("/dataset.json");
        if (inputStream == null) {
            System.out.println("dataset.json not found in resources.");
            return null;
        }
        InputStreamReader reader = new InputStreamReader(inputStream);

        // Use Gson to parse the JSON file
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();

        // Deserialize the JSON into a Map
        return gson.fromJson(reader, type);
    }

    public static void main(String[] args) {
        Graph loader = new Graph();
        Map<String, Object> dataset = loader.loadDataset();

        if (dataset != null) {
            System.out.println("Dataset loaded successfully!");

            // Testing Unweighted Graph using 'verbindingslijst'
            testUnweightedGraphAdjList(dataset);

            // Testing Unweighted Graph from adjacency matrix 'verbindingsmatrix'
            testUnweightedGraphFromAdjacencyMatrix(dataset);

            // Testing Weighted Graph using 'verbindingslijst_gewogen'
            testWeightedGraph(dataset);

            // Testing Edge List 'lijnlijst'
            testEdgeList(dataset);

            // Testing Weighted Edge List 'lijnlijst_gewogen'
            testWeightedEdgeList(dataset);

            // Testing Weighted Adjacency Matrix 'verbindingsmatrix_gewogen'
            testWeightedAdjacencyMatrix(dataset);
        } else {
            System.out.println("Failed to load dataset.");
        }
    }

    // Test Unweighted Graph with adjacency list
    private static void testUnweightedGraphAdjList(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Unweighted Graph (from 'verbindingslijst') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> verbindingslijstRaw = (List<List<Double>>) dataset.get("verbindingslijst");

        // Convert raw data to Integer adjacency list
        List<List<Integer>> verbindingslijst = new ArrayList<>();
        for (List<Double> rawList : verbindingslijstRaw) {
            List<Integer> intList = new ArrayList<>();
            for (Double value : rawList) {
                intList.add(value.intValue());
            }
            verbindingslijst.add(intList);
        }

        // Initialize the UnweightedGraph
        UnweightedGraph unweightedGraph = new UnweightedGraph(verbindingslijst);
        System.out.println("Initial Unweighted Graph:");
        unweightedGraph.printGraph();

        // Test adding and removing vertices and edges
        System.out.println("\nAdding a vertex to Unweighted Graph...");
        unweightedGraph.addVertex();
        unweightedGraph.printGraph();

        System.out.println("\nRemoving vertex 3 from Unweighted Graph...");
        unweightedGraph.removeVertex(3);
        unweightedGraph.printGraph();

        System.out.println("\nAdding edge (1 -> 4) in Unweighted Graph...");
        unweightedGraph.addEdge(1, 4);
        unweightedGraph.printGraph();

        System.out.println("\nRemoving edge (1 -> 4) in Unweighted Graph...");
        unweightedGraph.removeEdge(1, 4);
        unweightedGraph.printGraph();
    }

    private static void testUnweightedGraphFromAdjacencyMatrix (Map<String, Object> dataset) {
        System.out.println("\n--- Testing Unweighted Graph (from 'verbindingsMatrix') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> verbindingsmatrixRaw =
                (List<List<Double>>) dataset.get("verbindingsmatrix");

// Create our new matrix-based graph (using the second constructor):
        UnweightedGraphMatrix matrixGraph = new UnweightedGraphMatrix(verbindingsmatrixRaw);

        System.out.println("Initial Graph (Matrix Representation):");
        matrixGraph.printGraph();

// Demo calls:
        System.out.println("\nAdding a vertex to Matrix Graph...");
        matrixGraph.addVertex();
        matrixGraph.printGraph();

        System.out.println("\nAdding edge (0 -> 6) in Matrix Graph...");
        matrixGraph.addEdge(0, 6);
        matrixGraph.printGraph();

        System.out.println("\nRemoving an edge (1 -> 2) in Matrix Graph...");
        matrixGraph.removeEdge(1, 2);
        matrixGraph.printGraph();

// Currently removeVertex() is not implemented, so do:
        System.out.println("\nTrying to remove vertex 4 in Matrix Graph...");
        try {
            matrixGraph.removeVertex(4);
        } catch (UnsupportedOperationException e) {
            System.out.println("removeVertex not implemented (demo).");
        }
        matrixGraph.printGraph();

    }


    // Test Unweighted Graph created from adjacency matrix

    // Test Weighted Graph with adjacency list
    private static void testWeightedGraph(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Weighted Graph (from 'verbindingslijst_gewogen') ---");
        @SuppressWarnings("unchecked")
        List<List<List<Object>>> verbindingslijstGewogen =
                (List<List<List<Object>>>) dataset.get("verbindingslijst_gewogen");

        if (verbindingslijstGewogen != null) {
            WeightedGraph weightedGraph = new WeightedGraph(verbindingslijstGewogen);
            System.out.println("Initial Weighted Graph:");
            weightedGraph.printGraph();

            // Test adding and removing edges
            System.out.println("\nAdding edge (2 -> 4) with weight 60 in Weighted Graph...");
            weightedGraph.addEdge(2, 4, 60);
            weightedGraph.printGraph();

            System.out.println("\nRemoving edge (2 -> 4) in Weighted Graph...");
            weightedGraph.removeEdge(2, 4);
            weightedGraph.printGraph();
        }
    }

    // Test Edge List
    private static void testEdgeList(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Edge List ('lijnlijst') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> lijnlijstRaw = (List<List<Double>>) dataset.get("lijnlijst");
        System.out.println("Edge List:");
        for (List<Double> edge : lijnlijstRaw) {
            int from = edge.get(0).intValue();
            int to = edge.get(1).intValue();
            System.out.println("Edge: " + from + " -> " + to);
        }
    }



    // Test Weighted Edge List
    private static void testWeightedEdgeList(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Weighted Edge List ('lijnlijst_gewogen') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> lijnlijstGewogenRaw = (List<List<Double>>) dataset.get("lijnlijst_gewogen");
        System.out.println("Weighted Edge List:");
        for (List<Double> edge : lijnlijstGewogenRaw) {
            int from = edge.get(0).intValue();
            int to = edge.get(1).intValue();
            int weight = edge.get(2).intValue();
            System.out.println("Edge: " + from + " -> " + to + " (weight: " + weight + ")");
        }
    }

    // Test Weighted Adjacency Matrix
    private static void testWeightedAdjacencyMatrix(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Weighted Adjacency Matrix ('verbindingsmatrix_gewogen') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> verbindingsmatrixGewogenRaw =
                (List<List<Double>>) dataset.get("verbindingsmatrix_gewogen");

        // Build a WeightedGraphMatrix from the dataset
        WeightedGraphMatrix wgMatrix = new WeightedGraphMatrix(verbindingsmatrixGewogenRaw);

        System.out.println("Initial Weighted Adjacency Matrix:");
        wgMatrix.printGraph();

        // Test: add an edge (0 -> 2) with weight 50
        System.out.println("\nAdding edge (0 -> 2) with weight 50...");
        wgMatrix.addEdge(0, 2, 50);
        wgMatrix.printGraph();

        // Test: remove that edge
        System.out.println("\nRemoving edge (0 -> 2)...");
        wgMatrix.removeEdge(0, 2);
        wgMatrix.printGraph();

        // Test: check hasEdge
        System.out.println("\nHas edge (1 -> 3)? " + wgMatrix.hasEdge(1, 3));
        if (wgMatrix.hasEdge(1, 3)) {
            System.out.println("Weight is: " + wgMatrix.getWeight(1, 3));
        }

        // Test: add a new vertex
        System.out.println("\nAdding a vertex to WeightedGraphMatrix...");
        wgMatrix.addVertex();
        wgMatrix.printGraph();

        // (Optional) try removing vertex 4 (if it exists)
        if (wgMatrix.getVertexCount() > 4) {
            System.out.println("\nRemoving vertex 4 from WeightedGraphMatrix...");
            try {
                wgMatrix.removeVertex(4);
            } catch (UnsupportedOperationException e) {
                System.out.println("removeVertex not implemented yet in WeightedGraphMatrix.");
            }
            wgMatrix.printGraph();
        }
    }

}