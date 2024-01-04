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

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "AGV_material")
@NamedQueries({
    @NamedQuery(name = "AGVmaterial.findAll", query = "SELECT a FROM AGVmaterial a"),
    @NamedQuery(name = "AGVmaterial.findByMaterialId", query = "SELECT a FROM AGVmaterial a WHERE a.materialId = :materialId"),
    @NamedQuery(name = "AGVmaterial.findByMaterialDescription", query = "SELECT a FROM AGVmaterial a WHERE a.materialDescription = :materialDescription")})
public class AGVmaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "materialId")
    private String materialId;
    @Size(max = 100)
    @Column(name = "materialDescription")
    private String materialDescription;
    @Size(max = 100)
    @Column(name = "materialCategory")
    private String materialCategory;
    @Size(max = 100)
    @Column(name = "materialUnit")
    private String materialUnit;
    

    public AGVmaterial() {
    }

    public AGVmaterial(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
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
        hash += (materialId != null ? materialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AGVmaterial)) {
            return false;
        }
        AGVmaterial other = (AGVmaterial) object;
        if ((this.materialId == null && other.materialId != null) || (this.materialId != null && !this.materialId.equals(other.materialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.AGV.entity.AGVmaterial[ materialId=" + materialId + " ]";
    }
    
}
