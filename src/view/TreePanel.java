package view;

import controller.DiagnosticTree;
import controller.Node;
import controller.Simulation;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.*;


public class TreePanel extends JPanel{

    private java.util.List<TreeNode> treeNodeList;
    private TreeNode actualTreeNode;
    private TreeNode rootTreeNode;

    private JScrollPane scrollPane;
    private JTree tree;

    private Simulation simulation;

    DefaultMutableTreeNode root;
    DefaultMutableTreeNode actualRoot;
    DiagnosticTree diagTree;
    int nodeId;


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

    public void setTreeNodes(int depth)
    {
//        System.out.println("node number: " + nodeNumber);
//        for(Map.Entry<Integer, DiagnosticTree> e : leaves.entrySet())
//        {
//            System.out.print("depth: " + depth + ", cluster number: " + e.getKey() + ", ");
//            e.getValue().printDTree(depth+1);
//        }
    }

    private int addNode(int depth) {
        System.out.println("node number: " + nodeId);
        for(Map.Entry<Integer, DiagnosticTree> e : diagTree.leaves.entrySet())
        {
            diagTree = e.getValue();
            nodeId = diagTree.nodeNumber;

            //add tree node
            actualTreeNode = new TreeNode(depth, e.getKey(), nodeId);
            treeNodeList.add(actualTreeNode);

            System.out.print("depth: " + depth + ", cluster number: " + e.getKey() + ", ");
            addNode(depth+1);
        }


        return nodeId;
    }

    public void createNodes(int nodeId) {
        Node node = simulation.getNcde(nodeId);
        diagTree = node.diagTree;
        this.nodeId = nodeId;
        diagTree.printDTree(1);


        rootTreeNode = new TreeNode();
        rootTreeNode.nodeNumber = nodeId;
        rootTreeNode.nodeNumber = nodeId;
        rootTreeNode.nodeHandler = new DefaultMutableTreeNode("*" + nodeId);

        treeNodeList = new ArrayList<>();
        addNode(1);
        addTreeNodes();

        ToolTipManager.sharedInstance().registerComponent(tree);




        tree = new JTree(rootTreeNode.nodeHandler);
        ImageIcon tutorialIcon = createImageIcon("src/images/node.png");
        if (tutorialIcon != null) {
            tree.setCellRenderer(new MyRenderer(tutorialIcon));
        }
        expandAllNodes(0, tree.getRowCount());
    }

    private void expandAllNodes(int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(rowCount, tree.getRowCount());
        }
    }

    private void addTreeNodes() {
        for (int i = 0; i < treeNodeList.size(); i++) {
            TreeNode treeNode = treeNodeList.get(i);

            if(treeNode.depth == 1){
                rootTreeNode.nodeHandler.add(treeNode.clusterHandler);
            }else{
                if(treeNode.depth > treeNodeList.get(i-1).depth){
                    treeNodeList.get(i-1).nodeHandler.add(treeNode.clusterHandler);
                }else if(treeNode.depth == treeNodeList.get(i-1).depth){//pętla aż znajdzie depth mniejszy
                    int j = i-2;
                    while(true){
                        if(treeNodeList.get(j).depth >= treeNode.depth){
                            j--;
                        }else{
                            treeNodeList.get(j).nodeHandler.add(treeNode.clusterHandler);
                            break;
                        }
                    }
                }else if(treeNode.depth < treeNodeList.get(i-1).depth){
                    int j = i-2;
                    while(true){
                        if(treeNodeList.get(j).depth >= treeNode.depth){
                            j--;
                        }else{
                            treeNodeList.get(j).nodeHandler.add(treeNode.clusterHandler);
                            break;
                        }
                    }
                }
            }
            System.out.println("d:" + treeNode.depth + " ,c:" + treeNode.clusterNumber + " ,n:" + treeNode.nodeNumber);
        }
    }

    protected ImageIcon createImageIcon(String path) {
        ImageIcon icon = new ImageIcon("images/node.png");
//        java.net.URL imgURL = getClass().getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
        return icon;
    }
}
