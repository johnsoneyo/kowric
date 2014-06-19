/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Lob;
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
@Table(name = "PROJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findByProjectId", query = "SELECT p FROM Projects p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "Projects.findByProjectName", query = "SELECT p FROM Projects p WHERE p.projectName = :projectName"),
    @NamedQuery(name = "Projects.findByProjectDesc", query = "SELECT p FROM Projects p WHERE p.projectDesc = :projectDesc"),
    @NamedQuery(name = "Projects.findByRegprojectRegno", query = "SELECT p FROM Projects p WHERE p.regprojectRegno = :regprojectRegno"),
    @NamedQuery(name = "Projects.findByRegprojectRegdate", query = "SELECT p FROM Projects p WHERE p.regprojectRegdate = :regprojectRegdate"),
    @NamedQuery(name = "Projects.findByRegprojectAddress", query = "SELECT p FROM Projects p WHERE p.regprojectAddress = :regprojectAddress"),
    @NamedQuery(name = "Projects.findByRegprojectShareno", query = "SELECT p FROM Projects p WHERE p.regprojectShareno = :regprojectShareno"),
    @NamedQuery(name = "Projects.findByRegprojectSharecost", query = "SELECT p FROM Projects p WHERE p.regprojectSharecost = :regprojectSharecost"),
    @NamedQuery(name = "Projects.findByRegprojectBegundate", query = "SELECT p FROM Projects p WHERE p.regprojectBegundate = :regprojectBegundate"),
    @NamedQuery(name = "Projects.findByUnregprojectRequiredno", query = "SELECT p FROM Projects p WHERE p.unregprojectRequiredno = :unregprojectRequiredno"),
    @NamedQuery(name = "Projects.findByUnregprojectCapitalmax", query = "SELECT p FROM Projects p WHERE p.unregprojectCapitalmax = :unregprojectCapitalmax"),
    @NamedQuery(name = "Projects.findByUnregprojectCapitalmin", query = "SELECT p FROM Projects p WHERE p.unregprojectCapitalmin = :unregprojectCapitalmin"),
    @NamedQuery(name = "Projects.findByProjectNoOfShares", query = "SELECT p FROM Projects p WHERE p.projectNoOfShares = :projectNoOfShares"),
    @NamedQuery(name = "Projects.findByUnregprojectMaximumShares", query = "SELECT p FROM Projects p WHERE p.unregprojectMaximumShares = :unregprojectMaximumShares"),
    @NamedQuery(name = "Projects.findByUnregprojectMinimumShares", query = "SELECT p FROM Projects p WHERE p.unregprojectMinimumShares = :unregprojectMinimumShares"),
    @NamedQuery(name = "Projects.findByProjectFb", query = "SELECT p FROM Projects p WHERE p.projectFb = :projectFb"),
    @NamedQuery(name = "Projects.findByProjectTwitter", query = "SELECT p FROM Projects p WHERE p.projectTwitter = :projectTwitter"),
    @NamedQuery(name = "Projects.findByProjectGoogle", query = "SELECT p FROM Projects p WHERE p.projectGoogle = :projectGoogle"),
    @NamedQuery(name = "Projects.findByProjectLink", query = "SELECT p FROM Projects p WHERE p.projectLink = :projectLink"),
    @NamedQuery(name = "Projects.findByProjectServices", query = "SELECT p FROM Projects p WHERE p.projectServices = :projectServices"),
    @NamedQuery(name = "Projects.findByProjectWouldyou", query = "SELECT p FROM Projects p WHERE p.projectWouldyou = :projectWouldyou"),
    @NamedQuery(name = "Projects.findByProjectStatus", query = "SELECT p FROM Projects p WHERE p.projectStatus = :projectStatus"),
    @NamedQuery(name = "Projects.findByProjectType", query = "SELECT p FROM Projects p WHERE p.projectType = :projectType"),
    @NamedQuery(name = "Projects.findByTimeCreated", query = "SELECT p FROM Projects p WHERE p.timeCreated = :timeCreated")})
