package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class TreePanel extends JPanel{

    private JScrollPane scrollPane;
    private JTree tree;

    public TreePanel() {
        setLayout(new BorderLayout());
        createNodes();
        setupScrollPane();
        add(scrollPane);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(tree);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void createNodes() {
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("0");
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
