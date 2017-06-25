package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Simulation{

    public List<Node> nodes = new ArrayList<Node>();

    public Node getNcde(int i){return nodes.get(i);}

    public void fault(int id)
    {
        nodes.get(id).fault();
    }

    public void repair(int id)
    {
        nodes.get(id).repair();
    }

    public void test()
    {
        for (Node node : nodes)
        {
            /*
            try {
                node.newTestingRound();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            */
         //   node.doTestingRound();
        }
    }

    public void singleStep()
    {
        for(Node node : nodes)
        {
            try {
                node.singleStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*
    public void nSteps(int n)
    {
        for(int i = 0; i < n ; i++)
        {
            for(Node node : nodes)
            {
                node.singleStep();
            }
        }
    }

    public void doTestingRound()
    {
        for(Node node : nodes)
        {
            node.doTestingRound();
        }
    }

    public void continueExecution()
    {
        for(Node node : nodes)
        {
            node.continueExecution();
        }
    }
*/
    public void printDTrees()
    {
        for(Node n: nodes)
        {
            n.printDiagTree();
        }
    }

    public Simulation()
    {
    }


    public void runSimulation(int nodesNumber)
    {
        int numberOfClusters = (int) Math.ceil((double)Math.log(nodesNumber)/(double)Math.log(2));
        //numberOfClusters += 1;
        System.out.println("Number of clusters:" + numberOfClusters);
        System.out.println("" + nodesNumber);
        for(int i = 0 ; i < nodesNumber ; i++)
        {
            nodes.add(new Node(i, nodesNumber, numberOfClusters, this));
        }
    }

}
