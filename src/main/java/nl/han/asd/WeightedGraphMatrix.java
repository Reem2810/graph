package nl.han.asd;

import java.util.List;

public class WeightedGraphMatrix {

    private double[][] matrix;    // holds weights; "no edge" marked by special value
    private int vertexCount;
    private final double NO_EDGE = Double.POSITIVE_INFINITY;
    // or you could use -1.0 to indicate "no edge"

    /**
     * 1) Construct an empty weighted matrix of size numVertices x numVertices,
     * initializing each cell to NO_EDGE (except diagonal = 0.0 if you like).
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
     *    If your dataset uses 0 = no edge or -1 = no edge, adapt logic accordingly.
     */
    public WeightedGraphMatrix(List<List<Double>> matrixData) {
        this.vertexCount = matrixData.size();
        this.matrix = new double[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                double value = matrixData.get(i).get(j);
                // Example:
                //   if value <= 0 => no edge
                //   else => that is the weight
                if (value <= 0.0) {
                    matrix[i][j] = NO_EDGE;
                } else {
                    matrix[i][j] = value;
                }
            }
        }
    }

    /**
     * Adds an undirected edge with a given weight.
     */
    public void addEdge(int src, int dest, double weight) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for addEdge: " + src + " or " + dest);
            return;
        }
        matrix[src][dest] = weight;
        matrix[dest][src] = weight;  // undirected
    }

    /**
     * Removes an edge by setting the weight to NO_EDGE.
     */
    public void removeEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            System.out.println("Invalid vertex index for removeEdge: " + src + " or " + dest);
            return;
        }
        matrix[src][dest] = NO_EDGE;
        matrix[dest][src] = NO_EDGE;
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
     * Check if there's an actual edge (not NO_EDGE).
     */
    public boolean hasEdge(int src, int dest) {
        if (!isValidVertex(src) || !isValidVertex(dest)) {
            return false;
        }
        return matrix[src][dest] != NO_EDGE;
    }

    /**
     * Just an example of adding a vertex (requires resizing the matrix).
     * This is optional for your demonstration; often adjacency matrices are used for fixed V.
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
     * Remove a vertex by rebuilding matrix.
     * If you need it, implement similarly to the unweighted version.
     */
    public void removeVertex(int vertex) {
        throw new UnsupportedOperationException("removeVertex is not implemented in WeightedGraphMatrix demo.");
    }

    /**
     * Print each row.
     * If a cell is NO_EDGE, print something like "∞" (infinity).
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
     * Helper method to check valid vertex index.
     */
    private boolean isValidVertex(int v) {
        return (v >= 0 && v < vertexCount);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
