import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 27.05.2017.
 */
public class HiADSDHelper {

    /// Used for checking if node nNumber is an end of a cluster for node id
    public static boolean isAnEndOfCluster(int id, int nNumber, int cNumber)
    {
        int twoToS = (int) Math.pow(2, cNumber);
        return nNumber == nthOfCluster(twoToS / 2 - 1, id, cNumber);
    }

    public static boolean isTheFirstOfCluster(int id, int nNumber, int cNumber)
    {
        return nNumber == firstOfCluster(id, cNumber);
    }

    public static int firstOfCluster(int id, int cNumber)
    {
        return nthOfCluster(1, id, cNumber);
    }

    public static int nthOfCluster(int n, int id, int cNumber)
    {
        int twoToS = (int) Math.pow(2, cNumber);
        int j = n - 1;
        int a = id % twoToS < twoToS / 2 ? 1 : 0;
        Integer t = (int) ((id % twoToS + twoToS / 2 + j) % Math.pow(2, cNumber - 1 + a) + id / twoToS * twoToS);
        if (a == 1 && t < id)
            t += twoToS / 2;
        return t;
    }

    public static int whichCluster(int nNumber, int cNumber)
    {
        return (int) (nNumber / Math.pow(2, cNumber));
    }

    public static List<Integer> calculateCluster(int nNumber, int cNumber)
    {
        List<Integer> cluster = new ArrayList<>();
        int twoToS = (int) Math.pow(2, cNumber);
        for(int j = 0 ; j < twoToS / 2 - 1 ; j++) {
            int a = nNumber % twoToS < twoToS / 2 ? 1 : 0;
            Integer t = (int) ((nNumber % twoToS + twoToS / 2 + j) % Math.pow(2, cNumber - 1 + a) + nNumber / twoToS * twoToS);
            if (a == 1 && t < nNumber)
                t += twoToS / 2;
            cluster.add(t);
        }
        return cluster;
    }
}
