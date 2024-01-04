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
import java.util.List;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dean
 */
@Entity
@Table(name = "HRCourseType")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourseType.findAll", query = "SELECT h FROM HRCourseType h")
    , @NamedQuery(name = "HRCourseType.findByCourseTypeID", query = "SELECT h FROM HRCourseType h WHERE h.courseTypeID = :courseTypeID")
    , @NamedQuery(name = "HRCourseType.findByCourseTypeTitle", query = "SELECT h FROM HRCourseType h WHERE h.courseTypeTitle = :courseTypeTitle")
    , @NamedQuery(name = "HRCourseType.findByActive", query = "SELECT h FROM HRCourseType h WHERE h.active = :active")})
public class HRCourseType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "courseTypeID")
    private Integer courseTypeID;
    @Size(max = 2147483647)
    @Column(name = "courseTypeTitle")
    private String courseTypeTitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "courseTypeID")
    private List<HRCourse> hRCourseList;
    @OneToMany(mappedBy = "parent")
    private List<HRCourseType> hRCourseTypeList;
    @JoinColumn(name = "parent", referencedColumnName = "courseTypeID")
    @ManyToOne
    private HRCourseType parent;
    
    @Basic(optional = true)
    @NotNull
    @Column(name = "sendSurveyByMail")
    private boolean sendSurveyByMail;

    public HRCourseType() {
    }

    public HRCourseType(Integer courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    public HRCourseType(Integer courseTypeID, boolean active) {
        this.courseTypeID = courseTypeID;
        this.active = active;
    }

    public Integer getCourseTypeID() {
        return courseTypeID;
    }

    public void setCourseTypeID(Integer courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    public String getCourseTypeTitle() {
        return courseTypeTitle;
    }

    public void setCourseTypeTitle(String courseTypeTitle) {
        this.courseTypeTitle = courseTypeTitle;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSendSurveyByMail() {
        return sendSurveyByMail;
    }

    public void setSendSurveyByMail(boolean sendSurveyByMail) {
        this.sendSurveyByMail = sendSurveyByMail;
    }

    //@XmlTransient
    public List<HRCourse> getHRCourseList() {
        return hRCourseList;
    }

    public void setHRCourseList(List<HRCourse> hRCourseList) {
        this.hRCourseList = hRCourseList;
    }

    //@XmlTransient
    public List<HRCourseType> getHRCourseTypeList() {
        return hRCourseTypeList;
    }

    public void setHRCourseTypeList(List<HRCourseType> hRCourseTypeList) {
        this.hRCourseTypeList = hRCourseTypeList;
    }

    public HRCourseType getParent() {
        return parent;
    }

    public void setParent(HRCourseType parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseTypeID != null ? courseTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseType)) {
            return false;
        }
        HRCourseType other = (HRCourseType) object;
        if ((this.courseTypeID == null && other.courseTypeID != null) || (this.courseTypeID != null && !this.courseTypeID.equals(other.courseTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourseType[ courseTypeID=" + courseTypeID + " ]";
    }
    
}
