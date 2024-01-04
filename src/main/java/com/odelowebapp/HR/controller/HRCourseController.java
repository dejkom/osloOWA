package com.odelowebapp.HR.controller;

import com.google.common.collect.Maps;
import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.entity.PrimefacesUploadedFile;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.facade.HRCourseFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

@Named("hRCourseController")
@ViewScoped
public class HRCourseController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseFacade ejbFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private HRCourseTypeFacade coursetypesEJB;
    private List<HRCourse> items = null;
    private HRCourse selected;
    
    private LazyDataModel<HRCourse> lazyModel;
    
    //zgodovinski userji
    @EJB
    private VADCODEKSUsersFacade codeksusersFacade;
    private Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap = new HashMap();
    //

    private UploadedFile file;
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();
    private int activeIndex = 0;
    
    private List<SelectItem> coursetypesSelectItems = new ArrayList();
    private String coursetypeselection;
    
    PrimeFaces current;

    public HRCourseController() {
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for course:"+selected.getCourseID()+" files allready there:"+selected.getFiles().size());
        files = selected.getFiles();
        files.add(new PrimefacesUploadedFile(event.getFile()));
        selected.setFiles(files);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Files size:"+selected.getFiles().size());
    }
    
    public void testMethod(){
        System.out.println("Sem v testMethod()");
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        //options.put("height", "600px");
        options.put("modal", true);
        current.dialog().openDynamic("/HR/Courses/hRCourseInstructor/index", options, null);
        
        //current.executeScript("PF('HRCourseInstructorTypeDialog').show()");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v HRCourseController init()");
        
        current = PrimeFaces.current();
        
        List<HRCourseType> allCourseTypes = coursetypesEJB.findAll();
        
        try{
        //naslednja metoda findUsersByCodeksIds ne deluje ob ogromnem številu parametrov v IN pogoju
        //vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findUsersByCodeksIds(allAttendancesForOffering.stream().map(HRCourseAttendance::getCodeksID).collect(Collectors.toList())), VADCODEKSUsers::getId);
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findAll(), VADCODEKSUsers::getId);
        
        }catch(Exception e){}
        
        lazyModel = new LazyDataModel<HRCourse>() {
            
            @Override
            public String getRowKey(HRCourse object) {
                return object.getCourseID() + "";
                //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                Long count = ejbFacade.countEntities(HRCourse.class, filterBy);
                return count.intValue();
            }
            
            @Override
            public HRCourse getRowData(String rowKey) {
                for (HRCourse o : lazyModel.getWrappedData()) {
                    if (o.getCourseID() == Integer.parseInt(rowKey)) {
                        return o;
                    }
                }

                return null;
            }

            @Override
            public List<HRCourse> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                System.out.println("----- SEM V LOAD MADAFAKA ---");
                 return (List<HRCourse>) ejbFacade.findEntites(HRCourse.class ,first, pageSize, sortBy, filterBy);
            }
            
            
        };
        
    }

    public HRCourse getSelected() {
        return selected;
    }

    public void setSelected(HRCourse selected) {
        this.selected = selected;
        
        try{
            selected.findAttachmentsForCourse(selected.getCourseID());
            files = selected.getFiles();
        }catch(Exception e){
            System.out.println("selected.getFiles() catch blok");
        }
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseFacade getFacade() {
        return ejbFacade;
    }

    public HRCourse prepareCreate() {
        selected = new HRCourse();
        initializeEmbeddableKey();
        return selected;
    }

    public void saveBeforeAddingAttachments() {
        System.out.println("sem v saveBeforeAddingAttachments");
    }

    public void fileUploadAction() {
        System.out.println("Sem v fileUploadAction. Selected course has this many attachments:");
        selected.findAttachmentsForCourse(selected.getCourseID());
        System.out.println("Size:"+selected.getFileattachments().size());
    }
    
    public void showIzvedbeButtonAction() {
        System.out.println("Sem v showIzvedbeButtonAction. Selected course has this many..." );
        System.out.println("Izvedb:"+selected.getHRCourseOfferingList().size());
    }
    
    List<HRCourseAttendance> allAtt = new ArrayList();
    public void showVsiUdelezenciButtonAction(){
        System.out.println("Sem v showVsiUdelezenciButtonAction." );
        allAtt.clear();
        List<HRCourseOffering> hrCourseOfferingList = selected.getHRCourseOfferingList();
        for(HRCourseOffering co : hrCourseOfferingList){
            allAtt.addAll(co.getHRCourseAttendanceList());
        }
    }

    public void create() {
        System.out.println("Sem v create");
        selected.setLastUpdate(new Date());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseCreated"));
                
        //saveAttachmentsToDirectory(selected.getCourseID());
        
        if (!JsfUtil.isValidationFailed()) {
            System.out.println("sem v tistem Invalidate shitu");
            items = null;    // Invalidate list of items to trigger re-query.
        }

    }

    public void update() {
        System.out.println("Sem v update");
        selected.setLastUpdate(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourse> getItems() {
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

    public HRCourse getHRCourse(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<HRCourse> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourse> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = HRCourse.class)
    public static class HRCourseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseController controller = (HRCourseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseController");
            return controller.getHRCourse(getKey(value));
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
            if (object instanceof HRCourse) {
                HRCourse o = (HRCourse) object;
                return getStringKey(o.getCourseID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourse.class.getName()});
                return null;
            }
        }

    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public HRvcodeksusersFacade getUsersEJB() {
        return usersEJB;
    }

    public void setUsersEJB(HRvcodeksusersFacade usersEJB) {
        this.usersEJB = usersEJB;
    }

    public List<SelectItem> getCoursetypesSelectItems() {
        return coursetypesSelectItems;
    }

    public void setCoursetypesSelectItems(List<SelectItem> coursetypesSelectItems) {
        this.coursetypesSelectItems = coursetypesSelectItems;
    }

    public String getCoursetypeselection() {
        return coursetypeselection;
    }

    public void setCoursetypeselection(String coursetypeselection) {
        this.coursetypeselection = coursetypeselection;
    }

    public List<HRCourseAttendance> getAllAtt() {
        return allAtt;
    }

    public void setAllAtt(List<HRCourseAttendance> allAtt) {
        this.allAtt = allAtt;
    }

    public Map<Integer, VADCODEKSUsers> getVrnjeniUserjiMap() {
        return vrnjeniUserjiMap;
    }

    public void setVrnjeniUserjiMap(Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap) {
        this.vrnjeniUserjiMap = vrnjeniUserjiMap;
    }

    public LazyDataModel<HRCourse> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<HRCourse> lazyModel) {
        this.lazyModel = lazyModel;
    }
    
    

}
