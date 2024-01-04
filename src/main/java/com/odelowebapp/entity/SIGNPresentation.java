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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author itstudent
 */
@Entity
@Table(name = "SIGN_Presentation")
@NamedQueries({
    @NamedQuery(name = "SIGNPresentation.findAll", query = "SELECT s FROM SIGNPresentation s"),
    @NamedQuery(name = "SIGNPresentation.findByIdPresentation", query = "SELECT s FROM SIGNPresentation s WHERE s.idPresentation = :idPresentation"),
    @NamedQuery(name = "SIGNPresentation.findByName", query = "SELECT s FROM SIGNPresentation s WHERE s.name = :name"),
    @NamedQuery(name = "SIGNPresentation.findByDate", query = "SELECT s FROM SIGNPresentation s WHERE s.date = :date")})
public class SIGNPresentation implements Serializable {

    @Size(max = 128)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "idPresentation")
    private List<SIGNSlide> sIGNSlideList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPresentation")
    @Expose
    private Integer idPresentation;


    @Column(name = "date")
    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public SIGNPresentation() {
    }
    
    public SIGNPresentation(String name, Date date){
        this.name = name;
        this.date = date;
    }

    public SIGNPresentation(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }

    public Integer getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }


    public Date getDate() throws ParseException {
        return this.date;
    }

    public void setDate(Date date){
        this.date = date;
    }
    
    public String getStringDate(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String newDate = format.format(this.date);
        return newDate;
    }
    
    public String getStringUpdatedAt(){
        if(this.updatedAt == null)
            return "" ;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String newDate = format.format(this.updatedAt);
        return newDate;
    }  
    
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SIGNSlide> getSIGNSlideListOrdered() {
        Comparator<SIGNSlide> compareByPosition = (SIGNSlide x1, SIGNSlide x2)
                -> x1.getPosition().compareTo(x2.getPosition());
        Collections.sort(sIGNSlideList, compareByPosition);
        return sIGNSlideList;
    }
    
    public void setSIGNSlideListOrdered(List<SIGNSlide> sIGNSlideTestList) {
        this.sIGNSlideList = sIGNSlideTestList;
    }
    
    public List<SIGNSlide> getSIGNSlideList() {
        return sIGNSlideList;
    }

    public void setSIGNSlideList(List<SIGNSlide> sIGNSlideList) {
        this.sIGNSlideList = sIGNSlideList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPresentation != null ? idPresentation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SIGNPresentation)) {
            return false;
        }
        SIGNPresentation other = (SIGNPresentation) object;
        if ((this.idPresentation == null && other.idPresentation != null) || (this.idPresentation != null && !this.idPresentation.equals(other.idPresentation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[ idPresentation=" + idPresentation + " ]";
    }
}
