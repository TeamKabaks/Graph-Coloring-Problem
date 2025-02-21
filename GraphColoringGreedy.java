import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Graph {
    private final int vertices;
    private final List<List<Integer>> adjList;
    private final List<Edge> edges;
    private boolean solutionFound;
    private double executionTime;
    private int[] colors;

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
        solutionFound = true; // assume solution is found
        colors = new int[vertices];
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
            boolean foundColor = false;
            for (int c = 0; c < m; c++) {
                if (available[c]) {
                    colors[v] = c;
                    foundColor = true;
                    break;
                }
            }

            if (!foundColor) { // If no valid color is found for this vertex
                solutionFound = false;
                System.out.println("\nNo solution exists.\n");
                break;
            }
        }

        long endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1e6; // Convert to milliseconds

        // Print the coloring solution

        if(solutionFound){
            System.out.println("Solution Exists: Color Assignment");
            for (int v = 0; v < vertices; v++) {
                System.out.println("Vertex " + v + " -> Color " + (colors[v] + 1));
            }
        }
        System.out.printf("Execution Time: %.5f ms\n", executionTime);
        System.out.println("Final Color Array (Greedy): " + Arrays.toString(colors));
    }

    public int[] getColors() {
        return colors;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    
}

class Edge {
    int src, dest;

    public Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "{" + src + ", " + dest + "}";
    }
}


public class GraphColoringGreedy {
    private Graph graph;

    public GraphColoringGreedy(int vertices) {
        this.graph = new Graph(vertices);
    }

    public void addEdge(int u, int v) {
        graph.addEdge(u, v);
    }

    public void greedyColoring(int m) {
        graph.greedyColoring(m);
    }

    public int[] getColors() {
        return graph.getColors();
    }

    public double getExecutionTime() {
        return graph.getExecutionTime();
    }

    public boolean isSolutionFound(){
        return graph.isSolutionFound();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of vertices
        System.out.print("Enter number of vertices: ");
        int vertices = scanner.nextInt();

        // Input number of edges
        System.out.print("Enter number of edges: ");
        int edges = scanner.nextInt();

        Graph graph = new Graph(vertices);

        // Input edges
        System.out.println("Enter edges (u, v): "); 
        for (int i = 0; i < edges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }

        // Input number of colors
        System.out.print("Enter number of colors: ");
        int m = scanner.nextInt();

        // Print the edges
        graph.printEdges();

        // Run greedy graph coloring
        graph.greedyColoring(m);

        scanner.close();
    }
}
