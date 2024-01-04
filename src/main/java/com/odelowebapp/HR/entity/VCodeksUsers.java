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
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author drazpotnik
 */
@Entity
@Table(name = "v_codeks_users")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VCodeksUsers.findAll", query = "SELECT v FROM VCodeksUsers v")
    , @NamedQuery(name = "VCodeksUsers.findById", query = "SELECT v FROM VCodeksUsers v WHERE v.id = :id")
    , @NamedQuery(name = "VCodeksUsers.findByDeleted", query = "SELECT v FROM VCodeksUsers v WHERE v.deleted = :deleted")
    , @NamedQuery(name = "VCodeksUsers.findByLastname", query = "SELECT v FROM VCodeksUsers v WHERE v.lastname = :lastname")
    , @NamedQuery(name = "VCodeksUsers.findByFirstname", query = "SELECT v FROM VCodeksUsers v WHERE v.firstname = :firstname")
    , @NamedQuery(name = "VCodeksUsers.findByMemo", query = "SELECT v FROM VCodeksUsers v WHERE v.memo = :memo")
    , @NamedQuery(name = "VCodeksUsers.findByPicture", query = "SELECT v FROM VCodeksUsers v WHERE v.picture = :picture")
    , @NamedQuery(name = "VCodeksUsers.findByCompanyId", query = "SELECT v FROM VCodeksUsers v WHERE v.companyId = :companyId")
    , @NamedQuery(name = "VCodeksUsers.findByDepartmentId", query = "SELECT v FROM VCodeksUsers v WHERE v.departmentId = :departmentId")
    , @NamedQuery(name = "VCodeksUsers.findBySubunitId", query = "SELECT v FROM VCodeksUsers v WHERE v.subunitId = :subunitId")
    , @NamedQuery(name = "VCodeksUsers.findByExternalId", query = "SELECT v FROM VCodeksUsers v WHERE v.externalId = :externalId")
    , @NamedQuery(name = "VCodeksUsers.findByExportId", query = "SELECT v FROM VCodeksUsers v WHERE v.exportId = :exportId")
    , @NamedQuery(name = "VCodeksUsers.findByCard", query = "SELECT v FROM VCodeksUsers v WHERE v.card = :card")
    , @NamedQuery(name = "VCodeksUsers.findByCardProtected", query = "SELECT v FROM VCodeksUsers v WHERE v.cardProtected = :cardProtected")
    , @NamedQuery(name = "VCodeksUsers.findByPin", query = "SELECT v FROM VCodeksUsers v WHERE v.pin = :pin")
    , @NamedQuery(name = "VCodeksUsers.findByPersonalId", query = "SELECT v FROM VCodeksUsers v WHERE v.personalId = :personalId")
    , @NamedQuery(name = "VCodeksUsers.findByCode", query = "SELECT v FROM VCodeksUsers v WHERE v.code = :code")
    , @NamedQuery(name = "VCodeksUsers.findByValidTill", query = "SELECT v FROM VCodeksUsers v WHERE v.validTill = :validTill")
    , @NamedQuery(name = "VCodeksUsers.findByUseKeypad", query = "SELECT v FROM VCodeksUsers v WHERE v.useKeypad = :useKeypad")
    , @NamedQuery(name = "VCodeksUsers.findByFollow", query = "SELECT v FROM VCodeksUsers v WHERE v.follow = :follow")
    , @NamedQuery(name = "VCodeksUsers.findByHide", query = "SELECT v FROM VCodeksUsers v WHERE v.hide = :hide")
    , @NamedQuery(name = "VCodeksUsers.findByMaster", query = "SELECT v FROM VCodeksUsers v WHERE v.master = :master")
    , @NamedQuery(name = "VCodeksUsers.findByLinkCode", query = "SELECT v FROM VCodeksUsers v WHERE v.linkCode = :linkCode")
    , @NamedQuery(name = "VCodeksUsers.findByBlocked", query = "SELECT v FROM VCodeksUsers v WHERE v.blocked = :blocked")
    , @NamedQuery(name = "VCodeksUsers.findByDynamicAccess", query = "SELECT v FROM VCodeksUsers v WHERE v.dynamicAccess = :dynamicAccess")
    , @NamedQuery(name = "VCodeksUsers.findByLastEventId", query = "SELECT v FROM VCodeksUsers v WHERE v.lastEventId = :lastEventId")
    , @NamedQuery(name = "VCodeksUsers.findByLastEventType", query = "SELECT v FROM VCodeksUsers v WHERE v.lastEventType = :lastEventType")
    , @NamedQuery(name = "VCodeksUsers.findByLastEventPlaceId", query = "SELECT v FROM VCodeksUsers v WHERE v.lastEventPlaceId = :lastEventPlaceId")
    , @NamedQuery(name = "VCodeksUsers.findByLastEventTime", query = "SELECT v FROM VCodeksUsers v WHERE v.lastEventTime = :lastEventTime")
    , @NamedQuery(name = "VCodeksUsers.findByIsTimeAttendance", query = "SELECT v FROM VCodeksUsers v WHERE v.isTimeAttendance = :isTimeAttendance")
    , @NamedQuery(name = "VCodeksUsers.findByIsLoginAllowed", query = "SELECT v FROM VCodeksUsers v WHERE v.isLoginAllowed = :isLoginAllowed")
    , @NamedQuery(name = "VCodeksUsers.findByUsername", query = "SELECT v FROM VCodeksUsers v WHERE v.username = :username")
    , @NamedQuery(name = "VCodeksUsers.findByPassword", query = "SELECT v FROM VCodeksUsers v WHERE v.password = :password")
    , @NamedQuery(name = "VCodeksUsers.findByLanguage", query = "SELECT v FROM VCodeksUsers v WHERE v.language = :language")
    , @NamedQuery(name = "VCodeksUsers.findByEmail", query = "SELECT v FROM VCodeksUsers v WHERE v.email = :email")
    , @NamedQuery(name = "VCodeksUsers.findByCardType", query = "SELECT v FROM VCodeksUsers v WHERE v.cardType = :cardType")
    , @NamedQuery(name = "VCodeksUsers.findByTAStartDate", query = "SELECT v FROM VCodeksUsers v WHERE v.tAStartDate = :tAStartDate")
    , @NamedQuery(name = "VCodeksUsers.findByCalendarId", query = "SELECT v FROM VCodeksUsers v WHERE v.calendarId = :calendarId")
    , @NamedQuery(name = "VCodeksUsers.findByValidFrom", query = "SELECT v FROM VCodeksUsers v WHERE v.validFrom = :validFrom")
    , @NamedQuery(name = "VCodeksUsers.findByLostCard", query = "SELECT v FROM VCodeksUsers v WHERE v.lostCard = :lostCard")
    , @NamedQuery(name = "VCodeksUsers.findByMobileCard", query = "SELECT v FROM VCodeksUsers v WHERE v.mobileCard = :mobileCard")
    , @NamedQuery(name = "VCodeksUsers.findByEditOwnTA", query = "SELECT v FROM VCodeksUsers v WHERE v.editOwnTA = :editOwnTA")
    , @NamedQuery(name = "VCodeksUsers.findByEditOwnYearData", query = "SELECT v FROM VCodeksUsers v WHERE v.editOwnYearData = :editOwnYearData")
    , @NamedQuery(name = "VCodeksUsers.findByProcessOwnAbsences", query = "SELECT v FROM VCodeksUsers v WHERE v.processOwnAbsences = :processOwnAbsences")
    , @NamedQuery(name = "VCodeksUsers.findByProcessOwnPermits", query = "SELECT v FROM VCodeksUsers v WHERE v.processOwnPermits = :processOwnPermits")
    , @NamedQuery(name = "VCodeksUsers.findByCustomField1", query = "SELECT v FROM VCodeksUsers v WHERE v.customField1 = :customField1")
    , @NamedQuery(name = "VCodeksUsers.findByCustomField2", query = "SELECT v FROM VCodeksUsers v WHERE v.customField2 = :customField2")
    , @NamedQuery(name = "VCodeksUsers.findByCustomField3", query = "SELECT v FROM VCodeksUsers v WHERE v.customField3 = :customField3")
    , @NamedQuery(name = "VCodeksUsers.findByViewOwnTimeAndAttendance", query = "SELECT v FROM VCodeksUsers v WHERE v.viewOwnTimeAndAttendance = :viewOwnTimeAndAttendance")
    , @NamedQuery(name = "VCodeksUsers.findByAllowPlaceReservations", query = "SELECT v FROM VCodeksUsers v WHERE v.allowPlaceReservations = :allowPlaceReservations")
    , @NamedQuery(name = "VCodeksUsers.findByAllowGuests", query = "SELECT v FROM VCodeksUsers v WHERE v.allowGuests = :allowGuests")
    , @NamedQuery(name = "VCodeksUsers.findByIsAlternativeActionActive", query = "SELECT v FROM VCodeksUsers v WHERE v.isAlternativeActionActive = :isAlternativeActionActive")
    , @NamedQuery(name = "VCodeksUsers.findByUserReferenceId", query = "SELECT v FROM VCodeksUsers v WHERE v.userReferenceId = :userReferenceId")
    , @NamedQuery(name = "VCodeksUsers.findByPaysForLunch", query = "SELECT v FROM VCodeksUsers v WHERE v.paysForLunch = :paysForLunch")})
public class VCodeksUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private int id;
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

    public VCodeksUsers() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
}
