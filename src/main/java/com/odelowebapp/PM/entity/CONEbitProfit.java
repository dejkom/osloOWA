/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PM.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
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
@Table(name = "CON_EbitProfit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CONEbitProfit.findAll", query = "SELECT c FROM CONEbitProfit c"),
    @NamedQuery(name = "CONEbitProfit.findByIdRow", query = "SELECT c FROM CONEbitProfit c WHERE c.idRow = :idRow"),
    @NamedQuery(name = "CONEbitProfit.findByProjectNo", query = "SELECT c FROM CONEbitProfit c WHERE c.projectNo = :projectNo"),
    @NamedQuery(name = "CONEbitProfit.findByFormat", query = "SELECT c FROM CONEbitProfit c WHERE c.format = :format"),
    @NamedQuery(name = "CONEbitProfit.findByType", query = "SELECT c FROM CONEbitProfit c WHERE c.type = :type"),
    @NamedQuery(name = "CONEbitProfit.findByPhase", query = "SELECT c FROM CONEbitProfit c WHERE c.phase = :phase"),
    @NamedQuery(name = "CONEbitProfit.findByValue", query = "SELECT c FROM CONEbitProfit c WHERE c.value = :value"),
    @NamedQuery(name = "CONEbitProfit.findByDateFrom", query = "SELECT c FROM CONEbitProfit c WHERE c.dateFrom = :dateFrom"),
    @NamedQuery(name = "CONEbitProfit.findByDateTo", query = "SELECT c FROM CONEbitProfit c WHERE c.dateTo = :dateTo")})
public class CONEbitProfit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRow")
    private Integer idRow;
    @Size(max = 50)
    @Column(name = "ProjectNo")
    private String projectNo;
    @Size(max = 10)
    @Column(name = "Format")
    private String format;
    @Size(max = 100)
    @Column(name = "Type")
    private String type;
    @Column(name = "Phase")
    private Integer phase;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Value")
    private Double value;
    @Column(name = "dateFrom")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Column(name = "dateTo")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    
    @Size(max = 200)
    @Column(name = "Remark")
    private String remark;
    @Size(max = 50)
    @Column(name = "Source")
    private String source;

    public CONEbitProfit() {
    }

    public CONEbitProfit(Integer idRow) {
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Double getValue() {
        return value;
    }
    
    public String getValueAsString() {
        if(value != null){
            //DecimalFormat df = new DecimalFormat("#.#####");
            Locale locale = Locale.GERMANY; // You can change this to the desired locale
            NumberFormat nf = NumberFormat.getNumberInstance(locale);
            //System.out.println("Converting value to string:"+value);
            return nf.format(value);
        }else{
            return "NULL";
        }
        
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        if (!(object instanceof CONEbitProfit)) {
            return false;
        }
        CONEbitProfit other = (CONEbitProfit) object;
        if ((this.idRow == null && other.idRow != null) || (this.idRow != null && !this.idRow.equals(other.idRow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PM.entity.CONEbitProfit[ idRow=" + idRow + " ]";
    }
    
}
