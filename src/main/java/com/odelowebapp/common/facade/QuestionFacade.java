/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.facade;

import com.odelowebapp.common.entity.Question;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dematjasic
 */
@Stateless
public class QuestionFacade extends AbstractFacade<Question> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionFacade() {
        super(Question.class);
    }
    
    public List<Question> findAllQuestionsByQuestionaireID(int questionnaireID) {
        em = getEntityManager();
        List<Question> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM Question p where p.questionnaireID.questionnaireID = :quid ORDER BY p.sortColumn ASC");
            query.setParameter("quid", questionnaireID);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllQuestionsByQuestionaireID:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
}
