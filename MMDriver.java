/**
 * Created by cahlab on 9/28/17.
 */
public class MMDriver {



    public static void main(String[] args){
        MemoryManagement manager = new MemoryManagement(args[0]);
        manager.AllocateMemory(MemoryManagement.Allocate.FirstFit);
    }

}
