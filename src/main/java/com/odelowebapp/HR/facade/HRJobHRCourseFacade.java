/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRJobHRCourse;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Dean
 */
@Stateless
public class HRJobHRCourseFacade extends AbstractFacade<HRJobHRCourse> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRJobHRCourseFacade() {
        super(HRJobHRCourse.class);
    }
    
}
