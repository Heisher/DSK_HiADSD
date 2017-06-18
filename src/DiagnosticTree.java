import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pawel on 21.05.2017.
 */
public class DiagnosticTree {

    public int nodeNumber;
    public Map<Integer, DiagnosticTree> leaves = new HashMap<>();

    public DiagnosticTree(int nNumber)
    {
        nodeNumber = nNumber;
    }

    public void printDTree(int depth)
    {
        System.out.println("node number: " + nodeNumber);
        for(Map.Entry<Integer, DiagnosticTree> e : leaves.entrySet())
        {
            System.out.print("depth: " + depth + ", cluster number: " + e.getKey() + ", ");
            e.getValue().printDTree(depth+1);
        }
    }

    public void setLeaf(Integer cNumber, DiagnosticTree dTree)
    {
        if(leaves.containsKey(cNumber))
            leaves.replace(cNumber, dTree);
        else
            leaves.put(cNumber, dTree);
    }

    public void removeLeaf(int i)
    {
        leaves.remove(i);
    }

    public DiagnosticTree getLeaf(int i)
    {
        return leaves.get(i);
    }

    public DiagnosticTree getDiagInfo(Integer cNumber)
    {
        DiagnosticTree dTree = new DiagnosticTree(nodeNumber);

        for(Integer i = 1 ; i <= cNumber ; i++)
            if(leaves.containsKey(i))
               dTree.leaves.put(i, leaves.get(i));
        return dTree;
    }

    // cluster number 0 is reserved for handling repaired nodes
    // there may be duplicate data on nodes that were repaired recently
    public void putRepaired(Integer numberOfRepaired, Integer cNumber, DiagnosticTree dTree)
    {
        if(numberOfRepaired == 1)
            setLeaf(cNumber, dTree);
        else
            getLeaf(cNumber).putRepaired(numberOfRepaired - 1, 0, dTree);
    }

    public DiagnosticTree putRepairedOrBuild(Integer numberOfRepaired, Integer cNumber, DiagnosticTree dTree)
    {
        if(numberOfRepaired == 0)
            return dTree;
        putRepaired(numberOfRepaired, 0, dTree);
        return this;
    }

    public void initialize(int maxId, int numberOfClusters)
    {
        for(int currCluster = 1 ; currCluster <= numberOfClusters ; currCluster++)
        {
            int node = HiADSDHelper.firstOfCluster(nodeNumber, currCluster);
            if(node<maxId) {
                setLeaf(currCluster, new DiagnosticTree(node));
                getLeaf(currCluster).initialize(maxId, currCluster - 1);
            }
            else {
                Boolean found = false;
                int i = 2;
                int clusterSize = (int) Math.pow(2, currCluster-1);
                while(i <= clusterSize && !found)
                {
                    node = HiADSDHelper.nthOfCluster(i, nodeNumber, currCluster);
                    if(node<maxId)
                    {
                        setLeaf(currCluster, new DiagnosticTree(node));
                        getLeaf(currCluster).initialize(maxId, currCluster - 1);
                        found = true;
                    }
                    i++;
                }
            }
        }
    }

}
