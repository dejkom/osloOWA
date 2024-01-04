package com.odelowebapp.student.jan.controller;

import com.odelowebapp.student.jan.controller.util.JsfUtil;
import com.odelowebapp.student.jan.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.student.jan.entity.ENTRANSmeters;
import com.odelowebapp.student.jan.facade.ENTRANSmetersFacade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("eNTRANSmetersController")
@ViewScoped
public class ENTRANSmetersController implements Serializable {

    @EJB
    private com.odelowebapp.student.jan.facade.ENTRANSmetersFacade ejbFacade;
    private List<ENTRANSmeters> items = null;
    private ENTRANSmeters selected;

    public ENTRANSmetersController() {
    }

    public ENTRANSmeters getSelected() {
        return selected;
    }

    public void setSelected(ENTRANSmeters selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ENTRANSmetersFacade getFacade() {
        return ejbFacade;
    }

    public ENTRANSmeters prepareCreate() {
        selected = new ENTRANSmeters();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ENTRANSmetersCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ENTRANSmetersUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ENTRANSmetersDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ENTRANSmeters> getItems() {
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

    public ENTRANSmeters getENTRANSmeters(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ENTRANSmeters> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ENTRANSmeters> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ENTRANSmeters.class)
    public static class ENTRANSmetersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ENTRANSmetersController controller = (ENTRANSmetersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eNTRANSmetersController");
            return controller.getENTRANSmeters(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ENTRANSmeters) {
                ENTRANSmeters o = (ENTRANSmeters) object;
                return getStringKey(o.getMeterID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ENTRANSmeters.class.getName()});
                return null;
            }
        }

    }

}
