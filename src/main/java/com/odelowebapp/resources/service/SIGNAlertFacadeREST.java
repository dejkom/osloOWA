/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.resources.service;

import com.odelowebapp.entity.SIGNAlert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Stateless
@Path("SIGNalert")
public class SIGNAlertFacadeREST extends AbstractFacade<SIGNAlert> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public SIGNAlertFacadeREST() {
        super(SIGNAlert.class);
    }

    /*@POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SIGNAlert entity) {
        //super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, SIGNAlert entity) {
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
    public SIGNAlert find(@PathParam("id") Integer id) {
        //return super.find(id); NOT WORKING
        return null;
    }*/

    @GET
    @Path("last")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLast() {
        List<SIGNAlert> list = super.findAll();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        String responseMessage = "No alerts in database";
        if (!list.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String lastAlertDate = sdf.format(list.get(list.size() - 1).getDate());
            String todayDate = sdf.format(Date.from(date.toInstant()));

            if (lastAlertDate.equals(todayDate)) {
                return Response.ok(list.get(list.size() - 1)).build();
            }
            responseMessage += " for todays date";
        }
        return Response.status(204).tag(responseMessage).build();
    }

    /*
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNAlert> findAll() {
        //return super.findAll();
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SIGNAlert> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        //return super.findRange(new int[]{from, to});
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
