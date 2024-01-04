/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.qlik_mails.facade;

import com.odelowebapp.student.qlik_mails.entity.QLIKmailReceivers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author itstudent
 */
@Stateless
public class QLIKmailReceiversFacade extends AbstractFacade<QLIKmailReceivers> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public QLIKmailReceiversFacade() {
        super(QLIKmailReceivers.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<QLIKmailReceivers> findMailTO(String report) {
        em = getEntityManager();
        List<QLIKmailReceivers> forreturn;
        try {
            Query query = em.createQuery("SELECT m.mailListTO FROM QLIKmailReceivers m WHERE m.reportName = " + report, QLIKmailReceivers.class);
            forreturn = query.getResultList();
            System.out.println("Facade findMailTO vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findMailTO:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public boolean updateMailReceivers(QLIKmailReceivers mailReceiver, String mailTO, String mailCC, String mailBCC) {
        em = getEntityManager();
        try {
            Query query = em.createQuery("UPDATE QLIKmailReceivers m SET m.mailListTO = '" + mailTO + "', m.mailListCC = '" + mailCC + "', m.mailListBCC = '"
                    + mailBCC + "' WHERE m.reportName = '" + mailReceiver.getReportName() + "'");
            query.executeUpdate();
            System.out.println("Facade updateMailReceivers je posodobil podatke");
            return true;
        } catch (Exception e) {
            System.out.println("NAPAKA updateMailReceivers: " + e);
            return false;
        } finally {
            //em.close();
        }
    }
}
