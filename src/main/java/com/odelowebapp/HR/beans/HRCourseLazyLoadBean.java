/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.facade.HRCourseFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class HRCourseLazyLoadBean implements Serializable {

    @EJB
    private HRCourseFacade courseDAO;
    private LazyDataModel<HRCourse> lazyModel;
    
    @PostConstruct
    public void init() {
        
        lazyModel = new LazyDataModel<HRCourse>() {
            
            @Override
            public String getRowKey(HRCourse object) {
                return object.getCourseID() + "";
                //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                Long count = courseDAO.countEntities(HRCourse.class, filterBy);
                return count.intValue();
            }

            @Override
            public List<HRCourse> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                System.out.println("----- SEM V LOAD MADAFAKA ---");
                 return (List<HRCourse>) courseDAO.findEntites(HRCourse.class ,first, pageSize, sortBy, filterBy);
            }
            
            
        };
        
    }

    public LazyDataModel<HRCourse> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<HRCourse> lazyModel) {
        this.lazyModel = lazyModel;
    }
    
    

}
