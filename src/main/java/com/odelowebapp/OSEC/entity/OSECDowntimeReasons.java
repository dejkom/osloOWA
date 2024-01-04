/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.OSEC.entity;

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
@Table(name = "OSEC_DowntimeReasons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OSECDowntimeReasons.findAll", query = "SELECT o FROM OSECDowntimeReasons o"),
    @NamedQuery(name = "OSECDowntimeReasons.findByReasonID", query = "SELECT o FROM OSECDowntimeReasons o WHERE o.reasonID = :reasonID"),
    @NamedQuery(name = "OSECDowntimeReasons.findByText", query = "SELECT o FROM OSECDowntimeReasons o WHERE o.text = :text"),
    @NamedQuery(name = "OSECDowntimeReasons.findByReasonGroup", query = "SELECT o FROM OSECDowntimeReasons o WHERE o.reasonGroup = :reasonGroup"),
    @NamedQuery(name = "OSECDowntimeReasons.findByReasonSubgroup", query = "SELECT o FROM OSECDowntimeReasons o WHERE o.reasonSubgroup = :reasonSubgroup"),
    @NamedQuery(name = "OSECDowntimeReasons.findByActive", query = "SELECT o FROM OSECDowntimeReasons o WHERE o.active = :active")})
public class OSECDowntimeReasons implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reasonID")
    private Integer reasonID;
    @Size(max = 2147483647)
    @Column(name = "text")
    private String text;
    @Size(max = 50)
    @Column(name = "reasonGroup")
    private String reasonGroup;
    @Size(max = 50)
    @Column(name = "reasonSubgroup")
    private String reasonSubgroup;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "reasonIDfk")
    private List<OSECDowntime> oSECDowntimeList;

    public OSECDowntimeReasons() {
    }

    public OSECDowntimeReasons(Integer reasonID) {
        this.reasonID = reasonID;
    }

    public Integer getReasonID() {
        return reasonID;
    }

    public void setReasonID(Integer reasonID) {
        this.reasonID = reasonID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReasonGroup() {
        return reasonGroup;
    }

    public void setReasonGroup(String reasonGroup) {
        this.reasonGroup = reasonGroup;
    }

    public String getReasonSubgroup() {
        return reasonSubgroup;
    }

    public void setReasonSubgroup(String reasonSubgroup) {
        this.reasonSubgroup = reasonSubgroup;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<OSECDowntime> getOSECDowntimeList() {
        return oSECDowntimeList;
    }

    public void setOSECDowntimeList(List<OSECDowntime> oSECDowntimeList) {
        this.oSECDowntimeList = oSECDowntimeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reasonID != null ? reasonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OSECDowntimeReasons)) {
            return false;
        }
        OSECDowntimeReasons other = (OSECDowntimeReasons) object;
        if ((this.reasonID == null && other.reasonID != null) || (this.reasonID != null && !this.reasonID.equals(other.reasonID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.OSEC.entity.OSECDowntimeReasons[ reasonID=" + reasonID + " ]";
    }
    
}
