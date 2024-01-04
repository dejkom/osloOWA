package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.entity.HRCourseInstructorsWorkLog;
import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.facade.HRCourseInstructorsWorkLogFacade;

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

@Named("hRCourseInstructorsWorkLogController")
@ViewScoped
public class HRCourseInstructorsWorkLogController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseInstructorsWorkLogFacade ejbFacade;
    private List<HRCourseInstructorsWorkLog> items = null;
    private HRCourseInstructorsWorkLog selected;

    public HRCourseInstructorsWorkLogController() {
    }

    public HRCourseInstructorsWorkLog getSelected() {
        return selected;
    }

    public void setSelected(HRCourseInstructorsWorkLog selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseInstructorsWorkLogFacade getFacade() {
        return ejbFacade;
    }

    public HRCourseInstructorsWorkLog prepareCreate() {
        selected = new HRCourseInstructorsWorkLog();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCIWL").getString("HRCourseInstructorsWorkLogCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCIWL").getString("HRCourseInstructorsWorkLogUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCIWL").getString("HRCourseInstructorsWorkLogDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseInstructorsWorkLog> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCIWL").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCIWL").getString("PersistenceErrorOccured"));
            }
        }
    }

    public HRCourseInstructorsWorkLog getHRCourseInstructorsWorkLog(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourseInstructorsWorkLog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseInstructorsWorkLog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRCourseInstructorsWorkLog.class)
    public static class HRCourseInstructorsWorkLogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseInstructorsWorkLogController controller = (HRCourseInstructorsWorkLogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseInstructorsWorkLogController");
            return controller.getHRCourseInstructorsWorkLog(getKey(value));
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
            if (object instanceof HRCourseInstructorsWorkLog) {
                HRCourseInstructorsWorkLog o = (HRCourseInstructorsWorkLog) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseInstructorsWorkLog.class.getName()});
                return null;
            }
        }

    }

}
