package com.odelowebapp.HR.beans;

import com.odelowebapp.CODEKS.entity.Departments;
import com.odelowebapp.HR.controller.HRCourseAttendanceController;
import com.odelowebapp.HR.controller.HRCourseOfferingController;
import com.odelowebapp.HR.entity.HRCourse;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseHRCourseInstructor;
import com.odelowebapp.HR.entity.HRCourseInstructor;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.PrimefacesUploadedFile;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.*;
import com.odelowebapp.portal.beans.FileAttachment;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Dean
 */
@Named
@ViewScoped
public class HRCourseInstructorViewBean implements Serializable {

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

    @EJB
    private com.odelowebapp.CODEKS.facade.DepartmentsFacade departmentFacade;

    String personCID;
    private List<HRCourse> allCoursesInstructorCanTeach;
    private HRCourse selectedCourse;
    private HRCourseOffering newOffering;

    private List<HRCourseOffering> instructorsPastOfferings;
    private HRCourseOffering selectedCourseOfferingForPrint;
    private List<HRCourseOffering> instructorsPastOfferingsHistory;
    private HRCourseOffering selectedCourseOfferingFromHistory;

    private List<HRCourseInstructor> instruktorjiNaVoljo = new ArrayList();

    private List<HRCourseOffering> instructorsNextOfferings;

    private HRCourseOffering selectedCourseOfferingForAddingParticipants;
    private List<VCodeksUsersAktualniZaposleni> currentlyEmployed;
    private List<VCodeksUsersAktualniZaposleni> selectedEmployees;

    private HRCourseAttendance udelezenecZaBrisanje;

    private String newCommentValue;
    List<VCodeksUsersAktualniZaposleni> zaposleniPodrejeni;
    private List<HRCourseInstructor> instruktorjiPodrejeni;
    private List<HRCourseOffering> podrejeniPastOfferingsHistory;
    
    private List<HRCourseOffering> offeringsIAttendedAndWill;

    private HRCourseOffering selectedCourseOfferingToBeRepeated;

    @EJB
    private HRvcodeksusersFacade usersEJB;

    @Inject
    private HRCourseOfferingController cofferingController;
    @Inject
    private HRCourseAttendanceController cattendanceController;

    private UploadedFile file;
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();

    private List<Integer> zpids;

    PrimeFaces current;
    private List<FileAttachment> priloge;

    @PostConstruct
    public void init() {
        System.out.println("Sem v HRCourseInstructorViewBean");

        current = PrimeFaces.current();
        priloge = new ArrayList();

        personCID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("coid");

        System.out.println("CID:" + personCID);

        if (personCID == null || personCID.equals("")) {
            System.out.println("NI COID parametra, vzamem ga iz seje");
            personCID = (String) SecurityUtils.getSubject().getSession().getAttribute("codeksid");
            System.out.println("COID iz seje:" + personCID);

            //poiščem Course ki jih oseba lahko izobražuje
            //allCoursesInstructorCanTeach = courseFacade.findAllCoursesInstructorCanTeach(Integer.parseInt(personCID)); //izpiše vsa tudi hitra
            allCoursesInstructorCanTeach = courseFacade.findCoursesInstructorCanTeachWithoutQuickInternalEducations(Integer.parseInt(personCID)); //izpiše vse brez internih
            instructorsPastOfferings = cofferingFacade.findCourseOfferingsByInstructor(Integer.parseInt(personCID));
            instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
            instructorsPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructor(Integer.parseInt(personCID), 60);
            
            offeringsIAttendedAndWill = cofferingFacade.findAllCourseOfferingsPersonAttendedOrWill(Integer.parseInt(personCID));

            Departments loggedInUserDepartment = departmentFacade.find(usersEJB.find(Integer.parseInt(personCID)).getDepartmentId());
            System.out.println("LoggedInUserDepartment: " + loggedInUserDepartment.getName());
            List<Departments> findAllChildDepartments = departmentFacade.findAllChildDepartments(loggedInUserDepartment.getName());

            for (Departments d : findAllChildDepartments) {
                System.out.println("PODREJENI ODDELEK:" + d.getName());
            }

            List<Integer> findAllChildDepartmentsIds = departmentFacade.findAllChildDepartmentsIds(loggedInUserDepartment.getName());

            zaposleniPodrejeni = usersEJB.pridobiZaposleneIdOddelkovAktualniZaposleni(findAllChildDepartmentsIds);

            zpids = zaposleniPodrejeni.stream().map(obj -> obj.getId()).collect(Collectors.toList());

            instruktorjiPodrejeni = instructorFacade.findInstructorsByCodeksId(zpids);

            for (HRCourseInstructor i : instruktorjiPodrejeni) {
                System.out.println("Instruktor podrejen:" + i.getFirstname() + " " + i.getLastname());
            }
            //List<Integer> zpidsplusme = zpids;
            //zpidsplusme.add(Integer.parseInt(personCID));
            podrejeniPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructors(zpids, 60);

        }

        currentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();
        napolniCelotenHM();

    }

