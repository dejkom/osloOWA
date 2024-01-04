/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.facade;

import com.odelowebapp.PRC.entity.PRCbase;
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
public class PRCbaseFacade extends AbstractFacade<PRCbase> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PRCbaseFacade() {
        super(PRCbase.class);
    }
    
    public List<PRCbase> findAllByBmwProduct(String bmwp) {
        em = getEntityManager();
        List<PRCbase> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM PRCbase p WHERE  p.bmwProduct = :product");
            query.setParameter("product", bmwp);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllByBmwProduct:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<PRCbase> findAllByBmwProductCurentlyActive(String bmwp) {
        em = getEntityManager();
        List<PRCbase> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM PRCbase p WHERE  p.bmwProduct = :product and :today BETWEEN p.dateFrom AND p.dateTo");
            query.setParameter("product", bmwp);
            query.setParameter("today", new Date());
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllByBmwProduct:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
