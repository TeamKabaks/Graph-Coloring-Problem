import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

public class ButtonOperations implements ActionListener {
    private JButton drawBtn, backtrackingBtn, greedyBtn, dynamicBtn;
    private JLabel executionTimeLabel, algoLabel, statusLabel;
    private JTextField edgeInput, vertexInput, colorsInput;
    private Map<String, Integer> nodeMapping;
    private List<String[]> edges;
    private int numVertices, numEdges, numColors;
    private GraphPanel graphPanel;

    public ButtonOperations() {
        initializeGUIElements();
        addGUIListeners();
    }

    private void initializeGUIElements() {
        graphPanel = new GraphPanel();
        backtrackingBtn = new JButton("Backtracking Algorithm");
        greedyBtn = new JButton("Greedy Algorithm");
        dynamicBtn = new JButton("Dynamic Programming");
        drawBtn = new JButton("Draw Graph");
        edgeInput = new JTextField(20); 
        vertexInput = new JTextField(10);
        colorsInput = new JTextField(5);
        executionTimeLabel = new JLabel("");
        algoLabel = new JLabel("");
        statusLabel = new JLabel("Status: ");
    }

    private void addGUIListeners() {
        backtrackingBtn.addActionListener(this);
        greedyBtn.addActionListener(this);
        dynamicBtn.addActionListener(this);
    }
    
    public void backtrackingBtnPressed() {
        System.out.print("\n=====================================\n");
        System.out.print("BACKTRACKING ALGORITHM: GRAPH COLORING\n");
        System.out.print("=====================================\n");
        // Create graph
        GraphColoringBacktracking graphColoring = new GraphColoringBacktracking(numVertices, numEdges);
        // Get edges
        for (String[] edge : edges) {
            int u = nodeMapping.get(edge[0]);
            int v = nodeMapping.get(edge[1]);
            graphColoring.getGraph()[u][v] = graphColoring.getGraph()[v][u] = 1;
        }

        graphColoring.colorGraph(numColors);
        int[] colorArray = graphColoring.getColors();
        graphPanel.setNodeColors(colorArray);
        setAlgoLabel("Backtracking Algorithm");
        setExecutionTimeLabel(graphColoring.getExecutionTime());
        setStatusLabel(graphColoring.isSolutionFound());
    }

    public void greedyBtnPressed() {
        System.out.println("\n=====================================");
        System.out.println("GREEDY ALGORITHM: GRAPH COLORING");
        System.out.println("=====================================");

        // Create graph
        GraphColoringGreedy graphColoring = new GraphColoringGreedy(numVertices);
        // Get edges
        
        for (String[] edge : edges) {
            int u = nodeMapping.get(edge[0]);
            int v = nodeMapping.get(edge[1]);
            graphColoring.addEdge(u, v);
        }
    
        graphColoring.greedyColoring(numColors);

        // Retrieve and apply colors to GraphPanel
        int[] colorArray = graphColoring.getColors();
        graphPanel.setNodeColors(colorArray);
        setAlgoLabel("Greedy Algorithm");
        setExecutionTimeLabel(graphColoring.getExecutionTime());
        setStatusLabel(graphColoring.isSolutionFound());
    }

    public void dynamicBtnPressed() {
        System.out.println("\n=====================================");
        System.out.println("DYNAMIC PROGRAMMING: GRAPH COLORING");
        System.out.println("=====================================");

        // Create graph
        GraphColoringDP gc = new GraphColoringDP(numVertices, numColors);

        // Get edges
        for (String[] edge : edges) {
            int u = nodeMapping.get(edge[0]);
            int v = nodeMapping.get(edge[1]);
            gc.addEdge(u, v);
        }

        gc.colorGraph();
        int[] colorArray = gc.getColorArray();
        graphPanel.setNodeColors(colorArray);
        setAlgoLabel("Dynamic Programming Algorithm");
        setExecutionTimeLabel(gc.getExecutionTime());
        setStatusLabel(gc.isSolutionFound());
    }

