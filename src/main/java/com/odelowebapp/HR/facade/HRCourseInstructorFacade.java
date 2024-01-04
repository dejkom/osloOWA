/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseInstructor;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;

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
public class HRCourseInstructorFacade extends AbstractFacade<HRCourseInstructor> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseInstructorFacade() {
        super(HRCourseInstructor.class);
    }
    
    public List<HRCourseInstructor> findIfCodeksUserisCourseInstructor(VCodeksUsersAktualniZaposleni who){
        em = getEntityManager();
        List<HRCourseInstructor> uporabnikizavracanje = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM HRCourseInstructor u WHERE u.codeksID = :cid", HRCourseInstructor.class);
            query.setParameter("cid", who.getId());
            uporabnikizavracanje = query.getResultList();
            System.out.println("Že v CourseInstructorjih:"+uporabnikizavracanje.size());
            return uporabnikizavracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov v CourseInstructorjih: "+e);
            return uporabnikizavracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public List<HRCourseInstructor> findInstructorsByCodeksId(List<Integer> codeksids){
        em = getEntityManager();
        List<HRCourseInstructor> uporabnikizavracanje = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM HRCourseInstructor u WHERE u.codeksID in :cid", HRCourseInstructor.class);
            query.setParameter("cid", codeksids);
            uporabnikizavracanje = query.getResultList();
            return uporabnikizavracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov v findInstructorsByCodeksId: "+e);
            return uporabnikizavracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public HRCourseInstructor findInstructorByCodeksId(int who){
        em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM HRCourseInstructor u WHERE u.codeksID = :cid", HRCourseInstructor.class);
            query.setParameter("cid", who);
            return (HRCourseInstructor)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Napaka v findInstructorByCodeksId: "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }


    
}
