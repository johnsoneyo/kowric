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
@Table(name = "INVESTMENT_LIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvestmentList.findAll", query = "SELECT i FROM InvestmentList i"),
    @NamedQuery(name = "InvestmentList.findByInvestmentId", query = "SELECT i FROM InvestmentList i WHERE i.investmentId = :investmentId"),
    @NamedQuery(name = "InvestmentList.findByAmountRange", query = "SELECT i FROM InvestmentList i WHERE i.amountRange = :amountRange")})
public class InvestmentList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
     @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @NotNull
    @Column(name = "INVESTMENT_ID")
    private Integer investmentId;
    @Size(max = 30)
    @Column(name = "AMOUNT_RANGE")
    private String amountRange;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users userId;
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
    @ManyToOne
    private Projects projectId;

    public InvestmentList() {
    }

    public InvestmentList(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getAmountRange() {
        return amountRange;
    }

    public void setAmountRange(String amountRange) {
        this.amountRange = amountRange;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        hash += (investmentId != null ? investmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentList)) {
            return false;
        }
        InvestmentList other = (InvestmentList) object;
        if ((this.investmentId == null && other.investmentId != null) || (this.investmentId != null && !this.investmentId.equals(other.investmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.InvestmentList[ investmentId=" + investmentId + " ]";
    }
    
}
