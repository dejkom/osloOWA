/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "v_AD_CODEKS_Users")
@NamedQueries({
    @NamedQuery(name = "VADCODEKSUsers.findAll", query = "SELECT v FROM VADCODEKSUsers v"),
    @NamedQuery(name = "VADCODEKSUsers.findById", query = "SELECT v FROM VADCODEKSUsers v WHERE v.id = :id"),
    @NamedQuery(name = "VADCODEKSUsers.findByDn", query = "SELECT v FROM VADCODEKSUsers v WHERE v.dn = :dn"),
    @NamedQuery(name = "VADCODEKSUsers.findByExternalId", query = "SELECT v FROM VADCODEKSUsers v WHERE v.externalId = :externalId"),
    @NamedQuery(name = "VADCODEKSUsers.findByLastname", query = "SELECT v FROM VADCODEKSUsers v WHERE v.lastname = :lastname"),
    @NamedQuery(name = "VADCODEKSUsers.findByFirstname", query = "SELECT v FROM VADCODEKSUsers v WHERE v.firstname = :firstname"),
    @NamedQuery(name = "VADCODEKSUsers.findByDepartmentId", query = "SELECT v FROM VADCODEKSUsers v WHERE v.departmentId = :departmentId"),
    @NamedQuery(name = "VADCODEKSUsers.findByCodeksDeleted", query = "SELECT v FROM VADCODEKSUsers v WHERE v.codeksDeleted = :codeksDeleted"),
    @NamedQuery(name = "VADCODEKSUsers.findByIsTimeAttendance", query = "SELECT v FROM VADCODEKSUsers v WHERE v.isTimeAttendance = :isTimeAttendance"),
    @NamedQuery(name = "VADCODEKSUsers.findByCard", query = "SELECT v FROM VADCODEKSUsers v WHERE v.card = :card"),
    @NamedQuery(name = "VADCODEKSUsers.findByUsername", query = "SELECT v FROM VADCODEKSUsers v WHERE v.username = :username"),
    @NamedQuery(name = "VADCODEKSUsers.findByLastPwSetDateTime", query = "SELECT v FROM VADCODEKSUsers v WHERE v.lastPwSetDateTime = :lastPwSetDateTime"),
    @NamedQuery(name = "VADCODEKSUsers.findByTitle", query = "SELECT v FROM VADCODEKSUsers v WHERE v.title = :title"),
    @NamedQuery(name = "VADCODEKSUsers.findByTelephoneNumber", query = "SELECT v FROM VADCODEKSUsers v WHERE v.telephoneNumber = :telephoneNumber"),
    @NamedQuery(name = "VADCODEKSUsers.findByMobile", query = "SELECT v FROM VADCODEKSUsers v WHERE v.mobile = :mobile"),
    @NamedQuery(name = "VADCODEKSUsers.findByMail", query = "SELECT v FROM VADCODEKSUsers v WHERE v.mail = :mail"),
    @NamedQuery(name = "VADCODEKSUsers.findByCountryShort", query = "SELECT v FROM VADCODEKSUsers v WHERE v.countryShort = :countryShort"),
    @NamedQuery(name = "VADCODEKSUsers.findByCountry", query = "SELECT v FROM VADCODEKSUsers v WHERE v.country = :country"),
    @NamedQuery(name = "VADCODEKSUsers.findByCountryCode", query = "SELECT v FROM VADCODEKSUsers v WHERE v.countryCode = :countryCode"),
    @NamedQuery(name = "VADCODEKSUsers.findByPostalCode", query = "SELECT v FROM VADCODEKSUsers v WHERE v.postalCode = :postalCode"),
    @NamedQuery(name = "VADCODEKSUsers.findByLocation", query = "SELECT v FROM VADCODEKSUsers v WHERE v.location = :location"),
    @NamedQuery(name = "VADCODEKSUsers.findByStreetAddress", query = "SELECT v FROM VADCODEKSUsers v WHERE v.streetAddress = :streetAddress"),
    @NamedQuery(name = "VADCODEKSUsers.findByExtensionAttribute1", query = "SELECT v FROM VADCODEKSUsers v WHERE v.extensionAttribute1 = :extensionAttribute1"),
    @NamedQuery(name = "VADCODEKSUsers.findByExtensionAttribute2", query = "SELECT v FROM VADCODEKSUsers v WHERE v.extensionAttribute2 = :extensionAttribute2")})
public class VADCODEKSUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 4000)
    @Column(name = "DN")
    private String dn;
    @Size(max = 255)
    @Column(name = "ExternalId")
    private String externalId;
    @Size(max = 255)
    @Column(name = "Lastname")
    private String lastname;
    @Size(max = 255)
    @Column(name = "Firstname")
    private String firstname;
    @Column(name = "DepartmentId")
    private Integer departmentId;
    @Column(name = "CodeksDeleted")
    private Boolean codeksDeleted;
    @Column(name = "IsTimeAttendance")
    private Boolean isTimeAttendance;
    @Size(max = 255)
    @Column(name = "Card")
    private String card;
    @Size(max = 4000)
    @Column(name = "username")
    private String username;
    @Column(name = "lastPwSetDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPwSetDateTime;
    @Size(max = 4000)
    @Column(name = "title")
    private String title;
    @Size(max = 4000)
    @Column(name = "telephoneNumber")
    private String telephoneNumber;
    @Size(max = 4000)
    @Column(name = "mobile")
    private String mobile;
    @Size(max = 4000)
    @Column(name = "mail")
    private String mail;
    @Size(max = 4000)
    @Column(name = "CountryShort")
    private String countryShort;
    @Size(max = 4000)
    @Column(name = "Country")
    private String country;
    @Column(name = "countryCode")
    private Integer countryCode;
    @Size(max = 4000)
    @Column(name = "postalCode")
    private String postalCode;
    @Size(max = 4000)
    @Column(name = "Location")
    private String location;
    @Size(max = 4000)
    @Column(name = "streetAddress")
    private String streetAddress;
    @Size(max = 4000)
    @Column(name = "extensionAttribute1")
    private String extensionAttribute1;
    @Size(max = 4000)
    @Column(name = "extensionAttribute2")
    private String extensionAttribute2;

    public VADCODEKSUsers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getCodeksDeleted() {
        return codeksDeleted;
    }

    public void setCodeksDeleted(Boolean codeksDeleted) {
        this.codeksDeleted = codeksDeleted;
    }

    public Boolean getIsTimeAttendance() {
        return isTimeAttendance;
    }

    public void setIsTimeAttendance(Boolean isTimeAttendance) {
        this.isTimeAttendance = isTimeAttendance;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastPwSetDateTime() {
        return lastPwSetDateTime;
    }

    public void setLastPwSetDateTime(Date lastPwSetDateTime) {
        this.lastPwSetDateTime = lastPwSetDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCountryShort() {
        return countryShort;
    }

    public void setCountryShort(String countryShort) {
        this.countryShort = countryShort;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getExtensionAttribute1() {
        return extensionAttribute1;
    }

    public void setExtensionAttribute1(String extensionAttribute1) {
        this.extensionAttribute1 = extensionAttribute1;
    }

    public String getExtensionAttribute2() {
        return extensionAttribute2;
    }

    public void setExtensionAttribute2(String extensionAttribute2) {
        this.extensionAttribute2 = extensionAttribute2;
    }
    
}
