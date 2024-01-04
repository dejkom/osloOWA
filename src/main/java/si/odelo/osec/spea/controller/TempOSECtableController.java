package si.odelo.osec.spea.controller;

import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import si.odelo.osec.spea.entity.TempOSECtable;
import si.odelo.osec.spea.controller.util.JsfUtil;
import si.odelo.osec.spea.controller.util.JsfUtil.PersistAction;
import si.odelo.osec.spea.facade.TempOSECtableFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
//import si.odelo.uporabnik.beans.UporabnikovaSeja;

@Named("tempOSECtableController")
@ViewScoped
public class TempOSECtableController implements Serializable {

    @EJB
    private TempOSECtableFacade ejbFacade;
    private List<TempOSECtable> items = null;
    private TempOSECtable selected;
    private List<TempOSECtable> selectedMultiple = null;

    boolean dovolimSprostitev;
    
    private VCodeksUsersAktualniZaposleni kreator;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    
    @EJB
    private VADCODEKSUsersFacade usersFacade;
    VADCODEKSUsers user;

//    @ManagedProperty(value = "#{uporabnikovaSeja}")
//    private UporabnikovaSeja seja;

    public void sprostiVBaan() {
        System.out.println("Sem v sprostiVBaan()");
        //System.out.println("Sprostil bom vrstico z ID-jem:"+selected.getId());
        selected.setSprostilTimestamp(new Date());
        
        String uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
        //user = usersFacade.findUserByExternalId(uid);
        //System.out.println("USER ki potrjuje po starem:"+user.getUsername());
        
        
        //dodano nov portal
        Subject currentUser = SecurityUtils.getSubject();
        String codeksIDprijavljenega = currentUser.getSession().getAttribute("codeksid") + "";
        user = usersFacade.findUserByCodeksId(Integer.parseInt(codeksIDprijavljenega));
        System.out.println("USER ki potrjuje po novem:"+user.getUsername());
        //
        
        selected.setSprostilVBaan(user.getUsername());
        
        //selected.setSprostilVBaan(seja.getPrijavljen().getAdusername());
        selected.setPrenosVBaan(Boolean.TRUE);
        ejbFacade.edit(selected);
    }

    public void rowSelect() {
        System.out.println("Sem v rowSelect");
        System.out.println("Število izbranih elementov:" + selectedMultiple.size());
        if (selectedMultiple.size() == 1) {
            selected = selectedMultiple.get(0);
        } else {
            selected = null;
        }

    }

