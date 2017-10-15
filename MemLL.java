/**
 * Created by cahlab on 10/13/17.
 */


class BlockNode {

    protected Block block;
    protected BlockNode next;


    public BlockNode(){
        next = null;
        block = null;
    }


    public BlockNode(Block d, BlockNode n){
        this.block = d;
        this.next = n;
    }

    public void setNext(BlockNode n){
        next = n;
    }

    public void setBlock(Block d){
        block = d;
    }

    public BlockNode getNext(){
        return next;
    }

    public Block getBlock(){
        return block;
    }

    public BlockNode copy(){
        BlockNode copy = new BlockNode();
        copy.setNext(this.next);
        copy.setBlock(this.block);
        return copy;
    }


}





public class MemLL {


    protected BlockNode start;
    protected BlockNode end;
    public int size;

    public MemLL(){
        start = null;
        end = null;
        size = 0;
    }

    public boolean isEmpty(){
        return start == null;
    }

    public int getSize(){
        return size;
    }

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

                    if(block.getJob().getArgument() == 1)
                        curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                            curr.getBlock().getHole().start + block.getJob().getArgument());
                    else{
                        curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                                curr.getBlock().getHole().start + block.getJob().getArgument() - 1);
                    }

                    curr.getBlock().displayBlock();


                    if(curr.getBlock().getHole().end < end) {
                        BlockNode newBlock = new BlockNode(
                                new Block(null, new Hole(curr.getBlock().getHole().end + 1, end)), curr.getNext());
                        newBlock.getBlock().displayBlock();
                        System.out.println();

                        curr.setNext(newBlock);
                    }

                    return true;
                }

                curr = curr.getNext();
            }
            return false;
        }
    }



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

    public boolean specialFitInsert(Block block, MemoryManagement.Allocate mode){
        BlockNode ptr = new BlockNode(block,null);

        if(start == null){
            start = ptr;
            end = start;
            return true;
        }
        else {
            BlockNode curr = start;
            int index = -1;

            switch(mode){
                case BestFit:
                    index = findBestFitIndex(block.getJob());
                    break;
                case WorstFit:
                    index = findWorstFitIndex(block.getJob());
                    break;
            }

            int i = 0;
            while (curr != null) {

                if (i == index) {
                    if (curr.getBlock().canPlace(block.getJob())) {

                        int end = curr.getBlock().getHole().end;

                        curr.getBlock().setJob(block.getJob());

                        if (block.getJob().getArgument() == 1)
                            curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                                    curr.getBlock().getHole().start + block.getJob().getArgument());
                        else {
                            curr.getBlock().getHole().setRange(curr.getBlock().getHole().start,
                                    curr.getBlock().getHole().start + block.getJob().getArgument() - 1);
                        }

                        curr.getBlock().displayBlock();


                        if (curr.getBlock().getHole().end < end) {
                            BlockNode newBlock = new BlockNode(
                                    new Block(null, new Hole(curr.getBlock().getHole().end + 1, end)),
                                    curr.getNext());

                            newBlock.getBlock().displayBlock();
                            System.out.println();

                            curr.setNext(newBlock);
                        }

                        return true;
                    }
                }

                i++;
                curr = curr.getNext();
            }
            return false;
        }
    }


    public void joinBlocks(){
        BlockNode ptr = start;

        while(ptr.getNext() != null){

            BlockNode next = ptr.getNext();

            if(ptr.getBlock().getJob() == null && next.getBlock().getJob() == null){
                ptr.getBlock().getHole().setRange(ptr.getBlock().getHole().start, next.getBlock().getHole().end);
                ptr.setNext(next.getNext());
                System.out.println("Joining free space");
                continue;
            }

            ptr = ptr.getNext();
        }
    }


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

    public void deallocateBlock(int job_number){

        BlockNode ptr = start;
        while(ptr != null){

            if(ptr.getBlock().getJob() != null) {
                if (ptr.getBlock().getJob().getReference_number() == job_number) {
                    System.out.println("De-allocating");
                    ptr.getBlock().displayBlock();
                    System.out.println();
                    ptr.getBlock().setJob(null);
                    joinBlocks();
                    return;
                }
            }
            ptr = ptr.getNext();
        }
    }

    public void printBlocks(){
        System.out.println("Current memory display");
        BlockNode ptr = start;
        while(ptr != null){
            ptr.getBlock().displayBlock();
            ptr = ptr.getNext();
        }
    }




}
