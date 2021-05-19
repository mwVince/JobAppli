package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Read exported .txt file and save as needed .xml file
 */
public class JobReader {
    static String dbPath = System.getProperty("user.dir") + "/db/";
    static ObservableList<JobNode> jobNodeObservableList;

    public static void main(String[] args) {
        jobNodeObservableList = FXCollections.observableArrayList();
        try{
            File jobs = new File(dbPath + "/Job_Applied.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jobs));
            while(bufferedReader.ready() && !bufferedReader.readLine().equals("\n")) {
                String role = bufferedReader.readLine().substring(6);
                String company = bufferedReader.readLine().substring(9);
                String link = bufferedReader.readLine().substring(6);
                String status = bufferedReader.readLine().substring(8);

                JobNode jobNode = new JobNode();
                jobNode.setRole(role);
                jobNode.setCompany(company);
                jobNode.setLink(link);
                jobNode.setStatus(status);
                jobNodeObservableList.add(jobNode);

                bufferedReader.readLine();
            }
            System.out.println(jobNodeObservableList.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        saveToXML(new File(dbPath + "Job_Appli.xml"));

    }

    /**
     * Saves Job data to .xml file
     * @param file
     */
    public static void saveToXML(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(JobListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            JobListWrapper wrapper = new JobListWrapper();
            wrapper.setJobs(jobNodeObservableList);
            marshaller.marshal(wrapper, file);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
