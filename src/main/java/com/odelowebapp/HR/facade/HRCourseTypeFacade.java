/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dean
 */
@Stateless
public class HRCourseTypeFacade extends AbstractFacade<HRCourseType> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseTypeFacade() {
        super(HRCourseType.class);
    }

    public List<HRCourseType> findAllParents() {
        em = getEntityManager();
        List<HRCourseType> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseType p WHERE  p.parent IS NULL and p.active = 1 ORDER BY p.courseTypeTitle asc", HRCourseType.class);
            forreturn = query.getResultList();
            System.out.println("Facade findAllParents vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllParents:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourseType> findAllChildren(HRCourseType parent) {
        em = getEntityManager();
        List<HRCourseType> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseType p WHERE p.parent = ?1 and p.active = 1 ORDER BY p.courseTypeTitle asc", HRCourseType.class);
            query.setParameter(1, parent);

            forreturn = query.getResultList();
            System.out.println("Facade findAllChildren vraca vrstic: " + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllChildren: " + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourseType> getRecursiveCourseHierarchy(int parentId) {
        List<HRCourseType> result = new ArrayList<>();
        HRCourseType parent = getEntityManager().find(HRCourseType.class, parentId);

        if (parent != null && parent.getActive()) {
            result.add(parent);

            List<HRCourseType> children = parent.getHRCourseTypeList();
            for (HRCourseType child : children) {
                result.addAll(getRecursiveCourseHierarchy(child.getCourseTypeID()));
            }
        }

        return result;
    }

}