    public void prepareRepeatedCO() {
        System.out.println("sem v prepareRepeatedCO");
        selectedCourseOfferingToBeRepeated = selectedCourseOfferingFromHistory;
        selectedCourseOfferingToBeRepeated.setCourseDate(new Date());
        selectedCourseOfferingToBeRepeated.setCreator(Integer.parseInt(personCID));
        instruktorjiNaVoljo.clear();
        List<HRCourseHRCourseInstructor> joins = joinFacade.findAllInstructorsForCourse(selectedCourseOfferingToBeRepeated.getCourseID());
        for (HRCourseHRCourseInstructor i : joins) {
            instruktorjiNaVoljo.add(i.getInstructorID());
        }
    }

    public void saveRepeatedCO() {
        System.out.println("Sem v saveRepeatedCO");
        cofferingFacade.create(selectedCourseOfferingToBeRepeated);
        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
    }

    public void handleCommentSave(String attid) {
        System.out.println("Sem v handleCommentSave()");
//        System.out.println("Urejaš attendanceID:"+attid);
        HRCourseAttendance find = attendanceFacade.find(Integer.parseInt(attid));
//        System.out.println("Find:"+find.getAttendanceID()+" commentDB:"+find.getComment());
        find.setComment(newCommentValue);
        attendanceFacade.edit(find);
    }

    public void commentValueChangeListener(ValueChangeEvent event) {
        String newValue = event.getNewValue().toString();
//        System.out.println("New value: " + newValue);
//        System.out.println("Old value: " + oldValue);    
        newCommentValue = newValue;
    }

    public void fileUploadAction(String sc) {
        System.out.println("Sem v fileUploadAction.");
        //setSelectedCourseOfferingForViewingAttachments(selectedCourseOfferingFromHistory);
        priloge.clear();

        if (sc.equals("history")) {
            System.out.println("Sem v fileUploadAction. selectedCourseOfferingForViewingAttachments:" + selectedCourseOfferingFromHistory.getCourseOfferingID());
            System.out.println("Sem v fileUploadAction. Selected courseoffering has this many attachments:");
            selectedCourseOfferingFromHistory.findAttachmentsForCourse(selectedCourseOfferingFromHistory.getCourseOfferingID());
            System.out.println("Size offering:" + selectedCourseOfferingFromHistory.getFileattachments().size());
            System.out.println("Selected course has this many attachments:");
            selectedCourseOfferingFromHistory.getCourseID().findAttachmentsForCourse(selectedCourseOfferingFromHistory.getCourseID().getCourseID());
            System.out.println("Size course:" + selectedCourseOfferingFromHistory.getCourseID().getFileattachments().size());

            priloge.addAll(selectedCourseOfferingFromHistory.getFileattachments());
            priloge.addAll(selectedCourseOfferingFromHistory.getCourseID().getFileattachments());
        } else {
            System.out.println("Sem v fileUploadAction. Selected courseoffering has this many attachments:");
            selectedCourse.findAttachmentsForCourse(selectedCourse.getCourseID());
            System.out.println("Size offering:" + selectedCourse.getFileattachments().size());

            priloge.addAll(selectedCourse.getFileattachments());

        }

        System.out.println("PRILOGE SIZE:" + priloge.size());

        current.executeScript("PF('HRCourseAttachmentsViewDialog').show()");
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for courseOffering:" + selectedCourseOfferingForAddingParticipants.getCourseOfferingID() + " files allready there:" + selectedCourseOfferingForAddingParticipants.getFiles().size());
        files = selectedCourseOfferingForAddingParticipants.getFiles();
        files.add(new PrimefacesUploadedFile(event.getFile()));
        selectedCourseOfferingForAddingParticipants.setFiles(files);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Files size:" + selectedCourseOfferingForAddingParticipants.getFiles().size());
        selectedCourseOfferingForAddingParticipants.setDateUpdated(new Date());
    }

