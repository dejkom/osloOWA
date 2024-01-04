/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRSignProfNazivi;
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
public class HRSignProfNaziviFacade extends AbstractFacade<HRSignProfNazivi> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRSignProfNaziviFacade() {
        super(HRSignProfNazivi.class);
    }

    public List<Object[]> createSeznamZaposlenih() {
        em = getEntityManager();
        List<Object[]> forreturn;
        try {

            Query query = em.createNativeQuery("SELECT DeSifra,\n"
                    + "    DePriimek,\n"
                    + "    DeIme,\n"
                    + "    DeProfitCenter\n"
                    + "    ,[UradniNaziv]\n"
                    + "      ,[RazsirjenSlo]\n"
                    + "      ,[AngUradni]\n"
                    + "      ,[RazsirjenAng]\n"
                    + "      ,[OddelekSlo]\n"
                    + "      ,[OddelekAng]\n"
                    + "FROM HQSQL.SAPS_PL.dbo.Delavci as a\n"
                    + "left join\n"
                    + "[OWA].[dbo].[HRSignProfNazivi] as b on a.DeProfitCenter = b.id\n"
                    + "where  DeSifStat = 'A' or DeSifStat = 'T' or DeSifStat = 'Z'");
            
            forreturn = query.getResultList();
            System.out.println("createSeznamZaposlenih je vrnil vrstic:" + forreturn.size());
            return forreturn;

        } catch (Exception e) {
            System.out.println("NAPAKA createSeznamZaposlenih:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

}
