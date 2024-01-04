/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class HRCourseSelfRegistrationBean implements Serializable{
    
    //private String coid;
    //private String uid;
    private String attid;
    
    @EJB
    private HRCourseTypeFacade ctFacade;
    @EJB
    private HRCourseAttendanceFacade attFacade;
    
    private HRCourseAttendance find;
    
    private String wasAttendedAnswer;
    
    private boolean personMatch=false;
    
    @PostConstruct
    public void init() {
        System.out.println("---------Sem v HRCourseSelfRegistrationBean-----------");
        //coid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("coid");
        //uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
        attid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("attid");
        System.out.println("attid from parameter:"+attid);
        find = attFacade.find(Integer.parseInt(attid));
        System.out.println("Find coid:"+find.getCourseOfferingID().getCourseOfferingID());
        
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("Person selecting:" + currentUser.getSession().getAttribute("codeksid") + " must be:" + find.getCodeksID());
        if(currentUser.getSession().getAttribute("codeksid").equals(find.getCodeksID()+"")){
            personMatch=true;
            System.out.println("nastavljam personMatch=true");
        }else{
            personMatch=false;
            System.out.println("nastavljam personMatch=false");

        }
        
        
        System.out.println("Za izbrani course pridobim še priloge");
        find.getCourseOfferingID().getCourseID().findAttachmentsForCourse(find.getCourseOfferingID().getCourseID().getCourseID());
        find.getCourseOfferingID().findAttachmentsForCourse(find.getCourseOfferingID().getCourseOfferingID());
        
        System.out.println("CO file attachments:"+find.getCourseOfferingID().getFileattachments().size());
        System.out.println("COurse file attachments:"+find.getCourseOfferingID().getCourseID().getFileattachments().size());
        
        if(find.getWasAttended()){
            PrimeFaces.current().executeScript("$(\"#zeZabelezen\").modal();");
        }
    }
    
    public void confirmPresenceButtonAction(){
        System.out.println("Sem v confirmPresenceButtonAction()");
        System.out.println("Vpisan attendance ID na katerega se podpisujem: "+attid);
    }
    
    public void shraniKomentar(){
        System.out.println("Sem v shraniKomentar()");
        System.out.println("Sem v shraniKomentar(), komentar: "+find.getComment());
        
        if(wasAttendedAnswer.equals("0")){
            find.setWasAttended(false);
            find.setTimestamp(new Date());
            //dodaj pošiljanje maila na ksenija in anemari direkt
            
            PrimeFaces.current().executeScript("$(\"#vpisKomentarja\").hide();");
        }else if(wasAttendedAnswer.equals("1")){
            find.setWasAttended(true);
            find.setTimestamp(new Date());
            PrimeFaces.current().executeScript("$(\"#vpisKomentarja\").hide();");
        }else{
            
        }
//        find.setWasAttended(true);
//        find.setTimestamp(new Date());
        attFacade.edit(find);
    }

    public String getAttid() {
        return attid;
    }

    public void setAttid(String attid) {
        this.attid = attid;
    }

    public HRCourseAttendance getFind() {
        return find;
    }

    public void setFind(HRCourseAttendance find) {
        this.find = find;
    }

    public String getWasAttendedAnswer() {
        return wasAttendedAnswer;
    }

    public void setWasAttendedAnswer(String wasAttendedAnswer) {
        this.wasAttendedAnswer = wasAttendedAnswer;
    }

    public boolean isPersonMatch() {
        return personMatch;
    }

    public void setPersonMatch(boolean personMatch) {
        this.personMatch = personMatch;
    }
    
    
    
}
