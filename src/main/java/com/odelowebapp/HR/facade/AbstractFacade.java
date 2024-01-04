/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.facade;

import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;

/**
 *
 * @author dematjasic
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    //dodano za lazy load
    public Long countEntities(Class<?> c, Map<String, FilterMeta> filterBy) {
        String sql = "select count(ent) from " + c.getName() + " ent where 1=1 ";
        sql = buildWhereParams(sql, filterBy);
        TypedQuery<Long> query = getEntityManager().createQuery(sql, Long.class);
        query = setWhereParams(query, filterBy);
        return query.getSingleResult();
    }

    public List<?> findEntites(Class<?> c, int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        List<?> result = new ArrayList<>();
        String sql = buildWhereParams("from " + c.getName() + " ent where 1=1 ", filterBy);
        sql = buildOrderBy(sql, sortBy);
        TypedQuery query = getEntityManager().createQuery(sql, c)
                .setFirstResult(first)
                .setMaxResults(pageSize);
        query = setWhereParams(query, filterBy);
        System.out.println("QUERY1:" + query.toString());
        result = query.getResultList();
        return result;
    }

    protected String buildWhereParams(String sql, Map<String, FilterMeta> filters) {
        if ((filters != null) && !filters.isEmpty()) {
            Iterator<String> fColumns = filters.keySet().iterator();
            String column;
            FilterMeta fMeta;
            MatchMode mMode;
            Object fValue;
            while (fColumns.hasNext()) {
                column = fColumns.next();
                fMeta = filters.get(column);
                mMode = fMeta.getMatchMode();

                // Replace dot with underscore in the parameter name
                System.out.println("--COLUMN:"+column);
                String paramName = column.replace(".", "_");
                if (column.equals("creatorImePriimek")) {
                    System.out.println("---Handle special case for concatenated fields---creatorImePriimek");
                    sql += " and CONCAT(ent.creatorObjekt.firstname, ' ', ent.creatorObjekt.lastname) " + mapMatchModeToJPAOperator(mMode) + ":" + paramName + " ";
                } else
                if (column.equals("instructorImePriimek")) {
                    // Handle special case for concatenated fields
                    //System.out.println("---Handle special case for concatenated fields---");
                    sql += " and CONCAT(ent.instructorID.firstname, ' ', ent.instructorID.lastname) " + mapMatchModeToJPAOperator(mMode) + ":" + paramName + " ";
                } else {
                    // Normal fields
                    sql += " and ent." + column + mapMatchModeToJPAOperator(mMode) + ":" + paramName + " ";
                }
                
                //sql += " and ent." + column + mapMatchModeToJPAOperator(mMode) + ":" + paramName + " ";
            }
        }
        System.out.println("---SQL before returning---:"+sql);
        return sql;
    }// of buildWhereParams()

    protected String mapMatchModeToJPAOperator(MatchMode mMode) {
        switch (mMode) {
            case CONTAINS:
                return " like ";
            case ENDS_WITH:
                return " like ";
            case STARTS_WITH:
                return " like ";
            case EQUALS:
                return " = "; // Checks if column value equals the filter value
            case EXACT:
                return " = "; // Checks if string representations of column value and filter value are same
            case GREATER_THAN:
                return " > ";
            case GREATER_THAN_EQUALS:
                return " >= ";
            case LESS_THAN:
                return " < ";
            case LESS_THAN_EQUALS:
                return " <= ";
        }
        return " = ";
    }

    protected TypedQuery setWhereParams(TypedQuery query, Map<String, FilterMeta> filters) {
        if ((filters != null) && !filters.isEmpty()) {
            Iterator<String> fColumns = filters.keySet().iterator();
            String column;
            FilterMeta fMeta;
            MatchMode mMode;
            Object fValue;
            while (fColumns.hasNext()) {
                column = fColumns.next();
                fMeta = filters.get(column);
                mMode = fMeta.getMatchMode();
                fValue = fMeta.getFilterValue();

                // Replace dot with underscore in the parameter name
                String paramName = column.replace(".", "_");
                
                if (column.equals("kreatorImePriimek")) {
                    System.out.println("---Handle special case for concatenated fields---setWhereParams---kreatorImePriimek");
                    
                }else{
                
                query.setParameter(paramName, buildParamValue(fValue, mMode));
                //query.setParameter(column, buildParamValue(fValue, mMode));
                }
            }
        }
        return query;
    }

    protected Object buildParamValue(Object fValue, MatchMode mMode) {
        if ((fValue != null) && (fValue instanceof String)) {
            fValue = addPrefixWildcardIfNeed(mMode) + fValue + addSuffixWildcardIfNeed(mMode);
        }
        return fValue;
    }

    private String addPrefixWildcardIfNeed(MatchMode mMode) {
        switch (mMode) {
            case CONTAINS:
                return "%";
            case STARTS_WITH:
                return "%";
        }
        return "";
    }

    private String addSuffixWildcardIfNeed(MatchMode mMode) {
        switch (mMode) {
            case CONTAINS:
                return "%";
            case ENDS_WITH:
                return "%";
            // Add other cases if there are more MatchMode values
        }
        return "";
    }

    protected String buildOrderBy(String sql, Map<String, SortMeta> sortBy) {
        if ((sortBy != null) && !sortBy.isEmpty()) {
            SortMeta sortM = sortBy.entrySet().stream().findFirst().get().getValue();
            String sortField = sortM.getField().trim();
            String sortOrder = sortM.getOrder().toString();
            sql += " order by ent." + sortField + " " + ((sortOrder.equals("ASCENDING") ? "ASC" : "DESC"));
        }
        return sql;
    }

    //
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
