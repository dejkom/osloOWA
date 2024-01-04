/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "SIGN_Television")
@NamedQueries({
    @NamedQuery(name = "SIGNTelevision.findAll", query = "SELECT s FROM SIGNTelevision s"),
    @NamedQuery(name = "SIGNTelevision.findByIdTelevision", query = "SELECT s FROM SIGNTelevision s WHERE s.idTelevision = :idTelevision"),
    @NamedQuery(name = "SIGNTelevision.findByIpAddress", query = "SELECT s FROM SIGNTelevision s WHERE s.ipAddress = :ipAddress"),
    @NamedQuery(name = "SIGNTelevision.findByActive", query = "SELECT s FROM SIGNTelevision s WHERE s.active = :active"),
    @NamedQuery(name = "SIGNTelevision.findByLocation", query = "SELECT s FROM SIGNTelevision s WHERE s.location = :location"),
    @NamedQuery(name = "SIGNTelevision.findByHostName", query = "SELECT s FROM SIGNTelevision s WHERE s.hostName = :hostName")})
public class SIGNTelevision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTelevision")
    private Integer idTelevision;
    
    @Expose
    @Size(max = 50)
    @Column(name = "ipAddress")
    private String ipAddress;
    
    @Expose
    @Column(name = "active")
    private Boolean active;
    
    @Expose
    @Size(max = 85)
    @Column(name = "location")
    private String location;
    
    @Expose
    @Size(max = 128)
    @Column(name = "hostName")
    private String hostName;
    
    @Expose
    @JoinColumn(name = "idPresentation", referencedColumnName = "idPresentation") 
    @ManyToOne
    private SIGNPresentation idPresentation;

    public SIGNTelevision() {
    }
    
    public SIGNTelevision(String ipAddress, Boolean active, String location, String hostName, SIGNPresentation idPresentation) {
        this.ipAddress = ipAddress;
        this.active = active;
        this.location = location;
        this.hostName = hostName;
        this.idPresentation = idPresentation;
    }

    public SIGNTelevision(Integer idTelevision) {
        this.idTelevision = idTelevision;
    }

    public Integer getIdTelevision() {
        return idTelevision;
    }

    public void setIdTelevision(Integer idTelevision) {
        this.idTelevision = idTelevision;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public SIGNPresentation getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(SIGNPresentation idPresentation) {
        this.idPresentation = idPresentation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelevision != null ? idTelevision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SIGNTelevision)) {
            return false;
        }
        SIGNTelevision other = (SIGNTelevision) object;
        if ((this.idTelevision == null && other.idTelevision != null) || (this.idTelevision != null && !this.idTelevision.equals(other.idTelevision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.entity.SIGNTelevision[ idTelevision=" + idTelevision + " ]";
    }
    
}