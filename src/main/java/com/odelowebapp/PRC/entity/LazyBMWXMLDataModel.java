/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.entity;

import com.odelowebapp.PRC.beans.BmwXMLObject;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

/**
 *
 * @author dematjasic
 */
public class LazyBMWXMLDataModel extends LazyDataModel<BmwXMLObject> {

    private HashMap<String, BmwXMLObject> datasource;

//    public LazyBMWXMLDataModel(HashMap<String, BmwXMLObject> datasource) {
//        this.datasource = datasource;
//    }

    public LazyBMWXMLDataModel(HashMap<String, BmwXMLObject> bmwxmldocuments) {
        this.datasource = bmwxmldocuments;
    }

    @Override
    public BmwXMLObject getRowData(String rowkey) {

        for (BmwXMLObject o : datasource.values()) {
            if (o.getFilename().equals(rowkey)) {
                return o;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(BmwXMLObject object) {
        return String.valueOf(object.getFilename());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.values().stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<BmwXMLObject> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // apply offset & filters
        List<BmwXMLObject> customers = datasource.values().stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

        // sort
        if (!sortBy.isEmpty()) {
            List<Comparator<BmwXMLObject>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<BmwXMLObject> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            customers.sort(cp);
        }

        return customers;
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
        //System.out.println("Sem v filter()");
        boolean matching = true;

        for (FilterMeta filter : filterBy) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();
            
            //System.out.println("Sem v filter() for loopu, filterValue:"+filterValue+" filterFiled:"+filter.getField()+" constraint:"+constraint.toString());

            try {
                Object columnValue = getPropertyValueViaReflection(o, filter.getField());
                //System.out.println("columnValue:"+columnValue.toString());
                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
            } catch (ReflectiveOperationException | IntrospectionException e) {
                matching = false;
                System.out.println("Napaka:"+e);
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }

    public static final Object getPropertyValueViaReflection(Object o, String field)
            throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
        return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
    }

}
