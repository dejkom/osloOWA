/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.CODEKS.facade;

import com.odelowebapp.CODEKS.entity.Users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "codeks_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findUsersByCard(String card) {
        em = getEntityManager();
        List<Users> forreturn = new ArrayList();
         try {           
            TypedQuery<Users> query = em.createNamedQuery("Users.findByCard", Users.class).setParameter("card", card);
            forreturn = query.getResultList();    
            System.out.println("findUsersByCard() returned size:"+forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return forreturn; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public Users findUserByCard(String card) {
        em = getEntityManager();
        Users forreturn;
         try {           
            TypedQuery<Users> query = em.createNamedQuery("Users.findByCard", Users.class).setParameter("card", card);
            forreturn = (Users)query.getSingleResult();    
            System.out.println("findUsersByCard() returned size:"+forreturn);
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:"+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
}
