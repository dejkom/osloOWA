/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.entity;

import com.odelowebapp.PRC.beans.BmwXMLObject;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author dematjasic
 */
public class LazySorter implements Comparator<BmwXMLObject>{
    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(BmwXMLObject obj1, BmwXMLObject obj2) {
        try {
            Object value1 = getPropertyValueViaReflection(obj1, sortField);
            Object value2 = getPropertyValueViaReflection(obj2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static final Object getPropertyValueViaReflection(Object o, String field)
            throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
        return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
    }
    
}
