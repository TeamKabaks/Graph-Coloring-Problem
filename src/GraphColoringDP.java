import java.util.*;

public class GraphColoringDP {
    private boolean solutionFound;
    private double executionTime;
    private int vertices;
    private List<List<Integer>> graph;
    private int[] colorArray;
    private int[][] dp;
    private int numColors;
    public int minColors;

    public GraphColoringDP(int vertices, int numColors) {
        this.vertices = vertices;
        this.numColors = numColors;
        this.graph = new ArrayList<>(vertices);
        this.colorArray = new int[vertices];
        this.dp = new int[vertices][numColors + 1];

        // Initialize the adjacency list
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        // Initialize colorArray with -1 (unassigned)
        Arrays.fill(colorArray, -1);

        // Initialize dp table with -1
        for (int i = 0; i < vertices; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    public void addEdge(int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    public boolean colorGraph() {
        long startTime = System.nanoTime();
        boolean result = solve(0, -1);
        long endTime = System.nanoTime();

        if (result) {
            solutionFound = true;

            // Calculate the minimum number of colors used
            minColors = 0;
            for (int color : colorArray) {
                if (color > minColors) {
                    minColors = color;
                }
            }
            System.out.println("\nMinimum Colors Used: " + minColors);
        } else {
            solutionFound = false;
            System.out.println("\nNo solution exists with given number of colors.");
        }

        executionTime = (endTime - startTime) / 1e6;
        return result;
    }

    public int getMinimumColors() {
        return minColors;
    }

    private boolean solve(int vertex, int lastColor) {
        if (vertex == vertices) {
            return true;
        }

        if (lastColor != -1 && dp[vertex][lastColor] != -1) {
            return dp[vertex][lastColor] == 1;
        }

        for (int c = 1; c <= numColors; c++) {
            if (isSafe(vertex, c)) {
                colorArray[vertex] = c;
                minColors = Math.max(minColors, c);

                if (solve(vertex + 1, c)) {
                    if (lastColor != -1) dp[vertex][lastColor] = 1;
                    return true;
                }

                colorArray[vertex] = -1; // Backtrack
            }
        }

        if (lastColor != -1) {
            dp[vertex][lastColor] = 0;
        }

        return false;
    }

    private boolean isSafe(int vertex, int color) {
        for (int neighbor : graph.get(vertex)) {
            if (colorArray[neighbor] == color) {
                return false;
            }
        }
        return true;
    }

    public int[] getColorArray() {
        return colorArray;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }
}