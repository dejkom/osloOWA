/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.QS.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "QS_LogTable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QSLogTable.findAll", query = "SELECT q FROM QSLogTable q"),
    @NamedQuery(name = "QSLogTable.findByIdRow", query = "SELECT q FROM QSLogTable q WHERE q.idRow = :idRow"),
    @NamedQuery(name = "QSLogTable.findByCustomer", query = "SELECT q FROM QSLogTable q WHERE q.customer = :customer"),
    @NamedQuery(name = "QSLogTable.findByPlant", query = "SELECT q FROM QSLogTable q WHERE q.plant = :plant"),
    @NamedQuery(name = "QSLogTable.findByProject", query = "SELECT q FROM QSLogTable q WHERE q.project = :project"),
    @NamedQuery(name = "QSLogTable.findByHDLSWLBlendaAdapter", query = "SELECT q FROM QSLogTable q WHERE q.hDLSWLBlendaAdapter = :hDLSWLBlendaAdapter"),
    @NamedQuery(name = "QSLogTable.findByTeam", query = "SELECT q FROM QSLogTable q WHERE q.team = :team"),
    @NamedQuery(name = "QSLogTable.findByMonth", query = "SELECT q FROM QSLogTable q WHERE q.month = :month"),
    @NamedQuery(name = "QSLogTable.findByDefectdescription", query = "SELECT q FROM QSLogTable q WHERE q.defectdescription = :defectdescription"),
    @NamedQuery(name = "QSLogTable.findByShorterDefectDescription", query = "SELECT q FROM QSLogTable q WHERE q.shorterDefectDescription = :shorterDefectDescription"),
    @NamedQuery(name = "QSLogTable.findByShorterDefectDescriptionSLO", query = "SELECT q FROM QSLogTable q WHERE q.shorterDefectDescriptionSLO = :shorterDefectDescriptionSLO"),
    @NamedQuery(name = "QSLogTable.findByCausedByInt", query = "SELECT q FROM QSLogTable q WHERE q.causedByInt = :causedByInt"),
    @NamedQuery(name = "QSLogTable.findByStatus8D", query = "SELECT q FROM QSLogTable q WHERE q.status8D = :status8D"),
    @NamedQuery(name = "QSLogTable.findByStatusOfPartAfterTheComplaint", query = "SELECT q FROM QSLogTable q WHERE q.statusOfPartAfterTheComplaint = :statusOfPartAfterTheComplaint"),
    @NamedQuery(name = "QSLogTable.findByKrajsiOpisReklamacije", query = "SELECT q FROM QSLogTable q WHERE q.krajsiOpisReklamacije = :krajsiOpisReklamacije"),
    @NamedQuery(name = "QSLogTable.findByActionNonDetection", query = "SELECT q FROM QSLogTable q WHERE q.actionNonDetection = :actionNonDetection"),
    @NamedQuery(name = "QSLogTable.findByActionOccurence", query = "SELECT q FROM QSLogTable q WHERE q.actionOccurence = :actionOccurence"),
    @NamedQuery(name = "QSLogTable.findByRootCauseOccurence", query = "SELECT q FROM QSLogTable q WHERE q.rootCauseOccurence = :rootCauseOccurence"),
    @NamedQuery(name = "QSLogTable.findByRootCauseNonDetection", query = "SELECT q FROM QSLogTable q WHERE q.rootCauseNonDetection = :rootCauseNonDetection"),
    @NamedQuery(name = "QSLogTable.findByResponseTime", query = "SELECT q FROM QSLogTable q WHERE q.responseTime = :responseTime"),
    @NamedQuery(name = "QSLogTable.findByDReportCompletedOn", query = "SELECT q FROM QSLogTable q WHERE q.dReportCompletedOn = :dReportCompletedOn"),
    @NamedQuery(name = "QSLogTable.findByReactivity3D", query = "SELECT q FROM QSLogTable q WHERE q.reactivity3D = :reactivity3D"),
    @NamedQuery(name = "QSLogTable.findByTermin8DReport", query = "SELECT q FROM QSLogTable q WHERE q.termin8DReport = :termin8DReport"),
    @NamedQuery(name = "QSLogTable.findByNr8DReport", query = "SELECT q FROM QSLogTable q WHERE q.nr8DReport = :nr8DReport"),
    @NamedQuery(name = "QSLogTable.findByDReportCompletedOn1", query = "SELECT q FROM QSLogTable q WHERE q.dReportCompletedOn1 = :dReportCompletedOn1"),
    @NamedQuery(name = "QSLogTable.findByStatus8DDoneOpen", query = "SELECT q FROM QSLogTable q WHERE q.status8DDoneOpen = :status8DDoneOpen"),
    @NamedQuery(name = "QSLogTable.findByReactivity8D", query = "SELECT q FROM QSLogTable q WHERE q.reactivity8D = :reactivity8D"),
    @NamedQuery(name = "QSLogTable.findByCauseBy", query = "SELECT q FROM QSLogTable q WHERE q.causeBy = :causeBy"),
    @NamedQuery(name = "QSLogTable.findBySupplierCaused", query = "SELECT q FROM QSLogTable q WHERE q.supplierCaused = :supplierCaused"),
    @NamedQuery(name = "QSLogTable.findByOdeloCaused", query = "SELECT q FROM QSLogTable q WHERE q.odeloCaused = :odeloCaused"),
    @NamedQuery(name = "QSLogTable.findByRejected", query = "SELECT q FROM QSLogTable q WHERE q.rejected = :rejected"),
    @NamedQuery(name = "QSLogTable.findByCleanPoint", query = "SELECT q FROM QSLogTable q WHERE q.cleanPoint = :cleanPoint"),
    @NamedQuery(name = "QSLogTable.findByStatusKosaPoReklamaciji", query = "SELECT q FROM QSLogTable q WHERE q.statusKosaPoReklamaciji = :statusKosaPoReklamaciji"),
    @NamedQuery(name = "QSLogTable.findBySupplierName", query = "SELECT q FROM QSLogTable q WHERE q.supplierName = :supplierName"),
    @NamedQuery(name = "QSLogTable.findBySupplierPB", query = "SELECT q FROM QSLogTable q WHERE q.supplierPB = :supplierPB"),
    @NamedQuery(name = "QSLogTable.findByDateOfWeiterbelastung", query = "SELECT q FROM QSLogTable q WHERE q.dateOfWeiterbelastung = :dateOfWeiterbelastung"),
    @NamedQuery(name = "QSLogTable.findByDebitDone", query = "SELECT q FROM QSLogTable q WHERE q.debitDone = :debitDone"),
    @NamedQuery(name = "QSLogTable.findByTeamLeader", query = "SELECT q FROM QSLogTable q WHERE q.teamLeader = :teamLeader"),
    @NamedQuery(name = "QSLogTable.findByTerminPSReport", query = "SELECT q FROM QSLogTable q WHERE q.terminPSReport = :terminPSReport"),
    @NamedQuery(name = "QSLogTable.findByRemarks", query = "SELECT q FROM QSLogTable q WHERE q.remarks = :remarks"),
    @NamedQuery(name = "QSLogTable.findByClaimedParts", query = "SELECT q FROM QSLogTable q WHERE q.claimedParts = :claimedParts"),
    @NamedQuery(name = "QSLogTable.findByPPMrelevantParts", query = "SELECT q FROM QSLogTable q WHERE q.pPMrelevantParts = :pPMrelevantParts"),
    @NamedQuery(name = "QSLogTable.findByXRepetitiveDefects", query = "SELECT q FROM QSLogTable q WHERE q.xRepetitiveDefects = :xRepetitiveDefects"),
    @NamedQuery(name = "QSLogTable.findByClaimDate", query = "SELECT q FROM QSLogTable q WHERE q.claimDate = :claimDate"),
    @NamedQuery(name = "QSLogTable.findByCWWeek", query = "SELECT q FROM QSLogTable q WHERE q.cWWeek = :cWWeek"),
    @NamedQuery(name = "QSLogTable.findByProductionDate", query = "SELECT q FROM QSLogTable q WHERE q.productionDate = :productionDate"),
    @NamedQuery(name = "QSLogTable.findByRecievingDate", query = "SELECT q FROM QSLogTable q WHERE q.recievingDate = :recievingDate"),
    @NamedQuery(name = "QSLogTable.findByClaimNo", query = "SELECT q FROM QSLogTable q WHERE q.claimNo = :claimNo"),
    @NamedQuery(name = "QSLogTable.findByClaimNoAdditionalMark", query = "SELECT q FROM QSLogTable q WHERE q.claimNoAdditionalMark = :claimNoAdditionalMark"),
    @NamedQuery(name = "QSLogTable.findByCommentForReport", query = "SELECT q FROM QSLogTable q WHERE q.commentForReport = :commentForReport")})
