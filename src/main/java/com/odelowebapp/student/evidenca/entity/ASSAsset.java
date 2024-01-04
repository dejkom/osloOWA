/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.evidenca.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "ASS_Asset")
@NamedQueries({
    @NamedQuery(name = "ASSAsset.findAll", query = "SELECT a FROM ASSAsset a"),
    @NamedQuery(name = "ASSAsset.findByIDAsset", query = "SELECT a FROM ASSAsset a WHERE a.iDAsset = :iDAsset"),
    @NamedQuery(name = "ASSAsset.findByAssetDescription", query = "SELECT a FROM ASSAsset a WHERE a.assetDescription = :assetDescription"),
    @NamedQuery(name = "ASSAsset.findByAssetReturn", query = "SELECT a FROM ASSAsset a WHERE a.assetReturn = :assetReturn"),
    @NamedQuery(name = "ASSAsset.findByAssetDeleted", query = "SELECT a FROM ASSAsset a WHERE a.assetDeleted = :assetDeleted"),
    @NamedQuery(name = "ASSAsset.findByAssetSerialNumber", query = "SELECT a FROM ASSAsset a WHERE a.assetSerialNumber = :assetSerialNumber"),
    @NamedQuery(name = "ASSAsset.findByAssetComment", query = "SELECT a FROM ASSAsset a WHERE a.assetComment = :assetComment"),
    @NamedQuery(name = "ASSAsset.findByAssetUpdated", query = "SELECT a FROM ASSAsset a WHERE a.assetUpdated = :assetUpdated"),
    @NamedQuery(name = "ASSAsset.findByAssetUpdatedUser", query = "SELECT a FROM ASSAsset a WHERE a.assetUpdatedUser = :assetUpdatedUser"),
    @NamedQuery(name = "ASSAsset.findByAssetExpireDays", query = "SELECT a FROM ASSAsset a WHERE a.assetExpireDays = :assetExpireDays"),
    @NamedQuery(name = "ASSAsset.findByAssetExpireMonths", query = "SELECT a FROM ASSAsset a WHERE a.assetExpireMonths = :assetExpireMonths"),
    @NamedQuery(name = "ASSAsset.findByAssetExpireYears", query = "SELECT a FROM ASSAsset a WHERE a.assetExpireYears = :assetExpireYears"),
    @NamedQuery(name = "ASSAsset.findByAssetMinimumQuantityWarning", query = "SELECT a FROM ASSAsset a WHERE a.assetMinimumQuantityWarning = :assetMinimumQuantityWarning"),
    @NamedQuery(name = "ASSAsset.findByAssetMinimumQuantityCritical", query = "SELECT a FROM ASSAsset a WHERE a.assetMinimumQuantityCritical = :assetMinimumQuantityCritical")})
