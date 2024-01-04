package com.odelowebapp.CODEKS.controller;

import com.odelowebapp.CODEKS.controller.util.JsfUtil;
import com.odelowebapp.CODEKS.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.CODEKS.entity.Departments;
import com.odelowebapp.CODEKS.facade.DepartmentsFacade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import org.apache.commons.collections4.MapUtils;

@Named("departmentsController")
@ViewScoped
public class DepartmentsController implements Serializable {

    @EJB
    private com.odelowebapp.CODEKS.facade.DepartmentsFacade ejbFacade;
    private List<Departments> items = null;
    private Departments selected;

    public DepartmentsController() {
    }

    public Departments getSelected() {
        return selected;
    }

    public void setSelected(Departments selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DepartmentsFacade getFacade() {
        return ejbFacade;
    }

    public Departments prepareCreate() {
        selected = new Departments();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleCodeks").getString("DepartmentsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleCodeks").getString("DepartmentsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleCodeks").getString("DepartmentsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Departments> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Map<Integer, Departments> getMap() {
        Map<Integer, Departments> map = new HashMap<>();
        if (items == null) {
            items = getFacade().findAll();
        }else{
            //System.out.println("ITEMS NI ENAKO NULL, populateMap");
            MapUtils.populateMap(map, items, Departments::getId);
        }
        return map;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCodeks").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCodeks").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Departments getDepartments(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Departments> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Departments> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Departments.class)
    public static class DepartmentsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartmentsController controller = (DepartmentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departmentsController");
            return controller.getDepartments(getKey(value));
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
            if (object instanceof Departments) {
                Departments o = (Departments) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Departments.class.getName()});
                return null;
            }
        }

    }

}
