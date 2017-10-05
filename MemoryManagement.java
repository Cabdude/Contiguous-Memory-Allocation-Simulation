import java.util.ArrayList;

/**
 * Your program will be given a text file as input. Your program should read the input file from standard input.
 * The input file will list memory operations – one per line.
 * Each line of input will contain three fields (all positive integers):
 * • A reference number (a unique identifier for that operation)
 * • An operation (either 1 for “allocate” or 2 for “de-allocate”)
 * • An argument (a size in bytes for an allocate operation; a reference number for a de-allocate
 *   operation)
 */
public class MemoryManagement {



    public enum Allocate {

        FirstFit(0),
        BestFit(1),
        WorstFit(2);

        private int method;

        Allocate(int method){
            this.method = method;
        }

    }


    private final int TOTAL_BYTES = 1024;
    private ArrayList<Job> mem_jobs;

    public MemoryManagement(String filePath) {
        mem_jobs = MemIO.ReadMemFile(filePath);
    }


    public void AllocateMemory(Allocate AllocationMethod){

        switch(AllocationMethod){
            case FirstFit:
                FirstFit();
                break;
            case BestFit:
                break;
            case WorstFit:
                break;
            default:
                break;
        }

    }

    private void FirstFit() {

    }

}
