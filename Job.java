/**
 * @author Caleb Bishop
 * @version 1
 */
public class Job {


    // Private variables
    private int reference_number;
    private int operation;
    private int argument;

    /**
     * Default constructor
     */
    private Job(){

    }

    /**
     *
     * @param reference_number A reference number (a unique identifier for that operation)
     * @param operation An operation (either 1 for “allocate” or 2 for “de-allocate”)
     * @param argument An argument (a size in bytes for an allocate operation;
     *                 a reference number for a de-allocate operation)
     */
    public Job(int reference_number, int operation, int argument){
            this.reference_number = reference_number;
            this.operation = operation;
            this.argument = argument;
    }


    /**
     *
     * @return
     */
    public int getReference_number(){
        return this.reference_number;
    }

    /**
     *
     * @return
     */
    public int getOperation(){
        return this.operation;
    }

    /**
     *
     * @return
     */
    public int getArgument(){
        return this.argument;
    }


    /**
     *
     * @return
     */
    public boolean isAllocating(){
        return getOperation() == 1;
    }


    /**
     *
     * @return
     */
    public boolean isDeallocating(){
        return getOperation() == 2;
    }


}
