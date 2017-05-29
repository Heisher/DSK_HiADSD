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

    public void setLeaf(Integer cNumber, DiagnosticTree dTree)
    {
        if(leaves.containsKey(cNumber))
            leaves.replace(cNumber, dTree);
        else
            leaves.put(cNumber, dTree);
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
        if(numberOfRepaired == 0)
            setLeaf(cNumber, dTree);
        else
            getLeaf(cNumber).putRepaired(numberOfRepaired - 1, 0, dTree); //
    }

    public DiagnosticTree putRepairedOrBuild(Integer numberOfRepaired, Integer cNumber, DiagnosticTree dTree)
    {
        if(numberOfRepaired == 0)
            return dTree;
        putRepaired(numberOfRepaired, cNumber, dTree);
        return this;
    }

    public void initialize(int maxId, int numberOfClusters)
    {
        int i;
        for(int currCluster = 1 ; currCluster < numberOfClusters ; currCluster++)
        {
            i = 0;
            int node = HiADSDHelper.firstOfCluster(nodeNumber, currCluster);
            if(node<maxId) {
                setLeaf(currCluster, new DiagnosticTree(node));
                getLeaf(currCluster).initialize(maxId, currCluster - 1);
            }
            i++;
        }
    }

}
