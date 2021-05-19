package cli;

import java.io.*;

public class JobReader {
    static String dbPath = System.getProperty("user.dir") + "/db/";
    static JobList jobList;

    public static void main(String[] args) throws IOException {
        jobList = readJobList();

        System.out.println(dbPath);
        File jobs = new File(dbPath + "text_job_applied_2021.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(jobs));
        while(bufferedReader.ready()) {
            String[] job = bufferedReader.readLine().split(String.valueOf('\u2028'));
            String role = job[0];
            boolean rejected = false;
            if(role.contains("(Rejected)")) {
                role = role.substring(0, role.length() - 11);
                rejected = true;
            }
            String company = job[1];
            String link = job[2];
            JobNode node = new JobNode(role, company, link);
            if(rejected) {
                node.setStatus("Rejected");
            }
            jobList.add(node);
        }
        quit();
    }
    private static JobList readJobList() throws IOException {
        JobList jobList = null;
        try {
            FileInputStream fileIn = new FileInputStream(dbPath + "main.JobList.ser");
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            jobList = (JobList) inStream.readObject();
            inStream.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("main.JobList Not Found.");
            System.out.println("Initiating..");
            File f = new File(dbPath + "main.JobList.ser");
            new File(dbPath).mkdir();
            f.createNewFile();
            jobList = new JobList();
            System.out.println("done");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            jobList = new JobList();
        }
        return jobList;
    }

    private static void quit() {
        try {
            FileOutputStream fileOut = new FileOutputStream(dbPath + "main.JobList.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(jobList);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
