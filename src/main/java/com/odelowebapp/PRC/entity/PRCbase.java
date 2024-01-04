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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "PRC_Base")
@NamedQueries({
    @NamedQuery(name = "PRCbase.findAll", query = "SELECT p FROM PRCbase p"),
    @NamedQuery(name = "PRCbase.findById", query = "SELECT p FROM PRCbase p WHERE p.id = :id"),
    @NamedQuery(name = "PRCbase.findByDateFrom", query = "SELECT p FROM PRCbase p WHERE p.dateFrom = :dateFrom"),
    @NamedQuery(name = "PRCbase.findByDateTo", query = "SELECT p FROM PRCbase p WHERE p.dateTo = :dateTo"),
    @NamedQuery(name = "PRCbase.findByBmwProduct", query = "SELECT p FROM PRCbase p WHERE p.bmwProduct = :bmwProduct"),
    @NamedQuery(name = "PRCbase.findByBmwIndex", query = "SELECT p FROM PRCbase p WHERE p.bmwIndex = :bmwIndex"),
    @NamedQuery(name = "PRCbase.findByValue", query = "SELECT p FROM PRCbase p WHERE p.value = :value")})
public class PRCbase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "dateFrom")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Column(name = "dateTo")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    @Size(max = 50)
    @Column(name = "bmwProduct")
    private String bmwProduct;
    @Size(max = 10)
    @Column(name = "bmwIndex")
    private String bmwIndex;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;

    public PRCbase() {
    }

    public PRCbase(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getBmwProduct() {
        return bmwProduct;
    }

    public void setBmwProduct(String bmwProduct) {
        this.bmwProduct = bmwProduct;
    }

    public String getBmwIndex() {
        return bmwIndex;
    }

    public void setBmwIndex(String bmwIndex) {
        this.bmwIndex = bmwIndex;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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
        if (!(object instanceof PRCbase)) {
            return false;
        }
        PRCbase other = (PRCbase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PRC.entity.PRCbase[ id=" + id + " ]";
    }
    
}
