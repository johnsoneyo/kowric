/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@Entity
@Table(name = "PROJECT_JOB_LIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectJobList.findAll", query = "SELECT p FROM ProjectJobList p"),
    @NamedQuery(name = "ProjectJobList.findByProjectJobId", query = "SELECT p FROM ProjectJobList p WHERE p.projectJobId = :projectJobId"),
    @NamedQuery(name = "ProjectJobList.findByStatus", query = "SELECT p FROM ProjectJobList p WHERE p.status = :status")})
public class ProjectJobList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "PROJECT_JOB_ID")
    private Integer projectJobId;
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @JoinColumn(name = "JOB_ID", referencedColumnName = "JOB_ID")
    @ManyToOne
    private Job jobId;

    public ProjectJobList() {
    }

    public ProjectJobList(Integer projectJobId) {
        this.projectJobId = projectJobId;
    }

    public Integer getProjectJobId() {
        return projectJobId;
    }

    public void setProjectJobId(Integer projectJobId) {
        this.projectJobId = projectJobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Job getJobId() {
        return jobId;
    }

    public void setJobId(Job jobId) {
        this.jobId = jobId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectJobId != null ? projectJobId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectJobList)) {
            return false;
        }
        ProjectJobList other = (ProjectJobList) object;
        if ((this.projectJobId == null && other.projectJobId != null) || (this.projectJobId != null && !this.projectJobId.equals(other.projectJobId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.ProjectJobList[ projectJobId=" + projectJobId + " ]";
    }
    
}
