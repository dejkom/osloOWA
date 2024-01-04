/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.QS.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "QS_main")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QSmain.findAll", query = "SELECT q FROM QSmain q"),
    @NamedQuery(name = "QSmain.findById", query = "SELECT q FROM QSmain q WHERE q.id = :id"),
    @NamedQuery(name = "QSmain.findByCategory", query = "SELECT q FROM QSmain q WHERE q.category = :category"),
    @NamedQuery(name = "QSmain.findByDisabled", query = "SELECT q FROM QSmain q WHERE q.disabled = :disabled")})
public class QSmain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Category")
    private String category;
    @Column(name = "disabled")
    private Boolean disabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tid")
    private List<QSoverview> qSoverviewList;

    public QSmain() {
    }

    public QSmain(Integer id) {
        this.id = id;
    }

    public QSmain(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @XmlTransient
    public List<QSoverview> getQSoverviewList() {
        return qSoverviewList;
    }

    public void setQSoverviewList(List<QSoverview> qSoverviewList) {
        this.qSoverviewList = qSoverviewList;
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
        if (!(object instanceof QSmain)) {
            return false;
        }
        QSmain other = (QSmain) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.QS.entity.QSmain[ id=" + id + " ]";
    }
    
}
