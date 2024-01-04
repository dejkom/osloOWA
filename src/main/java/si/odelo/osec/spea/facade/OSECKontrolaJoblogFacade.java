/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.odelo.osec.spea.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import si.odelo.osec.spea.entity.OSECKontrolaJoblog;

/**
 *
 * @author dematjasic
 */
@Stateless
public class OSECKontrolaJoblogFacade extends AbstractFacade<OSECKontrolaJoblog> {
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECKontrolaJoblogFacade() {
        super(OSECKontrolaJoblog.class);
    }
    
}
