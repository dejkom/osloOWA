package com.odelowebapp.OSEC.controller;

import com.odelowebapp.OSEC.entity.OSECDowntime;
import com.odelowebapp.OSEC.controller.util.JsfUtil;
import com.odelowebapp.OSEC.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.OSEC.facade.OSECDowntimeFacade;

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

@Named("oSECDowntimeController")
@ViewScoped
public class OSECDowntimeController implements Serializable {

    @EJB
    private com.odelowebapp.OSEC.facade.OSECDowntimeFacade ejbFacade;
    private List<OSECDowntime> items = null;
    private OSECDowntime selected;
    
    private List<OSECDowntime> selectedItems = null;
    
    private boolean isSingleItemSelected;

    public OSECDowntimeController() {
    }

    public OSECDowntime getSelected() {
        return selected;
    }

    public void setSelected(OSECDowntime selected) {
        System.out.println("Sem v setSelected(OSECDowntime selected)");
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OSECDowntimeFacade getFacade() {
        return ejbFacade;
    }
    
    public void editOneSetSelected(){
        System.out.println("Sem v editOneSetSelected()");
        setSelected(selectedItems.get(0));
    }

    public boolean isIsSingleItemSelected() {
        if(selectedItems == null){
            isSingleItemSelected =false;
        }
        else if(selectedItems.size()==1){
            isSingleItemSelected = true;
        }else{
            isSingleItemSelected =false;
        }
        return isSingleItemSelected;
    }

    public void setIsSingleItemSelected(boolean isSingleItemSelected) {
        this.isSingleItemSelected = isSingleItemSelected;
    }
    
    
    
    public void confirmSelected(){
        System.out.println("Sem v confirmSelected(), potrdit jih moram:"+selectedItems.size());
        for(OSECDowntime d:selectedItems){
            d.setStatus(2);
            ejbFacade.edit(d);
        }
        selectedItems.clear();
        items = null;
    }
    
    public void declineSelected(){
        System.out.println("Sem v declineSelected(), declinati jih moram:"+selectedItems.size());
        for(OSECDowntime d:selectedItems){
            d.setStatus(1);
            ejbFacade.edit(d);
        }
        selectedItems.clear();
        items = null;
    }

    public OSECDowntime prepareCreate() {
        selected = new OSECDowntime();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECDowntimeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECDowntimeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOSECZastoji").getString("OSECDowntimeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OSECDowntime> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<OSECDowntime> getItemsUnconfirmed(int status) {
        if (items == null) {
            items = getFacade().findDowntimesByStatus(status);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOSECZastoji").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOSECZastoji").getString("PersistenceErrorOccured"));
            }
        }
    }

    public OSECDowntime getOSECDowntime(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<OSECDowntime> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OSECDowntime> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<OSECDowntime> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<OSECDowntime> selectedItems) {
        System.out.println("Sem v setSelectedItems(List<OSECDowntime> selectedItems)");
        this.selectedItems = selectedItems;
    }
    
    

    @FacesConverter(forClass = OSECDowntime.class)
    public static class OSECDowntimeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OSECDowntimeController controller = (OSECDowntimeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oSECDowntimeController");
            return controller.getOSECDowntime(getKey(value));
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
            if (object instanceof OSECDowntime) {
                OSECDowntime o = (OSECDowntime) object;
                return getStringKey(o.getDowntimeID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OSECDowntime.class.getName()});
                return null;
            }
        }

    }

}
