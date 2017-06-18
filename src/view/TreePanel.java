package view;

import controller.Simulation;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class TreePanel extends JPanel{

    private JScrollPane scrollPane;
    private JTree tree;

    private Simulation simulation;


    public TreePanel() {
        setLayout(new BorderLayout());
        createEmptyNodes();
        setupScrollPane();
    }

    private void createEmptyNodes() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("empty");
        tree = new JTree(root);
        tree.setShowsRootHandles(true);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(tree);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    public void refreshDiagnosticTree(Simulation simulation, int nodeId) {
        this.simulation = simulation;
        this.removeAll();
        simulation.getNcde(nodeId).diagTree.getDiagInfo(nodeId);
        createNodes(nodeId);
        setupScrollPane();
        revalidate();
        repaint();
    }

    public void createNodes(int nodeId) {
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(nodeId);
        //create the child nodes
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("1");
        vegetableNode.add(new DefaultMutableTreeNode("1"));
        vegetableNode.add(new DefaultMutableTreeNode("2"));
        vegetableNode.add(new DefaultMutableTreeNode("3"));
        vegetableNode.add(new DefaultMutableTreeNode("4"));
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("2");
        fruitNode.add(new DefaultMutableTreeNode("1"));
        fruitNode.add(new DefaultMutableTreeNode("2"));
        fruitNode.add(new DefaultMutableTreeNode("3"));
        fruitNode.add(new DefaultMutableTreeNode("4"));
        fruitNode.add(new DefaultMutableTreeNode("5"));
        //add the child nodes to the root node
        root.add(vegetableNode);
        root.add(fruitNode);

        //create the tree by passing in the root node
        tree = new JTree(root);
        tree.setShowsRootHandles(true);
    }
}
