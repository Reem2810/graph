package nl.han.asd;

import java.util.List;

public class WeightedGraphMatrix {

    private double[][] matrix;    // holds weights; "no edge" marked by special value
    private int vertexCount;
    private final double NO_EDGE = Double.POSITIVE_INFINITY;

    /**
     * 1) Construct an empty weighted matrix of size numVertices x numVertices,
     *    initializing each cell to NO_EDGE.
     */
    public WeightedGraphMatrix(int numVertices) {
        this.vertexCount = numVertices;
        this.matrix = new double[numVertices][numVertices];
        // Initialize to NO_EDGE
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrix[i][j] = NO_EDGE;
            }
        }
    }

    /**
     * 2) Construct from a 2D List of Double (the JSON adjacency matrix data).
     *    0.0 or some positive number is a valid edge weight,
     *    and we assume -1 or some sentinel indicates "no edge" (depends on how dataset is structured).
     */
    public WeightedGraphMatrix(List<List<Double>> matrixData) {
        this.vertexCount = matrixData.size();
        this.matrix = new double[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                double value = matrixData.get(i).get(j);
                // Example logic:
                // if value <= 0 => no edge
                // else => that is the weight
                if (value <= 0.0) {
                    matrix[i][j] = NO_EDGE;
                } else {
                    matrix[i][j] = value;
                }
            }
        }
    }

    /**
     * Adds a directed edge (src -> dest) with a given weight.
     */
    public void addEdge(int src, int dest, double weight) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for addEdge: " + src + " or " + dest);
            return;
        }
        // Directed: set only [src][dest]
        matrix[src][dest] = weight;
    }

    /**
     * Removes a directed edge (src -> dest) by setting weight to NO_EDGE.
     */
    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for removeEdge: " + src + " or " + dest);
            return;
        }
        // Directed: set only [src][dest] to NO_EDGE
        matrix[src][dest] = NO_EDGE;
    }

    /**
     * Get the weight of an edge (or NO_EDGE if none).
     */
    public double getWeight(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return NO_EDGE;
        }
        return matrix[src][dest];
    }

    /**
     * Check if there's an actual directed edge (not NO_EDGE).
     */
    public boolean hasEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return false;
        }
        return matrix[src][dest] != NO_EDGE;
    }

    /**
     * Add a vertex by expanding the matrix to newSize x newSize.
     */
    public void addVertex() {
        int newSize = vertexCount + 1;
        double[][] newMatrix = new double[newSize][newSize];

        // Initialize all to NO_EDGE
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                newMatrix[i][j] = NO_EDGE;
            }
        }

        // Copy old data
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        // Replace old
        matrix = newMatrix;
        vertexCount++;
    }

    /**
     * Remove a vertex by rebuilding the matrix without that row and column.
     */
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

    /**
     * Print each row. If a cell is NO_EDGE, print "∞" (infinity).
     */
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

    /**
     * Helper to check valid vertex index.
     */
    private boolean isValidVertex(int v) {
        return (v >= 0 && v < vertexCount);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
