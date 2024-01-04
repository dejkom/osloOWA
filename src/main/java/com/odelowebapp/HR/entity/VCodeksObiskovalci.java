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
import java.util.Date;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "v_codeks_obiskovalci")
public class VCodeksObiskovalci implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected VCodeksObiskovalciPK vCodeksObiskovalciPK;
    
    
//    @Id
////    @Basic(optional = false)
////    @NotNull
////    @Column(name = "visitId")
//    private int visitId;
    @Column(name = "TimeIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Column(name = "TimeOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;
    @Size(max = 255)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "foruser")
    private String foruser;
    
    @Column(name = "ForUserId")
    private int foruserid;
    
    @Size(max = 24)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "prisotnost")
    private String prisotnost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "najavljen")
    private String najavljen;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 512)
//    @Column(name = "guest")
//    private String guest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "guest_company")
    private String guestCompany;
    @Column(name = "Status")
    private Integer status;
    @Size(max = 255)
    @Column(name = "Name")
    private String name;

    public VCodeksObiskovalci() {
    }

    public VCodeksObiskovalci(VCodeksObiskovalciPK vCodeksObiskovalciPK, Date timeIn, Date datum, Date timeOut, String company, String foruser, int foruserid, String reason, String prisotnost, String najavljen, String guestCompany, Integer status, String name) {
        this.vCodeksObiskovalciPK = vCodeksObiskovalciPK;
        this.timeIn = timeIn;
        this.datum = datum;
        this.timeOut = timeOut;
        this.company = company;
        this.foruser = foruser;
        this.foruserid = foruserid;
        this.reason = reason;
        this.prisotnost = prisotnost;
        this.najavljen = najavljen;
        this.guestCompany = guestCompany;
        this.status = status;
        this.name = name;
    }
    
    

    public VCodeksObiskovalciPK getVCodeksObiskovalciPK() {
        return vCodeksObiskovalciPK;
    }

    public void setVCodeksObiskovalciPK(VCodeksObiskovalciPK vCodeksObiskovalciPK) {
        this.vCodeksObiskovalciPK = vCodeksObiskovalciPK;
    }

    
    
//    public int getVisitId() {
//        return visitId;
//    }
//
//    public void setVisitId(int visitId) {
//        this.visitId = visitId;
//    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getForuser() {
        return foruser;
    }

    public void setForuser(String foruser) {
        this.foruser = foruser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPrisotnost() {
        return prisotnost;
    }

    public void setPrisotnost(String prisotnost) {
        this.prisotnost = prisotnost;
    }

    public String getNajavljen() {
        return najavljen;
    }

    public void setNajavljen(String najavljen) {
        this.najavljen = najavljen;
    }

//    public String getGuest() {
//        return guest;
//    }
//
//    public void setGuest(String guest) {
//        this.guest = guest;
//    }

    public String getGuestCompany() {
        return guestCompany;
    }

    public void setGuestCompany(String guestCompany) {
        this.guestCompany = guestCompany;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForuserid() {
        return foruserid;
    }

    public void setForuserid(int foruserid) {
        this.foruserid = foruserid;
    }
    
    
    
}
