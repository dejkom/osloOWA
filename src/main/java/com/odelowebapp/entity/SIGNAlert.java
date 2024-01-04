/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "SIGN_Alert")
@NamedQueries({
    @NamedQuery(name = "SIGNAlert.findAll", query = "SELECT s FROM SIGNAlert s"),
    @NamedQuery(name = "SIGNAlert.findByIdAlert", query = "SELECT s FROM SIGNAlert s WHERE s.idAlert = :idAlert"),
    @NamedQuery(name = "SIGNAlert.findByInnerHTML", query = "SELECT s FROM SIGNAlert s WHERE s.innerHTML = :innerHTML"),
    @NamedQuery(name = "SIGNAlert.findByDate", query = "SELECT s FROM SIGNAlert s WHERE s.date = :date")})
public class SIGNAlert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAlert")
    private Integer idAlert;

    @Size(max = 2147483647)
    @Column(name = "innerHTML")
    private String innerHTML;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public SIGNAlert() {
    }

    public SIGNAlert(Integer idAlert) {
        this.idAlert = idAlert;
    }

    public Integer getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(Integer idAlert) {
        this.idAlert = idAlert;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlert != null ? idAlert.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SIGNAlert)) {
            return false;
        }
        SIGNAlert other = (SIGNAlert) object;
        if ((this.idAlert == null && other.idAlert != null) || (this.idAlert != null && !this.idAlert.equals(other.idAlert))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.entity.SIGNAlert[ idAlert=" + idAlert + " ]";
    }
}
