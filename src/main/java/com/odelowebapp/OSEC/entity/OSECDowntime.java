/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "OSEC_Downtime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OSECDowntime.findAll", query = "SELECT o FROM OSECDowntime o"),
    @NamedQuery(name = "OSECDowntime.findByDowntimeID", query = "SELECT o FROM OSECDowntime o WHERE o.downtimeID = :downtimeID"),
    @NamedQuery(name = "OSECDowntime.findByDate", query = "SELECT o FROM OSECDowntime o WHERE o.date = :date"),
    @NamedQuery(name = "OSECDowntime.findByShift", query = "SELECT o FROM OSECDowntime o WHERE o.shift = :shift"),
    @NamedQuery(name = "OSECDowntime.findByDuration", query = "SELECT o FROM OSECDowntime o WHERE o.duration = :duration"),
    @NamedQuery(name = "OSECDowntime.findByStartD", query = "SELECT o FROM OSECDowntime o WHERE o.startD = :startD"),
    @NamedQuery(name = "OSECDowntime.findByEndD", query = "SELECT o FROM OSECDowntime o WHERE o.endD = :endD"),
    @NamedQuery(name = "OSECDowntime.findByWorkOrder", query = "SELECT o FROM OSECDowntime o WHERE o.workOrder = :workOrder"),
    @NamedQuery(name = "OSECDowntime.findByPlannedUnplanned", query = "SELECT o FROM OSECDowntime o WHERE o.plannedUnplanned = :plannedUnplanned"),
    @NamedQuery(name = "OSECDowntime.findByComment", query = "SELECT o FROM OSECDowntime o WHERE o.comment = :comment")
    })
public class OSECDowntime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "downtimeID")
    private Integer downtimeID;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "shift")
    private Integer shift;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "startD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startD;
    @Column(name = "endD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endD;
    @Size(max = 50)
    @Column(name = "workOrder")
    private String workOrder;
    @Size(max = 50)
    @Column(name = "plannedUnplanned")
    private String plannedUnplanned;
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;
//    @Column(name = "machineID_fk")
//    private Integer machineIDfk;
    @JoinColumn(name = "reasonID_fk", referencedColumnName = "reasonID")
    @ManyToOne
    private OSECDowntimeReasons reasonIDfk;
    @JoinColumn(name = "machineID_fk", referencedColumnName = "machineID")
    @ManyToOne
    private OSECMachine oSECMachine;
    
    @Column(name = "status")
    private Integer status;
    @Size(max = 50)
    @Column(name = "source")
    private String source;
    @Size(max = 50)
    @Column(name = "userEntering")
    private String userEntering;
    @Size(max = 50)
    @Column(name = "userConfirming")
    private String userConfirming;
    

    public OSECDowntime() {
    }
    
    public String getStatusString(){
        if(null == status){
            return "status=null";
        }else switch (status) {
            case 1:
                return "Zavrnjen";
            case 0:
                return "Nepotrjen";
            default:
                return "Sprejet";
        }
    }

    public OSECDowntime(Integer downtimeID) {
        this.downtimeID = downtimeID;
    }

    public Integer getDowntimeID() {
        return downtimeID;
    }

    public void setDowntimeID(Integer downtimeID) {
        this.downtimeID = downtimeID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartD() {
        return startD;
    }

    public void setStartD(Date startD) {
        this.startD = startD;
    }

    public Date getEndD() {
        return endD;
    }

    public void setEndD(Date endD) {
        this.endD = endD;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getPlannedUnplanned() {
        return plannedUnplanned;
    }

    public void setPlannedUnplanned(String plannedUnplanned) {
        this.plannedUnplanned = plannedUnplanned;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

//    public Integer getMachineIDfk() {
//        return machineIDfk;
//    }
//
//    public void setMachineIDfk(Integer machineIDfk) {
//        this.machineIDfk = machineIDfk;
//    }

    public OSECDowntimeReasons getReasonIDfk() {
        return reasonIDfk;
    }

    public void setReasonIDfk(OSECDowntimeReasons reasonIDfk) {
        this.reasonIDfk = reasonIDfk;
    }

    public OSECMachine getOSECMachine() {
        return oSECMachine;
    }

    public void setOSECMachine(OSECMachine oSECMachine) {
        this.oSECMachine = oSECMachine;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserEntering() {
        return userEntering;
    }

    public void setUserEntering(String userEntering) {
        this.userEntering = userEntering;
    }

    public String getUserConfirming() {
        return userConfirming;
    }

    public void setUserConfirming(String userConfirming) {
        this.userConfirming = userConfirming;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (downtimeID != null ? downtimeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OSECDowntime)) {
            return false;
        }
        OSECDowntime other = (OSECDowntime) object;
        if ((this.downtimeID == null && other.downtimeID != null) || (this.downtimeID != null && !this.downtimeID.equals(other.downtimeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.OSEC.entity.OSECDowntime[ downtimeID=" + downtimeID + " ]";
    }
    
}
