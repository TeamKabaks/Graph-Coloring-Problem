import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

class GraphPanel extends JPanel {
    private Map<String, Point> nodes; //Stores node labels and their positions
    private List<String[]> edges; //Stores edges as pairs of node labels
    
    public GraphPanel() {
        nodes = new HashMap<>(); 
        edges = new ArrayList<>();
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
        g.setColor(Color.BLACK);
        
        //Draw edges (lines connecting nodes)
        for (String[] edge : edges) {
            Point p1 = nodes.get(edge[0]); //Get position of first node
            Point p2 = nodes.get(edge[1]); //Get position of second node
            if(p1 != null && p2 != null) g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        
        //Draw nodes (circles) and labels
        for (Map.Entry<String, Point> entry : nodes.entrySet()) {
            Point p = entry.getValue();
            g.fillOval(p.x - 10, p.y - 10, 20, 20); //Draw node as a circle
            g.setColor(Color.WHITE);
            g.drawString(entry.getKey(), p.x - 5, p.y + 5); //Draw node label
            g.setColor(Color.BLACK);
        }
    }
}

//Main class to create and display the GUI for the graph visualization
public class GraphGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Create the main application window
            JFrame frame = new JFrame("Graph Coloring Problem");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            
            GraphPanel graphPanel = new GraphPanel(); //Create graph panel to display the graph
            JTextField edgeInput = new JTextField(20); //Text field for user to enter edges
            JButton drawButton = new JButton("Draw Graph"); //Button to trigger graph drawing
            
            //Action listener that processes the user input and draws the graph
            ActionListener drawGraphAction = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Map<String, Point> nodes = new HashMap<>();
                    Set<String> uniqueEdges = new HashSet<>(); //Avoid duplicate edges
                    Set<String> uniqueNodes = new HashSet<>(); //Store unique node labels

                    //Read user input and split by semicolon
                    String[] edgePairs = edgeInput.getText().split("; ?");
                    for (String pair : edgePairs) {
                        String[] nodesPair = pair.split(",");
                        if (nodesPair.length == 2) {
                            //Split each pair by comma to get nodes
                            String nodeA = nodesPair[0].trim();
                            String nodeB = nodesPair[1].trim();
                            //Ensure no self-loops (e.g., "A,A" is ignored)
                            if (!nodeA.equals(nodeB)) {
                                //Store edges in sorted order to prevent duplicates
                                String edgeKey = (nodeA.compareTo(nodeB) < 0) ? nodeA + "," + nodeB : nodeB + "," + nodeA;
                                uniqueEdges.add(edgeKey);
                                uniqueNodes.add(nodeA);
                                uniqueNodes.add(nodeB);
                            }
                        }
                    }

                    //Convert unique edge set into a list of string arrays
                    List<String[]> edges = new ArrayList<>();
                    for (String edge : uniqueEdges) {
                        edges.add(edge.split(","));
                    }
                    
                    List<String> nodeList = new ArrayList<>(uniqueNodes);   //Convert unique nodes into a list and assign positions in a circular layout
                    nodes = generatePolygonPoints(nodeList, 200, 200, 150);
                    graphPanel.setGraph(nodes, edges); //Update the graph panel with new nodes and edges
                }
            };

            //Attach the same action listener to both button click and 'Enter' key press
            drawButton.addActionListener(drawGraphAction);
            edgeInput.addActionListener(drawGraphAction);
            
            //Create panel with input field and button
            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Edges (A,B; B,C; D,A):"));
            controlPanel.add(edgeInput);
            controlPanel.add(drawButton);
            
            //Set layout and add components to the frame
            frame.setLayout(new BorderLayout());
            frame.add(controlPanel, BorderLayout.NORTH);
            frame.add(graphPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
    
    //Generates positions for nodes in a circular pattern
    private static Map<String, Point> generatePolygonPoints(List<String> nodeLabels, int centerX, int centerY, int radius) {
        Map<String, Point> positions = new HashMap<>();
        int n = nodeLabels.size();
        for (int i = 0; i < n; i++) {
            //Calculate angle for evenly spaced nodes
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            positions.put(nodeLabels.get(i), new Point(x, y));
        }
        return positions;
    }
}
