/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.qlik_mails.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author itstudent
 */
@Entity
@Table(name = "v_AD_users_direct")
@NamedQueries({
        @NamedQuery(name = "VADusersdirect.findAll", query = "SELECT v FROM VADusersdirect v"),
        @NamedQuery(name = "VADusersdirect.findByLastPwSetDateTime", query = "SELECT v FROM VADusersdirect v WHERE v.lastPwSetDateTime = :lastPwSetDateTime"),
        @NamedQuery(name = "VADusersdirect.findByLastLogonFormat", query = "SELECT v FROM VADusersdirect v WHERE v.lastLogonFormat = :lastLogonFormat"),
        @NamedQuery(name = "VADusersdirect.findByLastLogon", query = "SELECT v FROM VADusersdirect v WHERE v.lastLogon = :lastLogon"),
        @NamedQuery(name = "VADusersdirect.findByWhenCreated", query = "SELECT v FROM VADusersdirect v WHERE v.whenCreated = :whenCreated"),
        @NamedQuery(name = "VADusersdirect.findByUserAccountControl", query = "SELECT v FROM VADusersdirect v WHERE v.userAccountControl = :userAccountControl"),
        @NamedQuery(name = "VADusersdirect.findByExtensionAttribute2", query = "SELECT v FROM VADusersdirect v WHERE v.extensionAttribute2 = :extensionAttribute2"),
        @NamedQuery(name = "VADusersdirect.findByExtensionAttribute1", query = "SELECT v FROM VADusersdirect v WHERE v.extensionAttribute1 = :extensionAttribute1"),
        @NamedQuery(name = "VADusersdirect.findByCountryCode", query = "SELECT v FROM VADusersdirect v WHERE v.countryCode = :countryCode"),
        @NamedQuery(name = "VADusersdirect.findByC", query = "SELECT v FROM VADusersdirect v WHERE v.c = :c"),
        @NamedQuery(name = "VADusersdirect.findByPhysicalDeliveryOfficeName", query = "SELECT v FROM VADusersdirect v WHERE v.physicalDeliveryOfficeName = :physicalDeliveryOfficeName"),
        @NamedQuery(name = "VADusersdirect.findByCompany", query = "SELECT v FROM VADusersdirect v WHERE v.company = :company"),
        @NamedQuery(name = "VADusersdirect.findByCo", query = "SELECT v FROM VADusersdirect v WHERE v.co = :co"),
        @NamedQuery(name = "VADusersdirect.findByPostalCode", query = "SELECT v FROM VADusersdirect v WHERE v.postalCode = :postalCode"),
        @NamedQuery(name = "VADusersdirect.findByL", query = "SELECT v FROM VADusersdirect v WHERE v.l = :l"),
        @NamedQuery(name = "VADusersdirect.findByStreetAddress", query = "SELECT v FROM VADusersdirect v WHERE v.streetAddress = :streetAddress"),
        @NamedQuery(name = "VADusersdirect.findByDisplayName", query = "SELECT v FROM VADusersdirect v WHERE v.displayName = :displayName"),
        @NamedQuery(name = "VADusersdirect.findByPwdLastSet", query = "SELECT v FROM VADusersdirect v WHERE v.pwdLastSet = :pwdLastSet"),
        @NamedQuery(name = "VADusersdirect.findBySAMAccountName", query = "SELECT v FROM VADusersdirect v WHERE v.sAMAccountName = :sAMAccountName"),
        @NamedQuery(name = "VADusersdirect.findByGivenName", query = "SELECT v FROM VADusersdirect v WHERE v.givenName = :givenName"),
        @NamedQuery(name = "VADusersdirect.findBySn", query = "SELECT v FROM VADusersdirect v WHERE v.sn = :sn"),
        @NamedQuery(name = "VADusersdirect.findByTitle", query = "SELECT v FROM VADusersdirect v WHERE v.title = :title"),
        @NamedQuery(name = "VADusersdirect.findByTelephoneNumber", query = "SELECT v FROM VADusersdirect v WHERE v.telephoneNumber = :telephoneNumber"),
        @NamedQuery(name = "VADusersdirect.findByMobile", query = "SELECT v FROM VADusersdirect v WHERE v.mobile = :mobile"),
        @NamedQuery(name = "VADusersdirect.findByInfo", query = "SELECT v FROM VADusersdirect v WHERE v.info = :info"),
        @NamedQuery(name = "VADusersdirect.findByMail", query = "SELECT v FROM VADusersdirect v WHERE v.mail = :mail"),
        @NamedQuery(name = "VADusersdirect.findByComment", query = "SELECT v FROM VADusersdirect v WHERE v.comment = :comment"),
        @NamedQuery(name = "VADusersdirect.findByDistinguishedName", query = "SELECT v FROM VADusersdirect v WHERE v.distinguishedName = :distinguishedName"),
        @NamedQuery(name = "VADusersdirect.findByEnabled", query = "SELECT v FROM VADusersdirect v WHERE v.enabled = :enabled"),
        @NamedQuery(name = "VADusersdirect.findByPasswordExpire", query = "SELECT v FROM VADusersdirect v WHERE v.passwordExpire = :passwordExpire")})
