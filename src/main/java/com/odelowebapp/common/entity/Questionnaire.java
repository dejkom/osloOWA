/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "Questionnaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionnaire.findAll", query = "SELECT q FROM Questionnaire q"),
    @NamedQuery(name = "Questionnaire.findByQuestionnaireID", query = "SELECT q FROM Questionnaire q WHERE q.questionnaireID = :questionnaireID"),
    @NamedQuery(name = "Questionnaire.findByTitle", query = "SELECT q FROM Questionnaire q WHERE q.title = :title"),
    @NamedQuery(name = "Questionnaire.findByDescription", query = "SELECT q FROM Questionnaire q WHERE q.description = :description"),
    @NamedQuery(name = "Questionnaire.findByActive", query = "SELECT q FROM Questionnaire q WHERE q.active = :active")})
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "questionnaireID")
    private Integer questionnaireID;
    @Size(max = 100)
    @Column(name = "title")
    private String title;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "questionnaireID")
    private List<Question> questionList;

    public Questionnaire() {
    }

    public Questionnaire(Integer questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public Integer getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(Integer questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireID != null ? questionnaireID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questionnaire)) {
            return false;
        }
        Questionnaire other = (Questionnaire) object;
        if ((this.questionnaireID == null && other.questionnaireID != null) || (this.questionnaireID != null && !this.questionnaireID.equals(other.questionnaireID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.common.entity.Questionnaire[ questionnaireID=" + questionnaireID + " ]";
    }
    
}
