package com.odelowebapp.AGV.controller;

import com.odelowebapp.AGV.controller.util.JsfUtil;
import com.odelowebapp.AGV.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.AGV.entity.AGVorderType;
import com.odelowebapp.AGV.facade.AGVorderTypeFacade;

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

@Named("aGVorderTypeController")
@ViewScoped
public class AGVorderTypeController implements Serializable {

    @EJB
    private com.odelowebapp.AGV.facade.AGVorderTypeFacade ejbFacade;
    private List<AGVorderType> items = null;
    private AGVorderType selected;

    public AGVorderTypeController() {
    }

    public AGVorderType getSelected() {
        return selected;
    }

    public void setSelected(AGVorderType selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AGVorderTypeFacade getFacade() {
        return ejbFacade;
    }

    public AGVorderType prepareCreate() {
        selected = new AGVorderType();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVorderTypeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleAGV").getString("AGVorderTypeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleAGV").getString("AGVorderTypeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AGVorderType> getItems() {
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

    public AGVorderType getAGVorderType(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<AGVorderType> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AGVorderType> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AGVorderType.class)
    public static class AGVorderTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AGVorderTypeController controller = (AGVorderTypeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aGVorderTypeController");
            return controller.getAGVorderType(getKey(value));
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
            if (object instanceof AGVorderType) {
                AGVorderType o = (AGVorderType) object;
                return getStringKey(o.getOrderTypeId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AGVorderType.class.getName()});
                return null;
            }
        }

    }

}
