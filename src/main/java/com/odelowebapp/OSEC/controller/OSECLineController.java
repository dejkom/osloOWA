package com.odelowebapp.OSEC.controller;

import com.odelowebapp.OSEC.entity.OSECLine;
import com.odelowebapp.OSEC.controller.util.JsfUtil;
import com.odelowebapp.OSEC.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.OSEC.facade.OSECLineFacade;

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

@Named("oSECLineController")
@ViewScoped
public class OSECLineController implements Serializable {

    @EJB
    private com.odelowebapp.OSEC.facade.OSECLineFacade ejbFacade;
    private List<OSECLine> items = null;
    private OSECLine selected;
    
    private int lineParam;
    
    

    public OSECLineController() {
    }

    public int getLineParam() {
        return lineParam;
    }

    public void setLineParam(int lineParam) {
        this.lineParam = lineParam;
    }
    
    

    public OSECLine getSelected() {
        return selected;
    }

    public void setSelected(OSECLine selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OSECLineFacade getFacade() {
        return ejbFacade;
    }

    public OSECLine prepareCreate() {
        selected = new OSECLine();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECLineCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECLineUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECLineDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OSECLine> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOSECZastoji").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOSECZastoji").getString("PersistenceErrorOccured"));
            }
        }
    }

    public OSECLine getOSECLine(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<OSECLine> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OSECLine> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OSECLine.class)
    public static class OSECLineControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OSECLineController controller = (OSECLineController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oSECLineController");
            return controller.getOSECLine(getKey(value));
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
            if (object instanceof OSECLine) {
                OSECLine o = (OSECLine) object;
                return getStringKey(o.getLineID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OSECLine.class.getName()});
                return null;
            }
        }

    }

}