    public void handleFileUploadNewCO(FileUploadEvent event) {
        System.out.println("File: " + event.getFile().getFileName() + " will be saved in List for courseOffering files allready there:" + newOffering.getFiles().size());
        files = newOffering.getFiles();
        files.add(new PrimefacesUploadedFile(event.getFile()));
        newOffering.setFiles(files);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Files size:" + newOffering.getFiles().size());
        newOffering.setDateUpdated(new Date());
    }

    public void createCO() {
        System.out.println("Sem v createCO()");
        newOffering.setActive(Boolean.TRUE);
        cofferingFacade.create(newOffering);

        cofferingController.sendMailNotification2Instructor(newOffering.getInstructorID().getMail(), newOffering, "");
        if (newOffering.getHRCourseAttendanceList().size() > 0) {
            System.out.println("Mail o novi napotitvi je potrebno poslati " + newOffering.getHRCourseAttendanceList().size() + " udeležencem");
//            pridobizaposlene(newOffering);
            for (HRCourseAttendance a : newOffering.getHRCourseAttendanceList()) {
                cofferingController.sendMailNotification2Atendee(selectedEmployeesHM.get(a.getCodeksID()).getEmail(), newOffering, selectedEmployeesHM.get(a.getCodeksID()).getLastname() + " " + selectedEmployeesHM.get(a.getCodeksID()).getFirstname(), "");
            }
        } else {
            System.out.println("Ni udeležencev, maila jim zato ne morem poslati");
        }

        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
        instructorsPastOfferings = cofferingFacade.findCourseOfferingsByInstructor(Integer.parseInt(personCID));
        instructorsPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructor(Integer.parseInt(personCID), 60);

    }

    private void napolniCelotenHM() {
        System.out.println("Sem v napolniCelotenHM");
        for (VCodeksUsersAktualniZaposleni zap : currentlyEmployed) {
            selectedEmployeesHM.put(zap.getId(), zap);
        }
    }

    private HashMap<Integer, VCodeksUsersAktualniZaposleni> selectedEmployeesHM = new HashMap<Integer, VCodeksUsersAktualniZaposleni>();
//    public void pridobizaposlene(HRCourseOffering co) {
//        System.out.println("Sem v pridobizaposlene() za CourseOffering:"+co.getCourseOfferingID());
//        currentlyEmployed = usersEJB.zaposleniZaAndroidAplikacijo();
//        List<HRCourseAttendance> prebrani = attendanceFacade.findAllPersonsForCourse(co);
////        selectedEmployees = new ArrayList();
//        for (HRCourseAttendance p : prebrani) {
//            System.out.println("Iscem uporabnika z id:" + p.getCodeksID());
//            VCodeksUsersAktualniZaposleni zap = usersEJB.findUserByCodeksId(p.getCodeksID());
////            selectedEmployees.add(zap);
////            zap.setInserted(true);
//            selectedEmployeesHM.put(zap.getId(), zap);
//        }
//    }

    public void editCO() {
        System.out.println("Sem v editCO()");
        selectedCourseOfferingForAddingParticipants.setActive(Boolean.TRUE);
        selectedCourseOfferingForAddingParticipants.setDateUpdated(new Date());
        cofferingFacade.edit(selectedCourseOfferingForAddingParticipants);

        cofferingController.sendMailNotification2Instructor(selectedCourseOfferingForAddingParticipants.getInstructorID().getMail(), selectedCourseOfferingForAddingParticipants, " - sprememba");
        if (selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList().size() > 0) {
            System.out.println("Mail o spremembi je potrebno poslati " + selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList().size() + " udeležencem");
//            pridobizaposlene(selectedCourseOfferingForAddingParticipants);
            for (HRCourseAttendance a : selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList()) {
                cofferingController.sendMailNotification2Atendee(selectedEmployeesHM.get(a.getCodeksID()).getEmail(), selectedCourseOfferingForAddingParticipants, selectedEmployeesHM.get(a.getCodeksID()).getLastname() + " " + selectedEmployeesHM.get(a.getCodeksID()).getFirstname(), "- sprememba");
            }
        } else {
            System.out.println("Ni udeležencev, maila jim zato ne morem poslati");
        }

        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
        instructorsPastOfferings = cofferingFacade.findCourseOfferingsByInstructor(Integer.parseInt(personCID));
        instructorsPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructor(Integer.parseInt(personCID), 60);
    }

