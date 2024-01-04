/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseAnswer;
import com.odelowebapp.HR.entity.HRCourseAnswerPK;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.facade.HRCourseAnswerFacade;
import com.odelowebapp.HR.facade.HRCourseFacade;
import com.odelowebapp.HR.facade.HRCourseOfferingFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class HRCourseSurveyBean implements Serializable {

    private String[] questions = {"Izobraževalni program je izpolnil moja pričakovanja.", "Obravnavano temo na izobraževanju sem razumel.", "Znanja, ki sem jih pridobil bom uporabil v praksi.", "Predavatelj je snov / znanje podajal na 'primeren, ustrezen' način.", "Obseg seminarja/izobraževanja je bil ustrezen.", "Gradivo, ki smo ga prejeli je uporabno.", "S predavateljem oz. predavateljico sem bil zadovoljen.", "S prostorom, kjer se je seminar izvajal sem bil zadovoljen."};
    private String coid = "";
    private String uid = "";

    private List<VprasanjeOdgovor> vprasanja = new ArrayList();

    @EJB
    private HRCourseFacade courseFacade;
    @EJB
    private HRCourseOfferingFacade offeringFacade;
    @EJB
    private VADCODEKSUsersFacade usersFacade;
    @EJB
    private HRCourseAnswerFacade answerFacade;

    HRCourseOffering coffering;
    VADCODEKSUsers user;
    
    List<HRCourseAnswer> answers;

    @PostConstruct
    public void init() {
        System.out.println("Sem v HRCourseSurveyBean init()");
        coid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("coid");
        uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
        System.out.println("prejel coid:" + coid + " uid:" + uid);
        coffering = offeringFacade.find(Integer.parseInt(coid));
        user = usersFacade.findUserByExternalId(uid);

        answers = answerFacade.findAllAnswersByPersonAndCourse(user.getId()+"", Integer.parseInt(coid), 1);

        if (!answers.isEmpty()) {
            
            for(HRCourseAnswer answer: answers){
                VprasanjeOdgovor v = new VprasanjeOdgovor();
                v.setVprasanje(answer.getQuestion());
                v.setOdgovor(answer.getAnswer());
                v.setZapSt(answer.getHRCourseAnswerPK().getQuestionNbr());
                v.setComment(answer.getComment());
                vprasanja.add(v);
            }
            
            
        } else {
            int zapSt = 1;
            for (String vpr : questions) {
                VprasanjeOdgovor v = new VprasanjeOdgovor();
                v.setVprasanje(vpr);
                v.setOdgovor("0");
                v.setZapSt(zapSt);
                vprasanja.add(v);
                zapSt++;
            }
        }

//        int zapSt = 1;
//        for (String vpr : questions) {
//            VprasanjeOdgovor v = new VprasanjeOdgovor();
//            v.setVprasanje(vpr);
//            v.setOdgovor("0");
//            v.setZapSt(zapSt);
//            vprasanja.add(v);
//            zapSt++;
//        }
    }

    public void saveResponse() {
        System.out.println("sem v saveResponse()");
        Date dateshranjevanja = new Date();
        for (VprasanjeOdgovor vpr : vprasanja) {
            System.out.println(vpr.vprasanje + " odgovor:" + vpr.odgovor);
            HRCourseAnswer ca = new HRCourseAnswer();
            ca.setAnswer(vpr.odgovor);
            ca.setComment(vpr.comment);
            ca.setQuestion(vpr.vprasanje);
            ca.setTimestamp(dateshranjevanja);
            HRCourseAnswerPK capk = new HRCourseAnswerPK();
            capk.setCourseID(coffering.getCourseOfferingID());
            capk.setQuestionNbr(vpr.zapSt);
            capk.setUserID(user.getId() + "");
            capk.setQuestionnaireID(1);
            ca.setHRCourseAnswerPK(capk);
            answerFacade.create(ca);
        }
        answerFacade.doRefresh();
        answers = answerFacade.findAllAnswersByPersonAndCourse(user.getId()+"", Integer.parseInt(coid), 1);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vaša ocenitev je bila shranjena. Hvala za sodelovanje!"));
    }
    
    private boolean rating6Disabled = false;
    public void gradivoSelectOneMenuListener(){
        System.out.println("Sem v gradivoSelectOneMenuListener()");
        rating6Disabled = true;
    }

    public boolean isRating6Disabled() {
        return rating6Disabled;
    }

    public void setRating6Disabled(boolean rating6Disabled) {
        this.rating6Disabled = rating6Disabled;
    }
    
    private boolean rating8Disabled = false;
    public void prostorSelectOneMenuListener(){
        System.out.println("Sem v prostorSelectOneMenuListener()");
        rating8Disabled = true;
    }

    public boolean isRating8Disabled() {
        return rating8Disabled;
    }

    public void setRating8Disabled(boolean rating8Disabled) {
        this.rating8Disabled = rating8Disabled;
    }
    
    

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public HRCourseOffering getCoffering() {
        return coffering;
    }

    public void setCoffering(HRCourseOffering coffering) {
        this.coffering = coffering;
    }

    public VADCODEKSUsers getUser() {
        return user;
    }

    public void setUser(VADCODEKSUsers user) {
        this.user = user;
    }

    public List<VprasanjeOdgovor> getVprasanja() {
        return vprasanja;
    }

    public void setVprasanja(List<VprasanjeOdgovor> vprasanja) {
        this.vprasanja = vprasanja;
    }

    public List<HRCourseAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<HRCourseAnswer> answers) {
        this.answers = answers;
    }


    
    

    public class VprasanjeOdgovor {

        private String vprasanje;
        private String odgovor;
        private String comment;
        private int zapSt;

        public VprasanjeOdgovor() {
        }

        public String getVprasanje() {
            return vprasanje;
        }

        public void setVprasanje(String vprasanje) {
            this.vprasanje = vprasanje;
        }

        public String getOdgovor() {
            return odgovor;
        }

        public void setOdgovor(String odgovor) {
            this.odgovor = odgovor;
        }

        public int getZapSt() {
            return zapSt;
        }

        public void setZapSt(int zapSt) {
            this.zapSt = zapSt;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }

}
