/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "PROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profile.findAll", query = "SELECT p FROM Profile p"),
    @NamedQuery(name = "Profile.findByUserId", query = "SELECT p FROM Profile p WHERE p.userId = :userId"),
    @NamedQuery(name = "Profile.findByDob", query = "SELECT p FROM Profile p WHERE p.dob = :dob"),
    @NamedQuery(name = "Profile.findBySex", query = "SELECT p FROM Profile p WHERE p.sex = :sex"),
    @NamedQuery(name = "Profile.findByPhone", query = "SELECT p FROM Profile p WHERE p.phone = :phone"),
    @NamedQuery(name = "Profile.findByAddress", query = "SELECT p FROM Profile p WHERE p.address = :address"),
    @NamedQuery(name = "Profile.findByReligion", query = "SELECT p FROM Profile p WHERE p.religion = :religion"),
    @NamedQuery(name = "Profile.findByNationality", query = "SELECT p FROM Profile p WHERE p.nationality = :nationality"),
    @NamedQuery(name = "Profile.findByBalance", query = "SELECT p FROM Profile p WHERE p.balance = :balance"),
    @NamedQuery(name = "Profile.findByTimeCreated", query = "SELECT p FROM Profile p WHERE p.timeCreated = :timeCreated"),
    @NamedQuery(name = "Profile.findByTimeModified", query = "SELECT p FROM Profile p WHERE p.timeModified = :timeModified")})
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Size(max = 30)
    @Column(name = "SEX")
    private String sex;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 50)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 30)
    @Column(name = "RELIGION")
    private String religion;
    @Size(max = 30)
    @Column(name = "NATIONALITY")
    private String nationality;
    @Lob
    @Column(name = "PICTURE")
    private byte[] picture;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BALANCE")
    private Double balance;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @Column(name = "TIME_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeModified;
    @Lob
    @Column(name = "CV")
    private byte[] cv;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Users users;

    public Profile() {
    }

    public Profile(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

   

    @XmlTransient
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Profile[ userId=" + userId + " ]";
    }
    
}
