/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "PRC_Contract_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PRCContractdetail.findAll", query = "SELECT p FROM PRCContractdetail p"),
    @NamedQuery(name = "PRCContractdetail.findById", query = "SELECT p FROM PRCContractdetail p WHERE p.id = :id"),
    @NamedQuery(name = "PRCContractdetail.findByType", query = "SELECT p FROM PRCContractdetail p WHERE p.type = :type"),
    @NamedQuery(name = "PRCContractdetail.findByValue", query = "SELECT p FROM PRCContractdetail p WHERE p.value = :value"),
    @NamedQuery(name = "PRCContractdetail.findByFrmlWeight", query = "SELECT p FROM PRCContractdetail p WHERE p.frmlWeight = :frmlWeight"),
    @NamedQuery(name = "PRCContractdetail.findByFrmlNotation", query = "SELECT p FROM PRCContractdetail p WHERE p.frmlNotation = :frmlNotation"),
    @NamedQuery(name = "PRCContractdetail.findByStatus", query = "SELECT p FROM PRCContractdetail p WHERE p.status = :status")})
public class PRCContractdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "frml_weight")
    private Double frmlWeight;
    @Column(name = "frml_notation")
    private Double frmlNotation;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "pogId", referencedColumnName = "id")
    @ManyToOne
    private PRCContract pogId;
    @Column(name = "detValidFrom")
    @Temporal(TemporalType.DATE)
    private Date detValidFrom;

    public PRCContractdetail() {
    }

    public PRCContractdetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getFrmlWeight() {
        return frmlWeight;
    }

    public void setFrmlWeight(Double frmlWeight) {
        this.frmlWeight = frmlWeight;
    }

    public Double getFrmlNotation() {
        return frmlNotation;
    }

    public void setFrmlNotation(Double frmlNotation) {
        this.frmlNotation = frmlNotation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PRCContract getPogId() {
        return pogId;
    }

    public void setPogId(PRCContract pogId) {
        this.pogId = pogId;
    }

    public Date getDetValidFrom() {
        return detValidFrom;
    }

    public void setDetValidFrom(Date detValidFrom) {
        this.detValidFrom = detValidFrom;
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
        if (!(object instanceof PRCContractdetail)) {
            return false;
        }
        PRCContractdetail other = (PRCContractdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PRC.entity.PRCContractdetail[ id=" + id + " ]";
    }
    
}
