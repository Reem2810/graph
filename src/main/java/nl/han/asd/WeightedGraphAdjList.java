package nl.han.asd;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraphAdjList {

    private List<List<Edge>> adjList;

    public static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + destination + ", weight: " + weight + ")";
        }
    }

    public WeightedGraphAdjList(int initialVertices) {
        adjList = new ArrayList<>(initialVertices);
        for (int i = 0; i < initialVertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public WeightedGraphAdjList(List<List<List<Object>>> verbindingslijstGewogen) {
        adjList = new ArrayList<>();
        for (List<List<Object>> nodeEdges : verbindingslijstGewogen) {
            List<Edge> edges = new ArrayList<>();
            for (List<Object> edgeData : nodeEdges) {
                if (edgeData.size() >= 2
                        && edgeData.get(0) instanceof Number
                        && edgeData.get(1) instanceof Number) {

                    int destination = ((Number) edgeData.get(0)).intValue();
                    int weight = ((Number) edgeData.get(1)).intValue();
                    edges.add(new Edge(destination, weight));
                } else {
                    System.out.println("Invalid edge data: " + edgeData);
                }
            }
            adjList.add(edges);
        }
    }

    /**
     * Add a new vertex by appending an empty edge list.
     */
    public void addVertex() {
        if (adjList == null) {
            adjList = new ArrayList<>();
        }
        adjList.add(new ArrayList<>());
    }

    /**
     * Remove the specified vertex.
     * 1) Remove its list (the row).
     * 2) For all remaining vertices, remove edges pointing to 'vertex'
     *    and adjust edges' destination if their index was above the removed vertex.
     */
    public void removeVertex(int vertex) {
        if (!isValidVertex(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex + " is out of range.");
        }
        // Remove the row
        adjList.remove(vertex);

        // For each remaining list, remove or adjust edges
        for (int src = 0; src < adjList.size(); src++) {
            // Remove edges that point to 'vertex'
            adjList.get(src).removeIf(e -> e.destination == vertex);

            // Decrement destination indices that are above 'vertex'
            for (Edge e : adjList.get(src)) {
                if (e.destination > vertex) {
                    e.destination--;
                }
            }
        }
    }

    public void addEdge(int src, int dest, int weight) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid operation or vertex index.");
            return;
        }

        boolean edgeExists = adjList.get(src).stream().anyMatch(edge -> edge.destination == dest);
        if (!edgeExists) {
            adjList.get(src).add(new Edge(dest, weight));
        }
    }

    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid operation or vertex index.");
            return;
        }
        adjList.get(src).removeIf(edge -> edge.destination == dest);
    }

    private boolean isValidVertex(int vertex) {
        return vertex >= 0 && vertex < adjList.size();
    }

    public int getVertexCount() {
        return (adjList == null) ? 0 : adjList.size();
    }

    public void printGraph() {
        if (adjList == null || adjList.isEmpty()) {
            System.out.println("The graph is empty.");
            return;
        }
        for (int i = 0; i < adjList.size(); i++) {
            System.out.println("Vertex " + i + ": " + adjList.get(i));
        }
    }
}
