package com.odelowebapp.common.controller;

import com.odelowebapp.common.entity.Questionnaire;
import com.odelowebapp.common.controller.util.JsfUtil;
import com.odelowebapp.common.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.common.facade.QuestionnaireFacade;

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

@Named("questionnaireController")
@ViewScoped
public class QuestionnaireController implements Serializable {

    @EJB
    private com.odelowebapp.common.facade.QuestionnaireFacade ejbFacade;
    private List<Questionnaire> items = null;
    private Questionnaire selected;

    public QuestionnaireController() {
    }

    public Questionnaire getSelected() {
        return selected;
    }

    public void setSelected(Questionnaire selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionnaireFacade getFacade() {
        return ejbFacade;
    }

    public Questionnaire prepareCreate() {
        selected = new Questionnaire();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleQuestionnaire").getString("QuestionnaireCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleQuestionnaire").getString("QuestionnaireUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleQuestionnaire").getString("QuestionnaireDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Questionnaire> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleQuestionnaire").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleQuestionnaire").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Questionnaire getQuestionnaire(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Questionnaire> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Questionnaire> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Questionnaire.class)
    public static class QuestionnaireControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionnaireController controller = (QuestionnaireController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionnaireController");
            return controller.getQuestionnaire(getKey(value));
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
            if (object instanceof Questionnaire) {
                Questionnaire o = (Questionnaire) object;
                return getStringKey(o.getQuestionnaireID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Questionnaire.class.getName()});
                return null;
            }
        }

    }

}
