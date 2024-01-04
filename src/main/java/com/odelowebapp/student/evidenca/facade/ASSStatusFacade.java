/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.facade;

import com.odelowebapp.AGV.facade.AbstractFacade;
import com.odelowebapp.student.evidenca.entity.ASSStatus;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author itstudent
 */
@Stateless
public class ASSStatusFacade extends AbstractFacade<ASSStatus> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ASSStatusFacade() {
        super(ASSStatus.class);
    }
    
    public List<ASSStatus> findAllActiveStatuses(){
        em = getEntityManager();
        List<ASSStatus> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSStatus a WHERE a.statusDisable = 0", ASSStatus.class);
            forreturn = query.getResultList();
            System.out.println("Facade findAllActiveStatuses vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActiveStatuses:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
