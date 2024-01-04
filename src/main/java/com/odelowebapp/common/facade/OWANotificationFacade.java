/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.facade;

import com.odelowebapp.common.entity.OWANotification;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;

/**
 *
 * @author dematjasic
 */
@Stateless
public class OWANotificationFacade extends AbstractFacade<OWANotification> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OWANotificationFacade() {
        super(OWANotification.class);
    }
    
    public List<OWANotification> findAllByArea(String area) {
        System.out.println("Sem v findAllByArea:"+area);
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);
        List<OWANotification> forreturn;
         try {           
            Query query = em.createQuery("SELECT n FROM OWANotification n WHERE n.showArea = :area AND :nowdt BETWEEN n.visibleFrom AND n.visibleTo");
            query.setParameter("nowdt", today, TemporalType.TIMESTAMP);
            query.setParameter("area", area);
            forreturn = query.getResultList();            
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
