/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.resources.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odelowebapp.entity.SIGNTelevision;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author itstudent
 */
@Stateless
@Path("SIGNtelevision")
public class SIGNTelevisionFacadeREST extends AbstractFacade<SIGNTelevision> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public SIGNTelevisionFacadeREST() {
        super(SIGNTelevision.class);
    }

    /*@POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SIGNTelevision entity) {
        //super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, SIGNTelevision entity) {
        //super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        //super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SIGNTelevision find(@PathParam("id") String id) {
        //return super.find(id); //NOT WORKING
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNTelevision> findAll() {
        //return super.findAll();//NOT WORKING
        return null;
    }*/

    @GET
    @Path("getByIpaddress")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findByIpAddress(@Context HttpServletRequest request, @QueryParam("ip") String ipaddress) {
        if (ipaddress.equals("")) {
            ipaddress = request.getRemoteAddr();
        }
        
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "rest/getbyIpaddress - requested by IP: {0}", request.getRemoteAddr());

        for (SIGNTelevision var : super.findAll()) {
            if (var.getIpAddress() == null ? ipaddress == null : var.getIpAddress().equals(ipaddress)) {
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                String jsonString = gson.toJson(var);

                return Response.ok(jsonString).build();
            }
        }
        return Response.status(204).tag("Televison with ip address" + ipaddress + "is not in database").build();
    }
/*
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNTelevision> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        //return super.findRange(new int[]{from, to}); //NOT WORKING
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
*/
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
