/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.odelo.osec.spea.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dematjasic
 */
@Embeddable
public class TempOSECtablePK implements Serializable {
    
    @Column(name = "ID")
    private String id;
    @Column(name = "TestDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testDatetime;

    public TempOSECtablePK() {
    }

    public TempOSECtablePK(String id, Date testDatetime) {
        this.id = id;
        this.testDatetime = testDatetime;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(Date testDatetime) {
        this.testDatetime = testDatetime;
    }
    
    public String getFormattedTestDatetime(){
        String pattern = "dd.MM.yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(testDatetime);
    }
    
    
    
}
