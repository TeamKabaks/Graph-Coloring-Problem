import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
    
        graphColoring.greedyColoring();

        // Retrieve and apply colors to GraphPanel
        int[] colorArray = graphColoring.getColors();
        graphPanel.setNodeColors(colorArray);
        setAlgoLabel("Greedy Algorithm");
        setExecutionTimeLabel(graphColoring.getExecutionTime());
        //setStatusLabel(graphColoring.isSolutionFound());
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
                    int processed = processColFiles(selectedFiles, selectedFiles.length);
        
                    // Show detailed confirmation message after processing
                    JOptionPane.showMessageDialog(null, 
                            "Successfully processed " + processed + " out of " + 
                            selectedFiles.length + " files.\nResults saved to graph_coloring_results.csv", 
                            "Processing Complete", JOptionPane.INFORMATION_MESSAGE);
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

    /**
 * Process multiple .col files and analyze them with different graph coloring algorithms
 * 
 * @param selectedFiles Array of files to process
 * @param numFiles Number of files selected (not used, but kept for backward compatibility)
 * @return Number of successfully processed files
 */
public int processColFiles(File[] selectedFiles, int numFiles) {
    if (selectedFiles == null || selectedFiles.length == 0) {
        System.out.println("No files provided for processing.");
        return 0;
    }
    
    String csvHeader = "File Name,No. of Vertices,No. of Edges,BA Chromatic Number,BA Runtime (ms)," +
                      "GA Chromatic Number,GA Runtime (ms),DP Chromatic Number,DP Runtime (ms)\n";
    File csvFile = new File("graph_coloring_results.csv");
    
    int successfullyProcessed = 0;
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
        writer.write(csvHeader);
        
        for (File file : selectedFiles) {
            try {
                // Validate file extension
                if (!file.getName().toLowerCase().endsWith(".col")) {
                    System.out.println("Skipping non-.col file: " + file.getName());
                    continue;
                }
                
                System.out.println("Processing file: " + file.getName());
                
                // Parse the .col file
                ColFileData graphData = parseColFile(file);
                if (graphData == null) {
                    System.out.println("Failed to parse file: " + file.getName());
                    continue;
                }
                
                // Extract data
                String fileName = file.getName().replace(".col", "");
                int numVertices = graphData.numVertices;
                int numEdges = graphData.numEdges;
                List<String[]> edgesList = graphData.edgesList;
                
                // Run Backtracking Algorithm
                System.out.println("  Running Backtracking Algorithm...");
                int baChromatic = -1;
                baRuntime = -1;
                try {
                    GraphColoringBacktracking ba = new GraphColoringBacktracking(numVertices, numEdges);
                    
                    for (String[] edge : edgesList) {
                        int v1 = Integer.parseInt(edge[0]) - 1; // Convert to 0-indexed
                        int v2 = Integer.parseInt(edge[1]) - 1; // Convert to 0-indexed
                        ba.getGraph()[v1][v2] = 1;
                        ba.getGraph()[v2][v1] = 1;
                    }
                    
                    ba.colorGraph(numVertices);
                    baRuntime = ba.getExecutionTime();
                    baChromatic = ba.getMinimumColors();
                } catch (Exception e) {
                    System.err.println("Error in Backtracking Algorithm: " + e.getMessage());
                }
                
                // Run Greedy Algorithm
                System.out.println("  Running Greedy Algorithm...");
                int gaChromatic = -1;
                gaRuntime = -1;
                try {
                    GraphColoringGreedy ga = new GraphColoringGreedy(numVertices);
                    
                    for (String[] edge : edgesList) {
                        int v1 = Integer.parseInt(edge[0]) - 1; // Convert to 0-indexed
                        int v2 = Integer.parseInt(edge[1]) - 1; // Convert to 0-indexed
                        ga.addEdge(v1, v2);
                    }
                    
                    ga.greedyColoring();
                    gaRuntime = ga.getExecutionTime();
                    gaChromatic = ga.getMinimumColors();
                } catch (Exception e) {
                    System.err.println("Error in Greedy Algorithm: " + e.getMessage());
                }
                
                // Run Dynamic Programming
                System.out.println("  Running Dynamic Programming Algorithm...");
                int dpChromatic = -1;
                dpRuntime = -1;
                try {
                    // Assuming we use numVertices as an initial upper bound for colors
                    GraphColoringDP dp = new GraphColoringDP(numVertices, numVertices);
                    
                    for (String[] edge : edgesList) {
                        int v1 = Integer.parseInt(edge[0]) - 1; // Convert to 0-indexed
                        int v2 = Integer.parseInt(edge[1]) - 1; // Convert to 0-indexed
                        dp.addEdge(v1, v2);
                    }
                    
                    dp.colorGraph();
                    dpRuntime = dp.getExecutionTime();
                    dpChromatic = dp.getMinimumColors();
                } catch (Exception e) {
                    System.err.println("Error in Dynamic Programming Algorithm: " + e.getMessage());
                }
                
                // Write results to CSV
                String csvRow = String.format("%s,%d,%d,%d,%.4f,%d,%.4f,%d,%.4f\n",
                    fileName, numVertices, numEdges,
                    baChromatic, baRuntime,
                    gaChromatic, gaRuntime,
                    dpChromatic, dpRuntime);
                writer.write(csvRow);
                
                successfullyProcessed++;
                System.out.println("Successfully processed: " + fileName);
            } catch (Exception e) {
                System.err.println("Error processing file " + file.getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("Results saved to " + csvFile.getAbsolutePath());
        return successfullyProcessed;
    } catch (IOException e) {
        System.err.println("Error writing to CSV file: " + e.getMessage());
        e.printStackTrace();
        return successfullyProcessed;
    }
}

/**
 * Helper class to hold parsed data from .col files
 */
private static class ColFileData {
    public int numVertices;
    public int numEdges;
    public List<String[]> edgesList;
    
    public ColFileData(int numVertices, int numEdges, List<String[]> edgesList) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.edgesList = edgesList;
    }
}

/**
 * Parse a .col file into structured data
 * 
 * @param file The .col file to parse
 * @return A ColFileData object containing the parsed graph information
 */
private ColFileData parseColFile(File file) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        int numVertices = 0;
        int numEdges = 0;
        List<String[]> edgesList = new ArrayList<>();
        Set<Integer> vertices = new TreeSet<>();
        
        // First pass: get graph dimensions and edges
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("c")) {
                // Skip comments and empty lines
                continue;
            } else if (line.startsWith("p edge")) {
                String[] parts = line.split("\\s+");
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid p edge line format");
                }
                numVertices = Integer.parseInt(parts[2]);
                numEdges = Integer.parseInt(parts[3]);
            } else if (line.startsWith("e ")) {
                String[] parts = line.substring(2).trim().split("\\s+");
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Invalid edge line format: " + line);
                }
                int v1 = Integer.parseInt(parts[0]);
                int v2 = Integer.parseInt(parts[1]);
                
                // Validate vertex indices
                if (v1 < 1 || v1 > numVertices || v2 < 1 || v2 > numVertices) {
                    System.err.println("Warning: Invalid vertex indices: " + v1 + ", " + v2 + 
                                      " (exceeds vertex count: " + numVertices + ")");
                    // Still add them as we may want to process invalid files
                }
                
                vertices.add(v1);
                vertices.add(v2);
                edgesList.add(new String[]{String.valueOf(v1), String.valueOf(v2)});
            }
        }
        
        // Validate dimensions
        if (numVertices <= 0) {
            throw new IllegalArgumentException("Invalid graph: No vertices defined");
        }
        
        // Validate edge count
        if (edgesList.size() != numEdges) {
            System.err.println("Warning: Expected " + numEdges + " edges but found " 
                + edgesList.size() + " in file " + file.getName());
        }
        
        return new ColFileData(numVertices, numEdges, edgesList);
    } catch (NumberFormatException e) {
        throw new IOException("Error parsing numeric values: " + e.getMessage(), e);
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