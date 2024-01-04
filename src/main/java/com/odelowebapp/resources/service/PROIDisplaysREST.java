/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.resources.service;

import com.odelowebapp.PRODUCTION.beans.ProdDisplayDataGetterBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author itstudent
 */
@Stateless
@Path("PROIDisplays")
public class PROIDisplaysREST {

    @Inject
    @Push(channel = "PROIDisplaysChannel")
    private PushContext PROIDisplaysChannel;
    
    @Inject
    private ProdDisplayDataGetterBean databean;

    public PROIDisplaysREST() {

    }

    
    @POST
    @Path("refresh")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String refresh() {
        System.out.println("Sem v refresh (REST)");
        databean.readCSV2();
        String exampleJSON = "{\"message\": \"Data has been refreshed in backend\", \"severity\": \"info\" }";
        PROIDisplaysChannel.send(exampleJSON);
        return "Latest refresh time:"+databean.getTimeOfDataReload()+"";
    }
    

    @GET
    @Path("refresh")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String sayHello() {
        System.out.println("Sem v sayHello (REST)");
        System.out.println("Latest refresh time:"+databean.getTimeOfDataReload());
        return "Latest refresh time:"+databean.getTimeOfDataReload()+"";
    }
    

}
