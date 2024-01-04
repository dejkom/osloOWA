/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author dematjasic
 */
@Embeddable
public class HRVisitLogPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "visitID")
    private int visitID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "guest")
    private String guest;

    public HRVisitLogPK() {
    }

    public HRVisitLogPK(int visitID, String guest) {
        this.visitID = visitID;
        this.guest = guest;
    }

    public int getVisitID() {
        return visitID;
    }

    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) visitID;
        hash += (guest != null ? guest.hashCode() : 0);
        return hash;
    }
    
    public int getRowKey() { 
        return this.hashCode(); 
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRVisitLogPK)) {
            return false;
        }
        HRVisitLogPK other = (HRVisitLogPK) object;
        if (this.visitID != other.visitID) {
            return false;
        }
        if ((this.guest == null && other.guest != null) || (this.guest != null && !this.guest.equals(other.guest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRVisitLogPK[ visitID=" + visitID + ", guest=" + guest + " ]";
    }
    
}
