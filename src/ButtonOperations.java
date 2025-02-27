import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;

public class ButtonOperations implements ActionListener {
    private JButton drawBtn, importBtn, backtrackingBtn, greedyBtn, dynamicBtn;
    private JLabel executionTimeLabel, algoLabel, statusLabel, minColorLabel, numVerticesLabel, numEdgesLabel;
    private JTextField edgeInput, vertexInput, colorsInput;
    private Map<String, Integer> nodeMapping;
    private List<String[]> edges;
    private int numVertices, numEdges, numColors;
    private GraphPanel graphPanel;
    public double baRuntime;
    public double gaRuntime;
    public double dpRuntime;

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
        importBtn = new JButton("Import Graphs");
        edgeInput = new JTextField(10); 
        vertexInput = new JTextField(10);
        colorsInput = new JTextField(5);
        executionTimeLabel = new JLabel("");
        algoLabel = new JLabel("");
        statusLabel = new JLabel("Status: ");
        minColorLabel = new JLabel("Minimum Color/s Used: ");
        numVerticesLabel = new JLabel("No. of Vertices: ");
        numEdgesLabel = new JLabel("No. of getEdges: ");
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
        setMinColorLabel(graphColoring.getMinimumColors());
        setNumVerticesLabel();
        setNumEdgesLabel();
        System.out.println("Execution Time: " + graphColoring.getExecutionTime());
        baRuntime = 0;
        baRuntime = graphColoring.getExecutionTime();
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
        setMinColorLabel(graphColoring.getMinimumColors());
        setNumVerticesLabel();
        setNumEdgesLabel();
        System.out.println("Execution Time: " + graphColoring.getExecutionTime());
        gaRuntime = 0;
        gaRuntime = graphColoring.getExecutionTime();
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
        setMinColorLabel(gc.getMinimumColors());
        setNumVerticesLabel();
        setNumEdgesLabel();
        System.out.println("Execution Time: " + gc.getExecutionTime());
        dpRuntime = 0;
        dpRuntime = gc.getExecutionTime();
        System.out.println("==============================================================================");
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
        drawBtn.addActionListener(drawGraphAction);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Select Graph Data in .col File Format");
        
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    processColFiles(selectedFiles, selectedFiles.length);
        
                    // Show confirmation message after processing
                    JOptionPane.showMessageDialog(null, "Graph Coloring Data Saved to CSV", 
                                                  "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

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

    private void setMinColorLabel(int minColor){
        if(numVertices > 0){
            minColorLabel.setText("Minimum Color/s used: " + minColor);
        }else if(numVertices <= 0){
            minColorLabel.setText("");
        }
    }

    private void setNumVerticesLabel(){
        if(numVertices > 0){
            numVerticesLabel.setText("No. of Vertices: " + numVertices);
        }else if(numVertices <= 0){
            numVerticesLabel.setText("");
        }
    }

    private void setNumEdgesLabel(){
        if(numEdges > 0){
            numEdgesLabel.setText("No. of Edges: " + numEdges);
        }else if(numEdges <= 0){
            numEdgesLabel.setText("");
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

    public void processColFiles(File[] selectedFiles, int numFiles) {
        String csvHeader = "File Name,No. of Vertices,No. of Edges,BA Chromatic Number,BA Runtime (ms),GA Chromatic Number,GA Runtime (ms),DP Chromatic Number,DP Runtime (ms)\n";
        File csvFile = new File("graph_coloring_results.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(csvHeader);

            for (File file : selectedFiles) {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            
                // ===== Parse .col File =====
                String fileName = file.getName().replace(".col", "");
                int numVertices = 0, numEdges = 0;
                Set<Integer> vertices = new TreeSet<>();
                List<String[]> edgesList = new ArrayList<>();

                String[] lines = content.split("\\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.startsWith("p edge")) {
                        String[] parts = line.split(" ");
                        numVertices = Integer.parseInt(parts[2]);
                        numEdges = Integer.parseInt(parts[3]);
                    } else if (line.startsWith("e ")) {
                        String[] parts = line.substring(2).split(" ");
                        int v1 = Integer.parseInt(parts[0]);
                        int v2 = Integer.parseInt(parts[1]);
                        vertices.add(v1);
                        vertices.add(v2);
                        edgesList.add(new String[]{String.valueOf(v1), String.valueOf(v2)});
                    }
                }
        
                // ===== Run Backtracking Algorithm =====
                GraphColoringBacktracking ba = new GraphColoringBacktracking(numVertices, numEdges);
            
                for (String[] edge : edgesList) {
                    ba.getGraph()[Integer.parseInt(edge[0]) - 1][Integer.parseInt(edge[1]) - 1] = 1;
                    ba.getGraph()[Integer.parseInt(edge[1]) - 1][Integer.parseInt(edge[0]) - 1] = 1;
                }
                ba.colorGraph(numVertices);
                baRuntime = ba.getExecutionTime();
                int baChromatic = ba.getMinimumColors();
        
                // ===== Run Greedy Algorithm =====
                GraphColoringGreedy ga = new GraphColoringGreedy(numVertices);
                for (String[] edge : edgesList) {
                    ga.addEdge(Integer.parseInt(edge[0]) - 1, Integer.parseInt(edge[1]) - 1);
                }
                ga.greedyColoring(numVertices);
                gaRuntime = ga.getExecutionTime();
                int gaChromatic = ga.getMinimumColors();

                
                // ===== Run Dynamic Programming =====
                GraphColoringDP dp = new GraphColoringDP(numVertices, numVertices);
                for (String[] edge : edgesList) {
                    dp.addEdge(Integer.parseInt(edge[0]) - 1, Integer.parseInt(edge[1]) - 1);
                }
                dp.colorGraph();
                dpRuntime = dp.getExecutionTime();
                int dpChromatic = dp.getMinimumColors();

                // ===== Write to CSV =====
                String csvRow = String.format("%s,%d,%d,%d,%.4f,%d,%.4f,%d,%.4f\n",
                    fileName, numVertices, numEdges, 
                    baChromatic, baRuntime, 
                    gaChromatic, gaRuntime, 
                    dpChromatic, dpRuntime);
                writer.write(csvRow);

                System.out.println("Processed file: " + fileName);
            }
            System.out.println("Results saved to " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
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

    public JButton getImportBtn() {
        return importBtn;
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

    public JLabel getMinColorLabel() {
        return minColorLabel;
    }


    public JLabel getNumVerticesLabel() {
        return numVerticesLabel;
    }

    public JLabel getNumEdgesLabel() {
        return numEdgesLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
}