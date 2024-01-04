/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.CODEKS.facade;

import com.odelowebapp.CODEKS.entity.Departments;
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
public class DepartmentsFacade extends AbstractFacade<Departments> {

    @PersistenceContext(unitName = "codeks_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartmentsFacade() {
        super(Departments.class);
    }
    
    public List<Departments> findAllChildDepartments(String departmentfullname) {
        em = getEntityManager();
        List<Departments> forreturn;
         try {           
            Query query = em.createQuery("SELECT p FROM Departments p WHERE p.fullName LIKE :wordl AND p.name != :word AND p.deleted = 0");
            query.setParameter("wordl", "%"+departmentfullname+"%");
            query.setParameter("word", departmentfullname);
            forreturn = query.getResultList();  
//            System.out.println("findAllChildDepartments vraca vrstic:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    public List<Integer> findAllChildDepartmentsIds(String departmentfullname) {
        em = getEntityManager();
        List<Integer> forreturn;
         try {           
            Query query = em.createQuery("SELECT p.id FROM Departments p WHERE p.fullName LIKE :wordl AND p.name != :word AND p.deleted = 0");
            query.setParameter("wordl", "%"+departmentfullname+"%");
            query.setParameter("word", departmentfullname);
            forreturn = query.getResultList();  
//            System.out.println("findAllChildDepartments vraca vrstic:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
