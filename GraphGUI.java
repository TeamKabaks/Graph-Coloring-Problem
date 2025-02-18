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
    private Map<String, Point> nodes;
    private List<String[]> edges;
    
    public GraphPanel() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }
    
    public void setGraph(Map<String, Point> nodes, List<String[]> edges) {
        this.nodes = nodes;
        this.edges = edges;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        
        // Draw edges
        for (String[] edge : edges) {
            Point p1 = nodes.get(edge[0]);
            Point p2 = nodes.get(edge[1]);
            if (p1 != null && p2 != null) {
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        
        // Draw nodes
        g.setColor(Color.BLUE);
        for (Map.Entry<String, Point> entry : nodes.entrySet()) {
            Point p = entry.getValue();
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString(entry.getKey(), p.x - 5, p.y + 5);
            g.setColor(Color.BLUE);
        }
    }
}

public class GraphGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph COLPRING");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            
            GraphPanel graphPanel = new GraphPanel();
            JTextField edgeInput = new JTextField(20);
            JButton drawButton = new JButton("Draw Graph");
            
            drawButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Map<String, Point> nodes = new HashMap<>();
                    List<String[]> edges = new ArrayList<>();
                    
                    String[] edgePairs = edgeInput.getText().split("; ?");
                    Set<String> uniqueNodes = new HashSet<>();
                    for (String pair : edgePairs) {
                        String[] nodesPair = pair.split(",");
                        if (nodesPair.length == 2) {
                            uniqueNodes.add(nodesPair[0]);
                            uniqueNodes.add(nodesPair[1]);
                            edges.add(new String[]{nodesPair[0], nodesPair[1]});
                        }
                    }
                    
                    List<String> nodeList = new ArrayList<>(uniqueNodes);
                    nodes = generatePolygonPoints(nodeList, 200, 200, 150);
                    graphPanel.setGraph(nodes, edges);
                }
            });
            
            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Edges (A,B; B,C; D,A):"));
            controlPanel.add(edgeInput);
            controlPanel.add(drawButton);
            
            frame.setLayout(new BorderLayout());
            frame.add(controlPanel, BorderLayout.NORTH);
            frame.add(graphPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
    
    private static Map<String, Point> generatePolygonPoints(List<String> nodeLabels, int centerX, int centerY, int radius) {
        Map<String, Point> positions = new HashMap<>();
        int n = nodeLabels.size();
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            positions.put(nodeLabels.get(i), new Point(x, y));
        }
        return positions;
    }
}
