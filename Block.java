/**
 * This class serves the purpose of simulating a block in memory,
 * storing the memory job, and size of the memory hole.
 *
 * @author Caleb Bishop
 * @version 1
 */
public class Block {




    Job job;
    Hole hole;


    /**
     * Default constructor
     */
    public Block(){
        job = null;
        hole = new Hole(0,1023);
    }

    /**
     * Constructor for Block
     * @param job Memory Job, null if available.
     * @param hole used to show or give size of memory for this current block
     */
    public Block(Job job, Hole hole){
        this.job = job;
        this.hole = hole;
    }

    /**
     * Constructor for block, only given a job, to set size of hole later if need be.
     * @param job Memory job, null if no job and is free.
     */
    public Block(Job job){
        this.job = job;
    }

    /**
     * If the job for the current block is null, then the block is available.
     * @return True if block is available, false if not.
     */
    public boolean available(){
        return job == null;
    }

    /**
     * This method checks to see if the current job wanting to be placed can be placed at this block.
     * If the block is available and the job is allocating and the bytes to be placed are within this blocks size
     * then it can be placed.
     *
     * @param job job to check if can be placed at this block
     * @return True if it can be placed, false if it cannot.
     */
    public boolean canPlace(Job job){
        return available() && job.isAllocating() && job.getArgument() <= getSize();
    }

    /**
     * This method is a setter for the job in this block.
     * Set null to free block, or adding job to the block.
     * @param j Job to be set.
     */
    public void setJob(Job j){
        this.job = j;
    }

    /**
     * Getter for the job in this block.
     * @return current job, null if no job.
     */
    public Job getJob(){
        return this.job;
    }

    /**
     * This method gets the hole for this block.
     * @return hole
     */
    public Hole getHole(){
        return this.hole;
    }

    /**
     * This method displays the current block and what is allocated to it if it is allocated.
     */
    public void displayBlock(){

        int start = hole.start;
        int end = hole.end;

        String allocated = "free";

        if(!available()) {
            allocated = "allocated to " + job.getReference_number();
        }

        System.out.printf("[%d-%d]: %s\n",start,end,allocated);
    }


    /**
     * This method returns the size of the hole for the block.
     * @return hole size
     */
    public int getSize(){
        return hole.getSize();
    }



}

/**
 * This class serves the purpose of storing the size of the block in memory.
 */
class Hole {

    int start;
    int end;

    /**
     * Default constructor.
     * Sets the first hole the size of the maximum space, and is free.
     */
    Hole(){
        start = 0;
        end = MemoryManagement.TOTAL_BYTES;
    }

    /**
     * Constructor of hole.
     * @param start start byte of the hole.
     * @param end end byte of the hole.
     */
    Hole(int start, int end){
        this.start = start;
        this.end = end;
    }

    /**
     * This method returns the size of the hole.
     * @return hole size
     */
    int getSize(){
        return (end - start) + 1;
    }

    /**
     * This method sets the range of the hole for the block
     * @param start start byte of the hole
     * @param end end byte of the hole
     */
    public void setRange(int start, int end){
        this.start = start;
        this.end = end;
    }



}
