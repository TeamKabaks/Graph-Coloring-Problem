import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphParser {

    public static void parseAndPrint(String input) {
        String fileName = "";
        int numVertices = 0;
        int numEdges = 0;
        Set<Integer> vertices = new TreeSet<>();
        List<String> edges = new ArrayList<>();

        String[] lines = input.split("\\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("c FILE:")) {
                fileName = line.substring(7).replace(".col", "").trim();
            } else if (line.startsWith("p edge")) {
                String[] parts = line.split(" ");
                if (parts.length >= 4) {
                    numVertices = Integer.parseInt(parts[2]);
                    numEdges = Integer.parseInt(parts[3]);
                }
            } else if (line.startsWith("e ")) {
                String[] parts = line.substring(2).split(" ");
                if (parts.length == 2) {
                    int v1 = Integer.parseInt(parts[0]);
                    int v2 = Integer.parseInt(parts[1]);
                    vertices.add(v1);
                    vertices.add(v2);
                    edges.add(v1 + "," + v2);
                }
            }
        }

        // Write parsed data to a text file
        try (FileWriter writer = new FileWriter(fileName + "_parsed.txt")) {
            writer.write("File name: " + fileName + "\n");
            writer.write("Number of vertices: " + numVertices + "\n");
            writer.write("Number of edges: " + numEdges + "\n");

            writer.write("Vertices: ");
            Iterator<Integer> vertexIterator = vertices.iterator();
            while (vertexIterator.hasNext()) {
                writer.write(vertexIterator.next().toString());
                if (vertexIterator.hasNext()) writer.write("; ");
            }
            writer.write("\n");

            writer.write("Edges: ");
            Iterator<String> edgeIterator = edges.iterator();
            while (edgeIterator.hasNext()) {
                writer.write(edgeIterator.next());
                if (edgeIterator.hasNext()) writer.write("; ");
            }
            writer.write("\n");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
