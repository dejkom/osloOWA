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
@Table(name = "AGV_orderType")
@NamedQueries({
    @NamedQuery(name = "AGVorderType.findAll", query = "SELECT a FROM AGVorderType a"),
    @NamedQuery(name = "AGVorderType.findByOrderTypeId", query = "SELECT a FROM AGVorderType a WHERE a.orderTypeId = :orderTypeId"),
    @NamedQuery(name = "AGVorderType.findByOrderName", query = "SELECT a FROM AGVorderType a WHERE a.orderName = :orderName"),
    @NamedQuery(name = "AGVorderType.findByDisabled", query = "SELECT a FROM AGVorderType a WHERE a.disabled = :disabled")})
public class AGVorderType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderTypeId")
    private Integer orderTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "orderName")
    private String orderName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disabled")
    private boolean disabled;
    @OneToMany(mappedBy = "orderTypeId")
    private List<AGVstatus> aGVstatusList;
    @OneToMany(mappedBy = "orderTypeId")
    private List<AGVcatalog> aGVcatalogList;
    @OneToMany(mappedBy = "orderTypeId")
    private List<AGVorder> aGVorderList;

    public AGVorderType() {
    }

    public AGVorderType(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public AGVorderType(Integer orderTypeId, String orderName, boolean disabled) {
        this.orderTypeId = orderTypeId;
        this.orderName = orderName;
        this.disabled = disabled;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<AGVstatus> getAGVstatusList() {
        return aGVstatusList;
    }

    public void setAGVstatusList(List<AGVstatus> aGVstatusList) {
        this.aGVstatusList = aGVstatusList;
    }

    public List<AGVcatalog> getAGVcatalogList() {
        return aGVcatalogList;
    }

    public void setAGVcatalogList(List<AGVcatalog> aGVcatalogList) {
        this.aGVcatalogList = aGVcatalogList;
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
        hash += (orderTypeId != null ? orderTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVorderType)) {
            return false;
        }
        AGVorderType other = (AGVorderType) object;
        if ((this.orderTypeId == null && other.orderTypeId != null) || (this.orderTypeId != null && !this.orderTypeId.equals(other.orderTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVorderType[ orderTypeId=" + orderTypeId + " ]";
    }
    
}