    public void courseSelectedForCreatingOffering() {
        System.out.println("Sem v courseSelectedForCreatingOffering()");
        System.out.println("Selected course:" + selectedCourse.getCourseID());
        newOffering = new HRCourseOffering();
        newOffering.setCourseID(selectedCourse);
        newOffering.setCreator(Integer.parseInt(personCID));
        newOffering.setDateCreated(new Date());

        instruktorjiNaVoljo.clear();
        List<HRCourseHRCourseInstructor> joins = joinFacade.findAllInstructorsForCourse(selectedCourse);
        for (HRCourseHRCourseInstructor i : joins) {
            instruktorjiNaVoljo.add(i.getInstructorID());
        }

        newOffering.getCourseID().findAttachmentsForCourse(newOffering.getCourseID().getCourseID());
        newOffering.findAttachmentsForCourse(newOffering.getCourseOfferingID());

    }

    public void courseSelectedForEditingOffering() {
        System.out.println("Sem v courseSelectedForEditingOffering()");
        System.out.println("Selected courseOffering:" + selectedCourseOfferingForAddingParticipants.getCourseOfferingID() + "date:" + selectedCourseOfferingForAddingParticipants.getCourseDate());

        instruktorjiNaVoljo.clear();
        List<HRCourseHRCourseInstructor> joins = joinFacade.findAllInstructorsForCourse(selectedCourseOfferingForAddingParticipants.getCourseID());
        for (HRCourseHRCourseInstructor i : joins) {
            instruktorjiNaVoljo.add(i.getInstructorID());
        }

        selectedCourseOfferingForAddingParticipants.getCourseID().findAttachmentsForCourse(selectedCourseOfferingForAddingParticipants.getCourseID().getCourseID());
        selectedCourseOfferingForAddingParticipants.findAttachmentsForCourse(selectedCourseOfferingForAddingParticipants.getCourseOfferingID());

    }

    public void deleteCourseOffering() {
        System.out.println("Sem v deleteCourseOffering(), brisem izvedbo:" + selectedCourseOfferingForAddingParticipants.getCourseOfferingID());
//        pridobizaposlene(selectedCourseOfferingForAddingParticipants); //HM napolnim tukaj, če ga kasneje bo prazen ker bodo podatki že izbrisani
        //BRISANJE Course Offeringa
        //najprej zbrisemo vse udeležence coursa  courseattendance
        List<HRCourseAttendance> findAllAttendancesForOffering = attendanceFacade.findAllAttendancesForOffering(selectedCourseOfferingForAddingParticipants.getCourseOfferingID());
        System.out.println("Zbrisal bom attendacev:" + findAllAttendancesForOffering.size());
        for (HRCourseAttendance a : findAllAttendancesForOffering) {
            attendanceFacade.remove(a);
        }
        //List<HRCourseHRCourseInstructor> findAllInstructorsForCourse = joinFacade.findAllInstructorsForCourse(selectedCourseOfferingForAddingParticipants.getCourseID());
        cofferingFacade.remove(selectedCourseOfferingForAddingParticipants);

        cofferingController.sendMailNotificationOdpoved2Instructor(selectedCourseOfferingForAddingParticipants.getInstructorID().getMail(), selectedCourseOfferingForAddingParticipants);

        if (selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList().size() > 0) {
            System.out.println("Mail o odpovedi je potrebno poslati " + selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList().size() + " udeležencem");
            for (HRCourseAttendance a : selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList()) {
                cofferingController.sendMailNotificationOdpoved2Atendee(selectedEmployeesHM.get(a.getCodeksID()).getEmail(), selectedCourseOfferingForAddingParticipants);
            }
        } else {
            System.out.println("Ni udeležencev, maila jim zato ne morem poslati");
        }

        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
    }

    public void deleteCourseOfferingNoNotification(HRCourseOffering co) {
        System.out.println("Sem v deleteCourseOfferingNoNotification(), brisem izvedbo:" + co.getCourseOfferingID());
//        pridobizaposlene(selectedCourseOfferingForAddingParticipants); //HM napolnim tukaj, če ga kasneje bo prazen ker bodo podatki že izbrisani
        //BRISANJE Course Offeringa
        //najprej zbrisemo vse udeležence coursa  courseattendance
        List<HRCourseAttendance> findAllAttendancesForOffering = attendanceFacade.findAllAttendancesForOffering(co.getCourseOfferingID());
        System.out.println("Zbrisal bom attendacev:" + findAllAttendancesForOffering.size());
        for (HRCourseAttendance a : findAllAttendancesForOffering) {
            attendanceFacade.remove(a);
        }
        //List<HRCourseHRCourseInstructor> findAllInstructorsForCourse = joinFacade.findAllInstructorsForCourse(selectedCourseOfferingForAddingParticipants.getCourseID());
        cofferingFacade.remove(co);

        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
        instructorsPastOfferings = cofferingFacade.findCourseOfferingsByInstructor(Integer.parseInt(personCID));
        instructorsPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructor(Integer.parseInt(personCID), 60);
        podrejeniPastOfferingsHistory = cofferingFacade.findCourseOfferingsHistoryByInstructors(zpids, 60);
    }

