/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.resources.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odelowebapp.entity.SIGNPresentation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Stateless
@Path("SIGNpresentation")
public class SIGNPresentationFacadeREST extends AbstractFacade<SIGNPresentation> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public SIGNPresentationFacadeREST() {
        super(SIGNPresentation.class);
    }

    /*@POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SIGNPresentation entity) {
        if (SecurityUtils.getSubject().isPermitted("hr:*")) {
            super.create(entity);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, SIGNPresentation entity) {
        //super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        //super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SIGNPresentation find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNPresentation> findAll() {
            //return super.findAll(); //NOT WORKING
        return null;
    }*/

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response all() {
        List<SIGNPresentation> all = super.findAll();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonString = gson.toJson(all);
        return Response
                .ok(jsonString)
                .build();
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNPresentation> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        //return super.findRange(new int[]{from, to}); //NOT WORKING
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }*/

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
