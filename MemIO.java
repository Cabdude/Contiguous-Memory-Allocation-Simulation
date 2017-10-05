import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by cahlab on 9/28/17.
 */
public class MemIO {



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
