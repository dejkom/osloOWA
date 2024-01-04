/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.facade;

import com.odelowebapp.OSEC.entity.OSECloginLog;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.joda.time.DateTime;

/**
 *
 * @author dematjasic
 */
@Stateless
public class OSECloginLogFacade extends AbstractFacade<OSECloginLog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECloginLogFacade() {
        super(OSECloginLog.class);
    }
    
    public List<OSECloginLog> findAllLoggedInOnLine(int lineID, int shift) {
        em = getEntityManager();
        DateTime today = new DateTime();
        List<OSECloginLog> forreturn;
        try {
            Query query = em.createQuery("SELECT l FROM OSECloginLog l WHERE l.lineID = :lineid AND l.loginShift = :shift AND l.loginDate = :date AND l.loginEnd = NULL");
            //query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            query.setParameter("lineid", lineID);
            query.setParameter("shift", shift);
            query.setParameter("date", today.toLocalDate().toDate());
            forreturn = query.getResultList();
            System.out.println("Nasel prijavljenih:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllLoggedInOnLine():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<OSECloginLog> findAllLoggedInOnLineIgnoreShift(int lineID) {
        em = getEntityManager();
        DateTime today = new DateTime();
        List<OSECloginLog> forreturn;
        try {
            Query query = em.createQuery("SELECT l FROM OSECloginLog l WHERE l.lineID = :lineid AND l.loginDate = :date AND l.loginEnd = NULL");
            //query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            query.setParameter("lineid", lineID);
            query.setParameter("date", today.toLocalDate().toDate());
            forreturn = query.getResultList();
            System.out.println("Nasel prijavljenih:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllLoggedInOnLineIgnoreShift():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<OSECloginLog> findAllLoggedInOnLineIgnoreShiftAndDate(int lineID) {
        em = getEntityManager();
        DateTime today = new DateTime();
        List<OSECloginLog> forreturn;
        try {
            Query query = em.createQuery("SELECT l FROM OSECloginLog l WHERE l.lineID = :lineid AND l.loginEnd = NULL");
            //query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            query.setParameter("lineid", lineID);
            forreturn = query.getResultList();
            System.out.println("Nasel prijavljenih:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllLoggedInOnLineIgnoreShiftAndDate():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
