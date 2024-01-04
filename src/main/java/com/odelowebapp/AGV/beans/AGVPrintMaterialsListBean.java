/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.beans;

import com.odelowebapp.AGV.entity.AGVcatalog;
import com.odelowebapp.AGV.entity.AGVorder;
import com.odelowebapp.AGV.entity.AGVplace;
import com.odelowebapp.AGV.facade.AGVcatalogFacade;
import com.odelowebapp.AGV.facade.AGVorderFacade;
import com.odelowebapp.AGV.facade.AGVplaceFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dematjasic
 */
@Named("agvPrintMaterialsListBean")
@ViewScoped
public class AGVPrintMaterialsListBean implements Serializable {

    private String placeIdFromURLparam = "";   
    private AGVplace place;

    @EJB
    private AGVplaceFacade placeFacade;
    @EJB
    private AGVcatalogFacade catalogFacade;
    @EJB
    private AGVorderFacade orderFacade;
    
    private Map<String, List<AGVcatalog>> catalogWhatICanOrderTEST2;
    private Map<String, List<AGVcatalog>> catalogWhatICanSupplyTEST2;
    
    private List<AGVorder> ordersForPrint;
    
    private String nowFormatted;
    
    @PostConstruct
    public void init() {       
        System.out.println("Sem v init() AGVPrintMaterialsListBean");
        placeIdFromURLparam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("placeId");

        System.out.println("PARAM:" + placeIdFromURLparam);
        if (placeIdFromURLparam != null) {
            place = placeFacade.find(Integer.parseInt(placeIdFromURLparam));
            
            catalogWhatICanOrderTEST2 = catalogFacade.findWhatICanOrderTEST2(place);           
            catalogWhatICanSupplyTEST2 = catalogFacade.findWhatICanSupplyTEST2(place);
            
            ordersForPrint = orderFacade.findRelevantOrdersForMe(place);
            
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");  
            nowFormatted = dateFormat.format(new Date());
           
        } else {
            System.out.println("Manjka URL parameter");
        }
        
    }

    public Map<String, List<AGVcatalog>> getCatalogWhatICanOrderTEST2() {
        return catalogWhatICanOrderTEST2;
    }

    public void setCatalogWhatICanOrderTEST2(Map<String, List<AGVcatalog>> catalogWhatICanOrderTEST2) {
        this.catalogWhatICanOrderTEST2 = catalogWhatICanOrderTEST2;
    }

    public String getNowFormatted() {
        return nowFormatted;
    }

    public void setNowFormatted(String nowFormatted) {
        this.nowFormatted = nowFormatted;
    }

    public Map<String, List<AGVcatalog>> getCatalogWhatICanSupplyTEST2() {
        return catalogWhatICanSupplyTEST2;
    }

    public void setCatalogWhatICanSupplyTEST2(Map<String, List<AGVcatalog>> catalogWhatICanSupplyTEST2) {
        this.catalogWhatICanSupplyTEST2 = catalogWhatICanSupplyTEST2;
    }

    public List<AGVorder> getOrdersForPrint() {
        return ordersForPrint;
    }

    public void setOrdersForPrint(List<AGVorder> ordersForPrint) {
        this.ordersForPrint = ordersForPrint;
    }

    

}
