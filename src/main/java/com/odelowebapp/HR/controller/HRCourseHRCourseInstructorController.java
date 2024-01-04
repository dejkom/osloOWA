package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRCourseHRCourseInstructor;
import com.odelowebapp.HR.facade.HRCourseHRCourseInstructorFacade;

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

@Named("hRCourseHRCourseInstructorController")
@ViewScoped
public class HRCourseHRCourseInstructorController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseHRCourseInstructorFacade ejbFacade;
    private List<HRCourseHRCourseInstructor> items = null;
    private HRCourseHRCourseInstructor selected;

    public HRCourseHRCourseInstructorController() {
    }

    public HRCourseHRCourseInstructor getSelected() {
        return selected;
    }

    public void setSelected(HRCourseHRCourseInstructor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseHRCourseInstructorFacade getFacade() {
        return ejbFacade;
    }

    public HRCourseHRCourseInstructor prepareCreate() {
        selected = new HRCourseHRCourseInstructor();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseHRCourseInstructorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseHRCourseInstructorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseHRCourseInstructorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseHRCourseInstructor> getItems() {
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

    public HRCourseHRCourseInstructor getHRCourseHRCourseInstructor(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourseHRCourseInstructor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseHRCourseInstructor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<HRCourseHRCourseInstructor> getInstructorsForCourse() {
        System.out.println("Sem v getInstructorsForCourse. Selected course:"+selected.getCourseID());
        return getFacade().findAllInstructorsForCourse(selected.getCourseID());
    }

    @FacesConverter(forClass = HRCourseHRCourseInstructor.class)
    public static class HRCourseHRCourseInstructorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseHRCourseInstructorController controller = (HRCourseHRCourseInstructorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseHRCourseInstructorController");
            return controller.getHRCourseHRCourseInstructor(getKey(value));
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
            if (object instanceof HRCourseHRCourseInstructor) {
                HRCourseHRCourseInstructor o = (HRCourseHRCourseInstructor) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseHRCourseInstructor.class.getName()});
                return null;
            }
        }

    }

}
