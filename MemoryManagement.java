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
    private int used_bytes = 0;


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
                BestFit();
                break;
            case WorstFit:
                WorstFit();
                break;
            default:
                break;
        }

    }

    private void FirstFit() {
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                    boolean placed = blockList.firstFitInsert(new Block(job));
                    if(!placed){
                        blockList.printBlocks();

                        System.out.println("Request " + job.getReference_number() + " failed at allocating "
                        + job.getArgument() + " bytes." );
                        System.out.println("External Fragmentation is "
                                + blockList.externalFragmentation() + " bytes.");
                        return;
                    }
            }else if(job.isDeallocating()){
                    blockList.deallocateBlock(job.getArgument());
            }

        }

        blockList.printBlocks();
    }

    private void BestFit(){
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                boolean placed = blockList.specialFitInsert(new Block(job),Allocate.BestFit);
                if(!placed){

                    blockList.printBlocks();

                    System.out.println("Request " + job.getReference_number() + " failed at allocating "
                            + job.getArgument() + " bytes." );
                    System.out.println("External Fragmentation is "
                            + blockList.externalFragmentation() + " bytes.");
                    return;
                }
            }else if(job.isDeallocating()){
                blockList.deallocateBlock(job.getArgument());
            }

        }

        blockList.printBlocks();
    }


    private void WorstFit(){
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                boolean placed = blockList.specialFitInsert(new Block(job),Allocate.WorstFit);
                if(!placed){

                    blockList.printBlocks();

                    System.out.println("Request " + job.getReference_number() + " failed at allocating "
                            + job.getArgument() + " bytes." );
                    System.out.println("External Fragmentation is "
                            + blockList.externalFragmentation() + " bytes.");
                    return;
                }
            }else if(job.isDeallocating()){
                blockList.deallocateBlock(job.getArgument());
            }

        }

        blockList.printBlocks();
    }

}
