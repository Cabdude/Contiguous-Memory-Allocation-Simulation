/**
 * Created by cahlab on 10/5/17.
 */
public class MemoryBlock {


    private MemoryBlock next;
    private int referenceId;
    private Range memRange;

    public MemoryBlock(int referenceId, Range memRange, MemoryBlock next){
        this.referenceId = referenceId;
        this.memRange = memRange;
        this.next = next;
    }

    public void setReferenceId(int referenceId){
        this.referenceId = referenceId;
    }

    public boolean isAllocated(){
        return referenceId != -1;
    }

    public void setNext(MemoryBlock next){
        this.next = next;
    }

    public MemoryBlock getNext(){
        return this.next;
    }

    public void setMemRange(int start, int end){
        memRange.start = start;
        memRange.end = end;
    }

    public int getStartLoc(){
        return memRange.start;
    }

    public Range getMemRange(){
        return this.memRange;
    }


}


class Range {

    public int start;
    public int end;

    public Range(int start, int end){
        this.start = start;
        this.end = end;
    }

    public void printRange(){
        System.out.println("Memory location: " + start +  "-" + end);
    }

}


