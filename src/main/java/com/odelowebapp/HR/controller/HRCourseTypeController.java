package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("hRCourseTypeController")
@ViewScoped
public class HRCourseTypeController implements Serializable {

    @EJB
    private HRCourseTypeFacade ejbFacade;
    private List<HRCourseType> items = null;
    private HRCourseType selected;
    private HRCourseType selectedListBox;
    
    private List<SelectItem> categories;
    
    @PostConstruct
    public void init(){
        recomputeCategoriesMultiSelectListBox();
    }
    
    public void recomputeCategoriesMultiSelectListBox(){
        System.out.println("Sem v recomputeCategoriesMultiSelectListBox()");
        categories = new ArrayList<>();
        List<HRCourseType> allParents1 = getFacade().findAllParents();
        for (HRCourseType parent1:allParents1) {
            SelectItemGroup group1 = new SelectItemGroup(parent1.getCourseTypeTitle());
            List<HRCourseType> allParents1_1 = getFacade().findAllChildren(parent1);
            ArrayList<SelectItem> selectItems1_1 = new ArrayList<>();
            for (HRCourseType parent1_1:allParents1_1) {
                SelectItemGroup group1_1 = new SelectItemGroup(parent1_1.getCourseTypeTitle());
                List<HRCourseType> allParents1_1_1 = getFacade().findAllChildren(parent1_1);

                ArrayList<SelectItem> selectItems1_1_1 = new ArrayList<>();
                for (HRCourseType option:allParents1_1_1) {
                    //SelectItem option1_1_1 = new SelectItem(option.getCourseTypeID(), option.getCourseTypeTitle());
                    SelectItem option1_1_1 = new SelectItem(option, option.getCourseTypeTitle());
                    selectItems1_1_1.add(option1_1_1);
                }

                group1_1.setSelectItems(selectItems1_1_1.toArray(new SelectItem[0]));
                selectItems1_1.add(group1_1);
            }
            group1.setSelectItems(selectItems1_1.toArray(new SelectItem[0]));
            categories.add(group1);
        }
    }

    public HRCourseTypeController() {
    }

    public HRCourseType getSelected() {
        return selected;
    }

    public void setSelected(HRCourseType selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseTypeFacade getFacade() {
        return ejbFacade;
    }

    public HRCourseType prepareCreate() {
        selected = new HRCourseType();
        initializeEmbeddableKey();
        return selected;
    }

    public List<SelectItem> getCategories() {
        return categories;
    }

    public void setCategories(List<SelectItem> categories) {
        this.categories = categories;
    }

    public HRCourseType getSelectedListBox() {
        return selectedListBox;
    }

    public void setSelectedListBox(HRCourseType selectedListBox) {
        this.selectedListBox = selectedListBox;
    }
    
    

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseTypeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseTypeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseTypeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseType> getItems() {
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

    public HRCourseType getHRCourseType(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourseType> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseType> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRCourseType.class)
    public static class HRCourseTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseTypeController controller = (HRCourseTypeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseTypeController");
            return controller.getHRCourseType(getKey(value));
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
            if (object instanceof HRCourseType) {
                HRCourseType o = (HRCourseType) object;
                return getStringKey(o.getCourseTypeID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseType.class.getName()});
                return null;
            }
        }

    }

}
