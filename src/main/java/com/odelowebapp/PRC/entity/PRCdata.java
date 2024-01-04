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
@Table(name = "PRC_data")
@NamedQueries({
    @NamedQuery(name = "PRCdata.findAll", query = "SELECT p FROM PRCdata p"),
    @NamedQuery(name = "PRCdata.findById", query = "SELECT p FROM PRCdata p WHERE p.id = :id"),
    @NamedQuery(name = "PRCdata.findByType", query = "SELECT p FROM PRCdata p WHERE p.type = :type"),
    @NamedQuery(name = "PRCdata.findByValue", query = "SELECT p FROM PRCdata p WHERE p.value = :value"),
    @NamedQuery(name = "PRCdata.findByDateFrom", query = "SELECT p FROM PRCdata p WHERE p.dateFrom = :dateFrom"),
    @NamedQuery(name = "PRCdata.findByDateTo", query = "SELECT p FROM PRCdata p WHERE p.dateTo = :dateTo"),
    @NamedQuery(name = "PRCdata.findByDeleted", query = "SELECT p FROM PRCdata p WHERE p.deleted = :deleted")})
public class PRCdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Column(name = "dateFrom")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Column(name = "dateTo")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    @Column(name = "deleted")
    private Boolean deleted;
    @Size(max = 50)
    @Column(name = "product")
    private String product;

    public PRCdata() {
    }

    public PRCdata(Integer id) {
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
        if (!(object instanceof PRCdata)) {
            return false;
        }
        PRCdata other = (PRCdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PRC.entity.PRCdata[ id=" + id + " ]";
    }
    
}
