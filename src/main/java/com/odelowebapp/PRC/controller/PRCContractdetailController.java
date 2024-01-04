package com.odelowebapp.PRC.controller;

import com.odelowebapp.PRC.entity.PRCContractdetail;
import com.odelowebapp.PRC.controller.util.JsfUtil;
import com.odelowebapp.PRC.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.PRC.facade.PRCContractdetailFacade;

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

@Named("pRCContractdetailController")
@SessionScoped
public class PRCContractdetailController implements Serializable {

    @EJB
    private com.odelowebapp.PRC.facade.PRCContractdetailFacade ejbFacade;
    private List<PRCContractdetail> items = null;
    private PRCContractdetail selected;

    public PRCContractdetailController() {
    }

    public PRCContractdetail getSelected() {
        return selected;
    }

    public void setSelected(PRCContractdetail selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PRCContractdetailFacade getFacade() {
        return ejbFacade;
    }

    public PRCContractdetail prepareCreate() {
        selected = new PRCContractdetail();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundlePRC2").getString("PRCContractdetailCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundlePRC2").getString("PRCContractdetailUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundlePRC2").getString("PRCContractdetailDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PRCContractdetail> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePRC2").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePRC2").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PRCContractdetail getPRCContractdetail(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PRCContractdetail> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PRCContractdetail> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PRCContractdetail.class)
    public static class PRCContractdetailControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PRCContractdetailController controller = (PRCContractdetailController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pRCContractdetailController");
            return controller.getPRCContractdetail(getKey(value));
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
            if (object instanceof PRCContractdetail) {
                PRCContractdetail o = (PRCContractdetail) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PRCContractdetail.class.getName()});
                return null;
            }
        }

    }

}
