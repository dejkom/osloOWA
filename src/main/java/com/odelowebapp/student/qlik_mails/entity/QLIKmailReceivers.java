/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.odelowebapp.student.qlik_mails.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author itstudent
 */
@Entity
@Table(name = "QLIK_mailReceivers")
@NamedQueries({
        @NamedQuery(name = "QLIKmailReceivers.findAll", query = "SELECT q FROM QLIKmailReceivers q"),
        @NamedQuery(name = "QLIKmailReceivers.findByReportName", query = "SELECT q FROM QLIKmailReceivers q WHERE q.reportName = :reportName"),
        @NamedQuery(name = "QLIKmailReceivers.findByMailListTO", query = "SELECT q FROM QLIKmailReceivers q WHERE q.mailListTO = :mailListTO"),
        @NamedQuery(name = "QLIKmailReceivers.findByMailListCC", query = "SELECT q FROM QLIKmailReceivers q WHERE q.mailListCC = :mailListCC"),
        @NamedQuery(name = "QLIKmailReceivers.findByMailListBCC", query = "SELECT q FROM QLIKmailReceivers q WHERE q.mailListBCC = :mailListBCC")})
public class QLIKmailReceivers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "report_name")
    private String reportName;
    @Size(max = 2147483647)
    @Column(name = "mailListTO")
    private String mailListTO;
    @Size(max = 2147483647)
    @Column(name = "mailListCC")
    private String mailListCC;
    @Size(max = 2147483647)
    @Column(name = "mailListBCC")
    private String mailListBCC;

    public QLIKmailReceivers() {
    }

    public QLIKmailReceivers(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getMailListTO() {
        return mailListTO;
    }

    public void setMailListTO(String mailListTO) {
        this.mailListTO = mailListTO;
    }

    public String getMailListCC() {
        return mailListCC;
    }

    public void setMailListCC(String mailListCC) {
        this.mailListCC = mailListCC;
    }

    public String getMailListBCC() {
        return mailListBCC;
    }

    public void setMailListBCC(String mailListBCC) {
        this.mailListBCC = mailListBCC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportName != null ? reportName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QLIKmailReceivers)) {
            return false;
        }
        QLIKmailReceivers other = (QLIKmailReceivers) object;
        return (this.reportName != null || other.reportName == null) && (this.reportName == null || this.reportName.equals(other.reportName));
    }

    @Override
    public String toString() {
        return "com.odelowebapp.student.qlik_mails.entity.QLIKmailReceivers[ reportName=" + reportName + " ]";
    }

}
