/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PM.facade;

import com.odelowebapp.PM.entity.CONPSReport;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dematjasic
 */
@Stateless
public class CONPSReportFacade extends AbstractFacade<CONPSReport> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CONPSReportFacade() {
        super(CONPSReport.class);
    }
    
}
