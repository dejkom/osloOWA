package com.odelowebapp.AGV.controller;

import com.odelowebapp.AGV.controller.util.JsfUtil;
import com.odelowebapp.AGV.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.AGV.entity.AGVcatalog;
import com.odelowebapp.AGV.facade.AGVcatalogFacade;

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

@Named("aGVcatalogController")
@ViewScoped
public class AGVcatalogController implements Serializable {

    @EJB
    private com.odelowebapp.AGV.facade.AGVcatalogFacade ejbFacade;
    private List<AGVcatalog> items = null;
    private AGVcatalog selected;

    public AGVcatalogController() {
    }

    public AGVcatalog getSelected() {
        return selected;
    }

    public void setSelected(AGVcatalog selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AGVcatalogFacade getFacade() {
        return ejbFacade;
    }

    public AGVcatalog prepareCreate() {
        selected = new AGVcatalog();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVcatalogCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVcatalogUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleAGV").getString("AGVcatalogDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AGVcatalog> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleAGV").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleAGV").getString("PersistenceErrorOccured"));
            }
        }
    }

    public AGVcatalog getAGVcatalog(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AGVcatalog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AGVcatalog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AGVcatalog.class)
    public static class AGVcatalogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AGVcatalogController controller = (AGVcatalogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aGVcatalogController");
            return controller.getAGVcatalog(getKey(value));
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
            if (object instanceof AGVcatalog) {
                AGVcatalog o = (AGVcatalog) object;
                return getStringKey(o.getCatalogId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AGVcatalog.class.getName()});
                return null;
            }
        }

    }

}
