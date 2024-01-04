/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.facade;

import com.odelowebapp.OSEC.entity.OSECDowntimeReasons;
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
public class OSECDowntimeReasonsFacade extends AbstractFacade<OSECDowntimeReasons> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECDowntimeReasonsFacade() {
        super(OSECDowntimeReasons.class);
    }
    
    public List<OSECDowntimeReasons> findAllActiveReasons() {
        em = getEntityManager();
        List<OSECDowntimeReasons> forreturn;
         try {           
            Query query = em.createQuery("SELECT p FROM OSECDowntimeReasons p WHERE p.active = TRUE");
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActiveReasons:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<OSECDowntimeReasons> findAllActiveReasonsByType(String type) {
        em = getEntityManager();
        List<OSECDowntimeReasons> forreturn;
         try {           
            Query query = em.createQuery("SELECT p FROM OSECDowntimeReasons p WHERE p.active = TRUE AND p.reasonSubgroup = :type");
            query.setParameter("type", type);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActiveReasonsByType:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
