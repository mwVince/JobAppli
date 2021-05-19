package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for JobNode
 */
public class JobNode {
    private static final long serialVersionUID = 1L;
    private transient StringProperty role;
    private transient StringProperty company;
    private transient StringProperty link;
    private transient StringProperty status;
    /**
     * Default constructor
     */
    public JobNode() {
        this.role = new SimpleStringProperty();
        this.company = new SimpleStringProperty();
        this.link = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getLink() {
        return link.get();
    }

    public StringProperty linkProperty() {
        return link;
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getRole() + "\n");
        stringBuilder.append(this.getCompany() + "\n");
        stringBuilder.append(this.getLink() + "\n");
        stringBuilder.append(this.getStatus());

        return stringBuilder.toString();
    }
}
