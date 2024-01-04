/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "OSEC_Line")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OSECLine.findAll", query = "SELECT o FROM OSECLine o"),
    @NamedQuery(name = "OSECLine.findByLineID", query = "SELECT o FROM OSECLine o WHERE o.lineID = :lineID"),
    @NamedQuery(name = "OSECLine.findByLineName", query = "SELECT o FROM OSECLine o WHERE o.lineName = :lineName"),
    @NamedQuery(name = "OSECLine.findByActive", query = "SELECT o FROM OSECLine o WHERE o.active = :active")})
public class OSECLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lineID")
    private Integer lineID;
    @Size(max = 2147483647)
    @Column(name = "lineName")
    private String lineName;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "lineIDfk")
    private List<OSECMachine> oSECMachineList;

    public OSECLine() {
    }

    public OSECLine(Integer lineID) {
        this.lineID = lineID;
    }

    public Integer getLineID() {
        return lineID;
    }

    public void setLineID(Integer lineID) {
        this.lineID = lineID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<OSECMachine> getOSECMachineList() {
        return oSECMachineList;
    }

    public void setOSECMachineList(List<OSECMachine> oSECMachineList) {
        this.oSECMachineList = oSECMachineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineID != null ? lineID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OSECLine)) {
            return false;
        }
        OSECLine other = (OSECLine) object;
        if ((this.lineID == null && other.lineID != null) || (this.lineID != null && !this.lineID.equals(other.lineID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.OSEC.entity.OSECLine[ lineID=" + lineID + " ]";
    }
    
}
