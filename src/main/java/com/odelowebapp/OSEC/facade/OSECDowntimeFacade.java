/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.facade;

import com.odelowebapp.OSEC.entity.OSECDowntime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;

/**
 *
 * @author dematjasic
 */
@Stateless
public class OSECDowntimeFacade extends AbstractFacade<OSECDowntime> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECDowntimeFacade() {
        super(OSECDowntime.class);
    }

    public List<OSECDowntime> findDowntimesForLine(int lineID) {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);
        List<OSECDowntime> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM OSECDowntime p WHERE p.oSECMachine.lineIDfk.lineID = :lineid");
            //query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            query.setParameter("lineid", lineID);
            forreturn = query.getResultList();
            System.out.println("Nasel zastojev:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findDowntimesForLine():" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<OSECDowntime> findDowntimesForLineCurrentShift(int lineID, int shiftIN, Date dateIN) {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIN);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateNT = calendar.getTime();

        List<OSECDowntime> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM OSECDowntime p WHERE p.oSECMachine.lineIDfk.lineID = :lineid AND p.shift = :shift AND p.date = :date");
            //query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            query.setParameter("lineid", lineID);
            query.setParameter("shift", shiftIN);
            query.setParameter("date", dateNT, TemporalType.DATE);
            forreturn = query.getResultList();
            System.out.println("Nasel zastojev:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findDowntimesForLine():" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<String> findPrivilegedUsersForDowntimeEntry() {
        em = getEntityManager();
        List<String> forreturn;
        try {
            Query query = em.createNativeQuery("SELECT [externalId] FROM [OWA].[dbo].[OSEC_privilaged_users] where [status]=1");
            forreturn = query.getResultList();
            System.out.println("Nasel priviligiranih uporabnikov:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findPrivilegedUsersForDowntimeEntry():" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<OSECDowntime> findDowntimesByStatus(int status) {
        em = getEntityManager();
        List<OSECDowntime> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM OSECDowntime p WHERE p.status = :statusint ORDER BY p.startD DESC");
            query.setParameter("statusint", status);
            forreturn = query.getResultList();
            System.out.println("Nasel zastojev:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findDowntimesByStatus():" + e);
            return null;
        } finally {
            //em.close();
        }
    }

}
