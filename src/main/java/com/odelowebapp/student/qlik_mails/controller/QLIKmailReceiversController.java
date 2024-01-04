package com.odelowebapp.student.qlik_mails.controller;

import com.odelowebapp.student.qlik_mails.entity.QLIKmailReceivers;
import com.odelowebapp.student.qlik_mails.facade.QLIKmailReceiversFacade;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;

@Named("qLIKmailReceiversController")
@ViewScoped
public class QLIKmailReceiversController implements Serializable {

    @EJB
    private QLIKmailReceiversFacade ejbFacade;
    private List<QLIKmailReceivers> items = null;
    private QLIKmailReceivers selected;
    private List<String> selectedMailTOList;
    private List<String> selectedMailCCList;
    private List<String> selectedMailBCCList;
    private HashMap<String, QLIKmailReceivers> reports;
    public QLIKmailReceiversController() {
    }

    private static String formatMails(List<String> selectedMailList) {
        StringBuilder builderMail = new StringBuilder();
        if (selectedMailList.isEmpty()) {
            return "";
        }
        for (String item :
                selectedMailList) {
            builderMail.append(item);
            builderMail.append(";");
        }
        if (builderMail.lastIndexOf(";") == builderMail.toString().length() - 1) {
            builderMail.deleteCharAt(builderMail.lastIndexOf(";"));
        }
        return builderMail.toString();
    }

    public QLIKmailReceivers getSelected() {
        return selected;
    }

    public void setSelected(QLIKmailReceivers selected) {
        this.selected = selected;
    }

    public List<String> getSelectedMailTOList() {
        return selectedMailTOList;
    }

    public void setSelectedMailTOList(List<String> selectedMailTOList) {
        this.selectedMailTOList = selectedMailTOList;
    }

    public List<String> getSelectedMailCCList() {
        return selectedMailCCList;
    }

    public void setSelectedMailCCList(List<String> selectedMailCCList) {
        this.selectedMailCCList = selectedMailCCList;
    }

    public List<String> getSelectedMailBCCList() {
        return selectedMailBCCList;
    }

    public void setSelectedMailBCCList(List<String> selectedMailBCCList) {
        this.selectedMailBCCList = selectedMailBCCList;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public HashMap<String, QLIKmailReceivers> getReports() {
        return reports;
    }

    public void setReports(HashMap<String, QLIKmailReceivers> reports) {
        this.reports = reports;
    }

    @PostConstruct
    public void init() {
        items = getFacade().findAll();
        reports = new HashMap<>();
        for (QLIKmailReceivers entry : items) {
            reports.put(entry.getReportName(), entry);
        }
    }

    public void updateMailList() {
        try {
            selectedMailTOList = Arrays.asList(selected.getMailListTO().split(";"));
            if (selected.getMailListTO().isEmpty()) {
                selectedMailTOList = new ArrayList<>();
            }
        } catch (Exception e) {
            selectedMailTOList = new ArrayList<>();
        }
        try {
            selectedMailCCList = Arrays.asList(selected.getMailListCC().split(";"));
            if (selected.getMailListCC().isEmpty()) {
                selectedMailCCList = new ArrayList<>();
            }
        } catch (Exception e) {
            selectedMailCCList = new ArrayList<>();
        }
        try {
            selectedMailBCCList = Arrays.asList(selected.getMailListBCC().split(";"));
            if (selected.getMailListBCC().isEmpty()) {
                selectedMailBCCList = new ArrayList<>();
            }
        } catch (Exception e) {
            selectedMailBCCList = new ArrayList<>();
        }
    }

    public void updateMailReceiversToDatabase() {
        //Properly format mails TO, CC & BCC for storing in database
        String builderMailTO = formatMails(selectedMailTOList);
        String builderMailCC = formatMails(selectedMailCCList);
        String builderMailBCC = formatMails(selectedMailBCCList);

        //Update in database
        getFacade().updateMailReceivers(selected, builderMailTO, builderMailCC, builderMailBCC);
    }

    private QLIKmailReceiversFacade getFacade() {
        return ejbFacade;
    }

    public QLIKmailReceivers prepareCreate() {
        selected = new QLIKmailReceivers();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QLIKmailReceiversCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QLIKmailReceiversUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QLIKmailReceiversDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QLIKmailReceivers> getItems() {
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

    public QLIKmailReceivers getQLIKmailReceivers(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<QLIKmailReceivers> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<QLIKmailReceivers> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = QLIKmailReceivers.class)
    public static class QLIKmailReceiversControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QLIKmailReceiversController controller = (QLIKmailReceiversController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "qLIKmailReceiversController");
            return controller.getQLIKmailReceivers(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof QLIKmailReceivers) {
                QLIKmailReceivers o = (QLIKmailReceivers) object;
                return getStringKey(o.getReportName());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QLIKmailReceivers.class.getName()});
                return null;
            }
        }

    }

}