    List<TempOSECtable> predhodnistatusi = new ArrayList();
    public void cmListener() {
        System.out.println("Sem v cmListener. Število izbranih elementov:" + selectedMultiple.size());
        dovolimSprostitev = true;

        if (selectedMultiple.size() == 1) {
            System.out.println("Izbrana je 1 vrstica za katero moram sedaj preveriti predhodne statuse");
            System.out.println("Izbrana vrstica:" + selectedMultiple.get(0).getUnitSN() + " TestDatetime:" + selectedMultiple.get(0).getIdpk().getTestDatetime());
            System.out.println("-------------------------------------");
            predhodnistatusi.clear();
            predhodnistatusi = ejbFacade.getPreviousStatuses(selectedMultiple.get(0).getUnitSN());
            //List<TempOSECtable> predhodnistatusi = ejbFacade.getPreviousStatuses(selectedMultiple.get(0).getUnitSN());
            System.out.println("Predhodni statusi size():" + predhodnistatusi.size());

            if (selectedMultiple.get(0).getStatus() == 20) {
                for (int i = 0; i < predhodnistatusi.size(); i++) {
                    System.out.println("Status(" + i + "):" + predhodnistatusi.get(i).getUnitSN() + " " + predhodnistatusi.get(i).getIdpk().getTestDatetime() + " " + predhodnistatusi.get(i).getStatus());
                    if (predhodnistatusi.get(i).getStatus() == 50) {
                        if(selectedMultiple.get(0).getRemark().contains("FORCE")){
                            System.out.println("Našel sem status 50 in FORCE -- DOVOLIM SPROSTITEV");
                            dovolimSprostitev = true;
                        }else{
                            System.out.println("Našel sem status 50 -- NE DOVOLIM SPROSTITVE");
                            dovolimSprostitev = false;
                        }
                    }
                    else if (predhodnistatusi.get(i).getStatus() == 80) {
                        System.out.println("Našel sem status 80 -- TAKŠEN KOS BI NAJ BIL AVTOMATSKO SPROŠČEN ŽE NA STRANI TALEND JOBA - TODO, lahko ga sprostite ročno");
                    }
                }
            } else if (selectedMultiple.get(0).getStatus() == 30) {
                System.out.println("Čakamo odgovor kako je s potrjevanjem statusa 30");
                dovolimSprostitev = true;
            }

//            for(int i=0; i<predhodnistatusi.size(); i++){
//                System.out.println("Status("+i+"):"+predhodnistatusi.get(i).getUnitSN()+" "+predhodnistatusi.get(i).getIdpk().getTestDatetime()+" "+predhodnistatusi.get(i).getStatus());
//                if(predhodnistatusi.get(i).getStatus() == 50){
//                    System.out.println("Našel sem status 50 -- NE DOVOLIM SPROSTITVE");
//                    dovolimSprostitev = false;
//                }else if(predhodnistatusi.get(i).getStatus() == 80){
//                    System.out.println("Našel sem status 80 -- TAKŠEN KOS BI NAJ BIL AVTOMATSKO SPROŠČEN ŽE NA STRANI TALEND JOBA - TODO");
//                }
//            }
        } else {
            System.out.println("Izbranih je preveč vrstic");
        }
    }
    
    public void arhiviraj(){
        System.out.println("Sem v arhiviraj()");
        for(int i=0; i<selectedMultiple.size();i++){
            System.out.println("Arhiviral bom:"+selectedMultiple.get(i).getIdpk().getId());
            TempOSECtable e = selectedMultiple.get(i);
            e.setArhivirano(true);
            ejbFacade.edit(e);
        }
        selectedMultiple.clear();
        items=null;
    }

    public List<TempOSECtable> getPredhodnistatusi() {
        return predhodnistatusi;
    }

    public void setPredhodnistatusi(List<TempOSECtable> predhodnistatusi) {
        this.predhodnistatusi = predhodnistatusi;
    }

    
    
    public boolean isDovolimSprostitev() {
        return dovolimSprostitev;
    }

    public void setDovolimSprostitev(boolean dovolimSprostitev) {
        this.dovolimSprostitev = dovolimSprostitev;
    }

//    public UporabnikovaSeja getSeja() {
//        return seja;
//    }
//
//    public void setSeja(UporabnikovaSeja seja) {
//        this.seja = seja;
//    }

    public List<TempOSECtable> getSelectedMultiple() {
        return selectedMultiple;
    }

    public void setSelectedMultiple(List<TempOSECtable> selectedMultiple) {
        this.selectedMultiple = selectedMultiple;
    }

    public TempOSECtableController() {
    }

    public TempOSECtable getSelected() {
        return selected;
    }

    public void setSelected(TempOSECtable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TempOSECtableFacade getFacade() {
        return ejbFacade;
    }

    public TempOSECtable prepareCreate() {
        selected = new TempOSECtable();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("TempOSECtableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleOsecSpea").getString("TempOSECtableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleOsecSpea").getString("TempOSECtableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TempOSECtable> getItems() {
        if (items == null) {
            //items = getFacade().findAll();
            items = getFacade().findAllNotArchived();
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

    public List<TempOSECtable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TempOSECtable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TempOSECtable.class)
    public static class TempOSECtableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TempOSECtableController controller = (TempOSECtableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tempOSECtableController");
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
            if (object instanceof TempOSECtable) {
                TempOSECtable o = (TempOSECtable) object;
//                return getStringKey(o.getId());
                return getStringKey(o.getUnitID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TempOSECtable.class.getName()});
                return null;
            }
        }

    }

}
