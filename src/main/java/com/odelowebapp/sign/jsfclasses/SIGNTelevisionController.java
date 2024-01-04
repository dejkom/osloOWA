package com.odelowebapp.sign.jsfclasses;

import com.odelowebapp.entity.SIGNTelevision;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil.PersistAction;
import com.odelowebapp.sign.sessionbeans.SIGNTelevisionFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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

@Named("sIGNTelevisionController")
@ViewScoped
public class SIGNTelevisionController implements Serializable {

    @EJB
    private com.odelowebapp.sign.sessionbeans.SIGNTelevisionFacade ejbFacade;
    private List<SIGNTelevision> items = null;
    private SIGNTelevision selected;
    private List<SIGNTelevision> selectedList = null;

    public SIGNTelevisionController() {
    }

    public SIGNTelevision getSelected() {
        return selected;
    }

    public void setSelected(SIGNTelevision selected) {
        this.selected = selected;
    }

    public List<SIGNTelevision> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<SIGNTelevision> selectedList) {
        if (selectedList.size() == 1) {
            selected = selectedList.get(0);
        }
        this.selectedList = selectedList;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SIGNTelevisionFacade getFacade() {
        return ejbFacade;
    }

    public SIGNTelevision prepareCreate() {
        selected = new SIGNTelevision();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNTelevisionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNTelevisionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNTelevisionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SIGNTelevision> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    @RequiresPermissions("hr:*")
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case CREATE: {
                        getFacade().create(selected);
                        break;
                    }
                    case UPDATE: {
                        if (selectedList.size() == 1) {
                            getFacade().edit(selected);
                        } else {
                            for (SIGNTelevision t : selectedList) {
                                t.setIdPresentation(selected.getIdPresentation());
                                t.setActive(selected.getActive());
                                getFacade().edit(t);
                            }
                        }
                        break;
                    }
                    case DELETE:{
                        for(SIGNTelevision t : selectedList){
                            getFacade().remove(t);
                        }
                        break;
                    }
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleSIGN").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleSIGN").getString("PersistenceErrorOccured"));
            }
        }
    }

    public SIGNTelevision getSIGNTelevision(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SIGNTelevision> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SIGNTelevision> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SIGNTelevision.class)
    public static class SIGNTelevisionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SIGNTelevisionController controller = (SIGNTelevisionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sIGNTelevisionController");
            return controller.getSIGNTelevision(getKey(value));
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
            if (object instanceof SIGNTelevision) {
                SIGNTelevision o = (SIGNTelevision) object;
                return getStringKey(o.getIdTelevision());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SIGNTelevision.class.getName()});
                return null;
            }
        }

    }

}
