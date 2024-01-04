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
@Table(name = "HRCourseInstructor")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourseInstructor.findAll", query = "SELECT h FROM HRCourseInstructor h")
    , @NamedQuery(name = "HRCourseInstructor.findByInstructorID", query = "SELECT h FROM HRCourseInstructor h WHERE h.instructorID = :instructorID")
    , @NamedQuery(name = "HRCourseInstructor.findByCodeksID", query = "SELECT h FROM HRCourseInstructor h WHERE h.codeksID = :codeksID")
    , @NamedQuery(name = "HRCourseInstructor.findByFirstname", query = "SELECT h FROM HRCourseInstructor h WHERE h.firstname = :firstname")
    , @NamedQuery(name = "HRCourseInstructor.findByLastname", query = "SELECT h FROM HRCourseInstructor h WHERE h.lastname = :lastname")
    , @NamedQuery(name = "HRCourseInstructor.findByCompany", query = "SELECT h FROM HRCourseInstructor h WHERE h.company = :company")
    , @NamedQuery(name = "HRCourseInstructor.findByPhone", query = "SELECT h FROM HRCourseInstructor h WHERE h.phone = :phone")
    , @NamedQuery(name = "HRCourseInstructor.findByMail", query = "SELECT h FROM HRCourseInstructor h WHERE h.mail = :mail")
    , @NamedQuery(name = "HRCourseInstructor.findByActive", query = "SELECT h FROM HRCourseInstructor h WHERE h.active = :active")})
public class HRCourseInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "instructorID")
    private Integer instructorID;
    @Column(name = "codeksID")
    private Integer codeksID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 100)
    @Column(name = "company")
    private String company;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "phone")
    private String phone;
    @Size(max = 100)
    @Column(name = "mail")
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "instructorID")
    private List<HRCourseOffering> hRCourseOfferingList;
    @OneToMany(mappedBy = "instructorID")
    private List<HRCourseHRCourseInstructor> hRCourseHRCourseInstructorList;

    public HRCourseInstructor() {
    }

    public HRCourseInstructor(Integer instructorID) {
        this.instructorID = instructorID;
    }

    public HRCourseInstructor(Integer instructorID, String firstname, boolean active) {
        this.instructorID = instructorID;
        this.firstname = firstname;
        this.active = active;
    }

    public Integer getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Integer instructorID) {
        this.instructorID = instructorID;
    }

    public Integer getCodeksID() {
        return codeksID;
    }

    public void setCodeksID(Integer codeksID) {
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
    
    @Transient
    private String firstNameLastname;
    public String getFirstNameLastname(){
        firstNameLastname = firstname+" "+lastname;
        return firstNameLastname;
    }

    public void setFirstNameLastname(String firstNameLastname) {
        this.firstNameLastname = firstNameLastname;
    }
    

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //@XmlTransient
    public List<HRCourseOffering> getHRCourseOfferingList() {
        return hRCourseOfferingList;
    }

    public void setHRCourseOfferingList(List<HRCourseOffering> hRCourseOfferingList) {
        this.hRCourseOfferingList = hRCourseOfferingList;
    }

    //@XmlTransient
    public List<HRCourseHRCourseInstructor> getHRCourseHRCourseInstructorList() {
        return hRCourseHRCourseInstructorList;
    }

    public void setHRCourseHRCourseInstructorList(List<HRCourseHRCourseInstructor> hRCourseHRCourseInstructorList) {
        this.hRCourseHRCourseInstructorList = hRCourseHRCourseInstructorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (instructorID != null ? instructorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseInstructor)) {
            return false;
        }
        HRCourseInstructor other = (HRCourseInstructor) object;
        if ((this.instructorID == null && other.instructorID != null) || (this.instructorID != null && !this.instructorID.equals(other.instructorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourseInstructor[ instructorID=" + instructorID + " ]";
    }
    
}
