/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.facade;

import com.odelowebapp.PRC.entity.PRCContract;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dematjasic
 */
@Stateless
public class PRCContractFacade extends AbstractFacade<PRCContract> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PRCContractFacade() {
        super(PRCContract.class);
    }
    
}
