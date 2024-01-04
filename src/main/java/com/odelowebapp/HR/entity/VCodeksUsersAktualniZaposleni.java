/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import com.odelowebapp.CODEKS.entity.Departments;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
//import javax.xml.bind.annotation.XmlRootElement;
//import si.odelo.HR.entity.HRCourseOffering;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "v_codeks_users_aktualni_zaposleni")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findAll", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v"),
    @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findAllAndroid", query = "SELECT v.id, v.card, v.firstname, v.lastname FROM VCodeksUsersAktualniZaposleni v")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findById", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.id = :id")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByDeleted", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.deleted = :deleted")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLastname", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lastname = :lastname")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByFirstname", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.firstname = :firstname")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByMemo", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.memo = :memo")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByPicture", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.picture = :picture")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCompanyId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.companyId = :companyId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByDepartmentId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.departmentId = :departmentId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findBySubunitId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.subunitId = :subunitId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByExternalId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.externalId = :externalId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByExportId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.exportId = :exportId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCard", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.card = :card")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCardProtected", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.cardProtected = :cardProtected")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByPin", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.pin = :pin")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByPersonalId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.personalId = :personalId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCode", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.code = :code")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByValidTill", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.validTill = :validTill")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByUseKeypad", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.useKeypad = :useKeypad")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByFollow", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.follow = :follow")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByHide", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.hide = :hide")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByMaster", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.master = :master")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLinkCode", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.linkCode = :linkCode")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByBlocked", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.blocked = :blocked")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByDynamicAccess", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.dynamicAccess = :dynamicAccess")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLastEventId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lastEventId = :lastEventId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLastEventType", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lastEventType = :lastEventType")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLastEventPlaceId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lastEventPlaceId = :lastEventPlaceId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLastEventTime", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lastEventTime = :lastEventTime")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByIsTimeAttendance", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.isTimeAttendance = :isTimeAttendance")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByIsLoginAllowed", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.isLoginAllowed = :isLoginAllowed")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByUsername", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.username = :username")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByPassword", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.password = :password")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLanguage", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.language = :language")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByEmail", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.email = :email")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCardType", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.cardType = :cardType")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByTAStartDate", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.tAStartDate = :tAStartDate")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCalendarId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.calendarId = :calendarId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByValidFrom", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.validFrom = :validFrom")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByLostCard", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.lostCard = :lostCard")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByMobileCard", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.mobileCard = :mobileCard")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByEditOwnTA", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.editOwnTA = :editOwnTA")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByEditOwnYearData", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.editOwnYearData = :editOwnYearData")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByProcessOwnAbsences", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.processOwnAbsences = :processOwnAbsences")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByProcessOwnPermits", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.processOwnPermits = :processOwnPermits")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCustomField1", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.customField1 = :customField1")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCustomField2", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.customField2 = :customField2")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByCustomField3", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.customField3 = :customField3")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByViewOwnTimeAndAttendance", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.viewOwnTimeAndAttendance = :viewOwnTimeAndAttendance")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByAllowPlaceReservations", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.allowPlaceReservations = :allowPlaceReservations")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByAllowGuests", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.allowGuests = :allowGuests")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByIsAlternativeActionActive", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.isAlternativeActionActive = :isAlternativeActionActive")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByUserReferenceId", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.userReferenceId = :userReferenceId")
    , @NamedQuery(name = "VCodeksUsersAktualniZaposleni.findByPaysForLunch", query = "SELECT v FROM VCodeksUsersAktualniZaposleni v WHERE v.paysForLunch = :paysForLunch")})
