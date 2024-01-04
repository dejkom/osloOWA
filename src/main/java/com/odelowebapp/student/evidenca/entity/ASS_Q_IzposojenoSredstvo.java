/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.student.evidenca.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author dematjasic
 */
@Entity
@SqlResultSetMapping(
        name = "IzposojenoResultConstructor",
        classes = @ConstructorResult(
                targetClass = ASS_Q_IzposojenoSredstvo.class,
                columns = {
                    @ColumnResult(name = "IDAsset", type = Integer.class),
                    @ColumnResult(name = "LogExternalID", type = String.class),
                    @ColumnResult(name = "AssetReturn", type = Boolean.class),
                    @ColumnResult(name = "AssetExpireDays", type = Integer.class),
                    @ColumnResult(name = "LogTimestamp", type = Date.class),
                    @ColumnResult(name = "IDLogStatus", type = Integer.class),
                    @ColumnResult(name = "suma", type = Long.class)}))
@SqlResultSetMapping(
        name = "IzposojenoResultNoTSConstructor",
        classes = @ConstructorResult(
                targetClass = ASS_Q_IzposojenoSredstvo.class,
                columns = {
                    @ColumnResult(name = "IDAsset", type = Integer.class),
                    @ColumnResult(name = "LogExternalID", type = String.class),
                    @ColumnResult(name = "AssetReturn", type = Boolean.class),
                    @ColumnResult(name = "AssetExpireDays", type = Integer.class),
                    @ColumnResult(name = "suma", type = Long.class)}))
public class ASS_Q_IzposojenoSredstvo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "IDAsset")
    @Id
    private int assetID;
    @Column(name = "LogExternalID")
    private String person;
    @Column(name = "AssetReturn")
    private boolean assetReturn;
    @Column(name = "AssetExpireDays")
    private int assetExpireDays;
    @Column(name = "LogTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logTimestamp;
    
    @Column(name = "IDLogStatus")
    private int iDLogStatus;
    
    @Column(name = "suma")
    private long suma;

    @Transient
    private Date dateWhenMustBeReturned;
    @Transient
    private ASSAsset sredstvo;
    @Transient
    private ASSStatus status;

    public ASS_Q_IzposojenoSredstvo() {
    }

    public ASS_Q_IzposojenoSredstvo(int assetID, String person, boolean assetReturn, int assetExpireDays, Date logTimestamp, int IDLogStatus, long suma) {
        this.assetID = assetID;
        this.person = person;
        this.assetReturn = assetReturn;
        this.assetExpireDays = assetExpireDays;
        this.logTimestamp = logTimestamp;
        this.suma = suma;
        this.iDLogStatus = IDLogStatus;

        LocalDateTime localDateTime = logTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(Long.valueOf(assetExpireDays));
        this.dateWhenMustBeReturned = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public ASS_Q_IzposojenoSredstvo(int assetID, String person, boolean assetReturn, int assetExpireDays, long suma) {
        this.assetID = assetID;
        this.person = person;
        this.assetReturn = assetReturn;
        this.assetExpireDays = assetExpireDays;
        this.suma = suma;
        
        //LocalDateTime localDateTime = logTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(Long.valueOf(assetExpireDays));
        //this.dateWhenMustBeReturned = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    

    public String printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        //System.out.printf("%d hours, %d minutes, %d seconds%n",elapsedHours, elapsedMinutes, elapsedSeconds);
        if(Math.abs(elapsedDays) == 0){
            if(Math.abs(elapsedHours) ==0){
                return String.format("%d minutes, %d seconds%n", elapsedMinutes, elapsedSeconds);
            }else{
                return String.format("%d hours, %d minutes, %d seconds%n", elapsedHours, elapsedMinutes, elapsedSeconds);
            }
            
        }else{
            return String.format("%d days, %d hours, %d minutes, %d seconds%n",elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        }
        

    }

    public int getAssetID() {
        return assetID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public boolean isAssetReturn() {
        return assetReturn;
    }

    public void setAssetReturn(boolean assetReturn) {
        this.assetReturn = assetReturn;
    }

    public long getSuma() {
        return suma;
    }

    public void setSuma(long suma) {
        this.suma = suma;
    }

    public int getAssetExpireDays() {
        return assetExpireDays;
    }

    public void setAssetExpireDays(int assetExpireDays) {
        this.assetExpireDays = assetExpireDays;
    }

    public Date getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Date logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public Date getDateWhenMustBeReturned() {
        return dateWhenMustBeReturned;
    }

    public void setDateWhenMustBeReturned(Date dateWhenMustBeReturned) {
        this.dateWhenMustBeReturned = dateWhenMustBeReturned;
    }

    public ASSAsset getSredstvo() {
        return sredstvo;
    }

    public void setSredstvo(ASSAsset sredstvo) {
        this.sredstvo = sredstvo;
    }

    public int getiDLogStatus() {
        return iDLogStatus;
    }

    public void setiDLogStatus(int iDLogStatus) {
        this.iDLogStatus = iDLogStatus;
    }

    public ASSStatus getStatus() {
        return status;
    }

    public void setStatus(ASSStatus status) {
        this.status = status;
    }
    
    

}
