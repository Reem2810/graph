package nl.han.asd;

import java.util.List;

public class UnweightedGraphMatrix {

    private boolean[][] adjMatrix;
    private int vertexCount;


    public UnweightedGraphMatrix(int numVertices) {
        this.vertexCount = numVertices;
        this.adjMatrix = new boolean[numVertices][numVertices];
    }

    /**
     * 2) Construct from a 2D List of Double (the JSON adjacency matrix data).
     *    Expects 1.0 = edge, 0.0 = no edge.
     */
    public UnweightedGraphMatrix(List<List<Double>> matrixData) {
        this.vertexCount = matrixData.size();
        this.adjMatrix = new boolean[vertexCount][vertexCount];

        // Populate the matrix
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < matrixData.get(i).size(); j++) {
                // If it's 1.0, mark the matrix cell as true (edge exists)
                if (matrixData.get(i).get(j) == 1.0) {
                    adjMatrix[i][j] = true;
                }
            }
        }
    }

    /**
     * Adds a new vertex by growing the matrix by 1 row and 1 column.
     */
    public void addVertex() {
        int newSize = vertexCount + 1;
        boolean[][] newMatrix = new boolean[newSize][newSize];

        // Copy old data
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                newMatrix[i][j] = adjMatrix[i][j];
            }
        }
        // Replace old with new
        adjMatrix = newMatrix;
        vertexCount++;
    }

    /**
     * Removes the specified vertex (row & column) by rebuilding the matrix
     * to exclude that vertex index.
     */
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Vertex " + vertex + " is out of range.");
        }
        int newSize = vertexCount - 1;
        boolean[][] newMatrix = new boolean[newSize][newSize];

        int newI = 0;
        for (int i = 0; i < vertexCount; i++) {
            // Skip the row for 'vertex'
            if (i == vertex) continue;
            int newJ = 0;
            for (int j = 0; j < vertexCount; j++) {
                // Skip the column for 'vertex'
                if (j == vertex) continue;

                newMatrix[newI][newJ] = adjMatrix[i][j];
                newJ++;
            }
            newI++;
        }
        // Replace old matrix
        adjMatrix = newMatrix;
        vertexCount = newSize;
    }

    public void addEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for addEdge: " + src + " or " + dest);
            return;
        }
        // Undirected edge
        adjMatrix[src][dest] = true;
        adjMatrix[dest][src] = true;
    }

    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            throw new IllegalArgumentException("Invalid vertex index for removeEdge.");
        }
        adjMatrix[src][dest] = false;
        adjMatrix[dest][src] = false;
    }

    public boolean hasEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return false;
        }
        return adjMatrix[src][dest];
    }

    private boolean isValidVertex(int vertex) {
        return vertex >= 0 && vertex < vertexCount;
    }


    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print("Vertex " + i + ": ");
            for (int j = 0; j < vertexCount; j++) {
                if (adjMatrix[i][j]) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
