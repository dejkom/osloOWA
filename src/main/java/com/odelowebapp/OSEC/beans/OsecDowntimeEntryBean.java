/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.beans;

import com.odelowebapp.HR.beans.JantarPINConvert;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.OSEC.controller.OSECDowntimeController;
import com.odelowebapp.OSEC.entity.OSECDowntime;
import com.odelowebapp.OSEC.entity.OSECDowntimeReasons;
import com.odelowebapp.OSEC.entity.OSECLine;
import com.odelowebapp.OSEC.entity.OSECMachine;
import com.odelowebapp.OSEC.entity.OSECloginLog;
import com.odelowebapp.OSEC.facade.OSECDowntimeFacade;
import com.odelowebapp.OSEC.facade.OSECDowntimeReasonsFacade;
import com.odelowebapp.OSEC.facade.OSECLineFacade;
import com.odelowebapp.OSEC.facade.OSECMachineFacade;
import com.odelowebapp.OSEC.facade.OSECloginLogFacade;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class OsecDowntimeEntryBean implements Serializable {

    private String lineIN;

    private String krneki;
    private OSECLine foundLine;
    private String kljucekString;
    private String izbrancitalec;

    @EJB
    private OSECLineFacade lineFacade;
    @EJB
    private OSECMachineFacade machineFacade;
    @EJB
    private OSECDowntimeFacade downtimeFacade;
    @EJB
    private OSECDowntimeReasonsFacade downtimereasonsFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private OSECloginLogFacade llFacade;

    private String shiftSelected;
    private String workorderScanned;

    final JantarPINConvert jpc = new JantarPINConvert();

    List<OSECDowntime> downtimesForSelectedLine;
    OSECDowntime newDowntime;
    int lineINInt;

    private List<OSECDowntimeReasons> possibleDowntimes = new ArrayList();

    private List<String> privilegedUsersForDowntimeEntry = new ArrayList();
    ;
    private List<OSECMachine> allMachinesForPlace;
    private List<OSECloginLog> allLoggedInOnLine = new ArrayList();
    private List<String> allLoggedInExternalIDS = new ArrayList();

    HashMap<Integer, VCodeksUsersAktualniZaposleni> vuserjiHM;

    // Get the current user's Subject object
    Subject currentUser = SecurityUtils.getSubject();

    PrimeFaces current;

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() OsecDowntimeEntryBean");
        current = PrimeFaces.current();

        lineIN = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("line");
        System.out.println("linija iz parametra:" + lineIN);

        lineINInt = Integer.parseInt(lineIN);

        String inCookie=null;
        String inCookieLine = readCookie("OSECLINE");
        if (inCookieLine == null) {
            createCookie("OSECLINE", lineIN, 604800); // 7 days expiry
        } else {
            if (inCookieLine.equals(lineIN)) {
                //do nothing
                inCookie = readCookie("OSECWORKORDER");
            } else {
                createCookie("OSECLINE", lineIN, 604800); // 7 days expiry
                deleteCookie("OSECWORKORDER");
            }
        }

        //String inCookie = readCookie("OSECWORKORDER");
        System.out.println("IN COOKIE:"+inCookie);        
        if (inCookie == null || inCookie.isEmpty()) {
            System.out.println("COOKIE NE OBSTAJA");
            workorderScanned = "";
        } else {
            System.out.println("COOKIE OBSTAJA");
            workorderScanned = readCookie("OSECWORKORDER");
        }

        // Check if the user has the "admin" role
        if (currentUser.hasRole("OWA_OSEC")) {
            System.out.println("User je admin");
        } else {
            System.out.println("User NI admin");
        }

        foundLine = lineFacade.find(lineINInt);
        System.out.println("Ime najdene linije:" + foundLine.getLineName());

        initEmptyDowntime();

        shiftSelected = newDowntime.getShift() + "";

        //downtimesForSelectedLine = downtimeFacade.findDowntimesForLine(lineINInt);
        downtimesForSelectedLine = downtimeFacade.findDowntimesForLineCurrentShift(lineINInt, newDowntime.getShift(), newDowntime.getDate());

        izbrancitalec = "elatec";

        allMachinesForPlace = machineFacade.findAllMachinesForPlace(lineINInt);

        privilegedUsersForDowntimeEntry = downtimeFacade.findPrivilegedUsersForDowntimeEntry();
        for (String s : privilegedUsersForDowntimeEntry) {
            System.out.println("PRIVILEGED:" + s);
        }

        pridobiVsePrijavljeneSedaj();

    }

    public void workorderScannedEvent() {
        System.out.println("Sem v workorderScannedEvent, nalog je:" + workorderScanned);
        createCookie("OSECWORKORDER", workorderScanned, 604800); // 7 days expiry

    }

    private String operaterKljucekIn;

    public void operaterPrijava(AjaxBehaviorEvent event) {
        System.out.println("Sem v operaterPrijava() kljucek in:" + operaterKljucekIn);
        System.out.println("Izbrana izmena:" + shiftSelected);

        DateTime now = new DateTime();
        VCodeksUsersAktualniZaposleni user = usersEJB.findUserByCard(operaterKljucekIn);
        System.out.println("USER:" + user.getLastname() + " " + user.getFirstname() + " " + user.getExternalId());

        //pridobim vse sedaj prijavljene
        pridobiVsePrijavljeneSedaj();
        //pogledam če je oseba ki sedaj skenira že na tem seznamu prijavljenih
        if (allLoggedInExternalIDS.contains(user.getExternalId())) {
            System.out.println("Uporabnik je že PRIJAVLJEN");
            OSECloginLog vrstica = new OSECloginLog();
            for (OSECloginLog l : allLoggedInOnLine) {
                if (l.getExternalId().equals(user.getExternalId())) {
                    vrstica = l;
                }
            }
            vrstica.setLoginEnd(new Date());
            llFacade.edit(vrstica);

        } else {
            System.out.println("Uporabnik ŠE NI PRIJAVLJEN");
            OSECloginLog vrstica = new OSECloginLog();
            vrstica.setLineID(lineINInt);
            vrstica.setWorkerCard(user.getCard()); //tukaj pride kar bo prebral skener
            vrstica.setExternalId(user.getExternalId());
            vrstica.setLoginDate(now.toLocalDate().toDate());

            vrstica.setLoginShift(Integer.parseInt(getShiftSelected()));
            vrstica.setLoginStart(new Date());
            llFacade.create(vrstica);
        }
        //če osebe ni jo ustvarim
        //če oseba je jo odjavim

        operaterKljucekIn = "";
        pridobiVsePrijavljeneSedaj();

    }

    public void izmenaChange() {
        System.out.println("sem v izmenaChange:" + shiftSelected);

    }

    public void pridobiVsePrijavljeneSedaj() {
        System.out.println("Sem v pridobiVsePrijavljeneSedaj()");
        //allLoggedInOnLine = llFacade.findAllLoggedInOnLine(lineINInt, newDowntime.getShift());
        allLoggedInOnLine = llFacade.findAllLoggedInOnLineIgnoreShift(lineINInt);
        allLoggedInExternalIDS = allLoggedInOnLine.stream().map(OSECloginLog::getExternalId).collect(Collectors.toList());
    }

    @Inject
    OSECDowntimeController dtct;

    public void dtStatusChangeEvent() {
        //System.out.println("Sem v dtStatusChangeEvent() - status:"+dtct.getSelected().getStatus()+" idzastoja:"+dtct.getSelected().getDowntimeID());
        String cid = currentUser.getSession().getAttribute("externalid") + "";
        dtct.getSelected().setUserConfirming(cid);
        dtct.update();
    }

    public void insertLunchDowntime() {
        System.out.println("Sem v insertLunchDowntime()");
        initMalicaDowntime();
    }

    public boolean isAdmin() {
        return currentUser.hasRole("OWA_OSEC");
    }

    public void onDowntimeTypeSelect() {
        System.out.println("Sem v onDowntimeTypeSelect()");
        possibleDowntimes = downtimereasonsFacade.findAllActiveReasonsByType(newDowntime.getPlannedUnplanned());
    }

    public void vpisSKljuckom() {
        System.out.println("Sem v vpisSKljuckom()");
        System.out.println("Vpisan kljucek:" + kljucekString);

        if (!kljucekString.isEmpty()) {

            //TODO IMPLEMENTIRAJ LOGIKO GLEDE NA IZBRANČITALEC, TRENUTNO DELUJE SAMO DESNI
            if (izbrancitalec.equals("emcard")) {
                String k = jpc.pretvoriKljucekVJantarOblikoPublic(kljucekString);
                System.out.println("EMCARD PRETVORJENO V JANTAR OBLIKO:" + k);
                //prebran = usersEJB.findUserByCard(k);
            } else {
                //prebran = usersEJB.findUserByCard(chip);
                System.out.println("CITALEC ELATEC, prebrano:" + kljucekString);
            }

            VCodeksUsersAktualniZaposleni user = usersEJB.findUserByCard(kljucekString);
            System.out.println("USER:" + user.getLastname() + " " + user.getFirstname() + " " + user.getExternalId());

            newDowntime.setUserEntering(user.getExternalId());

            if (privilegedUsersForDowntimeEntry.contains(user.getExternalId())) {
                System.out.println("UPORABNIK KI VNASA ZASTOJ OSEC JE PRIVILIGIRAN");
                newDowntime.setStatus(2);
            } else {
                System.out.println("UPORABNIK KI VNASA ZASTOJ OSEC NI PRIVILIGIRAN");
                newDowntime.setStatus(0);
            }

            //Ce ne uporabljamo prijave s kljuckom odkomentiramo naslednjo vrstico
            //newDowntime.setUserEntering("NO_LOGIN");
            kljucekString = "";
            saveDowntime();
            current.executeScript("PF('OSECDowntimePrijavaDialog').hide(); PF('OSECDowntimeCreateDialog').hide()");

        } else {
            //kljucek ni vpisan
        }

    }

    public void shiftSelected() {
        System.out.println("Sem v shiftSelected()");
        DateTime odredbaDatumOd = new DateTime(newDowntime.getDate());
        DateTime odredbaDatumDo = new DateTime(newDowntime.getDate());
        DateTime odredbaDatumDoDuration = new DateTime(newDowntime.getDate());
        if (newDowntime.getShift() == 1) {
            odredbaDatumOd = odredbaDatumOd.withTime(6, 0, 0, 0);
            odredbaDatumDo = odredbaDatumDo.withTime(14, 0, 0, 0);
            odredbaDatumDoDuration = odredbaDatumOd.plusMinutes(newDowntime.getDuration());

        } else if (newDowntime.getShift() == 2) {
            odredbaDatumOd = odredbaDatumOd.withTime(14, 0, 0, 0);
            odredbaDatumDo = odredbaDatumDo.withTime(22, 0, 0, 0);
            odredbaDatumDoDuration = odredbaDatumOd.plusMinutes(newDowntime.getDuration());
        } else if (newDowntime.getShift() == 3) {
            odredbaDatumOd = odredbaDatumOd.withTime(22, 0, 0, 0);
            odredbaDatumDo = odredbaDatumDo.withTime(6, 0, 0, 0);
            //odredbaDatumDo = odredbaDatumDo.plusDays(1);
            odredbaDatumDoDuration = odredbaDatumOd.plusMinutes(newDowntime.getDuration());
        } else {
//            System.out.println("shiftSelected unreachable loop");
        }
        newDowntime.setStartD(odredbaDatumOd.toDate());
        //newDowntime.setEndD(odredbaDatumDo.toDate());
        newDowntime.setEndD(odredbaDatumDoDuration.toDate());
    }

    public void saveDowntime() {
        System.out.println("Sem v saveDowntime()");
        downtimeFacade.create(newDowntime);
        initEmptyDowntime();
        //downtimesForSelectedLine = downtimeFacade.findDowntimesForLine(lineINInt);
        downtimesForSelectedLine = downtimeFacade.findDowntimesForLineCurrentShift(lineINInt, newDowntime.getShift(), newDowntime.getDate());
    }

    public void initEmptyDowntime() {
        newDowntime = new OSECDowntime();
        newDowntime.setWorkOrder(workorderScanned);
        newDowntime.setDuration(0);
        newDowntime.setSource("manual");
        newDowntime.setStatus(0);
        DateTime now = new DateTime();

        System.out.println("NOW hour of day:" + now.getHourOfDay());
        if (now.getHourOfDay() < 6) {
            now = now.minusDays(1);
            now = now.withTime(22, 0, 0, 0);
            newDowntime.setShift(3);
            newDowntime.setDate(now.toDate());
            System.out.println("Setting -1 day so result is:" + now.toDate().toString());
        } else if (now.getHourOfDay() < 14) {
            newDowntime.setShift(1);
            now = now.withTime(6, 0, 0, 0);
            newDowntime.setDate(now.toDate());
        } else if (now.getHourOfDay() < 22) {
            newDowntime.setShift(2);
            now = now.withTime(14, 0, 0, 0);
            newDowntime.setDate(now.toDate());
        } else {
            //now.minusDays(1);
            now = now.withTime(22, 0, 0, 0);
            newDowntime.setShift(3);
            newDowntime.setDate(now.toDate());
        }

        shiftSelected();
    }

    public void initMalicaDowntime() {
        newDowntime = new OSECDowntime();
        newDowntime.setDuration(30);
        newDowntime.setWorkOrder(workorderScanned);
        newDowntime.setSource("manual");
        newDowntime.setStatus(0);
        DateTime now = new DateTime();

        System.out.println("NOW hour of day:" + now.getHourOfDay());
        if (now.getHourOfDay() < 6 || now.getHourOfDay() >= 22) {
            now.minusDays(1);
            now.withTime(22, 0, 0, 0);
            newDowntime.setShift(3);
            newDowntime.setDate(now.toLocalDate().toDate());
        } else if (now.getHourOfDay() >= 6 && now.getHourOfDay() < 14) {
            newDowntime.setShift(1);
            newDowntime.setDate(now.toLocalDate().toDate());
        } else if (now.getHourOfDay() >= 14 && now.getHourOfDay() < 22) {
            newDowntime.setShift(2);
            newDowntime.setDate(now.toLocalDate().toDate());
        }
        System.out.println("----sem v initMalicaDowntime getDate:" + newDowntime.getDate());
        shiftSelected();
        newDowntime.setPlannedUnplanned("P");
        newDowntime.setComment("Malica - gumb");
        OSECDowntimeReasons malicaZastoj = downtimereasonsFacade.find(41);
        newDowntime.setReasonIDfk(malicaZastoj);
        newDowntime.setOSECMachine(allMachinesForPlace.get(0));
        System.out.println("----sem v initMalicaDowntime getDate drugic:" + newDowntime.getDate());
    }

    public String getKrneki() {
        return krneki;
    }

    public void setKrneki(String krneki) {
        this.krneki = krneki;
    }

    public OSECLine getFoundLine() {
        return foundLine;
    }

    public void setFoundLine(OSECLine foundLine) {
        this.foundLine = foundLine;
    }

    public OSECDowntime getNewDowntime() {
        return newDowntime;
    }

    public void setNewDowntime(OSECDowntime newDowntime) {
        this.newDowntime = newDowntime;
    }

    public List<OSECDowntime> getDowntimesForSelectedLine() {
        return downtimesForSelectedLine;
    }

    public void setDowntimesForSelectedLine(List<OSECDowntime> downtimesForSelectedLine) {
        this.downtimesForSelectedLine = downtimesForSelectedLine;
    }

    public String getKljucekString() {
        return kljucekString;
    }

    public void setKljucekString(String kljucekString) {
        this.kljucekString = kljucekString;
    }

    public String getIzbrancitalec() {
        return izbrancitalec;
    }

    public void setIzbrancitalec(String izbrancitalec) {
        this.izbrancitalec = izbrancitalec;
    }

    public List<OSECDowntimeReasons> getPossibleDowntimes() {
        return possibleDowntimes;
    }

    public void setPossibleDowntimes(List<OSECDowntimeReasons> possibleDowntimes) {
        this.possibleDowntimes = possibleDowntimes;
    }

    public List<OSECloginLog> getAllLoggedInOnLine() {
        return allLoggedInOnLine;
    }

    public void setAllLoggedInOnLine(List<OSECloginLog> allLoggedInOnLine) {
        this.allLoggedInOnLine = allLoggedInOnLine;
    }

    public List<String> getAllLoggedInExternalIDS() {
        return allLoggedInExternalIDS;
    }

    public void setAllLoggedInExternalIDS(List<String> allLoggedInExternalIDS) {
        this.allLoggedInExternalIDS = allLoggedInExternalIDS;
    }

    public String getOperaterKljucekIn() {
        return operaterKljucekIn;
    }

    public void setOperaterKljucekIn(String operaterKljucekIn) {
        this.operaterKljucekIn = operaterKljucekIn;
    }

    public String getShiftSelected() {
        return shiftSelected;
    }

    public void setShiftSelected(String shiftSelected) {
        this.shiftSelected = shiftSelected;
    }

    public String getWorkorderScanned() {
        return workorderScanned;
    }

    public void setWorkorderScanned(String workorderScanned) {
        this.workorderScanned = workorderScanned;
    }

    //--COOKIE METHODS
    public void createCookie(String name, String value, int maxAge) {
        //604800s = 7 dni
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge); // Max age in seconds
        cookie.setPath("/"); // Accessible within the whole application

        // Add the cookie to the response
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public String readCookie(String name) {
        Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void updateCookie(String name, String newValue, int maxAge) {
        //To update a cookie, you essentially create a new cookie with the same name and different value or properties:
        createCookie(name, newValue, maxAge);
    }

    public void deleteCookie(String name) {
        createCookie(name, "", 0);
    }

    //--END COOKIE METHODS
}
