/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "ASS_Status")
@NamedQueries({
    @NamedQuery(name = "ASSStatus.findAll", query = "SELECT a FROM ASSStatus a"),
    @NamedQuery(name = "ASSStatus.findByIDStatus", query = "SELECT a FROM ASSStatus a WHERE a.iDStatus = :iDStatus"),
    @NamedQuery(name = "ASSStatus.findByStatusDescription", query = "SELECT a FROM ASSStatus a WHERE a.statusDescription = :statusDescription"),
    @NamedQuery(name = "ASSStatus.findByStatusDisable", query = "SELECT a FROM ASSStatus a WHERE a.statusDisable = :statusDisable")})
public class ASSStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDStatus")
    private Integer iDStatus;
    @Size(max = 100)
    @Column(name = "StatusDescription")
    private String statusDescription;
    @Column(name = "StatusDisable")
    private Boolean statusDisable;
    @OneToMany(mappedBy = "iDLogStatus")
    private List<ASSLog> aSSLogList;
    @Column(name = "StatusFactor")
    private Integer statusFactor;

    public ASSStatus() {
    }

    public ASSStatus(Integer iDStatus) {
        this.iDStatus = iDStatus;
    }

    public Integer getIDStatus() {
        return iDStatus;
    }

    public void setIDStatus(Integer iDStatus) {
        this.iDStatus = iDStatus;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Boolean getStatusDisable() {
        return statusDisable;
    }

    public void setStatusDisable(Boolean statusDisable) {
        this.statusDisable = statusDisable;
    }

    public List<ASSLog> getASSLogList() {
        return aSSLogList;
    }

    public void setASSLogList(List<ASSLog> aSSLogList) {
        this.aSSLogList = aSSLogList;
    }

    public Integer getStatusFactor() {
        return statusFactor;
    }

    public void setStatusFactor(Integer statusFactor) {
        this.statusFactor = statusFactor;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDStatus != null ? iDStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASSStatus)) {
            return false;
        }
        ASSStatus other = (ASSStatus) object;
        if ((this.iDStatus == null && other.iDStatus != null) || (this.iDStatus != null && !this.iDStatus.equals(other.iDStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.evidenca.entity.ASSStatus[ iDStatus=" + iDStatus + " ]";
    }
    
}
