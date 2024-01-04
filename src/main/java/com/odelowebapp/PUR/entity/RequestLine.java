/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PUR.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author dematjasic + chatGPT
 */
public class RequestLine {
    @JsonProperty("PART_NUMBER")
    @SerializedName("PART_NUMBER")
    private String partNumber;
    
    @JsonProperty("MANUFACTURER_NAME")
    private String manufacturerName;
    
    @JsonProperty("DEMAND_QTY")
    @SerializedName("DEMAND_QTY")
    private Integer demandQty;
    
    @JsonProperty("UNIT_OF_MEASURE")
    private String unitOfMeasure;
    
    @JsonProperty("CURRENCY")
    private String currency;
    
    @JsonProperty("MAX_RESULT")
    private Integer maxResult;
    
    @JsonProperty("END_CUSTOMER")
    private String endCustomer;
    
    @JsonProperty("KDMAT")
    private String kdmat;

    public RequestLine() {
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Integer getDemandQty() {
        return demandQty;
    }

    public void setDemandQty(Integer demandQty) {
        this.demandQty = demandQty;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public String getEndCustomer() {
        return endCustomer;
    }

    public void setEndCustomer(String endCustomer) {
        this.endCustomer = endCustomer;
    }

    public String getKdmat() {
        return kdmat;
    }

    public void setKdmat(String kdmat) {
        this.kdmat = kdmat;
    }
    
    
}