public class Projects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "PROJECT_ID")
    private Integer projectId;
    @Size(max = 30)
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Size(max = 30)
    @Column(name = "PROJECT_DESC")
    private String projectDesc;
    @Size(max = 30)
    @Column(name = "REGPROJECT_REGNO")
    private String regprojectRegno;
    @Column(name = "REGPROJECT_REGDATE")
    @Temporal(TemporalType.DATE)
    private Date regprojectRegdate;
    @Size(max = 50)
    @Column(name = "REGPROJECT_ADDRESS")
    private String regprojectAddress;
    @Column(name = "REGPROJECT_SHARENO")
    private Integer regprojectShareno;
    @Size(max = 30)
    @Column(name = "REGPROJECT_SHARECOST")
    private String regprojectSharecost;
    @Column(name = "REGPROJECT_BEGUNDATE")
    @Temporal(TemporalType.DATE)
    private Date regprojectBegundate;
    @Column(name = "UNREGPROJECT_REQUIREDNO")
    private Integer unregprojectRequiredno;
    @Size(max = 30)
    @Column(name = "UNREGPROJECT_CAPITALMAX")
    private String unregprojectCapitalmax;
    @Size(max = 30)
    @Column(name = "UNREGPROJECT_CAPITALMIN")
    private String unregprojectCapitalmin;
    @Column(name = "PROJECT_NO_OF_SHARES")
    private Integer projectNoOfShares;
    @Column(name = "UNREGPROJECT_MAXIMUM_SHARES")
    private Integer unregprojectMaximumShares;
    @Column(name = "UNREGPROJECT_MINIMUM_SHARES")
    private Integer unregprojectMinimumShares;
    @Size(max = 50)
    @Column(name = "PROJECT_FB")
    private String projectFb;
    @Size(max = 50)
    @Column(name = "PROJECT_TWITTER")
    private String projectTwitter;
    @Size(max = 50)
    @Column(name = "PROJECT_GOOGLE")
    private String projectGoogle;
    @Size(max = 50)
    @Column(name = "PROJECT_LINK")
    private String projectLink;
    @Size(max = 50)
    @Column(name = "PROJECT_SERVICES")
    private String projectServices;
    @Column(name = "PROJECT_WOULDYOU")
    private Integer projectWouldyou;
    @Size(max = 30)
    @Column(name = "PROJECT_STATUS")
    private String projectStatus;
    @Size(max = 30)
    @Column(name = "PROJECT_TYPE")
    private String projectType;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Lob
    @Column(name = "PROJECT_IMAGE")
    private byte[] projectImage;
    @JoinTable(name = "USER_PROJECTS_XREF", joinColumns = {
        @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private List<Users> usersList;
    @JoinTable(name = "PROJECT_TAG_XREF", joinColumns = {
        @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID")})
    @ManyToMany
    private List<Tags> tagsList;
    @OneToMany(mappedBy = "projectId")
    private List<InvestmentList> investmentListList = new ArrayList();
    @OneToMany(mappedBy = "projectId")
    private List<Files> filesList = new ArrayList();
    @OneToMany(mappedBy = "projectId")
    private List<Subscription> subscriptionList = new ArrayList();
    @OneToMany(mappedBy = "projectId")
    private List<Job> jobList = new ArrayList();
    @OneToMany(mappedBy = "projectId")
    private List<Comment> commentList = new ArrayList();
    @OneToMany(mappedBy = "projectId")
    private List<Rating> ratingList = new ArrayList();
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    @ManyToOne
    private Category categoryId;
    @OneToMany(mappedBy = "projectId")
    private List<Shareholder> shareholderList = new ArrayList();

    public Projects() {
    }

    public Projects(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getRegprojectRegno() {
        return regprojectRegno;
    }

    public void setRegprojectRegno(String regprojectRegno) {
        this.regprojectRegno = regprojectRegno;
    }

    public Date getRegprojectRegdate() {
        return regprojectRegdate;
    }

    public void setRegprojectRegdate(Date regprojectRegdate) {
        this.regprojectRegdate = regprojectRegdate;
    }

    public String getRegprojectAddress() {
        return regprojectAddress;
    }

    public void setRegprojectAddress(String regprojectAddress) {
        this.regprojectAddress = regprojectAddress;
    }

    public Integer getRegprojectShareno() {
        return regprojectShareno;
    }

    public void setRegprojectShareno(Integer regprojectShareno) {
        this.regprojectShareno = regprojectShareno;
    }

    public String getRegprojectSharecost() {
        return regprojectSharecost;
    }

    public void setRegprojectSharecost(String regprojectSharecost) {
        this.regprojectSharecost = regprojectSharecost;
    }

    public Date getRegprojectBegundate() {
        return regprojectBegundate;
    }

    public void setRegprojectBegundate(Date regprojectBegundate) {
        this.regprojectBegundate = regprojectBegundate;
    }

    public Integer getUnregprojectRequiredno() {
        return unregprojectRequiredno;
    }

    public void setUnregprojectRequiredno(Integer unregprojectRequiredno) {
        this.unregprojectRequiredno = unregprojectRequiredno;
    }

    public String getUnregprojectCapitalmax() {
        return unregprojectCapitalmax;
    }

    public void setUnregprojectCapitalmax(String unregprojectCapitalmax) {
        this.unregprojectCapitalmax = unregprojectCapitalmax;
    }

    public String getUnregprojectCapitalmin() {
        return unregprojectCapitalmin;
    }

    public void setUnregprojectCapitalmin(String unregprojectCapitalmin) {
        this.unregprojectCapitalmin = unregprojectCapitalmin;
    }

    public Integer getProjectNoOfShares() {
        return projectNoOfShares;
    }

    public void setProjectNoOfShares(Integer projectNoOfShares) {
        this.projectNoOfShares = projectNoOfShares;
    }

    public Integer getUnregprojectMaximumShares() {
        return unregprojectMaximumShares;
    }

    public void setUnregprojectMaximumShares(Integer unregprojectMaximumShares) {
        this.unregprojectMaximumShares = unregprojectMaximumShares;
    }

    public Integer getUnregprojectMinimumShares() {
        return unregprojectMinimumShares;
    }

    public void setUnregprojectMinimumShares(Integer unregprojectMinimumShares) {
        this.unregprojectMinimumShares = unregprojectMinimumShares;
    }

    public String getProjectFb() {
        return projectFb;
    }

    public void setProjectFb(String projectFb) {
        this.projectFb = projectFb;
    }

    public String getProjectTwitter() {
        return projectTwitter;
    }

    public void setProjectTwitter(String projectTwitter) {
        this.projectTwitter = projectTwitter;
    }

    public String getProjectGoogle() {
        return projectGoogle;
    }

    public void setProjectGoogle(String projectGoogle) {
        this.projectGoogle = projectGoogle;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getProjectServices() {
        return projectServices;
    }

    public void setProjectServices(String projectServices) {
        this.projectServices = projectServices;
    }

    public Integer getProjectWouldyou() {
        return projectWouldyou;
    }

    public void setProjectWouldyou(Integer projectWouldyou) {
        this.projectWouldyou = projectWouldyou;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public byte[] getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(byte[] projectImage) {
        this.projectImage = projectImage;
    }

   
    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    @XmlTransient
    public List<InvestmentList> getInvestmentListList() {
        return investmentListList;
    }

    public void setInvestmentListList(List<InvestmentList> investmentListList) {
        this.investmentListList = investmentListList;
    }

    @XmlTransient
    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
    }

    @XmlTransient
    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    @XmlTransient
    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @XmlTransient
    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @XmlTransient
    public List<Shareholder> getShareholderList() {
        return shareholderList;
    }

    public void setShareholderList(List<Shareholder> shareholderList) {
        this.shareholderList = shareholderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Projects[ projectId=" + projectId + " ]";
    }
    
}