    public void shraniIzbraneOsebe() {
        System.out.println("Sem v shraniIzbraneOsebe()");
        System.out.println("Izbranih je oseb:" + selectedEmployees.size());
        System.out.println("Potrebno jih je dodati na offering:" + selectedCourseOfferingForAddingParticipants.getCourseOfferingID());

        for (VCodeksUsersAktualniZaposleni prebran : selectedEmployees) {
            System.out.println("Prebran user:" + prebran.getId());
            HRCourseAttendance ca = new HRCourseAttendance();
            ca.setCodeksID(prebran.getId());
            ca.setCourseOfferingID(selectedCourseOfferingForAddingParticipants);
            ca.setWasAttended(false);
            attendanceFacade.create(ca);
            cofferingController.sendMailNotification2Atendee(prebran.getEmail(), selectedCourseOfferingForAddingParticipants, prebran.getLastname() + " " + prebran.getFirstname(), "");
        }
        attendanceFacade.doRefresh();
        selectedEmployees.clear();
        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
        //tu še uredi refresh
    }

    HashMap<Integer, VCodeksUsersAktualniZaposleni> vuserjiHM;

    public void onRowToggle() {
        System.out.println("Sem v onRowToggle(), sedaj moram napolniti HM");
        if (selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList().size() > 0) {
            List<Integer> idjiuserjev = new ArrayList();
            for (HRCourseAttendance a : selectedCourseOfferingForAddingParticipants.getHRCourseAttendanceList()) {
                idjiuserjev.add(a.getCodeksID());
            }
            //userjiHM = codeksFacade.findCodeksUsersByIDsHashMap(idjiuserjev);
            vuserjiHM = usersEJB.zaposleniZaAndroidAplikacijoHM(idjiuserjev);

        }
    }

    public void brisiUdelezenca() {
        System.out.println("Sem v brisiUdelezenca()");
//        pridobizaposlene(udelezenecZaBrisanje.getCourseOfferingID());
        //seznamUdelezencev.remove(udelezenecZaBrisanje);
        attendanceFacade.remove(udelezenecZaBrisanje);
        cofferingController.sendMailNotificationOdpoved2Atendee(selectedEmployeesHM.get(udelezenecZaBrisanje.getCodeksID()).getEmail(), udelezenecZaBrisanje.getCourseOfferingID());

        attendanceFacade.doRefresh();
        instructorsNextOfferings = cofferingFacade.findNextCourseOfferingsByInstructorOrCreator(Integer.parseInt(personCID));
    }

    public String getPersonCID() {
        return personCID;
    }

    public void setPersonCID(String personCID) {
        this.personCID = personCID;
    }

    public List<HRCourse> getAllCoursesInstructorCanTeach() {
        return allCoursesInstructorCanTeach;
    }

    public void setAllCoursesInstructorCanTeach(List<HRCourse> allCoursesInstructorCanTeach) {
        this.allCoursesInstructorCanTeach = allCoursesInstructorCanTeach;
    }

