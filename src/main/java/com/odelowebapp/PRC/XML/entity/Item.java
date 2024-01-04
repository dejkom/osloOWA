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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dematjasic
 */
public class Item implements Serializable {

    @JsonProperty("PRODUCT")
    public String product;

    @JsonProperty("DESCRIPTION")
    public String description;

    @JsonProperty("PRICE_TYPE")
    public String priceType;

    @JsonProperty("PRICE_UNIT_ITEM")
    public String priceUnitItem;

    @JsonProperty("PRICE_UOM")
    public String priceUom;

    @JsonProperty("NET_PRICE")
    public String netPrice;

    @JsonProperty("ITEM_CURRENCY")
    public String itemCurrency;
    
    @JsonProperty("BMW_CBB_AMATNR")
    public String cbbamatnr;
    
    @JsonProperty("BMW_CBB_NAEL")
    public String cbbnael;

    //@JacksonXmlElementWrapper(localName = "CONDITIONS")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonMerge
    @JsonProperty("CONDITIONS")
    public List<Conditions> conditions;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonMerge
    @JsonProperty("DEMAND_LOC")
    public List<Demand_loc> demand_loc;

    public Conditions getBasisPriceCondition() {
        for (Conditions c : conditions) {
            if (c.type.equals("Basispreis")) {
                return c;
            }
        }
        return null;
    }

    public Conditions getRUKLCondition() {
        for (Conditions c : conditions) {
            if (c.type.contains("Residual")) {
                return c;
            }
        }
        return null;
    }

    public Conditions getPACKCondition() {
        for (Conditions c : conditions) {
            if (c.type.contains("VERP") || c.type.contains("LVVV")) {
                return c;
            }
        }
        return null;
    }

    public List<Conditions> getConditionsWithoutBasePrice() {
        List<Conditions> forreturn = new ArrayList();
        for (Conditions c : conditions) {
            if (c.type.contains("Basis")) {

            } else {
                forreturn.add(c);
            }
        }
        return forreturn;
    }

    public Map<String, Conditions> getMapOfAllConditions() {
        Map<String, Conditions> forreturn = Map.of();
        for (Conditions c : conditions) {
            forreturn.put(c.type, c);
        }
        return forreturn;
    }

    public double getRIKCalculatedFromConditionsInXML() {
        double result = 0;
        for (Conditions c : conditions) {
            if (c.type.equals("Basispreis") || c.type.contains("Residual") || c.type.contains("VERP") || c.type.contains("LVVV")) {

            } else {
                double prebrano = c.getValue();
                result = result + prebrano;
            }
        }
        //return result;
        return round(result, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceUnitItem() {
        return priceUnitItem;
    }

    public void setPriceUnitItem(String priceUnitItem) {
        this.priceUnitItem = priceUnitItem;
    }

    public String getPriceUom() {
        return priceUom;
    }

    public void setPriceUom(String priceUom) {
        this.priceUom = priceUom;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }

    public List<Conditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<Conditions> conditions) {
        this.conditions = conditions;
    }

    public List<Demand_loc> getDemand_loc() {
        return demand_loc;
    }

    public void setDemand_loc(List<Demand_loc> demand_loc) {
        this.demand_loc = demand_loc;
    }

    public String getCbbamatnr() {
        return cbbamatnr;
    }

    public void setCbbamatnr(String cbbamatnr) {
        this.cbbamatnr = cbbamatnr;
    }

    public String getCbbnael() {
        return cbbnael;
    }

    public void setCbbnael(String cbbnael) {
        this.cbbnael = cbbnael;
    }
    
    

}
