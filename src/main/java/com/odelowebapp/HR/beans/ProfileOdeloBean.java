/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.CODEKS.entity.Departments;
import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseQuestionnaireStatus;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseAnswerFacade;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.student.evidenca.entity.ASS_Q_IzposojenoSredstvo;
import com.odelowebapp.student.evidenca.facade.ASSAssetFacade;
import com.odelowebapp.student.evidenca.facade.ASSLogFacade;
import com.odelowebapp.student.evidenca.facade.ASSStatusFacade;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.SecurityUtils;

/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class ProfileOdeloBean implements Serializable {

    @EJB
    private HRCourseAttendanceFacade attendanceFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private ASSAssetFacade assAssetFacade;
    @EJB
    private ASSLogFacade sredstvaFacade;
    @EJB
    private ASSStatusFacade statusFacade;
    @EJB
    private HRCourseAnswerFacade answerFacade;
    @EJB
    private HRCourseTypeFacade ctFacade;

    @EJB
    private com.odelowebapp.CODEKS.facade.DepartmentsFacade departmentFacade;

    private String personCID;
    List<HRCourseAttendance> allAttendancesForPerson;
    VCodeksUsersAktualniZaposleni userByCodeksId;

    List<VCodeksUsersAktualniZaposleni> zaposleniPodrejeni;

    List<ASS_Q_IzposojenoSredstvo> izposojenaSredstva;

    Map<String, List<HRCourseAttendance>> allAttendancesForPersonMap = new HashMap();

    List<Object[]> findStatusOfQuestionnaires;
    Map<Integer, VCodeksUsersAktualniZaposleni> mapZaposlenih = new HashMap();
    
    List<HRCourseType> externalCategories = new ArrayList();

    @PostConstruct
    public void init() {
        System.out.println("Sem v ProfileOdeloBean");

        personCID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("coid");

        System.out.println("CID:" + personCID);

        if (personCID == null || personCID.equals("")) {
            System.out.println("NI COID parametra, vzamem ga iz seje");
            personCID = (String) SecurityUtils.getSubject().getSession().getAttribute("codeksid");
        }

        allAttendancesForPerson = attendanceFacade.findAllAttendancesForPerson2(Integer.parseInt(personCID));
        
        externalCategories = ctFacade.getRecursiveCourseHierarchy(2);

        List<HRCourseAttendance> niudelezbe = new ArrayList();
        List<HRCourseAttendance> neznanaudelezba = new ArrayList();
        List<HRCourseAttendance> izpolnite = new ArrayList();
        List<HRCourseAttendance> izpolnjeno = new ArrayList();
        for (HRCourseAttendance att : allAttendancesForPerson) {
            if (att.getTsAnketa() == null && !att.getWasAttended()) {
//                System.out.println("ni udeležbe");
//                niudelezbe.add(att);
                  neznanaudelezba.add(att);
            }else if(att.getTsAnketa() != null && !att.getWasAttended()){
                  neznanaudelezba.add(att);
            }else if (att.getTsAnketa() == null && att.getWasAttended()) {
                System.out.println("izpolnite");
                izpolnite.add(att);
            } else if (att.getTsAnketa() != null) {
                System.out.println("izpolnjeno");
                izpolnjeno.add(att);
            }
        }

        allAttendancesForPersonMap.put("niudelezbe", niudelezbe);
        allAttendancesForPersonMap.put("izpolnite", izpolnite);
        allAttendancesForPersonMap.put("izpolnjeno", izpolnjeno);
        allAttendancesForPersonMap.put("neznanaudelezba", neznanaudelezba);

        //System.out.println("attendances:"+allAttendancesForPerson.size());
        userByCodeksId = usersEJB.findUserByCodeksId(Integer.parseInt(personCID));
        //System.out.println("User:"+userByCodeksId.getLastname());

        izposojenaSredstva = sredstvaFacade.findWhatIsIzposojeno(personCID);

        for (ASS_Q_IzposojenoSredstvo sr : izposojenaSredstva) {
            sr.setSredstvo(assAssetFacade.find(sr.getAssetID()));
            sr.setStatus(statusFacade.find(sr.getiDLogStatus()));
        }

        //OCENITEV UČINKOVITOSTI IZOBRAŽEVANJA
        Departments loggedInUserDepartment = departmentFacade.find(usersEJB.find(Integer.parseInt(personCID)).getDepartmentId());
        System.out.println("LoggedInUserDepartment: " + loggedInUserDepartment.getName());
        List<Departments> findAllChildDepartments = departmentFacade.findAllChildDepartments(loggedInUserDepartment.getName());

        for (Departments d : findAllChildDepartments) {
            System.out.println("PODREJENI ODDELEK:" + d.getName());
        }

        List<Integer> findAllChildDepartmentsIds = departmentFacade.findAllChildDepartmentsIds(loggedInUserDepartment.getName());

        zaposleniPodrejeni = usersEJB.pridobiZaposleneIdOddelkovAktualniZaposleni(findAllChildDepartmentsIds);

        List<Integer> zpids = zaposleniPodrejeni.stream().map(obj -> obj.getId()).collect(Collectors.toList());
        for (int i : zpids) {
            System.out.println(i);
        }

        List<HRCourseAttendance> ucinkovitost = new ArrayList();

        if (zpids.size() > 0) {

            findStatusOfQuestionnaires = answerFacade.findStatusOfQuestionnaires2(zpids);

            System.out.println("findStatusOfQuestionnaires2 size:" + findStatusOfQuestionnaires.size());

            for (int i = 0; i < findStatusOfQuestionnaires.size(); i++) {
                HRCourseQuestionnaireStatus ret1 = (HRCourseQuestionnaireStatus) findStatusOfQuestionnaires.get(i)[0];
                HRCourseOffering ret2 = (HRCourseOffering) findStatusOfQuestionnaires.get(i)[1];
                //VCodeksUsersAktualniZaposleni ret3 = (VCodeksUsersAktualniZaposleni)findStatusOfQuestionnaires.get(1)[2];
                VCodeksUsersAktualniZaposleni ret3 = usersEJB.findUserByCodeksId(ret1.getCodeksID());
                mapZaposlenih.put(ret1.getCodeksID(), ret3);
                System.out.println("Returned:" + ret1.getCourseTitle() + " coid:" + ret2.getCourseOfferingID() + " ret3:" + ret3.getLastname());
            }

        }

        //
    }

    public void buttonAction() {
        System.out.println("Sem v buttonAction()");
    }
    
    public boolean isExternalEducation(HRCourse course){
        System.out.println("Sem v isExternalEducation");
        if(externalCategories.contains(course.getCourseTypeID())){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isButtonUcinkovitostEnabled(Date dateToCompareTo){
        LocalDate today = LocalDate.now();
        LocalDate dateToLocalDate = dateToCompareTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return today.isAfter(dateToLocalDate);
    }

    public List<HRCourseAttendance> getAllAttendancesForPerson() {
        return allAttendancesForPerson;
    }

    public void setAllAttendancesForPerson(List<HRCourseAttendance> allAttendancesForPerson) {
        this.allAttendancesForPerson = allAttendancesForPerson;
    }

    public VCodeksUsersAktualniZaposleni getUserByCodeksId() {
        return userByCodeksId;
    }

    public void setUserByCodeksId(VCodeksUsersAktualniZaposleni userByCodeksId) {
        this.userByCodeksId = userByCodeksId;
    }

    public List<ASS_Q_IzposojenoSredstvo> getIzposojenaSredstva() {
        return izposojenaSredstva;
    }

    public void setIzposojenaSredstva(List<ASS_Q_IzposojenoSredstvo> izposojenaSredstva) {
        this.izposojenaSredstva = izposojenaSredstva;
    }

    public Map<String, List<HRCourseAttendance>> getAllAttendancesForPersonMap() {
        return allAttendancesForPersonMap;
    }

    public void setAllAttendancesForPersonMap(Map<String, List<HRCourseAttendance>> allAttendancesForPersonMap) {
        this.allAttendancesForPersonMap = allAttendancesForPersonMap;
    }

    public List<Object[]> getFindStatusOfQuestionnaires() {
        return findStatusOfQuestionnaires;
    }

    public void setFindStatusOfQuestionnaires(List<Object[]> findStatusOfQuestionnaires) {
        this.findStatusOfQuestionnaires = findStatusOfQuestionnaires;
    }

    public Map<Integer, VCodeksUsersAktualniZaposleni> getMapZaposlenih() {
        return mapZaposlenih;
    }

    public void setMapZaposlenih(Map<Integer, VCodeksUsersAktualniZaposleni> mapZaposlenih) {
        this.mapZaposlenih = mapZaposlenih;
    }

    public String getPersonCID() {
        return personCID;
    }

    public void setPersonCID(String personCID) {
        this.personCID = personCID;
    }

}
