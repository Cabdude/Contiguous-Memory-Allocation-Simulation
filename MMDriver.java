/**
 *
 * Driver class for memory management.
 * @author Caleb Bishop
 * @version 1
 */
public class MMDriver {


    /**
     * Main class to run the Memory Simulation for First Fit, Best Fit, Worst Fit.
     * @param args Should be given a file path.
     */
    public static void main(String[] args){
        MemoryManagement manager = new MemoryManagement(args[0]);

        System.out.println("----------First Fit----------");
        manager.AllocateMemory(MemoryManagement.Allocate.FirstFit);

        System.out.println("\n----------Best Fit----------");
        manager.AllocateMemory(MemoryManagement.Allocate.BestFit);

        System.out.println("\n----------Worst Fit----------");
        manager.AllocateMemory(MemoryManagement.Allocate.WorstFit);
    }

}
