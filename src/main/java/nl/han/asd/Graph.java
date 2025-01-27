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
            testUnweightedGraph(dataset);

            // Testing Unweighted Graph from adjacency matrix 'verbindingsmatrix'
            testGraphFromAdjacencyMatrix(dataset);

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
    private static void testUnweightedGraph(Map<String, Object> dataset) {
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

    // Test Unweighted Graph created from adjacency matrix
    private static void testGraphFromAdjacencyMatrix(Map<String, Object> dataset) {
        System.out.println("\n--- Testing Graph from Adjacency Matrix ('verbindingsmatrix') ---");
        @SuppressWarnings("unchecked")
        List<List<Double>> verbindingsmatrixRaw = (List<List<Double>>) dataset.get("verbindingsmatrix");

        // Convert adjacency matrix to adjacency list
        List<List<Integer>> adjListFromMatrix = new ArrayList<>();
        for (int i = 0; i < verbindingsmatrixRaw.size(); i++) {
            List<Integer> neighbors = new ArrayList<>();
            for (int j = 0; j < verbindingsmatrixRaw.get(i).size(); j++) {
                if (verbindingsmatrixRaw.get(i).get(j) == 1.0) {
                    neighbors.add(j);
                }
            }
            adjListFromMatrix.add(neighbors);
        }

        // Create UnweightedGraph from adjacency list
        UnweightedGraph graphFromMatrix = new UnweightedGraph(adjListFromMatrix);
        System.out.println("Initial Graph from Adjacency Matrix:");
        graphFromMatrix.printGraph();

        // Test operations
        System.out.println("\nAdding a vertex to Graph from Adjacency Matrix...");
        graphFromMatrix.addVertex();
        graphFromMatrix.printGraph();

        System.out.println("\nAdding edge (0 -> 6) in Graph from Adjacency Matrix...");
        graphFromMatrix.addEdge(0, 6);
        graphFromMatrix.printGraph();

        System.out.println("\nRemoving an edge (1 -> 2) in Graph from Adjacency Matrix...");
        graphFromMatrix.removeEdge(1, 2);
        graphFromMatrix.printGraph();

        System.out.println("\nRemoving vertex 4 in Graph from Adjacency Matrix...");
        graphFromMatrix.removeVertex(4);
        graphFromMatrix.printGraph();
    }

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
        List<List<Double>> verbindingsmatrixGewogenRaw = (List<List<Double>>) dataset.get("verbindingsmatrix_gewogen");
        System.out.println("Weighted Adjacency Matrix:");
        for (List<Double> row : verbindingsmatrixGewogenRaw) {
            System.out.println(row);
        }
    }
}
