/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.google.common.collect.Maps;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRCourseOfferingFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class printAttendanceListBean implements Serializable{
    
    @EJB
    private HRCourseAttendanceFacade attendanceFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private HRCourseOfferingFacade offeringFacade;
    @EJB
    private VADCODEKSUsersFacade codeksusersFacade;
    
    private String cofid;
    List<HRCourseAttendance> allAttendancesForOffering;
    VCodeksUsersAktualniZaposleni userByCodeksId;
    
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");  
    private String nowFormatted;
    private HRCourseOffering offering;
        
    private List<VADCODEKSUsers> vrnjeniUserji = new ArrayList();
    private Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap = new HashMap();
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v printAttendanceListBean");
        
        cofid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cofid");
        
        System.out.println("COFID:"+cofid);
        
        allAttendancesForOffering = attendanceFacade.findAllAttendancesForOffering(Integer.parseInt(cofid));
//        vrnjeniUserji = codeksusersFacade.findUsersByCodeksIds(allAttendancesForOffering.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList()));
//        System.out.println("Vrnjenih po novem: "+vrnjeniUserji.size());
        try{
        //naslednja metoda findUsersByCodeksIds ne deluje ob ogromnem številu parametrov v IN pogoju
        //vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findUsersByCodeksIds(allAttendancesForOffering.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList())), VADCODEKSUsers::getId);
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findAll(), VADCODEKSUsers::getId);
        
        }catch(Exception e){}
        System.out.println("attendances:"+allAttendancesForOffering.size());
        
        offering = offeringFacade.find(Integer.parseInt(cofid));
        nowFormatted = dateFormat.format(new Date());
        

    }

    public List<HRCourseAttendance> getAllAttendancesForOffering() {
        return allAttendancesForOffering;
    }

    public void setAllAttendancesForOffering(List<HRCourseAttendance> allAttendancesForOffering) {
        this.allAttendancesForOffering = allAttendancesForOffering;
    }

    public String getNowFormatted() {
        return nowFormatted;
    }

    public void setNowFormatted(String nowFormatted) {
        this.nowFormatted = nowFormatted;
    }

    public HRCourseOffering getOffering() {
        return offering;
    }

    public void setOffering(HRCourseOffering offering) {
        this.offering = offering;
    }

    public List<VADCODEKSUsers> getVrnjeniUserji() {
        return vrnjeniUserji;
    }

    public void setVrnjeniUserji(List<VADCODEKSUsers> vrnjeniUserji) {
        this.vrnjeniUserji = vrnjeniUserji;
    }

    public Map<Integer, VADCODEKSUsers> getVrnjeniUserjiMap() {
        return vrnjeniUserjiMap;
    }

    public void setVrnjeniUserjiMap(Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap) {
        this.vrnjeniUserjiMap = vrnjeniUserjiMap;
    }

    
    
    
}
