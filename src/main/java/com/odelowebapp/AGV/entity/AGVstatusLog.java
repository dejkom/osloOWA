/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.entity;

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
@Table(name = "AGV_statusLog")
@NamedQueries({
    @NamedQuery(name = "AGVstatusLog.findAll", query = "SELECT a FROM AGVstatusLog a"),
    @NamedQuery(name = "AGVstatusLog.findByStatusLogId", query = "SELECT a FROM AGVstatusLog a WHERE a.statusLogId = :statusLogId"),
    @NamedQuery(name = "AGVstatusLog.findByTimestamp", query = "SELECT a FROM AGVstatusLog a WHERE a.timestamp = :timestamp"),
    @NamedQuery(name = "AGVstatusLog.findByStatus", query = "SELECT a FROM AGVstatusLog a WHERE a.status = :status"),
    @NamedQuery(name = "AGVstatusLog.findByPerson", query = "SELECT a FROM AGVstatusLog a WHERE a.person = :person"),
    @NamedQuery(name = "AGVstatusLog.findByOrderId", query = "SELECT a FROM AGVstatusLog a WHERE a.orderId = :orderId ORDER BY a.timestamp ASC"),
    @NamedQuery(name = "AGVstatusLog.findByMessage", query = "SELECT a FROM AGVstatusLog a WHERE a.message = :message")})
public class AGVstatusLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statusLogId")
    private Integer statusLogId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "status")
    private String status;
    @Size(max = 100)
    @Column(name = "person")
    private String person;
    @Size(max = 2147483647)
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    @ManyToOne
    private AGVorder orderId;

    public AGVstatusLog() {
    }

    public AGVstatusLog(Integer statusLogId) {
        this.statusLogId = statusLogId;
    }

    public AGVstatusLog(Integer statusLogId, Date timestamp, String status) {
        this.statusLogId = statusLogId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Integer getStatusLogId() {
        return statusLogId;
    }

    public void setStatusLogId(Integer statusLogId) {
        this.statusLogId = statusLogId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AGVorder getOrderId() {
        return orderId;
    }

    public void setOrderId(AGVorder orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusLogId != null ? statusLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVstatusLog)) {
            return false;
        }
        AGVstatusLog other = (AGVstatusLog) object;
        if ((this.statusLogId == null && other.statusLogId != null) || (this.statusLogId != null && !this.statusLogId.equals(other.statusLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVstatusLog[ statusLogId=" + statusLogId + " ]";
    }
    
}
