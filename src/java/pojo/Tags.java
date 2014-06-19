/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Guest
 */
@Entity
@Table(name = "TAGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tags.findAll", query = "SELECT t FROM Tags t"),
    @NamedQuery(name = "Tags.findByTagId", query = "SELECT t FROM Tags t WHERE t.tagId = :tagId"),
    @NamedQuery(name = "Tags.findByTagName", query = "SELECT t FROM Tags t WHERE t.tagName = :tagName"),
    @NamedQuery(name = "Tags.findByTagDesc", query = "SELECT t FROM Tags t WHERE t.tagDesc = :tagDesc"),
    @NamedQuery(name = "Tags.findByTagCount", query = "SELECT t FROM Tags t WHERE t.tagCount = :tagCount")})
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "TAG_ID")
    private Integer tagId;
    @Size(max = 30)
    @Column(name = "TAG_NAME")
    private String tagName;
    @Size(max = 30)
    @Column(name = "TAG_DESC")
    private String tagDesc;
    @Size(max = 30)
    @Column(name = "TAG_COUNT")
    private String tagCount;
    @JoinTable(name = "USERS_TAG_XREF", joinColumns = {
        @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private List<Users> usersList = new ArrayList();
    @ManyToMany(mappedBy = "tagsList")
    private List<Job> jobList = new ArrayList();
    @ManyToMany(mappedBy = "tagsList")
    private List<Projects> projectsList = new ArrayList();

    public Tags() {
    }

    public Tags(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getTagCount() {
        return tagCount;
    }

    public void setTagCount(String tagCount) {
        this.tagCount = tagCount;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    @XmlTransient
    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagId != null ? tagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tags)) {
            return false;
        }
        Tags other = (Tags) object;
        if ((this.tagId == null && other.tagId != null) || (this.tagId != null && !this.tagId.equals(other.tagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Tags[ tagId=" + tagId + " ]";
    }
    
}
