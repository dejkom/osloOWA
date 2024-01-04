/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.sign.sessionbeans;

import com.odelowebapp.entity.SIGNAlert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author itstudent
 */
@Stateless
public class SIGNAlertFacade extends AbstractFacade<SIGNAlert> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SIGNAlertFacade() {
        super(SIGNAlert.class);
    }
    
}
