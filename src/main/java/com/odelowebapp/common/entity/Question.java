/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "Question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findByQuestionID", query = "SELECT q FROM Question q WHERE q.questionID = :questionID"),
    @NamedQuery(name = "Question.findByQuestion", query = "SELECT q FROM Question q WHERE q.question = :question"),
    @NamedQuery(name = "Question.findByAnswer", query = "SELECT q FROM Question q WHERE q.answer = :answer"),
    @NamedQuery(name = "Question.findBySortColumn", query = "SELECT q FROM Question q WHERE q.sortColumn = :sortColumn"),
    @NamedQuery(name = "Question.findByQuestionType", query = "SELECT q FROM Question q WHERE q.questionType = :questionType"),
    @NamedQuery(name = "Question.findByOptions", query = "SELECT q FROM Question q WHERE q.options = :options")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "questionID")
    private Integer questionID;
    @Size(max = 2147483647)
    @Column(name = "question")
    private String question;
    @Size(max = 2147483647)
    @Column(name = "answer")
    private String answer;
    @Column(name = "sortColumn")
    private Integer sortColumn;
    @Size(max = 50)
    @Column(name = "questionType")
    private String questionType;
    @Size(max = 2147483647)
    @Column(name = "options")
    private String options;
    @JoinColumn(name = "questionnaireID", referencedColumnName = "questionnaireID")
    @ManyToOne
    private Questionnaire questionnaireID;

    public Question() {
    }

    public Question(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(Integer sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Questionnaire getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(Questionnaire questionnaireID) {
        this.questionnaireID = questionnaireID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionID != null ? questionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionID == null && other.questionID != null) || (this.questionID != null && !this.questionID.equals(other.questionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.common.entity.Question[ questionID=" + questionID + " ]";
    }
    
}
