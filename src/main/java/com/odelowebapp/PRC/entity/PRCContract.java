/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "PRC_Contract")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PRCContract.findAll", query = "SELECT p FROM PRCContract p"),
    @NamedQuery(name = "PRCContract.findById", query = "SELECT p FROM PRCContract p WHERE p.id = :id"),
    @NamedQuery(name = "PRCContract.findByIdent", query = "SELECT p FROM PRCContract p WHERE p.ident = :ident"),
    @NamedQuery(name = "PRCContract.findByPdfpath", query = "SELECT p FROM PRCContract p WHERE p.pdfpath = :pdfpath"),
    @NamedQuery(name = "PRCContract.findByXml", query = "SELECT p FROM PRCContract p WHERE p.xml = :xml"),
    @NamedQuery(name = "PRCContract.findByDateRetrieved", query = "SELECT p FROM PRCContract p WHERE p.dateRetrieved = :dateRetrieved"),
    @NamedQuery(name = "PRCContract.findByStatus", query = "SELECT p FROM PRCContract p WHERE p.status = :status"),
    @NamedQuery(name = "PRCContract.findByContractNumber", query = "SELECT p FROM PRCContract p WHERE p.contractNumber = :contractNumber"),
    @NamedQuery(name = "PRCContract.findByAmendmentDate", query = "SELECT p FROM PRCContract p WHERE p.amendmentDate = :amendmentDate"),
    @NamedQuery(name = "PRCContract.findByOrderDate", query = "SELECT p FROM PRCContract p WHERE p.orderDate = :orderDate")})
public class PRCContract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "ident")
    private String ident;
    @Size(max = 255)
    @Column(name = "pdfpath")
    private String pdfpath;
    @Size(max = 2147483647)
    @Column(name = "xml")
    private String xml;
    @Column(name = "date_retrieved")
    @Temporal(TemporalType.DATE)
    private Date dateRetrieved;
    @Size(max = 50)
    @Column(name = "status")
    private String status;
    @Size(max = 50)
    @Column(name = "contractNumber")
    private String contractNumber;
    @Column(name = "amendmentDate")
    @Temporal(TemporalType.DATE)
    private Date amendmentDate;
    @Column(name = "orderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @OneToMany(mappedBy = "pogId")
    private List<PRCContractdetail> pRCContractdetailList;
    
    @Size(max = 2147483647)
    @Column(name = "demandLoc")
    private String demandLoc;
    @Size(max = 2147483647)
    @Column(name = "deliveryLoc")
    private String deliveryLoc;
    @Size(max = 2147483647)
    @Column(name = "productLoc")
    private String productLoc;
    @Size(max = 2147483647)
    @Column(name = "incoterm")
    private String incoterm;
    @Size(max = 2147483647)
    @Column(name = "incotermLoc")
    private String incotermLoc;

    public PRCContract() {
    }

    public PRCContract(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public Date getDateRetrieved() {
        return dateRetrieved;
    }

    public void setDateRetrieved(Date dateRetrieved) {
        this.dateRetrieved = dateRetrieved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getAmendmentDate() {
        return amendmentDate;
    }

    public void setAmendmentDate(Date amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getDemandLoc() {
        return demandLoc;
    }

    public void setDemandLoc(String demandLoc) {
        this.demandLoc = demandLoc;
    }

    public String getDeliveryLoc() {
        return deliveryLoc;
    }

    public void setDeliveryLoc(String deliveryLoc) {
        this.deliveryLoc = deliveryLoc;
    }

    public String getProductLoc() {
        return productLoc;
    }

    public void setProductLoc(String productLoc) {
        this.productLoc = productLoc;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getIncotermLoc() {
        return incotermLoc;
    }

    public void setIncotermLoc(String incotermLoc) {
        this.incotermLoc = incotermLoc;
    }
    
    

    @XmlTransient
    public List<PRCContractdetail> getPRCContractdetailList() {
        return pRCContractdetailList;
    }

    public void setPRCContractdetailList(List<PRCContractdetail> pRCContractdetailList) {
        this.pRCContractdetailList = pRCContractdetailList;
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
        if (!(object instanceof PRCContract)) {
            return false;
        }
        PRCContract other = (PRCContract) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.PRC.entity.PRCContract[ id=" + id + " ]";
    }
    
}
