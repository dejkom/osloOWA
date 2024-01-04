package si.odelo.osec.spea.controller;

import si.odelo.osec.spea.entity.FinishedOSECtable;
import si.odelo.osec.spea.controller.util.JsfUtil;
import si.odelo.osec.spea.controller.util.JsfUtil.PersistAction;
import si.odelo.osec.spea.facade.FinishedOSECtableFacade;

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

@Named("finishedOSECtableController")
@ViewScoped
public class FinishedOSECtableController implements Serializable {

    @EJB
    private si.odelo.osec.spea.facade.FinishedOSECtableFacade ejbFacade;
    private List<FinishedOSECtable> items = null;
    private FinishedOSECtable selected;
    
    private int numberOfResults = 1000;

    public FinishedOSECtableController() {
    }

    public FinishedOSECtable getSelected() {
        return selected;
    }

    public void setSelected(FinishedOSECtable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FinishedOSECtableFacade getFacade() {
        return ejbFacade;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }
    
    

    public FinishedOSECtable prepareCreate() {
        selected = new FinishedOSECtable();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("FinishedOSECtableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("FinishedOSECtableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOsecSpea").getString("FinishedOSECtableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<FinishedOSECtable> getItems() {
        if (items == null) {
            //items = getFacade().findAll();
            items = getFacade().findAllTop(numberOfResults);
        }
        return items;
    }
    
    public void osveziTabelo(){
        System.out.println("Sem v osveziTabelo() FinishedOSECtableController");
        items=null;
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

    public List<FinishedOSECtable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<FinishedOSECtable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = FinishedOSECtable.class)
    public static class FinishedOSECtableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FinishedOSECtableController controller = (FinishedOSECtableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "finishedOSECtableController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof FinishedOSECtable) {
                FinishedOSECtable o = (FinishedOSECtable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FinishedOSECtable.class.getName()});
                return null;
            }
        }

    }

}
