/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "OSEC_Machine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OSECMachine.findAll", query = "SELECT o FROM OSECMachine o"),
    @NamedQuery(name = "OSECMachine.findByMachineID", query = "SELECT o FROM OSECMachine o WHERE o.machineID = :machineID"),
    @NamedQuery(name = "OSECMachine.findByMachineName", query = "SELECT o FROM OSECMachine o WHERE o.machineName = :machineName"),
    @NamedQuery(name = "OSECMachine.findByActive", query = "SELECT o FROM OSECMachine o WHERE o.active = :active")})
public class OSECMachine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machineID")
    private Integer machineID;
    @Size(max = 2147483647)
    @Column(name = "machineName")
    private String machineName;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "lineID_fk", referencedColumnName = "lineID")
    @ManyToOne
    private OSECLine lineIDfk;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "oSECMachine")
//    private OSECDowntime oSECDowntime;
    @OneToMany(mappedBy = "oSECMachine")
    private List<OSECDowntime> oSECDowntimeList;

    public OSECMachine() {
    }

    public OSECMachine(Integer machineID) {
        this.machineID = machineID;
    }

    public Integer getMachineID() {
        return machineID;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public OSECLine getLineIDfk() {
        return lineIDfk;
    }

    public void setLineIDfk(OSECLine lineIDfk) {
        this.lineIDfk = lineIDfk;
    }

    public List<OSECDowntime> getoSECDowntimeList() {
        return oSECDowntimeList;
    }

    public void setoSECDowntimeList(List<OSECDowntime> oSECDowntimeList) {
        this.oSECDowntimeList = oSECDowntimeList;
    }
    
    

//    public OSECDowntime getOSECDowntime() {
//        return oSECDowntime;
//    }
//
//    public void setOSECDowntime(OSECDowntime oSECDowntime) {
//        this.oSECDowntime = oSECDowntime;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (machineID != null ? machineID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OSECMachine)) {
            return false;
        }
        OSECMachine other = (OSECMachine) object;
        if ((this.machineID == null && other.machineID != null) || (this.machineID != null && !this.machineID.equals(other.machineID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.OSEC.entity.OSECMachine[ machineID=" + machineID + " ]";
    }
    
}
