package com.odelowebapp.HR.controller;

import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.*;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRCourseHRCourseInstructorFacade;
import com.odelowebapp.HR.facade.HRCourseOfferingFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import com.odelowebapp.exchange.entity.OsebniKoledar;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.Importance;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.file.UploadedFile;

//import si.odelo.uporabnik.beans.UporabnikovaSeja;
@Named("hRCourseOfferingController")
@ViewScoped
public class HRCourseOfferingController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.HRCourseOfferingFacade ejbFacade;
    private List<HRCourseOffering> items = null;
    private HRCourseOffering selected;

    private LazyDataModel<HRCourseOffering> lazyModel;

    private List<HRCourseOffering> selectedCourseOfferings = new ArrayList();

    @EJB
    private HRCourseHRCourseInstructorFacade joinFacade;

    @EJB
    private HRvcodeksusersFacade usersEJB;

    @EJB
    private VADCODEKSUsersFacade usersADCodeksEJB;

    @EJB
    private HRCourseAttendanceFacade cattendanceEJB;

    @EJB
    private HRCourseTypeFacade ctFacade;

    OsebniKoledar ok = new OsebniKoledar();

    private boolean kreiramDogodekExchange = false;

    private List<HRCourseInstructor> instruktorjiNaVoljo = new ArrayList();

    private List<VCodeksUsersAktualniZaposleni> selectedEmployees;
    private HashMap<Integer, VCodeksUsersAktualniZaposleni> selectedEmployeesHM = new HashMap<Integer, VCodeksUsersAktualniZaposleni>();

    private HashMap<Integer, VADCODEKSUsers> allEmployeesHM = new HashMap<Integer, VADCODEKSUsers>();

    private List<VCodeksUsersAktualniZaposleni> currentlyEmployed;

    private VCodeksUsersAktualniZaposleni selectedEmployeeToBeDeleted;

    private ExchangeService es = new ExchangeService();

    FacesContext facesContext = FacesContext.getCurrentInstance();
    //UporabnikovaSeja uporabnikovaseja = (UporabnikovaSeja) facesContext.getApplication().createValueBinding("#{uporabnikovaSeja}").getValue(facesContext);
    private UploadedFile file;
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();

    private List<Date> rangeFromTo = new ArrayList();

    @PostConstruct
    public void init() {
        initMail();
        napolniCelotenHM();
        lazyModel = new LazyDataModel<HRCourseOffering>() {

            @Override
            public String getRowKey(HRCourseOffering object) {
                return object.getCourseOfferingID() + "";
                //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                Long count = ejbFacade.countEntities(HRCourseOffering.class, filterBy);
                return count.intValue();
            }

            @Override
            public HRCourseOffering getRowData(String rowKey) {
                for (HRCourseOffering o : lazyModel.getWrappedData()) {
                    if (o.getCourseOfferingID() == Integer.parseInt(rowKey)) {
                        String creatorFirstnameLastname = allEmployeesHM.get(o.getCreator()).getFirstname() + " " + allEmployeesHM.get(o.getCreator()).getLastname();
                        o.setCreatorFirstnameLastname(creatorFirstnameLastname);
                        return o;
                    }
                }

                return null;
            }

            @Override
            public List<HRCourseOffering> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                System.out.println("----- SEM V LOAD MADAFAKA ---");

                List<HRCourseOffering> findEntites = (List<HRCourseOffering>) ejbFacade.findEntites(HRCourseOffering.class, first, pageSize, sortBy, filterBy);

                for (HRCourseOffering item : findEntites) {
                    String creatorFirstnameLastname = allEmployeesHM.get(item.getCreator()).getFirstname() + " " + allEmployeesHM.get(item.getCreator()).getLastname();
                    item.setCreatorFirstnameLastname(creatorFirstnameLastname);
                }

                return findEntites;
                //return (List<HRCourseOffering>) ejbFacade.findEntites(HRCourseOffering.class ,first, pageSize, sortBy, filterBy);

            }

        };

    }

    public HRvcodeksusersFacade getUsersEJB() {
        return usersEJB;
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for courseOffering:" + selected.getCourseOfferingID() + " files allready there:" + selected.getFiles().size());
        files = selected.getFiles();
        files.add(new PrimefacesUploadedFile(event.getFile()));
        selected.setFiles(files);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Files size:" + selected.getFiles().size());
    }

    public void courseSelected() {
        System.out.println("Sem v courseSelected!");
        System.out.println("Selected course:" + selected.getCourseID().getCourseID());
        instruktorjiNaVoljo.clear();
        List<HRCourseHRCourseInstructor> joins = joinFacade.findAllInstructorsForCourse(selected.getCourseID());
        for (HRCourseHRCourseInstructor i : joins) {
            instruktorjiNaVoljo.add(i.getInstructorID());
        }

    }

    public void fileUploadAction() {
        System.out.println("Sem v fileUploadAction. Selected courseoffering has this many attachments:");
        selected.findAttachmentsForCourse(selected.getCourseOfferingID());
        System.out.println("Size offering:" + selected.getFileattachments().size());
        System.out.println("Selected course has this many attachments:");
        selected.getCourseID().findAttachmentsForCourse(selected.getCourseID().getCourseID());
        System.out.println("Size course:" + selected.getCourseID().getFileattachments().size());
    }

    public void dodajvkoledar() {
        System.out.println("Sem v dodajvkoledar()");
        napolniCelotenHM();
        //ok.connect2Exchange();
        ok.connect2ExchangeIzobrazevanjeKadrovska();
        //ok.createTestAppointment();
        String subject = "Izobraževanje - " + selected.getCourseID().getCourseTitle();

        StringBuilder sb = new StringBuilder();
        sb.append("Prijavljeni ste na izobraževanje:<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + selected.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + selected.getCourseDate() + "<br/>");
        sb.append("<strong>Trajanje izobraževanja: </strong> " + selected.getCourseID().getDuration() + " minut <br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + selected.getMeetingroomString() + "<br/>");
        sb.append("<strong>Predavatelj: </strong>" + selected.getInstructorID().getLastname() + " " + selected.getInstructorID().getFirstname() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Ustvaril:</strong>" + allEmployeesHM.get(selected.getCreator()).getLastname() + " " + allEmployeesHM.get(selected.getCreator()).getFirstname());
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + selected.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");
        sb.append("<small>To sporočilo je ustvarjeno avtomatsko, zato nanj ne odgovarjajte. </small>");

        Date start = selected.getCourseDate();
        DateTime enddt = new DateTime(start.getTime());
        enddt = enddt.plusMinutes(selected.getCourseID().getDuration());

        List<HRCourseAttendance> hrCourseAttendanceList = selected.getHRCourseAttendanceList();
        List<String> mails = new ArrayList();
        for (HRCourseAttendance person : hrCourseAttendanceList) {
            //String mail = usersEJB.findUserByCodeksId(person.getCodeksID()).getEmail();
            String mail = usersADCodeksEJB.findUserByCodeksId(person.getCodeksID()).getMail();
            if (!mail.isEmpty()) {
                mails.add(mail);
            } else {
                System.out.println("Ne najdem maila osebe:" + usersADCodeksEJB.findUserByCodeksId(person.getCodeksID()).getExternalId() + " - " + usersADCodeksEJB.findUserByCodeksId(person.getCodeksID()).getLastname());
            }
        }

        ok.createAppointmentIzobrazevanje(subject, sb.toString(), start, enddt.toDate(), selected.getMeetingroomString(), mails, selected.getInstructorID().getMail());

        //prijavim se kot portal in shranim dogodek vanj - DELUJE VENDAR NE VEM ČE RABIMO DOKLER NE DELA @ReqiresRoles
//        ok.connect2Exchange();
//        ok.createAppointmentIzobrazevanjePortalMailbox(subject, sb.toString(), start, enddt.toDate(), selected.getMeetingroomString(), mails, selected.getInstructorID().getMail());
    }

    public void initMail() {
        try {
            System.out.println("Sem v initMail()");
            es.setUrl(new URI("https://hfmail01.sw.lighting.int/ews/Exchange.asmx"));
            String pass = "portal1010";
            String uname = "portal";
            ExchangeCredentials credentials = new WebCredentials(uname, pass);
            es.setCredentials(credentials);
        } catch (URISyntaxException ex) {
            System.out.println("Napaka initMail():" + ex);
            //Logger.getLogger(HRCourseOfferingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void employeeSelected() {
        System.out.println("Sem v employeeSelected!");
        System.out.println("Selected employees:" + selectedEmployees.size());

    }

    public void removeSelectedAtendee() {
        System.out.println("Sem v removeSelectedAtendee");
        selectedEmployeesHM.remove(selectedEmployeeToBeDeleted.getId());
        int result = cattendanceEJB.deletePersonFromCourse(selected, selectedEmployeeToBeDeleted);
        System.out.println("Izbrisanih vrstic:" + result);
        sendMailNotificationOdpoved2Atendee(selectedEmployeeToBeDeleted.getEmail(), selected);
        //POŠILJANJE MAILA O PREKLICU IZOBRAŽEVANJA
    }

    public void saveSelectedAtendees() {
        System.out.println("Sem v save selected atendees, shraniti moram toliko uporabnikov:" + selectedEmployeesHM.size());
        List<HRCourseAttendance> hrCourseAttendanceList = new ArrayList();
//        for (int i = 0; i < selectedEmployees.size(); i++) {
//            HRCourseAttendance h = new HRCourseAttendance();
//            h.setCodeksID(selectedEmployees.get(i).getId());
//            h.setCourseOfferingID(selected);
//            hrCourseAttendanceList.add(h);
//        }

        for (Map.Entry<Integer, VCodeksUsersAktualniZaposleni> entry : selectedEmployeesHM.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() + "inserted:" + entry.getValue().isInserted());

            if (!entry.getValue().isInserted()) {
                HRCourseAttendance h = new HRCourseAttendance();
                h.setCodeksID(entry.getKey());
                h.setCourseOfferingID(selected);
                hrCourseAttendanceList.add(h);
                //Tukaj lahko osebi pošljem mail
                if (entry.getValue().getEmail() == null || entry.getValue().getEmail().equals("")) {
                    System.out.println("Email osebe naj nebi obstajal:" + entry.getValue().getEmail());
                } else {
                    System.out.println("Email osebe:" + entry.getValue().getEmail());
                    sendMailNotification2Atendee(entry.getValue().getEmail(), selected, entry.getValue().getLastname() + " " + entry.getValue().getFirstname(), "");
                }

                //
            }

        }

        //sendMailNotification2Instructor(selected.getInstructorID().getMail(),selected,"");
        System.out.println("Pred shranjevanjem je list velikosti:" + hrCourseAttendanceList.size());
        selected.setHRCourseAttendanceList(hrCourseAttendanceList);
        persist(PersistAction.UPDATE, "Shranjevanje uspešno");
        selectedEmployees.clear();
    }

    public void sendMailNotification2Atendee(String mail, HRCourseOffering izobrazevanje, String imepriimekprejemnika, String reason) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obveščamo vas, da ste s strani kadrovske službe napoteni na naslednje izobraževanje:<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + izobrazevanje.getCourseDate() + "<br/>");
        sb.append("<strong>Trajanje izobraževanja: </strong> " + izobrazevanje.getCourseID().getDuration() + " minut <br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + izobrazevanje.getMeetingroomString() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o napotitvi na izobraževanje " + reason);
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("Obvestilo!", "Mail ni bil poslan osebi " + imepriimekprejemnika));
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void sendMailNotification2Instructor(String mail, HRCourseOffering izobrazevanje, String reason) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obveščamo vas, da vam je bila dodeljena izvedba izobraževanja:<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + izobrazevanje.getCourseDate() + "<br/>");
        sb.append("<strong>Trajanje izobraževanja: </strong> " + izobrazevanje.getCourseID().getDuration() + " minut <br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + izobrazevanje.getMeetingroomString() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Na izobraževanju ste v vlogi IZOBRAŽEVALCA/PREDAVATELJA </strong><br/>");
        sb.append("<br/>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo predavatelju o izvedbi izobraževanja " + reason);
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("NAPAKA!", "Mail ni bil poslan"));
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void sendMailNotificationOdpoved2Atendee(String mail, HRCourseOffering izobrazevanje) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obveščamo vas, da ste bili odstranjeni iz seznama udeležencev izobraževanja:<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + izobrazevanje.getCourseDate() + "<br/>");
        sb.append("<strong>Trajanje izobraževanja: </strong> " + izobrazevanje.getCourseID().getDuration() + " minut <br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + izobrazevanje.getMeetingroomString() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o preklicani napotitvi na izobraževanje");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("Obvestilo!", "Mail ni bil poslan. Razlog:" + ex.getLocalizedMessage()));
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void sendMailNotificationOdpoved2Instructor(String mail, HRCourseOffering izobrazevanje) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obveščamo vas, da je bilo vaše naslednje izobraževanje preklicano:<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + izobrazevanje.getCourseDate() + "<br/>");
        sb.append("<strong>Trajanje izobraževanja: </strong> " + izobrazevanje.getCourseID().getDuration() + " minut <br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + izobrazevanje.getMeetingroomString() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o preklicani izvedbi izobraževanja");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("NAPAKA!", "Mail ni bil poslan"));
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void sendMailNotificationNewInstructorAccess2HR(String mail, HRCourseOffering izobrazevanje) {
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obveščamo vas, da je v izobraževanje vstopil neznan (nov) predavatelj<br/>");
        sb.append("<br/>");
        sb.append("<strong>Naziv izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseTitle() + "<br/>");
        sb.append("<strong>Datum izobraževanja: </strong>" + izobrazevanje.getCourseDate() + "<br/>");
        sb.append("<strong>Lokacija izobraževanja: </strong>" + izobrazevanje.getMeetingroomString() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Opis izobraževanja: </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");
        sb.append("<br/>");
        sb.append("<strong>Preverite ali ima oseba kompetence za to opravilo in po potrebi ukrepajte! </strong>" + izobrazevanje.getCourseID().getCourseDescription() + "<br/>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o vstopu neznanega predavatelja");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("NAPAKA!", "Mail ni bil poslan"));
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void pridobizaposlene() {
        System.out.println("Sem v pridobizaposlene() za izbran offering:" + selected.getCourseOfferingID());
        currentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();
        List<HRCourseAttendance> prebrani = cattendanceEJB.findAllPersonsForCourse(selected);
        selectedEmployeesHM.clear();
        selectedEmployees = new ArrayList();
        for (HRCourseAttendance p : prebrani) {
            System.out.println("Iscem uporabnika z id:" + p.getCodeksID());
            VCodeksUsersAktualniZaposleni zap = usersEJB.findUserByCodeksId(p.getCodeksID());
            selectedEmployees.add(zap);
            zap.setInserted(true);
            selectedEmployeesHM.put(zap.getId(), zap);
        }
    }

//    private void napolniCelotenHM() {
//        System.out.println("Sem v napolniCelotenHM");
//        allEmployeesHM.clear();
//        List<VCodeksUsersAktualniZaposleni> allcurrentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();
//        for (VCodeksUsersAktualniZaposleni zap : allcurrentlyEmployed) {
//            allEmployeesHM.put(zap.getId(), zap);
//        }
//    }
    
    @EJB
    private VADCODEKSUsersFacade zgodFacade;
    
    private void napolniCelotenHM() {
        System.out.println("Sem v napolniCelotenHMZgodovinski");
        allEmployeesHM.clear();
        List<VADCODEKSUsers> allcurrentlyEmployed = zgodFacade.findAll();
        for (VADCODEKSUsers zap : allcurrentlyEmployed) {
            allEmployeesHM.put(zap.getId(), zap);
        }
    }

    public List<VCodeksUsersAktualniZaposleni> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<VCodeksUsersAktualniZaposleni> selectedEmployees) {
        System.out.println("Sem v setSelectedEmployees");
        for (VCodeksUsersAktualniZaposleni z : selectedEmployees) {
            if (!selectedEmployeesHM.containsKey(z.getId())) {
                selectedEmployeesHM.put(z.getId(), z);
            }

        }
        this.selectedEmployees = selectedEmployees;
        System.out.println("konec  setSelectedEmployees size:" + selectedEmployees.size());
    }

    public List<HRCourseInstructor> getInstruktorjiNaVoljo() {
        return instruktorjiNaVoljo;
    }

    public void setInstruktorjiNaVoljo(List<HRCourseInstructor> instruktorjiNaVoljo) {
        this.instruktorjiNaVoljo = instruktorjiNaVoljo;
    }

//    public List<VCodeksUsersAktualniZaposleni> getCurrentlyEmployed(){
//        return usersEJB.zaposleniZaAndroidAplikacijo();
//    }
    public List<VCodeksUsersAktualniZaposleni> getCurrentlyEmployed() {
        return currentlyEmployed;
    }

    public void setCurrentlyEmployed(List<VCodeksUsersAktualniZaposleni> currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }

//    public UporabnikovaSeja getUporabnikovaseja() {
//        return uporabnikovaseja;
//    }
//
//    public void setUporabnikovaseja(UporabnikovaSeja uporabnikovaseja) {
//        this.uporabnikovaseja = uporabnikovaseja;
//    }
    public HRCourseOfferingController() {
    }

    public HRCourseOffering getSelected() {
        return selected;
    }

    public void setSelected(HRCourseOffering selected) {
        this.selected = selected;

        try {
            selected.findAttachmentsForCourse(selected.getCourseOfferingID());
            files = selected.getFiles();
        } catch (Exception e) {
            System.out.println("selected.getFiles() catch blok, ne najdem datotek za courseoffering");
        }

    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private HRCourseOfferingFacade getFacade() {
        return ejbFacade;
    }

    public VCodeksUsersAktualniZaposleni getSelectedEmployeeToBeDeleted() {
        return selectedEmployeeToBeDeleted;
    }

    public void setSelectedEmployeeToBeDeleted(VCodeksUsersAktualniZaposleni selectedEmployeeToBeDeleted) {
        this.selectedEmployeeToBeDeleted = selectedEmployeeToBeDeleted;
    }

    public boolean isKreiramDogodekExchange() {
        return kreiramDogodekExchange;
    }

    public void setKreiramDogodekExchange(boolean kreiramDogodekExchange) {
        this.kreiramDogodekExchange = kreiramDogodekExchange;
    }

    public HRCourseOffering prepareCreate() {
        selected = new HRCourseOffering();
        initializeEmbeddableKey();
        int userid = 0;
        //userid = uporabnikovaseja.getCodeksUser().getId();
        userid = Integer.parseInt((String) SecurityUtils.getSubject().getSession().getAttribute("codeksid"));
        selected.setDateCreated(new Date());
        selected.setCreator(userid);
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseOfferingCreated"));

        sendMailNotification2Instructor(selected.getInstructorID().getMail(), selected, "");

        if (kreiramDogodekExchange) {
            System.out.println("Kreiram Exchange dogodek - naj bi");
        } else {
            System.out.println("Ne kreiram Exchange dogodka");
        }

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void deleteCOwithrelatedTables(HRCourseOffering co) {
        for (HRCourseAttendance at : co.getHRCourseAttendanceList()) {
            cattendanceEJB.remove(at);
        }
        selected = co;
        destroy();

    }

    public void deleteSelectedCOwithrelatedTables() {
        for (HRCourseAttendance at : selected.getHRCourseAttendanceList()) {
            cattendanceEJB.remove(at);
        }
        destroy();

    }

    public void update() {
        selected.setDateUpdated(new Date());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseOfferingUpdated"));
        sendMailNotification2Instructor(selected.getInstructorID().getMail(), selected, " - sprememba");
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleHRCourse").getString("HRCourseOfferingDeleted"));
        //ejbFacade.refreshDean();
        if (!JsfUtil.isValidationFailed()) {
            System.out.println("destroy, selected=null items=null");
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<HRCourseOffering> getItems() {
        System.out.println("Sem v getItems()");
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<HRCourseOffering> getItemsWithCreatorString() {
        System.out.println("Sem v getItemsWithCreatorString()");
        if (items == null) {
            items = getFacade().findAll();
            for (HRCourseOffering item : items) {
                String creatorFirstnameLastname = allEmployeesHM.get(item.getCreator()).getFirstname() + " " + allEmployeesHM.get(item.getCreator()).getLastname();
                item.setCreatorFirstnameLastname(creatorFirstnameLastname);
            }
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

    public HRCourseOffering getHRCourseOffering(java.lang.Integer id) {
        System.out.println("Sem v getHRCourseOffering()");
        return getFacade().find(id);
    }

    public List<HRCourseOffering> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<HRCourseOffering> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<HRCourseOffering> getTomorrowsExternalOfferings() {
        try {
            List<HRCourseType> ctypes = ctFacade.getRecursiveCourseHierarchy(2);
            return getFacade().findAllCourseOfferingsTomorrow(ctypes);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Date> getRangeFromTo() {
        if (rangeFromTo.isEmpty() || rangeFromTo == null) {
            Date now = new Date();
            rangeFromTo.add(now);
            rangeFromTo.add(now);
        } else {
            return rangeFromTo;
        }
        return null;
    }

    public void setRangeFromTo(List<Date> rangeFromTo) {
        this.rangeFromTo = rangeFromTo;
    }

    public List<HRCourseOffering> getExternalOfferingsCustomTimeRange(Date from, Date to) {
        try {
            List<HRCourseType> ctypes = ctFacade.getRecursiveCourseHierarchy(2);
            return getFacade().findAllCourseOfferingsCustomTimeRange(ctypes, from, to);
        } catch (Exception e) {
            return null;
        }
    }

    public List<HRCourseOffering> getSelectedCourseOfferings() {
        return selectedCourseOfferings;
    }

    public void setSelectedCourseOfferings(List<HRCourseOffering> selectedCourseOfferings) {
        this.selectedCourseOfferings = selectedCourseOfferings;
    }

    public LazyDataModel<HRCourseOffering> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<HRCourseOffering> lazyModel) {
        this.lazyModel = lazyModel;
    }

    @FacesConverter(forClass = HRCourseOffering.class)
    public static class HRCourseOfferingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HRCourseOfferingController controller = (HRCourseOfferingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hRCourseOfferingController");
            return controller.getHRCourseOffering(getKey(value));
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
            if (object instanceof HRCourseOffering) {
                HRCourseOffering o = (HRCourseOffering) object;
                return getStringKey(o.getCourseOfferingID());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), HRCourseOffering.class.getName()});
                return null;
            }
        }

    }

}
