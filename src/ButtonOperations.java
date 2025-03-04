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
import java.util.stream.Collectors;
import javax.swing.*;
import java.nio.file.Files;

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
    public int baChromatic, gaChromatic, dpChromatic;

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
        numEdgesLabel = new JLabel("No. of Edges: ");
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
        GraphColoringBacktracking graphColoring = new GraphColoringBacktracking(numVertices, numEdges);
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
        baRuntime = graphColoring.getExecutionTime();
        baChromatic = graphColoring.getMinimumColors();
    }

    public void greedyBtnPressed() {
        System.out.println("\n=====================================");
        System.out.println("GREEDY ALGORITHM: GRAPH COLORING");
        System.out.println("=====================================");

        GraphColoringGreedy graphColoring = new GraphColoringGreedy(numVertices);
        for (String[] edge : edges) {
            int u = nodeMapping.get(edge[0]);
            int v = nodeMapping.get(edge[1]);
            graphColoring.addEdge(u, v);
        }
    
        graphColoring.greedyColoring();

        int[] colorArray = graphColoring.getColors();
        graphPanel.setNodeColors(colorArray);
        setAlgoLabel("Greedy Algorithm");
        setExecutionTimeLabel(graphColoring.getExecutionTime());
        //setStatusLabel(graphColoring.isSolutionFound());
        setMinColorLabel(graphColoring.getMinimumColors());
        setNumVerticesLabel();
        setNumEdgesLabel();
        System.out.println("Execution Time: " + graphColoring.getExecutionTime());
        gaRuntime = graphColoring.getExecutionTime();
        gaChromatic = graphColoring.getMinimumColors();
    }

    public void dynamicBtnPressed() {
        System.out.println("\n=====================================");
        System.out.println("DYNAMIC PROGRAMMING: GRAPH COLORING");
        System.out.println("=====================================");

        GraphColoringDP gc = new GraphColoringDP(numVertices, numColors);
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
        dpRuntime = gc.getExecutionTime();
        dpChromatic = gc.getMinimumColors();
        System.out.println("==============================================================================");
    }

    public void graphGUI(){            
        ActionListener drawGraphAction = (ActionEvent e) -> {
            Map<String, Point> nodes = new HashMap<>();
            Set<String> uniqueEdges = new HashSet<>();
            Set<String> uniqueNodes = new HashSet<>();
            
            String[] edgePairs = edgeInput.getText().split("; ?");
            for (String pair : edgePairs) {
                String[] nodesPair = pair.split(",");
                if (nodesPair.length == 2) {
                    String nodeA = nodesPair[0].trim();
                    String nodeB = nodesPair[1].trim();
                    if (!nodeA.equals(nodeB)) {
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
            
            List<String[]> edges = new ArrayList<>();
            for (String edge : uniqueEdges) {
                edges.add(edge.split(","));
            }
            
            List<String> nodeList = new ArrayList<>(uniqueNodes);
            nodes = generatePolygonPoints(nodeList, (graphPanel.getSize().width)/2, (graphPanel.getSize().height)/2, 150);
            graphPanel.setGraph(nodes, edges);
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
                    processColFiles(selectedFiles);
                    JOptionPane.showMessageDialog(null, "Graph Coloring Data Saved to CSV", 
                                                  "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        edgeInput.addActionListener(drawGraphAction);
        vertexInput.addActionListener(drawGraphAction);
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
    
    private void extractGraphData() {
        nodeMapping = new HashMap<>();
        edges = new ArrayList<>(graphPanel.getEdges());
        int index = 0;
        numColors = colorsInput.getText().isEmpty() ? 0 : Integer.parseInt(colorsInput.getText());
        numEdges = edges.size();
    
        for (String node : graphPanel.getNodes().keySet()) {
            nodeMapping.putIfAbsent(node, index++);
        }
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

    public void processColFiles(File[] selectedFiles) {
        String csvHeader = "File Name,No. of Vertices,No. of Edges,BA Chromatic Number,BA Runtime (ms),GA Chromatic Number,GA Runtime (ms),DP Chromatic Number,DP Runtime (ms)\n";
        File csvFile = new File("graph_coloring_results.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(csvHeader);

            for (File file : selectedFiles) {
                String content = new String(Files.readAllBytes(file.toPath()));
                String fileName = file.getName().replace(".col", "");
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

                String vertexData = String.join("; ", vertices.stream().map(String::valueOf).toArray(String[]::new));
                String edgeData = edgesList.stream()
                        .map(edge -> edge[0] + "," + edge[1])
                        .collect(Collectors.joining("; "));

                this.vertexInput.setText(vertexData);
                this.edgeInput.setText(edgeData);

                // Simulate draw button press to update graphPanel
                ActionEvent drawEvent = new ActionEvent(this.drawBtn, ActionEvent.ACTION_PERFORMED, "");
                for (ActionListener listener : this.drawBtn.getActionListeners()) {
                    listener.actionPerformed(drawEvent);
                }

                extractGraphData();
                this.numColors = this.numVertices;

                backtrackingBtnPressed();
                greedyBtnPressed();
                dynamicBtnPressed();

                String csvRow = String.format("%s,%d,%d,%d,%.4f,%d,%.4f,%d,%.4f\n",
                        fileName, numVertices, numEdges,
                        baChromatic, baRuntime,
                        gaChromatic, gaRuntime,
                        dpChromatic, dpRuntime);
                writer.write(csvRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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