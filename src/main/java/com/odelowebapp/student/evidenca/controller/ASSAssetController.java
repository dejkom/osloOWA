package com.odelowebapp.student.evidenca.controller;

import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.AGV.controller.util.JsfUtil;
import com.odelowebapp.AGV.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.student.evidenca.facade.ASSAssetFacade;
import com.odelowebapp.usermanagement.controller.HomeController;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.SerializationUtils;

@Named("aSSAssetController")
@ViewScoped
public class ASSAssetController implements Serializable {

    @EJB
    private ASSAssetFacade ejbFacade;
    private List<ASSAsset> items = null;
    private ASSAsset selected;
    
    @Inject
    private HomeController hc;

    public ASSAssetController() {
    }

    public ASSAsset getSelected() {
        return selected;
    }

    public void setSelected(ASSAsset selected) {
        this.selected = selected;
    }

    public ASSAsset getKopija() {
        return kopija;
    }

    public void setKopija(ASSAsset kopija) {
        this.kopija = kopija;
    }
    
    

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ASSAssetFacade getFacade() {
        return ejbFacade;
    }

    public ASSAsset prepareCreate() {
        selected = new ASSAsset();
        selected.setAssetUpdated(new Date());
        System.out.println("CODEKS ID iz HOMECONTROLLERJA:"+hc.getCodeksid());
        selected.setAssetUpdatedUser(hc.getCodeksid());
        initializeEmbeddableKey();
        return selected;
    }
    
    private ASSAsset kopija;
    
    public ASSAsset prepareCreateCopy() {
        
        kopija = (ASSAsset) SerializationUtils.clone(selected);
        
        kopija.setIDAsset(null);
        kopija.setASSAssetList(null);
        kopija.setASSLogList(null);
        kopija.setIDAssetParent(selected);
        //selected = new ASSAsset();
        kopija.setAssetUpdated(new Date());
        System.out.println("CODEKS ID iz HOMECONTROLLERJA:"+hc.getCodeksid());
        kopija.setAssetUpdatedUser(hc.getCodeksid());
        initializeEmbeddableKey();
        return kopija;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ASSAssetCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createKopija() {
        persistKopija(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ASSAssetCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
//        selected.getASSAssetList().add(kopija);
//        update();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ASSAssetUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ASSAssetDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void inactivate() {
        selected.setAssetDeleted(Boolean.TRUE);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ASSAssetDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ASSAsset> getItems() {
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
    
    private void persistKopija(PersistAction persistAction, String successMessage) {
        if (kopija != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(kopija);
                } else {
                    getFacade().remove(kopija);
                }
                JsfUtil.addSuccessMessage(successMessage);
                //getFacade().evictCache(); //DEAN TEST
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

    public ASSAsset getASSAsset(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ASSAsset> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ASSAsset> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ASSAsset.class)
    public static class ASSAssetControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ASSAssetController controller = (ASSAssetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aSSAssetController");
            return controller.getASSAsset(getKey(value));
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
            if (object instanceof ASSAsset) {
                ASSAsset o = (ASSAsset) object;
                return getStringKey(o.getIDAsset());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ASSAsset.class.getName()});
                return null;
            }
        }

    }

}
