/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.facade;

import com.odelowebapp.AGV.facade.AbstractFacade;
import com.odelowebapp.student.evidenca.entity.ASSType;
import com.odelowebapp.student.qlik_mails.entity.QLIKmailReceivers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Stateless
public class ASSTypeFacade extends AbstractFacade<ASSType> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ASSTypeFacade() {
        super(ASSType.class);
    }
    
    public void evictCache(){
        this.em.getEntityManagerFactory().getCache().evictAll();
    }

    public List<ASSType> findAllActive(){
        em = getEntityManager();
        List<ASSType> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSType a WHERE a.typeDisable = false", ASSType.class);
            forreturn = query.getResultList();
            //System.out.println("Facade findAllActive vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllActive:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<ASSType> findAllActiveParents(){
        em = getEntityManager();
        List<ASSType> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSType a WHERE a.typeDisable = false AND a.iDTypeParent IS NULL ORDER BY a.typeDescription ASC", ASSType.class);
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

    public List<ASSType> findAllActiveChildren(ASSType parent){
        em = getEntityManager();
        List<ASSType> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSType a WHERE a.typeDisable = false AND a.iDTypeParent = ?1 ORDER BY a.typeDescription ASC", ASSType.class);
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
