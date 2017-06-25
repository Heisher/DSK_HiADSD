package view;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNode {
    public int depth;
    public int clusterNumber;
    public int nodeNumber;
    public DefaultMutableTreeNode clusterHandler;
    public DefaultMutableTreeNode nodeHandler;

    public TreeNode() {
    }

    public TreeNode(int depth, int clusterNumber, int nodeNumber) {
        this.depth = depth;
        this.clusterNumber = clusterNumber;
        this.nodeNumber = nodeNumber;
        clusterHandler = new DefaultMutableTreeNode(clusterNumber);
        nodeHandler = new DefaultMutableTreeNode(nodeNumber);
        clusterHandler.add(nodeHandler);
    }
}
