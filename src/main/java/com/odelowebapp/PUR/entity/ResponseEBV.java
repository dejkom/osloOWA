/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PUR.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author dematjasic
 */
public class ResponseEBV {
    
    @JsonProperty("GT_REQUEST_OUT")
    @SerializedName("GT_REQUEST_OUT")
    private List<ResponseEBVLine> responseLines;

    public ResponseEBV() {
    }

    public ResponseEBV(List<ResponseEBVLine> responseLines) {
        this.responseLines = responseLines;
    }
    
    

    public List<ResponseEBVLine> getResponseLines() {
        return responseLines;
    }

    public void setResponseLines(List<ResponseEBVLine> responseLines) {
        this.responseLines = responseLines;
    }
    
    
    
}
