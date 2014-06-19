/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@Entity
@Table(name = "COMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentId = :commentId"),
    @NamedQuery(name = "Comment.findByCommentMessage", query = "SELECT c FROM Comment c WHERE c.commentMessage = :commentMessage"),
    @NamedQuery(name = "Comment.findByTimeSaid", query = "SELECT c FROM Comment c WHERE c.timeSaid = :timeSaid")})
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "COMMENT_ID")
    private Integer commentId;
    @Size(max = 200)
    @Column(name = "COMMENT_MESSAGE")
    private String commentMessage;
    @Column(name = "TIME_SAID")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSaid;
    @JoinColumn(name = "COMMENT_USER", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users commentUser;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private Projects projectId;

    public Comment() {
    }

    public Comment(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public Date getTimeSaid() {
        return timeSaid;
    }

    public void setTimeSaid(Date timeSaid) {
        this.timeSaid = timeSaid;
    }

    public Users getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(Users commentUser) {
        this.commentUser = commentUser;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Comment[ commentId=" + commentId + " ]";
    }
    
}
