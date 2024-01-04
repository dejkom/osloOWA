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

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "SIGN_Slide")
@NamedQueries({
    @NamedQuery(name = "SIGNSlide.findAll", query = "SELECT s FROM SIGNSlide s"),
    @NamedQuery(name = "SIGNSlide.findByIdSlide", query = "SELECT s FROM SIGNSlide s WHERE s.idSlide = :idSlide"),
    @NamedQuery(name = "SIGNSlide.findBySourceUrl", query = "SELECT s FROM SIGNSlide s WHERE s.sourceUrl = :sourceUrl"),
    @NamedQuery(name = "SIGNSlide.findByTimeOnDisplay", query = "SELECT s FROM SIGNSlide s WHERE s.timeOnDisplay = :timeOnDisplay"),
    @NamedQuery(name = "SIGNSlide.findByFileName", query = "SELECT s FROM SIGNSlide s WHERE s.fileName = :fileName"),
    @NamedQuery(name = "SIGNSlide.findByPosition", query = "SELECT s FROM SIGNSlide s WHERE s.position = :position")})
public class SIGNSlide implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSlide")
    @Expose
    private Integer idSlide;
    
    @Size(max = 512)
    @Column(name = "sourceUrl")
    @Expose
    private String sourceUrl;
    
    @Column(name = "timeOnDisplay")
    @Expose
    private Integer timeOnDisplay;
    
    @Size(max = 255)
    @Column(name = "fileName")
    @Expose
    private String fileName;
    
    @Column(name = "position")
    @Expose
    private Integer position;
    
    @JoinColumn(name = "idPresentation", referencedColumnName = "idPresentation")
    @ManyToOne
    @Expose
    private SIGNPresentation idPresentation;
    
    @JoinColumn(name = "idTagType", referencedColumnName = "idTagType")
    @ManyToOne
    @Expose
    private SIGNTagType idTagType;
   
    public SIGNSlide() {
    }
    
    public SIGNSlide(String sourceUrl, Integer timeOnDisplay, String fileName, SIGNTagType idTagType, SIGNPresentation idPresentation) {
        this.sourceUrl = sourceUrl;
        this.timeOnDisplay = timeOnDisplay;
        this.fileName = fileName;
        this.idTagType = idTagType;
        this.idPresentation = idPresentation;
    }

    public SIGNSlide(Integer idSlide) {
        this.idSlide = idSlide;
    }

    public Integer getIdSlide() {
        return idSlide;
    }

    public void setIdSlide(Integer idSlide) {
        this.idSlide = idSlide;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Integer getTimeOnDisplay() {
        return timeOnDisplay;
    }

    public void setTimeOnDisplay(Integer timeOnDisplay) {
        this.timeOnDisplay = timeOnDisplay;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public SIGNPresentation getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(SIGNPresentation idPresentation) {
        this.idPresentation = idPresentation;
    }

    public SIGNTagType getIdTagType() {
        return idTagType;
    }

    public void setIdTagType(SIGNTagType idTagType) {
        this.idTagType = idTagType;
    }
    
    public boolean showTumbnail(){
//        if(idTagType.getName().equals("img")){
//            return true;
//        }
//        return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSlide != null ? idSlide.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SIGNSlide)) {
            return false;
        }
        SIGNSlide other = (SIGNSlide) object;
        if ((this.idSlide == null && other.idSlide != null) || (this.idSlide != null && !this.idSlide.equals(other.idSlide))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.entity.SIGNSlideTest[ idSlide=" + idSlide + " ]";
    }
    
}
