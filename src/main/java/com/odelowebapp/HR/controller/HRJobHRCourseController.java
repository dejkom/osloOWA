package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRJobHRCourse;
import com.odelowebapp.HR.facade.HRJobHRCourseFacade;

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

@Named("hRJobHRCourseController")
@ViewScoped
public class HRJobHRCourseController implements Serializable {

    @EJB
    private HRJobHRCourseFacade ejbFacade;
    private List<HRJobHRCourse> items = null;
    private HRJobHRCourse selected;

    public HRJobHRCourseController() {
    }

    public HRJobHRCourse getSelected() {
        return selected;
    }

    public void setSelected(HRJobHRCourse selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRJobHRCourseFacade getFacade() {
        return ejbFacade;
    }

    public HRJobHRCourse prepareCreate() {
        selected = new HRJobHRCourse();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRJobHRCourseCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRJobHRCourseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRJobHRCourseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRJobHRCourse> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
            }
        }
    }

    public HRJobHRCourse getHRJobHRCourse(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRJobHRCourse> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRJobHRCourse> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRJobHRCourse.class)
    public static class HRJobHRCourseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRJobHRCourseController controller = (HRJobHRCourseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRJobHRCourseController");
            return controller.getHRJobHRCourse(getKey(value));
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
            if (object instanceof HRJobHRCourse) {
                HRJobHRCourse o = (HRJobHRCourse) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRJobHRCourse.class.getName()});
                return null;
            }
        }

    }

}
