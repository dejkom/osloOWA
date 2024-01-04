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
 * @author dematjasic
 */
public class RequestEBV {
    @JsonProperty("REQUEST_LINE")
    @SerializedName("REQUEST_LINE")
    private RequestLine[] requestLines;
    
    @JsonProperty("REQUESTOR_REF")
    private String requestorRef;
    
    @JsonProperty("PROJECT_NAME")
    private String projectName;

    public RequestEBV() {
        
    }
    
    public void setRequestLines(RequestLine[] requestLines) {
        this.requestLines = requestLines;
    }

    public String getRequestorRef() {
        return requestorRef;
    }

    public void setRequestorRef(String requestorRef) {
        this.requestorRef = requestorRef;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    
}
