package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.entity.HRVisitLog;
import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.VCodeksObiskovalci;
import com.odelowebapp.HR.entity.VCodeksObiskovalciPK;
import com.odelowebapp.HR.facade.HRVisitLogFacade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

@Named("hRVisitLogController")
@ViewScoped
public class HRVisitLogController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRVisitLogFacade ejbFacade;
    private List<HRVisitLog> items = null;
    private HRVisitLog selected;
    
    private VCodeksObiskovalci selectedNajava;
    
    @EJB
    private com.odelowebapp.HR.facade.VCodeksObiskovalciFacade obiskFacade;
    
    private List<FilterMeta> filterBy;
            
    private String podpisValue = "";
    private boolean potrjujemSeznanjenost;
    
    @PostConstruct
    public void init() {
        filterBy = new ArrayList<>();
        filterBy.add(FilterMeta.builder()
                .field("timeIn")
                .filterValue(new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
                .matchMode(MatchMode.BETWEEN)
                .build());
    }
    
    public void izpisIzpolnjenegaPotrdila(){
        System.out.println("sem v izpisIzpolnjenegaPotrdila()");
        
        
        VCodeksObiskovalciPK pk = new VCodeksObiskovalciPK();
        pk.setGuest(selected.getHRVisitLogPK().getGuest());
        pk.setVisitId(selected.getHRVisitLogPK().getVisitID());
        selectedNajava = obiskFacade.find(pk);
        
        
        
    }

    public String getPodpisValue() {
        return podpisValue;
    }

    public void setPodpisValue(String podpisValue) {
        this.podpisValue = podpisValue;
    }

    public boolean isPotrjujemSeznanjenost() {
        return potrjujemSeznanjenost;
    }

    public void setPotrjujemSeznanjenost(boolean potrjujemSeznanjenost) {
        this.potrjujemSeznanjenost = potrjujemSeznanjenost;
    }

    public VCodeksObiskovalci getSelectedNajava() {
        return selectedNajava;
    }

    public void setSelectedNajava(VCodeksObiskovalci selectedNajava) {
        this.selectedNajava = selectedNajava;
    }

    public List<FilterMeta> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<FilterMeta> filterBy) {
        this.filterBy = filterBy;
    }
    
    

    public HRVisitLogController() {
    }

    public HRVisitLog getSelected() {
        return selected;
    }

    public void setSelected(HRVisitLog selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setHRVisitLogPK(new com.odelowebapp.HR.entity.HRVisitLogPK());
    }

    private HRVisitLogFacade getFacade() {
        return ejbFacade;
    }

    public HRVisitLog prepareCreate() {
        selected = new HRVisitLog();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleVisitorEntryLog").getString("HRVisitLogCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleVisitorEntryLog").getString("HRVisitLogUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleVisitorEntryLog").getString("HRVisitLogDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRVisitLog> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleVisitorEntryLog").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleVisitorEntryLog").getString("PersistenceErrorOccured"));
            }
        }
    }

    public HRVisitLog getHRVisitLog(com.odelowebapp.HR.entity.HRVisitLogPK id) {
        return getFacade().find(id);
    }

    public List<HRVisitLog> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRVisitLog> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRVisitLog.class)
    public static class HRVisitLogControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRVisitLogController controller = (HRVisitLogController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRVisitLogController");
            return controller.getHRVisitLog(getKey(value));
        }

        com.odelowebapp.HR.entity.HRVisitLogPK getKey(String value) {
            com.odelowebapp.HR.entity.HRVisitLogPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.odelowebapp.HR.entity.HRVisitLogPK();
            key.setVisitID(Integer.parseInt(values[0]));
            key.setGuest(values[1]);
            return key;
        }

        String getStringKey(com.odelowebapp.HR.entity.HRVisitLogPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getVisitID());
            sb.append(SEPARATOR);
            sb.append(value.getGuest());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof HRVisitLog) {
                HRVisitLog o = (HRVisitLog) object;
                return getStringKey(o.getHRVisitLogPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRVisitLog.class.getName()});
                return null;
            }
        }

    }

}
