/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import javax.persistence.*;
import java.io.Serializable;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dean
 */
@Entity
@Table(name = "HRJob_HRCourse")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRJobHRCourse.findAll", query = "SELECT h FROM HRJobHRCourse h")
    , @NamedQuery(name = "HRJobHRCourse.findById", query = "SELECT h FROM HRJobHRCourse h WHERE h.id = :id")})
public class HRJobHRCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne
    private HRCourse courseID;
    @JoinColumn(name = "jobID", referencedColumnName = "jobID")
    @ManyToOne
    private HRJob jobID;

    public HRJobHRCourse() {
    }

    public HRJobHRCourse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HRCourse getCourseID() {
        return courseID;
    }

    public void setCourseID(HRCourse courseID) {
        this.courseID = courseID;
    }

    public HRJob getJobID() {
        return jobID;
    }

    public void setJobID(HRJob jobID) {
        this.jobID = jobID;
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
        if (!(object instanceof HRJobHRCourse)) {
            return false;
        }
        HRJobHRCourse other = (HRJobHRCourse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRJobHRCourse[ id=" + id + " ]";
    }
    
}
