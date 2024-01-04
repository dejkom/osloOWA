/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author dematjasic
 */
//@SqlResultSetMapping(name = "questionnaireStatusMapping", entities = {
//        @EntityResult(entityClass = HRCourseQuestionnaireStatus.class, fields = {
//                @FieldResult(name = "codeksID", column = "codeksID"),
//                @FieldResult(name = "attendanceID", column = "attendanceID"),
//                @FieldResult(name = "questionnaire1Status", column = "questionnaire1Status"),
//                @FieldResult(name = "questionnaire1Required", column = "questionnaire1Required"),
//                @FieldResult(name = "questionnaire1TS", column = "questionnaire1TS"),
//                @FieldResult(name = "questionnaire2Status", column = "questionnaire2Status"),
//                @FieldResult(name = "questionnaire2TS", column = "questionnaire2TS"),
//                @FieldResult(name = "questionnaire2Required", column = "questionnaire2Required"),
//                @FieldResult(name = "questionnaire2FromTS", column = "questionnaire2FromTS"),
//                @FieldResult(name = "courseTitle", column = "courseTitle"),
//                @FieldResult(name = "courseOfferingID", column = "courseOfferingID")
//        })
//})
//@SqlResultSetMapping(
//        name = "questionnaireStatusMapping2",
//        classes = @ConstructorResult(
//                targetClass = HRCourseQuestionnaireStatus.class,
//                columns = {
//                    @ColumnResult(name = "codeksID", type = int.class),
//                    @ColumnResult(name = "attendanceID", type = int.class),
//                    @ColumnResult(name = "questionnaire1Status", type = String.class),
//                    @ColumnResult(name = "questionnaire1Required", type = Boolean.class),
//                    @ColumnResult(name = "questionnaire1TS", type = Date.class),
//                    @ColumnResult(name = "questionnaire2Status", type = String.class),
//                    @ColumnResult(name = "questionnaire2TS", type = Date.class),
//                    @ColumnResult(name = "questionnaire2Required", type = Boolean.class),
//                    @ColumnResult(name = "questionnaire2FromTS", type = Date.class),
//                    @ColumnResult(name = "courseTitle", type = String.class),
//                    @ColumnResult(name = "courseOfferingID", type = int.class)
//                }
//        )
//)
@Entity
@SqlResultSetMapping(
        name = "questionnaireStatusMapping3",
        entities = {
            @EntityResult(entityClass = HRCourseQuestionnaireStatus.class,
                    fields = {
                        @FieldResult(name = "codeksID", column = "codeksID"),
                        @FieldResult(name = "attendanceID", column = "attendanceID"),
                        @FieldResult(name = "questionnaire1Status", column = "questionnaire1Status"),
                        @FieldResult(name = "questionnaire1Required", column = "questionnaire1Required"),
                        @FieldResult(name = "questionnaire1TS", column = "questionnaire1TS"),
                        @FieldResult(name = "questionnaire2Status", column = "questionnaire2Status"),
                        @FieldResult(name = "questionnaire2TS", column = "questionnaire2TS"),
                        @FieldResult(name = "questionnaire2Required", column = "questionnaire2Required"),
                        @FieldResult(name = "questionnaire2FromTS", column = "questionnaire2FromTS"),
                        @FieldResult(name = "courseTitle", column = "courseTitle")
//                        @FieldResult(name = "courseOfferingID", column = "courseOfferingID"),
//                        @FieldResult(name = "zaposleni", column = "codeksIDzap")
                    }),
            @EntityResult(entityClass = HRCourseOffering.class)
            //@EntityResult(entityClass = VCodeksUsersAktualniZaposleni.class)
            //@EntityResult(entityClass = VCodeksUsersAktualniZaposleni.class, fields = {@FieldResult(name = "zaposleni", column = "zaposleni")})
        }
)
public class HRCourseQuestionnaireStatus implements Serializable {

    private int codeksID;
    @Id
    @Basic(optional = false)
    private int attendanceID;
    private String questionnaire1Status;
    private boolean questionnaire1Required;
    private Date questionnaire1TS;
    private String questionnaire2Status;
    private boolean questionnaire2Required;
    private Date questionnaire2TS;
    private Date questionnaire2FromTS;
    private String courseTitle;
    @ManyToOne
    @JoinColumn(name = "courseOfferingID", referencedColumnName = "courseOfferingID")
    private HRCourseOffering courseOfferingID;
    
    public HRCourseQuestionnaireStatus() {
    }

//    public HRCourseQuestionnaireStatus(int codeksID, int attendanceID, String questionnaire1Status, boolean questionnaire1Required, Date questionnaire1TS, String questionnaire2Status, Date questionnaire2TS, boolean questionnaire2Required, Date questionnaire2FromTS, String courseTitle, int courseOfferingID) {
//        this.codeksID = codeksID;
//        this.attendanceID = attendanceID;
//        this.questionnaire1Status = questionnaire1Status;
//        this.questionnaire1Required = questionnaire1Required;
//        this.questionnaire1TS = questionnaire1TS;
//        this.questionnaire2Status = questionnaire2Status;
//        this.questionnaire2Required = questionnaire2Required;
//        this.questionnaire2TS = questionnaire2TS;
//        this.questionnaire2FromTS = questionnaire2FromTS;
//        this.courseTitle = courseTitle;
//        this.courseOfferingID.setCourseOfferingID(courseOfferingID);
//        System.out.println("Sem v konstruktorju, nastavljam codeksid:"+codeksID);
////        this.zaposleni.setId(codeksIDzap);
//    }

    public int getCodeksID() {
        return codeksID;
    }

    public void setCodeksID(int codeksID) {
        this.codeksID = codeksID;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getQuestionnaire1Status() {
        return questionnaire1Status;
    }

    public void setQuestionnaire1Status(String questionnaire1Status) {
        this.questionnaire1Status = questionnaire1Status;
    }

    public boolean isQuestionnaire1Required() {
        return questionnaire1Required;
    }

    public void setQuestionnaire1Required(boolean questionnaire1Required) {
        this.questionnaire1Required = questionnaire1Required;
    }

    public Date getQuestionnaire1TS() {
        return questionnaire1TS;
    }

    public void setQuestionnaire1TS(Date questionnaire1TS) {
        this.questionnaire1TS = questionnaire1TS;
    }

    public String getQuestionnaire2Status() {
        return questionnaire2Status;
    }

    public void setQuestionnaire2Status(String questionnaire2Status) {
        this.questionnaire2Status = questionnaire2Status;
    }

    public boolean isQuestionnaire2Required() {
        return questionnaire2Required;
    }

    public void setQuestionnaire2Required(boolean questionnaire2Required) {
        this.questionnaire2Required = questionnaire2Required;
    }

    public Date getQuestionnaire2TS() {
        return questionnaire2TS;
    }

    public void setQuestionnaire2TS(Date questionnaire2TS) {
        this.questionnaire2TS = questionnaire2TS;
    }

    public Date getQuestionnaire2FromTS() {
        return questionnaire2FromTS;
    }

    public void setQuestionnaire2FromTS(Date questionnaire2FromTS) {
        this.questionnaire2FromTS = questionnaire2FromTS;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public HRCourseOffering getCourseOfferingID() {
        return courseOfferingID;
    }

    public void setCourseOfferingID(HRCourseOffering courseOfferingID) {
        this.courseOfferingID = courseOfferingID;
    }
    
}
