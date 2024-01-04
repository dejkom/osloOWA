/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.XML.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dematjasic
 */

public class Header implements Serializable{

    @JsonProperty("CONTRACT_NO")
    public String contractNo;

    @JsonProperty("CURRENCY_HEAD")
    public String currencyHead;

    @JsonProperty("SUPPLIER")
    public String supplier;

    @JsonProperty("SUPPLIER_NAME")
    public String supplierName;

    @JsonProperty("ORDER_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    public String orderDate;

    @JsonProperty("AMENDMENT_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    public String amendmentDate;

    @JsonProperty("PRINT_DATE")
    public String printDate;

    @JsonProperty("VER_NO")
    public String verNo;

    @JsonProperty("ACTIVITY_TEXT")
    public String activityText;


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCurrencyHead() {
        return currencyHead;
    }

    public void setCurrencyHead(String currencyHead) {
        this.currencyHead = currencyHead;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAmendmentDate() {
        return amendmentDate;
    }

    public void setAmendmentDate(String amendmentDate) {
        this.amendmentDate = amendmentDate;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getVerNo() {
        return verNo;
    }

    public void setVerNo(String verNo) {
        this.verNo = verNo;
    }

    public String getActivityText() {
        return activityText;
    }

    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }
    
    
    
    
}
