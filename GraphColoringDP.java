import java.util.*;

public class GraphColoringDP {
    private int vertices;
    private List<List<Integer>> graph;
    private int[] colorArray;
    private int[][] dp;
    private int numColors;

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

    public void printGraph() {
        System.out.println("\nList of edges:");
        for (int i = 0; i < vertices; i++) {
            for (int j : graph.get(i)) {
                if (i < j) {                                        // Print each edge only once
                    System.out.print("{" + i + "," + j + "}, ");
                }
            }
        }
        System.out.println();
    }

    public boolean colorGraph() {
        long startTime = System.nanoTime();
        boolean result = solve(0, -1);
        long endTime = System.nanoTime();
        
        if (result) {
            System.out.println("\nSolution Exists: Color Assignment");
            for (int i = 0; i < vertices; i++) {
                System.out.println("Vertex " + i + " -> Color " + colorArray[i]);
            }
        } else {
            System.out.println("\nNo solution exists with given number of colors.");
        }
        
        System.out.printf("\nExecution Time: %.3f ms%n", (endTime - startTime) / 1e6);
        return result;
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
                if (solve(vertex + 1, c)) {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n=====================================");
        System.out.println("GRAPH COLORING WITH DYNAMIC PROGRAMMING");
        System.out.println("=====================================");

        // Get number of vertices
        System.out.print("\nEnter number of vertices: ");
        int V = scanner.nextInt();

        // Get number of edges
        System.out.print("Enter number of edges: ");
        int E = scanner.nextInt();

        // Get number of colors
        System.out.print("Enter number of colors: ");
        int numColors = scanner.nextInt();

        // Create graph
        GraphColoringDP gc = new GraphColoringDP(V, numColors);

        // Get edges
        System.out.println("\nEnter edges (u v) one per line:");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            
            // Validate input
            if (u >= 0 && u < V && v >= 0 && v < V) {
                gc.addEdge(u, v);
            } else {
                System.out.println("Invalid edge! Vertices should be between 0 and " + (V-1));
                i--; // Retry this edge
            }
        }

        // Print the graph
        gc.printGraph();

        // Color the graph
        gc.colorGraph();
        
        scanner.close();
    }
}