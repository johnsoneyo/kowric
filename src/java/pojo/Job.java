/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Guest
 */
@Entity
@Table(name = "JOB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findByJobId", query = "SELECT j FROM Job j WHERE j.jobId = :jobId"),
    @NamedQuery(name = "Job.findByJobTitle", query = "SELECT j FROM Job j WHERE j.jobTitle = :jobTitle"),
    @NamedQuery(name = "Job.findByJobDesc", query = "SELECT j FROM Job j WHERE j.jobDesc = :jobDesc"),
    @NamedQuery(name = "Job.findByTimeCreated", query = "SELECT j FROM Job j WHERE j.timeCreated = :timeCreated")})
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "JOB_ID")
    private Integer jobId;
    @Size(max = 50)
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Size(max = 200)
    @Column(name = "JOB_DESC")
    private String jobDesc;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @JoinTable(name = "JOB_TAG_XREF", joinColumns = {
        @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID")})
    @ManyToMany
    private List<Tags> tagsList;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private Projects projectId;
    @OneToMany(mappedBy = "jobId")
    private List<ProjectJobList> projectJobListList;

    public Job() {
    }

    public Job(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    @XmlTransient
    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public List<ProjectJobList> getProjectJobListList() {
        return projectJobListList;
    }

    public void setProjectJobListList(List<ProjectJobList> projectJobListList) {
        this.projectJobListList = projectJobListList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobId != null ? jobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Job[ jobId=" + jobId + " ]";
    }
    
}
