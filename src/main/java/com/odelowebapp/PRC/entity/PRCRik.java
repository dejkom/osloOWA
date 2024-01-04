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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "PRC_Rik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PRCRik.findAll", query = "SELECT p FROM PRCRik p"),
    @NamedQuery(name = "PRCRik.findById", query = "SELECT p FROM PRCRik p WHERE p.id = :id"),
    @NamedQuery(name = "PRCRik.findByFrom", query = "SELECT p FROM PRCRik p WHERE p.frm = :frm"),
    @NamedQuery(name = "PRCRik.findByTo", query = "SELECT p FROM PRCRik p WHERE p.too = :to"),
    @NamedQuery(name = "PRCRik.findByValue", query = "SELECT p FROM PRCRik p WHERE p.val = :value"),
    @NamedQuery(name = "PRCRik.findByType", query = "SELECT p FROM PRCRik p WHERE p.type = :type")})
public class PRCRik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "frm")
    @Temporal(TemporalType.DATE)
    private Date frm;
    @Column(name = "too")
    @Temporal(TemporalType.DATE)
    private Date too;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "val")
    private Double val;
    @Size(max = 150)
    @Column(name = "type")
    private String type;

    public PRCRik() {
    }

    public PRCRik(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFrm() {
        return frm;
    }

    public void setFrm(Date from) {
        this.frm = from;
    }

    public Date getToo() {
        return too;
    }

    public void setToo(Date to) {
        this.too = to;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double value) {
        this.val = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof PRCRik)) {
            return false;
        }
        PRCRik other = (PRCRik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PRC.entity.PRCRik[ id=" + id + " ]";
    }
    
}
