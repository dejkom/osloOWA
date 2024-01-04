/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.QS.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "QS_overview")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QSoverview.findAll", query = "SELECT q FROM QSoverview q"),
    @NamedQuery(name = "QSoverview.findById", query = "SELECT q FROM QSoverview q WHERE q.id = :id"),
    @NamedQuery(name = "QSoverview.findByName", query = "SELECT q FROM QSoverview q WHERE q.name = :name"),
    @NamedQuery(name = "QSoverview.findByDisabled", query = "SELECT q FROM QSoverview q WHERE q.disabled = :disabled")})
public class QSoverview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Name")
    private String name;
    @Column(name = "disabled")
    private Boolean disabled;
    @JoinColumn(name = "TID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private QSmain tid;

    public QSoverview() {
    }

    public QSoverview(Integer id) {
        this.id = id;
    }

    public QSoverview(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public QSmain getTid() {
        return tid;
    }

    public void setTid(QSmain tid) {
        this.tid = tid;
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
        if (!(object instanceof QSoverview)) {
            return false;
        }
        QSoverview other = (QSoverview) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.QS.entity.QSoverview[ id=" + id + " ]";
    }
    
}
