import java.util.Scanner;

public class Main {
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
