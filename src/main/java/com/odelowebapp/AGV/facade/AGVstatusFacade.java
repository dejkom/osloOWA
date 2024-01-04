/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVorderType;
import com.odelowebapp.AGV.entity.AGVstatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVstatusFacade extends AbstractFacade<AGVstatus> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVstatusFacade() {
        super(AGVstatus.class);
    }
    
     public List<AGVstatus> findStatusByName(String name) {
        em = getEntityManager();
        List<AGVstatus> forreturn = new ArrayList();
         try {           
            TypedQuery<AGVstatus> query = em.createNamedQuery("AGVsatus.findByStatusName", AGVstatus.class).setParameter("statusName", name);
            forreturn = query.getResultList();    
            System.out.println("findStatusByName() returned size:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
     
     public List<AGVstatus> findStatusesForOrderType(AGVorderType orderType) {
        em = getEntityManager();
        List<AGVstatus> forreturn = new ArrayList();
         try {           
            TypedQuery<AGVstatus> query = em.createNamedQuery("AGVstatus.findByOrderType", AGVstatus.class).setParameter("orderType", orderType);
            forreturn = query.getResultList();    
            System.out.println("findStatusesForOrderType() returned size:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
     
     public AGVstatus findNextStatus(AGVstatus currentStatus) {
        em = getEntityManager();
        AGVstatus forreturn; 
         try {           
            Query query = em.createQuery("SELECT s FROM AGVstatus s WHERE s.orderTypeId = :otype and s.statusOrder > :num ORDER BY s.statusOrder asc", AGVstatus.class).setParameter("otype", currentStatus.getOrderTypeId()).setParameter("num", currentStatus.getStatusOrder()).setMaxResults(1);
            forreturn = (AGVstatus) query.getSingleResult();    
            System.out.println("findNextStatus() returned size:"+forreturn.getStatusName());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
     
     public AGVstatus findCanceledStatus(AGVstatus currentStatus) {
        em = getEntityManager();
        AGVstatus forreturn; 
         try {           
            Query query = em.createQuery("SELECT s FROM AGVstatus s WHERE s.orderTypeId = :otype and s.statusName like '%Canceled' ", AGVstatus.class).setParameter("otype", currentStatus.getOrderTypeId()).setMaxResults(1);
            forreturn = (AGVstatus) query.getSingleResult();    
            System.out.println("findCanceledStatus() returned status name:"+forreturn.getStatusName());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
