/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PM.entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "CON_PSReport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CONPSReport.findAll", query = "SELECT c FROM CONPSReport c"),
    @NamedQuery(name = "CONPSReport.findByIdRow", query = "SELECT c FROM CONPSReport c WHERE c.idRow = :idRow"),
    @NamedQuery(name = "CONPSReport.findByProjectNo", query = "SELECT c FROM CONPSReport c WHERE c.projectNo = :projectNo"),
    @NamedQuery(name = "CONPSReport.findByCategory", query = "SELECT c FROM CONPSReport c WHERE c.category = :category"),
    @NamedQuery(name = "CONPSReport.findByComment", query = "SELECT c FROM CONPSReport c WHERE c.comment = :comment")})
public class CONPSReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRow")
    private Integer idRow;
    @Size(max = 200)
    @Column(name = "ProjectNo")
    private String projectNo;
    @Size(max = 200)
    @Column(name = "Category")
    private String category;
    @Size(max = 200)
    @Column(name = "Comment")
    private String comment;
    
    @Column(name = "DateFrom")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Size(max = 200)
    @Column(name = "Responsible")
    private String responsible;
    @Size(max = 200)
    @Column(name = "Status")
    private String status;

    public CONPSReport() {
    }

    public CONPSReport(Integer idRow) {
        this.idRow = idRow;
    }

    public Integer getIdRow() {
        return idRow;
    }

    public void setIdRow(Integer idRow) {
        this.idRow = idRow;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRow != null ? idRow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CONPSReport)) {
            return false;
        }
        CONPSReport other = (CONPSReport) object;
        if ((this.idRow == null && other.idRow != null) || (this.idRow != null && !this.idRow.equals(other.idRow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PM.entity.CONPSReport[ idRow=" + idRow + " ]";
    }
    
}
