import java.util.Arrays;
import java.util.Scanner;

public class GraphColoringBacktracking{
    private double executionTime;
    private boolean solutionFound;
    private int V;
    private int[][] graph;                                          // Adjacency matrix
    private int[] colors;

    public GraphColoringBacktracking(int V, int E){
        this.V = V;
        this.colors = new int[V];
        this.graph = new int[V][V];
    }

    private boolean isSafe(int v, int c){
        for (int i = 0; i < V; i++)                                 // Checks neighbor vertex/vertices
            if (graph[v][i] == 1 && colors[i] == c)
                return false;
        return true;
    }

    private boolean solveGraphColoring(int v, int m){
        if(v == V) return true;                                     // All vertices are colored

        // If vertex has no edges, assign the first available color
        boolean isDisconnected = true;
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1) {
                isDisconnected = false;
                break;
            }
        }

        if (isDisconnected) {
            colors[v] = 1; // Assign any color (e.g., 1) to isolated vertices
            return solveGraphColoring(v + 1, m);
        }

        for (int c = 1; c <= m; c++){
            if (isSafe(v, c)){
                colors[v] = c;
                if (solveGraphColoring(v + 1, m)) return true;      
                colors[v] = -1;                                     // Backtracks
            }
        }

        return false;
    }

    public void colorGraph(int m){
        long startTime = System.nanoTime(); 

        if (solveGraphColoring(0, m)){
            solutionFound = true;
            System.out.println("\nSolution Exists: Color Assignment\n");
            for (int i = 0; i < V; i++)
                System.out.println("Vertex " + (i) + " -> Color " + colors[i]);
        }else{
            solutionFound = false;
            System.out.println("\nNo solution exists.");
        }

        long endTime = System.nanoTime();
        executionTime = endTime - startTime / 1e6;
        System.out.println("\nExecution Time: " + (endTime - startTime / 1e6 + " ms"));
        System.out.println("Final Color Array (Backtracking): " + Arrays.toString(colors));
    }

    public static void main(String[] args){
        System.out.print("\n=====================================\n");
        System.out.print("BACKTRACKING ALGORITHM: GRAPH COLORING\n");
        System.out.print("=====================================\n");
        Scanner scn = new Scanner(System.in);
        System.err.print("\nEnter number of vertices: ");
        int V = scn.nextInt();

        System.out.print("Enter number of edges: ");
        int E = scn.nextInt();
        GraphColoringBacktracking graphColoring = new GraphColoringBacktracking(V, E);
        System.out.println("\nEnter edges (u, v): ");
        for (int i = 0; i < E; i++){
            int u = scn.nextInt();
            int v = scn.nextInt();
            graphColoring.graph[u][v] = graphColoring.graph[v][u] = 1;
        }

        
        System.out.println("\nEnter number of colors: ");
        int m = scn.nextInt();

        System.out.println("\nList of edges: ");
        for (int i = 0; i < V; i++){
            for (int j = 0; j < graphColoring.graph.length; j++)
            if ((graphColoring.graph[i][j] & graphColoring.graph[j][i]) == 1)
                System.out.print("{" + i + "," + j + "},");
        }
        System.out.println();

        graphColoring.colorGraph(m);
        scn.close();
    }

    public int[][] getGraph() {
        return graph;
    }

    public int[] getColors() {
        return colors;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

}