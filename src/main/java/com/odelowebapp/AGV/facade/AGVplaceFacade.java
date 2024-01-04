/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.facade;

import com.odelowebapp.AGV.entity.AGVplace;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dematjasic
 */
@Stateless
public class AGVplaceFacade extends AbstractFacade<AGVplace> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AGVplaceFacade() {
        super(AGVplace.class);
    }
    
    
    
}
