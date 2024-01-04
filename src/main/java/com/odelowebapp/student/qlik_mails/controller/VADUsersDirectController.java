package com.odelowebapp.student.qlik_mails.controller;

import com.odelowebapp.student.qlik_mails.entity.VADusersdirect;
import com.odelowebapp.student.qlik_mails.facade.VADUsersDirectFacade;
import com.odelowebapp.student.qlik_mails.util.JsfUtil;
import com.odelowebapp.student.qlik_mails.util.JsfUtil.PersistAction;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named("vADUsersDirectController")
@SessionScoped
public class VADUsersDirectController implements Serializable {

    @EJB
    private VADUsersDirectFacade ejbFacade;
    private List<VADusersdirect> items = null;
    private VADusersdirect selected;

    public VADUsersDirectController() {
    }

    public VADusersdirect getSelected() {
        return selected;
    }

    public void setSelected(VADusersdirect selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    @PostConstruct
    public void init() {
        items = getFacade().findAll();
    }

    public List<String> completeTextMail(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> mailList = new ArrayList<>();
        for (VADusersdirect entry : items) {
            if (entry.getMail() != null && !entry.getMail().isEmpty()) {
                mailList.add(entry.getMail());
            }
        }

        return mailList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public List<String> noResults(String query) {
        return Collections.EMPTY_LIST;
    }


    private VADUsersDirectFacade getFacade() {
        return ejbFacade;
    }

    public VADusersdirect prepareCreate() {
        selected = new VADusersdirect();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VADusersdirectCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VADusersdirectUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VADusersdirectDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<VADusersdirect> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public VADusersdirect getQLIKmailReceivers(String id) {
        return getFacade().find(id);
    }

    public List<VADusersdirect> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VADusersdirect> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = VADusersdirect.class)
    public static class QLIKmailReceiversControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VADUsersDirectController controller = (VADUsersDirectController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "VADusersdirectController");
            return controller.getQLIKmailReceivers(getKey(value));
        }

        String getKey(String value) {
            String key;
            key = value;
            return key;
        }

        String getStringKey(String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VADusersdirect) {
                VADusersdirect o = (VADusersdirect) object;
                return getStringKey(o.getDisplayName());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VADusersdirect.class.getName()});
                return null;
            }
        }

    }

}
