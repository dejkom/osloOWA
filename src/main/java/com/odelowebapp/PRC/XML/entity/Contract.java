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

@JacksonXmlRootElement(localName = "CONTRACT")
public class Contract implements Serializable{
    @JsonProperty("HEADER")
    public Header header;
    
    @JsonProperty("PURCHASER")
    public Purchaser purchaser;
    
    @JsonProperty("ITEM")
    public Item item;


    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    
}
