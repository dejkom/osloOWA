/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.facade;

import com.odelowebapp.OSEC.entity.OSECLine;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dematjasic
 */
@Stateless
public class OSECLineFacade extends AbstractFacade<OSECLine> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OSECLineFacade() {
        super(OSECLine.class);
    }
    
}