public class VADusersdirect implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "lastPwSetDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPwSetDateTime;
    @Column(name = "lastLogonFormat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogonFormat;
    @Size(max = 4000)
    @Column(name = "lastLogon")
    private String lastLogon;
    @Column(name = "whenCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenCreated;
    @Column(name = "userAccountControl")
    private Integer userAccountControl;
    @Size(max = 4000)
    @Column(name = "extensionAttribute2")
    private String extensionAttribute2;
    @Size(max = 4000)
    @Column(name = "extensionAttribute1")
    private String extensionAttribute1;
    @Column(name = "countryCode")
    private Integer countryCode;
    @Size(max = 4000)
    @Column(name = "c")
    private String c;
    @Size(max = 4000)
    @Column(name = "physicalDeliveryOfficeName")
    private String physicalDeliveryOfficeName;
    @Size(max = 4000)
    @Column(name = "company")
    private String company;
    @Size(max = 4000)
    @Column(name = "co")
    private String co;
    @Size(max = 4000)
    @Column(name = "postalCode")
    private String postalCode;
    @Size(max = 4000)
    @Column(name = "l")
    private String l;
    @Size(max = 4000)
    @Column(name = "streetAddress")
    private String streetAddress;
    @Size(max = 4000)
    @Column(name = "displayName")
    private String displayName;
    @Size(max = 4000)
    @Column(name = "pwdLastSet")
    private String pwdLastSet;
    @Size(max = 4000)
    @Column(name = "SAMAccountName")
    private String sAMAccountName;
    @Size(max = 4000)
    @Column(name = "givenName")
    private String givenName;
    @Size(max = 4000)
    @Column(name = "sn")
    private String sn;
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
    @Column(name = "info")
    private String info;
    @Size(max = 4000)
    @Column(name = "mail")
    private String mail;
    @Size(max = 4000)
    @Column(name = "Comment")
    private String comment;
    @Id
    @Size(max = 4000)
    @Column(name = "distinguishedName")
    private String distinguishedName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "enabled")
    private String enabled;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "passwordExpire")
    private String passwordExpire;

    public VADusersdirect() {
    }

    public Date getLastPwSetDateTime() {
        return lastPwSetDateTime;
    }

    public void setLastPwSetDateTime(Date lastPwSetDateTime) {
        this.lastPwSetDateTime = lastPwSetDateTime;
    }

    public Date getLastLogonFormat() {
        return lastLogonFormat;
    }

    public void setLastLogonFormat(Date lastLogonFormat) {
        this.lastLogonFormat = lastLogonFormat;
    }

    public String getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(String lastLogon) {
        this.lastLogon = lastLogon;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Integer getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(Integer userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getExtensionAttribute2() {
        return extensionAttribute2;
    }

    public void setExtensionAttribute2(String extensionAttribute2) {
        this.extensionAttribute2 = extensionAttribute2;
    }

    public String getExtensionAttribute1() {
        return extensionAttribute1;
    }

    public void setExtensionAttribute1(String extensionAttribute1) {
        this.extensionAttribute1 = extensionAttribute1;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getPhysicalDeliveryOfficeName() {
        return physicalDeliveryOfficeName;
    }

    public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
        this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPwdLastSet() {
        return pwdLastSet;
    }

    public void setPwdLastSet(String pwdLastSet) {
        this.pwdLastSet = pwdLastSet;
    }

    public String getSAMAccountName() {
        return sAMAccountName;
    }

    public void setSAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(String passwordExpire) {
        this.passwordExpire = passwordExpire;
    }
}
