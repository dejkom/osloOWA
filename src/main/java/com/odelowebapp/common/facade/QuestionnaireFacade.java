/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.facade;

import com.odelowebapp.common.entity.Questionnaire;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dematjasic
 */
@Stateless
public class QuestionnaireFacade extends AbstractFacade<Questionnaire> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionnaireFacade() {
        super(Questionnaire.class);
    }
    
}
