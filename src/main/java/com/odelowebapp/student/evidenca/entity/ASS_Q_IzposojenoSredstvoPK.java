/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.student.evidenca.entity;

import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dematjasic
 */
@Embeddable
public class ASS_Q_IzposojenoSredstvoPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDAsset")
    private ASSAsset assetID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LogExternalID")
    private VCodeksUsersAktualniZaposleni person;

    public ASS_Q_IzposojenoSredstvoPK() {
    }

    public ASS_Q_IzposojenoSredstvoPK(ASSAsset assetID, VCodeksUsersAktualniZaposleni person) {
        this.assetID = assetID;
        this.person = person;
    }

    public ASSAsset getAssetID() {
        return assetID;
    }

    public void setAssetID(ASSAsset assetID) {
        this.assetID = assetID;
    }

    public VCodeksUsersAktualniZaposleni getPerson() {
        return person;
    }

    public void setPerson(VCodeksUsersAktualniZaposleni person) {
        this.person = person;
    }
    
    
    
}
