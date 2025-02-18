import java.util.*;

public class Graph {
    int vertices;
    List<List<Integer>> adjList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u); // Undirected graph
    }

    public void printEdges() {
        for (int u = 0; u < vertices; u++) {
            for (int v : adjList.get(u)) {
                if (u < v) // Avoid printing duplicate edges
                    System.out.println("{" + u + ", " + v + "}");
            }
        }
    }
}
