/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.facade;

import com.odelowebapp.OSEC.entity.OSECMachine;
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
public class OSECMachineFacade extends AbstractFacade<OSECMachine> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECMachineFacade() {
        super(OSECMachine.class);
    }
    
    public List<OSECMachine> findAllMachinesForPlace(int placeID) {
        em = getEntityManager();
        List<OSECMachine> forreturn;
         try {           
            Query query = em.createQuery("SELECT p FROM OSECMachine p WHERE p.lineIDfk.lineID = :lineIN AND p.active = TRUE");
            query.setParameter("lineIN", placeID);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllMachinesForPlace:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
