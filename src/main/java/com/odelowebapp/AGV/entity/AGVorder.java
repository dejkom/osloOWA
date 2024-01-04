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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "AGV_order")
@NamedQueries({
    @NamedQuery(name = "AGVorder.findAll", query = "SELECT a FROM AGVorder a"),
    @NamedQuery(name = "AGVorder.findByOrderId", query = "SELECT a FROM AGVorder a WHERE a.orderId = :orderId"),
    @NamedQuery(name = "AGVorder.findByMaterialId", query = "SELECT a FROM AGVorder a WHERE a.materialId = :materialId"),
    @NamedQuery(name = "AGVorder.findByCreatedTimestamp", query = "SELECT a FROM AGVorder a WHERE a.createdTimestamp = :createdTimestamp"),
    @NamedQuery(name = "AGVorder.findByModifiedTimestamp", query = "SELECT a FROM AGVorder a WHERE a.modifiedTimestamp = :modifiedTimestamp"),
    @NamedQuery(name = "AGVorder.findByQuantity", query = "SELECT a FROM AGVorder a WHERE a.quantity = :quantity")})
public class AGVorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderId")
    private Integer orderId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "materialId")
    private String materialId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modifiedTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTimestamp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private Double quantity;
    @JoinColumn(name = "orderTypeId", referencedColumnName = "orderTypeId")
    @ManyToOne
    private AGVorderType orderTypeId;
    @JoinColumn(name = "placeTo", referencedColumnName = "placeId")
    @ManyToOne
    private AGVplace placeTo;
    @JoinColumn(name = "placeFrom", referencedColumnName = "placeId")
    @ManyToOne
    private AGVplace placeFrom;
    @JoinColumn(name = "statusId", referencedColumnName = "statusId")
    @ManyToOne
    private AGVstatus statusId;
    @OneToMany(mappedBy = "orderId")
    private List<AGVstatusLog> aGVstatusLogList;
    
    @Size(min = 1, max = 50)
    @Column(name = "userCard")
    private String userCard;
    @Size(min = 1, max = 50)
    @Column(name = "userExternalId")
    private String userExternalId;
    @Size(min = 1, max = 50)
    @Column(name = "userFLName")
    private String userFLName;
    
    @Size(max = 100)
    @Column(name = "materialUnit")
    private String materialUnit;
    
    @Column(name = "comment")
    private String comment;
    @Column(name = "priority")
    private String priority;
    
    private transient AGVstatus nextStaus;
    
    //2.12.2021
    private transient String changedString;
    //

    public AGVorder() {
    }

    public AGVorder(Integer orderId) {
        this.orderId = orderId;
    }

    public AGVorder(Integer orderId, String materialId, Date createdTimestamp, Date modifiedTimestamp) {
        this.orderId = orderId;
        this.materialId = materialId;
        this.createdTimestamp = createdTimestamp;
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(Date modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public AGVorderType getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(AGVorderType orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public AGVplace getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(AGVplace placeTo) {
        this.placeTo = placeTo;
    }

    public AGVplace getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(AGVplace placeFrom) {
        this.placeFrom = placeFrom;
    }

    public AGVstatus getStatusId() {
        return statusId;
    }

    public void setStatusId(AGVstatus statusId) {
        this.statusId = statusId;
    }

    public List<AGVstatusLog> getAGVstatusLogList() {
        return aGVstatusLogList;
    }

    public void setAGVstatusLogList(List<AGVstatusLog> aGVstatusLogList) {
        this.aGVstatusLogList = aGVstatusLogList;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getUserExternalId() {
        return userExternalId;
    }

    public void setUserExternalId(String userExternalId) {
        this.userExternalId = userExternalId;
    }

    public String getUserFLName() {
        return userFLName;
    }

    public void setUserFLName(String userFLName) {
        this.userFLName = userFLName;
    }

    public AGVstatus getNextStaus() {
        return nextStaus;
    }

    public void setNextStaus(AGVstatus nextStaus) {
        this.nextStaus = nextStaus;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    

    public String getChangedString() {
        //return changedString;
        changedString = "notModified";
        try{
        for(AGVstatusLog log : aGVstatusLogList){
            if(log.getStatus().equals("OrderSplitted")){
                changedString = log.getMessage();
                return changedString;
            }
        }
        }catch(Exception e){}
        return changedString;
    }
    
    
    
    @Transient
    public long getDurationBetween(Date beginning, Date end){
        return ChronoUnit.SECONDS.between(convertToLocalDateTimeViaInstant(end), convertToLocalDateTimeViaInstant(beginning));
    }
    
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVorder)) {
            return false;
        }
        AGVorder other = (AGVorder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }else if(this.orderId == null && !this.materialId.equals(other.materialId) && !this.quantity.equals(other.quantity)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVorder[ orderId=" + orderId + " ]";
    }
    
}
