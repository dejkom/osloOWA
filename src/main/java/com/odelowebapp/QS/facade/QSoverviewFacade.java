/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.QS.facade;

import com.odelowebapp.QS.entity.QSoverview;
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
public class QSoverviewFacade extends AbstractFacade<QSoverview> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QSoverviewFacade() {
        super(QSoverview.class);
    }
    
    public List<QSoverview> findAllByParentID(int pid) {
        em = getEntityManager();
        List<QSoverview> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM QSoverview p WHERE  p.tid.id = :pid AND (p.disabled != 1 OR p.disabled is null)");
            query.setParameter("pid", pid);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllByParentID:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
