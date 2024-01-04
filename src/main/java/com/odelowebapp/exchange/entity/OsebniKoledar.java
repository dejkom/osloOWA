/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.exchange.entity;

/**
 *
 * @author dematjasic
 */
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.response.CancelMeetingMessage;
import microsoft.exchange.webservices.data.core.service.schema.AppointmentSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.joda.time.DateTime;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import microsoft.exchange.webservices.data.core.enumeration.permission.folder.DelegateFolderPermissionLevel;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.enumeration.service.MeetingRequestsDeliveryScope;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsOrCancellationsMode;
import microsoft.exchange.webservices.data.core.response.DelegateUserResponse;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.DelegateUser;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.primefaces.PrimeFaces;
//import si.odelo.uporabnik.beans.UporabnikovaSeja;

@Named
@RequestScoped
public class OsebniKoledar implements Serializable {

    private ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);

    private ScheduleModel eventModel;

    private ScheduleEvent<?> event = new DefaultScheduleEvent();

//    @ManagedProperty(value = "#{uporabnikovaSeja}")
//    UporabnikovaSeja uporabnikovaseja;
    ArrayList<Appointment> appointmenti;
    List<Attendee> odziviRequiredAttendees = new ArrayList();

    @Inject
    private SecurityContext securityContext;
    PrimeFaces current;

    @PostConstruct
    public void init() {
        try {
            connect2Exchange();
            //createTestAppointment();
            appointmenti = readAppointments();

            eventModel = new DefaultScheduleModel();

            for (int i = 0; i < appointmenti.size(); i++) {
                //eventModel.addEvent(new DefaultScheduleEvent(appointmenti.get(i).getSubject(), appointmenti.get(i).getStart(), appointmenti.get(i).getEnd(), appointmenti.get(i)));

                event = DefaultScheduleEvent.builder()
                        .title(appointmenti.get(i).getSubject())
                        .startDate(convertToLocalDateTimeViaInstant(appointmenti.get(i).getStart()))
                        .endDate(convertToLocalDateTimeViaInstant(appointmenti.get(i).getEnd()))
                        .borderColor("red")
                        .id(appointmenti.get(i).getId().getUniqueId())
                        .build();

                eventModel.addEvent(event);
            }
        } catch (Exception e) {
            System.out.println("Napaka OsebniKoledar:" + e);
        }

    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void reloadEventModel() {
        try {
            appointmenti = readAppointments();

            eventModel = new DefaultScheduleModel();
            for (int i = 0; i < appointmenti.size(); i++) {
                event = DefaultScheduleEvent.builder()
                        .title(appointmenti.get(i).getSubject())
                        .startDate(convertToLocalDateTimeViaInstant(appointmenti.get(i).getStart()))
                        .endDate(convertToLocalDateTimeViaInstant(appointmenti.get(i).getEnd()))
                        .build();

                eventModel.addEvent(event);
                //eventModel.addEvent(new DefaultScheduleEvent(appointmenti.get(i).getSubject(), appointmenti.get(i).getStart(), appointmenti.get(i).getEnd(), appointmenti.get(i)));
            }
        } catch (Exception e) {
            System.out.println("Napaka OsebniKoledar:" + e);
        }
    }

    public void connect2Exchange() {
        try {
            String pass = "portal1010";
            String uname = "portal";
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            service.setUrl(new URI("https://hfmail01.sw.lighting.int/ews/Exchange.asmx"));
            ExchangeCredentials credentials = new WebCredentials(uname, pass);
            service.setCredentials(credentials);

        } catch (Exception ex) {
            Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connect2ExchangeIzobrazevanjeKadrovska() {
        try {
            String pass = "odelohr2401";
            String uname = "izobrazevanje";
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            service.setUrl(new URI("https://hfmail01.sw.lighting.int/ews/Exchange.asmx"));
            ExchangeCredentials credentials = new WebCredentials(uname, pass);
            service.setCredentials(credentials);

        } catch (Exception ex) {
            Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createTestAppointment() {
        try {
            // Create the appointment.
            Appointment appointment = new Appointment(service);
            // Set properties on the appointment.
            appointment.setSubject("Test Appointment");
            MessageBody mb = new MessageBody();
            mb.setBodyType(BodyType.HTML);
            mb.setText("Testni appointment - ustvarjen programsko");
            appointment.setBody(mb);

            DateTime st = new DateTime();
            st.plusHours(1);
            DateTime en = new DateTime();
            en.plusHours(2);
            en.plusMinutes(15);

            appointment.setStart(st.toDate());
            appointment.setEnd(en.toDate());

            appointment.setLocation("Test Office");
            appointment.getRequiredAttendees().add("dean.matjasic@odelo.si");
            appointment.getRequiredAttendees().add("domen.razpotnik@odelo.si");

            // Save the appointment.
            appointment.save(SendInvitationsMode.SendToAllAndSaveCopy);
        } catch (Exception ex) {
            Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createAppointmentIzobrazevanje(String subject, String message, Date start, Date end, String location, List<String> mails, String instructorMail) {
        try {
            // Create the appointment.
            Appointment appointment = new Appointment(service);

            // Set properties on the appointment.
            appointment.setSubject(subject);
            MessageBody mb = new MessageBody();
            mb.setBodyType(BodyType.HTML);
            mb.setText(message);
            appointment.setBody(mb);
            appointment.setStart(start);
            appointment.setEnd(end);
            appointment.setAllowNewTimeProposal(Boolean.FALSE);
            appointment.setLocation(location);
            appointment.setReminderMinutesBeforeStart(30);
            for (String mail : mails) {
                appointment.getRequiredAttendees().add(mail);
                //appointment.setConferenceType(0);
            }
            
            //med udeležence dodamo tudi predavatelja
            appointment.getRequiredAttendees().add(instructorMail);
            
            Mailbox mailbox = new Mailbox("Portal@odelo.si");
            
            System.out.println("Shraniti moram v mailbox izobraževalca:"+instructorMail);
            //Mailbox organizer = new Mailbox(instructorMail);
            Mailbox organizer = new Mailbox("izobrazevanje.hr@odelo.si");

//            DelegateUser du = new DelegateUser(instructorMail);
//
//            du.getPermissions().setCalendarFolderPermissionLevel(DelegateFolderPermissionLevel.Editor);
//            Collection<DelegateUserResponse> addDelegates = service.addDelegates(mailbox, MeetingRequestsDeliveryScope.DelegatesAndSendInformationToMe, du);
//            for (DelegateUserResponse dur : addDelegates) {
//                System.out.println("DUR:" + dur.getResult().name());
//            }

            //appointment.setIsOnlineMeeting(Boolean.FALSE);
            // Save the appointment.
            //appointment.save(SendInvitationsMode.SendToAllAndSaveCopy);
            FolderId fi = new FolderId(WellKnownFolderName.Calendar, organizer);
            
            //fi2 gre v folder portal, samo da se pokaže v koledarju na index strani
            //FolderId fi2 = new FolderId(WellKnownFolderName.Calendar, mailbox);
            
            appointment.save(fi, SendInvitationsMode.SendOnlyToAll);
            //appointment.save(fi2, SendInvitationsMode.SendToNone);
            //appointment.save(SendInvitationsMode.SendOnlyToAll);

            reloadEventModel();

        } catch (Exception ex) {
            System.out.println("Napaka EWS: " + ex);
        }
    }
    
    public void createAppointmentIzobrazevanjePortalMailbox(String subject, String message, Date start, Date end, String location, List<String> mails, String instructorMail) {
        try {
            // Create the appointment.
            Appointment appointment = new Appointment(service);

            // Set properties on the appointment.
            appointment.setSubject(subject);
            MessageBody mb = new MessageBody();
            mb.setBodyType(BodyType.HTML);
            mb.setText(message);
            appointment.setBody(mb);
            appointment.setStart(start);
            appointment.setEnd(end);
            appointment.setAllowNewTimeProposal(Boolean.FALSE);
            appointment.setLocation(location);
            appointment.setReminderMinutesBeforeStart(30);
//            for (String mail : mails) {
//                appointment.getRequiredAttendees().add(mail);
//                //appointment.setConferenceType(0);
//            }
            
            //med udeležence dodamo tudi predavatelja
            appointment.getRequiredAttendees().add(instructorMail);
            
            Mailbox mailbox = new Mailbox("Portal@odelo.si");
            
            System.out.println("Shraniti moram v mailbox izobraževalca:"+instructorMail);

            FolderId fi = new FolderId(WellKnownFolderName.Calendar, mailbox);
            
            appointment.save(fi, SendInvitationsMode.SendToNone);

            reloadEventModel();

        } catch (Exception ex) {
            System.out.println("Napaka EWS: " + ex);
        }
    }

    public void register2Appointment() {
        System.out.println("Sem v register2Appointemnt(), prijavljaš se na event: " + event.getTitle() + " od:" + event.getStartDate() + " do:" + event.getEndDate());
    }

    public void checkEventResponses(String input) {
        System.out.println("Sem v checkEventResponses");
        try {
            odziviRequiredAttendees.clear();
            //Appointment tbd = (Appointment) event.getData();
            //System.out.println("Brisati moram appointment z idjem:" + tbd.getId());
            ItemId iid = new ItemId(input);

            Appointment aptItem = Appointment.bind(service, iid);

            for (int i = 0; i < aptItem.getRequiredAttendees().getCount(); i++) {
                System.out.println("Required attendee - " + aptItem.getRequiredAttendees().getItems().get(i).getAddress() + ": " + aptItem.getRequiredAttendees().getItems().get(i).getResponseType().name());
                odziviRequiredAttendees.add(aptItem.getRequiredAttendees().getItems().get(i));
            }

            reloadEventModel();

        } catch (ServiceLocalException ex) {
            Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequiresRoles("IT_ADMIN")
    public void deleteAppointment(String input) {
        //System.out.println("Sem v deleteAppointment(): input = "+input+" konec");
        //check if user has permission to DELETE appointment
        Subject currentUser = SecurityUtils.getSubject();

        //System.out.println("User:" + currentUser.getSession().getAttribute("username")+" EducationsAdmin:"+currentUser.hasRole("OWA_EDUCATIONS_ADMIN")+" IT_ADMIN:"+currentUser.hasRole("IT_ADMIN"));
//        System.out.println("Subject currentUser:" + currentUser.getSession().getAttribute("username"));
//        System.out.println("OWA_EDUCATIONS_ADMIN:"+securityContext.isCallerInRole("OWA_EDUCATIONS_ADMIN"));
//        System.out.println("IT_ADMIN:"+securityContext.isCallerInRole("IT_ADMIN"));
        if (securityContext.isCallerInRole("OWA_EDUCATIONS_ADMIN") || securityContext.isCallerInRole("IT_ADMIN")) {
            try {

                //Appointment tbd = (Appointment) event.getData();
                //System.out.println("Brisati moram appointment z idjem:" + tbd.getId());
                ItemId iid = new ItemId(input);

                Appointment aptItem = Appointment.bind(service, iid);

                //Appointment meeting = Appointment.Bind(service, meetingId, new PropertySet());
                // Delete the meeting by using the CreateCancelMeetingMessage method.
                CancelMeetingMessage cancelMessage = new CancelMeetingMessage(aptItem);
                cancelMessage.setBody(new MessageBody("Izobraževanje je bilo preklicano. To sporočilo je generirano avtomatsko, nanj prosim ne odgovarjajte."));

                cancelMessage.sendAndSaveCopy();

                aptItem.update(ConflictResolutionMode.AutoResolve, SendInvitationsOrCancellationsMode.SendToNone);

                reloadEventModel();

            } catch (ServiceLocalException ex) {
                Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Uporabnik nima pravic za brisanje dogodka na Exchangeu");
            addMessage(FacesMessage.SEVERITY_WARN, "Warn Message", "No permissions to perform this action");
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public ArrayList<Appointment> readAppointments() {

        List apntmtDataList = new ArrayList();
        Calendar now = Calendar.getInstance();
        //Date startDate = Calendar.getInstance().getTime();
        DateTime startd = new DateTime().minusMonths(2);
        Date startDate = startd.toDate();
        now.add(Calendar.DATE, 30);
        Date endDate = now.getTime();
        System.out.println("Iscem appointmente startDate:" + startDate + " endDate:" + endDate);
        try {
            CalendarFolder calendarFolder = CalendarFolder.bind(service, WellKnownFolderName.Calendar, new PropertySet());
            //CalendarView cView = new CalendarView(startDate, endDate, 5);
            CalendarView cView = new CalendarView(startDate, endDate);
            cView.setPropertySet(new PropertySet(AppointmentSchema.Subject, AppointmentSchema.Start, AppointmentSchema.End));// we can set other properties as well depending upon our need.
            FindItemsResults<Appointment> appointments = calendarFolder.findAppointments(cView);
            System.out.println("Vrnjenih appointmentov:" + appointments.getItems().size());
            for (Appointment appointment : appointments.getItems()) {
                System.out.println("Appointment:" + appointment.getSubject() + " | " + appointment.getStart() + " | " + appointment.getEnd() + " | " + appointment.getId());
            }
            return appointments.getItems();
        } catch (Exception e) {
            System.out.println("Napaka uporabnikovaSeja - readAppointments():" + e);
            return null;
        }

    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

//    public UporabnikovaSeja getUporabnikovaseja() {
//        return uporabnikovaseja;
//    }
//
//    public void setUporabnikovaseja(UporabnikovaSeja uporabnikovaseja) {
//        this.uporabnikovaseja = uporabnikovaseja;
//    }
    public ArrayList<Appointment> getAppointmenti() {
        return appointmenti;
    }

    public void setAppointmenti(ArrayList<Appointment> appointmenti) {
        this.appointmenti = appointmenti;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
            try {
                Appointment nov = new Appointment(service);
                nov.setStart(convertToDateViaInstant(event.getStartDate()));
                nov.setEnd(convertToDateViaInstant(event.getEndDate()));
                nov.setSubject(event.getTitle());
                nov.save();
            } catch (Exception ex) {
                Logger.getLogger(OsebniKoledar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        //event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        event = DefaultScheduleEvent.builder()
                .title("Nov dogodek")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        FacesMessage message = new FacesMessage("Warning", "Event resized");
        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Attendee> getOdziviRequiredAttendees() {
        return odziviRequiredAttendees;
    }

    public void setOdziviRequiredAttendees(List<Attendee> odziviRequiredAttendees) {
        this.odziviRequiredAttendees = odziviRequiredAttendees;
    }

    public PrimeFaces getCurrent() {
        return current;
    }

    public void setCurrent(PrimeFaces current) {
        this.current = current;
    }

}
