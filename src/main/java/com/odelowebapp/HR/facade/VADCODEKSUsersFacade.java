/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.VADCODEKSUsers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Stateless
public class VADCODEKSUsersFacade extends AbstractFacade<VADCODEKSUsers> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VADCODEKSUsersFacade() {
        super(VADCODEKSUsers.class);
    }
    
    public VADCODEKSUsers findUserByUsername(String adusername){
        em = getEntityManager();
        System.out.println("sem v findUserByUsername");
        try {
            Query query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE u.username = :adusername", VADCODEKSUsers.class);
            query.setParameter("adusername", adusername);
            return (VADCODEKSUsers)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika z usernameom: "+adusername+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public VADCODEKSUsers findUserByCodeksId(int codeksid){
        em = getEntityManager();
        System.out.println("sem v findUserByCodeksId");
        try {
            Query query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE u.id = :codeksid", VADCODEKSUsers.class);
            query.setParameter("codeksid", codeksid);
            return (VADCODEKSUsers)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika z codeksidjem: "+codeksid+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public VADCODEKSUsers findUserByExternalId(String externalid){
        em = getEntityManager();
        System.out.println("sem v findUserByExternalId");
        try {
            Query query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE u.externalId = :externalid", VADCODEKSUsers.class);
            query.setParameter("externalid", externalid);
            return (VADCODEKSUsers)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika z externalidjem: "+externalid+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public List<VADCODEKSUsers> findUsersByCodeksIds(List<Integer> cids){
        em = getEntityManager();
        System.out.println("sem v findUsersByCodeksIds");
        try {
            Query query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE u.id IN :cids", VADCODEKSUsers.class);
            query.setParameter("cids", cids);
            return (List<VADCODEKSUsers>) query.getResultList();
        } catch (Exception e) {
            System.out.println("napaka findUsersByCodeksIds "+" "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

    public List<VADCODEKSUsers> searchUserByNameOrSurnameOrPhone(String input){
        em = getEntityManager();
        System.out.println("sem v searchUserByNameOrSurnameOrPhone");
        input = input.trim();
        String search = "%" + input + "%";
        String search1;
        String search2;
        String search3;
        if (input.contains(" ")){
            search1 = "%" + input.split(" ")[0] + "%";
            search2 = "%" + input.split(" ")[1] + "%";
            search3 = "%" + input.split(" ")[2] + "%";
        }
        else{
            search1 = search;
            search2 = search;
            search3 = search;
        }

        try {
            TypedQuery<VADCODEKSUsers> query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE (((u.firstname LIKE :search) OR (u.lastname LIKE :search) OR (u.telephoneNumber LIKE :search) OR (u.title LIKE :search))" +
                    "OR ((u.firstname LIKE :search1) AND (u.lastname LIKE :search2)) OR ((u.firstname LIKE :search2) AND (u.lastname LIKE :search1))  OR ((u.firstname LIKE :search3) AND (u.lastname LIKE :search3)))" +
                    "AND (((u.firstname IS NOT NULL) AND (u.lastname IS NOT NULL)) AND ((u.telephoneNumber IS NOT NULL) OR (u.mail IS NOT NULL))) ORDER BY u.firstname, u.lastname", VADCODEKSUsers.class);
            query.setParameter("search", search);
            query.setParameter("search1", search1);
            query.setParameter("search2", search2);
            query.setParameter("search3", search3);
            List<VADCODEKSUsers> rez = query.getResultList();
            System.out.println("Vrnjenih kontaktov: " + rez.size());
            return rez;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika z imenom, priimkom, telefonsko: "+search+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

    public List<VADCODEKSUsers> findAllNotNull(){
        em = getEntityManager();
        System.out.println("sem v findAllNotNull");

        try {
            TypedQuery<VADCODEKSUsers> query = em.createQuery("SELECT u FROM VADCODEKSUsers u WHERE ((u.firstname IS NOT NULL) AND (u.lastname IS NOT NULL)) AND" +
                    " ((u.telephoneNumber IS NOT NULL) OR (u.mail IS NOT NULL)) ORDER BY u.firstname, u.lastname", VADCODEKSUsers.class);
            List<VADCODEKSUsers> rez = query.getResultList();
            System.out.println("Vrnjenih kontaktov: " + rez.size());
            return rez;
        } catch (Exception e){
            System.out.println("Nisem našel not null kontaktov" + e);
            return null;
        } finally {
            //em.close();
        }
    }
}
