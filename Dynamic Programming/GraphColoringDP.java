import java.util.Arrays;

public class GraphColoringDP {
    private Graph graph;
    private int numColors;
    private int[] color;
    private int[][] dp;

    public GraphColoringDP(Graph graph, int numColors) {
        this.graph = graph;
        this.numColors = numColors;
        this.color = new int[graph.vertices];
        Arrays.fill(color, -1); // Initialize color array

        // DP table to store results (initialized with -1)
        this.dp = new int[graph.vertices][numColors + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
    }

    public boolean colorGraph() {
        return solve(0, -1);
    }

    private boolean solve(int v, int lastColor) {
        if (v == graph.vertices) {
            return true; // All vertices colored successfully
        }

        if (lastColor != -1 && dp[v][lastColor] != -1) {
            return dp[v][lastColor] == 1; // Return memoized result
        }

        for (int c = 1; c <= numColors; c++) {
            if (isSafe(v, c)) {
                color[v] = c; // Assign color
                if (solve(v + 1, c)) {
                    return true; // If successful, return true
                }
                color[v] = -1; // Backtrack
            }
        }

        if (lastColor != -1) {
            dp[v][lastColor] = 0; // Memoize failure case
        }

        return false;
    }

    private boolean isSafe(int v, int c) {
        for (int neighbor : graph.adjList.get(v)) {
            if (color[neighbor] == c) {
                return false; // Conflict
            }
        }
        return true;
    }

    public void printColorAssignment() {
        for (int v = 0; v < graph.vertices; v++) {
            System.out.println("Vertex " + v + " -> Color " + color[v]);
        }
    }
}
