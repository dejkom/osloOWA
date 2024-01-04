package com.odelowebapp.HR.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odelowebapp.HR.entity.VCodeksObiskovalci;
import com.odelowebapp.HR.controller.util.JsfUtil;
import com.odelowebapp.HR.controller.util.JsfUtil.PersistAction;
import com.odelowebapp.HR.entity.HRVisitLog;
import com.odelowebapp.HR.entity.HRVisitLogPK;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.entity.VCodeksObiskovalci;
import com.odelowebapp.HR.facade.VCodeksObiskovalciFacade;
import com.odelowebapp.entity.Photo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.Importance;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.PrimeFaces;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

@Named("vCodeksObiskovalciController")
@ViewScoped
public class VCodeksObiskovalciController implements Serializable {

    @EJB
    private com.odelowebapp.HR.facade.VCodeksObiskovalciFacade ejbFacade;
    @EJB
    private com.odelowebapp.HR.facade.HRVisitLogFacade visitLogFacade;
    @EJB
    private com.odelowebapp.HR.facade.VADCODEKSUsersFacade codeksFacade;

    private List<VCodeksObiskovalci> items = null;
    private VCodeksObiskovalci selected;
    private List<VCodeksObiskovalci> selectedList;
    
     private List<Photo> photos;

    private String podpisValue = "";
    private boolean potrjujemSeznanjenost;
    private String rfidKey;
    private String anketaMailGost;

    private boolean hasMail;
    private boolean hasMobile;
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
    private ExchangeService es = new ExchangeService();

    PrimeFaces current;
    
    @Inject
    @Push(channel = "Reception_Visitor_channel")
    private PushContext RECEPVISITchannel;
    
    @Inject
    @Push(channel = "Reception_Visitor_channel_survey")
    private PushContext RECEPVISITchannelsurvey;

    @PostConstruct
    public void init() {
        System.out.println("Sem v INIT VCodeksObiskovalciController");
        current = PrimeFaces.current();
        initMail();
        photos = new ArrayList<>();
        photos.add(new Photo("visitorinformations/Slide1.png", "visitorinformations/Slide1.png", "Description for Image 1", "Title 1"));
        photos.add(new Photo("visitorinformations/Slide2.PNG", "visitorinformations/Slide2.PNG", "Description for Image 2", "Title 2"));
        photos.add(new Photo("visitorinformations/Slide3.PNG", "visitorinformations/Slide3.PNG", "Description for Image 3", "Title 3"));
        photos.add(new Photo("visitorinformations/Slide4.PNG", "visitorinformations/Slide4.PNG", "Description for Image 4", "Title 4"));
        photos.add(new Photo("visitorinformations/Slide5.PNG", "visitorinformations/Slide5.PNG", "Description for Image 5", "Title 5"));
        photos.add(new Photo("visitorinformations/Slide6.PNG", "visitorinformations/Slide6.PNG", "Description for Image 6", "Title 6"));
        photos.add(new Photo("visitorinformations/Slide7.PNG", "visitorinformations/Slide7.PNG", "Description for Image 7", "Title 7"));
    }
    
    public String isSignedStatus(int visitid){
        System.out.println("Sem v isSignedStatus(), iscem za id:"+visitid);
        List<HRVisitLog> findLogsForVisitID = visitLogFacade.findLogsForVisitID(visitid);
        System.out.println("Sem v isSignedStatus(), vrnjenih sem dobil podatkov:"+findLogsForVisitID.size());
        int gostov = findLogsForVisitID.size();
        int podpisov = 0;
        for(HRVisitLog log : findLogsForVisitID){
            if(log.getSignature() == null){
                
            }else{
                podpisov++;
            }
        }
        return gostov +"/"+podpisov;
    }
    
