import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Graph {
    private final int vertices;
    private final List<List<Integer>> adjList;
    private final List<Edge> edges;
    private boolean solutionFound;
    private double executionTime;
    private int[] colors;
    public int minColors;

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

    
    public void greedyColoring(int m) {
        long startTime = System.nanoTime();
        solutionFound = true; // Assume solution is found
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
    
        // Print the minimum number of colors used
        if (solutionFound) {
            minColors = 0;
            for (int color : colors) {
                if (color + 1 > minColors) { // +1 to adjust for 0-based indexing
                    minColors = color + 1;
                }
            }
            System.out.println("\nMinimum Colors Used: " + minColors);
        } else {
            System.out.println("No solution exists.");
        }
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

    public int getMinimumColors(){
        return graph.minColors;
    }

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
}