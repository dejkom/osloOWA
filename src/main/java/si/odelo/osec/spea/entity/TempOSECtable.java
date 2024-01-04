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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dematjasic
 */
@Entity
@Table(name = "tempOSECtable")
@NamedQueries({
    @NamedQuery(name = "TempOSECtable.findAll", query = "SELECT t FROM TempOSECtable t"),
    //@NamedQuery(name = "TempOSECtable.findById", query = "SELECT t FROM TempOSECtable t WHERE t.id = :id"),
    @NamedQuery(name = "TempOSECtable.findByUnitSN", query = "SELECT t FROM TempOSECtable t WHERE t.unitSN = :unitSN"),
    @NamedQuery(name = "TempOSECtable.findByFirstTestDatetime", query = "SELECT t FROM TempOSECtable t WHERE t.firstTestDatetime = :firstTestDatetime"),
    @NamedQuery(name = "TempOSECtable.findByUnitLN", query = "SELECT t FROM TempOSECtable t WHERE t.unitLN = :unitLN"),
    @NamedQuery(name = "TempOSECtable.findByUnitName", query = "SELECT t FROM TempOSECtable t WHERE t.unitName = :unitName"),
    @NamedQuery(name = "TempOSECtable.findByUnitType", query = "SELECT t FROM TempOSECtable t WHERE t.unitType = :unitType"),
    @NamedQuery(name = "TempOSECtable.findByUnitID", query = "SELECT t FROM TempOSECtable t WHERE t.unitID = :unitID"),
    @NamedQuery(name = "TempOSECtable.findByUnitPN", query = "SELECT t FROM TempOSECtable t WHERE t.unitPN = :unitPN"),
    @NamedQuery(name = "TempOSECtable.findByUnitPanel", query = "SELECT t FROM TempOSECtable t WHERE t.unitPanel = :unitPanel"),
    @NamedQuery(name = "TempOSECtable.findByUnitIndex", query = "SELECT t FROM TempOSECtable t WHERE t.unitIndex = :unitIndex"),
    @NamedQuery(name = "TempOSECtable.findByStationName", query = "SELECT t FROM TempOSECtable t WHERE t.stationName = :stationName"),
    @NamedQuery(name = "TempOSECtable.findByStationOS", query = "SELECT t FROM TempOSECtable t WHERE t.stationOS = :stationOS"),
    //@NamedQuery(name = "TempOSECtable.findByTestDatetime", query = "SELECT t FROM TempOSECtable t WHERE t.testDatetime = :testDatetime"),
    @NamedQuery(name = "TempOSECtable.findByStatus", query = "SELECT t FROM TempOSECtable t WHERE t.status = :status"),
    @NamedQuery(name = "TempOSECtable.findByRemark", query = "SELECT t FROM TempOSECtable t WHERE t.remark = :remark"),
    @NamedQuery(name = "TempOSECtable.findByProcessID", query = "SELECT t FROM TempOSECtable t WHERE t.processID = :processID"),
    @NamedQuery(name = "TempOSECtable.findByOperatorName", query = "SELECT t FROM TempOSECtable t WHERE t.operatorName = :operatorName"),
    @NamedQuery(name = "TempOSECtable.findByPrenosVBaan", query = "SELECT t FROM TempOSECtable t WHERE t.prenosVBaan = :prenosVBaan"),
    @NamedQuery(name = "TempOSECtable.findBySprostilVBaan", query = "SELECT t FROM TempOSECtable t WHERE t.sprostilVBaan = :sprostilVBaan"),
    @NamedQuery(name = "TempOSECtable.findBySprostilTimestamp", query = "SELECT t FROM TempOSECtable t WHERE t.sprostilTimestamp = :sprostilTimestamp"),
    @NamedQuery(name = "TempOSECtable.findByPrenosVBaanTimestamp", query = "SELECT t FROM TempOSECtable t WHERE t.prenosVBaanTimestamp = :prenosVBaanTimestamp")})
public class TempOSECtable implements Serializable {
    private static final long serialVersionUID = 1L;
    
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 36)
//    @Column(name = "ID")
//    private String id;
    @EmbeddedId
    private TempOSECtablePK idpk;
    
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
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "TestDatetime")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date testDatetime;
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
    @Column(name = "Arhivirano")
    private Boolean arhivirano;

    public TempOSECtable() {
    }

//    public TempOSECtable(String id) {
//        this.id = id;
//    }

//    public TempOSECtable(String id, String unitSN, Date firstTestDatetime, String unitLN, String unitName, String unitID, short unitIndex, String stationName, Date testDatetime, String processID) {
//        this.id = id;
//        this.unitSN = unitSN;
//        this.firstTestDatetime = firstTestDatetime;
//        this.unitLN = unitLN;
//        this.unitName = unitName;
//        this.unitID = unitID;
//        this.unitIndex = unitIndex;
//        this.stationName = stationName;
//        this.testDatetime = testDatetime;
//        this.processID = processID;
//    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public TempOSECtablePK getIdpk() {
        return idpk;
    }

    public void setIdpk(TempOSECtablePK idpk) {
        this.idpk = idpk;
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

//    public Date getTestDatetime() {
//        return testDatetime;
//    }
//
//    public void setTestDatetime(Date testDatetime) {
//        this.testDatetime = testDatetime;
//    }

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

    public Boolean getArhivirano() {
        return arhivirano;
    }

    public void setArhivirano(Boolean arhivirano) {
        this.arhivirano = arhivirano;
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
    
//    @Transient
//    public String testIDunique(){
//        String idu = "";
//        idu = id + "-"+processID;
//        return idu;
//    }

//ORIGINAL METODA hashCode
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpk != null ? idpk.hashCode() : 0);
        System.out.println("Returning hash:"+hash);
        return hash;
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        System.out.println("Returning hash zacetek:"+hash);
//        hash += (id != null ? id.hashCode() : 0);
//        System.out.println("Returning hash vmes:"+hash);
//        hash += (testDatetime != null ? testDatetime.hashCode() : 0);
//        System.out.println("Hash datuma "+testDatetime.toString()+":"+testDatetime.hashCode());
//        System.out.println("Returning hash end:"+hash);
//        return hash;
//    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        System.out.println("Sem v equals metodi. Ne vem kaj delam");
        if (!(object instanceof TempOSECtable)) {
            return false;
        }
        TempOSECtable other = (TempOSECtable) object;
        if ((this.idpk == null && other.idpk != null) || (this.idpk != null && !this.idpk.equals(other.idpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.osec.spea.entity.TempOSECtable[ id=" + idpk + " ]";
    }
    
}
