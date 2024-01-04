package com.odelowebapp.sign.jsfclasses;

import com.odelowebapp.entity.SIGNSlide;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil.PersistAction;
import com.odelowebapp.sign.sessionbeans.SIGNSlideFacade;

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

@Named("sIGNSlideTestController")
@ViewScoped
public class SIGNSlideTestController implements Serializable {

    @EJB
    private com.odelowebapp.sign.sessionbeans.SIGNSlideFacade ejbFacade;
    private List<SIGNSlide> items = null;
    private SIGNSlide selected;

    public SIGNSlideTestController() {
    }

    public SIGNSlide getSelected() {
        return selected;
    }

    public void setSelected(SIGNSlide selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SIGNSlideFacade getFacade() {
        return ejbFacade;
    }

    public SIGNSlide prepareCreate() {
        selected = new SIGNSlide();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SIGNSlideTestCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SIGNSlideTestUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SIGNSlideTestDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SIGNSlide> getItems() {
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

    public SIGNSlide getSIGNSlideTest(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SIGNSlide> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SIGNSlide> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SIGNSlide.class)
    public static class SIGNSlideTestControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SIGNSlideTestController controller = (SIGNSlideTestController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sIGNSlideTestController");
            return controller.getSIGNSlideTest(getKey(value));
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
            if (object instanceof SIGNSlide) {
                SIGNSlide o = (SIGNSlide) object;
                return getStringKey(o.getIdSlide());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SIGNSlide.class.getName()});
                return null;
            }
        }

    }

}
