package view;

import controller.Node;
import controller.Simulation;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class SimPanel extends JPanel implements ActionListener {
    MainFrame mainFrame;
    JPanel buttonsPanel;
    private JScrollPane scrollPane;
    JPanel nodesPanel;
    JPanel descriptionPanel;
    JLabel nodeName;


    JButton startNewSimButton;
    JFormattedTextField nodesNumberTextField;

    JButton oneStepButton;
    JButton nStepsButton;
    JFormattedTextField nStepsTextField;
    JButton continueSimButton;
    JButton pauseSimButton;
    boolean pause = true;
    JFormattedTextField speedTextField;

    JLabel testsLabel = new JLabel("steps: 0");

    JCheckBox faultyCheckBox;
    JButton repairButton;

    Simulation simulation;
    int steps;
    int selectedNodeId = -1;

    java.util.List<JButton> buttons = new ArrayList<>();

    private static TimerTask timerTask;
    private static Timer timer;


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
        descriptionPanel.add(nodeName, "wrap");
        descriptionPanel.add(faultyCheckBox, "wrap");
        descriptionPanel.add(repairButton);
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(nodesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void setupNodesPanel() {
        nodesPanel = new JPanel(new MigLayout());
        //nodesPanel.setDoubleBuffered(true);
    }

    private void setupNodes() {
        for (Node node : simulation.nodes) {
            JGradientButton button = new JGradientButton("" + Integer.valueOf(node.id).toString());
            buttons.add(button);
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(40,40));
            button.setColor(Color.GREEN);
            if(node.faulty) {
                button.setColor(Color.RED);
            }
            if(!node.readyToShare){
                button.setColor(Color.YELLOW);
            }

            if((node.id+1)%12==0){
                nodesPanel.add(button, "wrap");
            }else {
                nodesPanel.add(button);
            }

        }
    }

    private void setupButtonsPanel() {
        setupButtonsAndTextFields();
        buttonsPanel = new JPanel(new BorderLayout());
        JPanel simulationStartPanel = new JPanel();
        simulationStartPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JPanel simulationControlPanel = new JPanel();

        simulationStartPanel.add(startNewSimButton);
        simulationStartPanel.add(new JLabel("nodes number:"));
        simulationStartPanel.add(nodesNumberTextField);
        simulationStartPanel.add(testsLabel);

        simulationControlPanel.add(oneStepButton);
        simulationControlPanel.add(nStepsButton);
        simulationControlPanel.add(nStepsTextField);
        simulationControlPanel.add(continueSimButton);
        simulationControlPanel.add(new JLabel("speed:"));
        simulationControlPanel.add(speedTextField);
        simulationControlPanel.add(pauseSimButton);

        buttonsPanel.add(simulationStartPanel, BorderLayout.NORTH);
        buttonsPanel.add(simulationControlPanel, BorderLayout.CENTER);
    }

    private void setupButtonsAndTextFields() {
        startNewSimButton = new JButton("Start new Simulation");
        startNewSimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation = new Simulation();
                steps = 0;
                simulation.runSimulation(((Number)nodesNumberTextField.getValue()).intValue());
                refreshNodes();
                testsLabel.setText("steps: " + steps);
                pause = true;
            }
        });

        oneStepButton = new JButton("Next Step");
        oneStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSingleStep();
            }
        });

        nStepsButton = new JButton("N Steps");
        nStepsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < ((Number) nStepsTextField.getValue()).intValue(); i++) {
                    doSingleStep();
                }
            }
        });

        continueSimButton = new JButton("Continue");
        continueSimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause = false;
                timerStart();
            }
        });

        pauseSimButton = new JButton("Pause");
        pauseSimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause = true;
                continueSimButton.setEnabled(true);
                nStepsButton.setEnabled(true);
                oneStepButton.setEnabled(true);
            }
        });

        repairButton = new JButton("Repair");
        repairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation.getNcde(selectedNodeId).repair();
                refreshNodes();
            }
        });


        faultyCheckBox = new JCheckBox("faulty");
        faultyCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //faultyCheckBox.setSelected(!faultyCheckBox.isSelected());
                simulation.getNcde(selectedNodeId).faulty = faultyCheckBox.isSelected();
                refreshNodes();
            }
        });


        nStepsTextField = new JFormattedTextField();
        nStepsTextField.setValue(2);
        nStepsTextField.setColumns(5);

        nodesNumberTextField = new JFormattedTextField();
        nodesNumberTextField.setValue(10);
        nodesNumberTextField.setColumns(5);

        speedTextField = new JFormattedTextField();
        speedTextField.setValue(1);
        speedTextField.setColumns(5);

    }

    private void doSingleStep() {
        steps++;
        testsLabel.setText("steps: " + steps);
        System.out.println("steps: " + steps);
        for (Node node : simulation.nodes) {
            System.out.print(node.id + ", ");
        }
        System.out.println("");

        if(selectedNodeId != -1){
            faultyCheckBox.setSelected(simulation.getNcde(selectedNodeId).faulty);
        }

        simulation.singleStep();
        refreshNodes();
    }

    private void refreshNodes() {
        nodesPanel.removeAll();
        setupNodes();
        nodesPanel.revalidate();
        nodesPanel.repaint();
    }

    public void timerStart() {
        continueSimButton.setEnabled(false);
        nStepsButton.setEnabled(false);
        oneStepButton.setEnabled(false);
        int wait = 2000/((Number) speedTextField.getValue()).intValue();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!pause){
                    doSingleStep();
                }else{
                    timmerStop();
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, wait);
    }

    public static void timmerStop(){
        timer.cancel();
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
        selectedNodeId = Integer.valueOf(button.getText());
        faultyCheckBox.setSelected(simulation.getNcde(selectedNodeId).faulty);
        nodeName.setText("Node name: " + selectedNodeId);
        mainFrame.refreshDiagnosticTree(simulation, Integer.valueOf(button.getText()));
    }
}
