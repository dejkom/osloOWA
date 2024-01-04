/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.*;
import com.odelowebapp.HR.facade.*;
import java.io.IOException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class OpenCourseOfferingBean implements Serializable {

    @EJB
    private HRCourseInstructorFacade instructorFacade;
    @EJB
    private HRCourseFacade courseFacade;
    @EJB
    private HRCourseOfferingFacade courseOfferingFacade;
    @EJB
    private HRCourseHRCourseInstructorFacade joinFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private HRCourseTypeFacade typesEJB;

    private HRCourse selectedCourse;
    private HRCourseInstructor selectedInstructor;

    private HRCourse newCourse;
    private HRCourseOffering newCourseOffering;

    private List<HRCourseInstructor> instruktorjiNaVoljo = new ArrayList();
    private VCodeksUsersAktualniZaposleni kreator;
    private String kreatorString;
    private List<izvedbaEasy> izvedbe = new ArrayList();
    private izvedbaEasy novaIzvedba;
    
    private int pinIn;
    
    private List<VCodeksUsersAktualniZaposleni> aktualniZaposleni;
    private VCodeksUsersAktualniZaposleni izbranZaposleni;
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();
    
    private HRCourseType tip;

    @PostConstruct
    public void init() {
        System.out.println("Sem v OpenCourseOfferingBean Beanu");
        String codeksIDprijavljenega = "";
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser.getSession().getAttribute("username")) {
            codeksIDprijavljenega = currentUser.getSession().getAttribute("codeksid") + "";
        } else {
            //obiskovalec
            codeksIDprijavljenega = "";
        }

        System.out.println("Oseba ki kreira izobraževanje ima codeksid:" + codeksIDprijavljenega);
        kreator = usersEJB.findUserByCodeksId(Integer.parseInt(codeksIDprijavljenega));
        kreatorString = kreator.getLastname() + " " + kreator.getFirstname();

        tip = typesEJB.find(3);
        

        System.out.println("Določen tip: " + tip.getCourseTypeTitle());
        
        newCourse = new HRCourse();
        newCourse.setCourseTypeID(tip);
        
        aktualniZaposleni = usersEJB.zaposleniZaAndroidAplikacijo();
        
        novaIzvedba = new izvedbaEasy();
    }
    
    public void brisiSliko(String index){
        System.out.println("Sem v brisiSliko("+index+")");
        newCourse.getFiles().remove(Integer.parseInt(index));
    }
    
