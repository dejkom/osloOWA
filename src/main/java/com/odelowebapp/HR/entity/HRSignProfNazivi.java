/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "HRSignProfNazivi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRSignProfNazivi.findAll", query = "SELECT h FROM HRSignProfNazivi h"),
    @NamedQuery(name = "HRSignProfNazivi.findById", query = "SELECT h FROM HRSignProfNazivi h WHERE h.id = :id"),
    @NamedQuery(name = "HRSignProfNazivi.findByUradniNaziv", query = "SELECT h FROM HRSignProfNazivi h WHERE h.uradniNaziv = :uradniNaziv"),
    @NamedQuery(name = "HRSignProfNazivi.findByRazsirjenSlo", query = "SELECT h FROM HRSignProfNazivi h WHERE h.razsirjenSlo = :razsirjenSlo"),
    @NamedQuery(name = "HRSignProfNazivi.findByAngUradni", query = "SELECT h FROM HRSignProfNazivi h WHERE h.angUradni = :angUradni"),
    @NamedQuery(name = "HRSignProfNazivi.findByRazsirjenAng", query = "SELECT h FROM HRSignProfNazivi h WHERE h.razsirjenAng = :razsirjenAng"),
    @NamedQuery(name = "HRSignProfNazivi.findByOddelekSlo", query = "SELECT h FROM HRSignProfNazivi h WHERE h.oddelekSlo = :oddelekSlo"),
    @NamedQuery(name = "HRSignProfNazivi.findByOddelekAng", query = "SELECT h FROM HRSignProfNazivi h WHERE h.oddelekAng = :oddelekAng")})
public class HRSignProfNazivi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "UradniNaziv")
    private String uradniNaziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "RazsirjenSlo")
    private String razsirjenSlo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "AngUradni")
    private String angUradni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "RazsirjenAng")
    private String razsirjenAng;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "OddelekSlo")
    private String oddelekSlo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "OddelekAng")
    private String oddelekAng;

    public HRSignProfNazivi() {
    }

    public HRSignProfNazivi(Short id) {
        this.id = id;
    }

    public HRSignProfNazivi(Short id, String uradniNaziv, String razsirjenSlo, String angUradni, String razsirjenAng, String oddelekSlo, String oddelekAng) {
        this.id = id;
        this.uradniNaziv = uradniNaziv;
        this.razsirjenSlo = razsirjenSlo;
        this.angUradni = angUradni;
        this.razsirjenAng = razsirjenAng;
        this.oddelekSlo = oddelekSlo;
        this.oddelekAng = oddelekAng;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getUradniNaziv() {
        return uradniNaziv;
    }

    public void setUradniNaziv(String uradniNaziv) {
        this.uradniNaziv = uradniNaziv;
    }

    public String getRazsirjenSlo() {
        return razsirjenSlo;
    }

    public void setRazsirjenSlo(String razsirjenSlo) {
        this.razsirjenSlo = razsirjenSlo;
    }

    public String getAngUradni() {
        return angUradni;
    }

    public void setAngUradni(String angUradni) {
        this.angUradni = angUradni;
    }

    public String getRazsirjenAng() {
        return razsirjenAng;
    }

    public void setRazsirjenAng(String razsirjenAng) {
        this.razsirjenAng = razsirjenAng;
    }

    public String getOddelekSlo() {
        return oddelekSlo;
    }

    public void setOddelekSlo(String oddelekSlo) {
        this.oddelekSlo = oddelekSlo;
    }

    public String getOddelekAng() {
        return oddelekAng;
    }

    public void setOddelekAng(String oddelekAng) {
        this.oddelekAng = oddelekAng;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRSignProfNazivi)) {
            return false;
        }
        HRSignProfNazivi other = (HRSignProfNazivi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRSignProfNazivi[ id=" + id + " ]";
    }
    
}
