import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Node {
    public int id;
    public int maxId;
    public int numberOfClusters;

    public int roundsDone;
    public boolean readyToShare;
    public boolean testingRoundDone;
    public boolean testingClusterDone;
    public int foundRepaired;
    public boolean faulty;
    public int currentCluster;
    public DiagnosticTree diagTree;
    public Simulation sim;

    public Node(int i, int N, int clusters, Simulation s) {
        id = i;
        maxId = N;
        numberOfClusters = clusters;
        roundsDone = 0;
        readyToShare = true;
        testingRoundDone = false;
        testingClusterDone = false;
        foundRepaired = 0;
        faulty = false;
        currentCluster = 0;
        sim = s;
        initializeDiagnosticTree();
    }

    public void newTestingRound()
    {
        testingRoundDone = false;
        testingClusterDone = false;
        if(currentCluster < numberOfClusters)
            currentCluster++;
        else {
            currentCluster = 1;
            readyToShare = true;
        }
        testingRound();
    }

    public void testingRound()
    {
        DiagnosticTree tempDiagTree;

        int i, endOfCluster;
        while(!testingRoundDone)
        {
            tempDiagTree = new DiagnosticTree(-1);

            i = 1;
            endOfCluster = (int) Math.pow(2.0, currentCluster - 1);
            while(!testingClusterDone && i <= endOfCluster && HiADSDHelper.nthOfCluster(i, id, currentCluster) < maxId)
            {
                //System.out.println("i: " + i + ", id: " + id + ",current: " + currentCluster + ", end: " + endOfCluster);
                tempDiagTree = test(sim.getNcde(HiADSDHelper.nthOfCluster(i, id, currentCluster)), tempDiagTree);
                i++;
            }

            if(tempDiagTree.nodeNumber >= 0) {
                diagTree.setLeaf(currentCluster, tempDiagTree);
                testingRoundDone = true;
            }
            else{
                if(currentCluster < numberOfClusters)
                    currentCluster++;
                else
                    testingRoundDone = true;
            }
            testingClusterDone = false;
        }
    }

    public DiagnosticTree test(Node tested, DiagnosticTree tempDiagTree) // tempDiagTree is constructed outside of diagTree to avoid giving wrong information because the cluster data is being gathered at the time of response
    {
        Response res = tested.answer(buildRequest());
        if(res.senderID >= 0) // if senderID is negative, the tested node is faulty
        {
            if (res.diagTree != null) { //if no DiagnosticTree was returned, but there was a response, the tested node was repaired recently
                tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, res.diagTree); // foundRepaired indicates how many recently repaired nodes have been found since the start of the current testing round
                testingClusterDone = true;
                testingRoundDone = true;
                foundRepaired = 0; // the cluster was diagnosed completely, there is no longer a need to store the number of repaired nodes
            } else {
                //System.out.println("Found repaired!");
                if (HiADSDHelper.isAnEndOfCluster(id, res.senderID, currentCluster)) {
                    tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, new DiagnosticTree(res.senderID));
                    testingClusterDone = true;
                    foundRepaired = 0; // the cluster was diagnosed completely, there is no longer a need to store the number of repaired nodes
                } else {
                    tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, new DiagnosticTree(res.senderID));
                    foundRepaired++;
                }
            }
        }
        else
        {
            //System.out.println("Found faulty!");
            if (HiADSDHelper.isAnEndOfCluster(id, -1 * res.senderID, currentCluster)) {
                testingClusterDone = true;
                foundRepaired = 0;
            }
        }
        return tempDiagTree;
    }

    public void initializeDiagnosticTree() {
        diagTree = new DiagnosticTree(id);
        diagTree.initialize(maxId, numberOfClusters); }

    public Request buildRequest() {return new Request(id, currentCluster);}

    public Response answer(Request req)
    {
        if(faulty)
            return new Response(-id); // -id is used as an indicator that a node is faulty
        if(!readyToShare)
            return new Response(id);

        return new Response(id, diagTree.getDiagInfo(req.currentCluster));
    }

    public void repair()
    {
        faulty = false;
        readyToShare = false;
        roundsDone = 0;
        initializeDiagnosticTree();
    }

    public void fault()
    {
        faulty = true;
    }
}
