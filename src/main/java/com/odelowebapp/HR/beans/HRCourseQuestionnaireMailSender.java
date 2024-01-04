/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.HRCourseOffering;
import com.odelowebapp.HR.entity.HRCourseType;
import com.odelowebapp.HR.entity.VCodeksUsers;
import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.HRCourseOfferingFacade;
import com.odelowebapp.HR.facade.HRCourseTypeFacade;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.Importance;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

/**
 *
 * @author dematjasic
 */
@Named("hRCourseQuestionnaireMailSender")
@ApplicationScoped
public class HRCourseQuestionnaireMailSender implements Serializable {

    @EJB
    HRCourseAttendanceFacade caFacade;
    @EJB
    HRCourseTypeFacade ctFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private HRCourseOfferingFacade coFacade;

    private List<VCodeksUsers> currentlyEmployed;

    private HashMap<Integer, VCodeksUsers> selectedEmployeesHM = new HashMap<Integer, VCodeksUsers>();

    private ExchangeService es = new ExchangeService();

//    @PostConstruct
//    public void init(){
//        initMail();
//    }
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

    public void sendMailToOne(String cid) {
        System.out.println("Sem v sendMailToOne, sending mail to person:" + cid);
        initMail();
        List<Object[]> returned = caFacade.findAllAttendancesWithoutSolvedQuestionnaire(cid);
        System.out.println("UnsolvedQuestionnaires:" + returned.size());

        StringBuilder sb = new StringBuilder();
        sb.append("Pozdravljeni!<br/>");
        sb.append("<br/>");
        sb.append("Obveščamo vas, da imate na osloWebApp portalu " + returned.size() + singlePlural(returned.size(), " neizpolnjen", " neizpolnjene", "neizpolnjena") + " " + singlePlural(returned.size(), "vprašalnik", "vprašalnike", "vprašalnika") + " (ocenitev izobraževanja)");
        sb.append("<br/>");
        sb.append("Prosimo vas, da " + singlePlural(returned.size(), "ocenitev", "ocenitve", "ocenitvi") + " čimprej izpolnite.");
        sb.append("<br/>");
        sb.append("To lahko storite na naslednji povezavi: " + "<a href=\"http://prapps02.pr.lighting.int:7070/osloWebApp/faces/profileOdelo.xhtml?coid=" + cid + "\"> POVEZAVA ZA OCENITEV IZOBRAŽEVANJA </a>   ");
        sb.append("<br/>");
        sb.append("Vaši neizpolnjeni vprašalniki:");
        sb.append("<br/><ul>");
        String mailosebe = "";
        for (Object[] vrstica : returned) {
            mailosebe = vrstica[11] + "";
            sb.append("<li>");
            sb.append(vrstica[6] + " (datum udeležbe:" + vrstica[7] + " )");
            sb.append("</li>");
        }

        sb.append("</ul><br/>");
        sb.append("<small>Obvestilo je avtomatsko generirano zato nanj ne odgovarjate. Če ste sporočilo prejeli pomotoma se obrnite na skrbnika sistema (HR oddelek)</small>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o neizpolnjenih ocenitvah izobraževanj");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mailosebe);
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            System.out.println("Napaka mail send:" + ex);
        }

    }

    public void sendMailToAll() {
        System.out.println("Sem v sendMailToAll");
        initMail();
        List<Object[]> returned = caFacade.findAllAttendancesWithoutSolvedQuestionnaireEksterna();
        System.out.println("Vrnjenih vseh vrstic:" + returned.size());

        MultivaluedMap<Integer, Object[]> map = new MultivaluedHashMap<>();

        for (Object[] row : returned) {
            //System.out.println("V map dodajam atendanceID:"+row[0]+" codeksID:"+row[1]+" celrow:"+row);
            map.add((Integer) row[1], row);
        }

        Set<Integer> keySet = map.keySet();
        for (Integer i : keySet) {
            System.out.println("Keyset value:" + i);
            sendMailToOneFromAll(map.get(i));
        }

//        System.out.println("MultiMap size:" + map.size());
//        
//        List<Object[]> list288 = map.get(288);
//        
//        System.out.println("288 size:"+list288.size());
//        
//        for(Object[] row: list288){
//            System.out.println("Podatek:"+row[0]+" title:"+row[6]+" priimek:"+row[8]);
//        }
    }

    //uporabno na gumbku ki pošlje vsem, spodaj bo še enaka metoda ki pošlje vsem iz izbranega courseofferinga
    public void sendOpomnikEksternaNaslednjiDan() {
        System.out.println("Sem v sendOpomnikEksternaNaslednjiDan()");
        initMail();
        currentlyEmployed = usersEJB.getAllUsersWithNonexistent();
        napolniCelotenHM();
        List<HRCourseType> coursetypes = ctFacade.getRecursiveCourseHierarchy(2);
        List<HRCourseOffering> findAllCourseOfferingsTomorrow = coFacade.findAllCourseOfferingsTomorrow(coursetypes);
        System.out.println("findAllCourseOfferingsTomorrow.size():" + findAllCourseOfferingsTomorrow.size());

        HashMap<Integer, List<HRCourseAttendance>> groupedAttendances = new HashMap<Integer, List<HRCourseAttendance>>();

        for (HRCourseOffering co : findAllCourseOfferingsTomorrow) {
            List<HRCourseAttendance> hrCourseAttendanceList = co.getHRCourseAttendanceList();

            for (HRCourseAttendance att : hrCourseAttendanceList) {
                // Check if the user ID is already in the HashMap
                if (groupedAttendances.containsKey(att.getCodeksID())) {
                    // User ID is already present, add attendance to the existing list
                    att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                    groupedAttendances.get(att.getCodeksID()).add(att);
                } else {
                    // User ID is not present, create a new list and add attendance
                    List<HRCourseAttendance> newList = new ArrayList<>();
                    att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                    newList.add(att);
                    groupedAttendances.put(att.getCodeksID(), newList);
                }
            }

        }
        System.out.println("Groupped attendaces size:"+groupedAttendances.size());
        
        for (Map.Entry<Integer, List<HRCourseAttendance>> entry : groupedAttendances.entrySet()) {
            int userId = entry.getKey();
            
            StringBuilder sb = new StringBuilder();
            sb.append("Pozdravljeni!<br/>");
            sb.append("<br/>");
            sb.append("Obveščamo vas, da imate za jutrišnji dan planirano eksterno izobraževanje <strong>"+entry.getValue().get(0).getCourseOfferingID().getCourseID().getCourseTitle()+" ("+entry.getValue().get(0).getCourseOfferingID().getCourseDateString()+" - "+entry.getValue().get(0).getCourseOfferingID().getMeetingroomString()+") </strong>");
            sb.append("<br/>");
            sb.append("Prosimo vas, da na osloWebApp portalu, po končanem izobraževanju podate svoj odziv (ali ste se izobraževanja udeležili ali ne).");
            sb.append("<br/>");
            sb.append("<br/>");
            sb.append("<small>Obvestilo je avtomatsko generirano zato nanj ne odgovarjate. Če ste sporočilo prejeli pomotoma se obrnite na skrbnika sistema (HR oddelek)</small>");

            try {
                EmailMessage msg = new EmailMessage(es);
                msg.setImportance(Importance.High);
                msg.setSubject("Prihajajoče eksterno izobraževanje");
                MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
                msg.setBody(mb);
                System.out.println("mail je:" + sb.toString());
                System.out.println("mail dobi:" + entry.getValue().get(0).getOsebaInfo().getEmail());
                msg.getToRecipients().add(entry.getValue().get(0).getOsebaInfo().getEmail());

                msg.sendAndSaveCopy();
            } catch (Exception ex) {
                System.out.println("Napaka mail send:" + ex);
            }
            
        }
        
    }
    
    public void sendOpomnikEksternaIzbrana(List<HRCourseOffering> selection) {
        System.out.println("Sem v sendOpomnikEksternaIzbrana()");
        initMail();
        currentlyEmployed = usersEJB.getAllUsersWithNonexistent();
        napolniCelotenHM();
        List<HRCourseOffering> findAllCourseOfferingsTomorrow = selection;
        System.out.println("findAllCourseOfferingsSelected.size():" + findAllCourseOfferingsTomorrow.size());

        HashMap<Integer, List<HRCourseAttendance>> groupedAttendances = new HashMap<Integer, List<HRCourseAttendance>>();

        for (HRCourseOffering co : findAllCourseOfferingsTomorrow) {
            List<HRCourseAttendance> hrCourseAttendanceList = co.getHRCourseAttendanceList();

            for (HRCourseAttendance att : hrCourseAttendanceList) {
                // Check if the user ID is already in the HashMap
                if (groupedAttendances.containsKey(att.getCodeksID())) {
                    // User ID is already present, add attendance to the existing list
                    att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                    groupedAttendances.get(att.getCodeksID()).add(att);
                } else {
                    // User ID is not present, create a new list and add attendance
                    List<HRCourseAttendance> newList = new ArrayList<>();
                    att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                    newList.add(att);
                    groupedAttendances.put(att.getCodeksID(), newList);
                }
            }

        }
        System.out.println("Groupped attendaces size:"+groupedAttendances.size());
        
        for (Map.Entry<Integer, List<HRCourseAttendance>> entry : groupedAttendances.entrySet()) {
            int userId = entry.getKey();
            
            StringBuilder sb = new StringBuilder();
            sb.append("Pozdravljeni!<br/>");
            sb.append("<br/>");
            sb.append("Obveščamo vas, da imate dne "+entry.getValue().get(0).getCourseOfferingID().getCourseDateString()+" planirano eksterno izobraževanje <strong>"+entry.getValue().get(0).getCourseOfferingID().getCourseID().getCourseTitle()+" ("+entry.getValue().get(0).getCourseOfferingID().getCourseDateString()+" - "+entry.getValue().get(0).getCourseOfferingID().getMeetingroomString()+") </strong>");
            sb.append("<br/>");
            sb.append("Prosimo vas, da na osloWebApp portalu, po končanem izobraževanju podate svoj odziv (ali ste se izobraževanja udeležili ali ne).");
            sb.append("<br/>");
            sb.append("<br/>");
            sb.append("<small>Obvestilo je avtomatsko generirano zato nanj ne odgovarjate. Če ste sporočilo prejeli pomotoma se obrnite na skrbnika sistema (HR oddelek)</small>");

            try {
                EmailMessage msg = new EmailMessage(es);
                msg.setImportance(Importance.High);
                msg.setSubject("Prihajajoče eksterno izobraževanje");
                MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
                msg.setBody(mb);
                System.out.println("mail je:" + sb.toString());
                System.out.println("mail dobi:" + entry.getValue().get(0).getOsebaInfo().getEmail());
                msg.getToRecipients().add(entry.getValue().get(0).getOsebaInfo().getEmail());

                msg.sendAndSaveCopy();
            } catch (Exception ex) {
                System.out.println("Napaka mail send:" + ex);
            }
            
        }
        
    }

    public void sendOpomnikEksternoIzobrazevanjeSamoregistracija() {
        System.out.println("Sem v sendOpomnikEksternoIzobrazevanjeSamoregistracija()");
        initMail();

        currentlyEmployed = usersEJB.getAllUsersWithNonexistent();
        napolniCelotenHM();

        List<HRCourseType> coursetypes = ctFacade.getRecursiveCourseHierarchy(2);
        List<HRCourseAttendance> allUnconfirmedExternalAttendances = caFacade.findAllUnconfirmedExternalAttendances(coursetypes);
        System.out.println("Mail bi se poslal za toliko attendancev: " + allUnconfirmedExternalAttendances.size());

        HashMap<Integer, List<HRCourseAttendance>> groupedAttendances = new HashMap<Integer, List<HRCourseAttendance>>();

        for (HRCourseAttendance att : allUnconfirmedExternalAttendances) {
            System.out.println("mail for course:" + att.getCourseOfferingID().getCourseID().getCourseTitle() + " and performed on:" + att.getCourseOfferingID().getCourseDate() + " person:" + att.getCodeksID() + " which is:" + selectedEmployeesHM.get(att.getCodeksID()).getLastname() + " " + selectedEmployeesHM.get(att.getCodeksID()).getFirstname() + " and MAIL:" + selectedEmployeesHM.get(att.getCodeksID()).getEmail());

            // Check if the user ID is already in the HashMap
            if (groupedAttendances.containsKey(att.getCodeksID())) {
                // User ID is already present, add attendance to the existing list
                att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                groupedAttendances.get(att.getCodeksID()).add(att);
            } else {
                // User ID is not present, create a new list and add attendance
                List<HRCourseAttendance> newList = new ArrayList<>();
                att.setOsebaInfo(selectedEmployeesHM.get(att.getCodeksID()));
                newList.add(att);
                groupedAttendances.put(att.getCodeksID(), newList);
            }

        }

        //List<HRCourseAttendance> dean = groupedAttendances.get(667);
        //System.out.println("Deanovih opomnikov:"+dean.size());
        for (Map.Entry<Integer, List<HRCourseAttendance>> entry : groupedAttendances.entrySet()) {
            int userId = entry.getKey();

//            NASLEDNJI IF ELSE ČE ŽELIŠ TESTIRATI POŠILJANJE SAMO DEANU IN DOMNU
//            if(userId == 667 || userId==1944){
            List<HRCourseAttendance> userAttendanceList = entry.getValue();

            StringBuilder sb = new StringBuilder();
            sb.append("Pozdravljeni!<br/>");
            sb.append("<br/>");
            sb.append("Obveščamo vas, da imate na osloWebApp portalu " + userAttendanceList.size() + singlePlural(userAttendanceList.size(), " eksterno izobraževanje", " eksterna izobraževanja", " eksterni izobraževanji") + " za " + singlePlural(userAttendanceList.size(), "katerega", "katere", "kateri") + " ne poznamo vaše udeležbe.");
            sb.append("<br/>");
            sb.append("Prosimo vas, da na osloWebApp portalu podate svoj odziv (ali ste se izobraževanja udeležili ali ne).");
            sb.append("<br/>");
            sb.append("To lahko storite na naslednjih povezavah: ");
            sb.append("<br/>");
            sb.append("<ul>");

            for (HRCourseAttendance att : userAttendanceList) {
                sb.append("<li>");
                sb.append("<a href=\"http://prapps02.pr.lighting.int:7070/osloWebApp/faces/HR/Courses/selfRegisterExternalCourse.xhtml?attid=" + att.getAttendanceID() + "\"> " + att.getCourseOfferingID().getCourseID().getCourseTitle() + " (" + att.getCourseOfferingID().getCourseDateString() + ") " + " </a>   ");
                sb.append("</li>");
            }
            sb.append("</ul><br/>");
            sb.append("<small>Obvestilo je avtomatsko generirano zato nanj ne odgovarjate. Če ste sporočilo prejeli pomotoma se obrnite na skrbnika sistema (HR oddelek)</small>");

            try {
                EmailMessage msg = new EmailMessage(es);
                msg.setImportance(Importance.High);
                msg.setSubject("Izobraževanja ki potrebujejo vaš odziv");
                MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
                msg.setBody(mb);
                System.out.println("mail je:" + sb.toString());
                System.out.println("mail dobi:" + entry.getValue().get(0).getOsebaInfo().getEmail());
                msg.getToRecipients().add(entry.getValue().get(0).getOsebaInfo().getEmail());

                msg.sendAndSaveCopy();
            } catch (Exception ex) {
                System.out.println("Napaka mail send:" + ex);
            }

//            }else{
//                System.out.println("Ni dean");
//            }
        }

    }

