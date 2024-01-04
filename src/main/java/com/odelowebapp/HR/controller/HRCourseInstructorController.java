package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRCourseInstructor;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseInstructorFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import org.primefaces.event.SelectEvent;

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



@Named("hRCourseInstructorController")
@ViewScoped
public class HRCourseInstructorController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseInstructorFacade ejbFacade;
    private List<HRCourseInstructor> items = null;
    private HRCourseInstructor selected;
    
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private VADCODEKSUsersFacade ADcodeksFacade;
    private List<VCodeksUsersAktualniZaposleni> aktualniZaposleni;

    public HRCourseInstructorController() {
    }

    public void codekspersonSelected(SelectEvent event){
        System.out.println("Sem v codekspersonSelected: "+selected.getCodeksID());
        VADCODEKSUsers selusr = ADcodeksFacade.findUserByCodeksId(selected.getCodeksID());
        //VCodeksUsersAktualniZaposleni person = (VCodeksUsersAktualniZaposleni) event.getObject();
        System.out.println("SELECTED:"+selusr.getFirstname());
        selected.setMail(selusr.getMail());
        selected.setPhone(selusr.getMobile());
        selected.setActive(true);
    }
    
    
    public List<VCodeksUsersAktualniZaposleni> getAktualniZaposleni() {
        return aktualniZaposleni;
    }

    public void setAktualniZaposleni(List<VCodeksUsersAktualniZaposleni> aktualniZaposleni) {
        this.aktualniZaposleni = aktualniZaposleni;
    }
    
    

    public HRCourseInstructor getSelected() {
        return selected;
    }

    public void setSelected(HRCourseInstructor selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseInstructorFacade getFacade() {
        return ejbFacade;
    }

    public HRCourseInstructor prepareCreate() {
        selected = new HRCourseInstructor();
        initializeEmbeddableKey();
        return selected;
    }
    
    public HRCourseInstructor prepareCreateInterni() {
        System.out.println("Sem v prepareCreateInterni. Pridobiti moram zaposlene iz codeksa");
        aktualniZaposleni = usersEJB.zaposleniZaAndroidAplikacijo();
        System.out.println("Aktualnih zaposlenih:"+aktualniZaposleni.size());
        selected = new HRCourseInstructor();
        selected.setCompany("odelo Slovenija");
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseInstructorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createInterni() {
        
        selected.setFirstname(usersEJB.find(selected.getCodeksID()).getFirstname());
        selected.setLastname(usersEJB.find(selected.getCodeksID()).getLastname());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseInstructorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseInstructorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseInstructorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseInstructor> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleHRCourse").getString("PersistenceErrorOccured"));
            }
        }
    }

    public HRCourseInstructor getHRCourseInstructor(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourseInstructor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseInstructor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRCourseInstructor.class)
    public static class HRCourseInstructorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseInstructorController controller = (HRCourseInstructorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseInstructorController");
            return controller.getHRCourseInstructor(getKey(value));
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
            if (object instanceof HRCourseInstructor) {
                HRCourseInstructor o = (HRCourseInstructor) object;
                return getStringKey(o.getInstructorID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseInstructor.class.getName()});
                return null;
            }
        }

    }

}
