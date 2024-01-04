/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.CODEKS.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "Users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByDeleted", query = "SELECT u FROM Users u WHERE u.deleted = :deleted"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByMemo", query = "SELECT u FROM Users u WHERE u.memo = :memo"),
    @NamedQuery(name = "Users.findByPicture", query = "SELECT u FROM Users u WHERE u.picture = :picture"),
    @NamedQuery(name = "Users.findByCompanyId", query = "SELECT u FROM Users u WHERE u.companyId = :companyId"),
    @NamedQuery(name = "Users.findByDepartmentId", query = "SELECT u FROM Users u WHERE u.departmentId = :departmentId"),
    @NamedQuery(name = "Users.findBySubunitId", query = "SELECT u FROM Users u WHERE u.subunitId = :subunitId"),
    @NamedQuery(name = "Users.findByExternalId", query = "SELECT u FROM Users u WHERE u.externalId = :externalId"),
    @NamedQuery(name = "Users.findByExportId", query = "SELECT u FROM Users u WHERE u.exportId = :exportId"),
    @NamedQuery(name = "Users.findByCard", query = "SELECT u FROM Users u WHERE u.card = :card"),
    @NamedQuery(name = "Users.findByCardProtected", query = "SELECT u FROM Users u WHERE u.cardProtected = :cardProtected"),
    @NamedQuery(name = "Users.findByPin", query = "SELECT u FROM Users u WHERE u.pin = :pin"),
    @NamedQuery(name = "Users.findByPersonalId", query = "SELECT u FROM Users u WHERE u.personalId = :personalId"),
    @NamedQuery(name = "Users.findByCode", query = "SELECT u FROM Users u WHERE u.code = :code"),
    @NamedQuery(name = "Users.findByValidTill", query = "SELECT u FROM Users u WHERE u.validTill = :validTill"),
    @NamedQuery(name = "Users.findByUseKeypad", query = "SELECT u FROM Users u WHERE u.useKeypad = :useKeypad"),
    @NamedQuery(name = "Users.findByFollow", query = "SELECT u FROM Users u WHERE u.follow = :follow"),
    @NamedQuery(name = "Users.findByHide", query = "SELECT u FROM Users u WHERE u.hide = :hide"),
    @NamedQuery(name = "Users.findByMaster", query = "SELECT u FROM Users u WHERE u.master = :master"),
    @NamedQuery(name = "Users.findByLinkCode", query = "SELECT u FROM Users u WHERE u.linkCode = :linkCode"),
    @NamedQuery(name = "Users.findByBlocked", query = "SELECT u FROM Users u WHERE u.blocked = :blocked"),
    @NamedQuery(name = "Users.findByDynamicAccess", query = "SELECT u FROM Users u WHERE u.dynamicAccess = :dynamicAccess"),
    @NamedQuery(name = "Users.findByLastEventId", query = "SELECT u FROM Users u WHERE u.lastEventId = :lastEventId"),
    @NamedQuery(name = "Users.findByLastEventType", query = "SELECT u FROM Users u WHERE u.lastEventType = :lastEventType"),
    @NamedQuery(name = "Users.findByLastEventPlaceId", query = "SELECT u FROM Users u WHERE u.lastEventPlaceId = :lastEventPlaceId"),
    @NamedQuery(name = "Users.findByLastEventTime", query = "SELECT u FROM Users u WHERE u.lastEventTime = :lastEventTime"),
    @NamedQuery(name = "Users.findByIsTimeAttendance", query = "SELECT u FROM Users u WHERE u.isTimeAttendance = :isTimeAttendance"),
    @NamedQuery(name = "Users.findByIsLoginAllowed", query = "SELECT u FROM Users u WHERE u.isLoginAllowed = :isLoginAllowed"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByLanguage", query = "SELECT u FROM Users u WHERE u.language = :language"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByCardType", query = "SELECT u FROM Users u WHERE u.cardType = :cardType"),
    @NamedQuery(name = "Users.findByTAStartDate", query = "SELECT u FROM Users u WHERE u.tAStartDate = :tAStartDate"),
    @NamedQuery(name = "Users.findByCalendarId", query = "SELECT u FROM Users u WHERE u.calendarId = :calendarId"),
    @NamedQuery(name = "Users.findByValidFrom", query = "SELECT u FROM Users u WHERE u.validFrom = :validFrom"),
    @NamedQuery(name = "Users.findByLostCard", query = "SELECT u FROM Users u WHERE u.lostCard = :lostCard"),
    @NamedQuery(name = "Users.findByMobileCard", query = "SELECT u FROM Users u WHERE u.mobileCard = :mobileCard"),
    @NamedQuery(name = "Users.findByEditOwnTA", query = "SELECT u FROM Users u WHERE u.editOwnTA = :editOwnTA"),
    @NamedQuery(name = "Users.findByEditOwnYearData", query = "SELECT u FROM Users u WHERE u.editOwnYearData = :editOwnYearData"),
    @NamedQuery(name = "Users.findByProcessOwnAbsences", query = "SELECT u FROM Users u WHERE u.processOwnAbsences = :processOwnAbsences"),
    @NamedQuery(name = "Users.findByProcessOwnPermits", query = "SELECT u FROM Users u WHERE u.processOwnPermits = :processOwnPermits"),
    @NamedQuery(name = "Users.findByCustomField1", query = "SELECT u FROM Users u WHERE u.customField1 = :customField1"),
    @NamedQuery(name = "Users.findByCustomField2", query = "SELECT u FROM Users u WHERE u.customField2 = :customField2"),
    @NamedQuery(name = "Users.findByCustomField3", query = "SELECT u FROM Users u WHERE u.customField3 = :customField3"),
    @NamedQuery(name = "Users.findByViewOwnTimeAndAttendance", query = "SELECT u FROM Users u WHERE u.viewOwnTimeAndAttendance = :viewOwnTimeAndAttendance"),
    @NamedQuery(name = "Users.findByAllowPlaceReservations", query = "SELECT u FROM Users u WHERE u.allowPlaceReservations = :allowPlaceReservations"),
    @NamedQuery(name = "Users.findByAllowGuests", query = "SELECT u FROM Users u WHERE u.allowGuests = :allowGuests"),
    @NamedQuery(name = "Users.findByIsAlternativeActionActive", query = "SELECT u FROM Users u WHERE u.isAlternativeActionActive = :isAlternativeActionActive"),
    @NamedQuery(name = "Users.findByUserReferenceId", query = "SELECT u FROM Users u WHERE u.userReferenceId = :userReferenceId"),
    @NamedQuery(name = "Users.findByPaysForLunch", query = "SELECT u FROM Users u WHERE u.paysForLunch = :paysForLunch"),
    @NamedQuery(name = "Users.findByCard3ProKeyStatus", query = "SELECT u FROM Users u WHERE u.card3ProKeyStatus = :card3ProKeyStatus"),
    @NamedQuery(name = "Users.findByCard3ProKeyStatusChangedAt", query = "SELECT u FROM Users u WHERE u.card3ProKeyStatusChangedAt = :card3ProKeyStatusChangedAt"),
    @NamedQuery(name = "Users.findByFDAnnounceVisitors", query = "SELECT u FROM Users u WHERE u.fDAnnounceVisitors = :fDAnnounceVisitors"),
    @NamedQuery(name = "Users.findByFDProccessOwnVisitorAnnounces", query = "SELECT u FROM Users u WHERE u.fDProccessOwnVisitorAnnounces = :fDProccessOwnVisitorAnnounces"),
    @NamedQuery(name = "Users.findByEditOwnUserEditableStatistics", query = "SELECT u FROM Users u WHERE u.editOwnUserEditableStatistics = :editOwnUserEditableStatistics"),
    @NamedQuery(name = "Users.findByAutomaticLunchOrdering", query = "SELECT u FROM Users u WHERE u.automaticLunchOrdering = :automaticLunchOrdering")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "Card3ProKeyStatus")
    private Integer card3ProKeyStatus;
    @Column(name = "Card3ProKeyStatusChangedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date card3ProKeyStatusChangedAt;
    @Column(name = "FDAnnounceVisitors")
    private Boolean fDAnnounceVisitors;
    @Column(name = "FDProccessOwnVisitorAnnounces")
    private Boolean fDProccessOwnVisitorAnnounces;
    @Column(name = "EditOwnUserEditableStatistics")
    private Boolean editOwnUserEditableStatistics;
    @Column(name = "AutomaticLunchOrdering")
    private Boolean automaticLunchOrdering;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCard3ProKeyStatus() {
        return card3ProKeyStatus;
    }

    public void setCard3ProKeyStatus(Integer card3ProKeyStatus) {
        this.card3ProKeyStatus = card3ProKeyStatus;
    }

    public Date getCard3ProKeyStatusChangedAt() {
        return card3ProKeyStatusChangedAt;
    }

    public void setCard3ProKeyStatusChangedAt(Date card3ProKeyStatusChangedAt) {
        this.card3ProKeyStatusChangedAt = card3ProKeyStatusChangedAt;
    }

    public Boolean getFDAnnounceVisitors() {
        return fDAnnounceVisitors;
    }

    public void setFDAnnounceVisitors(Boolean fDAnnounceVisitors) {
        this.fDAnnounceVisitors = fDAnnounceVisitors;
    }

    public Boolean getFDProccessOwnVisitorAnnounces() {
        return fDProccessOwnVisitorAnnounces;
    }

    public void setFDProccessOwnVisitorAnnounces(Boolean fDProccessOwnVisitorAnnounces) {
        this.fDProccessOwnVisitorAnnounces = fDProccessOwnVisitorAnnounces;
    }

    public Boolean getEditOwnUserEditableStatistics() {
        return editOwnUserEditableStatistics;
    }

    public void setEditOwnUserEditableStatistics(Boolean editOwnUserEditableStatistics) {
        this.editOwnUserEditableStatistics = editOwnUserEditableStatistics;
    }

    public Boolean getAutomaticLunchOrdering() {
        return automaticLunchOrdering;
    }

    public void setAutomaticLunchOrdering(Boolean automaticLunchOrdering) {
        this.automaticLunchOrdering = automaticLunchOrdering;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.CODEKS.entity.Users[ id=" + id + " ]";
    }
    
}
