/**
 * @author Caleb Bishop
 * @version 1.0
 * This class is used as a node for the linked list
 */
class BlockNode {

    private Block block;
    private  BlockNode next;


    /**
     * Default constructor
     */
    public BlockNode(){
        next = null;
        block = null;
    }


    /**
     * Constructor of BlockNode
     * @param d Block of memory at this node
     * @param n next node linked to current node
     */
    public BlockNode(Block d, BlockNode n){
        this.block = d;
        this.next = n;
    }

    /**
     * Setter for the next BlockNode link
     * @param n next BlockNode to be linked to
     */
    public void setNext(BlockNode n){
        next = n;
    }

    /**
     * Sets the block of memory for current node.
     * @param d Block to be set.
     */
    public void setBlock(Block d){
        block = d;
    }

    /**
     * Gets the next BlockNode that's linked.
     * @return next BlockNode linked to current.
     */
    public BlockNode getNext(){
        return next;
    }

    /**
     * Getter for the block stored at this BlockNode if one.
     * @return block to get
     */
    public Block getBlock(){
        return block;
    }

}

/**
 * This class main purpose is to be a linked list
 * for the current blocks of memory that are placed or free
 * for the simulation of First Fit, Best Fit, and Worst Fit
 * memory allocation methods.
 */
public class MemLL {


    private BlockNode start;
    private BlockNode end;
    private int size;

    /**
     * Constructor, initialize linked list
     */
    public MemLL(){
        start = null;
        end = null;
        size = 0;
    }

    /**
     * Checks if linked list is empty
     * @return True if empty,  false if not
     */
    public boolean isEmpty(){
        return start == null;
    }

    /**
     * Gets the size of linked list
     * @return size of linked list
     */
    public int getSize(){
        return size;
    }

    /**
     * Inserts Block at start of linked list, best to be used to initialize first node.
     * @param block Block of memory to insert.
     */
    public void insertAtStart(Block block){
        BlockNode nptr = new BlockNode(block,null);
        size++;
        if(start == null){
            start = nptr;
            end = start;
        }
        else
        {
            nptr.setNext(start);
            start = nptr;
        }
    }