    public void initMail(){
        try {
            System.out.println("Sem v initMail()");
            es.setUrl(new URI("https://hfmail01.sw.lighting.int/ews/Exchange.asmx"));
            String pass = "portal1010";
            String uname = "portal";
            ExchangeCredentials credentials = new WebCredentials(uname, pass);
            es.setCredentials(credentials);
        } catch (URISyntaxException ex) {
            System.out.println("Napaka initMail():"+ex);
            //Logger.getLogger(HRCourseOfferingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public VCodeksObiskovalciController() {
    }

    public VCodeksObiskovalci getSelected() {
        return selected;
    }

    public void setSelected(VCodeksObiskovalci selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VCodeksObiskovalciFacade getFacade() {
        return ejbFacade;
    }
    
    public void refresh(){
        System.out.println("Sem v refreshu");
        items = null;
    }
    
    public void novaAnketaPokazi(){
        System.out.println("sem v novaAnketaPokazi()");
        current.executeInitScript("renderSurvey('surveyDiv', '"+selected.getVCodeksObiskovalciPK().getGuest()+"', 'PriimekD', 'SI', '"+selected.getVCodeksObiskovalciPK().getVisitId()+"'); PF('dlg8').show()");
    }
    
    public void novaAnketaPokaziIF(){
        System.out.println("sem v novaAnketaPokazi()");
        current.executeInitScript("renderSurvey('surveyDiv', 'priimekD', 'PriimekD', 'SI', '"+selected.getVCodeksObiskovalciPK().getVisitId()+"');openWin('surveyDiv')");
    }
    
    public void novaAnketaPokaziAIO(){
        System.out.println("sem v novaAnketaPokaziAIO()");
        String idnajave = selected.getVCodeksObiskovalciPK().getVisitId()+"";
        String guest = selected.getVCodeksObiskovalciPK().getGuest()+"";
        String company = selected.getCompany()+"";
        String reason = selected.getReason()+"";
        String json = "{\"visitID\":\"" + idnajave + "\",\"guest\":\"" + guest + "\",\"company\":\"" + company + "\",\"reason\":\"" + reason + "\"}";
        System.out.println("JSON: " + json);
        RECEPVISITchannelsurvey.send(json);
    }
    
    // PUSH FUNKCIONALNOST
    public void testPushMessaging(){
        System.out.println("Sem v testPushMessaging");
        String jsonobjekta = gson.toJson(selected);
        RECEPVISITchannel.send(jsonobjekta);
    }
    
    public void pushSetSelected(){
        System.out.println("Sem v pushSetSelected()");
        String param1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param1");
        System.out.println("param1:"+param1);
        VCodeksObiskovalci fromJson = gson.fromJson(param1, VCodeksObiskovalci.class);
        System.out.println("Iz objekta:"+fromJson.getForuser()+" "+fromJson.getVCodeksObiskovalciPK().getGuest()+" "+fromJson.getVCodeksObiskovalciPK().getVisitId());
        selected = fromJson;
        podpisValue = "";
        potrjujemSeznanjenost = false;
    }
    
    // END PUSH FUNKCIONALNOSTI
    
    public void pridobiPodpisIzBaze(){
        System.out.println("Sem v pridobiPodpisIzBaze()");
        HRVisitLogPK visitLogPK = new HRVisitLogPK();
        visitLogPK.setGuest(selected.getVCodeksObiskovalciPK().getGuest());
        visitLogPK.setVisitID(selected.getVCodeksObiskovalciPK().getVisitId());
        
        HRVisitLog find = visitLogFacade.find(visitLogPK);
        if(Objects.isNull(find)){
            potrjujemSeznanjenost = false;
            podpisValue = "";
        }else{
        System.out.println("FIND:"+find.getHRVisitLogPK().getGuest());
        
        potrjujemSeznanjenost = find.isAgreeWithTerms();
        podpisValue = find.getSignature();
        }
    }

    public void podpisObiskovalca() {
        System.out.println("Sem v podpisObiskovalca()");
        System.out.println("Podpis:" + podpisValue);
        System.out.println("Potrdil:" + potrjujemSeznanjenost);
        
        HRVisitLogPK visitLogPK = new HRVisitLogPK();
        visitLogPK.setGuest(selected.getVCodeksObiskovalciPK().getGuest());
        visitLogPK.setVisitID(selected.getVCodeksObiskovalciPK().getVisitId());
        
        HRVisitLog find = visitLogFacade.find(visitLogPK);
        System.out.println("FIND:"+find.getHRVisitLogPK().getGuest());
        find.setSignature(podpisValue);
        find.setAgreeWithTerms(potrjujemSeznanjenost);
        visitLogFacade.edit(find);
        
    }

    public void izberiPovezaneNajave() {
        //dopisano za select multiple
        //selected = selectedList.get(0);
        //--------
        System.out.println("Sem v izberiPovezaneNajave(), VisitID:" + selected.getVCodeksObiskovalciPK().getVisitId());
        selectedList = new ArrayList();
        for (VCodeksObiskovalci item : items) {
            if (item.getVCodeksObiskovalciPK().getVisitId() == selected.getVCodeksObiskovalciPK().getVisitId()) {
                System.out.println("Našel povezanega, dodajam na seznam");
                selectedList.add(item);
            }
        }
        System.out.println("Velikost arraya po for zanki:" + selectedList.size());
        if (selectedList.size() > 1) {
            FacesMessage msg = new FacesMessage("Izbranih " + selectedList.size() + " povezanih najav", "So povezane");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Ni povezanih najav", "Niso povezane");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void dogodekObPrihoduGosta() {
        System.out.println("Sem v dogodekObPrihoduGosta()");
        //Sedaj se zapiše vnos v HRVisitLog tabelo
        HRVisitLog visitLog = new HRVisitLog();
        HRVisitLogPK visitLogPK = new HRVisitLogPK();

        visitLogPK.setGuest(selected.getVCodeksObiskovalciPK().getGuest());
        visitLogPK.setVisitID(selected.getVCodeksObiskovalciPK().getVisitId());
        visitLog.setTimeIn(new Date());

        visitLog.setHRVisitLogPK(visitLogPK);
        visitLogFacade.edit(visitLog);
        //konec dela z HRVisitLog tabelo
        //Sedaj je potrebno pridobiti podatke o naročniku iz codeksa, da dobimo mail in telefon za obveščanje
        System.out.println("V codeksu iščem uporabnika z IDjem:" + selected.getForuserid());
        VADCODEKSUsers personForWhoVisitIs = codeksFacade.findUserByCodeksId(selected.getForuserid());

        if (personForWhoVisitIs.getMobile() != null && !personForWhoVisitIs.getMobile().isEmpty()) {
            hasMobile = true;
        } else {
            hasMobile = false;
        }

        if (personForWhoVisitIs.getMail() != null && !personForWhoVisitIs.getMail().isEmpty()) {
            hasMail = true;
        } else {
            hasMail = false;
        }

        

    }

    public void sendSMS(String message, String mobilephone) {
        try {
            URL url = new URL("http://10.36.70.238:5000/api/v1.0/sms/outgoing");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST"); // PUT is another valid option

            http.setDoOutput(true);

            String json = "{\"text\":\"" + message + "\",\"mobiles\":[\"" + mobilephone + "\"]}";
            System.out.println("JSON: " + json);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            //http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            
            String auth = "odelo" + ":" + 123;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            http.setRequestProperty("Authorization", authHeaderValue);
            
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out, 0, length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(VCodeksObiskovalciController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VCodeksObiskovalciController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMailAnketa(){
        System.out.println("Sem v sendMailAnketa(), pošiljam na:"+anketaMailGost);
        VADCODEKSUsers personForWhoVisitIs = codeksFacade.findUserByCodeksId(selected.getForuserid());
        //String mail = personForWhoVisitIs.getMail();
        String mail = anketaMailGost;
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Pozdravljeni "+selected.getVCodeksObiskovalciPK().getGuest()+"");
        sb.append("<br/>");
        sb.append("Pošiljamo vam povezavo do ankete o ogljičnem odtisu (Carbon Footprint). Reševanje ankete je prostovoljno, anketa je anonimna.");
        sb.append("<br/>");
        sb.append("<a href='https://forms.office.com/Pages/ResponsePage.aspx?id=MwBzODf4LkSUXc8ilmG5pgX8iyIc74NAned2Z1g3o6dURFAxQVpRSlRXNEYwT1owSVlWSEw1MFNJNS4u&wdLOR=cFC860A44-1D22-4DD3-9840-858CBC378190'>POVEZAVA DO ANKETE</a>");
        sb.append("<br/>");
        sb.append("<small>Prosim da na to sporočilo ne odgovarjate saj je generirano avtomatsko.</small>");
        
        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Anketa o ogljičnem odtisu");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("NAPAKA!",  "Mail ni bil poslan") );
            System.out.println("Napaka mail send:" + ex);
        }
        
    }

    public void sendSMS() {
        System.out.println("Sem v sendSMS()");
        //ta metoda pripravi vsebino SMS in številko ter jo posreduje metodi s parametri
        VADCODEKSUsers personForWhoVisitIs = codeksFacade.findUserByCodeksId(selected.getForuserid());
        String message = "Obiskovalec "+selected.getVCodeksObiskovalciPK().getGuest()+"("+selected.getCompany()+") vas caka na recepciji. Prosim prevzemite ga.";
        String origSt = personForWhoVisitIs.getMobile();
        origSt = origSt.replaceAll("\\s+","");
        String newstevilka = origSt.replaceAll("\\+386", "00386");
        System.out.println(newstevilka);
        //naslednjo metodo odkomentiraj na produkciji
        sendSMS(message, newstevilka);
        //sendSMS(message, "0038651357933"); //DEAN ŠTEVILKA ZA TEST
    }
    
    public void sendMail(){
        VADCODEKSUsers personForWhoVisitIs = codeksFacade.findUserByCodeksId(selected.getForuserid());
        String mail = personForWhoVisitIs.getMail();
        //String mail = "Dean.Matjasic@odelo.si";
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("Obiskovalec "+selected.getVCodeksObiskovalciPK().getGuest()+" ("+selected.getCompany()+") vas čaka na recepciji.");
        sb.append("<br/>");
        sb.append("Prosim da obiskovalca sprejmete na recepciji. Ob sprejemu boste potrebovali svoj RFID ključek.");
        sb.append("<br/>");
        sb.append("<br/>");
        sb.append("<small>Prosim da na to sporočilo ne odgovarjate saj je generirano avtomatsko.</small>");
        
        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Prihod gosta na recepcijo");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mail);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage("NAPAKA!",  "Mail ni bil poslan") );
            System.out.println("Napaka mail send:" + ex);
        }
    }

    public void sprejmiObiskovalca() {
        System.out.println("Sem v sprejmiObiskovalca");
        System.out.println("Vpisan RFID kljuc:"+rfidKey);
        

        HRVisitLogPK visitLogPK = new HRVisitLogPK();
        visitLogPK.setGuest(selected.getVCodeksObiskovalciPK().getGuest());
        visitLogPK.setVisitID(selected.getVCodeksObiskovalciPK().getVisitId());
        
        HRVisitLog find = visitLogFacade.find(visitLogPK);
        find.setOdeloPersonRecipient(Integer.parseInt(rfidKey));
        visitLogFacade.edit(find);
    }

    public void narocnikSprejmeGosta() {
        System.out.println("Sem v narocnikSprejmeGosta");
    }

    public void zabeleziOdhodGosta() {
        System.out.println("Sem v zabeleziOdhodGosta");
        HRVisitLogPK visitLogPK = new HRVisitLogPK();
        visitLogPK.setGuest(selected.getVCodeksObiskovalciPK().getGuest());
        visitLogPK.setVisitID(selected.getVCodeksObiskovalciPK().getVisitId());
        
        HRVisitLog find = visitLogFacade.find(visitLogPK);

        find.setTimeOut(new Date());
        visitLogFacade.edit(find);
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

    public List<VCodeksObiskovalci> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<VCodeksObiskovalci> selectedList) {
        this.selectedList = selectedList;
    }

    public String getRfidKey() {
        return rfidKey;
    }

    public void setRfidKey(String rfidKey) {
        this.rfidKey = rfidKey;
    }

    public boolean isHasMail() {
        return hasMail;
    }

    public void setHasMail(boolean hasMail) {
        this.hasMail = hasMail;
    }

    public boolean isHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(boolean hasMobile) {
        this.hasMobile = hasMobile;
    }

    public String getAnketaMailGost() {
        return anketaMailGost;
    }

    public void setAnketaMailGost(String anketaMailGost) {
        this.anketaMailGost = anketaMailGost;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    

    public VCodeksObiskovalci prepareCreate() {
        selected = new VCodeksObiskovalci();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleCodeksObiskovalci").getString("VCodeksObiskovalciCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleCodeksObiskovalci").getString("VCodeksObiskovalciUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleCodeksObiskovalci").getString("VCodeksObiskovalciDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<VCodeksObiskovalci> getItems() {
        if (items == null) {
            //items = getFacade().findAll();
            items = getFacade().findAllVisitsForToday();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCodeksObiskovalci").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleCodeksObiskovalci").getString("PersistenceErrorOccured"));
            }
        }
    }

    public VCodeksObiskovalci getVCodeksObiskovalci(int id) {
        return getFacade().find(id);
    }

    public List<VCodeksObiskovalci> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VCodeksObiskovalci> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = VCodeksObiskovalci.class)
    public static class VCodeksObiskovalciControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VCodeksObiskovalciController controller = (VCodeksObiskovalciController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vCodeksObiskovalciController");
            return controller.getVCodeksObiskovalci(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VCodeksObiskovalci) {
                VCodeksObiskovalci o = (VCodeksObiskovalci) object;
                //return getStringKey(o.getVisitId());
                return getStringKey(o.getVCodeksObiskovalciPK().getVisitId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VCodeksObiskovalci.class.getName()});
                return null;
            }
        }

    }

}
