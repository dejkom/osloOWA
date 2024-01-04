/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.odelo.osec.spea.facade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import si.odelo.osec.spea.entity.TempOSECtable;

/**
 *
 * @author dematjasic
 */
@Stateless
public class TempOSECtableFacade extends AbstractFacade<TempOSECtable> {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TempOSECtableFacade() {
        super(TempOSECtable.class);
    }
    
    
    public List<TempOSECtable> getPreviousStatuses(String code){
        em = getEntityManager();
        List<TempOSECtable> zavracanje = new ArrayList();
        try{
            //DODAL SEM DISTINCT
            Query query = em.createQuery("SELECT DISTINCT p FROM TempOSECtable p WHERE p.unitSN = :CODE ORDER BY p.idpk.testDatetime ASC");
            query.setParameter("CODE", code);
            zavracanje = query.getResultList();
            System.out.println("getPreviousStatuses() v FACADE  vraca vrstic:"+zavracanje.size());
            return zavracanje;
        }catch(Exception e){
            System.out.println("Sem v TempOSECtableFacade - getPreviousStatuses, NAPAKA:"+e);
            return null;
        }finally{
            //em.close();
        }
        
    }
    
    public List<TempOSECtable> findAllNotArchived(){
        em = getEntityManager();
        List<TempOSECtable> zavracanje = new ArrayList();
        try{
            //DODAL SEM DISTINCT
            Query query = em.createQuery("SELECT DISTINCT p FROM TempOSECtable p WHERE p.arhivirano = 0 OR p.arhivirano IS NULL");
            zavracanje = query.getResultList();
            return zavracanje;
        }catch(Exception e){
            System.out.println("Sem v TempOSECtableFacade - findAllNotArchived, NAPAKA:"+e);
            return null;
        }finally{          
            //em.flush();
        }
        
    }
    
    
    
    
    
}
