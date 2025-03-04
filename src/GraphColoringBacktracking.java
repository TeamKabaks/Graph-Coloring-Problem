import java.util.Arrays;

public class GraphColoringBacktracking{
    public double executionTime;
    private boolean solutionFound;
    private int V;
    private int[][] graph;                               // Adjacency matrix
    private int[] colors;
    public int minColors;

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

    public void colorGraph(int m) {
        long startTime = System.nanoTime(); 
    
        // Reset colors array before starting
        Arrays.fill(colors, -1);  
    
        if (solveGraphColoring(0, m)) {
            solutionFound = true;
    
            // Find the maximum color used
            minColors = 0;
            for (int i = 0; i < V; i++) {
                if (colors[i] > minColors) {
                    minColors = colors[i];
                }
            }
    
            System.out.println("\nMinimum Colors Used: " + minColors);
        } else {
            solutionFound = false;
            System.out.println("\nNo solution exists.");
        }
    
        long endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1e6;  // Convert to ms
    }
    

    public int getMinimumColors(){
        return minColors;
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