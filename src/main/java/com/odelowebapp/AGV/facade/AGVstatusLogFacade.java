/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVorder;
import com.odelowebapp.AGV.entity.AGVstatusLog;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVstatusLogFacade extends AbstractFacade<AGVstatusLog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVstatusLogFacade() {
        super(AGVstatusLog.class);
    }
    
    public List<AGVstatusLog> findStatusesByOrderID(AGVorder order) {
        em = getEntityManager();
        List<AGVstatusLog> forreturn = new ArrayList();
         try {           
            TypedQuery<AGVstatusLog> query = em.createNamedQuery("AGVstatusLog.findByOrderId", AGVstatusLog.class).setParameter("orderId", order);
            forreturn = query.getResultList();    
            System.out.println("findStatusesByOrderID() returned size:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
