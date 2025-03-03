import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphParserGUI extends JFrame {

    private JTextArea inputTextArea;
    private JButton runButton;

    public GraphParserGUI() {
        setTitle("Graph Parser");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        runButton = new JButton("Run");
        add(runButton, BorderLayout.SOUTH);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                GraphParser.parseAndPrint(inputText);
            }
        });

        setVisible(true);
    }
}
