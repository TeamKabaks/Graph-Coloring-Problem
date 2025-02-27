import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

class GraphPanel extends JPanel {
    private Map<String, Point> nodes; //Stores node labels and their positions
    private List<String[]> edges; //Stores edges as pairs of node labels
    private Map<String, Integer> nodeColors; //Stores color values of each nodes
    
    public GraphPanel() {
        nodes = new HashMap<>(); 
        edges = new ArrayList<>();
        nodeColors = new HashMap<>();
        setBackground(Color.RED);
    }
    
    //Sets the graph nodes and edges, then repaints the panel
    public void setGraph(Map<String, Point> nodes, List<String[]> edges) {
        this.nodes = nodes;
        this.edges = edges;
        repaint(); //Repaint the panel to reflect changes
    }
    
    //Paints the graph with nodes and edges
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        
        //Draw edges (lines connecting nodes)
        for (String[] edge : edges) {
            Point p1 = nodes.get(edge[0]); //Get position of first node
            Point p2 = nodes.get(edge[1]); //Get position of second node
            if(p1 != null && p2 != null) g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        
        for (Map.Entry<String, Point> entry : nodes.entrySet()) {
            Point p = entry.getValue();
            String node = entry.getKey();
            int colorIndex = nodeColors.getOrDefault(node, -1);
    
            Color nodeColor = (colorIndex == -1) ? Color.GRAY : getColorFromIndex(colorIndex);
            g.setColor(nodeColor);
            g.fillOval(p.x - 15, p.y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(node, p.x - 5, p.y + 5);
            g.setColor(Color.WHITE);
        }
    }

    private Color getColorFromIndex(int index) {
    Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA};
        return colors[index % colors.length];
    }   

    public void setNodeColors(int[] colorArray) {
        //System.out.println("Received Color Array: " + Arrays.toString(colorArray)); // Debugging
    
        boolean hasInvalidSolution = false;
        for (int color : colorArray) {
            if (color == -1) { // If at least one node remains uncolored, solution failed
                hasInvalidSolution = true;
                break;
            }
        }
    
        nodeColors.clear(); // Clear previous colors before setting new ones
    
        if (hasInvalidSolution) {
            System.out.println("Invalid solution detected! Coloring all nodes gray.");
            for (String node : nodes.keySet()) {
                nodeColors.put(node, -1); // Store -1 to indicate gray
            }
        } else {
            Map<Integer, Integer> colorMapping = new HashMap<>();
            int newIndex = 0;
            
            // Normalize color index since the algorithms use different base indexing
            for (int color : colorArray) {
                if (!colorMapping.containsKey(color)) {
                    colorMapping.put(color, newIndex++); // Assign new normalized index
                }
            }
            
            int index = 0;
            for (String node : nodes.keySet()) {
                int originalColor = colorArray[index++];
                int normalizedColor = colorMapping.getOrDefault(originalColor, -1); // Default to -1 if missing
                nodeColors.put(node, normalizedColor);
                //System.out.println("Node: " + node + ", Original Color: " + originalColor + ", Normalized Color: " + normalizedColor);
            }
        }
    
        repaint();
    }

    public Map<String, Point> getNodes() {
        return nodes;
    }

    public List<String[]> getEdges() {
        return edges;
    }
}