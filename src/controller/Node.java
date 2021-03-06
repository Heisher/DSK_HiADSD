package controller;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Node{
/*
    public static int CONTINUE = 1;
    public static int SINGLE_STEP = 2;
    public static int DO_TESTING_ROUND = 3;
    public static int END = 4;

    public static int EXECUTE = 1;
    public static int WAIT = 2;

    public int execution = WAIT;
    public int mode = CONTINUE;
    */

    public int id;
    public int maxId;
    public int numberOfClusters;

    public int testedNodeNumber;
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
        testedNodeNumber = 0;
        roundsDone = 0;
        readyToShare = true;
        //readyToShare = false;
        testingRoundDone = false;
        testingClusterDone = false;
        foundRepaired = 0;
        faulty = false;
        currentCluster = 0;
        sim = s;
        initializeDiagnosticTree();
    }

    public void log(String s)
    {
        System.out.println("Jam jest " + id + ": " + s);
    }

    public void doWait() throws Exception
    {
        Thread.sleep(10);
    }


    public void printDiagTree()
    {
        diagTree.printDTree(1);
    }

    public void singleStep() throws Exception
    {
        if(!faulty) {
            newTestingRound();

        }
    }

    DiagnosticTree tempDiagTree;
    int currI;
    int endOfCluster;

    public void newTestingRound() throws Exception{
        testingRoundDone = false;
        testingClusterDone = false;
        if (currentCluster < numberOfClusters)

            currentCluster++;
        else {
            currentCluster = 1;
            readyToShare = true;
        }
        testingRound();
    }

    public void testingRound() throws Exception
    {

        int i, endOfCluster;
        while(!testingRoundDone)
        {
            tempDiagTree = new DiagnosticTree(-1);

            i = 1;
            endOfCluster = (int) Math.pow(2.0, currentCluster - 1);

            while(!testingClusterDone && i <= endOfCluster)
            {
                if(HiADSDHelper.nthOfCluster(i, id, currentCluster) < maxId)
                {
                    testedNodeNumber = HiADSDHelper.nthOfCluster(i, id, currentCluster);
                    //System.out.println("i: " + i + ", id: " + id + ",current: " + currentCluster + ", end: " + endOfCluster);
                    tempDiagTree = test(sim.getNcde(testedNodeNumber), tempDiagTree);
                }
                i++;
            }
            if(tempDiagTree.nodeNumber >= 0) {
                diagTree.setLeaf(currentCluster, tempDiagTree);
                testingRoundDone = true;
            }
            else{
                diagTree.removeLeaf(currentCluster);
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
                log("Bez Kappy " + res.senderID);
                tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, res.diagTree); // foundRepaired indicates how many recently repaired nodes have been found since the start of the current testing round
                testingClusterDone = true;
                testingRoundDone = true;
                foundRepaired = 0; // the cluster was diagnosed completely, there is no longer a need to store the number of repaired nodes
            } else {
                log("Tak troche Kappa" + res.senderID);
                if (HiADSDHelper.isAnEndOfCluster(id, res.senderID, currentCluster)) {
                    tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, new DiagnosticTree(res.senderID));
                    testingClusterDone = true;
                    foundRepaired = 0;
                } else {
                    tempDiagTree = tempDiagTree.putRepairedOrBuild(foundRepaired, currentCluster, new DiagnosticTree(res.senderID));
                    foundRepaired++;
                }
            }
        }
        else
        {
            log("Full Kappa" + res.senderID);
            if (HiADSDHelper.isAnEndOfCluster(id, -1 * res.senderID, currentCluster)) {
                log("SUPREME KAPPA: " + res.senderID);
                testingClusterDone = true;
                foundRepaired = 0;
            }
        }
        return tempDiagTree;
    }


    public void initializeDiagnosticTree() {
        diagTree = new DiagnosticTree(id);
        diagTree.initialize(maxId, numberOfClusters);
    }

    public Request buildRequest() {return new Request(id, currentCluster);}

    public Response answer(Request req)
    {
        if(faulty)
            return new Response(-id);
        if(!readyToShare)
            return new Response(id);

        return new Response(id, diagTree.getDiagInfo(req.currentCluster - 1));
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
