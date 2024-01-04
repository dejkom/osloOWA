package com.odelowebapp.PM.controller;

import com.odelowebapp.PM.entity.CONPSReport;
import com.odelowebapp.PM.controller.util.JsfUtil;
import com.odelowebapp.PM.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.PM.facade.CONPSReportFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;

@Named("cONPSReportController")
@ViewScoped
public class CONPSReportController implements Serializable {

    @EJB
    private com.odelowebapp.PM.facade.CONPSReportFacade ejbFacade;
    private List<CONPSReport> items = null;
    private CONPSReport selected;

    public CONPSReportController() {
    }

    public CONPSReport getSelected() {
        return selected;
    }

    public void setSelected(CONPSReport selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CONPSReportFacade getFacade() {
        return ejbFacade;
    }

    public CONPSReport prepareCreate() {
        selected = new CONPSReport();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleCONPSReport").getString("CONPSReportCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleCONPSReport").getString("CONPSReportUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleCONPSReport").getString("CONPSReportDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CONPSReport> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCONPSReport").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCONPSReport").getString("PersistenceErrorOccured"));
            }
        }
    }

    public CONPSReport getCONPSReport(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<CONPSReport> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CONPSReport> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CONPSReport.class)
    public static class CONPSReportControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CONPSReportController controller = (CONPSReportController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cONPSReportController");
            return controller.getCONPSReport(getKey(value));
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
            if (object instanceof CONPSReport) {
                CONPSReport o = (CONPSReport) object;
                return getStringKey(o.getIdRow());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CONPSReport.class.getName()});
                return null;
            }
        }

    }

}
