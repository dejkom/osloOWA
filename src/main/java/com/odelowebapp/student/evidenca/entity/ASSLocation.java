/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "ASS_Location")
@NamedQueries({
    @NamedQuery(name = "ASSLocation.findAll", query = "SELECT a FROM ASSLocation a"),
    @NamedQuery(name = "ASSLocation.findByIDLocation", query = "SELECT a FROM ASSLocation a WHERE a.iDLocation = :iDLocation"),
    @NamedQuery(name = "ASSLocation.findByLocationDescription", query = "SELECT a FROM ASSLocation a WHERE a.locationDescription = :locationDescription"),
    @NamedQuery(name = "ASSLocation.findByLocationDisable", query = "SELECT a FROM ASSLocation a WHERE a.locationDisable = :locationDisable")})
public class ASSLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDLocation")
    private Integer iDLocation;
    @Size(max = 100)
    @Column(name = "LocationDescription")
    private String locationDescription;
    @Column(name = "LocationDisable")
    private Boolean locationDisable;
    @OneToMany(mappedBy = "iDLocationParent")
    private List<ASSLocation> aSSLocationList;
    @JoinColumn(name = "IDLocationParent", referencedColumnName = "IDLocation")
    @ManyToOne
    private ASSLocation iDLocationParent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDAssetLocation")
    private List<ASSAsset> aSSAssetList;

    public ASSLocation() {
    }
    
    
    //returns all children to any level
    public List<ASSLocation> getAllChildren(){
        return getAllChildren(this);
    }

    //recursive function to walk the tree
    private List<ASSLocation> getAllChildren(ASSLocation parent){
        List<ASSLocation> allChildren = new ArrayList<>();

        for(ASSLocation child : parent.aSSLocationList){
            allChildren.add(child);
            allChildren.addAll(getAllChildren(child));
        }

        return allChildren;
    }
    

    public ASSLocation(Integer iDLocation) {
        this.iDLocation = iDLocation;
    }

    public Integer getIDLocation() {
        return iDLocation;
    }

    public void setIDLocation(Integer iDLocation) {
        this.iDLocation = iDLocation;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Boolean getLocationDisable() {
        return locationDisable;
    }

    public void setLocationDisable(Boolean locationDisable) {
        this.locationDisable = locationDisable;
    }

    public List<ASSLocation> getASSLocationList() {
        return aSSLocationList;
    }

    public void setASSLocationList(List<ASSLocation> aSSLocationList) {
        this.aSSLocationList = aSSLocationList;
    }

    public ASSLocation getIDLocationParent() {
        return iDLocationParent;
    }

    public void setIDLocationParent(ASSLocation iDLocationParent) {
        this.iDLocationParent = iDLocationParent;
    }

    public List<ASSAsset> getASSAssetList() {
        return aSSAssetList;
    }

    public void setASSAssetList(List<ASSAsset> aSSAssetList) {
        this.aSSAssetList = aSSAssetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDLocation != null ? iDLocation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASSLocation)) {
            return false;
        }
        ASSLocation other = (ASSLocation) object;
        if ((this.iDLocation == null && other.iDLocation != null) || (this.iDLocation != null && !this.iDLocation.equals(other.iDLocation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.evidenca.entity.ASSLocation[ iDLocation=" + iDLocation + " ]";
    }
    
}
