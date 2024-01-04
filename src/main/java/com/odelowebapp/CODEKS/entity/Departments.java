/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.CODEKS.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "Departments")
@NamedQueries({
    @NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d"),
    @NamedQuery(name = "Departments.findById", query = "SELECT d FROM Departments d WHERE d.id = :id"),
    @NamedQuery(name = "Departments.findByDeleted", query = "SELECT d FROM Departments d WHERE d.deleted = :deleted"),
    @NamedQuery(name = "Departments.findByName", query = "SELECT d FROM Departments d WHERE d.name = :name"),
    @NamedQuery(name = "Departments.findByCode", query = "SELECT d FROM Departments d WHERE d.code = :code"),
    @NamedQuery(name = "Departments.findByDepartmentHeadId", query = "SELECT d FROM Departments d WHERE d.departmentHeadId = :departmentHeadId"),
    @NamedQuery(name = "Departments.findByDepartmentHeadDeputyId", query = "SELECT d FROM Departments d WHERE d.departmentHeadDeputyId = :departmentHeadDeputyId"),
    @NamedQuery(name = "Departments.findByParentId", query = "SELECT d FROM Departments d WHERE d.parentId = :parentId"),
    @NamedQuery(name = "Departments.findByType", query = "SELECT d FROM Departments d WHERE d.type = :type"),
    @NamedQuery(name = "Departments.findByLft", query = "SELECT d FROM Departments d WHERE d.lft = :lft"),
    @NamedQuery(name = "Departments.findByRgt", query = "SELECT d FROM Departments d WHERE d.rgt = :rgt"),
    @NamedQuery(name = "Departments.findByPosition", query = "SELECT d FROM Departments d WHERE d.position = :position"),
    @NamedQuery(name = "Departments.findByFullName", query = "SELECT d FROM Departments d WHERE d.fullName = :fullName"),
    @NamedQuery(name = "Departments.findByDefaultPassageId", query = "SELECT d FROM Departments d WHERE d.defaultPassageId = :defaultPassageId"),
    @NamedQuery(name = "Departments.findByLAReplacementRequired", query = "SELECT d FROM Departments d WHERE d.lAReplacementRequired = :lAReplacementRequired")})
public class Departments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Deleted")
    private Boolean deleted;
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    @Size(max = 255)
    @Column(name = "Code")
    private String code;
    @Column(name = "DepartmentHeadId")
    private Integer departmentHeadId;
    @Column(name = "DepartmentHeadDeputyId")
    private Integer departmentHeadDeputyId;
    @Column(name = "ParentId")
    private Integer parentId;
    @Column(name = "Type")
    private Integer type;
    @Column(name = "lft")
    private Integer lft;
    @Column(name = "rgt")
    private Integer rgt;
    @Column(name = "Position")
    private Integer position;
    @Size(max = 512)
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "DefaultPassageId")
    private Integer defaultPassageId;
    @Column(name = "LAReplacementRequired")
    private Boolean lAReplacementRequired;

    public Departments() {
    }

    public Departments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDepartmentHeadId() {
        return departmentHeadId;
    }

    public void setDepartmentHeadId(Integer departmentHeadId) {
        this.departmentHeadId = departmentHeadId;
    }

    public Integer getDepartmentHeadDeputyId() {
        return departmentHeadDeputyId;
    }

    public void setDepartmentHeadDeputyId(Integer departmentHeadDeputyId) {
        this.departmentHeadDeputyId = departmentHeadDeputyId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getDefaultPassageId() {
        return defaultPassageId;
    }

    public void setDefaultPassageId(Integer defaultPassageId) {
        this.defaultPassageId = defaultPassageId;
    }

    public Boolean getLAReplacementRequired() {
        return lAReplacementRequired;
    }

    public void setLAReplacementRequired(Boolean lAReplacementRequired) {
        this.lAReplacementRequired = lAReplacementRequired;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.CODEKS.entity.Departments[ id=" + id + " ]";
    }
    
}
