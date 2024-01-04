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
import java.util.Objects;

/**
 *
 * @author dematjasic
 */

@Embeddable
public class VCodeksObiskovalciPK implements Serializable{
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "visitId")
    private int visitId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "guest")
    private String guest;

    public VCodeksObiskovalciPK(int visitId, String guest) {
        this.visitId = visitId;
        this.guest = guest;
    }

    public VCodeksObiskovalciPK() {
    }
    
    public int getRowKeyDean(){
        return this.hashCode();
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.visitId;
        hash = 59 * hash + Objects.hashCode(this.guest);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VCodeksObiskovalciPK other = (VCodeksObiskovalciPK) obj;
        if (this.visitId != other.visitId) {
            return false;
        }
        if (!Objects.equals(this.guest, other.guest)) {
            return false;
        }
        return true;
    }
    
    
    
}
