import java.util.*;

class Graph {
    private boolean solutionFound;
    private final int vertices;
    private final List<List<Integer>> adjList;
    private double executionTime;
    private int[] colors;
    public int minColors;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Add edge to graph
    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    // Greedy Coloring Algorithm
    public void greedyColoring() {
        long startTime = System.nanoTime();
        colors = new int[vertices];
        Arrays.fill(colors, -1); // Initialize all vertices as uncolored

        // Sort vertices by degree in descending order (Welsh-Powell heuristic)
        Integer[] vertexOrder = new Integer[vertices];
        for (int i = 0; i < vertices; i++) vertexOrder[i] = i;

        Arrays.sort(vertexOrder, (a, b) -> adjList.get(b).size() - adjList.get(a).size());

        // Assign colors to vertices
        for (int v : vertexOrder) {
            boolean[] available = new boolean[vertices];
            Arrays.fill(available, true); // All colors available initially

            // Check colors of adjacent vertices
            for (int neighbor : adjList.get(v)) {
                if (colors[neighbor] != -1) {
                    available[colors[neighbor]] = false;
                }
            }

            // Assign the smallest available color
            for (int c = 0; c < vertices; c++) {
                if (available[c]) {
                    colors[v] = c;
                    minColors = Math.max(minColors, c + 1); // Track max color used
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1e6; // Convert to milliseconds
        System.out.println("\nMinimum Colors Used: " + minColors);
    }

    public int[] getColors() {
        return colors;
    }

    public double getExecutionTime() {
        return executionTime;
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

    public void greedyColoring() {
        graph.greedyColoring();
    }

    public int getMinimumColors() {
        return graph.minColors;
    }

    public int[] getColors() {
        return graph.getColors();
    }

    public double getExecutionTime() {
        return graph.getExecutionTime();
    }
}
