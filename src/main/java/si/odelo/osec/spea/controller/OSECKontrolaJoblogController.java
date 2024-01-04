package si.odelo.osec.spea.controller;

import si.odelo.osec.spea.entity.OSECKontrolaJoblog;
import si.odelo.osec.spea.controller.util.JsfUtil;
import si.odelo.osec.spea.controller.util.JsfUtil.PersistAction;
import si.odelo.osec.spea.facade.OSECKontrolaJoblogFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("oSECKontrolaJoblogController")
@ViewScoped
public class OSECKontrolaJoblogController implements Serializable {

    @EJB
    private si.odelo.osec.spea.facade.OSECKontrolaJoblogFacade ejbFacade;
    private List<OSECKontrolaJoblog> items = null;
    private OSECKontrolaJoblog selected;

    public OSECKontrolaJoblogController() {
    }

    public OSECKontrolaJoblog getSelected() {
        return selected;
    }

    public void setSelected(OSECKontrolaJoblog selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OSECKontrolaJoblogFacade getFacade() {
        return ejbFacade;
    }

    public OSECKontrolaJoblog prepareCreate() {
        selected = new OSECKontrolaJoblog();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("OSECKontrolaJoblogCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("OSECKontrolaJoblogUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOsecSpea").getString("OSECKontrolaJoblogDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<OSECKontrolaJoblog> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOsecSpea").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleOsecSpea").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<OSECKontrolaJoblog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<OSECKontrolaJoblog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = OSECKontrolaJoblog.class)
    public static class OSECKontrolaJoblogControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OSECKontrolaJoblogController controller = (OSECKontrolaJoblogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oSECKontrolaJoblogController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof OSECKontrolaJoblog) {
                OSECKontrolaJoblog o = (OSECKontrolaJoblog) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OSECKontrolaJoblog.class.getName()});
                return null;
            }
        }

    }

}
