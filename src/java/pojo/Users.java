/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByStatus", query = "SELECT u FROM Users u WHERE u.status = :status"),
    @NamedQuery(name = "Users.findBySocialId", query = "SELECT u FROM Users u WHERE u.socialId = :socialId"),
    @NamedQuery(name = "Users.findByUserType", query = "SELECT u FROM Users u WHERE u.userType = :userType"),
    @NamedQuery(name = "Users.findByFacebookId", query = "SELECT u FROM Users u WHERE u.facebookId = :facebookId"),
    @NamedQuery(name = "Users.findByTwitterId", query = "SELECT u FROM Users u WHERE u.twitterId = :twitterId"),
    @NamedQuery(name = "Users.findByGoogleId", query = "SELECT u FROM Users u WHERE u.googleId = :googleId"),
    @NamedQuery(name = "Users.findByLinkedinId", query = "SELECT u FROM Users u WHERE u.linkedinId = :linkedinId"),
    @NamedQuery(name = "Users.findByHashpass", query = "SELECT u FROM Users u WHERE u.hashpass = :hashpass")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "USER_ID")
    private Integer userId;
    @Size(max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 30)
    @Column(name = "LAST_NAME")
    private String lastName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 80)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 30)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 50)
    @Column(name = "SOCIAL_ID")
    private String socialId;
    @Size(max = 30)
    @Column(name = "USER_TYPE")
    private String userType;
    @Size(max = 50)
    @Column(name = "FACEBOOK_ID")
    private String facebookId;
    @Size(max = 50)
    @Column(name = "TWITTER_ID")
    private String twitterId;
    @Size(max = 50)
    @Column(name = "GOOGLE_ID")
    private String googleId;
    @Size(max = 50)
    @Column(name = "LINKEDIN_ID")
    private String linkedinId;
    @Size(max = 40)
    @Column(name = "HASHPASS")
    private String hashpass;
    @ManyToMany(mappedBy = "usersList")
    private List<Notifications> notificationsList;
    @JoinTable(name = "USERS_TAG_XREF", joinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID")})
    @ManyToMany
    private List<Tags> tagsList = new ArrayList();
    @ManyToMany(mappedBy = "usersList")
    private List<Projects> projectsList = new ArrayList();
    @OneToMany(mappedBy = "userId")
    private List<InvestmentList> investmentListList = new ArrayList();
    @OneToMany(mappedBy = "userId")
    private List<Subscription> subscriptionList = new ArrayList();
    @OneToMany(mappedBy = "userId")
    private List<Notifications> notificationsList1 = new ArrayList();
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "users")
    private Profile profile;
    @OneToMany(mappedBy = "userId")
    private List<ProjectJobList> projectJobListList = new ArrayList();
    @OneToMany(mappedBy = "commentUser")
    private List<Comment> commentList = new ArrayList();
    @OneToMany(mappedBy = "userId")
    private List<Rating> ratingList = new ArrayList();

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public String getHashpass() {
        return hashpass;
    }

    public void setHashpass(String hashpass) {
        this.hashpass = hashpass;
    }

    @XmlTransient
    public List<Notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @XmlTransient
    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    @XmlTransient
    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    @XmlTransient
    public List<InvestmentList> getInvestmentListList() {
        return investmentListList;
    }

    public void setInvestmentListList(List<InvestmentList> investmentListList) {
        this.investmentListList = investmentListList;
    }

    @XmlTransient
    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    @XmlTransient
    public List<Notifications> getNotificationsList1() {
        return notificationsList1;
    }

    public void setNotificationsList1(List<Notifications> notificationsList1) {
        this.notificationsList1 = notificationsList1;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @XmlTransient
    public List<ProjectJobList> getProjectJobListList() {
        return projectJobListList;
    }

    public void setProjectJobListList(List<ProjectJobList> projectJobListList) {
        this.projectJobListList = projectJobListList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Users[ userId=" + userId + " ]";
    }
    
}
