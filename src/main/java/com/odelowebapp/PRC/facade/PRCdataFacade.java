/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.facade;

import com.odelowebapp.PRC.entity.PRCdata;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dematjasic
 */
@Stateless
public class PRCdataFacade extends AbstractFacade<PRCdata> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PRCdataFacade() {
        super(PRCdata.class);
    }
    
    public List<PRCdata> findAllByBmwProduct(String bmwp) {
        em = getEntityManager();
        List<PRCdata> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM PRCdata p WHERE  p.product = :product");
            query.setParameter("product", bmwp);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA PRCdata findAllByBmwProduct:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<PRCdata> findAllByBmwProductCurentlyActive(String bmwp) {
        em = getEntityManager();
        List<PRCdata> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM PRCdata p WHERE  p.product = :product and :today BETWEEN p.dateFrom AND p.dateTo");
            query.setParameter("product", bmwp);
            query.setParameter("today", new Date());
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA PRCdata findAllByBmwProduct:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
