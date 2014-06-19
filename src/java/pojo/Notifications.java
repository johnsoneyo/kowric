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
@Table(name = "NOTIFICATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notifications.findAll", query = "SELECT n FROM Notifications n"),
    @NamedQuery(name = "Notifications.findByNotificationId", query = "SELECT n FROM Notifications n WHERE n.notificationId = :notificationId"),
    @NamedQuery(name = "Notifications.findByNotificationMessage", query = "SELECT n FROM Notifications n WHERE n.notificationMessage = :notificationMessage"),
    @NamedQuery(name = "Notifications.findByRed", query = "SELECT n FROM Notifications n WHERE n.red = :red"),
    @NamedQuery(name = "Notifications.findByTimeCreated", query = "SELECT n FROM Notifications n WHERE n.timeCreated = :timeCreated")})
public class Notifications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "NOTIFICATION_ID")
    private Integer notificationId;
    @Size(max = 50)
    @Column(name = "NOTIFICATION_MESSAGE")
    private String notificationMessage;
    @Column(name = "RED")
    private Serializable red;
    @Column(name = "TIME_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;
    @JoinTable(name = "USER_NOTIFCATION_XREF", joinColumns = {
        @JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "NOTIFICATION_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private List<Users> usersList;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;

    public Notifications() {
    }

    public Notifications(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Serializable getRed() {
        return red;
    }

    public void setRed(Serializable red) {
        this.red = red;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationId != null ? notificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notifications)) {
            return false;
        }
        Notifications other = (Notifications) object;
        if ((this.notificationId == null && other.notificationId != null) || (this.notificationId != null && !this.notificationId.equals(other.notificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Notifications[ notificationId=" + notificationId + " ]";
    }
    
}
