/**
 * Created by cahlab on 9/28/17.
 */
public class MMDriver {



    public static void main(String[] args){
        MemoryManagement manager = new MemoryManagement(args[0]);
        System.out.println("First Fit");
        manager.AllocateMemory(MemoryManagement.Allocate.FirstFit);
        System.out.println("Best Fit");
        manager.AllocateMemory(MemoryManagement.Allocate.BestFit);

        System.out.println("Worst Fit");
        manager.AllocateMemory(MemoryManagement.Allocate.WorstFit);
    }

}
