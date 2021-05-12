import java.io.*;
import java.util.Scanner;

class JobAppli {
    static String dbPath = System.getProperty("user.dir") + "/db/";
    static JobList jobList;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Glad you are applying again..");
        jobList = readJobList();
        System.out.println(jobList.size() + " jobs applied\n");
        menu();
    }

    public static void menu() {
        while(true) {
            System.out.println("Menu:");
            System.out.println("\tA) Add New Job");
            System.out.println("\tE) Edit Job");
            System.out.println("\tF) Find Job");
            System.out.println("\tR) Rejected");
            System.out.println("\tS) Change on Status");
            System.out.println("\tX) Show All");
            System.out.println("\tP) Export to txt");
            System.out.println("\tQ) Save and Quit");

            switch (sc.nextLine().toLowerCase()) {
                case "a":
                    addJob();
                    save();
                    break;
                case "e":
                    editJob();
                    save();
                    break;
                case "f":
                    findJob();
                    break;
                case "r":
                    rejected();
                    save();
                    break;
                case "s":
                    changeStatus();
                    save();
                    break;
                case "x":
                    jobList.showAll();
                    break;
                case "p":
                    export();
                    break;
                case "q":
                    save();
                    System.exit(0);
                default:
                    break;
            }
            System.out.println();
        }

    }

    private static void addJob() {
        System.out.println("Adding new job");
        System.out.print("Role: ");
        String role = sc.nextLine();
        System.out.print("Company: ");
        String company = sc.nextLine();
        System.out.print("Link: ");
        String link = sc.nextLine();
        System.out.print("Confirm? (Y/N) ");
        if(sc.nextLine().toLowerCase().equals("y")) {
            jobList.addJob(role, company, link);
        }
    }

    private static void editJob() {
        System.out.println("Editing job");
        System.out.print("Which Company to Edit: ");
        String company = sc.nextLine();
        jobList.edit(company);
    }

    private static void rejected() {
        System.out.println("Getting rejected");
        System.out.print("Which Company Rejects You: ");
        String company = sc.nextLine();
        jobList.reject(company);
    }

    private static void changeStatus() {
        System.out.println("Updating Status");
        System.out.print("Which Company to Change Status: ");
        String company = sc.nextLine();
        jobList.changeStatus(company);
    }

    private static void findJob() {
        System.out.println("Finding Job");
        System.out.print("Which Company to Find: ");
        String company = sc.nextLine();
        jobList.find(company);
    }



    private static JobList readJobList() throws IOException {
        JobList jobList = null;
        try {
            FileInputStream fileIn = new FileInputStream(dbPath + "JobList.ser");
            ObjectInputStream inStream = new ObjectInputStream(fileIn);
            jobList = (JobList) inStream.readObject();
            inStream.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("JobList Not Found.");
            System.out.println("Initiating..");
            File f = new File(dbPath + "JobList.ser");
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

    private static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(dbPath + "JobList.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(jobList);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void export() {
        System.out.println("Confirm to Export? (Y/N)");
        if(sc.nextLine().toLowerCase().equals("y")) {
            jobList.exportTxt();
            System.out.println("Done.");
        }
    }
}