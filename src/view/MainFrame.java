package view;

import controller.Simulation;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    TreePanel treePanel;
    JPanel simPanel;
    JSplitPane splitPane;


    public MainFrame(String title) throws HeadlessException {
        super(title);
        setupFrameAttributes();
        setupPanels();
        setupHorizontalSplitPane();
    }

    private void setupNodes() {

    }

    private void setupHorizontalSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treePanel, simPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        getContentPane().add(splitPane);
    }


    private void setupPanels() {
        treePanel = new TreePanel();
        simPanel = new SimPanel(this);
    }

    private void setupFrameAttributes() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
    }

    public void refreshDiagnosticTree(Simulation simulation, int nodeId) {
        treePanel.refreshDiagnosticTree(simulation, nodeId);
    }
}
