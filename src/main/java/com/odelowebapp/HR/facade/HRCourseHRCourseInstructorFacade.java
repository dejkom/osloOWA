/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.HRCourseHRCourseInstructor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Dean
 */
@Stateless
public class HRCourseHRCourseInstructorFacade extends AbstractFacade<HRCourseHRCourseInstructor> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseHRCourseInstructorFacade() {
        super(HRCourseHRCourseInstructor.class);
    }

    public List<HRCourseHRCourseInstructor> findAllInstructorsForCourse(HRCourse course) {
        em = getEntityManager();
        List<HRCourseHRCourseInstructor> forreturn = new ArrayList();
         try {           
            Query query = em.createQuery("SELECT p FROM HRCourseHRCourseInstructor p WHERE  p.courseID = :course ORDER BY p.instructorID.lastname asc");
            query.setParameter("course", course);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

    
    
    
}