//    public void handleFileUpload(FileUploadEvent event) {
//        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for course:"+newCourse.getCourseID()+" files allready there:"+newCourse.getFiles().size());
//        //files.add(event.getFile());
//        files = newCourse.getFiles();
//        files.add(new PrimefacesUploadedFile(event.getFile()));
//        newCourse.setFiles(files);
//        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
//        FacesContext.getCurrentInstance().addMessage(null, message);
//    }
    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for course:"+newCourse.getCourseID()+" files allready there:"+newCourse.getFiles().size());
        files = newCourse.getFiles();
        files.add(new PrimefacesUploadedFile(event.getFile()));
        newCourse.setFiles(files);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Files size:"+newCourse.getFiles().size());
    }

    public void buttonActionDodajIzvedbo() {
        System.out.println("Sem v buttonActionDodajIzvedbo, sedaj moram po korakih shranjevati v bazo");
        newCourse.setPrimaryInstructors(kreatorString);
        newCourse.setCourseTypeID(tip);
        courseFacade.create(newCourse);
        
        for(izvedbaEasy iz:izvedbe){

            if(instructorFacade.findIfCodeksUserisCourseInstructor(iz.getWho()).isEmpty()){
                System.out.println("Osebo moram shraniti med instructorje");
                HRCourseInstructor novinstructor = new HRCourseInstructor();
                novinstructor.setActive(true);
                novinstructor.setCodeksID(iz.getWho().getId());
                novinstructor.setFirstname(iz.getWho().getFirstname());
                novinstructor.setLastname(iz.getWho().getLastname());
                novinstructor.setCompany("odelo Slovenija");
                
                instructorFacade.create(novinstructor);
                
                HRCourseHRCourseInstructor linkinstructorcourse = new HRCourseHRCourseInstructor();
                linkinstructorcourse.setCourseID(newCourse);
                linkinstructorcourse.setInstructorID(novinstructor);
                joinFacade.create(linkinstructorcourse);
                               
            }else{
                System.out.println("Osebe ne shranim med instructorje, je že notri. Shranim pa jo v join tabelo");
                HRCourseInstructor obstojecinstruktor = instructorFacade.findInstructorByCodeksId(iz.getWho().getId());
                               
                HRCourseHRCourseInstructor linkinstructorcourse = new HRCourseHRCourseInstructor();
                linkinstructorcourse.setCourseID(newCourse);
                linkinstructorcourse.setInstructorID(obstojecinstruktor);
                joinFacade.create(linkinstructorcourse);
            }
            
            HRCourseOffering co = new HRCourseOffering();
            co.setActive(Boolean.TRUE);
            co.setCourseDate(iz.getWhen());
            co.setCourseID(newCourse);
            co.setCreator(kreator.getId());
            co.setDateCreated(new Date());
            co.setInstructorID(instructorFacade.findInstructorByCodeksId(iz.getWho().getId()));
            co.setMeetingroomString(iz.getWhere());
            co.setAccessPIN(pinIn);
            courseOfferingFacade.create(co);
                        
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Izvedba izobraževanja je bila uspešno ustvarjena."));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            System.out.println("Redirect:"+((HttpServletRequest) ec.getRequest()).getRequestURI());
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(OpenCourseOfferingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public void buttonkdajkjekdo() {
        System.out.println("Sem v buttonkdajkjekdo ustvarim prazno izvedbo");
        //novaIzvedba = new izvedbaEasy();
        
    }
    
    public void izbranZaposleniEvent(){
        System.out.println("sem v izbranZaposleniEvent()");
    }
    
    public void dialog1SubmitButton(){
        System.out.println("Sem v dialog1SubmitButton, osebo dodajem na seznam");
        izvedbe.add(novaIzvedba);
        novaIzvedba = new izvedbaEasy();
        
    }

    public void courseSelected() {
        System.out.println("Sem v courseSelected!");
        System.out.println("Selected course:" + selectedCourse.getCourseID());

        List<HRCourseHRCourseInstructor> joins = joinFacade.findAllInstructorsForCourse(selectedCourse);
        for (HRCourseHRCourseInstructor i : joins) {
            instruktorjiNaVoljo.add(i.getInstructorID());
        }

    }

    public HRCourse getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(HRCourse selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public HRCourseInstructor getSelectedInstructor() {
        return selectedInstructor;
    }

    public void setSelectedInstructor(HRCourseInstructor selectedInstructor) {
        this.selectedInstructor = selectedInstructor;
    }

    public List<HRCourseInstructor> getInstruktorjiNaVoljo() {
        return instruktorjiNaVoljo;
    }

    public void setInstruktorjiNaVoljo(List<HRCourseInstructor> instruktorjiNaVoljo) {
        this.instruktorjiNaVoljo = instruktorjiNaVoljo;
    }

    public HRCourse getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(HRCourse newCourse) {
        this.newCourse = newCourse;
    }

    public HRCourseOffering getNewCourseOffering() {
        return newCourseOffering;
    }

    public void setNewCourseOffering(HRCourseOffering newCourseOffering) {
        this.newCourseOffering = newCourseOffering;
    }

    public VCodeksUsersAktualniZaposleni getKreator() {
        return kreator;
    }

    public void setKreator(VCodeksUsersAktualniZaposleni kreator) {
        this.kreator = kreator;
    }

    public String getKreatorString() {
        return kreatorString;
    }

    public void setKreatorString(String kreatorString) {
        this.kreatorString = kreatorString;
    }

    public List<izvedbaEasy> getIzvedbe() {
        return izvedbe;
    }

    public void setIzvedbe(List<izvedbaEasy> izvedbe) {
        this.izvedbe = izvedbe;
    }

    public izvedbaEasy getNovaIzvedba() {
        return novaIzvedba;
    }

    public void setNovaIzvedba(izvedbaEasy novaIzvedba) {
        this.novaIzvedba = novaIzvedba;
    }

    public List<VCodeksUsersAktualniZaposleni> getAktualniZaposleni() {
        return aktualniZaposleni;
    }

    public void setAktualniZaposleni(List<VCodeksUsersAktualniZaposleni> aktualniZaposleni) {
        this.aktualniZaposleni = aktualniZaposleni;
    }

    public VCodeksUsersAktualniZaposleni getIzbranZaposleni() {
        return izbranZaposleni;
    }

    public void setIzbranZaposleni(VCodeksUsersAktualniZaposleni izbranZaposleni) {
        this.izbranZaposleni = izbranZaposleni;
    }

    public int getPinIn() {
        return pinIn;
    }

    public void setPinIn(int pinIn) {
        this.pinIn = pinIn;
    }

    
    
    
    
    
    
    
    
    public class izvedbaEasy {

        private Date when;
        private VCodeksUsersAktualniZaposleni who;
        private String where;

        public izvedbaEasy() {
            who = new VCodeksUsersAktualniZaposleni();
        }

        public Date getWhen() {
            return when;
        }

        public void setWhen(Date when) {
            this.when = when;
        }

        public VCodeksUsersAktualniZaposleni getWho() {
            return who;
        }

        public void setWho(VCodeksUsersAktualniZaposleni who) {
            this.who = who;
        }

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
        }

    }

}
