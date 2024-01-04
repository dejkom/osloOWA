/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.jan.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "EN_ALS_meters")
@NamedQueries({
    @NamedQuery(name = "ENALSmeters.findAll", query = "SELECT e FROM ENALSmeters e"),
    @NamedQuery(name = "ENALSmeters.findByMeterID", query = "SELECT e FROM ENALSmeters e WHERE e.meterID = :meterID"),
    @NamedQuery(name = "ENALSmeters.findByName", query = "SELECT e FROM ENALSmeters e WHERE e.name = :name")})
public class ENALSmeters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "meterID")
    private Integer meterID;
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    public ENALSmeters() {
    }

    public ENALSmeters(Integer meterID) {
        this.meterID = meterID;
    }

    public Integer getMeterID() {
        return meterID;
    }

    public void setMeterID(Integer meterID) {
        this.meterID = meterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof ENALSmeters)) {
            return false;
        }
        ENALSmeters other = (ENALSmeters) object;
        if ((this.meterID == null && other.meterID != null) || (this.meterID != null && !this.meterID.equals(other.meterID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.jan.entity.ENALSmeters[ meterID=" + meterID + " ]";
    }
    
}
