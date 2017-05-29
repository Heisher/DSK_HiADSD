import java.util.Scanner;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Main {

    public static void main(String [ ] args)
    {
        System.out.println("Enter the number of nodes:");
        Scanner s = new Scanner(System.in);
        Simulation sim = new Simulation();
        sim.runSimulation(s.nextInt());
    }
}
