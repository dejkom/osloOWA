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
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "AGV_status")
@NamedQueries({
    @NamedQuery(name = "AGVstatus.findAll", query = "SELECT a FROM AGVstatus a"),
    @NamedQuery(name = "AGVstatus.findByStatusId", query = "SELECT a FROM AGVstatus a WHERE a.statusId = :statusId"),
    @NamedQuery(name = "AGVstatus.findByStatusName", query = "SELECT a FROM AGVstatus a WHERE a.statusName = :statusName"),
    @NamedQuery(name = "AGVstatus.findByDisabled", query = "SELECT a FROM AGVstatus a WHERE a.disabled = :disabled"),
    @NamedQuery(name = "AGVstatus.findByOrderType", query = "SELECT a FROM AGVstatus a WHERE a.orderTypeId = :orderType ORDER BY a.statusOrder ASC"),
    @NamedQuery(name = "AGVstatus.findByStatusOrder", query = "SELECT a FROM AGVstatus a WHERE a.statusOrder = :statusOrder")})
public class AGVstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statusId")
    private Integer statusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "statusName")
    private String statusName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disabled")
    private boolean disabled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "statusOrder")
    private int statusOrder;
    @JoinColumn(name = "orderTypeId", referencedColumnName = "orderTypeId")
    @ManyToOne
    private AGVorderType orderTypeId;
    @OneToMany(mappedBy = "statusId")
    private List<AGVorder> aGVorderList;

    public AGVstatus() {
    }

    public AGVstatus(Integer statusId) {
        this.statusId = statusId;
    }

    public AGVstatus(Integer statusId, String statusName, boolean disabled, int statusOrder) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.disabled = disabled;
        this.statusOrder = statusOrder;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    public AGVorderType getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(AGVorderType orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public List<AGVorder> getAGVorderList() {
        return aGVorderList;
    }

    public void setAGVorderList(List<AGVorder> aGVorderList) {
        this.aGVorderList = aGVorderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVstatus)) {
            return false;
        }
        AGVstatus other = (AGVstatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVstatus[ statusId=" + statusId + " ]";
    }
    
}
