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

public class Purchaser implements Serializable{
    @JsonProperty("PURCHASER_NAME")
    public String purchaserName;
    
    @JsonProperty("DEPARTMENT")
    public String department;
    
    @JsonProperty("TELEPHONE")
    public String telephone;
    
    @JsonProperty("FAX")
    public String fax;
    
    @JsonProperty("EMAIL")
    public String email;

    public Purchaser() {
    }

    public Purchaser(String purchaserName, String department, String telephone, String fax, String email) {
        this.purchaserName = purchaserName;
        this.department = department;
        this.telephone = telephone;
        this.fax = fax;
        this.email = email;
    }
    
    

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}