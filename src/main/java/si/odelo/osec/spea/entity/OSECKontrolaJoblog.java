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
@Table(name = "OSEC_Kontrola_Joblog")
@NamedQueries({
    @NamedQuery(name = "OSECKontrolaJoblog.findAll", query = "SELECT o FROM OSECKontrolaJoblog o"),
    @NamedQuery(name = "OSECKontrolaJoblog.findById", query = "SELECT o FROM OSECKontrolaJoblog o WHERE o.id = :id"),
    @NamedQuery(name = "OSECKontrolaJoblog.findByExecutiontime", query = "SELECT o FROM OSECKontrolaJoblog o WHERE o.executiontime = :executiontime"),
    @NamedQuery(name = "OSECKontrolaJoblog.findByMessage", query = "SELECT o FROM OSECKontrolaJoblog o WHERE o.message = :message"),
    @NamedQuery(name = "OSECKontrolaJoblog.findByRowstransfered", query = "SELECT o FROM OSECKontrolaJoblog o WHERE o.rowstransfered = :rowstransfered"),
    @NamedQuery(name = "OSECKontrolaJoblog.findByMaxtimestampintransfereddata", query = "SELECT o FROM OSECKontrolaJoblog o WHERE o.maxtimestampintransfereddata = :maxtimestampintransfereddata")})
public class OSECKontrolaJoblog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "executiontime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executiontime;
    @Size(max = 2147483647)
    @Column(name = "message")
    private String message;
    @Column(name = "rowstransfered")
    private Integer rowstransfered;
    @Column(name = "maxtimestampintransfereddata")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maxtimestampintransfereddata;

    public OSECKontrolaJoblog() {
    }

    public OSECKontrolaJoblog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExecutiontime() {
        return executiontime;
    }

    public void setExecutiontime(Date executiontime) {
        this.executiontime = executiontime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRowstransfered() {
        return rowstransfered;
    }

    public void setRowstransfered(Integer rowstransfered) {
        this.rowstransfered = rowstransfered;
    }

    public Date getMaxtimestampintransfereddata() {
        return maxtimestampintransfereddata;
    }

    public void setMaxtimestampintransfereddata(Date maxtimestampintransfereddata) {
        this.maxtimestampintransfereddata = maxtimestampintransfereddata;
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
        if (!(object instanceof OSECKontrolaJoblog)) {
            return false;
        }
        OSECKontrolaJoblog other = (OSECKontrolaJoblog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.osec.spea.entity.OSECKontrolaJoblog[ id=" + id + " ]";
    }
    
}
