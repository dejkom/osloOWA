/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Dean
 */
@Stateless
public class HRCourseAttendanceFacade extends AbstractFacade<HRCourseAttendance> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HRCourseAttendanceFacade() {
        super(HRCourseAttendance.class);
    }

    //lazy load test
   

//    public List<HRCourseAttendance> findWithFiltersAndSorting(int first, int pageSize, Map<String, Object> customFilters, String sortField, SortOrder sortOrder) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<HRCourseAttendance> cq = cb.createQuery(HRCourseAttendance.class);
//        Root<HRCourseAttendance> root = cq.from(HRCourseAttendance.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        for (Map.Entry<String, Object> entry : customFilters.entrySet()) {
//            String[] parts = entry.getKey().split("\\.");
//            if (parts.length == 2) {
//                // For nested properties: "courseOfferingID.courseDate"
//                Path<Object> path = root.get(parts[0]).get(parts[1]);
//                predicates.add(cb.equal(path, entry.getValue()));
//            } else {
//                Path<Object> path = root.get(entry.getKey());
//                if (entry.getValue() instanceof String) {
//                    // For string type, use 'like' predicate
//                    predicates.add(cb.like(path.as(String.class), "%" + entry.getValue() + "%"));
//                } else {
//                    // For other types, use 'equal' predicate
//                    predicates.add(cb.equal(path, entry.getValue()));
//                }
//            }
//        }
//
//        cq.where(predicates.toArray(new Predicate[0]));
//
//        // Handling Sorting
//        if (sortField != null && !sortField.isEmpty()) {
//            Path<Object> sortPath = root.get(sortField);
//            if (sortOrder == SortOrder.ASCENDING) {
//                cq.orderBy(cb.asc(sortPath));
//            } else if (sortOrder == SortOrder.DESCENDING) {
//                cq.orderBy(cb.desc(sortPath));
//            }
//        }
//
//        TypedQuery<HRCourseAttendance> query = em.createQuery(cq);
//        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
//    }
    public List<HRCourseAttendance> findWithFiltersAndSorting(int first, int pageSize, Map<String, Object> customFilters, String sortField, SortOrder sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HRCourseAttendance> cq = cb.createQuery(HRCourseAttendance.class);
        Root<HRCourseAttendance> root = cq.from(HRCourseAttendance.class);
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : customFilters.entrySet()) {
            String[] parts = entry.getKey().split("\\.");
            Path<Object> path;
            if (parts.length > 1) {
                // Handling nested properties
                Join<Object, Object> join = root.join(parts[0]);
                for (int i = 1; i < parts.length - 1; i++) {
                    join = join.join(parts[i]);
                }
                path = join.get(parts[parts.length - 1]);
            } else {
                path = root.get(entry.getKey());
            }

            if (entry.getValue() instanceof String) {
                predicates.add(cb.like(path.as(String.class), "%" + entry.getValue() + "%"));
            } else {
                predicates.add(cb.equal(path, entry.getValue()));
            }
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Handling Sorting
        Path<Object> sortPath;
        if (sortField != null && !sortField.isEmpty()) {
            if (sortField.contains(".")) {
                String[] sortParts = sortField.split("\\.");
                Join<Object, Object> sortJoin = root.join(sortParts[0]);
                for (int i = 1; i < sortParts.length - 1; i++) {
                    sortJoin = sortJoin.join(sortParts[i]);
                }
                sortPath = sortJoin.get(sortParts[sortParts.length - 1]);
            } else {
                sortPath = root.get(sortField);
            }

            if (sortOrder == SortOrder.ASCENDING) {
                cq.orderBy(cb.asc(sortPath));
            } else if (sortOrder == SortOrder.DESCENDING) {
                cq.orderBy(cb.desc(sortPath));
            }
        }

        TypedQuery<HRCourseAttendance> query = em.createQuery(cq);
        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int countWithFilters(Map<String, Object> customFilters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<HRCourseAttendance> root = cq.from(HRCourseAttendance.class);

        cq.select(cb.count(root));  // Count the results
        List<Predicate> predicates = new ArrayList<>();

        // Build predicates based on filters
        for (Map.Entry<String, Object> entry : customFilters.entrySet()) {
            String[] parts = entry.getKey().split("\\.");
            Path<Object> path;

            if (parts.length > 1) {
                // Handling nested properties
                Join<Object, Object> join = root.join(parts[0]);
                for (int i = 1; i < parts.length - 1; i++) {
                    join = join.join(parts[i]);
                }
                path = join.get(parts[parts.length - 1]);
            } else {
                path = root.get(entry.getKey());
            }

            if (entry.getValue() instanceof String) {
                predicates.add(cb.like(path.as(String.class), "%" + entry.getValue() + "%"));
            } else {
                predicates.add(cb.equal(path, entry.getValue()));
            }
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> query = em.createQuery(cq);
        return query.getSingleResult().intValue(); // Return the count
    }

    //end lazy load test
    public void doRefresh() {
        System.out.println("Sem v doRefresh HRCourseAttendanceFacade");
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    public List<HRCourseAttendance> findAllPersonsForCourse(HRCourseOffering courseoffering) {
        em = getEntityManager();
        List<HRCourseAttendance> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseAttendance p WHERE  p.courseOfferingID = :co");
            query.setParameter("co", courseoffering);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourseAttendance> findAllAttendancesForPerson(int codeksId) {
        em = getEntityManager();
        List<HRCourseAttendance> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseAttendance p WHERE  p.codeksID = :cid");
            query.setParameter("cid", codeksId);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAttendancesForPerson:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    //NOV QUERY - DODAN TIMESTAMP REŠEVANJA ANKETE
    public List<HRCourseAttendance> findAllAttendancesForPerson2(int codeksId) {
        em = getEntityManager();
        List<HRCourseAttendance> forreturn = new ArrayList();
        try {

            List<Object[]> results = this.em.createNativeQuery("SELECT distinct att.*, an.timestamp AS tsAnketa FROM HRCourseAttendance att\n"
                    + "LEFT OUTER JOIN HRCourseAnswer an\n"
                    + "ON att.codeksID = an.userID and att.courseOfferingID = an.courseID\n"
                    + "WHERE att.codeksID = ?", "AttendancesWithQuestionaireResult2").setParameter(1, codeksId).getResultList();

            results.stream().forEach((record) -> {
                HRCourseAttendance att = (HRCourseAttendance) record[0];
                Date tsAnketa = (Date) record[1];
                //System.out.println("ATT:"+att.getCourseOfferingID().getCourseOfferingID()+" tsAnketa:"+tsAnketa);
                att.setTsAnketa(tsAnketa);
                forreturn.add(att);
            });

            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAttendancesForPerson2:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public List<HRCourseAttendance> findAllAttendancesForOffering(int offeringId) {
        em = getEntityManager();
        List<HRCourseAttendance> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseAttendance p WHERE  p.courseOfferingID.courseOfferingID = :ofid");
            query.setParameter("ofid", offeringId);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAttendancesForOffering:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

    public int deletePersonFromCourse(HRCourseOffering courseoffering, VCodeksUsersAktualniZaposleni codeksid) {
        em = getEntityManager();
        int forreturn;
        try {
            Query query = em.createQuery("DELETE FROM HRCourseAttendance p WHERE  p.courseOfferingID = :co AND p.codeksID = :codeksid");
            query.setParameter("co", courseoffering);
            query.setParameter("codeksid", codeksid.getId());
            forreturn = query.executeUpdate();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA deletePersonFromCourse:" + e);
            return 0;
        } finally {
            //em.close();
        }
    }

    public List<Object[]> findAllAttendancesWithoutSolvedQuestionnaire(String cid) {
        System.out.println("Sem v findAllAttendancesWithoutSolvedQuestionnaire(" + cid + ")");
        em = getEntityManager();
        List<Object[]> forreturn;
        try {
            Query query = em.createNativeQuery("SELECT [attendanceID]\n"
                    + "      ,[codeksID]\n"
                    + "      ,[wasAttended]\n"
                    + "      ,t1.[courseOfferingID]\n"
                    + "	  ,t6.courseTypeID\n"
                    + "	  ,t7.courseTypeTitle\n"
                    + "	  ,t6.courseTitle\n"
                    + "      ,[timestamp]\n"
                    + "	  ,t3.Lastname\n"
                    + "	  ,t3.Firstname\n"
                    + "	  ,t3.ExternalId\n"
                    + "	  ,t4.mail\n"
                    + "  FROM [OWA].[dbo].[HRCourseAttendance] t1 \n"
                    + "  LEFT OUTER JOIN [codeks_production].[dbo].[Users] t3 ON t3.Id = t1.codeksID\n"
                    + "  LEFT OUTER JOIN [OWA].[dbo].[v_AD_users_direct] t4 ON t4.info collate SQL_Latin1_General_CP1_CI_AS = t3.ExternalId collate SQL_Latin1_General_CP1_CI_AS\n"
                    + "  LEFT OUTER JOIN [dbo].[HRCourseOffering] t5 ON t5.courseOfferingID = t1.courseOfferingID\n"
                    + "  LEFT OUTER JOIN [dbo].[HRCourse] t6 ON t6.courseID = t5.courseID\n"
                    + "  LEFT OUTER JOIN [dbo].[HRCourseType] t7 ON t6.courseTypeID = t7.courseTypeID\n"
                    + "  where wasAttended = 1 AND codeksID = ? AND NOT EXISTS (\n"
                    + "	  SELECT courseID, userID\n"
                    + "	  FROM [OWA].[dbo].[HRCourseAnswer] t2\n"
                    + "	  where t1.codeksID = t2.userID AND t1.courseOfferingID = t2.courseID\n"
                    + "  )");
            query.setParameter(1, cid);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAttendancesWithoutSolvedQuestionnaire:" + e);
            return null;
        } finally {
            //em.close();
        }

    }

    public List<Object[]> findAllAttendancesWithoutSolvedQuestionnaireEksterna() {
        System.out.println("Sem v findAllAttendancesWithoutSolvedQuestionnaireEksterna()");
        em = getEntityManager();
        List<Object[]> forreturn;
        try {
            Query query = em.createNativeQuery("SELECT [attendanceID]\n"
                    + "        ,[codeksID]\n"
                    + "        ,[wasAttended]\n"
                    + "        ,t1.[courseOfferingID]\n"
                    + "        ,t6.courseTypeID\n"
                    + "        ,t7.courseTypeTitle\n"
                    + "        ,t6.courseTitle\n"
                    + "        ,[timestamp]\n"
                    + "        ,t3.Lastname\n"
                    + "        ,t3.Firstname\n"
                    + "        ,t3.ExternalId\n"
                    + "        ,t4.mail\n"
                    + "    FROM [OWA].[dbo].[HRCourseAttendance] t1 \n"
                    + "    LEFT OUTER JOIN [codeks_production].[dbo].[Users] t3 ON t3.Id = t1.codeksID\n"
                    + "    LEFT OUTER JOIN [OWA].[dbo].[v_AD_users_direct] t4 ON t4.info collate SQL_Latin1_General_CP1_CI_AS = t3.ExternalId collate SQL_Latin1_General_CP1_CI_AS\n"
                    + "    LEFT OUTER JOIN [dbo].[HRCourseOffering] t5 ON t5.courseOfferingID = t1.courseOfferingID\n"
                    + "    LEFT OUTER JOIN [dbo].[HRCourse] t6 ON t6.courseID = t5.courseID\n"
                    + "    LEFT OUTER JOIN [dbo].[HRCourseType] t7 ON t6.courseTypeID = t7.courseTypeID\n"
                    + "    where wasAttended = 1 \n"
                    + "	--AND codeksID = 667\n"
                    + "	AND t4.mail is not null\n"
                    + "	AND t6.courseTypeID in (\n"
                    + "	 SELECT [courseTypeID]\n"
                    + "  FROM [OWA].[dbo].[HRCourseType] where parent in (\n"
                    + "  SELECT [courseTypeID]\n"
                    + "  FROM [OWA].[dbo].[HRCourseType] where parent in (\n"
                    + "  SELECT [courseTypeID]\n"
                    + "  FROM [OWA].[dbo].[HRCourseType] where courseTypeID = 2\n"
                    + "  )\n"
                    + "  )\n"
                    + "	)\n"
                    + "	AND NOT EXISTS (\n"
                    + "        SELECT courseID, userID\n"
                    + "        FROM [OWA].[dbo].[HRCourseAnswer] t2\n"
                    + "        where t1.codeksID = t2.userID AND t1.courseOfferingID = t2.courseID\n"
                    + "    )");
            //query.setParameter(1, cid);
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllAttendancesWithoutSolvedQuestionnaireEksterna:" + e);
            return null;
        } finally {
            //em.close();
        }

    }

    public List<HRCourseAttendance> findAllUnconfirmedExternalAttendances(List<HRCourseType> coursetypes) {
        em = getEntityManager();
        List<HRCourseAttendance> forreturn;
        try {
            Query query = em.createQuery("SELECT p FROM HRCourseAttendance p WHERE  p.courseOfferingID.courseID.courseTypeID in :coursetypes AND p.wasAttended = 0 AND p.courseOfferingID.courseDate < :now AND p.timestamp IS NULL");
            query.setParameter("coursetypes", coursetypes);
            query.setParameter("now", new Date());
            forreturn = query.getResultList();
            return forreturn;
        } catch (Exception e) {
            System.out.println("NAPAKA findAllUnconfirmedExternalAttendances:" + e);
            return null;
        } finally {
            //em.close();
        }
    }

}
