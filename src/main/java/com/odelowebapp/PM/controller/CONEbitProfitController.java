package com.odelowebapp.PM.controller;

import com.odelowebapp.PM.entity.CONEbitProfit;
import com.odelowebapp.PM.controller.util.JsfUtil;
import com.odelowebapp.PM.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.PM.facade.CONEbitProfitFacade;

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

@Named("cONEbitProfitController")
@ViewScoped
public class CONEbitProfitController implements Serializable {

    @EJB
    private com.odelowebapp.PM.facade.CONEbitProfitFacade ejbFacade;
    private List<CONEbitProfit> items = null;
    private CONEbitProfit selected;

    public CONEbitProfitController() {
    }

    public CONEbitProfit getSelected() {
        return selected;
    }

    public void setSelected(CONEbitProfit selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CONEbitProfitFacade getFacade() {
        return ejbFacade;
    }

    public CONEbitProfit prepareCreate() {
        selected = new CONEbitProfit();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleEbit").getString("CONEbitProfitCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleEbit").getString("CONEbitProfitUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleEbit").getString("CONEbitProfitDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CONEbitProfit> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEbit").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleEbit").getString("PersistenceErrorOccured"));
            }
        }
    }

    public CONEbitProfit getCONEbitProfit(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<CONEbitProfit> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CONEbitProfit> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CONEbitProfit.class)
    public static class CONEbitProfitControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CONEbitProfitController controller = (CONEbitProfitController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cONEbitProfitController");
            return controller.getCONEbitProfit(getKey(value));
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
            if (object instanceof CONEbitProfit) {
                CONEbitProfit o = (CONEbitProfit) object;
                return getStringKey(o.getIdRow());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CONEbitProfit.class.getName()});
                return null;
            }
        }

    }

}