public class QSLogTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRow")
    private Integer idRow;
    @Column(name = "Customer")
    private Integer customer;
    @Column(name = "Plant")
    private Integer plant;
    @Column(name = "Project")
    private Integer project;
    @Column(name = "HDLSWLBlendaAdapter")
    private Integer hDLSWLBlendaAdapter;
    @Column(name = "TEAM")
    private Integer team;
    @Column(name = "Month")
    private Integer month;
    @Column(name = "Defectdescription")
    private Integer defectdescription;
    @Column(name = "ShorterDefectDescription")
    private Integer shorterDefectDescription;
    @Column(name = "ShorterDefectDescriptionSLO")
    private Integer shorterDefectDescriptionSLO;
    @Column(name = "CausedByInt")
    private Integer causedByInt;
    @Column(name = "Status8D")
    private Integer status8D;
    @Column(name = "StatusOfPartAfterTheComplaint")
    private Integer statusOfPartAfterTheComplaint;
    @Column(name = "KrajsiOpisReklamacije")
    private Integer krajsiOpisReklamacije;
    @Column(name = "ActionNonDetection")
    private Integer actionNonDetection;
    @Column(name = "ActionOccurence")
    private Integer actionOccurence;
    @Column(name = "RootCauseOccurence")
    private Integer rootCauseOccurence;
    @Column(name = "RootCauseNonDetection")
    private Integer rootCauseNonDetection;
    @Size(max = 250)
    @Column(name = "ResponseTime")
    private String responseTime;
    @Size(max = 250)
    @Column(name = "[3DReportCompletedOn]")
    private String dReportCompletedOn;
    @Size(max = 250)
    @Column(name = "Reactivity3D")
    private String reactivity3D;
    @Size(max = 250)
    @Column(name = "Termin8DReport")
    private String termin8DReport;
    @Size(max = 250)
    @Column(name = "Nr8DReport")
    private String nr8DReport;
    @Size(max = 250)
    @Column(name = "[8DReportCompletedOn]")
    private String dReportCompletedOn1;
    @Size(max = 250)
    @Column(name = "Status8DDoneOpen")
    private String status8DDoneOpen;
    @Size(max = 250)
    @Column(name = "Reactivity8D")
    private String reactivity8D;
    @Size(max = 250)
    @Column(name = "CauseBy")
    private String causeBy;
    @Size(max = 250)
    @Column(name = "SupplierCaused")
    private String supplierCaused;
    @Size(max = 250)
    @Column(name = "OdeloCaused")
    private String odeloCaused;
    @Size(max = 250)
    @Column(name = "Rejected")
    private String rejected;
    @Size(max = 250)
    @Column(name = "CleanPoint")
    private String cleanPoint;
    @Size(max = 250)
    @Column(name = "StatusKosaPoReklamaciji")
    private String statusKosaPoReklamaciji;
    @Size(max = 250)
    @Column(name = "SupplierName")
    private String supplierName;
    @Size(max = 250)
    @Column(name = "SupplierPB")
    private String supplierPB;
    @Size(max = 250)
    @Column(name = "DateOfWeiterbelastung")
    private String dateOfWeiterbelastung;
    @Size(max = 250)
    @Column(name = "DebitDone")
    private String debitDone;
    @Size(max = 250)
    @Column(name = "TeamLeader")
    private String teamLeader;
    @Size(max = 250)
    @Column(name = "TerminPSReport")
    private String terminPSReport;
    @Size(max = 250)
    @Column(name = "Remarks")
    private String remarks;
    @Size(max = 250)
    @Column(name = "ClaimedParts")
    private String claimedParts;
    @Size(max = 250)
    @Column(name = "PPMrelevantParts")
    private String pPMrelevantParts;
    @Size(max = 250)
    @Column(name = "XRepetitiveDefects")
    private String xRepetitiveDefects;
    @Column(name = "ClaimDate")
    @Temporal(TemporalType.DATE)
    private Date claimDate;
    @Size(max = 250)
    @Column(name = "CWWeek")
    private String cWWeek;
    @Size(max = 250)
    @Column(name = "ProductionDate")
    private String productionDate;
    @Size(max = 250)
    @Column(name = "RecievingDate")
    private String recievingDate;
    @Size(max = 250)
    @Column(name = "ClaimNo")
    private String claimNo;
    @Size(max = 250)
    @Column(name = "ClaimNoAdditionalMark")
    private String claimNoAdditionalMark;
    @Size(max = 250)
    @Column(name = "CommentForReport")
    private String commentForReport;
    
    @Size(max = 50)
    @Column(name = "CustomerPartNumber")
    private String customerPartNumber;

    public QSLogTable() {
    }

    public QSLogTable(Integer idRow) {
        this.idRow = idRow;
    }

    public Integer getIdRow() {
        return idRow;
    }

    public void setIdRow(Integer idRow) {
        this.idRow = idRow;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getPlant() {
        return plant;
    }

    public void setPlant(Integer plant) {
        this.plant = plant;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getHDLSWLBlendaAdapter() {
        return hDLSWLBlendaAdapter;
    }

    public void setHDLSWLBlendaAdapter(Integer hDLSWLBlendaAdapter) {
        this.hDLSWLBlendaAdapter = hDLSWLBlendaAdapter;
    }

    public Integer getTeam() {
        return team;
    }

    public void setTeam(Integer team) {
        this.team = team;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDefectdescription() {
        return defectdescription;
    }

    public void setDefectdescription(Integer defectdescription) {
        this.defectdescription = defectdescription;
    }

    public Integer getShorterDefectDescription() {
        return shorterDefectDescription;
    }

    public void setShorterDefectDescription(Integer shorterDefectDescription) {
        this.shorterDefectDescription = shorterDefectDescription;
    }

    public Integer getShorterDefectDescriptionSLO() {
        return shorterDefectDescriptionSLO;
    }

    public void setShorterDefectDescriptionSLO(Integer shorterDefectDescriptionSLO) {
        this.shorterDefectDescriptionSLO = shorterDefectDescriptionSLO;
    }

    public Integer getCausedByInt() {
        return causedByInt;
    }

    public void setCausedByInt(Integer causedByInt) {
        this.causedByInt = causedByInt;
    }

    public Integer getStatus8D() {
        return status8D;
    }

    public void setStatus8D(Integer status8D) {
        this.status8D = status8D;
    }

    public Integer getStatusOfPartAfterTheComplaint() {
        return statusOfPartAfterTheComplaint;
    }

    public void setStatusOfPartAfterTheComplaint(Integer statusOfPartAfterTheComplaint) {
        this.statusOfPartAfterTheComplaint = statusOfPartAfterTheComplaint;
    }

    public Integer getKrajsiOpisReklamacije() {
        return krajsiOpisReklamacije;
    }

    public void setKrajsiOpisReklamacije(Integer krajsiOpisReklamacije) {
        this.krajsiOpisReklamacije = krajsiOpisReklamacije;
    }

    public Integer getActionNonDetection() {
        return actionNonDetection;
    }

    public void setActionNonDetection(Integer actionNonDetection) {
        this.actionNonDetection = actionNonDetection;
    }

    public Integer getActionOccurence() {
        return actionOccurence;
    }

    public void setActionOccurence(Integer actionOccurence) {
        this.actionOccurence = actionOccurence;
    }

    public Integer getRootCauseOccurence() {
        return rootCauseOccurence;
    }

    public void setRootCauseOccurence(Integer rootCauseOccurence) {
        this.rootCauseOccurence = rootCauseOccurence;
    }

    public Integer getRootCauseNonDetection() {
        return rootCauseNonDetection;
    }

    public void setRootCauseNonDetection(Integer rootCauseNonDetection) {
        this.rootCauseNonDetection = rootCauseNonDetection;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getDReportCompletedOn() {
        return dReportCompletedOn;
    }

    public void setDReportCompletedOn(String dReportCompletedOn) {
        this.dReportCompletedOn = dReportCompletedOn;
    }

    public String getReactivity3D() {
        return reactivity3D;
    }

    public void setReactivity3D(String reactivity3D) {
        this.reactivity3D = reactivity3D;
    }

    public String getTermin8DReport() {
        return termin8DReport;
    }

    public void setTermin8DReport(String termin8DReport) {
        this.termin8DReport = termin8DReport;
    }

    public String getNr8DReport() {
        return nr8DReport;
    }

    public void setNr8DReport(String nr8DReport) {
        this.nr8DReport = nr8DReport;
    }

    public String getDReportCompletedOn1() {
        return dReportCompletedOn1;
    }

    public void setDReportCompletedOn1(String dReportCompletedOn1) {
        this.dReportCompletedOn1 = dReportCompletedOn1;
    }

    public String getStatus8DDoneOpen() {
        return status8DDoneOpen;
    }

    public void setStatus8DDoneOpen(String status8DDoneOpen) {
        this.status8DDoneOpen = status8DDoneOpen;
    }

    public String getReactivity8D() {
        return reactivity8D;
    }

    public void setReactivity8D(String reactivity8D) {
        this.reactivity8D = reactivity8D;
    }

    public String getCauseBy() {
        return causeBy;
    }

    public void setCauseBy(String causeBy) {
        this.causeBy = causeBy;
    }

    public String getSupplierCaused() {
        return supplierCaused;
    }

    public void setSupplierCaused(String supplierCaused) {
        this.supplierCaused = supplierCaused;
    }

    public String getOdeloCaused() {
        return odeloCaused;
    }

    public void setOdeloCaused(String odeloCaused) {
        this.odeloCaused = odeloCaused;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getCleanPoint() {
        return cleanPoint;
    }

    public void setCleanPoint(String cleanPoint) {
        this.cleanPoint = cleanPoint;
    }

    public String getStatusKosaPoReklamaciji() {
        return statusKosaPoReklamaciji;
    }

    public void setStatusKosaPoReklamaciji(String statusKosaPoReklamaciji) {
        this.statusKosaPoReklamaciji = statusKosaPoReklamaciji;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPB() {
        return supplierPB;
    }

    public void setSupplierPB(String supplierPB) {
        this.supplierPB = supplierPB;
    }

    public String getDateOfWeiterbelastung() {
        return dateOfWeiterbelastung;
    }

    public void setDateOfWeiterbelastung(String dateOfWeiterbelastung) {
        this.dateOfWeiterbelastung = dateOfWeiterbelastung;
    }

    public String getDebitDone() {
        return debitDone;
    }

    public void setDebitDone(String debitDone) {
        this.debitDone = debitDone;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getTerminPSReport() {
        return terminPSReport;
    }

    public void setTerminPSReport(String terminPSReport) {
        this.terminPSReport = terminPSReport;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClaimedParts() {
        return claimedParts;
    }

    public void setClaimedParts(String claimedParts) {
        this.claimedParts = claimedParts;
    }

    public String getPPMrelevantParts() {
        return pPMrelevantParts;
    }

    public void setPPMrelevantParts(String pPMrelevantParts) {
        this.pPMrelevantParts = pPMrelevantParts;
    }

    public String getXRepetitiveDefects() {
        return xRepetitiveDefects;
    }

    public void setXRepetitiveDefects(String xRepetitiveDefects) {
        this.xRepetitiveDefects = xRepetitiveDefects;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getCWWeek() {
        return cWWeek;
    }

    public void setCWWeek(String cWWeek) {
        this.cWWeek = cWWeek;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getRecievingDate() {
        return recievingDate;
    }

    public void setRecievingDate(String recievingDate) {
        this.recievingDate = recievingDate;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getClaimNoAdditionalMark() {
        return claimNoAdditionalMark;
    }

    public void setClaimNoAdditionalMark(String claimNoAdditionalMark) {
        this.claimNoAdditionalMark = claimNoAdditionalMark;
    }

    public String getCommentForReport() {
        return commentForReport;
    }

    public void setCommentForReport(String commentForReport) {
        this.commentForReport = commentForReport;
    }

    public Integer gethDLSWLBlendaAdapter() {
        return hDLSWLBlendaAdapter;
    }

    public void sethDLSWLBlendaAdapter(Integer hDLSWLBlendaAdapter) {
        this.hDLSWLBlendaAdapter = hDLSWLBlendaAdapter;
    }

    public String getdReportCompletedOn() {
        return dReportCompletedOn;
    }

    public void setdReportCompletedOn(String dReportCompletedOn) {
        this.dReportCompletedOn = dReportCompletedOn;
    }

    public String getdReportCompletedOn1() {
        return dReportCompletedOn1;
    }

    public void setdReportCompletedOn1(String dReportCompletedOn1) {
        this.dReportCompletedOn1 = dReportCompletedOn1;
    }

    public String getpPMrelevantParts() {
        return pPMrelevantParts;
    }

    public void setpPMrelevantParts(String pPMrelevantParts) {
        this.pPMrelevantParts = pPMrelevantParts;
    }

    public String getxRepetitiveDefects() {
        return xRepetitiveDefects;
    }

    public void setxRepetitiveDefects(String xRepetitiveDefects) {
        this.xRepetitiveDefects = xRepetitiveDefects;
    }

    public String getcWWeek() {
        System.out.println("Sem v getcWWeek()");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(claimDate);
         int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
         System.out.println("Returning:"+weekNumber);
         return weekNumber+"";
        //return cWWeek;
    }

    public void setcWWeek(String cWWeek) {
        this.cWWeek = cWWeek;
    }

    public String getCustomerPartNumber() {
        return customerPartNumber;
    }

    public void setCustomerPartNumber(String customerPartNumber) {
        this.customerPartNumber = customerPartNumber;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRow != null ? idRow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QSLogTable)) {
            return false;
        }
        QSLogTable other = (QSLogTable) object;
        if ((this.idRow == null && other.idRow != null) || (this.idRow != null && !this.idRow.equals(other.idRow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.QS.entity.QSLogTable[ idRow=" + idRow + " ]";
    }
    
}
