/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.sign.sessionbeans;

import com.odelowebapp.entity.SIGNTelevision;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author itstudent
 */
@Stateless
public class SIGNTelevisionFacade extends AbstractFacade<SIGNTelevision> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SIGNTelevisionFacade() {
        super(SIGNTelevision.class);
    }
    
}
