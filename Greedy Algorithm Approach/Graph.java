import java.util.*;

public class Graph {
    private final int vertices;
    private final List<List<Integer>> adjList;
    private final List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        this.edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Add edge to graph
    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
        edges.add(new Edge(u, v));
    }

    // Print the edges
    public void printEdges() {
        System.out.println("\nList of edges:");
        for (Edge edge : edges) {
            System.out.print(edge + " ");
        }
        System.out.println("\n");
    }

    // Greedy Graph Coloring Algorithm
    public void greedyColoring(int m) {
        long startTime = System.nanoTime();

        int[] colors = new int[vertices];
        Arrays.fill(colors, -1); // Initialize all vertices as uncolored

        // Assign colors to vertices one by one
        for (int v = 0; v < vertices; v++) {
            boolean[] available = new boolean[m];
            Arrays.fill(available, true); // All colors are available initially

            // Check colors of adjacent vertices
            for (int neighbor : adjList.get(v)) {
                if (colors[neighbor] != -1) {
                    available[colors[neighbor]] = false;
                }
            }

            // Assign the smallest available color
            for (int c = 0; c < m; c++) {
                if (available[c]) {
                    colors[v] = c;
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1e6; // Convert to milliseconds

        // Print the coloring solution
        System.out.println("Solution Exists: Color Assignment");
        for (int v = 0; v < vertices; v++) {
            System.out.println("Vertex " + v + " -> Color " + (colors[v] + 1));
        }
        System.out.printf("Execution Time: %.5f ms\n", executionTime);
    }
}
