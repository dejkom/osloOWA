/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.VCodeksObiskovalci;
import org.joda.time.DateTime;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class VCodeksObiskovalciFacade extends AbstractFacade<VCodeksObiskovalci> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VCodeksObiskovalciFacade() {
        super(VCodeksObiskovalci.class);
    }
    
    public List<VCodeksObiskovalci> findAllVisitsForToday() {
        em = getEntityManager();
        Date today = new Date();
        DateTime tomorrow = new DateTime().plusDays(1);
        List<VCodeksObiskovalci> forreturn;
         try {  
            Query query = em.createQuery("SELECT p FROM VCodeksObiskovalci p WHERE p.datum = :dat ORDER BY p.vCodeksObiskovalciPK.visitId DESC", VCodeksObiskovalci.class);
            query.setParameter("dat", today, TemporalType.DATE);
//            query.setParameter("tomorrow", tomorrow.toDate(), TemporalType.DATE);
            forreturn = query.getResultList();    
            System.out.println("Nasel obiskovalcev:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
}
