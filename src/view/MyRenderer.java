package view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class MyRenderer  extends DefaultTreeCellRenderer {
    Icon tutorialIcon;

    public MyRenderer(Icon icon) {
        tutorialIcon = icon;
    }

    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(
                tree, value, sel,
                expanded, leaf, row,
                hasFocus);
        if (isNode(value)) {
            setIcon(tutorialIcon);
            setToolTipText("This book is in the Tutorial series.");
        } else {
            setToolTipText(null); //no tool tip
        }

        return this;
    }

    protected boolean isNode(Object value) {
        String val = String.valueOf(value);
        if(val.substring(0, 1).matches("\\*")){
            return true;
        }

//        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
//        String nodeInfo = (String)(node.getUserObject());
//        String title = nodeInfo.bookName;
//        if (title.indexOf("Tutorial") >= 0) {
//            return true;
//        }

        return false;
    }
}