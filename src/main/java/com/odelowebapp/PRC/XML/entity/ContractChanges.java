/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.XML.entity;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dematjasic
 */

@JacksonXmlRootElement(localName = "CONTRACT_CHANGES")
public class ContractChanges implements Serializable{
    
    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonMerge
    @JsonProperty("CONTRACT")
    public List<Contract> contracts;

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }


   
    
}
