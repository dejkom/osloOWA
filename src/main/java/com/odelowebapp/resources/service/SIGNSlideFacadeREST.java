/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.resources.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odelowebapp.entity.SIGNSlide;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Stateless
@Path("SIGNSlide")
public class SIGNSlideFacadeREST extends AbstractFacade<SIGNSlide> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public SIGNSlideFacadeREST() {
        super(SIGNSlide.class);
    }

    /*@POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SIGNSlideTest entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, SIGNSlideTest entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SIGNSlideTest find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNSlideTest> findAll() {
        return super.findAll();
    }*/
    
    @GET
    @Path("findByPresentationId/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findByPresentationId(@PathParam("id") String id) {
        List<SIGNSlide> all = super.findAll();
        List<SIGNSlide> filteredByPresentation = new ArrayList<>();
        for (SIGNSlide var : all) {
            if (id != null && var.getIdPresentation().getIdPresentation().toString().equals(id)) {
                filteredByPresentation.add(var);
            }
        }
        Comparator<SIGNSlide> compareByPosition = (SIGNSlide x1, SIGNSlide x2)
                -> x1.getPosition().compareTo(x2.getPosition());
        Collections.sort(filteredByPresentation, compareByPosition);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonString = gson.toJson(filteredByPresentation);

        return Response
                .ok(jsonString)
                .build();
    }

    /*@GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNSlideTest> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }*/

    /*
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
