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
@Table(name = "AGV_catalog")
@NamedQueries({
    @NamedQuery(name = "AGVcatalog.findAll", query = "SELECT a FROM AGVcatalog a"),
    @NamedQuery(name = "AGVcatalog.findByCatalogId", query = "SELECT a FROM AGVcatalog a WHERE a.catalogId = :catalogId"),
    @NamedQuery(name = "AGVcatalog.findByDisabled", query = "SELECT a FROM AGVcatalog a WHERE a.disabled = :disabled"),
    @NamedQuery(name = "AGVcatalog.findByCatalogFrom", query = "SELECT a FROM AGVcatalog a WHERE a.catalogFrom = :catalogFrom"),
    @NamedQuery(name = "AGVcatalog.findByCatalogTo", query = "SELECT a FROM AGVcatalog a WHERE a.catalogTo = :catalogTo"),
    @NamedQuery(name = "AGVcatalog.findWhatICanOrder", query = "SELECT a FROM AGVcatalog a WHERE a.placeTo.placeId = :placeid"),
    @NamedQuery(name = "AGVcatalog.findWhatICanSupply", query = "SELECT a FROM AGVcatalog a WHERE a.placeFrom.placeId = :placeid"),
    @NamedQuery(name = "AGVcatalog.findByMaterialId", query = "SELECT a FROM AGVcatalog a WHERE a.materialId = :materialId")})
@SqlResultSetMapping(
        name = "CatalogMaterialCategoryMapping",
        entities = @EntityResult(
                entityClass = AGVcatalog.class,
                fields = {
                    @FieldResult(name = "catalogId", column = "catalogId"),
                    @FieldResult(name = "disabled", column = "disabled"),
                    @FieldResult(name = "catalogFrom", column = "catalogFrom"),
                    @FieldResult(name = "catalogTo", column = "catalogTo"),
                    @FieldResult(name = "materialId", column = "materialId"),
                    @FieldResult(name = "orderTypeId", column = "orderTypeId"),
                    @FieldResult(name = "placeFrom", column = "placeFrom"),
                    @FieldResult(name = "placeTo", column = "placeTo")}),
        columns = { @ColumnResult(name = "materialCategory", type = String.class),
                    @ColumnResult(name = "materialDescription", type = String.class),
                    @ColumnResult(name = "materialUnit", type = String.class)})

public class AGVcatalog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "catalogId")
    private Integer catalogId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disabled")
    private boolean disabled;
    @Column(name = "catalogFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date catalogFrom;
    @Column(name = "catalogTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date catalogTo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "materialId")
    private String materialId;
    @JoinColumn(name = "orderTypeId", referencedColumnName = "orderTypeId")
    @ManyToOne
    private AGVorderType orderTypeId;
    @JoinColumn(name = "placeFrom", referencedColumnName = "placeId")
    @ManyToOne
    private AGVplace placeFrom;
    @JoinColumn(name = "placeTo", referencedColumnName = "placeId")
    @ManyToOne
    private AGVplace placeTo;
    
    private transient String materialCategory;
    private transient String materialDescription;
    private transient String materialUnit;

    public AGVcatalog() {
    }

    public AGVcatalog(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public AGVcatalog(Integer catalogId, boolean disabled, String materialId) {
        this.catalogId = catalogId;
        this.disabled = disabled;
        this.materialId = materialId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getCatalogFrom() {
        return catalogFrom;
    }

    public void setCatalogFrom(Date catalogFrom) {
        this.catalogFrom = catalogFrom;
    }

    public Date getCatalogTo() {
        return catalogTo;
    }

    public void setCatalogTo(Date catalogTo) {
        this.catalogTo = catalogTo;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public AGVorderType getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(AGVorderType orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public AGVplace getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(AGVplace placeFrom) {
        this.placeFrom = placeFrom;
    }

    public AGVplace getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(AGVplace placeTo) {
        this.placeTo = placeTo;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catalogId != null ? catalogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVcatalog)) {
            return false;
        }
        AGVcatalog other = (AGVcatalog) object;
        if ((this.catalogId == null && other.catalogId != null) || (this.catalogId != null && !this.catalogId.equals(other.catalogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVcatalog[ catalogId=" + catalogId + " ]";
    }
    
}
