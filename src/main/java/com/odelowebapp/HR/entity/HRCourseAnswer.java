/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "HRCourseAnswer")
@NamedQueries({
    @NamedQuery(name = "HRCourseAnswer.findAll", query = "SELECT h FROM HRCourseAnswer h"),
    @NamedQuery(name = "HRCourseAnswer.findByCourseID", query = "SELECT h FROM HRCourseAnswer h WHERE h.hRCourseAnswerPK.courseID = :courseID"),
    @NamedQuery(name = "HRCourseAnswer.findByUserID", query = "SELECT h FROM HRCourseAnswer h WHERE h.hRCourseAnswerPK.userID = :userID"),
    @NamedQuery(name = "HRCourseAnswer.findByQuestionNbr", query = "SELECT h FROM HRCourseAnswer h WHERE h.hRCourseAnswerPK.questionNbr = :questionNbr"),
    @NamedQuery(name = "HRCourseAnswer.findByAnswer", query = "SELECT h FROM HRCourseAnswer h WHERE h.answer = :answer"),
    @NamedQuery(name = "HRCourseAnswer.findByTimestamp", query = "SELECT h FROM HRCourseAnswer h WHERE h.timestamp = :timestamp"),
    @NamedQuery(name = "HRCourseAnswer.findByQuestion", query = "SELECT h FROM HRCourseAnswer h WHERE h.question = :question")})
public class HRCourseAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HRCourseAnswerPK hRCourseAnswerPK;
    @Size(max = 2147483647)
    @Column(name = "answer")
    private String answer;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Size(max = 2147483647)
    @Column(name = "question")
    private String question;
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;
    @Size(min = 1, max = 255)
    @Column(name = "solvedUserID")
    private String solvedUserID;

    public HRCourseAnswer() {
    }

    public HRCourseAnswer(HRCourseAnswerPK hRCourseAnswerPK) {
        this.hRCourseAnswerPK = hRCourseAnswerPK;
    }

    public HRCourseAnswer(int courseID, String userID, int questionNbr) {
        this.hRCourseAnswerPK = new HRCourseAnswerPK(courseID, userID, questionNbr);
    }

    public HRCourseAnswerPK getHRCourseAnswerPK() {
        return hRCourseAnswerPK;
    }

    public void setHRCourseAnswerPK(HRCourseAnswerPK hRCourseAnswerPK) {
        this.hRCourseAnswerPK = hRCourseAnswerPK;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSolvedUserID() {
        return solvedUserID;
    }

    public void setSolvedUserID(String solvedUserID) {
        this.solvedUserID = solvedUserID;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hRCourseAnswerPK != null ? hRCourseAnswerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseAnswer)) {
            return false;
        }
        HRCourseAnswer other = (HRCourseAnswer) object;
        if ((this.hRCourseAnswerPK == null && other.hRCourseAnswerPK != null) || (this.hRCourseAnswerPK != null && !this.hRCourseAnswerPK.equals(other.hRCourseAnswerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRCourseAnswer[ hRCourseAnswerPK=" + hRCourseAnswerPK + " ]";
    }
    
}