    public void graphGUI(){            
        //Action listener that processes the user input and draws the graph
        ActionListener drawGraphAction = (ActionEvent e) -> {
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
            
            String[] singleNodes = vertexInput.getText().split("; ?");
            for (String node : singleNodes) {
                String trimmedNode = node.trim();
                if (!trimmedNode.isEmpty()) {
                    uniqueNodes.add(trimmedNode);
                }
            }
            
            //Convert unique edge set into a list of string arrays
            List<String[]> edges = new ArrayList<>();
            for (String edge : uniqueEdges) {
                edges.add(edge.split(","));
            }
            
            List<String> nodeList = new ArrayList<>(uniqueNodes);   //Convert unique nodes into a list and assign positions in a circular layout
            nodes = generatePolygonPoints(nodeList, (graphPanel.getSize().width)/2, (graphPanel.getSize().height)/2, 150);
            graphPanel.setGraph(nodes, edges); //Update the graph panel with new nodes and edges
        };

        //Attach the same action listener to both button click and 'Enter' key press
        drawBtn.addActionListener(drawGraphAction);
        edgeInput.addActionListener(drawGraphAction);
        vertexInput.addActionListener(drawGraphAction);
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
    
    private void extractGraphData() {
        nodeMapping = new HashMap<>();
        edges = new ArrayList<>(graphPanel.getEdges());
        int index = 0;
        // Get number of colors
        numColors = colorsInput.getText().isEmpty() ? 0 : Integer.parseInt(colorsInput.getText());
        // Get number of edges
        numEdges = edges.size();
    
        // Map node labels to numerical indices
        for (String node : graphPanel.getNodes().keySet()) {
            nodeMapping.putIfAbsent(node, index++);
        }
        // Get number of vertices
        numVertices = nodeMapping.size();
    }

    private void setStatusLabel(boolean status){
        if(numVertices > 0){
            if(status){
                statusLabel.setText("Status: Solution Found!");
            }else{
                statusLabel.setText("Status: No Solution Found!");
            }
        }else if(numVertices<=0){
            statusLabel.setText("Status: Error Empty Graph!");
        }
    }

    private void setExecutionTimeLabel(double executionTime){
        if(numVertices > 0){
            executionTimeLabel.setText("Execution Time: " + executionTime + "ms");
        }else if(numVertices<=0){
            executionTimeLabel.setText("");
        }
    }

    private void setAlgoLabel(String algorithm){
        if(numVertices > 0){
            algoLabel.setText("Algorithm: " + algorithm);
        }else if(numVertices<=0){
            algoLabel.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        extractGraphData();
        if (e.getSource() == backtrackingBtn) {
            backtrackingBtnPressed();
        } else if (e.getSource() == greedyBtn) {
            greedyBtnPressed();
        } else if (e.getSource() == dynamicBtn) {
            dynamicBtnPressed();
        }
    }

    // Getters for buttons
    public JButton getBacktrackingBtn() {
        return backtrackingBtn;
    }

    public JButton getGreedyBtn() {
        return greedyBtn;
    }

    public JButton getDynamicBtn() {
        return dynamicBtn;
    }

    public JButton getDrawBtn() {
        return drawBtn;
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public JTextField getEdgeInput() {
        return edgeInput;
    }

    public JTextField getVertexInput() {
        return vertexInput;
    }

    public JTextField getColorsInput() {
        return colorsInput;
    }

    public JLabel getExecutionTimeLabel() {
        return executionTimeLabel;
    }

    public void setExecutionTimeLabel(JLabel executionTimeLabel) {
        this.executionTimeLabel = executionTimeLabel;
    }

    public JLabel getAlgoLabel() {
        return algoLabel;
    }

    public void setAlgoLabel(JLabel algoLabel) {
        this.algoLabel = algoLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
}