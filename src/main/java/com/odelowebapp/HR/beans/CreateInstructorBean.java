/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseInstructor;
import com.odelowebapp.HR.facade.HRCourseFacade;
import com.odelowebapp.HR.facade.HRCourseHRCourseInstructorFacade;
import com.odelowebapp.HR.facade.HRCourseInstructorFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class CreateInstructorBean implements Serializable{
    
    @EJB
    private HRCourseInstructorFacade instructorFacade;
    @EJB
    private HRCourseFacade courseFacade;
    @EJB
    private HRCourseHRCourseInstructorFacade joinFacade;
    
    private HRCourseInstructor selectedInstructor;
    
    private List<HRCourseInstructor> instruktorjiNaVoljo = new ArrayList();
    
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v CreateInstructorBean Beanu");
    }
    
    public void buttonAction(){
        System.out.println("Sem v buttonAction");
    }
    
    public void codekspersonSelected(){
        System.out.println("Sem v codekspersonSelected!");
        
    
    }

    public HRCourseInstructor getSelectedInstructor() {
        return selectedInstructor;
    }

    public void setSelectedInstructor(HRCourseInstructor selectedInstructor) {
        this.selectedInstructor = selectedInstructor;
    }

    public List<HRCourseInstructor> getInstruktorjiNaVoljo() {
        return instruktorjiNaVoljo;
    }

    public void setInstruktorjiNaVoljo(List<HRCourseInstructor> instruktorjiNaVoljo) {
        this.instruktorjiNaVoljo = instruktorjiNaVoljo;
    }
    
    
    
}
