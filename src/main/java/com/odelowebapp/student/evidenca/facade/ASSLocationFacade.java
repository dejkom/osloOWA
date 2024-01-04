/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.facade;

import com.odelowebapp.AGV.facade.AbstractFacade;
import com.odelowebapp.student.evidenca.entity.ASSLocation;
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
public class ASSLocationFacade extends AbstractFacade<ASSLocation> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ASSLocationFacade() {
        super(ASSLocation.class);
    }
    
    public List<ASSLocation> findAllActiveParents(){
        em = getEntityManager();
        List<ASSLocation> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSLocation a WHERE a.locationDisable = false AND a.iDLocationParent IS NULL ORDER BY a.locationDescription ASC", ASSLocation.class);
            forreturn = query.getResultList();
            //System.out.println("Facade findAllActiveParents vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActiveParents:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<ASSLocation> findAllActiveChildren(ASSLocation parent){
        em = getEntityManager();
        List<ASSLocation> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSLocation a WHERE a.locationDisable = false AND a.iDLocationParent = ?1 ORDER BY a.locationDescription ASC", ASSLocation.class);
            query.setParameter(1, parent);
            forreturn = query.getResultList();
            //System.out.println("Facade findAllActiveChildren vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActiveChildren:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
