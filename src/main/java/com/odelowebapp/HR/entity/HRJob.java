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
@Table(name = "HRJob")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRJob.findAll", query = "SELECT h FROM HRJob h")
    , @NamedQuery(name = "HRJob.findByJobID", query = "SELECT h FROM HRJob h WHERE h.jobID = :jobID")
    , @NamedQuery(name = "HRJob.findByJobTitle", query = "SELECT h FROM HRJob h WHERE h.jobTitle = :jobTitle")
    , @NamedQuery(name = "HRJob.findByActive", query = "SELECT h FROM HRJob h WHERE h.active = :active")})
public class HRJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "jobID")
    private Integer jobID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "jobTitle")
    private String jobTitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "jobID")
    private List<HRJobHRCourse> hRJobHRCourseList;

    public HRJob() {
    }

    public HRJob(Integer jobID) {
        this.jobID = jobID;
    }

    public HRJob(Integer jobID, String jobTitle, boolean active) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.active = active;
    }

    public Integer getJobID() {
        return jobID;
    }

    public void setJobID(Integer jobID) {
        this.jobID = jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //@XmlTransient
    public List<HRJobHRCourse> getHRJobHRCourseList() {
        return hRJobHRCourseList;
    }

    public void setHRJobHRCourseList(List<HRJobHRCourse> hRJobHRCourseList) {
        this.hRJobHRCourseList = hRJobHRCourseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobID != null ? jobID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRJob)) {
            return false;
        }
        HRJob other = (HRJob) object;
        if ((this.jobID == null && other.jobID != null) || (this.jobID != null && !this.jobID.equals(other.jobID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRJob[ jobID=" + jobID + " ]";
    }
    
}
