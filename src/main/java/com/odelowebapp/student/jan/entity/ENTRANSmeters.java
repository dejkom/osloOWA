/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.jan.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Student
 */
@Entity
@Table(name = "EN_TRANS_meters")
@NamedQueries({
    @NamedQuery(name = "ENTRANSmeters.findAll", query = "SELECT e FROM ENTRANSmeters e"),
    @NamedQuery(name = "ENTRANSmeters.findByMeterID", query = "SELECT e FROM ENTRANSmeters e WHERE e.meterID = :meterID"),
    @NamedQuery(name = "ENTRANSmeters.findByIp", query = "SELECT e FROM ENTRANSmeters e WHERE e.ip = :ip"),
    @NamedQuery(name = "ENTRANSmeters.findByName", query = "SELECT e FROM ENTRANSmeters e WHERE e.name = :name"),
    @NamedQuery(name = "ENTRANSmeters.findByLocation", query = "SELECT e FROM ENTRANSmeters e WHERE e.location = :location"),
    @NamedQuery(name = "ENTRANSmeters.findByTp", query = "SELECT e FROM ENTRANSmeters e WHERE e.tp = :tp"),
    @NamedQuery(name = "ENTRANSmeters.findBySapnumber", query = "SELECT e FROM ENTRANSmeters e WHERE e.sapnumber = :sapnumber")})
public class ENTRANSmeters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "meterID")
    private String meterID;
    @Size(max = 15)
    @Column(name = "IP")
    private String ip;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "location")
    private String location;
    @Size(max = 50)
    @Column(name = "TP")
    private String tp;
    @Size(max = 50)
    @Column(name = "SAPNUMBER")
    private String sapnumber;

    public ENTRANSmeters() {
    }

    public ENTRANSmeters(String meterID) {
        this.meterID = meterID;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getSapnumber() {
        return sapnumber;
    }

    public void setSapnumber(String sapnumber) {
        this.sapnumber = sapnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meterID != null ? meterID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ENTRANSmeters)) {
            return false;
        }
        ENTRANSmeters other = (ENTRANSmeters) object;
        if ((this.meterID == null && other.meterID != null) || (this.meterID != null && !this.meterID.equals(other.meterID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.jan.entity.ENTRANSmeters[ meterID=" + meterID + " ]";
    }
    
}
