/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author dematjasic
 */
@Entity
@Table(name = "OSEC_loginLog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OSECloginLog.findAll", query = "SELECT o FROM OSECloginLog o"),
    @NamedQuery(name = "OSECloginLog.findByLoginId", query = "SELECT o FROM OSECloginLog o WHERE o.loginId = :loginId"),
    @NamedQuery(name = "OSECloginLog.findByLineID", query = "SELECT o FROM OSECloginLog o WHERE o.lineID = :lineID"),
    @NamedQuery(name = "OSECloginLog.findByWorkerCard", query = "SELECT o FROM OSECloginLog o WHERE o.workerCard = :workerCard"),
    @NamedQuery(name = "OSECloginLog.findByExternalId", query = "SELECT o FROM OSECloginLog o WHERE o.externalId = :externalId"),
    @NamedQuery(name = "OSECloginLog.findByLoginStart", query = "SELECT o FROM OSECloginLog o WHERE o.loginStart = :loginStart"),
    @NamedQuery(name = "OSECloginLog.findByLoginEnd", query = "SELECT o FROM OSECloginLog o WHERE o.loginEnd = :loginEnd"),
    @NamedQuery(name = "OSECloginLog.findByLoginDate", query = "SELECT o FROM OSECloginLog o WHERE o.loginDate = :loginDate"),
    @NamedQuery(name = "OSECloginLog.findByLoginShift", query = "SELECT o FROM OSECloginLog o WHERE o.loginShift = :loginShift")})
public class OSECloginLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "loginId")
    private Integer loginId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lineID")
    private int lineID;
    @Size(max = 50)
    @Column(name = "workerCard")
    private String workerCard;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ExternalId")
    private String externalId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "loginStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginStart;
    @Column(name = "loginEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginEnd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "loginDate")
    @Temporal(TemporalType.DATE)
    private Date loginDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "loginShift")
    private int loginShift;

    public OSECloginLog() {
    }

    public OSECloginLog(Integer loginId) {
        this.loginId = loginId;
    }

    public OSECloginLog(Integer loginId, int lineID, String externalId, Date loginStart, Date loginDate, int loginShift) {
        this.loginId = loginId;
        this.lineID = lineID;
        this.externalId = externalId;
        this.loginStart = loginStart;
        this.loginDate = loginDate;
        this.loginShift = loginShift;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public String getWorkerCard() {
        return workerCard;
    }

    public void setWorkerCard(String workerCard) {
        this.workerCard = workerCard;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Date getLoginStart() {
        return loginStart;
    }

    public void setLoginStart(Date loginStart) {
        this.loginStart = loginStart;
    }

    public Date getLoginEnd() {
        return loginEnd;
    }

    public void setLoginEnd(Date loginEnd) {
        this.loginEnd = loginEnd;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public int getLoginShift() {
        return loginShift;
    }

    public void setLoginShift(int loginShift) {
        this.loginShift = loginShift;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OSECloginLog)) {
            return false;
        }
        OSECloginLog other = (OSECloginLog) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.OSEC.entity.OSECloginLog[ loginId=" + loginId + " ]";
    }
    
}
