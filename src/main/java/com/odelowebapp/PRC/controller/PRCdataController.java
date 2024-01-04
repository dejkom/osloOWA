package com.odelowebapp.PRC.controller;

import com.odelowebapp.PRC.entity.PRCdata;
import com.odelowebapp.PRC.controller.util.JsfUtil;
import com.odelowebapp.PRC.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.PRC.facade.PRCdataFacade;

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

@Named("pRCdataController")
@ViewScoped
public class PRCdataController implements Serializable {

    @EJB
    private com.odelowebapp.PRC.facade.PRCdataFacade ejbFacade;
    private List<PRCdata> items = null;
    private PRCdata selected;

    public PRCdataController() {
    }

    public PRCdata getSelected() {
        return selected;
    }

    public void setSelected(PRCdata selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PRCdataFacade getFacade() {
        return ejbFacade;
    }

    public PRCdata prepareCreate() {
        selected = new PRCdata();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundlePRC").getString("PRCdataCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundlePRC").getString("PRCdataUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundlePRC").getString("PRCdataDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PRCdata> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePRC").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePRC").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PRCdata getPRCdata(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PRCdata> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PRCdata> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PRCdata.class)
    public static class PRCdataControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PRCdataController controller = (PRCdataController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pRCdataController");
            return controller.getPRCdata(getKey(value));
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
            if (object instanceof PRCdata) {
                PRCdata o = (PRCdata) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PRCdata.class.getName()});
                return null;
            }
        }

    }

}
