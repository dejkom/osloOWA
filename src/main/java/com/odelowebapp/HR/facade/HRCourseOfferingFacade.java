/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import org.joda.time.DateTime;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dean
 */
@Stateless
public class HRCourseOfferingFacade extends AbstractFacade<HRCourseOffering> {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HRCourseOfferingFacade() {
        super(HRCourseOffering.class);
    }
    
    public void refreshDean() {
        System.out.println("Sem v refreshDean EM");
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    public List<HRCourseOffering> findAllCoursesForToday() {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate >= :dat AND p.courseDate < :tomorrow AND p.courseID.courseTypeID.courseTypeID != 121");
            query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllCourseOfferingsPersonAttendedOrWill(int userid) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        try {            
            //Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseID.courseTypeID.courseTypeID != 121 AND (:userid IN p.hRCourseAttendanceList.codeksID)");
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p \n"
                    + "JOIN p.hRCourseAttendanceList attendance\n"
                    + "WHERE p.courseID.courseTypeID.courseTypeID != 121 \n"
                    + "AND :userid = attendance.codeksID ORDER BY p.courseDate DESC");
            query.setParameter("userid", userid);
            System.out.println("QUERY:" + query.toString());
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllOdredbeInFuture(int numberofdays) {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(numberofdays);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate >= :dat AND p.courseDate < :tomorrow AND p.courseID.courseTypeID.courseTypeID = 121");
            query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllCoursesInPast() {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE p.courseDate < :tomorrow");
            //query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCoursesInPast():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findNextCourseOfferings(int howmany) {
        em = getEntityManager();
        Date today = new Date();
        //DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate >= :dat ORDER BY p.courseDate ASC");
            query.setParameter("dat", today, TemporalType.DATE);
            //query.setParameter("howmany", howmany);
            forreturn = query.setMaxResults(howmany).getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findNextCourseOfferings():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findNextCourseOfferingsAllowingSelfRegistration(int howmany) {
        em = getEntityManager();
        Date today = new Date();
        //DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate > :dat AND p.courseID.selfRegistration = 1 ORDER BY p.courseDate ASC");
            query.setParameter("dat", today, TemporalType.DATE);
            //query.setParameter("howmany", howmany);
            forreturn = query.setMaxResults(howmany).getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findNextCourseOfferings():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findNextCourseOfferingsByInstructorOrCreator(int userid) {
        em = getEntityManager();
        Date today = new Date();
        //DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate >= :dat AND (p.creator = :userid OR p.instructorID.codeksID = :userid) AND p.courseID.courseTypeID.courseTypeID != 121 ORDER BY p.courseDate ASC");
            query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("userid", userid);
            //query.setParameter("howmany", howmany);
            
            forreturn = query.getResultList();            
            
            System.out.println("findNextCourseOfferingsByInstructorOrCreator vrača vrstic:" + forreturn.size());
            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findNextCourseOfferingsByInstructorOrCreator():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findNextUsersCourseOfferings(int howmany, int userid) {
        em = getEntityManager();
        Date today = new Date();
        //DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {
            Query query2 = em.createQuery("SELECT p.courseOfferingID.courseOfferingID FROM HRCourseAttendance p WHERE p.wasAttended=0 AND p.codeksID = :cid AND p.courseOfferingID.courseDate >= :dat ORDER BY p.courseOfferingID.courseDate ASC");
            query2.setParameter("cid", userid);
            query2.setParameter("dat", today, TemporalType.DATE);
            List<Integer> resultList = new ArrayList();
            resultList = query2.setMaxResults(howmany).getResultList();
            
            List<Integer> novlist = new ArrayList();
            novlist.add(0);
            for (Integer i : resultList) {
                System.out.println("najden CourseOfferingID ki bi ga moral prikazati:" + i);
                novlist.add(i);
            }
            
            System.out.println("resultlist size:" + resultList.size());
            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  (p.courseDate >= :dat AND p.instructorID.codeksID = :userid) OR (p.courseOfferingID IN :cofferinglist ) ORDER BY p.courseDate ASC");
            query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("userid", userid);
            query.setParameter("cofferinglist", novlist);
            //query.setParameter("howmany", howmany);
            forreturn = query.setMaxResults(howmany).getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findNextUsersCourseOfferings():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCourseOfferingsByInstructor(int coid) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.instructorID.codeksID = :coid AND p.courseID.courseTypeID.courseTypeID != 121 ORDER BY p.courseDate ASC");
            query.setParameter("coid", coid);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCourseOfferingsByInstructor():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findNextCommandmentsByInstructorOrCreator(int userid) {
        em = getEntityManager();
        Date today = new Date();
        //DateTime tomorrow = new DateTime().plusDays(1);
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.courseDate >= :dat AND (p.creator = :userid OR p.instructorID.codeksID = :userid) AND p.courseID.courseTypeID.courseTypeID = 121 ORDER BY p.courseDate ASC");
            query.setParameter("dat", today, TemporalType.DATE);
            query.setParameter("userid", userid);
            //query.setParameter("howmany", howmany);
            
            forreturn = query.getResultList();            
            
            System.out.println("findNextCommandmentsByInstructorOrCreator vrača vrstic:" + forreturn.size());
            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findNextCommandmentsByInstructorOrCreator():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCommandmentsByInstructor(int coid) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  (p.instructorID.codeksID = :coid OR p.creator = :coid)  AND p.courseID.courseTypeID.courseTypeID = 121 ORDER BY p.courseDate ASC");
            query.setParameter("coid", coid);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCommandmentsByInstructor():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCommandmentsHistoryByInstructor(int coid, int numberofdaysinpast) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        DateTime today = new DateTime().minusMinutes(1);
        DateTime past = new DateTime().minusDays(numberofdaysinpast);
        
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  (p.instructorID.codeksID = :coid OR p.creator = :coid)  AND p.courseID.courseTypeID.courseTypeID = 121 AND p.courseDate < :today AND p.courseDate > :past ORDER BY p.courseDate DESC");
            query.setParameter("coid", coid);
            query.setParameter("today", today.toDate());
            query.setParameter("past", past.toDate());
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCommandmentsHistoryByInstructor():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCommandmentsHistoryByInstructors(List<Integer> coids, int numberofdaysinpast) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        DateTime today = new DateTime().minusMinutes(1);
        DateTime past = new DateTime().minusDays(numberofdaysinpast);
        
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE p.instructorID.codeksID IN :coids AND p.courseID.courseTypeID.courseTypeID = 121 AND p.courseDate < :today AND p.courseDate > :past ORDER BY p.courseDate DESC");
            query.setParameter("coids", coids);
            query.setParameter("today", today.toDate());
            query.setParameter("past", past.toDate());
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCommandmentsHistoryByInstructor():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCourseOfferingsHistoryByInstructor(int coid, int numberofdaysinpast) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        DateTime today = new DateTime().minusMinutes(1);
        DateTime past = new DateTime().minusDays(numberofdaysinpast);
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.instructorID.codeksID = :coid AND p.courseID.courseTypeID.courseTypeID != 121 AND p.courseDate < :today AND p.courseDate > :past ORDER BY p.courseDate DESC");
            query.setParameter("coid", coid);
            query.setParameter("today", today.toDate());
            query.setParameter("past", past.toDate());
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCourseOfferingsHistoryByInstructor():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findCourseOfferingsHistoryByInstructors(List<Integer> coids, int numberofdaysinpast) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        DateTime today = new DateTime().minusMinutes(1);
        DateTime past = new DateTime().minusDays(numberofdaysinpast);
        try {            
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE  p.instructorID.codeksID IN :coids AND p.courseID.courseTypeID.courseTypeID != 121 AND p.courseDate < :today AND p.courseDate > :past ORDER BY p.courseDate DESC");
            query.setParameter("coids", coids);
            query.setParameter("today", today.toDate());
            query.setParameter("past", past.toDate());
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCourseOfferingsHistoryByInstructors():" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllCourseOfferingsWithQSTopic() {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE p.courseID.qsTopic = 1");
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCourseOfferingsWithQSTopic:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllCourseOfferingsTomorrow(List<HRCourseType> ctypes) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        // Get today's date
        LocalDate today = LocalDate.now();

        // Get tomorrow's date
        LocalDate tomorrow = today.plusDays(1);

        // Get the beginning of tomorrow
        LocalDateTime beginningOfTomorrow = LocalDateTime.of(tomorrow, LocalTime.MIN);
        Date beginningOfTomorrowDate = Date.from(beginningOfTomorrow.atZone(ZoneId.systemDefault()).toInstant());

        // Get the end of tomorrow
        LocalDateTime endOfTomorrow = LocalDateTime.of(tomorrow, LocalTime.MAX);
        Date endOfTomorrowDate = Date.from(endOfTomorrow.atZone(ZoneId.systemDefault()).toInstant());

        // Output the results
        System.out.println("Beginning of tomorrow: " + beginningOfTomorrowDate);
        System.out.println("End of tomorrow: " + endOfTomorrowDate);
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE p.courseDate > :start AND p.courseDate < :end AND p.courseID.courseTypeID in :ctypes");
            query.setParameter("start", beginningOfTomorrowDate);
            query.setParameter("end", endOfTomorrowDate);
            query.setParameter("ctypes", ctypes);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCourseOfferingsTomorrow:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseOffering> findAllCourseOfferingsCustomTimeRange(List<HRCourseType> ctypes, Date from, Date to) {
        em = getEntityManager();
        List<HRCourseOffering> forreturn;
        
        // Output the results
        System.out.println("Searching courseofferings from: " + from);
        System.out.println("Searching courseofferings to: " + to);
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseOffering p WHERE p.courseDate >= :start AND p.courseDate <= :end AND p.courseID.courseTypeID in :ctypes ORDER BY p.courseDate ASC");
            query.setParameter("start", from);
            query.setParameter("end", to);
            query.setParameter("ctypes", ctypes);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCourseOfferingsCustomTimeRange:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
