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
@Table(name = "HRCourse_HRCourseInstructor")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourseHRCourseInstructor.findAll", query = "SELECT h FROM HRCourseHRCourseInstructor h")
    , @NamedQuery(name = "HRCourseHRCourseInstructor.findById", query = "SELECT h FROM HRCourseHRCourseInstructor h WHERE h.id = :id")})
public class HRCourseHRCourseInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne
    private HRCourse courseID;
    @JoinColumn(name = "instructorID", referencedColumnName = "instructorID")
    @ManyToOne
    private HRCourseInstructor instructorID;

    public HRCourseHRCourseInstructor() {
    }

    public HRCourseHRCourseInstructor(Integer id) {
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

    public HRCourseInstructor getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(HRCourseInstructor instructorID) {
        this.instructorID = instructorID;
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
        if (!(object instanceof HRCourseHRCourseInstructor)) {
            return false;
        }
        HRCourseHRCourseInstructor other = (HRCourseHRCourseInstructor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourseHRCourseInstructor[ id=" + id + " ]";
    }
    
}
