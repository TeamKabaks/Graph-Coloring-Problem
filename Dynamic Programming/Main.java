import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for number of vertices and edges
        System.out.print("Enter number of vertices: ");
        int vertices = scanner.nextInt();
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
        int colors = scanner.nextInt();

        // Display edges
        System.out.println("\nList of edges:");
        graph.printEdges();

        // Start timing the execution
        Timer.start();

        // Solve graph coloring
        GraphColoringDP solver = new GraphColoringDP(graph, colors);
        boolean solutionExists = solver.colorGraph();

        // Stop timing
        Timer.stop();

        // Display results
        if (solutionExists) {
            System.out.println("\nSolution Exists: Color Assignment");
            solver.printColorAssignment();
        } else {
            System.out.println("\nNo solution exists with given number of colors.");
        }

        // Display execution time
        System.out.printf("Execution Time: %.5f seconds\n", Timer.getTime());
        
        scanner.close();
    }
}
