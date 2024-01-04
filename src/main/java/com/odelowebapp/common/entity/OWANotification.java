/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.common.entity;

import java.io.Serializable;
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
@Table(name = "OWA_Notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OWANotification.findAll", query = "SELECT o FROM OWANotification o"),
    @NamedQuery(name = "OWANotification.findById", query = "SELECT o FROM OWANotification o WHERE o.id = :id"),
    @NamedQuery(name = "OWANotification.findByTitle", query = "SELECT o FROM OWANotification o WHERE o.title = :title"),
    @NamedQuery(name = "OWANotification.findByNotificationContent", query = "SELECT o FROM OWANotification o WHERE o.notificationContent = :notificationContent"),
    @NamedQuery(name = "OWANotification.findByIsPinBoardNotification", query = "SELECT o FROM OWANotification o WHERE o.isPinBoardNotification = :isPinBoardNotification"),
    @NamedQuery(name = "OWANotification.findByIsToastNotification", query = "SELECT o FROM OWANotification o WHERE o.isToastNotification = :isToastNotification"),
    @NamedQuery(name = "OWANotification.findByVisibleFrom", query = "SELECT o FROM OWANotification o WHERE o.visibleFrom = :visibleFrom"),
    @NamedQuery(name = "OWANotification.findByVisibleTo", query = "SELECT o FROM OWANotification o WHERE o.visibleTo = :visibleTo"),
    @NamedQuery(name = "OWANotification.findByCreatedDateTime", query = "SELECT o FROM OWANotification o WHERE o.createdDateTime = :createdDateTime"),
    @NamedQuery(name = "OWANotification.findByCreator", query = "SELECT o FROM OWANotification o WHERE o.creator = :creator"),
    @NamedQuery(name = "OWANotification.findByLinkURL", query = "SELECT o FROM OWANotification o WHERE o.linkURL = :linkURL"),
    @NamedQuery(name = "OWANotification.findByShowArea", query = "SELECT o FROM OWANotification o WHERE o.showArea = :showArea"),
    @NamedQuery(name = "OWANotification.findBySeverityStyle", query = "SELECT o FROM OWANotification o WHERE o.severityStyle = :severityStyle")})
public class OWANotification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "title")
    private String title;
    @Size(max = 2147483647)
    @Column(name = "notificationContent")
    private String notificationContent;
    @Column(name = "isPinBoardNotification")
    private Boolean isPinBoardNotification;
    @Column(name = "isToastNotification")
    private Boolean isToastNotification;
    @Column(name = "visibleFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visibleFrom;
    @Column(name = "visibleTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visibleTo;
    @Column(name = "createdDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDateTime;
    @Size(max = 2147483647)
    @Column(name = "creator")
    private String creator;
    @Size(max = 2147483647)
    @Column(name = "linkURL")
    private String linkURL;
    @Size(max = 2147483647)
    @Column(name = "showArea")
    private String showArea;
    @Size(max = 50)
    @Column(name = "severityStyle")
    private String severityStyle;

    public OWANotification() {
    }

    public OWANotification(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Boolean getIsPinBoardNotification() {
        return isPinBoardNotification;
    }

    public void setIsPinBoardNotification(Boolean isPinBoardNotification) {
        this.isPinBoardNotification = isPinBoardNotification;
    }

    public Boolean getIsToastNotification() {
        return isToastNotification;
    }

    public void setIsToastNotification(Boolean isToastNotification) {
        this.isToastNotification = isToastNotification;
    }

    public Date getVisibleFrom() {
        return visibleFrom;
    }

    public void setVisibleFrom(Date visibleFrom) {
        this.visibleFrom = visibleFrom;
    }

    public Date getVisibleTo() {
        return visibleTo;
    }

    public void setVisibleTo(Date visibleTo) {
        this.visibleTo = visibleTo;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLinkURL() {
        System.out.println("sem v getLinkURL(), returning:"+linkURL);
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getShowArea() {
        return showArea;
    }

    public void setShowArea(String showArea) {
        this.showArea = showArea;
    }

    public String getSeverityStyle() {
        return severityStyle;
    }

    public void setSeverityStyle(String severityStyle) {
        this.severityStyle = severityStyle;
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
        if (!(object instanceof OWANotification)) {
            return false;
        }
        OWANotification other = (OWANotification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.odelowebapp.common.entity.OWANotification[ id=" + id + " ]";
    }
    
}
