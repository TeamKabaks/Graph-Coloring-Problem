import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainGUI {
    private JFrame mainFrame;
    private JPanel mainPanel, mainGraphPanel, mainControlPanel;
    private GraphPanel graphDisplayPanel;
    private JTextField edgeInput, vertexInput, colorsInput;
    private JButton drawButton, backtrackingBtn, greedyBtn, dynamicBtn;
    private ButtonOperations buttonOperations;
    private JLabel executionTimeLabel, algoLabel, statusLabel;
    private int numColors;
    
    public MainGUI(ButtonOperations buttonOperationsInput){
        this.buttonOperations = buttonOperationsInput;
        this.graphDisplayPanel = buttonOperations.getGraphPanel();
        this.backtrackingBtn = buttonOperations.getBacktrackingBtn();
        this.greedyBtn = buttonOperations.getGreedyBtn();
        this.dynamicBtn = buttonOperations.getDynamicBtn();
        this.drawButton = buttonOperations.getDrawBtn();
        this.edgeInput = buttonOperations.getEdgeInput();
        this.vertexInput = buttonOperations.getVertexInput();
        this.colorsInput = buttonOperations.getColorsInput();
        this.executionTimeLabel = buttonOperations.getExecutionTimeLabel();
        this.algoLabel = buttonOperations.getAlgoLabel();
        this.statusLabel = buttonOperations.getStatusLabel();
    }

    public void createGUI(){
        createMainFrame();
        createMainPanel();
        buttonOperations.graphGUI();
    }

    private void createMainFrame(){
        mainFrame = new JFrame("Graph Coloring Problem");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
    }

    private void createMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 31, 34));

        createMainGraphPanel();
        createMainControlPanel();

        GridBagConstraints mainPanelGBC = new GridBagConstraints();
        mainPanelGBC.fill = GridBagConstraints.BOTH;
        mainPanelGBC.gridx = 0;
        mainPanelGBC.gridy = 0;
        mainPanelGBC.weightx = 0.8;
        mainPanelGBC.weighty = 1;
        mainPanel.add(mainGraphPanel, mainPanelGBC);
        mainPanelGBC.gridx = 1;
        mainPanelGBC.gridy = 0;
        mainPanelGBC.weightx = 0.4;
        mainPanelGBC.weighty = 1;
        mainPanel.add(mainControlPanel, mainPanelGBC);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void createMainGraphPanel(){
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(49, 51, 56));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JLabel vertexLabel = new JLabel("   Vertices (A; B; C):");
        JLabel edgesLabel = new JLabel("   Edges (A,B; B,C; D,A):");
        JLabel inputLabel = new JLabel("   Number of Colors:");
        
        vertexLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        edgesLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        vertexLabel.setForeground(Color.WHITE);
        edgesLabel.setForeground(Color.WHITE);
        inputLabel.setForeground(Color.WHITE);

        inputPanel.add(vertexLabel);
        inputPanel.add(vertexInput);
        inputPanel.add(edgesLabel);
        inputPanel.add(edgeInput);
        inputPanel.add(inputLabel);
        inputPanel.add(colorsInput);
        inputPanel.add(drawButton);
        
        graphDisplayPanel.setPreferredSize(new Dimension(600,600));
        graphDisplayPanel.setOpaque(false);
        
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new GridLayout(1,3));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        algoLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        executionTimeLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 10));

        algoLabel.setForeground(Color.WHITE);
        executionTimeLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.WHITE);

        statusPanel.add(statusLabel);
        statusPanel.add(algoLabel);
        statusPanel.add(executionTimeLabel);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setBackground(Color.BLUE);
        paddingPanel.setPreferredSize(new Dimension(600,600));
        paddingPanel.setBackground(new Color(30, 31, 34));
        paddingPanel.setBorder(BorderFactory.createLineBorder(new Color(49, 51, 56), 15));
        paddingPanel.setLayout(new GridBagLayout());

        GridBagConstraints padGbc = new GridBagConstraints();
        padGbc.fill = GridBagConstraints.BOTH;
        padGbc.gridx = 0;
        padGbc.gridy = 0;
        padGbc.weightx = 0.1;
        padGbc.weighty = 1;
        paddingPanel.add(graphDisplayPanel, padGbc);
        padGbc.gridx = 0;
        padGbc.gridy = 1;
        padGbc.weightx = 0.1;
        padGbc.weighty = 0.05;
        paddingPanel.add(statusPanel, padGbc);

        mainGraphPanel = new JPanel();
        mainGraphPanel.setOpaque(false);
        mainGraphPanel.setLayout(new GridBagLayout());

        GridBagConstraints mainGraphPanelGBC = new GridBagConstraints();
        mainGraphPanelGBC.fill = GridBagConstraints.BOTH;
        mainGraphPanelGBC.gridx = 0;
        mainGraphPanelGBC.gridy = 0;
        mainGraphPanelGBC.weightx = 0.1;
        mainGraphPanelGBC.weighty = 1;
        mainGraphPanel.add(inputPanel, mainGraphPanelGBC);
        mainGraphPanelGBC.gridx = 0;
        mainGraphPanelGBC.gridy = 1;
        mainGraphPanelGBC.weightx = 0.4;
        mainGraphPanelGBC.weighty = 1;
        mainGraphPanel.add(paddingPanel, mainGraphPanelGBC);
    }

    private void createMainControlPanel(){
        mainControlPanel = new JPanel();
        mainControlPanel.setBackground(new Color(49, 51, 56));
        mainControlPanel.setLayout(new GridLayout(4, 1, 10,30));
        mainControlPanel.setLayout(new GridBagLayout());
        
        JPanel buttonContainerPanel = new JPanel();
        buttonContainerPanel.setLayout(new GridLayout(3, 1, 10,30));
        buttonContainerPanel.setOpaque(false);

        designButtons();

        buttonContainerPanel.add(backtrackingBtn);
        buttonContainerPanel.add(greedyBtn);
        buttonContainerPanel.add(dynamicBtn);
        mainControlPanel.add(buttonContainerPanel);
    }

    private void designButtons(){
        Dimension buttonSize = new Dimension(200, 70);
        backtrackingBtn.setPreferredSize(buttonSize);
        greedyBtn.setPreferredSize(buttonSize);
        dynamicBtn.setPreferredSize(buttonSize);
        drawButton.setPreferredSize(new Dimension(120, 30));

        Color buttonColor = new  Color(170, 170, 170);
        backtrackingBtn.setBackground(buttonColor);
        greedyBtn.setBackground(buttonColor);
        dynamicBtn.setBackground(buttonColor);
        drawButton.setBackground(buttonColor);

        backtrackingBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        greedyBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        dynamicBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        drawButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        backtrackingBtn.setFocusPainted(false);
        greedyBtn.setFocusPainted(false);
        dynamicBtn.setFocusPainted(false);
        drawButton.setFocusPainted(false);

        addGUIButtonListeners(backtrackingBtn);
        addGUIButtonListeners(greedyBtn);
        addGUIButtonListeners(dynamicBtn);
        addGUIButtonListeners(drawButton);
    }
    
    private void addGUIButtonListeners(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new  Color(250, 250, 250));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new  Color(170, 170, 170));
            }
        });
    }

    public int getNumColors() {
        return numColors;
    }
}
