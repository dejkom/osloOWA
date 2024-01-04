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
@Table(name = "EN_GAS_meters")
@NamedQueries({
    @NamedQuery(name = "ENGASmeters.findAll", query = "SELECT e FROM ENGASmeters e"),
    @NamedQuery(name = "ENGASmeters.findByMeterId", query = "SELECT e FROM ENGASmeters e WHERE e.meterId = :meterId"),
    @NamedQuery(name = "ENGASmeters.findByName", query = "SELECT e FROM ENGASmeters e WHERE e.name = :name"),
    @NamedQuery(name = "ENGASmeters.findByUnit", query = "SELECT e FROM ENGASmeters e WHERE e.unit = :unit"),
    @NamedQuery(name = "ENGASmeters.findByLocation", query = "SELECT e FROM ENGASmeters e WHERE e.location = :location")})
public class ENGASmeters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "meterId")
    private Integer meterId;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 10)
    @Column(name = "unit")
    private String unit;
    @Size(max = 50)
    @Column(name = "location")
    private String location;

    public ENGASmeters() {
    }

    public ENGASmeters(Integer meterId) {
        this.meterId = meterId;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meterId != null ? meterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ENGASmeters)) {
            return false;
        }
        ENGASmeters other = (ENGASmeters) object;
        if ((this.meterId == null && other.meterId != null) || (this.meterId != null && !this.meterId.equals(other.meterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.jan.entity.ENGASmeters[ meterId=" + meterId + " ]";
    }
    
}
