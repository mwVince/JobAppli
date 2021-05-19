package cli;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JobList extends ArrayList<JobNode> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Searches applied job of certain company
    public ArrayList<JobNode> search(String company) {
        ArrayList<JobNode> result = new ArrayList<>();
        for(JobNode node: this) {
            if(node.getCompany().toLowerCase().contains(company.toLowerCase())) {
                result.add(node);
            }
        }
        return result;
    }

    // Adds new job to the list
    public void addJob(String role, String company, String link) {
        this.add(new JobNode(role, company, link));
    }

    // Edit a job
    public void edit(String company) {
        ArrayList<JobNode> list = search(company);
        Scanner scanner = new Scanner(System.in);
        if(list.isEmpty()) {
            System.out.println("No data find for " + company);
            return;
        }

        while(!list.isEmpty()) {
            System.out.println(list.get(0).getRole());
            System.out.println(list.get(0).getCompany());
            System.out.println("Edit information for this one? (Y/N) ");
            if(scanner.nextLine().toLowerCase().equals("y")) {
                System.out.println("Leave empty if not changing");
                System.out.print("Role: ");
                String newRole = scanner.nextLine();
                System.out.print("Company: ");
                String newCompany = scanner.nextLine();
                System.out.print("Link: ");
                String newLink = scanner.nextLine();

                if(!newRole.isEmpty()) {
                    list.get(0).setRole(newRole);
                }
                if(!newCompany.isEmpty()) {
                    list.get(0).setCompany(newCompany);
                }
                if(!newLink.isEmpty()) {
                    list.get(0).setLink(newLink);
                }
                System.out.println("Done");
                return;
            }
        }
        System.out.println("That's all data for " + company);
    }

    // Gets rejected
    public void reject(String company) {
        ArrayList<JobNode> list = search(company);
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if(list.isEmpty()) {
            System.out.println("No data find for " + company);
            return;
        }
        
        while(!list.isEmpty()) {
            System.out.println("Role: " + list.get(0).getRole());
            System.out.println("Company: " + list.get(0).getCompany());
            System.out.println("Get rejected by this one? :( (Y/N) ");
            if(scanner.nextLine().toLowerCase().equals("y")) {
                list.get(0).setStatus("Rejected");
                System.out.println("Done");
                return;
            }
            list.remove(0);
        }
        System.out.println("That's all data for " + company);
    }

    // Updates status manually
    public void changeStatus(String company) {
        ArrayList<JobNode> list = search(company);
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if(list.isEmpty()) {
            System.out.println("No data find for " + company);
            return;
        }

        while(!list.isEmpty()) {
            System.out.println("Role: " + list.get(0).getRole());
            System.out.println("Company: " + list.get(0).getCompany());
            System.out.println("Change status for this one? (Y/N) ");
            if(scanner.nextLine().toLowerCase().equals("y")) {
                System.out.print("New Status: ");
                String newStatus = scanner.nextLine();
                list.get(0).setStatus(newStatus);
                System.out.println("Done");
                return;
            }
            list.remove(0);
        }
        System.out.println("That's all data for " + company);
    }

    public void showAll() {
        if(this.isEmpty()) {
            System.out.println("Empty");
            return;
        }
        int counter = 0;
        for(JobNode node: this) {
            System.out.println(++counter + ".");
            System.out.println(node.toString());
            System.out.println();
        }
    }

    public void find(String company) {
        ArrayList<JobNode> list = search(company);
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if(list.isEmpty()) {
            System.out.println("No Result Found");
        }
        else {
            for(JobNode node: list) {
                System.out.println(node.toString());
                System.out.print("\nNext");
                scanner.nextLine();
                System.out.println();
                continue;
            }
            System.out.println("That's all");
        }
    }

    public void exportTxt() {
        try {
            File f = new File("Job_Applied.txt");
            PrintWriter printWriter = new PrintWriter(f);

            int counter = 0;
            for(JobNode node: this) {
                printWriter.println(++counter + ".");
                printWriter.println(node.toString());
                printWriter.println();
            }

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
