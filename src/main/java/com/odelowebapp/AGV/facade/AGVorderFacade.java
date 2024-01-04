/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVorder;
import com.odelowebapp.AGV.entity.AGVplace;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVorderFacade extends AbstractFacade<AGVorder> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVorderFacade() {
        super(AGVorder.class);
    }
    
    public List<AGVorder> findOrdersByMe(AGVplace place) {
        em = getEntityManager();
        List<AGVorder> forreturn = new ArrayList();
         try {           
            Query query = em.createQuery("SELECT p FROM AGVorder p WHERE p.placeTo = :place AND (p.statusId.statusName like '%Submitted%' OR p.statusId.statusName like '%Processing%')", AGVorder.class);
            query.setParameter("place", place);
            forreturn = query.getResultList();    
            Date nowminus2h = new Date(System.currentTimeMillis() - 3600 * 1000 * 2);
            //dodamo še preklicane in realizirane kjer timestamp modifikacije ni starejši od 2 ure
            Query query2 = em.createQuery("SELECT p FROM AGVorder p WHERE p.placeTo = :place AND (p.statusId.statusName like '%Canceled%' OR p.statusId.statusName like '%Sent%') AND p.modifiedTimestamp > :nowminus2h", AGVorder.class);
            query2.setParameter("nowminus2h", nowminus2h);
            query2.setParameter("place", place);
            forreturn.addAll(query2.getResultList());
            //
            
            System.out.println("findOrdersByMe() returned:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public List<AGVorder> findOrdersForMe(AGVplace place) {
        em = getEntityManager();
        List<AGVorder> forreturn = new ArrayList();
         try {           
            Query query = em.createQuery("SELECT p FROM AGVorder p WHERE p.placeFrom = :place ORDER BY p.createdTimestamp DESC", AGVorder.class);
            query.setParameter("place", place);
            forreturn = query.getResultList();    
            System.out.println("findOrdersForMe() returned:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    //Orders in statuses Submitted and processing
    public List<AGVorder> findRelevantOrdersForMe(AGVplace place) {
        em = getEntityManager();
        List<AGVorder> forreturn = new ArrayList();
         try {           
            Query query = em.createQuery("SELECT p FROM AGVorder p WHERE p.placeFrom = :place AND (p.statusId.statusName like '%Submitted' OR p.statusId.statusName like '%Processing' ) ORDER BY p.createdTimestamp DESC", AGVorder.class);
            query.setParameter("place", place);
            forreturn = query.getResultList();    
            System.out.println("findRelevantOrdersForMe() returned:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
      
    public List<AGVplace> findAllPlacesISupplyTo(AGVplace place) {
        em = getEntityManager();
        List<AGVplace> forreturn = new ArrayList();
         try {           
            Query query = em.createQuery("SELECT DISTINCT p.placeTo FROM AGVorder p WHERE p.placeFrom = :place AND (p.statusId.statusName like '%Submitted' OR p.statusId.statusName like '%Processing' ) ORDER BY p.createdTimestamp DESC", AGVplace.class);
            query.setParameter("place", place);
            forreturn = query.getResultList();    
            System.out.println("findAllPlacesISupplyTo() returned:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
