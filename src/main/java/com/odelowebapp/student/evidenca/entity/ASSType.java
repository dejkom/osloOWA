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
@Table(name = "ASS_Type")
@NamedQueries({
    @NamedQuery(name = "ASSType.findAll", query = "SELECT a FROM ASSType a"),
    @NamedQuery(name = "ASSType.findByIDType", query = "SELECT a FROM ASSType a WHERE a.iDType = :iDType"),
    @NamedQuery(name = "ASSType.findByTypeDescription", query = "SELECT a FROM ASSType a WHERE a.typeDescription = :typeDescription"),
    @NamedQuery(name = "ASSType.findByTypeDisable", query = "SELECT a FROM ASSType a WHERE a.typeDisable = :typeDisable"),
    @NamedQuery(name = "ASSType.findByTypeReturn", query = "SELECT a FROM ASSType a WHERE a.typeReturn = :typeReturn")})
public class ASSType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDType")
    private Integer iDType;
    @Size(max = 100)
    @Column(name = "TypeDescription")
    private String typeDescription;
    @Column(name = "TypeDisable")
    private Boolean typeDisable;
    @Column(name = "TypeReturn")
    private Boolean typeReturn;
    @OneToMany(mappedBy = "iDTypeParent")
    private List<ASSType> aSSTypeList;
    @JoinColumn(name = "IDTypeParent", referencedColumnName = "IDType")
    @ManyToOne
    private ASSType iDTypeParent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDAssetType")
    private List<ASSAsset> aSSAssetList;
    
    
    //returns all children to any level
    public List<ASSType> getAllChildren(){
        return getAllChildren(this);
    }

    //recursive function to walk the tree
    private List<ASSType> getAllChildren(ASSType parent){
        List<ASSType> allChildren = new ArrayList<>();

        for(ASSType child : parent.aSSTypeList){
            allChildren.add(child);
            allChildren.addAll(getAllChildren(child));
        }

        return allChildren;
    }
    
    

    public ASSType() {
    }

    public ASSType(Integer iDType) {
        this.iDType = iDType;
    }

    public Integer getIDType() {
        return iDType;
    }

    public void setIDType(Integer iDType) {
        this.iDType = iDType;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Boolean getTypeDisable() {
        return typeDisable;
    }

    public void setTypeDisable(Boolean typeDisable) {
        this.typeDisable = typeDisable;
    }

    public Boolean getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(Boolean typeReturn) {
        this.typeReturn = typeReturn;
    }

    public List<ASSType> getASSTypeList() {
        return aSSTypeList;
    }

    public void setASSTypeList(List<ASSType> aSSTypeList) {
        this.aSSTypeList = aSSTypeList;
    }

    public ASSType getIDTypeParent() {
        return iDTypeParent;
    }

    public void setIDTypeParent(ASSType iDTypeParent) {
        this.iDTypeParent = iDTypeParent;
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
        hash += (iDType != null ? iDType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASSType)) {
            return false;
        }
        ASSType other = (ASSType) object;
        if ((this.iDType == null && other.iDType != null) || (this.iDType != null && !this.iDType.equals(other.iDType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.evidenca.entity.ASSType[ iDType=" + iDType + " ]";
    }
    
}
