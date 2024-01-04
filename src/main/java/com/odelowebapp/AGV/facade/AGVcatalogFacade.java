/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVcatalog;
import com.odelowebapp.AGV.entity.AGVplace;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVcatalogFacade extends AbstractFacade<AGVcatalog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVcatalogFacade() {
        super(AGVcatalog.class);
    }

//    public List<AGVcatalog> findAllCatalogsForPlace(AGVplace place) {
//        em = getEntityManager();
//        List<AGVcatalog> forreturn = new ArrayList();
//         try {           
//             TypedQuery<AGVcatalog> query = em.createNamedQuery("AGVcatalog.findByPlaceId", AGVcatalog.class).setParameter("placeId", place.getPlaceId());
//            forreturn = query.getResultList();            
//            return forreturn;
//        } catch (Exception e) {
//            System.out.println("NAPAKA:"+e);
//            return forreturn; //če nič ne najde je torej prazen arraylist
//        } finally {
//            //em.close();
//        }
//    }
//
    public List<AGVcatalog> findWhatICanOrder(AGVplace place) {
        em = getEntityManager();
        List<AGVcatalog> forreturn = new ArrayList();
        try {
            TypedQuery<AGVcatalog> query = em.createNamedQuery("AGVcatalog.findWhatICanOrder", AGVcatalog.class).setParameter("placeid", place.getPlaceId());
            forreturn = query.getResultList();
            System.out.println("findWhatICanOrder() returned size:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

    //naslednja metoda je testna, z njo hočem dobiti še material kategorije
    //to se verjetno lahko izbriše!!!!
    public List<AGVcatalog> findWhatICanOrderTEST(AGVplace place) {
        System.out.println("Sem v findWhatICanOrderTEST");
        em = getEntityManager();
        List<AGVcatalog> forreturn = new ArrayList();
        try {
            Query query = em.createNativeQuery("SELECT c.*, m.materialCategory, m.materialDescription, m.materialUnit  FROM [OWA].[dbo].[AGV_catalog] c, [OWA].[dbo].[AGV_material]  m WHERE c.materialId = m.materialId AND c.placeTo = ?1", "CatalogMaterialCategoryMapping");
            query.setParameter(1, place.getPlaceId());
            List<Object[]> forreturn2 = query.getResultList();

            forreturn2.stream().forEach((record) -> {
                AGVcatalog catalog = (AGVcatalog) record[0];
                String category = (String) record[1];
                String description = (String) record[2];
                String materialUnit = (String) record[3];
                //System.out.println("Catalog: materialID [" + catalog.getMaterialId() + "] disabled [" + catalog.getDisabled() + "] category [" + category + "]");
                catalog.setMaterialCategory(category);
                catalog.setMaterialDescription(description);
                catalog.setMaterialUnit(materialUnit);
                forreturn.add(catalog);
            });

            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    
    //naslednja metoda je testna, z njo hočem dobiti še material kategorije
    public Map<String, List<AGVcatalog>> findWhatICanOrderTEST2(AGVplace place) {
        System.out.println("Sem v findWhatICanOrderTEST2");
        em = getEntityManager();
        //List<AGVcatalog> forreturn = new ArrayList();
        Map<String, List<AGVcatalog>> map1 = new HashMap();
        try {
            Query query = em.createNativeQuery("SELECT c.*, m.materialCategory, m.materialDescription, m.materialUnit  FROM [OWA].[dbo].[AGV_catalog] c, [OWA].[dbo].[AGV_material]  m WHERE c.materialId = m.materialId AND c.placeTo = ?1", "CatalogMaterialCategoryMapping");
            query.setParameter(1, place.getPlaceId());
            List<Object[]> forreturn2 = query.getResultList();

            forreturn2.stream().forEach((record) -> {
                AGVcatalog catalog = (AGVcatalog) record[0];
                String category = (String) record[1];
                String description = (String) record[2];
                String materialUnit = (String) record[3];
                //System.out.println("Catalog: materialID [" + catalog.getMaterialId() + "] disabled [" + catalog.getDisabled() + "] category [" + category + "]");
                catalog.setMaterialCategory(category);
                catalog.setMaterialDescription(description);
                catalog.setMaterialUnit(materialUnit);
                //forreturn.add(catalog);
                //map1.put(category, catalog);
                map1.computeIfAbsent(category, k -> new ArrayList<>()).add(catalog);
            });            

            return map1;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return map1; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

//
    public List<AGVcatalog> findWhatICanSupply(AGVplace place) {
        em = getEntityManager();
        List<AGVcatalog> forreturn = new ArrayList();
        try {
            TypedQuery<AGVcatalog> query = em.createNamedQuery("AGVcatalog.findWhatICanSupply", AGVcatalog.class).setParameter("placeid", place.getPlaceId());
            forreturn = query.getResultList();
            System.out.println("findWhatICanSupply() returned size:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    //vrne seznam za tiskanje
    public Map<String, List<AGVcatalog>> findWhatICanSupplyTEST2(AGVplace place) {
        System.out.println("findWhatICanSupplyTEST2");
        em = getEntityManager();
        Map<String, List<AGVcatalog>> map1 = new HashMap();
        try {
            Query query = em.createNativeQuery("SELECT c.*, m.materialCategory, m.materialDescription  FROM [OWA].[dbo].[AGV_catalog] c, [OWA].[dbo].[AGV_material]  m WHERE c.materialId = m.materialId AND c.placeFrom = ?1", "CatalogMaterialCategoryMapping");
            query.setParameter(1, place.getPlaceId());
            List<Object[]> forreturn2 = query.getResultList();

            forreturn2.stream().forEach((record) -> {
                AGVcatalog catalog = (AGVcatalog) record[0];
                String category = (String) record[1];
                String description = (String) record[2];
                String materialUnit = (String) record[3];
                //System.out.println("Catalog: materialID [" + catalog.getMaterialId() + "] disabled [" + catalog.getDisabled() + "] category [" + category + "]");
                catalog.setMaterialCategory(category);
                catalog.setMaterialDescription(description);
                catalog.setMaterialUnit(materialUnit);
                //forreturn.add(catalog);
                //map1.put(category, catalog);
                map1.computeIfAbsent(category, k -> new ArrayList<>()).add(catalog);
            });            

            return map1;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return map1; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

}
