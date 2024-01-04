/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PUR.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dematjasic
 */
public class ResponseEBVLine {
    
    @JsonProperty("ORIGINAL_LINE")
    private Integer originalLine;
    
    @JsonProperty("PART_NUMBER")
    private String partNumber;
    
    @JsonProperty("MANUFACTURER_NAME")
    private String manufacturerName;
    
    @JsonProperty("DEMAND_QTY")
    private Integer demandQty;
    
    @JsonProperty("UNIT_OF_MEASURE")
    private String unitOfMeasure;
    
    @JsonProperty("CURRENCY")
    private String currency;
    
    @JsonProperty("MAX_RESULT")
    private Integer maxResult;
    
    @JsonProperty("END_CUSTOMER")
    private String endCustomer;
    
    @JsonProperty("PART_NUMBER_OUT")
    private String partNumberOut;
    
    @JsonProperty("MANUFACTURER_NAME_OUT")
    private String manufacturerNameOut;
    
    @JsonProperty("DEMAND_QTY_OUT")
    private Integer demandQtyOut;
    
    @JsonProperty("FREE_STOCK")
    private Integer freeStock;
    
    @JsonProperty("MIN_ORDER_QTY")
    private Integer minOrderQty;
    
    @JsonProperty("PACKAGE_TYPE")
    private String packageType;
    
    @JsonProperty("PACK_SIZE")
    private Integer packSize;
    
    @JsonProperty("MATERIAL_STATUS")
    private String materialStatus;
    
    @JsonProperty("EOL_PCN")
    private String eolPcn;
    
    @JsonProperty("ROHSL")
    private String rohsl;
    
    @JsonProperty("MARKET_PRICE")
    private Double marketPrice;
    
    @JsonProperty("CURRENCY_OUT")
    private String currencyOut;
    
    @JsonProperty("LEAD_TIME")
    private Integer leadTime;
    
    @JsonProperty("PO_PIPELINE")
    private String poPipeline;
    
    @JsonProperty("DEPARTURE_COUNTRY")
    private String departureCountry;
    
    @JsonProperty("ECCN_NUMER")
    private String eccnNumer;
    
    @JsonProperty("MESSAGE")
    private String message;
    
    @JsonProperty("PART_NUMBER_DESC")
    private String partNumberDesc;
    
    @JsonProperty("MATNR")
    private String matnr;
    
    @JsonProperty("ERROR")
    private String error;
    
    @JsonProperty("TDLINE")
    private String tdline;
    
    @JsonProperty("KDMAT_OUT")
    private String kdmatOut;

    public ResponseEBVLine() {
    }

    public ResponseEBVLine(Integer originalLine, String partNumber, String manufacturerName, Integer demandQty, String unitOfMeasure, String currency, Integer maxResult, String endCustomer, String partNumberOut, String manufacturerNameOut, Integer demandQtyOut, Integer freeStock, Integer minOrderQty, String packageType, Integer packSize, String materialStatus, String eolPcn, String rohsl, Double marketPrice, String currencyOut, Integer leadTime, String poPipeline, String departureCountry, String eccnNumer, String message, String partNumberDesc, String matnr, String error, String tdline, String kdmatOut) {
        this.originalLine = originalLine;
        this.partNumber = partNumber;
        this.manufacturerName = manufacturerName;
        this.demandQty = demandQty;
        this.unitOfMeasure = unitOfMeasure;
        this.currency = currency;
        this.maxResult = maxResult;
        this.endCustomer = endCustomer;
        this.partNumberOut = partNumberOut;
        this.manufacturerNameOut = manufacturerNameOut;
        this.demandQtyOut = demandQtyOut;
        this.freeStock = freeStock;
        this.minOrderQty = minOrderQty;
        this.packageType = packageType;
        this.packSize = packSize;
        this.materialStatus = materialStatus;
        this.eolPcn = eolPcn;
        this.rohsl = rohsl;
        this.marketPrice = marketPrice;
        this.currencyOut = currencyOut;
        this.leadTime = leadTime;
        this.poPipeline = poPipeline;
        this.departureCountry = departureCountry;
        this.eccnNumer = eccnNumer;
        this.message = message;
        this.partNumberDesc = partNumberDesc;
        this.matnr = matnr;
        this.error = error;
        this.tdline = tdline;
        this.kdmatOut = kdmatOut;
    }
    
    

    public Integer getOriginalLine() {
        return originalLine;
    }

    public void setOriginalLine(Integer originalLine) {
        this.originalLine = originalLine;
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

    public String getPartNumberOut() {
        return partNumberOut;
    }

    public void setPartNumberOut(String partNumberOut) {
        this.partNumberOut = partNumberOut;
    }

    public String getManufacturerNameOut() {
        return manufacturerNameOut;
    }

    public void setManufacturerNameOut(String manufacturerNameOut) {
        this.manufacturerNameOut = manufacturerNameOut;
    }

    public Integer getDemandQtyOut() {
        return demandQtyOut;
    }

    public void setDemandQtyOut(Integer demandQtyOut) {
        this.demandQtyOut = demandQtyOut;
    }

    public Integer getFreeStock() {
        return freeStock;
    }

    public void setFreeStock(Integer freeStock) {
        this.freeStock = freeStock;
    }

    public Integer getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(Integer minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    public String getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }

    public String getEolPcn() {
        return eolPcn;
    }

    public void setEolPcn(String eolPcn) {
        this.eolPcn = eolPcn;
    }

    public String getRohsl() {
        return rohsl;
    }

    public void setRohsl(String rohsl) {
        this.rohsl = rohsl;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getCurrencyOut() {
        return currencyOut;
    }

    public void setCurrencyOut(String currencyOut) {
        this.currencyOut = currencyOut;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public String getPoPipeline() {
        return poPipeline;
    }

    public void setPoPipeline(String poPipeline) {
        this.poPipeline = poPipeline;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getEccnNumer() {
        return eccnNumer;
    }

    public void setEccnNumer(String eccnNumer) {
        this.eccnNumer = eccnNumer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPartNumberDesc() {
        return partNumberDesc;
    }

    public void setPartNumberDesc(String partNumberDesc) {
        this.partNumberDesc = partNumberDesc;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTdline() {
        return tdline;
    }

    public void setTdline(String tdline) {
        this.tdline = tdline;
    }

    public String getKdmatOut() {
        return kdmatOut;
    }

    public void setKdmatOut(String kdmatOut) {
        this.kdmatOut = kdmatOut;
    }
    
    
    
}
