/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.VCodeksUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.*;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

//import com.odelowebapp.uporabnik.codeks.entity.Users;
//import com.odelowebapp.uporabnik.codeks.facade.UsersFacade;
/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class HRCourseIzvedbaOdredbeBean implements Serializable {

    @EJB
    private HRCourseInstructorFacade instructorFacade;
    @EJB
    private HRCourseFacade courseFacade;
    @EJB
    private HRCourseHRCourseInstructorFacade joinFacade;
    @EJB
    private HRCourseOfferingFacade cofferingFacade;
    @EJB
    private HRCourseAttendanceFacade attendanceFacade;
    //@EJB
    //private UsersFacade codeksFacade;
    //private HRvcodeksusersFacade codeksFacade;
    JantarPINConvert jpc = new JantarPINConvert();

    @EJB
    private HRvcodeksusersFacade usersEJB;

    private List<HRCourseAttendance> seznamUdelezencev;
    private HRCourseAttendance udelezenecZaBrisanje;

    private List<HRCourseOffering> allOfferings;
    private HRCourseOffering selectedOffering;

    private String kljucekStringBelezenje;
    private String izbrancitalec;
    private boolean kinenabled;
    private List<String> kljucekStringBelezenjeChips = new ArrayList<String>();

    private VCodeksUsersAktualniZaposleni rocnoIzbranUser;
    private List<VCodeksUsersAktualniZaposleni> currentlyEmployed;

    //List<Users> userji;
    HashMap<Integer, VCodeksUsers> userjiHM;
    HashMap<Integer, VCodeksUsersAktualniZaposleni> vuserjiHM;

    private VCodeksUsersAktualniZaposleni usernotonthelist;

    private int activeIndex = 0;

    private String inputPIN;
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v HRCourseIzvedbaOdredbeBean");

        allOfferings = cofferingFacade.findAllOdredbeInFuture(10);
        System.out.println("Odredb v bazi v naslednjih 10 dneh:" + allOfferings.size());

        currentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();

        if (allOfferings.isEmpty()) {
            showModalNoOfferings();
        }

        kinenabled = false;

    }

    private String itemSelected;
    private String itemUnselected;
    private List<String> items = new ArrayList();
    private Map<String, VCodeksUsersAktualniZaposleni> udelezenciMap = new LinkedHashMap();
    private Map<String, Date> udelezenciMapTimestamps = new LinkedHashMap();

    public void chipsSelect(SelectEvent e) {
        System.out.println("chip selected" + e.getObject().toString());
        //this.setItemSelected(e.getObject().toString()); DELALO PREJ, VNESE PREBRANO CIFRO V CHIP
        //items.add(e.getObject().toString());
        System.out.println("items size afteR chipselected " + items.size());
        System.out.println("kljucekStringBelezenjeChips size afteR chipselected " + kljucekStringBelezenjeChips.size());

        VCodeksUsersAktualniZaposleni prebran;
        String chip = e.getObject().toString();
        if (izbrancitalec.equals("emcard")) {
            String k = jpc.pretvoriKljucekVJantarOblikoPublic(chip);
            //prebran = codeksFacade.getUsersByCard(k);
            prebran = usersEJB.findUserByCard(k);
        } else {
            prebran = usersEJB.findUserByCard(chip);
        }

        System.out.println("Prebran user:" + prebran.getId());
        udelezenciMap.put(chip, prebran);
        udelezenciMapTimestamps.put(prebran.getCard(), new Date());
        this.setItemSelected(prebran.getLastname() + " " + prebran.getFirstname());
        items.add(prebran.getLastname() + " " + prebran.getFirstname());
        //kljucekStringBelezenjeChips.add("(" + chip + ")" + prebran.getLastname() + " " + prebran.getFirstname());
        kljucekStringBelezenjeChips.add(prebran.getLastname() + " " + prebran.getFirstname());
        kljucekStringBelezenjeChips.remove(chip);
    }

    public void chipsDeselect(UnselectEvent e) {
        System.out.println("chip deselect" + e.getObject().toString() + " " + items.size());
        this.setItemUnselected(e.getObject().toString());
        udelezenciMap.remove(e.getObject().toString());

    }

    public void changeActiveIndex() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.activeIndex = Integer.valueOf(params.get("index"));
    }

    public void rocnoIzbranUserAjax() {
        System.out.println("Sem v rocnoIzbranUserAjax, izbran:" + rocnoIzbranUser.getFirstname()+ " persons card:"+rocnoIzbranUser.getCard());
        //kljucekStringBelezenje = rocnoIzbranUser.getCard();
        System.out.println("kljucekStringBelezenjeChips size before adding item into it:"+kljucekStringBelezenjeChips.size());
        kljucekStringBelezenjeChips.add(rocnoIzbranUser.getCard());
        System.out.println("kljucekStringBelezenjeChips size after adding item into it:"+kljucekStringBelezenjeChips.size());

        VCodeksUsersAktualniZaposleni prebran;
        String chip = rocnoIzbranUser.getCard();
        prebran = usersEJB.findUserByCard(chip);

        System.out.println("Prebran user:" + prebran.getId());
        udelezenciMap.put(chip, prebran);
        //this.setItemSelected(prebran.getLastname() + " " + prebran.getFirstname());
        items.add(prebran.getLastname() + " " + prebran.getFirstname());
        //kljucekStringBelezenjeChips.add("(" + chip + ")" + prebran.getLastname() + " " + prebran.getFirstname());
        kljucekStringBelezenjeChips.add(prebran.getLastname() + " " + prebran.getFirstname());
        kljucekStringBelezenjeChips.remove(chip);

    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        HRCourseAttendance entity = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{car}", HRCourseAttendance.class);

        System.out.println("Objekt id:" + entity.getAttendanceID());

        attendanceFacade.edit(entity);

        if (newValue != null && !newValue.equals(oldValue)) {
            //System.out.println("Old value:"+oldValue.toString()+" New value:"+newValue.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void readerChange() {
        System.out.println("Sem v readerChange");
        System.out.println("Izbran citalec:" + izbrancitalec);
        if (izbrancitalec != null) {
            kinenabled = true;
        } else {
            kinenabled = false;
        }
        //RequestContext.getCurrentInstance().execute("document.getElementById('form2:kin').focus(); $('#kin').focus();");
        //PrimeFaces.current().executeScript("document.getElementById('form2:kin2').focus(); $('#kin2').focus();");
        PrimeFaces.current().focus("form2:kin2");

    }

    public void shraniZabelezenKljucek() {
        System.out.println("Sem v shraniZabelezenKljucek");
        System.out.println("Vpisan Kljucek:" + kljucekStringBelezenje + " osebo je treba zabeleziti na courseofferingu:" + selectedOffering.getCourseOfferingID());
        VCodeksUsersAktualniZaposleni prebran;
        if (izbrancitalec.equals("emcard")) {
            String k = jpc.pretvoriKljucekVJantarOblikoPublic(kljucekStringBelezenje);
            //prebran = codeksFacade.getUsersByCard(k);
            prebran = usersEJB.findUserByCard(k);
        } else {
            prebran = usersEJB.findUserByCard(kljucekStringBelezenje);
        }

        System.out.println("Prebran user:" + prebran.getId());

        boolean founduser = false;
        for (HRCourseAttendance u : seznamUdelezencev) {
            if (prebran.getId() == u.getCodeksID()) {
                founduser = true;
                u.setWasAttended(true);
                attendanceFacade.edit(u);
            }
        }

        if (!founduser) {
            //user that attended is not on the list of attendees
            usernotonthelist = prebran;
            //RequestContext.getCurrentInstance().execute("$(\"#niPrijavljenUporabnik\").modal();");
            PrimeFaces.current().executeScript("$(\"#niPrijavljenUporabnik\").modal('show');");
        }

        kljucekStringBelezenje = "";
    }
    
//    public void manualSelectionOfEmployees(){
//        System.out.println("Sem v manualSelectionOfEmployees. Zdaj pokličem shraniZabelezenKljucekChips in odprem nov");
//        shraniZabelezenKljucekChips();
//        PrimeFaces.current().executeScript("$('#skenirajKljucek').modal('hide');$('#izbiraOseb').modal('show');");
//    }
    
    public void manualSelectionOfEmployees(){
        System.out.println("Sem v manualSelectionOfEmployees. Zdaj pokličem shraniZabelezenKljucekChips in odprem nov");
        if(getKljucekStringBelezenjeChips() == null){
            System.out.println("kljucekStringBelezenjeChips je NULL");
        }else{
            System.out.println("kljucekStringBelezenjeChips ni NULL");
            System.out.println("Koliko je že chipov? "+kljucekStringBelezenjeChips.size());
            shraniZabelezenKljucekChips();
        }
        
        
       // selectedEmployees.clear(); // počistimo že prej izbrane osebe
        PrimeFaces.current().executeScript("$('#skenirajKljucek').modal('hide');$('#izbiraOseb').modal('show');");      
    }
    
    private List<VCodeksUsersAktualniZaposleni> selectedEmployees;
    public void shraniIzbraneOsebe(){
        System.out.println("Sem v shraniIzbraneOsebe()");
        System.out.println("Izbranih je oseb:"+selectedEmployees.size());
        
        for (VCodeksUsersAktualniZaposleni prebran : selectedEmployees) {
            System.out.println("Prebran user:" + prebran.getId());

            boolean founduser = false;
            for (HRCourseAttendance u : seznamUdelezencev) {
                if (prebran.getId() == u.getCodeksID()) {
                    founduser = true;
                    u.setWasAttended(true);
                    u.setTimestamp(new Date());
                    attendanceFacade.edit(u);
                }
            }

            if (!founduser) {
                //user that attended is not on the list of attendees
                usernotonthelist = prebran;
                //PrimeFaces.current().executeScript("$(\"#niPrijavljenUporabnik\").modal('show');");
                shraniNeprijavljenega("");
            }
        }
        selectedEmployees.clear();  
    }

    public void shraniZabelezenKljucekChips() {
        System.out.println("Sem v shraniZabelezenKljucekChips");
        System.out.println("Vpisanih kljuckov:" + kljucekStringBelezenjeChips.size() + " osebo je treba zabeleziti na courseofferingu:" + selectedOffering.getCourseOfferingID());

        for (VCodeksUsersAktualniZaposleni prebran : udelezenciMap.values()) {
//            if (izbrancitalec.equals("emcard")) {
//                String k = jpc.pretvoriKljucekVJantarOblikoPublic(chip);
//                //prebran = codeksFacade.getUsersByCard(k);
//                prebran = usersEJB.findUserByCard(k);
//            } else {
//                prebran = usersEJB.findUserByCard(chip);
//            }

            System.out.println("Prebran user:" + prebran.getId());

            boolean founduser = false;
            for (HRCourseAttendance u : seznamUdelezencev) {
                if (prebran.getId() == u.getCodeksID()) {
                    founduser = true;
                    u.setWasAttended(true);
                    //u.setTimestamp(new Date());
                    u.setTimestamp(udelezenciMapTimestamps.get(prebran.getCard()));
                    attendanceFacade.edit(u);
                }
            }

            if (!founduser) {
                //user that attended is not on the list of attendees
                usernotonthelist = prebran;

                //PrimeFaces.current().executeScript("$(\"#niPrijavljenUporabnik\").modal('show');");
                shraniNeprijavljenega("");
            }
        }//end for

//        for (String chip: kljucekStringBelezenjeChips) {
//            VCodeksUsersAktualniZaposleni prebran;
//            if (izbrancitalec.equals("emcard")) {
//                String k = jpc.pretvoriKljucekVJantarOblikoPublic(chip);
//                //prebran = codeksFacade.getUsersByCard(k);
//                prebran = usersEJB.findUserByCard(k);
//            } else {
//                prebran = usersEJB.findUserByCard(chip);
//            }
//
//            System.out.println("Prebran user:" + prebran.getId());
//
//            boolean founduser = false;
//            for (HRCourseAttendance u : seznamUdelezencev) {
//                if (prebran.getId() == u.getCodeksID()) {
//                    founduser = true;
//                    u.setWasAttended(true);
//                    u.setTimestamp(new Date());
//                    attendanceFacade.edit(u);
//                }
//            }
//
//            if (!founduser) {
//                //user that attended is not on the list of attendees
//                usernotonthelist = prebran;
//                
//                //PrimeFaces.current().executeScript("$(\"#niPrijavljenUporabnik\").modal('show');");
//                shraniNeprijavljenega("");
//            }
//        }//end for
        kljucekStringBelezenje = "";
        kljucekStringBelezenjeChips.clear();
        izbrancitalec = null;
        kinenabled = false;
        attendanceFacade.doRefresh();
    }

    public void offeringSelected() {
        System.out.println("Sem v offeringSelected():" + selectedOffering.toString());
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println("Person selecting:" + currentUser.getSession().getAttribute("codeksid") + " must be:" + selectedOffering.getInstructorID().getCodeksID());

        if (currentUser.getSession().getAttribute("codeksid").equals(selectedOffering.getInstructorID().getCodeksID() + "")) {
            seznamUdelezencev = attendanceFacade.findAllPersonsForCourse(selectedOffering);
            System.out.println("Za ta Course obstaja udeležencev:" + seznamUdelezencev.size());
            if (seznamUdelezencev.size() > 0) {
                List<Integer> idjiuserjev = new ArrayList();
                for (HRCourseAttendance a : seznamUdelezencev) {
                    idjiuserjev.add(a.getCodeksID());
                }
                //userjiHM = codeksFacade.findCodeksUsersByIDsHashMap(idjiuserjev);
                vuserjiHM = usersEJB.zaposleniZaAndroidAplikacijoHM(idjiuserjev);

            }
            System.out.println("Za izbrani course pridobim še priloge");
            selectedOffering.getCourseID().findAttachmentsForCourse(selectedOffering.getCourseID().getCourseID());
            selectedOffering.findAttachmentsForCourse(selectedOffering.getCourseOfferingID());
        } else {
            //Show POPUP
            selectedBackup = selectedOffering;
            selectedOffering=null;
            inputPIN = "";
            PrimeFaces.current().executeScript("$(\"#accessPIN\").modal();");
        }

    }
    
    HRCourseOffering selectedBackup;
    public void accessWithPIN(){
        System.out.println("Sem v accessWithPIN(), pin inputted:"+inputPIN);
        
        if(inputPIN.equals(selectedBackup.getAccessPIN()+"")){
            selectedOffering = selectedBackup;
            seznamUdelezencev = attendanceFacade.findAllPersonsForCourse(selectedOffering);
            System.out.println("Za ta Course obstaja udeležencev:" + seznamUdelezencev.size());
            if (seznamUdelezencev.size() > 0) {
                List<Integer> idjiuserjev = new ArrayList();
                for (HRCourseAttendance a : seznamUdelezencev) {
                    idjiuserjev.add(a.getCodeksID());
                }
                //userjiHM = codeksFacade.findCodeksUsersByIDsHashMap(idjiuserjev);
                vuserjiHM = usersEJB.zaposleniZaAndroidAplikacijoHM(idjiuserjev);

            }
            System.out.println("Za izbrani course pridobim še priloge");
            selectedOffering.getCourseID().findAttachmentsForCourse(selectedOffering.getCourseID().getCourseID());
            selectedOffering.findAttachmentsForCourse(selectedOffering.getCourseOfferingID());
        }else{
            //napačen pin
            System.out.println("Vpisan napačen PIN predmeta");
            PrimeFaces.current().executeScript("$(\"#accessPIN\").modal('hide');");
            //PrimeFaces.current().executeScript("$(\"#accessPINwrong\").modal();");
            PrimeFaces.current().executeScript("$(\"#myModal\").modal();");
        }
        
    }

    public void shraniNeprijavljenega(String comment) {
        System.out.println("Sem v shrani neprijavljenega. Shraniti je treba userja:" + usernotonthelist.getId());
        HRCourseAttendance nov = new HRCourseAttendance();
        nov.setCodeksID(usernotonthelist.getId());
        nov.setWasAttended(true);
        nov.setCourseOfferingID(selectedOffering);
        nov.setComment(comment);
        //nov.setTimestamp(new Date());
        Date izMapa = udelezenciMapTimestamps.get(usernotonthelist.getCard());
        System.out.println("Date iz mapa:"+izMapa);
        if(izMapa == null){
            nov.setTimestamp(new Date());
        }else{
            nov.setTimestamp(udelezenciMapTimestamps.get(usernotonthelist.getCard()));
        }
        //nov.setTimestamp(udelezenciMapTimestamps.get(usernotonthelist.getCard()));

        attendanceFacade.create(nov);
        seznamUdelezencev = attendanceFacade.findAllPersonsForCourse(selectedOffering);
        List<Integer> idjiuserjev = new ArrayList();
        for (HRCourseAttendance a : seznamUdelezencev) {
            idjiuserjev.add(a.getCodeksID());
        }
        vuserjiHM = usersEJB.zaposleniZaAndroidAplikacijoHM(idjiuserjev);
    }

    public void neshraniNeprijavljenega() {
        System.out.println("Sem v NE shrani neprijavljenega");
        usernotonthelist = null;
    }

    public int getSteviloUdelezencevRegistriranih() {
        int stevec = 0;
        for (HRCourseAttendance a : seznamUdelezencev) {
            if (a.getWasAttended()) {
                stevec++;
            }
        }
        return stevec;
    }
    
    public void brisiUdelezenca(){
        System.out.println("Sem v brisiUdelezenca()");
        //seznamUdelezencev.remove(udelezenecZaBrisanje);
        attendanceFacade.remove(udelezenecZaBrisanje);
        attendanceFacade.doRefresh();
        seznamUdelezencev = attendanceFacade.findAllPersonsForCourse(selectedOffering);
    }

    public void showModalNoOfferings() {
        //RequestContext.getCurrentInstance().execute("$(\"#niOfferingov\").modal();");
        PrimeFaces.current().executeScript("$(\"#niOfferingov\").modal();");
    }

    public List<HRCourseOffering> getAllOfferings() {
        return allOfferings;
    }

    public void setAllOfferings(List<HRCourseOffering> allOfferings) {
        this.allOfferings = allOfferings;
    }

    public HRCourseOffering getSelectedOffering() {
        return selectedOffering;
    }

    public void setSelectedOffering(HRCourseOffering selectedOffering) {
        this.selectedOffering = selectedOffering;
    }

    public List<HRCourseAttendance> getSeznamUdelezencev() {
        return seznamUdelezencev;
    }

    public void setSeznamUdelezencev(List<HRCourseAttendance> seznamUdelezencev) {
        this.seznamUdelezencev = seznamUdelezencev;
    }

    public String getKljucekStringBelezenje() {
        return kljucekStringBelezenje;
    }

    public void setKljucekStringBelezenje(String kljucekStringBelezenje) {
        this.kljucekStringBelezenje = kljucekStringBelezenje;
    }

    public String getIzbrancitalec() {
        return izbrancitalec;
    }

    public void setIzbrancitalec(String izbrancitalec) {
        this.izbrancitalec = izbrancitalec;
    }

    public boolean isKinenabled() {
        return kinenabled;
    }

    public void setKinenabled(boolean kinenabled) {
        this.kinenabled = kinenabled;
    }

    public HashMap<Integer, VCodeksUsers> getUserjiHM() {
        return userjiHM;
    }

    public void setUserjiHM(HashMap<Integer, VCodeksUsers> userjiHM) {
        this.userjiHM = userjiHM;
    }

    public HashMap<Integer, VCodeksUsersAktualniZaposleni> getVuserjiHM() {
        return vuserjiHM;
    }

    public void setVuserjiHM(HashMap<Integer, VCodeksUsersAktualniZaposleni> vuserjiHM) {
        this.vuserjiHM = vuserjiHM;
    }

    public VCodeksUsersAktualniZaposleni getUsernotonthelist() {
        return usernotonthelist;
    }

    public void setUsernotonthelist(VCodeksUsersAktualniZaposleni usernotonthelist) {
        this.usernotonthelist = usernotonthelist;
    }

    public VCodeksUsersAktualniZaposleni getRocnoIzbranUser() {
        return rocnoIzbranUser;
    }

    public void setRocnoIzbranUser(VCodeksUsersAktualniZaposleni rocnoIzbranUser) {
        this.rocnoIzbranUser = rocnoIzbranUser;
    }

    public List<VCodeksUsersAktualniZaposleni> getCurrentlyEmployed() {
        return currentlyEmployed;
    }

    public void setCurrentlyEmployed(List<VCodeksUsersAktualniZaposleni> currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<String> getKljucekStringBelezenjeChips() {
        return kljucekStringBelezenjeChips;
    }

    public void setKljucekStringBelezenjeChips(List<String> kljucekStringBelezenjeChips) {
        this.kljucekStringBelezenjeChips = kljucekStringBelezenjeChips;
    }

    public String getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(String itemSelected) {
        this.itemSelected = itemSelected;
        System.out.println("Sem v setItemSelected:" + itemSelected);
    }

    public String getItemUnselected() {
        return itemUnselected;
    }

    public void setItemUnselected(String itemUnselected) {
        this.itemUnselected = itemUnselected;
    }

    public String getInputPIN() {
        return inputPIN;
    }

    public void setInputPIN(String inputPIN) {
        this.inputPIN = inputPIN;
    }

    public List<VCodeksUsersAktualniZaposleni> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<VCodeksUsersAktualniZaposleni> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public HRCourseAttendance getUdelezenecZaBrisanje() {
        return udelezenecZaBrisanje;
    }

    public void setUdelezenecZaBrisanje(HRCourseAttendance udelezenecZaBrisanje) {
        this.udelezenecZaBrisanje = udelezenecZaBrisanje;
    }
    
    

    public void change() {
        System.out.println("Sem v change()");

    }

    public void valuechange() {
        System.out.println("Sem v valuechange()");
    }

}