public class ASSAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDAsset")
    private Integer iDAsset;
    @Size(max = 100)
    @Column(name = "AssetDescription")
    private String assetDescription;
    @Column(name = "AssetReturn")
    private Boolean assetReturn;
    @Column(name = "AssetDeleted")
    private Boolean assetDeleted;
    @Size(max = 100)
    @Column(name = "AssetSerialNumber")
    private String assetSerialNumber;
    @Size(max = 4000)
    @Column(name = "AssetComment")
    private String assetComment;
    @Column(name = "AssetUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assetUpdated;
    @Size(max = 10)
    @Column(name = "AssetUpdatedUser")
    private String assetUpdatedUser;
    @Column(name = "AssetExpireDays")
    private Integer assetExpireDays;
    @Column(name = "AssetExpireMonths")
    private Integer assetExpireMonths;
    @Column(name = "AssetExpireYears")
    private Integer assetExpireYears;
    @Column(name = "AssetMinimumQuantityWarning")
    private Integer assetMinimumQuantityWarning;
    @Column(name = "AssetMinimumQuantityCritical")
    private Integer assetMinimumQuantityCritical;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDLogAsset")
    private List<ASSLog> aSSLogList;
    @OneToMany(mappedBy = "iDAssetParent")
    private List<ASSAsset> aSSAssetList;
    @JoinColumn(name = "IDAssetParent", referencedColumnName = "IDAsset")
    @ManyToOne
    private ASSAsset iDAssetParent;
    @JoinColumn(name = "IDAssetLocation", referencedColumnName = "IDLocation")
    @ManyToOne(optional = false)
    private ASSLocation iDAssetLocation;
    @JoinColumn(name = "IDAssetType", referencedColumnName = "IDType")
    @ManyToOne(optional = false)
    private ASSType iDAssetType;
    
    @Transient
    private int kolicinaKosarica;
    
    
    //returns all children to any level
    public List<ASSAsset> getAllChildren(){
        return getAllChildren(this);
    }

    //recursive function to walk the tree
    private List<ASSAsset> getAllChildren(ASSAsset parent){
        List<ASSAsset> allChildren = new ArrayList<>();

        for(ASSAsset child : parent.aSSAssetList){
            allChildren.add(child);
            allChildren.addAll(getAllChildren(child));
        }

        return allChildren;
    }
    

    public ASSAsset() {
    }

    public ASSAsset(Integer iDAsset) {
        this.iDAsset = iDAsset;
    }

    public Integer getIDAsset() {
        return iDAsset;
    }

    public void setIDAsset(Integer iDAsset) {
        this.iDAsset = iDAsset;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public Boolean getAssetReturn() {
        return assetReturn;
    }

    public void setAssetReturn(Boolean assetReturn) {
        this.assetReturn = assetReturn;
    }

    public Boolean getAssetDeleted() {
        return assetDeleted;
    }

    public void setAssetDeleted(Boolean assetDeleted) {
        this.assetDeleted = assetDeleted;
    }

    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }

    public String getAssetComment() {
        return assetComment;
    }

    public void setAssetComment(String assetComment) {
        this.assetComment = assetComment;
    }

    public Date getAssetUpdated() {
        return assetUpdated;
    }

    public void setAssetUpdated(Date assetUpdated) {
        this.assetUpdated = assetUpdated;
    }

    public String getAssetUpdatedUser() {
        return assetUpdatedUser;
    }

    public void setAssetUpdatedUser(String assetUpdatedUser) {
        this.assetUpdatedUser = assetUpdatedUser;
    }

    public Integer getAssetExpireDays() {
        return assetExpireDays;
    }

    public void setAssetExpireDays(Integer assetExpireDays) {
        this.assetExpireDays = assetExpireDays;
    }

    public Integer getAssetExpireMonths() {
        return assetExpireMonths;
    }

    public void setAssetExpireMonths(Integer assetExpireMonths) {
        this.assetExpireMonths = assetExpireMonths;
    }

    public Integer getAssetExpireYears() {
        return assetExpireYears;
    }

    public void setAssetExpireYears(Integer assetExpireYears) {
        this.assetExpireYears = assetExpireYears;
    }

    public Integer getAssetMinimumQuantityWarning() {
        return assetMinimumQuantityWarning;
    }

    public void setAssetMinimumQuantityWarning(Integer assetMinimumQuantityWarning) {
        this.assetMinimumQuantityWarning = assetMinimumQuantityWarning;
    }

    public Integer getAssetMinimumQuantityCritical() {
        return assetMinimumQuantityCritical;
    }

    public void setAssetMinimumQuantityCritical(Integer assetMinimumQuantityCritical) {
        this.assetMinimumQuantityCritical = assetMinimumQuantityCritical;
    }

    public List<ASSLog> getASSLogList() {
        return aSSLogList;
    }

    public void setASSLogList(List<ASSLog> aSSLogList) {
        this.aSSLogList = aSSLogList;
    }

    public List<ASSAsset> getASSAssetList() {
        return aSSAssetList;
    }

    public void setASSAssetList(List<ASSAsset> aSSAssetList) {
        this.aSSAssetList = aSSAssetList;
    }

    public ASSAsset getIDAssetParent() {
        return iDAssetParent;
    }

    public void setIDAssetParent(ASSAsset iDAssetParent) {
        this.iDAssetParent = iDAssetParent;
    }

    public ASSLocation getIDAssetLocation() {
        return iDAssetLocation;
    }

    public void setIDAssetLocation(ASSLocation iDAssetLocation) {
        this.iDAssetLocation = iDAssetLocation;
    }

    public ASSType getIDAssetType() {
        return iDAssetType;
    }

    public void setIDAssetType(ASSType iDAssetType) {
        this.iDAssetType = iDAssetType;
    }

    public int getKolicinaKosarica() {
        return kolicinaKosarica;
    }

    public void setKolicinaKosarica(int kolicinaKosarica) {
        this.kolicinaKosarica = kolicinaKosarica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDAsset != null ? iDAsset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASSAsset)) {
            return false;
        }
        ASSAsset other = (ASSAsset) object;
        if ((this.iDAsset == null && other.iDAsset != null) || (this.iDAsset != null && !this.iDAsset.equals(other.iDAsset))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.evidenca.entity.ASSAsset[ iDAsset=" + iDAsset + " ]";
    }
    
}
