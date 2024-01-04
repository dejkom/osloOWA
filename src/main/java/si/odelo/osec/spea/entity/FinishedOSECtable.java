/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.odelo.osec.spea.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "finishedOSECtable")
@NamedQueries({
    @NamedQuery(name = "FinishedOSECtable.findAll", query = "SELECT f FROM FinishedOSECtable f"),
    @NamedQuery(name = "FinishedOSECtable.findById", query = "SELECT f FROM FinishedOSECtable f WHERE f.id = :id"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitSN", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitSN = :unitSN"),
    @NamedQuery(name = "FinishedOSECtable.findByFirstTestDatetime", query = "SELECT f FROM FinishedOSECtable f WHERE f.firstTestDatetime = :firstTestDatetime"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitLN", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitLN = :unitLN"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitName", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitName = :unitName"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitType", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitType = :unitType"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitID", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitID = :unitID"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitPN", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitPN = :unitPN"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitPanel", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitPanel = :unitPanel"),
    @NamedQuery(name = "FinishedOSECtable.findByUnitIndex", query = "SELECT f FROM FinishedOSECtable f WHERE f.unitIndex = :unitIndex"),
    @NamedQuery(name = "FinishedOSECtable.findByStationName", query = "SELECT f FROM FinishedOSECtable f WHERE f.stationName = :stationName"),
    @NamedQuery(name = "FinishedOSECtable.findByStationOS", query = "SELECT f FROM FinishedOSECtable f WHERE f.stationOS = :stationOS"),
    @NamedQuery(name = "FinishedOSECtable.findByTestDatetime", query = "SELECT f FROM FinishedOSECtable f WHERE f.testDatetime = :testDatetime"),
    @NamedQuery(name = "FinishedOSECtable.findByStatus", query = "SELECT f FROM FinishedOSECtable f WHERE f.status = :status"),
    @NamedQuery(name = "FinishedOSECtable.findByRemark", query = "SELECT f FROM FinishedOSECtable f WHERE f.remark = :remark"),
    @NamedQuery(name = "FinishedOSECtable.findByProcessID", query = "SELECT f FROM FinishedOSECtable f WHERE f.processID = :processID"),
    @NamedQuery(name = "FinishedOSECtable.findByOperatorName", query = "SELECT f FROM FinishedOSECtable f WHERE f.operatorName = :operatorName"),
    @NamedQuery(name = "FinishedOSECtable.findByPrenosVBaan", query = "SELECT f FROM FinishedOSECtable f WHERE f.prenosVBaan = :prenosVBaan"),
    @NamedQuery(name = "FinishedOSECtable.findBySprostilVBaan", query = "SELECT f FROM FinishedOSECtable f WHERE f.sprostilVBaan = :sprostilVBaan"),
    @NamedQuery(name = "FinishedOSECtable.findBySprostilTimestamp", query = "SELECT f FROM FinishedOSECtable f WHERE f.sprostilTimestamp = :sprostilTimestamp"),
    @NamedQuery(name = "FinishedOSECtable.findByPrenosVBaanTimestamp", query = "SELECT f FROM FinishedOSECtable f WHERE f.prenosVBaanTimestamp = :prenosVBaanTimestamp")})
public class FinishedOSECtable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UnitSN")
    private String unitSN;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FirstTestDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstTestDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UnitLN")
    private String unitLN;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UnitName")
    private String unitName;
    @Size(max = 50)
    @Column(name = "UnitType")
    private String unitType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "UnitID")
    private String unitID;
    @Size(max = 16)
    @Column(name = "UnitPN")
    private String unitPN;
    @Size(max = 255)
    @Column(name = "UnitPanel")
    private String unitPanel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UnitIndex")
    private short unitIndex;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "StationName")
    private String stationName;
    @Size(max = 50)
    @Column(name = "StationOS")
    private String stationOS;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TestDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testDatetime;
    @Column(name = "Status")
    private Short status;
    @Size(max = 255)
    @Column(name = "Remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "ProcessID")
    private String processID;
    @Size(max = 50)
    @Column(name = "OperatorName")
    private String operatorName;
    @Column(name = "PrenosVBaan")
    private Boolean prenosVBaan;
    @Size(max = 2147483647)
    @Column(name = "SprostilVBaan")
    private String sprostilVBaan;
    @Column(name = "SprostilTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprostilTimestamp;
    @Column(name = "PrenosVBaanTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prenosVBaanTimestamp;

    public FinishedOSECtable() {
    }

    public FinishedOSECtable(String id) {
        this.id = id;
    }

    public FinishedOSECtable(String id, String unitSN, Date firstTestDatetime, String unitLN, String unitName, String unitID, short unitIndex, String stationName, Date testDatetime, String processID) {
        this.id = id;
        this.unitSN = unitSN;
        this.firstTestDatetime = firstTestDatetime;
        this.unitLN = unitLN;
        this.unitName = unitName;
        this.unitID = unitID;
        this.unitIndex = unitIndex;
        this.stationName = stationName;
        this.testDatetime = testDatetime;
        this.processID = processID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitSN() {
        return unitSN;
    }

    public void setUnitSN(String unitSN) {
        this.unitSN = unitSN;
    }

    public Date getFirstTestDatetime() {
        return firstTestDatetime;
    }

    public void setFirstTestDatetime(Date firstTestDatetime) {
        this.firstTestDatetime = firstTestDatetime;
    }

    public String getUnitLN() {
        return unitLN;
    }

    public void setUnitLN(String unitLN) {
        this.unitLN = unitLN;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitPN() {
        return unitPN;
    }

    public void setUnitPN(String unitPN) {
        this.unitPN = unitPN;
    }

    public String getUnitPanel() {
        return unitPanel;
    }

    public void setUnitPanel(String unitPanel) {
        this.unitPanel = unitPanel;
    }

    public short getUnitIndex() {
        return unitIndex;
    }

    public void setUnitIndex(short unitIndex) {
        this.unitIndex = unitIndex;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationOS() {
        return stationOS;
    }

    public void setStationOS(String stationOS) {
        this.stationOS = stationOS;
    }

    public Date getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(Date testDatetime) {
        this.testDatetime = testDatetime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Boolean getPrenosVBaan() {
        return prenosVBaan;
    }

    public void setPrenosVBaan(Boolean prenosVBaan) {
        this.prenosVBaan = prenosVBaan;
    }

    public String getSprostilVBaan() {
        return sprostilVBaan;
    }

    public void setSprostilVBaan(String sprostilVBaan) {
        this.sprostilVBaan = sprostilVBaan;
    }

    public Date getSprostilTimestamp() {
        return sprostilTimestamp;
    }

    public void setSprostilTimestamp(Date sprostilTimestamp) {
        this.sprostilTimestamp = sprostilTimestamp;
    }

    public Date getPrenosVBaanTimestamp() {
        return prenosVBaanTimestamp;
    }

    public void setPrenosVBaanTimestamp(Date prenosVBaanTimestamp) {
        this.prenosVBaanTimestamp = prenosVBaanTimestamp;
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
        if (!(object instanceof FinishedOSECtable)) {
            return false;
        }
        FinishedOSECtable other = (FinishedOSECtable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.osec.spea.entity.FinishedOSECtable[ id=" + id + " ]";
    }
    
}
