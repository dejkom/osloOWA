/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "HRVisitLog")
@NamedQueries({
    @NamedQuery(name = "HRVisitLog.findAll", query = "SELECT h FROM HRVisitLog h"),
    @NamedQuery(name = "HRVisitLog.findByVisitID", query = "SELECT h FROM HRVisitLog h WHERE h.hRVisitLogPK.visitID = :visitID"),
    @NamedQuery(name = "HRVisitLog.findByGuest", query = "SELECT h FROM HRVisitLog h WHERE h.hRVisitLogPK.guest = :guest"),
    @NamedQuery(name = "HRVisitLog.findByTimeIn", query = "SELECT h FROM HRVisitLog h WHERE h.timeIn = :timeIn"),
    @NamedQuery(name = "HRVisitLog.findByTimeOut", query = "SELECT h FROM HRVisitLog h WHERE h.timeOut = :timeOut"),
    @NamedQuery(name = "HRVisitLog.findByOdeloPersonRecipient", query = "SELECT h FROM HRVisitLog h WHERE h.odeloPersonRecipient = :odeloPersonRecipient"),
    @NamedQuery(name = "HRVisitLog.findBySignature", query = "SELECT h FROM HRVisitLog h WHERE h.signature = :signature")})
public class HRVisitLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HRVisitLogPK hRVisitLogPK;
    @Column(name = "timeIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "timeOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;
    @Column(name = "odeloPersonRecipient")
    private Integer odeloPersonRecipient;
    @Size(max = 2147483647)
    @Column(name = "signature")
    private String signature;
    @Basic(optional = false)
    @Column(name = "agreeWithTerms")
    private boolean agreeWithTerms;

    public HRVisitLog() {
    }

    public HRVisitLog(HRVisitLogPK hRVisitLogPK) {
        this.hRVisitLogPK = hRVisitLogPK;
    }

    public HRVisitLog(int visitID, String guest) {
        this.hRVisitLogPK = new HRVisitLogPK(visitID, guest);
    }

    public HRVisitLogPK getHRVisitLogPK() {
        return hRVisitLogPK;
    }

    public void setHRVisitLogPK(HRVisitLogPK hRVisitLogPK) {
        this.hRVisitLogPK = hRVisitLogPK;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getOdeloPersonRecipient() {
        return odeloPersonRecipient;
    }

    public void setOdeloPersonRecipient(Integer odeloPersonRecipient) {
        this.odeloPersonRecipient = odeloPersonRecipient;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isAgreeWithTerms() {
        return agreeWithTerms;
    }

    public void setAgreeWithTerms(boolean agreeWithTerms) {
        this.agreeWithTerms = agreeWithTerms;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hRVisitLogPK != null ? hRVisitLogPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRVisitLog)) {
            return false;
        }
        HRVisitLog other = (HRVisitLog) object;
        if ((this.hRVisitLogPK == null && other.hRVisitLogPK != null) || (this.hRVisitLogPK != null && !this.hRVisitLogPK.equals(other.hRVisitLogPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRVisitLog[ hRVisitLogPK=" + hRVisitLogPK + " ]";
    }
    
}
