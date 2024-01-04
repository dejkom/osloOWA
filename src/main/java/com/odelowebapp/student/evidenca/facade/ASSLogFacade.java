/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.facade;

import com.odelowebapp.AGV.facade.AbstractFacade;
import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.student.evidenca.entity.ASSLog;
import com.odelowebapp.student.evidenca.entity.ASS_Q_IzposojenoSredstvo;

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
public class ASSLogFacade extends AbstractFacade<ASSLog> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ASSLogFacade() {
        super(ASSLog.class);
    }

    public Number findSumLogQuantity(ASSAsset assAsset) {
        em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT SUM(l.logQuantity) FROM ASSLog l WHERE l.iDLogAsset = ?1", Integer.class);
            query.setParameter(1, assAsset);
            Number suma = (Number) query.getSingleResult();
            //System.out.println("Facade findSumLogQuantity vraca rezultat:" + suma);
            if (suma == null) {
                return 0;
            } else {
                return suma;
            }
        } catch (Exception e) {
            System.out.println("NAPAKA findSumLogQuantity:" + e);
            return -1;
        } finally {
            //em.close();
        }
    }

    public List<ASS_Q_IzposojenoSredstvo> findWhatIsIzposojeno(String userid) {
        em = getEntityManager();
        List<ASS_Q_IzposojenoSredstvo> list;
        List<Object[]> results;
        try {
            Query query = em.createNativeQuery("SELECT a.IDAsset AS IDAsset, l.LogExternalID AS LogExternalID, a.AssetReturn AS AssetReturn, ISNULL(a.AssetExpireDays, 0 ) AS AssetExpireDays, l.LogTimestamp AS LogTimestamp, l.IDLogStatus, SUM(l.LogQuantity) as suma FROM [OWA].[dbo].[ASS_Asset] a, [OWA].[dbo].[ASS_Log] l\n"
                    + " where a.IDAsset = l.IDLogAsset\n"
                    + " and l.LogExternalID = ?1\n"
                    + " GROUP BY a.IDAsset, l.LogExternalID, a.AssetReturn, a.AssetExpireDays, l.LogTimestamp, l.IDLogStatus ORDER BY l.LogTimestamp ASC \n"
                    + " ", "IzposojenoResultConstructor");
                    //+ " HAVING SUM(l.LogQuantity) < 0", "IzposojenoResultConstructor");
            query.setParameter(1, userid);
            list = query.getResultList();

            //System.out.println("Facade findWhatIsIzposojeno vraca vrstic:" + list.size() +" vsebina:"+list.get(0).getAssetID());
            
            return list;

        } catch (Exception e) {
            System.out.println("NAPAKA findWhatIsIzposojeno:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    //TUKAJ V GROUPBY ni timestampa
    public List<ASS_Q_IzposojenoSredstvo> findWhatIsIzposojenoNoTS(String userid) {
        em = getEntityManager();
        List<ASS_Q_IzposojenoSredstvo> list;
        List<Object[]> results;
        try {
            //Query query = em.createQuery("SELECT a.iDAsset, l.logExternalID, a.assetReturn, SUM(l.logQuantity) FROM ASSAsset a, ASSLog l WHERE a.iDAsset = l.iDLogAsset AND l.logExternalID = ?1 GROUP BY a.iDAsset, l.logExternalID, a.assetReturn HAVING SUM(l.logQuantity) < 0", ASS_Q_IzposojenoSredstvo.class);
            //Query query = em.createQuery("SELECT new com.odelowebapp.student.evidenca.entity.ASS_Q_IzposojenoSredstvo(a.iDAsset, l.logExternalID, a.assetReturn, SUM(l.logQuantity)) FROM ASSAsset a, ASSLog l WHERE a.iDAsset = l.iDLogAsset AND l.logExternalID = ?1 GROUP BY a.iDAsset, l.logExternalID, a.assetReturn HAVING SUM(l.logQuantity) < 0");
            Query query = em.createNativeQuery("SELECT a.IDAsset AS IDAsset, l.LogExternalID AS LogExternalID, a.AssetReturn AS AssetReturn, ISNULL(a.AssetExpireDays, 0 ) AS AssetExpireDays, SUM(l.LogQuantity) as suma FROM [OWA].[dbo].[ASS_Asset] a, [OWA].[dbo].[ASS_Log] l\n"
                    + " where a.IDAsset = l.IDLogAsset\n"
                    + " and l.LogExternalID = ?1\n"
                    + " GROUP BY a.IDAsset, l.LogExternalID, a.AssetReturn, a.AssetExpireDays \n"
                    + " ", "IzposojenoResultNoTSConstructor");
            query.setParameter(1, userid);
            list = query.getResultList();

            //System.out.println("Facade findWhatIsIzposojenoNoTS vraca vrstic:" + list.size() +" vsebina:"+list.get(0).getAssetID());
            
//            results.stream().forEach((record) -> {
//                ASS_Q_IzposojenoSredstvo sredstvo = (ASS_Q_IzposojenoSredstvo) record[0];
//                // do something useful
//                System.out.println("SREDSTVO:" + sredstvo.getAssetID());
//            });

            
            return list;
            //System.out.println("vrnjna vrstica 1:" + list.get(0).getAssetID());
            //return list;
        } catch (Exception e) {
            System.out.println("NAPAKA findWhatIsIzposojenoNoTS:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
    
    public List<ASSLog> findIzdanoVKompletDescription(int idkompleta){
        //System.out.println("Sem v findIzdanoVKompletDescription()");
        String exp = "Izdano v komplet:"+idkompleta;
        em = getEntityManager();
        List<ASSLog> forreturn;
        try {
            Query query = em.createQuery("SELECT a FROM ASSLog a WHERE a.logComment = ?1 ", ASSLog.class);
            query.setParameter(1, exp);
            forreturn = query.getResultList();
            //System.out.println("Facade findIzdanoVKompletDescription vraca vrstic:" + forreturn.size());
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findIzdanoVKompletDescription:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    

}
