/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dean
 */
@Entity
@Table(name = "HRCourseAttendance")
@NamedQueries({
    @NamedQuery(name = "HRCourseAttendance.findAll", query = "SELECT h FROM HRCourseAttendance h")
    , @NamedQuery(name = "HRCourseAttendance.findByAttendanceID", query = "SELECT h FROM HRCourseAttendance h WHERE h.attendanceID = :attendanceID")
    , @NamedQuery(name = "HRCourseAttendance.findByCodeksID", query = "SELECT h FROM HRCourseAttendance h WHERE h.codeksID = :codeksID")
    , @NamedQuery(name = "HRCourseAttendance.findByWasAttended", query = "SELECT h FROM HRCourseAttendance h WHERE h.wasAttended = :wasAttended")
    , @NamedQuery(name = "HRCourseAttendance.findByComment", query = "SELECT h FROM HRCourseAttendance h WHERE h.comment = :comment")
    , @NamedQuery(name = "HRCourseAttendance.findByRate", query = "SELECT h FROM HRCourseAttendance h WHERE h.rate = :rate")})
@NamedNativeQuery(name = "AttendancesWithQuestionaire",
  query = "SELECT distinct att.*, an.timestamp AS tsAnketa FROM HRCourseAttendance att\n"
                    + "LEFT OUTER JOIN HRCourseAnswer an\n"
                    + "ON att.codeksID = an.userID and att.courseOfferingID = an.courseID\n"
                    + "WHERE att.codeksID = ?",
  resultSetMapping = "AttendancesWithQuestionaireResult2")
@SqlResultSetMapping(
        name = "AttendancesWithQuestionaireResult2",
        entities = @EntityResult(
                entityClass = HRCourseAttendance.class,
                fields = {
                    @FieldResult(name = "attendanceID", column = "attendanceID"),
                    @FieldResult(name = "codeksID", column = "codeksID"),
                    @FieldResult(name = "wasAttended", column = "wasAttended"),
                    @FieldResult(name = "comment", column = "comment"),
                    @FieldResult(name = "rate", column = "rate"),
                    @FieldResult(name = "courseOfferingID", column = "courseOfferingID"),
                    @FieldResult(name = "timestamp", column = "timestamp")}),
        columns = @ColumnResult(name = "tsAnketa", type = Date.class))
public class HRCourseAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "attendanceID")
    private Integer attendanceID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codeksID")
    private int codeksID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wasAttended")
    private boolean wasAttended;
    @Size(max = 300)
    @Column(name = "comment")
    private String comment;
    @Column(name = "rate")
    private Integer rate;
    @JoinColumn(name = "courseOfferingID", referencedColumnName = "courseOfferingID")
    @ManyToOne
    private HRCourseOffering courseOfferingID;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "samopotrdil")
    private boolean samopotrdil;
    
    @Transient
    private Date tsAnketa;
    @Transient
    private VCodeksUsers osebaInfo;

    public HRCourseAttendance() {
    }

    public HRCourseAttendance(Integer attendanceID) {
        this.attendanceID = attendanceID;
    }

    public HRCourseAttendance(Integer attendanceID, int codeksID, boolean wasAttended) {
        this.attendanceID = attendanceID;
        this.codeksID = codeksID;
        this.wasAttended = wasAttended;
    }

    public HRCourseAttendance(Integer attendanceID, int codeksID, boolean wasAttended, String comment, Integer rate, int courseOfferingID, Date timestamp, Date tsAnketa) {
        this.attendanceID = attendanceID;
        this.codeksID = codeksID;
        this.wasAttended = wasAttended;
        this.comment = comment;
        this.rate = rate;
        
        this.courseOfferingID = new HRCourseOffering(courseOfferingID);
        this.timestamp = timestamp;
        this.tsAnketa = tsAnketa;
    }

    public Integer getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(Integer attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getCodeksID() {
        return codeksID;
    }

    public void setCodeksID(int codeksID) {
        this.codeksID = codeksID;
    }

    public boolean getWasAttended() {
        return wasAttended;
    }

    public void setWasAttended(boolean wasAttended) {
        this.wasAttended = wasAttended;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public HRCourseOffering getCourseOfferingID() {
        return courseOfferingID;
    }

    public void setCourseOfferingID(HRCourseOffering courseOfferingID) {
        this.courseOfferingID = courseOfferingID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTsAnketa() {
        return tsAnketa;
    }

    public void setTsAnketa(Date tsAnketa) {
        this.tsAnketa = tsAnketa;
    }

    public VCodeksUsers getOsebaInfo() {
        return osebaInfo;
    }

    public void setOsebaInfo(VCodeksUsers osebaInfo) {
        this.osebaInfo = osebaInfo;
    }

    public boolean isSamopotrdil() {
        return samopotrdil;
    }

    public void setSamopotrdil(boolean samopotrdil) {
        this.samopotrdil = samopotrdil;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceID != null ? attendanceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseAttendance)) {
            return false;
        }
        HRCourseAttendance other = (HRCourseAttendance) object;
        if ((this.attendanceID == null && other.attendanceID != null) || (this.attendanceID != null && !this.attendanceID.equals(other.attendanceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourseAttendance[ attendanceID=" + attendanceID + " ]";
    }
    
}
