package com.odelowebapp.HR.controller;


import com.google.common.collect.Maps;
import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named("hRCourseAttendanceController")
@ViewScoped
public class HRCourseAttendanceController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseAttendanceFacade ejbFacade;
    @EJB
    private VADCODEKSUsersFacade codeksusersFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    
    private List<HRCourseAttendance> items = null;
    private HRCourseAttendance selected;
    
    private List<VCodeksUsersAktualniZaposleni> currentlyEmployed;

    public HRCourseAttendanceController() {
    }

    public HRCourseAttendance getSelected() {
        return selected;
    }

    public void setSelected(HRCourseAttendance selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseAttendanceFacade getFacade() {
        return ejbFacade;
    }

    public HRCourseAttendance prepareCreate() {
        selected = new HRCourseAttendance();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseAttendanceCreated"));
        ejbFacade.doRefresh();
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseAttendanceUpdated"));
        ejbFacade.doRefresh();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseAttendanceDeleted"));
        ejbFacade.doRefresh();
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseAttendance> getItems() {
        if (items == null) {
            items = getFacade().findAll();
            //pridobiOsebe();
            pridobiOsebeVse();
        }  
        return items;
    }
        
    public void pridobiOsebe(){
        //System.out.println("Sem v pridobiOsebe()");
        //vrnjeniUserji = codeksusersFacade.findUsersByCodeksIds(allAttendancesForOffering.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList()));
        //System.out.println("Vrnjenih po novem: "+vrnjeniUserji.size());
        //TA METODA NE DELA ČE JE VELIKO ŠTEVILO OSEB V IN POGOJU!!!
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findUsersByCodeksIds(items.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList())), VADCODEKSUsers::getId);
        
        
    }
    
    private Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap = new HashMap();
    public void pridobiOsebeVse(){
        System.out.println("Sem v pridobiOsebeVse()");        
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findAll(), VADCODEKSUsers::getId);
           
    }

    public Map<Integer, VADCODEKSUsers> getVrnjeniUserjiMap() {
        return vrnjeniUserjiMap;
    }

    public void setVrnjeniUserjiMap(Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap) {
        this.vrnjeniUserjiMap = vrnjeniUserjiMap;
    }
    
    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
            }
        }
    }

    public HRCourseAttendance getHRCourseAttendance(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourseAttendance> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseAttendance> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<VCodeksUsersAktualniZaposleni> getCurrentlyEmployed() {
        return usersEJB.zaposleniZaAndroidAplikacijo();
        //return currentlyEmployed;
    }

    public void setCurrentlyEmployed(List<VCodeksUsersAktualniZaposleni> currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }
    
    

    @FacesConverter(forClass = HRCourseAttendance.class)
    public static class HRCourseAttendanceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseAttendanceController controller = (HRCourseAttendanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseAttendanceController");
            return controller.getHRCourseAttendance(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof HRCourseAttendance) {
                HRCourseAttendance o = (HRCourseAttendance) object;
                return getStringKey(o.getAttendanceID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseAttendance.class.getName()});
                return null;
            }
        }

    }

}
