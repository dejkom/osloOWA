package com.odelowebapp.QS.controller;

import com.odelowebapp.QS.entity.QSoverview;
import com.odelowebapp.QS.controller.util.JsfUtil;
import com.odelowebapp.QS.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.QS.facade.QSoverviewFacade;

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

@Named("qSoverviewController")
@ViewScoped
public class QSoverviewController implements Serializable {

    @EJB
    private com.odelowebapp.QS.facade.QSoverviewFacade ejbFacade;
    private List<QSoverview> items = null;
    private QSoverview selected;

    public QSoverviewController() {
    }

    public QSoverview getSelected() {
        return selected;
    }

    public void setSelected(QSoverview selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QSoverviewFacade getFacade() {
        return ejbFacade;
    }

    public QSoverview prepareCreate() {
        selected = new QSoverview();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleQS").getString("QSoverviewCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleQS").getString("QSoverviewUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleQS").getString("QSoverviewDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QSoverview> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleQS").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleQS").getString("PersistenceErrorOccured"));
            }
        }
    }

    public QSoverview getQSoverview(java.lang.Integer id) {
        if(id != null){
        return getFacade().find(id);
        }else {return null;}
    }

    public List<QSoverview> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<QSoverview> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<QSoverview> getItemsAvailableByParentID(int pid) {
        return getFacade().findAllByParentID(pid);
    }

    @FacesConverter(forClass = QSoverview.class)
    public static class QSoverviewControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QSoverviewController controller = (QSoverviewController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "qSoverviewController");
            return controller.getQSoverview(getKey(value));
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
            if (object instanceof QSoverview) {
                QSoverview o = (QSoverview) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QSoverview.class.getName()});
                return null;
            }
        }

    }

}
