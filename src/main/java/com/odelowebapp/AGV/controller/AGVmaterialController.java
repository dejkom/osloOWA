package com.odelowebapp.AGV.controller;

import com.odelowebapp.AGV.controller.util.JsfUtil;
import com.odelowebapp.AGV.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.AGV.entity.AGVmaterial;
import com.odelowebapp.AGV.facade.AGVmaterialFacade;

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

@Named("aGVmaterialController")
@ViewScoped
public class AGVmaterialController implements Serializable {

    @EJB
    private com.odelowebapp.AGV.facade.AGVmaterialFacade ejbFacade;
    private List<AGVmaterial> items = null;
    private AGVmaterial selected;

    public AGVmaterialController() {
    }

    public AGVmaterial getSelected() {
        return selected;
    }

    public void setSelected(AGVmaterial selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AGVmaterialFacade getFacade() {
        return ejbFacade;
    }

    public AGVmaterial prepareCreate() {
        selected = new AGVmaterial();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVmaterialCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVmaterialUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleAGV").getString("AGVmaterialDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AGVmaterial> getItems() {
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

    public AGVmaterial getAGVmaterial(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<AGVmaterial> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AGVmaterial> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AGVmaterial.class)
    public static class AGVmaterialControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AGVmaterialController controller = (AGVmaterialController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aGVmaterialController");
            return controller.getAGVmaterial(getKey(value));
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
            if (object instanceof AGVmaterial) {
                AGVmaterial o = (AGVmaterial) object;
                return getStringKey(o.getMaterialId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AGVmaterial.class.getName()});
                return null;
            }
        }

    }

}
