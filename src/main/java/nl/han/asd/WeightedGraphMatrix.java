package nl.han.asd;

import java.util.List;

public class WeightedGraphMatrix {

    private double[][] matrix;
    private int vertexCount;
    private final double NO_EDGE = Double.POSITIVE_INFINITY;


    public WeightedGraphMatrix(int numVertices) {
        this.vertexCount = numVertices;
        this.matrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrix[i][j] = NO_EDGE;
            }
        }
    }


    public WeightedGraphMatrix(List<List<Double>> matrixData) {
        this.vertexCount = matrixData.size();
        this.matrix = new double[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                double value = matrixData.get(i).get(j);
                if (value <= 0.0) {
                    matrix[i][j] = NO_EDGE;
                } else {
                    matrix[i][j] = value;
                }
            }
        }
    }

    public void addEdge(int src, int dest, double weight) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for addEdge: " + src + " or " + dest);
            return;
        }
        // Directed: set only [src][dest]
        matrix[src][dest] = weight;
    }

    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for removeEdge: " + src + " or " + dest);
            return;
        }
        // Directed: set only [src][dest] to NO_EDGE
        matrix[src][dest] = NO_EDGE;
    }


    public double getWeight(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return NO_EDGE;
        }
        return matrix[src][dest];
    }


    public boolean hasEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return false;
        }
        return matrix[src][dest] != NO_EDGE;
    }


    public void addVertex() {
        int newSize = vertexCount + 1;
        double[][] newMatrix = new double[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                newMatrix[i][j] = NO_EDGE;
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        matrix = newMatrix;
        vertexCount++;
    }


    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) {
            throw new IllegalArgumentException("Invalid vertex index: " + vertex);
        }
        int newSize = vertexCount - 1;
        double[][] newMatrix = new double[newSize][newSize];

        int newI = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (i == vertex) continue;  // skip row 'vertex'
            int newJ = 0;
            for (int j = 0; j < vertexCount; j++) {
                if (j == vertex) continue;  // skip col 'vertex'
                newMatrix[newI][newJ] = matrix[i][j];
                newJ++;
            }
            newI++;
        }

        matrix = newMatrix;
        vertexCount = newSize;
    }


    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print("Vertex " + i + ": ");
            for (int j = 0; j < vertexCount; j++) {
                if (matrix[i][j] == NO_EDGE) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }


    private boolean isValidVertex(int v) {
        return (v >= 0 && v < vertexCount);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
