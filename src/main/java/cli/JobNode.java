package main;

import java.io.Serializable;

public class JobNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String role;
    private String company;
    private String link;
    private String status;
    
    public JobNode(String role, String company, String link) {
        this.role = role;
        this.company = company;
        this.link = link;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        String str = "";
        str += "Role: " + this.role + "\n";
        str += "Company: " + this.company + "\n";
        str += "Link: " + this.link + "\n";
        str += "Status: " + this.status;
        return str;
    }
}
