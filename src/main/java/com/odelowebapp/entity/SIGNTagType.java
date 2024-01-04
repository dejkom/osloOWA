/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "SIGN_TagType")
@NamedQueries({
    @NamedQuery(name = "SIGNTagType.findAll", query = "SELECT s FROM SIGNTagType s"),
    @NamedQuery(name = "SIGNTagType.findByIdTagType", query = "SELECT s FROM SIGNTagType s WHERE s.idTagType = :idTagType"),
    @NamedQuery(name = "SIGNTagType.findByName", query = "SELECT s FROM SIGNTagType s WHERE s.name = :name")})
public class SIGNTagType implements Serializable {

    @Size(max = 8)
    @Column(name = "name")
    @Expose 
    private String name;
    
    @OneToMany(mappedBy = "idTagType")
    private List<SIGNSlide> sIGNSlideTestList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTagType")
    @Expose 
    private Integer idTagType;

    public SIGNTagType() {
    }
    
    public SIGNTagType(Integer idTagType, String name) {
        this.idTagType = idTagType;
        this.name = name;
    }

    public SIGNTagType(Integer idTagType) {
        this.idTagType = idTagType;
    }

    public Integer getIdTagType() {
        return idTagType;
    }

    public void setIdTagType(Integer idTagType) {
        this.idTagType = idTagType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTagType != null ? idTagType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SIGNTagType)) {
            return false;
        }
        SIGNTagType other = (SIGNTagType) object;
        if ((this.idTagType == null && other.idTagType != null) || (this.idTagType != null && !this.idTagType.equals(other.idTagType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.entity.SIGNTagType[ idTagType=" + idTagType + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SIGNSlide> getSIGNSlideTestList() {
        return sIGNSlideTestList;
    }

    public void setSIGNSlideTestList(List<SIGNSlide> sIGNSlideTestList) {
        this.sIGNSlideTestList = sIGNSlideTestList;
    }
    
}
