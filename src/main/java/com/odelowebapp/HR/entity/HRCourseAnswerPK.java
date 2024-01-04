/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dematjasic
 */
@Embeddable
public class HRCourseAnswerPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private int courseID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "userID")
    private String userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "questionNbr")
    private int questionNbr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "questionnaireID")
    private int questionnaireID;

    public HRCourseAnswerPK() {
    }

    public HRCourseAnswerPK(int courseID, String userID, int questionNbr) {
        this.courseID = courseID;
        this.userID = userID;
        this.questionNbr = questionNbr;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getQuestionNbr() {
        return questionNbr;
    }

    public void setQuestionNbr(int questionNbr) {
        this.questionNbr = questionNbr;
    }

    public int getQuestionnaireID() {
        return questionnaireID;
    }

    public void setQuestionnaireID(int questionnaireID) {
        this.questionnaireID = questionnaireID;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) courseID;
        hash += (userID != null ? userID.hashCode() : 0);
        hash += (int) questionNbr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseAnswerPK)) {
            return false;
        }
        HRCourseAnswerPK other = (HRCourseAnswerPK) object;
        if (this.courseID != other.courseID) {
            return false;
        }
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        if (this.questionNbr != other.questionNbr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.HR.entity.HRCourseAnswerPK[ courseID=" + courseID + ", userID=" + userID + ", questionNbr=" + questionNbr + " ]";
    }
    
}
