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
        if (leaf && isTutorialBook(value)) {
            setIcon(tutorialIcon);
            setToolTipText("This book is in the Tutorial series.");
        } else {
            setToolTipText(null); //no tool tip
        }

        return this;
    }

    protected boolean isTutorialBook(Object value) {
//        DefaultMutableTreeNode node =
//                (DefaultMutableTreeNode)value;
//        BookInfo nodeInfo =
//                (BookInfo)(node.getUserObject());
//        String title = nodeInfo.bookName;
//        if (title.indexOf("Tutorial") >= 0) {
//            return true;
//        }

        return false;
    }
}