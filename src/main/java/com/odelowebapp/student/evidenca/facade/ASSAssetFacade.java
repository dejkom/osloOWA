/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.facade;

import com.odelowebapp.AGV.facade.AbstractFacade;
import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.student.evidenca.entity.ASSLocation;
import com.odelowebapp.student.evidenca.entity.ASSType;

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
public class ASSAssetFacade extends AbstractFacade<ASSAsset> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ASSAssetFacade() {
        super(ASSAsset.class);
    }


    public List<ASSAsset> findAllOfType(ASSType assType){
        em = getEntityManager();
        List<ASSAsset> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSAsset a WHERE a.iDAssetType = ?1 ORDER BY a.iDAssetType.typeDescription ASC", ASSAsset.class);
            query.setParameter(1, assType);
            forreturn = query.getResultList();
            System.out.println("Facade findAllOfType vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllOfType:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<ASSAsset> findAllOfLocation(ASSLocation assLoc){
        em = getEntityManager();
        List<ASSAsset> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSAsset a WHERE a.iDAssetLocation = ?1 ORDER BY a.assetDescription ASC", ASSAsset.class);
            query.setParameter(1, assLoc);
            forreturn = query.getResultList();
            System.out.println("Facade findAllOfLocation vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllOfLocation:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<ASSAsset> findAllAlternateLocations(ASSAsset child){
        System.out.println("SEM V findAllAlternateLocations, vhodni podatek:"+child.getIDAsset());
        em = getEntityManager();
        List<ASSAsset> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSAsset a WHERE a.iDAsset = ?1 OR a.iDAsset = ?2 ORDER BY a.assetDescription ASC", ASSAsset.class);
//            query.setParameter(1, child.getIDAssetParent());
//            query.setParameter(2, child.getIDAsset());
            query.setParameter(1, child.getIDAssetParent().getIDAsset());
            query.setParameter(2, child.getIDAsset());
            forreturn = query.getResultList();
            System.out.println("Facade findAllAlternateLocations vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAlternateLocations:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    //NOT WORKING, JUST PLACEHOLDER IF I DECIDE TO WRITE QUERY
//    public List<ASSAsset> findAllOfTypeWithAllChildren(ASSType assType){
//        em = getEntityManager();
//        List<ASSAsset> forreturn;
//        try {
//            Query query = em.createQuery("SELECT a FROM ASSAsset a WHERE a.iDAssetType = ?1 ORDER BY a.iDAssetType.typeDescription ASC", ASSAsset.class);
//            query.setParameter(1, assType);
//            forreturn = query.getResultList();
//            System.out.println("Facade findAllOfTypeWithAllChildren vraca vrstic:" + forreturn.size());
//            return forreturn;
//        } catch (Exception e) {
//            System.out.println("NAPAKA findAllOfTypeWithAllChildren:" + e);
//            return null;
//        } finally {
//            //em.close();
//        }
//    }

    public boolean updateAssAsset(ASSAsset assAsset){
        em = getEntityManager();
        try {
            Query query = em.createQuery("UPDATE ASSAsset a SET a.iDAssetParent = :iDAssetParent," +
                    "a.assetDescription = :assetDescription," +
                    "a.assetReturn = :assetReturn," +
                    "a.assetDeleted = :assetDeleted," +
                    "a.assetSerialNumber = :assetSerialNumber," +
                    "a.assetComment = :assetComment," +
                    "a.assetUpdated = :assetUpdated," +
                    "a.assetUpdatedUser = :assetUpdatedUser," +
                    "a.iDAssetType = :assetType," +
                    "a.iDAssetLocation = :assetLocation," +
                    "a.assetExpireDays = :assetExpireDays," +
                    "a.assetExpireMonths = :assetExpireMonths," +
                    "a.assetExpireYears = :assetExpireYears" +
                    " WHERE a.iDAsset = :iDAsset");
            query.setParameter("iDAssetParent", assAsset.getIDAssetParent());
            query.setParameter("assetDescription", assAsset.getAssetDescription());
            query.setParameter("assetReturn", assAsset.getAssetReturn());
            query.setParameter("assetDeleted", assAsset.getAssetDeleted());
            query.setParameter("assetSerialNumber", assAsset.getAssetSerialNumber());
            query.setParameter("assetComment", assAsset.getAssetComment());
            query.setParameter("assetUpdated", assAsset.getAssetUpdated());
            query.setParameter("assetUpdatedUser", assAsset.getAssetUpdatedUser());
            query.setParameter("assetType", assAsset.getIDAssetType());
            query.setParameter("assetLocation", assAsset.getIDAssetLocation());
            query.setParameter("assetExpireDays", assAsset.getAssetExpireDays());
            query.setParameter("assetExpireMonths", assAsset.getAssetExpireMonths());
            query.setParameter("assetExpireYears", assAsset.getAssetExpireYears());
            query.setParameter("iDAsset", assAsset.getIDAsset());
            query.executeUpdate();
            System.out.println("Facade updateAssAsset je posodobil podatke");
            return true;
        } catch (Exception e) {
            System.out.println("NAPAKA updateAssAsset: " + e);
            return false;
        } finally {
            //em.close();
        }
    }

    public boolean markAssAssetDeleted(ASSAsset assAsset){
        em = getEntityManager();
        try {
            Query query = em.createQuery("UPDATE ASSAsset a SET a.assetDeleted = true" +
                    " WHERE a.iDAsset = :iDAsset");
            query.setParameter("iDAsset", assAsset.getIDAsset());
            query.executeUpdate();
            System.out.println("Facade markAssAssetDeleted je posodobil podatke");
            return true;
        } catch (Exception e) {
            System.out.println("NAPAKA markAssAssetDeleted: " + e);
            return false;
        } finally {
            //em.close();
        }
    }
    
    public void evictCache(){
        this.em.getEntityManagerFactory().getCache().evictAll();
    }

}
