/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "ASS_Log")
@NamedQueries({
    @NamedQuery(name = "ASSLog.findAll", query = "SELECT a FROM ASSLog a"),
    @NamedQuery(name = "ASSLog.findByIDLog", query = "SELECT a FROM ASSLog a WHERE a.iDLog = :iDLog"),
    @NamedQuery(name = "ASSLog.findByLogTimestamp", query = "SELECT a FROM ASSLog a WHERE a.logTimestamp = :logTimestamp"),
    @NamedQuery(name = "ASSLog.findByLogExternalID", query = "SELECT a FROM ASSLog a WHERE a.logExternalID = :logExternalID"),
    @NamedQuery(name = "ASSLog.findByLogComment", query = "SELECT a FROM ASSLog a WHERE a.logComment = :logComment"),
    @NamedQuery(name = "ASSLog.findByLogQuantity", query = "SELECT a FROM ASSLog a WHERE a.logQuantity = :logQuantity")})
public class ASSLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDLog")
    private Integer iDLog;
    @Column(name = "LogTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logTimestamp;
    @Size(max = 10)
    @Column(name = "LogExternalID")
    private String logExternalID;
    @Size(max = 500)
    @Column(name = "LogComment")
    private String logComment;
    @Column(name = "LogQuantity")
    private Integer logQuantity;
    @JoinColumn(name = "IDLogAsset", referencedColumnName = "IDAsset")
    @ManyToOne(optional = false)
    private ASSAsset iDLogAsset;
    @JoinColumn(name = "IDLogStatus", referencedColumnName = "IDStatus")
    @ManyToOne
    private ASSStatus iDLogStatus;

    public ASSLog() {
    }

    public ASSLog(Integer iDLog) {
        this.iDLog = iDLog;
    }

    public Integer getIDLog() {
        return iDLog;
    }

    public void setIDLog(Integer iDLog) {
        this.iDLog = iDLog;
    }

    public Date getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Date logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public String getLogExternalID() {
        return logExternalID;
    }

    public void setLogExternalID(String logExternalID) {
        this.logExternalID = logExternalID;
    }

    public String getLogComment() {
        return logComment;
    }

    public void setLogComment(String logComment) {
        this.logComment = logComment;
    }

    public Integer getLogQuantity() {
        return logQuantity;
    }

    public void setLogQuantity(Integer logQuantity) {
        this.logQuantity = logQuantity;
    }

    public ASSAsset getIDLogAsset() {
        return iDLogAsset;
    }

    public void setIDLogAsset(ASSAsset iDLogAsset) {
        this.iDLogAsset = iDLogAsset;
    }

    public ASSStatus getIDLogStatus() {
        return iDLogStatus;
    }

    public void setIDLogStatus(ASSStatus iDLogStatus) {
        this.iDLogStatus = iDLogStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDLog != null ? iDLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASSLog)) {
            return false;
        }
        ASSLog other = (ASSLog) object;
        if ((this.iDLog == null && other.iDLog != null) || (this.iDLog != null && !this.iDLog.equals(other.iDLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.evidenca.entity.ASSLog[ iDLog=" + iDLog + " ]";
    }
    
}
