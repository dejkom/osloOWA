/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseAnswer;
import com.odelowebapp.HR.entity.HRCourseQuestionnaireStatus;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dematjasic
 */
@Stateless
public class HRCourseAnswerFacade extends AbstractFacade<HRCourseAnswer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseAnswerFacade() {
        super(HRCourseAnswer.class);
    }

    public void doRefresh() {
        System.out.println("Sem v doRefresh HRCourseAnswerFacade");
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    public List<HRCourseAnswer> findAllAnswersByPersonAndCourse(String codeksId, int courseID, int questionnaireID) {
        em = getEntityManager();
        List<HRCourseAnswer> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseAnswer p WHERE p.hRCourseAnswerPK.userID = :cid AND p.hRCourseAnswerPK.courseID = :courseid AND p.hRCourseAnswerPK.questionnaireID = :quid ORDER BY p.hRCourseAnswerPK.questionNbr ASC");
            query.setParameter("cid", codeksId);
            query.setParameter("courseid", courseID);
            query.setParameter("quid", questionnaireID);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAnswersByPersonAndCourse:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourseQuestionnaireStatus> findStatusOfQuestionnaires(List<Integer> idpodrejenih) {
        em = getEntityManager();
        List<HRCourseQuestionnaireStatus> forreturn;
        
        String valuesString = idpodrejenih.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        
        System.out.println("Za v IN pogoj: "+valuesString);
        
        try {
            Query query = em.createNativeQuery("SELECT\n"
                    + "  CA.codeksID,\n"
                    + "  CA.attendanceID,\n"
                    + "  CASE WHEN EXISTS (SELECT 1 FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 1 AND CAQ.courseID = CA.courseOfferingID) THEN 'Solved' ELSE 'Not Solved' END AS questionnaire1Status,\n"
                    + "  c.educationAssesmentRequired AS questionnaire1Required,\n"
                    + "  (SELECT MAX(CAQ.timestamp) FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 1 AND CAQ.courseID = CA.courseOfferingID) AS questionnaire1TS,\n"
                    + "  CASE WHEN EXISTS (SELECT 1 FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 2 AND CAQ.courseID = CA.courseOfferingID) THEN 'Solved' ELSE 'Not Solved' END AS questionnaire2Status,\n"
                    + "  (SELECT MAX(CAQ.timestamp) FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 2 AND CAQ.courseID = CA.courseOfferingID) AS questionnaire2TS,\n"
                    + "  c.performanceEvaluationRequired AS questionnaire2Required,\n"
                    + "  DATEADD(MONTH, 6,(co.courseDate)) AS questionnaire2FromTS,\n"
                    + "  c.courseTitle,\n"
                    + "  CA.courseOfferingID\n"
                    + "FROM\n"
                    + "  HRCourseAttendance CA\n"
                    + "LEFT JOIN\n"
                    + "  HRCourseAnswer CAQ ON CA.codeksID = CAQ.userID\n"
                    + "LEFT JOIN HRCourseOffering co ON co.courseOfferingID = CA.courseOfferingID \n"
                    + "LEFT JOIN HRCourse c  ON c.courseID = co.courseID\n"
                    + "  where c.performanceEvaluationRequired = 1\n"
                    + "  and CA.codeksID IN (" +valuesString+ ") \n"
                    + "GROUP BY\n"
                    + "  CA.codeksID,\n"
                    + "  CA.attendanceID,\n"
                    + "  CA.courseOfferingID,\n"
                    + "  c.performanceEvaluationRequired,\n"
                    + "  c.educationAssesmentRequired,\n"
                    + "  c.courseTitle,\n"
                    + "  co.courseDate;","questionnaireStatusMapping3");

            query.setParameter(1, valuesString);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findStatusOfQuestionnaires:" + e);
            return null;
        } finally {
            //em.close();
        }
    }
    
        public List<Object[]> findStatusOfQuestionnaires2(List<Integer> idpodrejenih) {
        em = getEntityManager();
        List<Object[]> forreturn;
        
        String valuesString = idpodrejenih.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        
        System.out.println("Za v IN pogoj: "+valuesString);
        
        try {
            Query query = em.createNativeQuery("SELECT\n"
                    + "  CA.codeksID,\n"
                    + "  CA.attendanceID,\n"
                    + "  CASE WHEN EXISTS (SELECT 1 FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 1 AND CAQ.courseID = CA.courseOfferingID) THEN 'Solved' ELSE 'Not Solved' END AS questionnaire1Status,\n"
                    + "  c.educationAssesmentRequired AS questionnaire1Required,\n"
                    + "  (SELECT MAX(CAQ.timestamp) FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 1 AND CAQ.courseID = CA.courseOfferingID) AS questionnaire1TS,\n"
                    + "  CASE WHEN EXISTS (SELECT 1 FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 2 AND CAQ.courseID = CA.courseOfferingID) THEN 'Solved' ELSE 'Not Solved' END AS questionnaire2Status,\n"
                    + "  (SELECT MAX(CAQ.timestamp) FROM HRCourseAnswer CAQ WHERE CAQ.userID = CA.codeksID AND CAQ.QuestionnaireID = 2 AND CAQ.courseID = CA.courseOfferingID) AS questionnaire2TS,\n"
                    + "  c.performanceEvaluationRequired AS questionnaire2Required,\n"
                    + "  DATEADD(MONTH, 6,(co.courseDate)) AS questionnaire2FromTS,\n"
                    + "  c.courseTitle,\n"
                    + "  CA.courseOfferingID, CA.codeksID AS zaposleni\n"
                    + "FROM\n"
                    + "  HRCourseAttendance CA\n"
                    + "LEFT JOIN\n"
                    + "  HRCourseAnswer CAQ ON CA.codeksID = CAQ.userID\n"
                    + "LEFT JOIN HRCourseOffering co ON co.courseOfferingID = CA.courseOfferingID \n"
                    + "LEFT JOIN HRCourse c  ON c.courseID = co.courseID\n"
                    + "  where c.performanceEvaluationRequired = 1\n"
                    + "  and CA.codeksID IN (" +valuesString+ ") \n"
                    + "GROUP BY\n"
                    + "  CA.codeksID,\n"
                    + "  CA.attendanceID,\n"
                    + "  CA.courseOfferingID,\n"
                    + "  c.performanceEvaluationRequired,\n"
                    + "  c.educationAssesmentRequired,\n"
                    + "  c.courseTitle,\n"
                    + "  co.courseDate;","questionnaireStatusMapping3");

            //query.setParameter(1, valuesString);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findStatusOfQuestionnaires2:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

}
