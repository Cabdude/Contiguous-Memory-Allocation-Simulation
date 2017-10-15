/**
 * Created by cahlab on 10/13/17.
 */
public class Block {




    Job job;
    Hole hole;


    public Block(){
        job = null;
        hole = new Hole(0,1023);
    }

    public Block(Job job, Hole hole){
        this.job = job;
        this.hole = hole;
    }

    public Block(Job job){
        this.job = job;
    }

    public boolean available(){
        return job == null;
    }

    public boolean canPlace(Job job){
        return available() && job.isAllocating() && job.getArgument() <= getSize();
    }

    public void setJob(Job j){
        this.job = j;
    }

    public Job getJob(){
        return this.job;
    }

    public Hole getHole(){
        return this.hole;
    }

    public void displayBlock(){

        int start = hole.start;
        int end = hole.end;

        String allocated = "free";

        if(!available()) {
            allocated = "allocated to " + job.getReference_number();
        }

        System.out.printf("[%d-%d]: %s\n",start,end,allocated);
    }




    public int getSize(){
        return hole.getSize();
    }

    public Block copy(){
        Block copy = new Block();
        copy.job = this.job;
        copy.hole = this.hole;
        return copy;
    }

}


class Hole {

    int start;
    int end;

    Hole(){
        start = 0;
        end = 1023;
    }

    Hole(int start, int end){
        this.start = start;
        this.end = end;
    }

    int getSize(){
        return (end - start) + 1;
    }

    public void setRange(int start, int end){
        this.start = start;
        this.end = end;
    }



}
