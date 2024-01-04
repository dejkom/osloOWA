/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.sign.sessionbeans;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author itstudent
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} create(entity)", entityClass.toString());
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} edit(entity)", entityClass.toString());
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} remove(entity)", entityClass.toString());
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} find(id)", entityClass.toString());
        //getEntityManager().getEntityManagerFactory().getCache().evictAll();
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} findAll()", entityClass.toString());
        System.out.println("Sem v FINDALL: "+entityClass.toString());
        //getEntityManager().getEntityManagerFactory().getCache().evictAll();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} findRange()", entityClass.toString());
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AbstractFacade -- in {0} count()", entityClass.toString());
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
