package com.odelowebapp.common.controller;

import com.odelowebapp.common.entity.OWANotification;
import com.odelowebapp.common.controller.util.JsfUtil;
import com.odelowebapp.common.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.common.facade.OWANotificationFacade;

import java.io.Serializable;
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

@Named("oWANotificationController")
@ViewScoped
public class OWANotificationController implements Serializable {

    @EJB
    private com.odelowebapp.common.facade.OWANotificationFacade ejbFacade;
    private List<OWANotification> items = null;
    private OWANotification selected;

    public OWANotificationController() {
    }

    public OWANotification getSelected() {
        return selected;
    }

    public void setSelected(OWANotification selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OWANotificationFacade getFacade() {
        return ejbFacade;
    }

    public OWANotification prepareCreate() {
        selected = new OWANotification();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleNotifications").getString("OWANotificationCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleNotifications").getString("OWANotificationUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleNotifications").getString("OWANotificationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OWANotification> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<OWANotification> getItemsByArea(String area) {
        if (items == null) {
            items = getFacade().findAllByArea(area);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotifications").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleNotifications").getString("PersistenceErrorOccured"));
            }
        }
    }

    public OWANotification getOWANotification(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<OWANotification> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OWANotification> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OWANotification.class)
    public static class OWANotificationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OWANotificationController controller = (OWANotificationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oWANotificationController");
            return controller.getOWANotification(getKey(value));
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
            if (object instanceof OWANotification) {
                OWANotification o = (OWANotification) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OWANotification.class.getName()});
                return null;
            }
        }

    }

}
