package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Simulation {


    public List<Node> nodes = new ArrayList<Node>();
    public List<Thread> threads = new ArrayList<Thread>();

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
            node.doTestingRound();
        }
    }

    public void singleStep()
    {
        for(Node node : nodes)
        {
            node.singleStep();
        }
    }

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

    public void start()
    {
        for(int i = 0 ; i < nodes.size() ; i++)
        {
            threads.add(new Thread(nodes.get(i)));
            threads.get(i).start();
        }
    }

    public void join()
    {
        for(Thread t : threads)
        {
            try {
                t.join();
            }
            catch(Exception e)
            {

            }
        }
    }

    public void end()
    {
        for(Node node : nodes)
        {
            node.end();
        }
    }

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
            nodes.add(new Node(i, nodesNumber, numberOfClusters, this, Node.WAIT));
        }


        fault(1);
        fault(4);
        fault(6);
        repair(4);
        repair(6);

        start();
/*
        for(int i = 0 ; i < 1000 ; i++)
        {
            test();
        }
*/

        continueExecution();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
        fault(1);
        fault(4);
        fault(5);
        fault(6);
        repair(4);
        repair(5);
        repair(6);*/


  //      fault(3);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*
        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }

        repair(2);

        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }


        //repair(2);

        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }
        */
        end();

        join();

        System.out.println("Done");


        //nodes.get(0).printDiagTree();

        //printDTrees();
    }

}
