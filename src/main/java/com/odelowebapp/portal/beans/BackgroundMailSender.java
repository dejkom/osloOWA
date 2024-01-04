/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import com.odelowebapp.HR.beans.HRCourseQuestionnaireMailSender;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author dematjasic
 */
@Singleton
public class BackgroundMailSender {
    
    @Inject
    HRCourseQuestionnaireMailSender ms;
    
//    @Schedule(hour="0", minute="0", second="0", persistent=false)
//    public void someDailyJob() {
//        // Do your job here which should run every start of day.
//    }
//
//    @Schedule(hour="*/1", minute="0", second="0", persistent=false)
//    public void someHourlyJob() {
//        // Do your job here which should run every hour of day.
//    }
//
//    @Schedule(hour="*", minute="*/15", second="0", persistent=false)
//    public void someQuarterlyJob() {
//        // Do your job here which should run every 15 minute of hour.
//    }
    
    //Zaenkrat za test, pošiljanje samo Deanu, da spremljam delovanje ApplicationScoped
    @Schedule(dayOfWeek = "Fri", hour="7", minute="0", second="0", persistent=false, timezone = "Europe/Ljubljana")
    public void someMinutelyJob() {
        Date now = new Date();
        System.out.println("someMinutelyJob: "+now.toString());
        //HRCourseQuestionnaireMailSender ms = new HRCourseQuestionnaireMailSender();
        //ms.sendMailToOne("667");
        ms.sendMailToAll();
    }
    
    @Schedule(dayOfWeek = "Fri", hour="7", minute="0", second="0", persistent=false, timezone = "Europe/Ljubljana")
    public void sendOpomnikEksternoIzobrazevanjeSamoregistracija() {
        Date now = new Date();
        System.out.println("sendOpomnikEksternoIzobrazevanjeSamoregistracija: "+now.toString());
        //HRCourseQuestionnaireMailSender ms = new HRCourseQuestionnaireMailSender();
        //ms.sendMailToOne("667");
        ms.sendOpomnikEksternoIzobrazevanjeSamoregistracija();
    }
    
    @Schedule(dayOfMonth = "6", month = "10", year = "2023",hour = "10", minute = "0",second="0", persistent = false, timezone = "Europe/Ljubljana")
    public void sendOpomnikEksternoIzobrazevanjeSamoregistracijaPrvic() {
        Date now = new Date();
        System.out.println("sendOpomnikEksternoIzobrazevanjeSamoregistracijaPrvic: "+now.toString());
        //HRCourseQuestionnaireMailSender ms = new HRCourseQuestionnaireMailSender();
        //ms.sendMailToOne("667");
        ms.sendOpomnikEksternoIzobrazevanjeSamoregistracija();
    }

    //@Schedule(hour = "*", minute = "*/3", persistent = false, timezone = "Europe/Ljubljana")
//    @Schedule(dayOfMonth = "16", month = "11", year = "2022",hour = "10", minute = "30",second="0", persistent = false, timezone = "Europe/Ljubljana")
//    public void everyMinute(){
//        Date now = new Date();
//        System.out.println("only once(): "+now.toString());
//        ms.sendMailToAll();
//    }
    
}
