/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.XML.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dematjasic
 */


public class Conditions implements Serializable{

    @JsonProperty("TYPE")
    public String type;
    @JsonProperty("VALIDITY_START")
    public String validityStart;
    @JsonProperty("VALIDITY_END")
    public String validityEnd;
    @JsonProperty("VALUE")
    public double value;
    @JsonProperty("CURRENCY_COND")
    public String correncycond;
    @JsonProperty("PRICE_UNIT_COND")
    public int priceunitcond;
    @JsonProperty("PRICE_UOM_COND")
    public String priceuomcond;
    @JsonProperty("LOCATION_NAME")
    public String locationname;
    @JsonProperty("DOC_CURR")
    public String doccurr;
    @JsonProperty("CONVERTED_PRICE")
    public String convertedprice;
    @JsonProperty("FRML_WEIGHT")
    public String frmlweight;
    @JsonProperty("FRML_NOTATION")
    public String frmlnotation;
    @JsonProperty("FRML_PER")
    public String frmlper;
    @JsonProperty("FRML_UM")
    public String frmlum;
    @JsonProperty("FRML_BTQ")
    public String frmlbtq;
    @JsonProperty("FRML_SURCHRG")
    public String frmlsurchrg;

    public Conditions() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidityStart() {
        return validityStart;
    }
    
    public Date getValidityStartDate() {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            date = dateFormat.parse(validityStart);
        } catch (ParseException e) {
            
        }
        return date;
    }

    public void setValidityStart(String validityStart) {
        this.validityStart = validityStart;
    }

    public String getValidityEnd() {
        return validityEnd;
    }
    
    public Date getValidityEndDate() {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            date = dateFormat.parse(validityEnd);
        } catch (ParseException e) {
            
        }
        return date;
    }


    public void setValidityEnd(String validityEnd) {
        this.validityEnd = validityEnd;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCorrencycond() {
        return correncycond;
    }

    public void setCorrencycond(String correncycond) {
        this.correncycond = correncycond;
    }

    public int getPriceunitcond() {
        return priceunitcond;
    }

    public void setPriceunitcond(int priceunitcond) {
        this.priceunitcond = priceunitcond;
    }

    public String getPriceuomcond() {
        return priceuomcond;
    }

    public void setPriceuomcond(String priceuomcond) {
        this.priceuomcond = priceuomcond;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getDoccurr() {
        return doccurr;
    }

    public void setDoccurr(String doccurr) {
        this.doccurr = doccurr;
    }

    public String getConvertedprice() {
        return convertedprice;
    }

    public void setConvertedprice(String convertedprice) {
        this.convertedprice = convertedprice;
    }

    public String getFrmlweight() {
        return frmlweight;
    }

    public void setFrmlweight(String frmlweight) {
        this.frmlweight = frmlweight;
    }

    public String getFrmlnotation() {
        return frmlnotation;
    }

    public void setFrmlnotation(String frmlnotation) {
        this.frmlnotation = frmlnotation;
    }

    public String getFrmlper() {
        return frmlper;
    }

    public void setFrmlper(String frmlper) {
        this.frmlper = frmlper;
    }

    public String getFrmlum() {
        return frmlum;
    }

    public void setFrmlum(String frmlum) {
        this.frmlum = frmlum;
    }

    public String getFrmlbtq() {
        return frmlbtq;
    }

    public void setFrmlbtq(String frmlbtq) {
        this.frmlbtq = frmlbtq;
    }

    public String getFrmlsurchrg() {
        return frmlsurchrg;
    }

    public void setFrmlsurchrg(String frmlsurchrg) {
        this.frmlsurchrg = frmlsurchrg;
    }

    
    
   
}
