/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVorderType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVorderTypeFacade extends AbstractFacade<AGVorderType> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVorderTypeFacade() {
        super(AGVorderType.class);
    }
    
    public AGVorderType findByOrderName(String orderName) {
        em = getEntityManager();
        AGVorderType forreturn;
         try {           
            TypedQuery<AGVorderType> query = em.createNamedQuery("AGVorderType.findByOrderName", AGVorderType.class).setParameter("orderName", orderName);
            forreturn = query.getSingleResult();    
            System.out.println("findByOrderName() returned:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
