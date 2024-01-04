package com.odelowebapp.sign.jsfclasses;

import com.odelowebapp.entity.SIGNAlert;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil.PersistAction;
import com.odelowebapp.sign.sessionbeans.SIGNAlertFacade;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("sIGNAlertController")
@ViewScoped
public class SIGNAlertController implements Serializable {

    @EJB
    private com.odelowebapp.sign.sessionbeans.SIGNAlertFacade ejbFacade;
    private List<SIGNAlert> items = null;
    private SIGNAlert selected = new SIGNAlert();

    public SIGNAlertController() {

    }
    
    public SIGNAlert getSelected() {
        List<SIGNAlert> list = getFacade().findAll();
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        selected.setInnerHTML("");
        if (!list.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String lastAlertDate = sdf.format(list.get(list.size() - 1).getDate());
            String todayDate = sdf.format(Date.from(date.toInstant()));

            if (lastAlertDate.equals(todayDate)) {
                selected.setInnerHTML(list.get(list.size() - 1).getInnerHTML());
                selected.setIdAlert(list.get(list.size() - 1).getIdAlert());
            }
        }
        return selected;
    }

    public void setSelected(SIGNAlert selected) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set selected");
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SIGNAlertFacade getFacade() {
        return ejbFacade;
    }

    
    public SIGNAlert prepareCreate() {
        selected = new SIGNAlert();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Obvestilo ustvarjeno");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE,"Obvestilo ustvarjeno");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Obvestilo odstranjeno");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    @RequiresPermissions("hr:*")
    public List<SIGNAlert> getItems() {
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
                ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
                if (persistAction != PersistAction.DELETE && !selected.getInnerHTML().trim().equals("")) {
                    selected.setDate(Date.from(date.toInstant()));
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleSIGN").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleSIGN").getString("PersistenceErrorOccured"));
            }
        }
    }

    public SIGNAlert getSIGNAlert(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SIGNAlert> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SIGNAlert> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SIGNAlert.class)
    public static class SIGNAlertControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SIGNAlertController controller = (SIGNAlertController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sIGNAlertController");
            return controller.getSIGNAlert(getKey(value));
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
            if (object instanceof SIGNAlert) {
                SIGNAlert o = (SIGNAlert) object;
                return getStringKey(o.getIdAlert());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SIGNAlert.class.getName()});
                return null;
            }
        }

    }

}
