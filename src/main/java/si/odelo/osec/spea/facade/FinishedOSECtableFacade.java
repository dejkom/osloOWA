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
import si.odelo.osec.spea.entity.FinishedOSECtable;

/**
 *
 * @author dematjasic
 */
@Stateless
public class FinishedOSECtableFacade extends AbstractFacade<FinishedOSECtable> {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FinishedOSECtableFacade() {
        super(FinishedOSECtable.class);
    }
    
    public List<FinishedOSECtable> findAllTop(int results){
        em = getEntityManager();
        List<FinishedOSECtable> zavracanje = new ArrayList();
        try{
            Query query = em.createQuery("SELECT p FROM FinishedOSECtable p ORDER BY p.prenosVBaanTimestamp DESC ");
            query.setMaxResults(results);
            zavracanje = query.getResultList();
            return zavracanje;
        }catch(Exception e){
            System.out.println("Sem v FinishedOSECtableFacade - findAllTop() NAPAKA:"+e);
            return null;
        }finally{
            //em.close();
        }
        
    }
    
}
