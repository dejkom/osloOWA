/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.joda.time.DateTime;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Dean
 */
@Stateless
public class HRCourseFacade extends AbstractFacade<HRCourse> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseFacade() {
        super(HRCourse.class);
    }


    public List<HRCourse> findAllCoursesInstructorCanTeach(int instructorid) {
        em = getEntityManager();
        List<HRCourse> forreturn;
        try {

            Query query1 = em.createQuery("SELECT a FROM HRCourseHRCourseInstructor a WHERE a.instructorID.codeksID = :instructorid");
            query1.setParameter("instructorid", instructorid);
            List resultList = query1.getResultList();
            System.out.println("Prvi query result size:" + resultList.size());

            Query query = em.createQuery("SELECT p FROM HRCourse p WHERE p.hRCourseHRCourseInstructorList IN :prviresult");
            query.setParameter("prviresult", resultList);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCoursesInstructorCanTeach:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourse> findAllCoursesWithSelfRegistration() {
        em = getEntityManager();
        List<HRCourse> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourse p WHERE p.selfRegistration = 1");
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCoursesWithSelfRegistration:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourse> findCoursesInstructorCanTeachWithoutQuickInternalEducations(int instructorid) {
        em = getEntityManager();
        List<HRCourse> forreturn;
        try {

            Query query1 = em.createQuery("SELECT a FROM HRCourseHRCourseInstructor a WHERE a.instructorID.codeksID = :instructorid");
            query1.setParameter("instructorid", instructorid);
            List resultList = query1.getResultList();
            System.out.println("Prvi query result size:" + resultList.size());

            Query query = em.createQuery("SELECT p FROM HRCourse p WHERE p.hRCourseHRCourseInstructorList IN :prviresult AND p.courseTypeID.courseTypeID != 3 AND p.courseTypeID.courseTypeID != 121");
            query.setParameter("prviresult", resultList);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCoursesInstructorCanTeachWithoutQuickInternalEducations:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourse> findCommandmentsInstructorCanPerform(int instructorid) {
        em = getEntityManager();
        List<HRCourse> forreturn;
        try {

            Query query1 = em.createQuery("SELECT a FROM HRCourseHRCourseInstructor a WHERE a.instructorID.codeksID = :instructorid");
            query1.setParameter("instructorid", instructorid);
            List resultList = query1.getResultList();
            System.out.println("Prvi query result size:" + resultList.size());

            Query query = em.createQuery("SELECT p FROM HRCourse p WHERE p.hRCourseHRCourseInstructorList IN :prviresult AND p.courseTypeID.courseTypeID = 121");
            query.setParameter("prviresult", resultList);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findCommandmentsInstructorCanPerform:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourse> findAllCoursesWithQSTopic() {
        em = getEntityManager();
        List<HRCourse> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourse p WHERE p.qsTopic = 1");
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllCoursesWithQSTopic:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

}