    public HRCourse getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(HRCourse selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public HRCourseOffering getNewOffering() {
        return newOffering;
    }

    public void setNewOffering(HRCourseOffering newOffering) {
        this.newOffering = newOffering;
    }

    public List<HRCourseInstructor> getInstruktorjiNaVoljo() {
        return instruktorjiNaVoljo;
    }

    public void setInstruktorjiNaVoljo(List<HRCourseInstructor> instruktorjiNaVoljo) {
        this.instruktorjiNaVoljo = instruktorjiNaVoljo;
    }

    public List<HRCourseOffering> getInstructorsPastOfferings() {
        return instructorsPastOfferings;
    }

    public void setInstructorsPastOfferings(List<HRCourseOffering> instructorsPastOfferings) {
        this.instructorsPastOfferings = instructorsPastOfferings;
    }

    public HRCourseOffering getSelectedCourseOfferingForPrint() {
        return selectedCourseOfferingForPrint;
    }

    public void setSelectedCourseOfferingForPrint(HRCourseOffering selectedCourseOfferingForPrint) {
        this.selectedCourseOfferingForPrint = selectedCourseOfferingForPrint;
    }

    public List<HRCourseOffering> getInstructorsNextOfferings() {
        return instructorsNextOfferings;
    }

    public void setInstructorsNextOfferings(List<HRCourseOffering> instructorsNextOfferings) {
        this.instructorsNextOfferings = instructorsNextOfferings;
    }

    public HRCourseOffering getSelectedCourseOfferingForAddingParticipants() {
        return selectedCourseOfferingForAddingParticipants;
    }

    public void setSelectedCourseOfferingForAddingParticipants(HRCourseOffering selectedCourseOfferingForAddingParticipants) {
        this.selectedCourseOfferingForAddingParticipants = selectedCourseOfferingForAddingParticipants;

    }

    public List<VCodeksUsersAktualniZaposleni> getCurrentlyEmployed() {
        return currentlyEmployed;
    }

    public void setCurrentlyEmployed(List<VCodeksUsersAktualniZaposleni> currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }

    public List<VCodeksUsersAktualniZaposleni> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<VCodeksUsersAktualniZaposleni> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }

    public HashMap<Integer, VCodeksUsersAktualniZaposleni> getVuserjiHM() {
        return vuserjiHM;
    }

    public void setVuserjiHM(HashMap<Integer, VCodeksUsersAktualniZaposleni> vuserjiHM) {
        this.vuserjiHM = vuserjiHM;
    }

    public HRCourseAttendance getUdelezenecZaBrisanje() {
        return udelezenecZaBrisanje;
    }

    public void setUdelezenecZaBrisanje(HRCourseAttendance udelezenecZaBrisanje) {
        this.udelezenecZaBrisanje = udelezenecZaBrisanje;
    }

    public HashMap<Integer, VCodeksUsersAktualniZaposleni> getSelectedEmployeesHM() {
        return selectedEmployeesHM;
    }

    public void setSelectedEmployeesHM(HashMap<Integer, VCodeksUsersAktualniZaposleni> selectedEmployeesHM) {
        this.selectedEmployeesHM = selectedEmployeesHM;
    }

    public List<HRCourseOffering> getInstructorsPastOfferingsHistory() {
        return instructorsPastOfferingsHistory;
    }

    public void setInstructorsPastOfferingsHistory(List<HRCourseOffering> instructorsPastOfferingsHistory) {
        this.instructorsPastOfferingsHistory = instructorsPastOfferingsHistory;
    }

    public HRCourseOffering getSelectedCourseOfferingFromHistory() {
        return selectedCourseOfferingFromHistory;
    }

    public void setSelectedCourseOfferingFromHistory(HRCourseOffering selectedCourseOfferingFromHistory) {
        this.selectedCourseOfferingFromHistory = selectedCourseOfferingFromHistory;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<PrimefacesUploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<PrimefacesUploadedFile> files) {
        this.files = files;
    }

    public List<HRCourseOffering> getPodrejeniPastOfferingsHistory() {
        return podrejeniPastOfferingsHistory;
    }

    public void setPodrejeniPastOfferingsHistory(List<HRCourseOffering> podrejeniPastOfferingsHistory) {
        this.podrejeniPastOfferingsHistory = podrejeniPastOfferingsHistory;
    }

    public HRCourseOffering getSelectedCourseOfferingToBeRepeated() {
        return selectedCourseOfferingToBeRepeated;
    }

    public void setSelectedCourseOfferingToBeRepeated(HRCourseOffering selectedCourseOfferingToBeRepeated) {
        this.selectedCourseOfferingToBeRepeated = selectedCourseOfferingToBeRepeated;
    }

    public List<FileAttachment> getPriloge() {
        return priloge;
    }

    public void setPriloge(List<FileAttachment> priloge) {
        this.priloge = priloge;
    }

    public List<HRCourseOffering> getOfferingsIAttendedAndWill() {
        return offeringsIAttendedAndWill;
    }

    public void setOfferingsIAttendedAndWill(List<HRCourseOffering> offeringsIAttendedAndWill) {
        this.offeringsIAttendedAndWill = offeringsIAttendedAndWill;
    }
    
    

}
