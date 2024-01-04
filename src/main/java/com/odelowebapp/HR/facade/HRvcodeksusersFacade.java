/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.VCodeksUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import si.odelo.lean.delnes.entity.Departments;

/**
 *
 * @author drazpotnik
 */
@Stateless
public class HRvcodeksusersFacade extends AbstractFacade<VCodeksUsers> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRvcodeksusersFacade() {
        //super(VCodeksUsers.class);
        super(VCodeksUsers.class);
    }
    
    public List<VCodeksUsersAktualniZaposleni> zaposleniZaAndroidAplikacijo(){
        em = getEntityManager();
        List<VCodeksUsersAktualniZaposleni> uporabnikizavracanje = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u", VCodeksUsersAktualniZaposleni.class);
            uporabnikizavracanje = query.getResultList();
            System.out.println("Aktualnih zaposlenih:"+uporabnikizavracanje.size());
            return uporabnikizavracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov: "+e);
            return uporabnikizavracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public VCodeksUsersAktualniZaposleni findUserByCard(String card){
        em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u WHERE u.card = :card", VCodeksUsersAktualniZaposleni.class);
            query.setParameter("card", card);
            return (VCodeksUsersAktualniZaposleni)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika s kartico: "+card+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public VCodeksUsersAktualniZaposleni findUserByCodeksId(int id){
        em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u WHERE u.id = :id", VCodeksUsersAktualniZaposleni.class);
            query.setParameter("id", id);
            return (VCodeksUsersAktualniZaposleni)query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnika z id-jem: "+id+" | "+e);
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public HashMap<Integer,VCodeksUsersAktualniZaposleni> vsizaposleniZaAndroidAplikacijoHM(){
        em = getEntityManager();
        List<VCodeksUsersAktualniZaposleni> uporabnikizavracanje = new ArrayList<VCodeksUsersAktualniZaposleni>();
        HashMap<Integer,VCodeksUsersAktualniZaposleni> hmvracanje = new HashMap<Integer,VCodeksUsersAktualniZaposleni>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u", VCodeksUsersAktualniZaposleni.class);
            uporabnikizavracanje = query.getResultList();
            System.out.println("Aktualnih zaposlenih:"+uporabnikizavracanje.size());
            for(VCodeksUsersAktualniZaposleni u:uporabnikizavracanje){
                hmvracanje.put(u.getId(), u);
            }
            return hmvracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov: "+e);
            return hmvracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    
    
    public HashMap<Integer,VCodeksUsersAktualniZaposleni> zaposleniZaAndroidAplikacijoHM(List<Integer> idji){
        em = getEntityManager();
        List<VCodeksUsersAktualniZaposleni> uporabnikizavracanje = new ArrayList<VCodeksUsersAktualniZaposleni>();
        HashMap<Integer,VCodeksUsersAktualniZaposleni> hmvracanje = new HashMap<Integer,VCodeksUsersAktualniZaposleni>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u WHERE u.id IN :listidjev", VCodeksUsersAktualniZaposleni.class);
            query.setParameter("listidjev", idji);
            uporabnikizavracanje = query.getResultList();
            System.out.println("Aktualnih zaposlenih:"+uporabnikizavracanje.size());
            for(VCodeksUsersAktualniZaposleni u:uporabnikizavracanje){
                hmvracanje.put(u.getId(), u);
            }
            return hmvracanje;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov: "+e);
            return hmvracanje; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

public List<VCodeksUsers> pridobiZaposleneIdOddelkov(List<Integer> oddelki)
    {
        em = getEntityManager();
        List<VCodeksUsers> osebe = new ArrayList<VCodeksUsers>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsers u where u.departmentId IN :oddelki", VCodeksUsers.class);
            query.setParameter("oddelki", oddelki);
            osebe = query.getResultList();
            System.out.println("Aktualnih zaposlenih:"+osebe.size());
            return osebe;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov: "+e);
            return osebe; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
        
    }

public List<VCodeksUsersAktualniZaposleni> pridobiZaposleneIdOddelkovAktualniZaposleni(List<Integer> oddelki)
    {
        em = getEntityManager();
        List<VCodeksUsersAktualniZaposleni> osebe = new ArrayList<VCodeksUsersAktualniZaposleni>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsersAktualniZaposleni u where u.departmentId IN :oddelki", VCodeksUsersAktualniZaposleni.class);
            query.setParameter("oddelki", oddelki);
            osebe = query.getResultList();
            System.out.println("Aktualnih zaposlenih:"+osebe.size());
            return osebe;
        } catch (Exception e) {
            System.out.println("Nisem našel uporabnikov: "+e);
            return osebe; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
        
    }
    
    public void pridobiPredlaganeNadure(int id,int leto,int mesec)
    {
        em = getEntityManager();
        
    }
    
    public VCodeksUsers getUserByExternalId(String externalid)
    {
        em = getEntityManager();
        List<VCodeksUsers> osebe = new ArrayList<VCodeksUsers>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsers u where u.externalId = :externalid", VCodeksUsers.class);
            query.setParameter("externalid", externalid);
            osebe = query.getResultList();
            return osebe.get(0);
        } catch (Exception e) {
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public VCodeksUsers getUserByCodeksId(String cid)
    {
        em = getEntityManager();
        List<VCodeksUsers> osebe = new ArrayList<VCodeksUsers>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsers u where u.id = :cid", VCodeksUsers.class);
            query.setParameter("cid", cid);
            osebe = query.getResultList();
            return osebe.get(0);
        } catch (Exception e) {
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }
    
    public List<VCodeksUsers> getAllUsersWithNonexistent()
    {
        em = getEntityManager();
        List<VCodeksUsers> osebe = new ArrayList<VCodeksUsers>();
        try {
            Query query = em.createQuery("SELECT u FROM VCodeksUsers u ", VCodeksUsers.class);
            osebe = query.getResultList();
            return osebe;
        } catch (Exception e) {
            return null; //če nič ne najde je torej prazen arraylist
        } finally {
            //em.close();
        }
    }

    
}
