import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Simulation {


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
            node.newTestingRound();
        }
    }

    public Simulation()
    {
    }

    public void runSimulation(int nodesNumer)
    {
        for(int i = 0 ; i < nodesNumer ; i++)
        {
            nodes.add(new Node(i, nodesNumer, (int) Math.ceil(Math.log(nodesNumer)), this));
        }

        for(int i = 0 ; i < 1000 ; i++)
        {
            test();
        }

        fault(1);
        fault(2);
        fault(3);

        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }


        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }


        repair(2);

        for(int i = 0 ; i < 100 ; i++)
        {
            test();
        }

        System.out.print("Done");
    }

}
