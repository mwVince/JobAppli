package gui;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of JobNode, in order to store in XML
 */
@XmlRootElement(name = "jobs")
public class JobListWrapper {
    private List<JobNode> jobs;

    @XmlElement(name = "job")
    public List<JobNode> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobNode> jobs) {
        this.jobs = jobs;
    }
}