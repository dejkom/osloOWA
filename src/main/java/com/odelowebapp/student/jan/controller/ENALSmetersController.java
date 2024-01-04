package com.odelowebapp.student.jan.controller;

import com.odelowebapp.student.jan.entity.ENALSmeters;
import com.odelowebapp.student.jan.controller.util.JsfUtil;
import com.odelowebapp.student.jan.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.student.jan.facade.ENALSmetersFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("eNALSmetersController")
@ViewScoped
public class ENALSmetersController implements Serializable {

    @EJB
    private com.odelowebapp.student.jan.facade.ENALSmetersFacade ejbFacade;
    private List<ENALSmeters> items = null;
    private ENALSmeters selected;

    public ENALSmetersController() {
    }

    public ENALSmeters getSelected() {
        return selected;
    }

    public void setSelected(ENALSmeters selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ENALSmetersFacade getFacade() {
        return ejbFacade;
    }

    public ENALSmeters prepareCreate() {
        selected = new ENALSmeters();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ENALSmetersCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ENALSmetersUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ENALSmetersDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ENALSmeters> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ENALSmeters getENALSmeters(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ENALSmeters> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ENALSmeters> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ENALSmeters.class)
    public static class ENALSmetersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ENALSmetersController controller = (ENALSmetersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eNALSmetersController");
            return controller.getENALSmeters(getKey(value));
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
            if (object instanceof ENALSmeters) {
                ENALSmeters o = (ENALSmeters) object;
                return getStringKey(o.getMeterID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ENALSmeters.class.getName()});
                return null;
            }
        }

    }

}
