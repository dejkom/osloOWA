package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;

@Named("vADCODEKSUsersController")
@ViewScoped
public class VADCODEKSUsersController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.VADCODEKSUsersFacade ejbFacade;
    private List<VADCODEKSUsers> items = null;
    private VADCODEKSUsers selected;

    private String searchBoxText;

    public String getSearchBoxText() {
        return searchBoxText;
    }

    public void setSearchBoxText(String searchBoxText) {
        this.searchBoxText = searchBoxText;
    }

    public void updateContactsList(){
        items = getFacade().searchUserByNameOrSurnameOrPhone(searchBoxText);
    }

    @PostConstruct
    public void init(){

    }

    public VADCODEKSUsersController() {
    }

    public VADCODEKSUsers getSelected() {
        return selected;
    }

    public void setSelected(VADCODEKSUsers selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VADCODEKSUsersFacade getFacade() {
        return ejbFacade;
    }

    public VADCODEKSUsers prepareCreate() {
        selected = new VADCODEKSUsers();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRADCODEKS").getString("VADCODEKSUsersCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRADCODEKS").getString("VADCODEKSUsersUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRADCODEKS").getString("VADCODEKSUsersDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<VADCODEKSUsers> getItems() {
        if (items == null) {
            if (searchBoxText != null) {
                if (searchBoxText.trim().isEmpty()){
                    items = getFacade().findAllNotNull();
                }
                else {
                    items = getFacade().searchUserByNameOrSurnameOrPhone(searchBoxText);
                }
            }
            else {
                //items = getFacade().findAll();
                items = getFacade().findAllNotNull();
            }
        }
        System.out.println("Vrnjenih kontaktov: " + items.size());
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRADCODEKS").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRADCODEKS").getString("PersistenceErrorOccured"));
            }
        }
    }

    public VADCODEKSUsers getVADCODEKSUsers(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public VADCODEKSUsers getVADCODEKSUsersByExternalID(java.lang.Integer id) {
        return getFacade().findUserByExternalId(id+"");
    }
    
    public List<VADCODEKSUsers> getVADCODEKSUsersList(List<Integer> ids) {
        return getFacade().findUsersByCodeksIds(ids);
    }

    public List<VADCODEKSUsers> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VADCODEKSUsers> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = VADCODEKSUsers.class)
    public static class VADCODEKSUsersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VADCODEKSUsersController controller = (VADCODEKSUsersController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vADCODEKSUsersController");
            return controller.getVADCODEKSUsers(getKey(value));
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
            if (object instanceof VADCODEKSUsers) {
                VADCODEKSUsers o = (VADCODEKSUsers) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VADCODEKSUsers.class.getName()});
                return null;
            }
        }

    }

}
