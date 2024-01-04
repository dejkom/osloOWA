/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseInstructorsWorkLog;
import java.util.ArrayList;
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
public class HRCourseInstructorsWorkLogFacade extends AbstractFacade<HRCourseInstructorsWorkLog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseInstructorsWorkLogFacade() {
        super(HRCourseInstructorsWorkLog.class);
    }
    
    public List<HRCourseInstructorsWorkLog> getAdditionalInstructorsByCourseOfferingID(int coid){
        em = getEntityManager();
        List<HRCourseInstructorsWorkLog> uporabnikizavracanje = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM HRCourseInstructorsWorkLog u WHERE u.courseOfferingID = :coid", HRCourseInstructorsWorkLog.class);
            query.setParameter("coid", coid);
            uporabnikizavracanje = query.getResultList();
            System.out.println("Vracam dodatnih instruktorjev:"+uporabnikizavracanje.size());
            return uporabnikizavracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel dodatnih instruktorjev: "+e);
            return uporabnikizavracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
