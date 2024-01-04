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
@Table(name = "AGV_place")
@NamedQueries({
    @NamedQuery(name = "AGVplace.findAll", query = "SELECT a FROM AGVplace a"),
    @NamedQuery(name = "AGVplace.findByPlaceId", query = "SELECT a FROM AGVplace a WHERE a.placeId = :placeId"),
    @NamedQuery(name = "AGVplace.findByPlaceName", query = "SELECT a FROM AGVplace a WHERE a.placeName = :placeName"),
    @NamedQuery(name = "AGVplace.findByDisabled", query = "SELECT a FROM AGVplace a WHERE a.disabled = :disabled"),
    @NamedQuery(name = "AGVplace.findByChanel", query = "SELECT a FROM AGVplace a WHERE a.chanel = :chanel"),
    @NamedQuery(name = "AGVplace.findByCoordinates", query = "SELECT a FROM AGVplace a WHERE a.coordinates = :coordinates")})
public class AGVplace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "placeId")
    private Integer placeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "placeName")
    private String placeName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disabled")
    private boolean disabled;
    @Size(max = 100)
    @Column(name = "chanel")
    private String chanel;
    @Size(max = 10)
    @Column(name = "coordinates")
    private String coordinates;
    @OneToMany(mappedBy = "placeFrom")
    private List<AGVcatalog> aGVcatalogList;
    @OneToMany(mappedBy = "placeTo")
    private List<AGVcatalog> aGVcatalogList1;
    @OneToMany(mappedBy = "placeTo")
    private List<AGVorder> aGVorderList;
    @OneToMany(mappedBy = "placeFrom")
    private List<AGVorder> aGVorderList1;

    public AGVplace() {
    }

    public AGVplace(Integer placeId) {
        this.placeId = placeId;
    }

    public AGVplace(Integer placeId, String placeName, boolean disabled) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.disabled = disabled;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<AGVcatalog> getAGVcatalogList() {
        return aGVcatalogList;
    }

    public void setAGVcatalogList(List<AGVcatalog> aGVcatalogList) {
        this.aGVcatalogList = aGVcatalogList;
    }

    public List<AGVcatalog> getAGVcatalogList1() {
        return aGVcatalogList1;
    }

    public void setAGVcatalogList1(List<AGVcatalog> aGVcatalogList1) {
        this.aGVcatalogList1 = aGVcatalogList1;
    }

    public List<AGVorder> getAGVorderList() {
        return aGVorderList;
    }

    public void setAGVorderList(List<AGVorder> aGVorderList) {
        this.aGVorderList = aGVorderList;
    }

    public List<AGVorder> getAGVorderList1() {
        return aGVorderList1;
    }

    public void setAGVorderList1(List<AGVorder> aGVorderList1) {
        this.aGVorderList1 = aGVorderList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (placeId != null ? placeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVplace)) {
            return false;
        }
        AGVplace other = (AGVplace) object;
        if ((this.placeId == null && other.placeId != null) || (this.placeId != null && !this.placeId.equals(other.placeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVplace[ placeId=" + placeId + " ]";
    }
    
}
