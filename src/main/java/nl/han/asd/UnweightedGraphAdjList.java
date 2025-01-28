package nl.han.asd;

import java.util.ArrayList;
import java.util.List;

public class UnweightedGraph {

    private List<List<Integer>> adjList;


    public UnweightedGraph(int numVertices) {
        adjList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public UnweightedGraph(List<List<Integer>> verbindingslijst) {
        adjList = new ArrayList<>();
        for (List<Integer> list : verbindingslijst) {
            adjList.add(new ArrayList<>(list));
        }
    }

    public void addVertex() {

        adjList.add(new ArrayList<>());
    }

    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= adjList.size()) {
            throw new IllegalArgumentException("Vertex " + vertex + " does not exist.");
        }
        adjList.remove(vertex);
        for (List<Integer> list : adjList) {
            list.removeIf(v -> v == vertex);
            for (int i = 0; i < list.size(); i++) {
                int v = list.get(i);
                if (v > vertex) {
                    list.set(i, v - 1);
                }
            }
        }
    }

    public void addEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index.");
            return;
        }
        if (!adjList.get(src).contains(dest)) {
            adjList.get(src).add(dest);
        }
        if (!adjList.get(dest).contains(src)) {
            adjList.get(dest).add(src);
        }
    }

    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            throw new IllegalArgumentException("Invalid vertex index.");
        }
        adjList.get(src).remove((Integer) dest);
        adjList.get(dest).remove((Integer) src);
    }


    private boolean isValidVertex(int vertex) {

        return vertex >= 0 && vertex < adjList.size();
    }

    public boolean hasEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return false;
        }
        return adjList.get(src).contains(dest);
    }


    // Print the graph
    public void printGraph() {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.println("Vertex " + i + ": " + adjList.get(i));
        }
    }
}