//    private void napolniCelotenHMTrenutni() {
//        System.out.println("Sem v napolniCelotenHMTrenutni");
//        for (VCodeksUsersAktualniZaposleni zap : currentlyEmployed) {
//            selectedEmployeesHM.put(zap.getId(), zap);
//        }
//    }
    private void napolniCelotenHM() {
        System.out.println("Sem v napolniCelotenHM");
        for (VCodeksUsers zap : currentlyEmployed) {
            selectedEmployeesHM.put(zap.getId(), zap);
        }
    }

    public void sendMailToOneFromAll(List<Object[]> data) {
        System.out.println("Sem v sendMailToOneFromAll");
        initMail();
        //List<Object[]> returned = caFacade.findAllAttendancesWithoutSolvedQuestionnaire(cid);       
        String cid = data.get(0)[1] + "";
        System.out.println("UnsolvedQuestionnaires:" + data.size() + " for persons CID:" + cid);

        StringBuilder sb = new StringBuilder();
        sb.append("Pozdravljeni!<br/>");
        sb.append("<br/>");
        sb.append("Obveščamo vas, da imate na osloWebApp portalu " + data.size() + singlePlural(data.size(), " neizpolnjen", " neizpolnjene", "neizpolnjena") + " " + singlePlural(data.size(), "vprašalnik", "vprašalnike", "vprašalnika") + " (ocenitev izobraževanja)");
        sb.append("<br/>");
        sb.append("Prosimo vas, da " + singlePlural(data.size(), "ocenitev", "ocenitve", "ocenitvi") + " čimprej izpolnite.");
        sb.append("<br/>");
        sb.append("To lahko storite na naslednji povezavi: " + "<a href=\"http://prapps02.pr.lighting.int:7070/osloWebApp/faces/profileOdelo.xhtml?coid=" + cid + "\"> POVEZAVA ZA OCENITEV IZOBRAŽEVANJA </a>   ");
        sb.append("<br/>");
        sb.append("Vaši neizpolnjeni vprašalniki:");
        sb.append("<br/><ul>");
        String mailosebe = "";
        for (Object[] vrstica : data) {
            mailosebe = vrstica[11] + "";
            sb.append("<li>");
            sb.append(vrstica[6] + " (datum udeležbe:" + vrstica[7] + " )");
            sb.append("</li>");
        }

        sb.append("</ul><br/>");
        sb.append("<small>Obvestilo je avtomatsko generirano zato nanj ne odgovarjate. Če ste sporočilo prejeli pomotoma se obrnite na skrbnika sistema (HR oddelek)</small>");

        try {
            EmailMessage msg = new EmailMessage(es);
            msg.setImportance(Importance.High);
            msg.setSubject("Obvestilo o neizpolnjenih ocenitvah izobraževanj");
            MessageBody mb = new MessageBody(BodyType.HTML, sb.toString());
            msg.setBody(mb);
            msg.getToRecipients().add(mailosebe);
            //msg.getToRecipients().add("dean.matjasic@odelo.si");
            msg.sendAndSaveCopy();
        } catch (Exception ex) {
            System.out.println("Napaka mail send:" + ex);
        }

    }

    public String singlePlural(int count, String singular, String plural, String doubles) {
        //return count == 1 ? singular : plural;
        if (count == 1) {
            return singular;
        } else if (count == 2) {
            return doubles;
        } else {
            return plural;
        }
    }
}
