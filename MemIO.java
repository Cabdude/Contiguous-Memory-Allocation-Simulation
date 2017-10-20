import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Caleb Bishop
 * @version 1
 */
public class MemIO {


    /**
     *
     * @param filePath file path of text file
     * @return an ArrayList<Job> containing the content of the file loaded into a Job
     */
    public static ArrayList<Job> ReadMemFile(String filePath){
        int reference_number, operation, argument;
        ArrayList<Job> jobs;
        BufferedReader mReader;
        File memFile;
        Job memJob;
        String memLine;
        String[] memContent;

        try {
            jobs = new ArrayList<>();
            memFile = new File(filePath);
            mReader = new BufferedReader(new FileReader(memFile));

            while((memLine = mReader.readLine()) != null){
                memContent = memLine.split(" ");
                reference_number = Integer.parseInt(memContent[0]);
                operation = Integer.parseInt(memContent[1]);
                argument = Integer.parseInt(memContent[2]);
                memJob = new Job(reference_number,operation,argument);
                jobs.add(memJob);
            }

            return jobs;
        }catch (Exception exc){
            return null;
        }
    }


}
