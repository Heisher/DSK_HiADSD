package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SimPanel extends JPanel implements ActionListener {
    MainFrame mainFrame;
    JPanel buttonsPanel;
    private JScrollPane scrollPane;
    JPanel nodesPanel;
    JPanel descriptionPanel;
    JLabel nodeName;

    java.util.List<JButton> buttons = new ArrayList<>();


    public SimPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setupButtonsPanel();
        setupNodesPanel();
        setupDescriptionPanel();
        setupScrollPane();

        add(buttonsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(descriptionPanel, BorderLayout.SOUTH);
    }

    private void setupDescriptionPanel() {
        descriptionPanel = new JPanel(new MigLayout());
        nodeName = new JLabel("Node name: ");
        descriptionPanel.add(nodeName);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(nodesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void setupNodesPanel() {
        nodesPanel = new JPanel(new MigLayout());
        for (int i = 1; i <= 200; i++) {
            JGradientButton button = new JGradientButton("" + Integer.valueOf(i).toString());
            buttons.add(button);
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(40,40));
            button.setColor(Color.GREEN);
            if(i%5==0) {
                button.setColor(Color.RED);
            }if(i%6==0) {
                button.setColor(Color.YELLOW);
            }
            if(i%12==0){
                nodesPanel.add(button, "wrap");
            }else {
                nodesPanel.add(button);
            }

        }
    }

    private void setupButtonsPanel() {
        buttonsPanel = new JPanel(new MigLayout());
        buttonsPanel.add(new JButton("Next Step"));
        buttonsPanel.add(new JButton("10 Steps"));
        buttonsPanel.add(new JButton("To End"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        for (JButton button:buttons){
            if (src == button) {
                buttonAction(button);
            }
        }
    }

    private void buttonAction(JButton button) {
        nodeName.setText("Node name: " + button.getText());
    }
}
