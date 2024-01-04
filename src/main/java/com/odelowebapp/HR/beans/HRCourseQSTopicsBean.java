/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.google.common.collect.Maps;
import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseFacade;
import com.odelowebapp.HR.facade.HRCourseOfferingFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class HRCourseQSTopicsBean implements Serializable{
    
    @EJB
    private HRCourseTypeFacade ctFacade;
    @EJB
    private HRCourseFacade cFacade;
    @EJB
    private HRCourseOfferingFacade coFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    
    //zgodovinski userji
    @EJB
    private VADCODEKSUsersFacade codeksusersFacade;
    private Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap = new HashMap();
    //
    
    private String testVar;
    
    List<HRCourseType> recursiveCourseHierarchy;
    List<HRCourseOffering> allCourseOfferingsWithQSTopic;
    private List<VCodeksUsersAktualniZaposleni> currentlyEmployed;
    
    private HashMap<Integer, VCodeksUsersAktualniZaposleni> selectedEmployeesHM = new HashMap<Integer, VCodeksUsersAktualniZaposleni>();
    
    @Inject
    HRCourseQuestionnaireMailSender ms;
    
    @PostConstruct
    public void init() {
        System.out.println("---------Sem v HRCourseQSTopicsBean-----------");
        
//        recursiveCourseHierarchy = ctFacade.getRecursiveCourseHierarchy(2);
//        
//        System.out.println("Vrnjenih otrok:"+recursiveCourseHierarchy.size());
//        
//        ms.sendOpomnikEksternoIzobrazevanjeSamoregistracija();
        
        allCourseOfferingsWithQSTopic = coFacade.findAllCourseOfferingsWithQSTopic();
        System.out.println("Vrnjenih qs topic course offeringov:"+allCourseOfferingsWithQSTopic.size());
        
        currentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();
        napolniCelotenHM();
        
        try{
        //naslednja metoda findUsersByCodeksIds ne deluje ob ogromnem številu parametrov v IN pogoju
        //vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findUsersByCodeksIds(allAttendancesForOffering.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList())), VADCODEKSUsers::getId);
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findAll(), VADCODEKSUsers::getId);
        
        }catch(Exception e){}
        
    }
    
    private void napolniCelotenHM() {
        System.out.println("Sem v napolniCelotenHM");
        for (VCodeksUsersAktualniZaposleni zap : currentlyEmployed) {
            selectedEmployeesHM.put(zap.getId(), zap);
        }
    }

    
    public void testMethod(){
        System.out.println("Sem v testMethod()");
    }

    public String getTestVar() {
        return testVar;
    }

    public void setTestVar(String testVar) {
        this.testVar = testVar;
    }

    public List<HRCourseType> getRecursiveCourseHierarchy() {
        return recursiveCourseHierarchy;
    }

    public void setRecursiveCourseHierarchy(List<HRCourseType> recursiveCourseHierarchy) {
        this.recursiveCourseHierarchy = recursiveCourseHierarchy;
    }

    public List<HRCourseOffering> getAllCourseOfferingsWithQSTopic() {
        return allCourseOfferingsWithQSTopic;
    }

    public void setAllCourseOfferingsWithQSTopic(List<HRCourseOffering> allCourseOfferingsWithQSTopic) {
        this.allCourseOfferingsWithQSTopic = allCourseOfferingsWithQSTopic;
    }

    public HashMap<Integer, VCodeksUsersAktualniZaposleni> getSelectedEmployeesHM() {
        return selectedEmployeesHM;
    }

    public void setSelectedEmployeesHM(HashMap<Integer, VCodeksUsersAktualniZaposleni> selectedEmployeesHM) {
        this.selectedEmployeesHM = selectedEmployeesHM;
    }

    public Map<Integer, VADCODEKSUsers> getVrnjeniUserjiMap() {
        return vrnjeniUserjiMap;
    }

    public void setVrnjeniUserjiMap(Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap) {
        this.vrnjeniUserjiMap = vrnjeniUserjiMap;
    }

    
    
    
    
}
