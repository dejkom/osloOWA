/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.XML.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;

/**
 *
 * @author dematjasic
 */

public class Demand_loc implements Serializable{

    @JsonProperty("DEMAND_LOCATION")
    public String demandlocation;
    @JsonProperty("DEMAND_LOCATION_DESC")
    public String demandlocationdesc;
    @JsonProperty("DEMAND_LOC_STATUS")
    public String demandlocstatus;
    @JsonProperty("QUANTITY")
    public String quantity;
    @JsonProperty("DELIVERY_LOCATION")
    public int deliverylocation;
    @JsonProperty("DELIVERY_LOCATION_DESC")
    public String deliverylocationdesc;
    @JsonProperty("DELIVERY_TERMS")
    public String deliveryterms;
    @JsonProperty("PRODUCT_LOCATION")
    public int productlocation;
    @JsonProperty("PRODUCT_LOCATION_DESC")
    public String productlocationdesc;
    @JsonProperty("INCOTERM")
    public String incoterm;
    @JsonProperty("INCOTERM_LOCATION")
    public String incotermlocation;
    @JsonProperty("PACKAGING_TYPE")
    public String packagingtype;
    @JsonProperty("DISPATCH_TYPE")
    public String dispatchtype;
    @JsonProperty("PAYMENT_TERMS")
    public String paymentterms;
    @JsonProperty("INVOICING_PARTY")
    public int invoicingparty;
    @JsonProperty("INVOICING_PARTY_DESCR")
    public String invoicingpartydescr;
    @JsonProperty("QUOTA")
    public int quota;


    public String getDemandlocation() {
        return demandlocation;
    }

    public void setDemandlocation(String demandlocation) {
        this.demandlocation = demandlocation;
    }

    public String getDemandlocationdesc() {
        return demandlocationdesc;
    }

    public void setDemandlocationdesc(String demandlocationdesc) {
        this.demandlocationdesc = demandlocationdesc;
    }

    public String getDemandlocstatus() {
        return demandlocstatus;
    }

    public void setDemandlocstatus(String demandlocstatus) {
        this.demandlocstatus = demandlocstatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(int deliverylocation) {
        this.deliverylocation = deliverylocation;
    }

    public String getDeliverylocationdesc() {
        return deliverylocationdesc;
    }

    public void setDeliverylocationdesc(String deliverylocationdesc) {
        this.deliverylocationdesc = deliverylocationdesc;
    }

    public String getDeliveryterms() {
        return deliveryterms;
    }

    public void setDeliveryterms(String deliveryterms) {
        this.deliveryterms = deliveryterms;
    }

    public int getProductlocation() {
        return productlocation;
    }

    public void setProductlocation(int productlocation) {
        this.productlocation = productlocation;
    }

    public String getProductlocationdesc() {
        return productlocationdesc;
    }

    public void setProductlocationdesc(String productlocationdesc) {
        this.productlocationdesc = productlocationdesc;
    }

    public String getIncoterm() {
        return incoterm;
    }

    public void setIncoterm(String incoterm) {
        this.incoterm = incoterm;
    }

    public String getIncotermlocation() {
        return incotermlocation;
    }

    public void setIncotermlocation(String incotermlocation) {
        this.incotermlocation = incotermlocation;
    }

    public String getPackagingtype() {
        return packagingtype;
    }

    public void setPackagingtype(String packagingtype) {
        this.packagingtype = packagingtype;
    }

    public String getDispatchtype() {
        return dispatchtype;
    }

    public void setDispatchtype(String dispatchtype) {
        this.dispatchtype = dispatchtype;
    }

    public String getPaymentterms() {
        return paymentterms;
    }

    public void setPaymentterms(String paymentterms) {
        this.paymentterms = paymentterms;
    }

    public int getInvoicingparty() {
        return invoicingparty;
    }

    public void setInvoicingparty(int invoicingparty) {
        this.invoicingparty = invoicingparty;
    }

    public String getInvoicingpartydescr() {
        return invoicingpartydescr;
    }

    public void setInvoicingpartydescr(String invoicingpartydescr) {
        this.invoicingpartydescr = invoicingpartydescr;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    

}
