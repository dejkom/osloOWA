/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class IndexPageBean implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseOfferingFacade courseOfferingFacade;
    
    List<HRCourseOffering> nextCourseOfferings;
    List<HRCourseOffering> nextUsersCourseOfferings;
    
    List<HRCourseOffering> nextCourseOfferingsAllowingSelfRegistration;
    
    private HRCourseOffering selectedForRegistration;
    
    private Subject currentUser;

    @PostConstruct
    public void init() {
        System.out.println("Sem v IndexPageBean init()");
        nextCourseOfferings = courseOfferingFacade.findNextCourseOfferings(10);
        try {
            currentUser = SecurityUtils.getSubject();
            String userid = currentUser.getSession().getAttribute("codeksid") + "";
            nextUsersCourseOfferings = courseOfferingFacade.findNextUsersCourseOfferings(10, Integer.parseInt(userid));
        } catch (Exception e) {
            nextUsersCourseOfferings = new ArrayList();
        }
        nextCourseOfferingsAllowingSelfRegistration = courseOfferingFacade.findNextCourseOfferingsAllowingSelfRegistration(10);
        System.out.println("Courses allowing self registration:"+nextCourseOfferingsAllowingSelfRegistration.size());

    }
    
    public void userIsRegisteringToCourse(){
        System.out.println("Sem v userIsRegisteringToCourse, course:"+selectedForRegistration.getCourseID().getCourseTitle());
        HRCourseAttendance att = new HRCourseAttendance();
        att.setCodeksID(Integer.parseInt(currentUser.getSession().getAttribute("codeksid")+""));
        att.setComment("Samoprijava");
        att.setCourseOfferingID(selectedForRegistration);
        att.setWasAttended(false);
        selectedForRegistration.getHRCourseAttendanceList().add(att);
        courseOfferingFacade.edit(selectedForRegistration);
        
    }

    public List<HRCourseOffering> getNextCourseOfferingsAllowingSelfRegistration() {
        return nextCourseOfferingsAllowingSelfRegistration;
    }

    public void setNextCourseOfferingsAllowingSelfRegistration(List<HRCourseOffering> nextCourseOfferingsAllowingSelfRegistration) {
        this.nextCourseOfferingsAllowingSelfRegistration = nextCourseOfferingsAllowingSelfRegistration;
    }

    public HRCourseOffering getSelectedForRegistration() {
        return selectedForRegistration;
    }

    public void setSelectedForRegistration(HRCourseOffering selectedForRegistration) {
        this.selectedForRegistration = selectedForRegistration;
    }
    
    

    public List<HRCourseOffering> getNextCourseOfferings() {
        return nextCourseOfferings;
    }

    public void setNextCourseOfferings(List<HRCourseOffering> nextCourseOfferings) {
        this.nextCourseOfferings = nextCourseOfferings;
    }

    public List<HRCourseOffering> getNextUsersCourseOfferings() {
        return nextUsersCourseOfferings;
    }

    public void setNextUsersCourseOfferings(List<HRCourseOffering> nextUsersCourseOfferings) {
        this.nextUsersCourseOfferings = nextUsersCourseOfferings;
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        UIComponent component = event.getComponent();
        String clientId = event.getTab().getClientId();
        int index = -1;
        for (int i = 0; i < component.getChildCount(); i++) {
            UIComponent child = component.getChildren().get(i);
            // Not each child is a tab per se
            if (child instanceof Tab) {
                index++;
                if (clientId.equals(child.getClientId())) {
                    // Found it, break out of the for loop
                    break;
                }
            }
        }
        System.out.println("index " + index);
    }
    
    public void onTabChange2(final TabChangeEvent event) {
      // get the tab view
      final TabView tabView = (TabView) event.getTab().getParent();

      final String tabName = event.getTab().getClientId();

      int i = 0;
      for (final UIComponent item : tabView.getChildren()) {
         if (item.getClientId().equalsIgnoreCase(tabName)) {
            index = i+"";
            return;
         } else {
            i++;
         }
      }
      System.out.println("Selected tab "+event.getTab().getClientId()+" could not be located!");
   }

    private String index;
    
    public String getIndex() {
        System.out.println("GET: " + index);
        return index;
    }

    public void setIndex(String index) {
        System.out.println("SET: " + index);
        this.index = index;
    }

    private String index2;
    
    public String getIndex2() {
        System.out.println("GET: " + index2);
        return index2;
    }

    public void setIndex2(String index) {
        System.out.println("SET: " + index);
        this.index2 = index;
    }
    
    
    
}
