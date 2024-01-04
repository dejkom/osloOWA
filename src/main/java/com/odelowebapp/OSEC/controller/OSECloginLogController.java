package com.odelowebapp.OSEC.controller;

import com.odelowebapp.OSEC.entity.OSECloginLog;
import com.odelowebapp.OSEC.controller.util.JsfUtil;
import com.odelowebapp.OSEC.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.OSEC.facade.OSECloginLogFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

@Named("oSECloginLogController")
@ViewScoped
public class OSECloginLogController implements Serializable {

    @EJB
    private com.odelowebapp.OSEC.facade.OSECloginLogFacade ejbFacade;
    private List<OSECloginLog> items = null;
    private OSECloginLog selected;

    public OSECloginLogController() {
    }
    
    public void selectLogAndDelete(int logid){
        System.out.println("Sem v selectLogAndDelete() brišem log id:"+logid);
        selected = getOSECloginLog(logid);
        selected.setLoginEnd(new Date());
        update();
        //PrimeFaces.current().ajax().update(":formLoggedin:operaterjiuir");
    }

    public OSECloginLog getSelected() {
        return selected;
    }

    public void setSelected(OSECloginLog selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OSECloginLogFacade getFacade() {
        return ejbFacade;
    }

    public OSECloginLog prepareCreate() {
        selected = new OSECloginLog();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOsecOperaterjiPrijava").getString("OSECloginLogCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOsecOperaterjiPrijava").getString("OSECloginLogUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOsecOperaterjiPrijava").getString("OSECloginLogDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OSECloginLog> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<OSECloginLog> getLoggedinUsers(int lineid){
        System.out.println("SEM V getLoggedinUsers() iščem za lineid:"+lineid);
        items=null;
        if (items == null) {
            items = getFacade().findAllLoggedInOnLineIgnoreShiftAndDate(lineid);
            System.out.println("SEM V getLoggedinUsers() iščem za lineid:"+lineid+" FOUND USERS:"+items.size());
        }
        return items;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOsecOperaterjiPrijava").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOsecOperaterjiPrijava").getString("PersistenceErrorOccured"));
            }
        }
    }

    public OSECloginLog getOSECloginLog(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<OSECloginLog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OSECloginLog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OSECloginLog.class)
    public static class OSECloginLogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OSECloginLogController controller = (OSECloginLogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oSECloginLogController");
            return controller.getOSECloginLog(getKey(value));
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
            if (object instanceof OSECloginLog) {
                OSECloginLog o = (OSECloginLog) object;
                return getStringKey(o.getLoginId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OSECloginLog.class.getName()});
                return null;
            }
        }

    }

}
