//import view.MainFrame;

import java.util.Scanner;

/**
 * Created by Pawel on 21.05.2017.
 */
public class Main {

    public static void main(String [ ] args)
    {
        //openMainFrame();
        System.out.println("Enter the number of nodes:");
        Scanner s = new Scanner(System.in);
        Simulation sim = new Simulation();
        sim.runSimulation(s.nextInt());

    }
/*
    private static void openMainFrame() {
        MainFrame mainFrame = new MainFrame("Hi-ADSD Aplication");
        mainFrame.setVisible(true);
    }
    */
}