public class VCodeksUsersAktualniZaposleni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Deleted")
    private Boolean deleted;
    @Size(max = 255)
    @Column(name = "Lastname")
    private String lastname;
    @Size(max = 255)
    @Column(name = "Firstname")
    private String firstname;
    @Size(max = 255)
    @Column(name = "Memo")
    private String memo;
    @Size(max = 255)
    @Column(name = "Picture")
    private String picture;
    @Column(name = "CompanyId")
    private Integer companyId;
    
    @Column(name = "DepartmentId")
    private Integer departmentId;
    
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "Id", insertable=false, updatable=false)
//    private Departments departmentObject;
    
    @Column(name = "SubunitId")
    private Integer subunitId;
    @Size(max = 255)
    @Column(name = "ExternalId")
    private String externalId;
    @Size(max = 255)
    @Column(name = "ExportId")
    private String exportId;
    @Size(max = 255)
    @Column(name = "Card")
    private String card;
    @Size(max = 255)
    @Column(name = "CardProtected")
    private String cardProtected;
    @Size(max = 255)
    @Column(name = "Pin")
    private String pin;
    @Size(max = 255)
    @Column(name = "PersonalId")
    private String personalId;
    @Size(max = 255)
    @Column(name = "Code")
    private String code;
    @Column(name = "ValidTill")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTill;
    @Column(name = "UseKeypad")
    private Boolean useKeypad;
    @Column(name = "Follow")
    private Boolean follow;
    @Column(name = "Hide")
    private Boolean hide;
    @Column(name = "Master")
    private Boolean master;
    @Column(name = "LinkCode")
    private Integer linkCode;
    @Column(name = "Blocked")
    private Boolean blocked;
    @Column(name = "DynamicAccess")
    private Integer dynamicAccess;
    @Column(name = "LastEventId")
    private Integer lastEventId;
    @Column(name = "LastEventType")
    private Integer lastEventType;
    @Column(name = "LastEventPlaceId")
    private Integer lastEventPlaceId;
    @Column(name = "LastEventTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEventTime;
    @Column(name = "IsTimeAttendance")
    private Boolean isTimeAttendance;
    @Column(name = "IsLoginAllowed")
    private Boolean isLoginAllowed;
    @Size(max = 255)
    @Column(name = "Username")
    private String username;
    @Size(max = 255)
    @Column(name = "Password")
    private String password;
    @Size(max = 255)
    @Column(name = "Language")
    private String language;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "Email")
    private String email;
    @Column(name = "CardType")
    private Integer cardType;
    @Column(name = "TAStartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tAStartDate;
    @Column(name = "CalendarId")
    private Integer calendarId;
    @Column(name = "ValidFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column(name = "LostCard")
    private Boolean lostCard;
    @Column(name = "MobileCard")
    private Boolean mobileCard;
    @Column(name = "EditOwnTA")
    private Boolean editOwnTA;
    @Column(name = "EditOwnYearData")
    private Boolean editOwnYearData;
    @Column(name = "ProcessOwnAbsences")
    private Boolean processOwnAbsences;
    @Column(name = "ProcessOwnPermits")
    private Boolean processOwnPermits;
    @Size(max = 255)
    @Column(name = "CustomField1")
    private String customField1;
    @Size(max = 255)
    @Column(name = "CustomField2")
    private String customField2;
    @Size(max = 255)
    @Column(name = "CustomField3")
    private String customField3;
    @Column(name = "ViewOwnTimeAndAttendance")
    private Boolean viewOwnTimeAndAttendance;
    @Column(name = "AllowPlaceReservations")
    private Boolean allowPlaceReservations;
    @Column(name = "AllowGuests")
    private Boolean allowGuests;
    @Column(name = "IsAlternativeActionActive")
    private Boolean isAlternativeActionActive;
    @Column(name = "UserReferenceId")
    private Integer userReferenceId;
    @Column(name = "PaysForLunch")
    private Boolean paysForLunch;
    
    @Transient
    private boolean inserted;
    

    public VCodeksUsersAktualniZaposleni() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }
    

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getSubunitId() {
        return subunitId;
    }

    public void setSubunitId(Integer subunitId) {
        this.subunitId = subunitId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardProtected() {
        return cardProtected;
    }

    public void setCardProtected(String cardProtected) {
        this.cardProtected = cardProtected;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public Boolean getUseKeypad() {
        return useKeypad;
    }

    public void setUseKeypad(Boolean useKeypad) {
        this.useKeypad = useKeypad;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public Integer getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(Integer linkCode) {
        this.linkCode = linkCode;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Integer getDynamicAccess() {
        return dynamicAccess;
    }

    public void setDynamicAccess(Integer dynamicAccess) {
        this.dynamicAccess = dynamicAccess;
    }

    public Integer getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(Integer lastEventId) {
        this.lastEventId = lastEventId;
    }

    public Integer getLastEventType() {
        return lastEventType;
    }

    public void setLastEventType(Integer lastEventType) {
        this.lastEventType = lastEventType;
    }

    public Integer getLastEventPlaceId() {
        return lastEventPlaceId;
    }

    public void setLastEventPlaceId(Integer lastEventPlaceId) {
        this.lastEventPlaceId = lastEventPlaceId;
    }

    public Date getLastEventTime() {
        return lastEventTime;
    }

    public void setLastEventTime(Date lastEventTime) {
        this.lastEventTime = lastEventTime;
    }

    public Boolean getIsTimeAttendance() {
        return isTimeAttendance;
    }

    public void setIsTimeAttendance(Boolean isTimeAttendance) {
        this.isTimeAttendance = isTimeAttendance;
    }

    public Boolean getIsLoginAllowed() {
        return isLoginAllowed;
    }

    public void setIsLoginAllowed(Boolean isLoginAllowed) {
        this.isLoginAllowed = isLoginAllowed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Date getTAStartDate() {
        return tAStartDate;
    }

    public void setTAStartDate(Date tAStartDate) {
        this.tAStartDate = tAStartDate;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Boolean getLostCard() {
        return lostCard;
    }

    public void setLostCard(Boolean lostCard) {
        this.lostCard = lostCard;
    }

    public Boolean getMobileCard() {
        return mobileCard;
    }

    public void setMobileCard(Boolean mobileCard) {
        this.mobileCard = mobileCard;
    }

    public Boolean getEditOwnTA() {
        return editOwnTA;
    }

    public void setEditOwnTA(Boolean editOwnTA) {
        this.editOwnTA = editOwnTA;
    }

    public Boolean getEditOwnYearData() {
        return editOwnYearData;
    }

    public void setEditOwnYearData(Boolean editOwnYearData) {
        this.editOwnYearData = editOwnYearData;
    }

    public Boolean getProcessOwnAbsences() {
        return processOwnAbsences;
    }

    public void setProcessOwnAbsences(Boolean processOwnAbsences) {
        this.processOwnAbsences = processOwnAbsences;
    }

    public Boolean getProcessOwnPermits() {
        return processOwnPermits;
    }

    public void setProcessOwnPermits(Boolean processOwnPermits) {
        this.processOwnPermits = processOwnPermits;
    }

    public String getCustomField1() {
        return customField1;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    public Boolean getViewOwnTimeAndAttendance() {
        return viewOwnTimeAndAttendance;
    }

    public void setViewOwnTimeAndAttendance(Boolean viewOwnTimeAndAttendance) {
        this.viewOwnTimeAndAttendance = viewOwnTimeAndAttendance;
    }

    public Boolean getAllowPlaceReservations() {
        return allowPlaceReservations;
    }

    public void setAllowPlaceReservations(Boolean allowPlaceReservations) {
        this.allowPlaceReservations = allowPlaceReservations;
    }

    public Boolean getAllowGuests() {
        return allowGuests;
    }

    public void setAllowGuests(Boolean allowGuests) {
        this.allowGuests = allowGuests;
    }

    public Boolean getIsAlternativeActionActive() {
        return isAlternativeActionActive;
    }

    public void setIsAlternativeActionActive(Boolean isAlternativeActionActive) {
        this.isAlternativeActionActive = isAlternativeActionActive;
    }

    public Integer getUserReferenceId() {
        return userReferenceId;
    }

    public void setUserReferenceId(Integer userReferenceId) {
        this.userReferenceId = userReferenceId;
    }

    public Boolean getPaysForLunch() {
        return paysForLunch;
    }

    public void setPaysForLunch(Boolean paysForLunch) {
        this.paysForLunch = paysForLunch;
    }

//    public Departments getDepartmentObject() {
//        return departmentObject;
//    }
//
//    public void setDepartmentObject(Departments departmentObject) {
//        this.departmentObject = departmentObject;
//    }
    
    

    @Override
    public String toString() {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return "si.odelo.HROBR.entity.VCodeksUsersAktualniZaposleni[ Id=" + id + " ]";
    }

    @Override
    public boolean equals(Object object) {
        //return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VCodeksUsersAktualniZaposleni)) {
            return false;
        }
        VCodeksUsersAktualniZaposleni other = (VCodeksUsersAktualniZaposleni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        //return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
