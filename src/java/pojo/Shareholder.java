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
@Table(name = "SHAREHOLDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shareholder.findAll", query = "SELECT s FROM Shareholder s"),
    @NamedQuery(name = "Shareholder.findByShareholderId", query = "SELECT s FROM Shareholder s WHERE s.shareholderId = :shareholderId"),
    @NamedQuery(name = "Shareholder.findByShareholderName", query = "SELECT s FROM Shareholder s WHERE s.shareholderName = :shareholderName"),
    @NamedQuery(name = "Shareholder.findByShareholderDetail", query = "SELECT s FROM Shareholder s WHERE s.shareholderDetail = :shareholderDetail"),
    @NamedQuery(name = "Shareholder.findByShareholderPosition", query = "SELECT s FROM Shareholder s WHERE s.shareholderPosition = :shareholderPosition"),
    @NamedQuery(name = "Shareholder.findByShareholderPhone", query = "SELECT s FROM Shareholder s WHERE s.shareholderPhone = :shareholderPhone"),
    @NamedQuery(name = "Shareholder.findByShareholderEmail", query = "SELECT s FROM Shareholder s WHERE s.shareholderEmail = :shareholderEmail")})
public class Shareholder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "SHAREHOLDER_ID")
    private Integer shareholderId;
    @Size(max = 40)
    @Column(name = "SHAREHOLDER_NAME")
    private String shareholderName;
    @Size(max = 40)
    @Column(name = "SHAREHOLDER_DETAIL")
    private String shareholderDetail;
    @Size(max = 30)
    @Column(name = "SHAREHOLDER_POSITION")
    private String shareholderPosition;
    @Size(max = 30)
    @Column(name = "SHAREHOLDER_PHONE")
    private String shareholderPhone;
    @Size(max = 70)
    @Column(name = "SHAREHOLDER_EMAIL")
    private String shareholderEmail;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private Projects projectId;

    public Shareholder() {
    }

    public Shareholder(Integer shareholderId) {
        this.shareholderId = shareholderId;
    }

    public Integer getShareholderId() {
        return shareholderId;
    }

    public void setShareholderId(Integer shareholderId) {
        this.shareholderId = shareholderId;
    }

    public String getShareholderName() {
        return shareholderName;
    }

    public void setShareholderName(String shareholderName) {
        this.shareholderName = shareholderName;
    }

    public String getShareholderDetail() {
        return shareholderDetail;
    }

    public void setShareholderDetail(String shareholderDetail) {
        this.shareholderDetail = shareholderDetail;
    }

    public String getShareholderPosition() {
        return shareholderPosition;
    }

    public void setShareholderPosition(String shareholderPosition) {
        this.shareholderPosition = shareholderPosition;
    }

    public String getShareholderPhone() {
        return shareholderPhone;
    }

    public void setShareholderPhone(String shareholderPhone) {
        this.shareholderPhone = shareholderPhone;
    }

    public String getShareholderEmail() {
        return shareholderEmail;
    }

    public void setShareholderEmail(String shareholderEmail) {
        this.shareholderEmail = shareholderEmail;
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
        hash += (shareholderId != null ? shareholderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shareholder)) {
            return false;
        }
        Shareholder other = (Shareholder) object;
        if ((this.shareholderId == null && other.shareholderId != null) || (this.shareholderId != null && !this.shareholderId.equals(other.shareholderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Shareholder[ shareholderId=" + shareholderId + " ]";
    }
    
}
