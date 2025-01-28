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


    public void addEdge(int src, int dest, int weight) {
        if (adjList == null || adjList.isEmpty()
                || !isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid operation or vertex index.");
            return;
        }

        boolean edgeExists = adjList.get(src).stream().anyMatch(edge -> edge.destination == dest);

        if (!edgeExists) {
            adjList.get(src).add(new Edge(dest, weight));
        }
    }

    public void removeEdge(int src, int dest) {
        if (adjList == null || adjList.isEmpty()
                || !isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid operation or vertex index.");
            return;
        }

        adjList.get(src).removeIf(edge -> edge.destination == dest);
    }

    private boolean isValidVertex(int vertex) {
        return vertex >= 0 && vertex < adjList.size();
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
