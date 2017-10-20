import java.util.ArrayList;

/**
 * Programming Assignment 2.
 *
 * Contiguous Memory Allocation
 *
 * Simulates First Fit, Best Fit, and Worst Fit memory allocation.
 *
 * @author Caleb Bishop
 * @version 1
 */
public class MemoryManagement {


    /**
     * Enum for allocation modes
     */
    public enum Allocate {

        FirstFit(0),
        BestFit(1),
        WorstFit(2);

        private int method;

        /**
         * Allocate mode constructor, First Fit, Best Fit, or Worst Fit
         * @param method Simulation method
         */
        Allocate(int method){
            this.method = method;
        }

    }


    public static final int TOTAL_BYTES = 1024;

    private ArrayList<Job> mem_jobs;

    /**
     * Constructor that reads the file from argument and stores the content into a Job list.
     * @param filePath file path of the text file containing the memory jobs
     */
    public MemoryManagement(String filePath) {
        mem_jobs = MemIO.ReadMemFile(filePath);
    }


    /**
     * Method used to select a memory simulation based on what allocation mode given.
     * @param AllocationMethod The mode for which simulation to run.
     */
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

    /**
     * This method runs the First Fit Memory Allocation simulation using
     * a linked list. Loops through the jobs in the job list and
     * allocates appropriately. If it cannot allocate, it will fail
     * and print why accordingly. If it succeeds it will print 'Success'.
     */
    private void FirstFit() {
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                    boolean placed = blockList.firstFitInsert(new Block(job));
                    if(!placed){
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
        System.out.println("Success");

    }

    /**
     * This method runs the Best Fit Memory Allocation simulation using
     * a linked list. Loops through the jobs in the job list and
     * allocates appropriately. If it cannot allocate, it will fail
     * and print why accordingly. If it succeeds it will print 'Success'.
     */
    private void BestFit(){
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                boolean placed = blockList.specialFitInsert(new Block(job),Allocate.BestFit);
                if(!placed){

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

        System.out.println("Success");


    }


    /**
     * This method runs the Worst Fit Memory Allocation simulation using
     * a linked list. Loops through the jobs in the job list and
     * allocates appropriately. If it cannot allocate, it will fail
     * and print why accordingly. If it succeeds it will print 'Success'.
     */
    private void WorstFit(){
        MemLL blockList = new MemLL();
        blockList.insertAtStart(new Block());

        for(Job job : mem_jobs){

            if(job.isAllocating()){
                boolean placed = blockList.specialFitInsert(new Block(job),Allocate.WorstFit);
                if(!placed){


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
        System.out.println("Success");

    }

}
