/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.XML.entity;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author dematjasic
 */
public class BMWXmlJsonProductPair {
    
    private String pairName;
    private List<String> products;

    public BMWXmlJsonProductPair() {
    }      

    public BMWXmlJsonProductPair(String pairName, List<String> products) {
        this.pairName = pairName;
        this.products = products;
    }
    
    
    
    public List<String> filterProducts(String query) {
        return products.stream()
                .filter(item -> item.contains(query))
                .collect(Collectors.toList());
    }

    public String getPairName() {
        return pairName;
    }

    public void setPairName(String pairName) {
        this.pairName = pairName;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
    
    
    
}