    /**
     * First fit insert, this method goes through the linked list finding the
     * first place it can insert the block into memory.
     * @param block block to insert
     * @return True if successfully inserted block of memory, False if failed.
     */
    public boolean firstFitInsert(Block block){
        BlockNode nptr = new BlockNode(block,null);

        if(start == null){
            start = nptr;
            end = start;
            return true;
        }else{

            BlockNode curr = start;

            while(curr != null){

                if(curr.getBlock().canPlace(block.getJob())){

                    int end = curr.getBlock().getHole().end;

                    curr.getBlock().setJob(block.getJob());


                    curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                            curr.getBlock().getHole().start + block.getJob().getArgument() - 1);



                    if(curr.getBlock().getHole().end < end) {
                        BlockNode newBlock = new BlockNode(
                                new Block(null, new Hole(curr.getBlock().getHole().end + 1, end)), curr.getNext());

                        curr.setNext(newBlock);
                    }
                    size++;
                    return true;
                }

                curr = curr.getNext();
            }
            return false;
        }
    }


    /**
     * This method finds the position of block of memory that the job
     * can best be fit into. Among ties, it will go to the first block it found in the ties.
     * @param job Job to find best fit
     * @return index of best fit, if -1 then it failed to find index
     */
    public int findBestFitIndex(Job job){
        int index = -1;
        BlockNode ptr = start;

        int min = 5000;
        int i = 0;
        while(ptr != null){

            if(ptr.getBlock().getSize() >= job.getArgument()) {
                if (ptr.getBlock().canPlace(job)) {
                    if(ptr.getBlock().getSize() < min) {
                        index = i;
                        min = ptr.getBlock().getSize();
                    }

                }
            }

            ptr = ptr.getNext();
            i++;
        }


        return index;
    }

    /**
     * This method finds the worst possible fit for the current job in memory,
     * Among ties it will go to the first worst fit it found.
     * @param job job to find worst fit
     * @return index of worst fit position in memory, -1 if failed.
     */
    public int findWorstFitIndex(Job job){
        int index = -1;
        BlockNode ptr = start;

        int max = -5000;
        int i = 0;
        while(ptr != null){

            if(ptr.getBlock().getSize() >= job.getArgument()) {
                if (ptr.getBlock().canPlace(job)) {
                    if(ptr.getBlock().getSize() > max) {
                        index = i;
                        max = ptr.getBlock().getSize();
                    }

                }
            }

            ptr = ptr.getNext();
            i++;
        }


        return index;
    }

    /**
     * This method can simulate both Worst Fit, and Best Fit and will place accordingly if
     * it can be placed
     * @param block block of memory to insert
     * @param mode What Allocation mode to use, BestFit, or WorstFit
     * @return True if successfully placed, false if it failed.
     */
    public boolean specialFitInsert(Block block, MemoryManagement.Allocate mode){
        BlockNode ptr = new BlockNode(block,null);

        if(start == null){
            start = ptr;
            end = start;
            return true;
        }
        else {
            BlockNode curr = start;
            int index;

            switch(mode){
                case BestFit:
                    index = findBestFitIndex(block.getJob());
                    break;
                case WorstFit:
                    index = findWorstFitIndex(block.getJob());
                    break;
                default:
                    return false;
            }

            if(index == -1){
                return false;
            }

            int i = 0;
            while (curr != null) {

                if (i == index) {
                    if (curr.getBlock().canPlace(block.getJob())) {

                        int end = curr.getBlock().getHole().end;

                        curr.getBlock().setJob(block.getJob());


                        curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                                curr.getBlock().getHole().start + block.getJob().getArgument() - 1);




                        if (curr.getBlock().getHole().end < end) {
                            BlockNode newBlock = new BlockNode(
                                    new Block(null, new Hole(curr.getBlock().getHole().end + 1, end)),
                                    curr.getNext());

                            curr.setNext(newBlock);
                        }
                        size++;
                        return true;
                    }
                }

                i++;
                curr = curr.getNext();
            }
            return false;
        }
    }


    /**
     * This method goes through current memory blocks. If blocks are next to each other
     * and free it will join the blocks together making a larger block.
     */
    public void joinBlocks(){
        BlockNode ptr = start;

        while(ptr.getNext() != null){

            BlockNode next = ptr.getNext();

            if(ptr.getBlock().getJob() == null && next.getBlock().getJob() == null){
                ptr.getBlock().getHole().setRange(ptr.getBlock().getHole().start, next.getBlock().getHole().end);
                ptr.setNext(next.getNext());
                size--;
                continue;
            }

            ptr = ptr.getNext();
        }
    }


    /**
     * This method gets the external fragmentation of the current memory blocks
     * if a block of memory failed to placed.
     * @return external fragmentation of memory.
     */
    public int externalFragmentation(){
        BlockNode ptr = start;
        int externalFragmentation = 0;
        while(ptr != null){

            if(ptr.getBlock().getJob() == null){
                externalFragmentation += ptr.getBlock().getHole().getSize();
            }
            ptr = ptr.getNext();
        }

        return externalFragmentation;
    }


    /**
     * This method goes through the blocks of memory and de-allocates the block
     * for the provided job_number
     * @param job_number job to be de-allocated.
     */
    public void deallocateBlock(int job_number){

        BlockNode ptr = start;
        while(ptr != null){

            if(ptr.getBlock().getJob() != null) {
                if (ptr.getBlock().getJob().getReference_number() == job_number) {
                    ptr.getBlock().setJob(null);
                    joinBlocks();
                    return;
                }
            }
            ptr = ptr.getNext();
        }
    }

    /**
     * This method prints the whole list of current memory.
     */
    public void printBlocks(){
        System.out.println("Current memory display");
        BlockNode ptr = start;
        while(ptr != null){
            ptr.getBlock().displayBlock();
            ptr = ptr.getNext();
        }
    }




}
