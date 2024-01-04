/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "HRCourseInstructorsWorkLog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findAll", query = "SELECT h FROM HRCourseInstructorsWorkLog h"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findById", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.id = :id"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByCourseOfferingID", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.courseOfferingID = :courseOfferingID"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByCodeksID", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.codeksID = :codeksID"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByFirstname", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.firstname = :firstname"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByLastname", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.lastname = :lastname"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByDuration", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.duration = :duration"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByTimestamp", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.timestampcreation = :timestamp"),
    @NamedQuery(name = "HRCourseInstructorsWorkLog.findByComment", query = "SELECT h FROM HRCourseInstructorsWorkLog h WHERE h.comment = :comment")})
public class HRCourseInstructorsWorkLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseOfferingID")
    private int courseOfferingID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codeksID")
    private int codeksID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "duration")
    private Integer duration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampcreation;
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;

    public HRCourseInstructorsWorkLog() {
    }

    public HRCourseInstructorsWorkLog(Integer id) {
        this.id = id;
    }

    public HRCourseInstructorsWorkLog(Integer id, int courseOfferingID, int codeksID, String firstname, String lastname, Date timestamp) {
        this.id = id;
        this.courseOfferingID = courseOfferingID;
        this.codeksID = codeksID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.timestampcreation = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCourseOfferingID() {
        return courseOfferingID;
    }

    public void setCourseOfferingID(int courseOfferingID) {
        this.courseOfferingID = courseOfferingID;
    }

    public int getCodeksID() {
        return codeksID;
    }

    public void setCodeksID(int codeksID) {
        this.codeksID = codeksID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getTimestamp() {
        return timestampcreation;
    }

    public void setTimestamp(Date timestamp) {
        this.timestampcreation = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseInstructorsWorkLog)) {
            return false;
        }
        HRCourseInstructorsWorkLog other = (HRCourseInstructorsWorkLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRCourseInstructorsWorkLog[ id=" + id + " ]";
    }
    
}
