/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRVisitLog;
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
public class HRVisitLogFacade extends AbstractFacade<HRVisitLog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRVisitLogFacade() {
        super(HRVisitLog.class);
    }
    
    public List<HRVisitLog> findLogsForVisitID(int visitID) {
        em = getEntityManager();
        List<HRVisitLog> forreturn;
        try {

            Query query = em.createQuery("SELECT p FROM HRVisitLog p WHERE p.hRVisitLogPK.visitID = :visitid");
            query.setParameter("visitid", visitID);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findLogsForVisitID:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
