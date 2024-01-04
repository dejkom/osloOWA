/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRODUCTION.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author dematjasic
 */
public class LineProdDisplay {
    
    //@CsvBindByName(column="ï»¿workcenter")
    @CsvBindByName(column="workcenter")
    public String workcenter;
    @CsvBindByName(column="quantity_left")
    public String quantity_left;
    @CsvBindByName(column="quantity_right")
    public String quantity_right;
    @CsvBindByName(column="scrap_left")
    public String scrap_left;
    @CsvBindByName(column="scrap_right")
    public String scrap_right;
    @CsvBindByName(column="lineName")
    public String lineName;
    
    @CsvBindByName(column="shiftStart")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    public Date shiftStart;
    
    @CsvBindByName(column="timeNow")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    public Date timeNow;
    
    @CsvBindByName(column="numberOfPcsInCycle")
    public String numberOfPcsInCycle;
    
    @CsvBindByName(column="numberOfWorkers")
    public String numberOfWorkers;
    
    @CsvBindByName(column="plannedNumberOfWorkers")
    public String plannedNumberOfWorkers;
    
    @CsvBindByName(column="lastLogout")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    public Date lastLogout;
    
    
    @CsvBindByName(column="downtimeDurationUnplanned")
    public String downtimeDurationUnplanned;
    @CsvBindByName(column="plannedQuantity")
    public String plannedQuantity;
    @CsvBindByName(column="shiftEnd")
    public String shiftEnd;
    @CsvBindByName(column="totalTime")
    public String totalTime;
    
    @CsvBindByName(column="material_left")
    public String materialLeft;
    @CsvBindByName(column="material_right")
    public String materialRight;
    
    @CsvBindByName(column="downtimeDurationPlanned")
    public String downtimeDurationPlanned;
    
    @CsvBindByName(column="downtimeRate")
    public String downtimeRate;
    
    @CsvBindByName(column="effectiveMinutes")
    public String effectiveMinutes;
    
    @CsvBindByName(column="goodGoal")
    public String goodGoal;
    @CsvBindByName(column="productivity")
    public String productivity;
    @CsvBindByName(column="cycle")
    public String cycle;
    
    @CsvBindByName(column="material_left_desc")
    public String matLeftDesc;
    @CsvBindByName(column="material_right_desc")
    public String matRightDesc;
    @CsvBindByName(column="scrapRate")
    public String scrapRate;
    
    @CsvBindByName(column="material_left_work")
    public String materialLeftOrder; 
    @CsvBindByName(column="material_right_work")
    public String materialRightOrder; 
    
    @CsvBindByName(column="location")
    public String location; 
    
    @CsvBindByName(column="p1")
    public String p1;
    @CsvBindByName(column="p2")
    public String p2;
    @CsvBindByName(column="p3")
    public String p3;
    @CsvBindByName(column="p4")
    public String p4;
    @CsvBindByName(column="p5")
    public String p5;
    @CsvBindByName(column="p6")
    public String p6;
    @CsvBindByName(column="p7")
    public String p7;
    @CsvBindByName(column="p8")
    public String p8;
    @CsvBindByName(column="p9")
    public String p9;
    @CsvBindByName(column="p10")
    public String p10;
    
    @CsvBindByName(column="indowntime")
    public String indowntime;

    public LineProdDisplay() {
    }

    public String getStatusColor(){
        //NOT YET IMPLEMENTED
        return "";
    }
    
    public long getQuality(){
        long result;
        try{
            result = ((getQuantity_left_int() + getQuantity_right_int()) / (getQuantity_left_int() + getQuantity_right_int() + getScrap_left_int() + getScrap_right_int())) * 100;
        }catch(Exception e){
            result = 0;
        }
       return result;
    }
    
    public int getPerformance(){
        return 75;
    }
    
    public long getAvailability(){
        return Long.parseLong("60");
    }
    
    public void setAvailability(){
        
    }
    
    public void setPerformance(){
        
    }
    
    public void setQuality(){
        
    }
    
    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }

    public String getQuantity_left() {
        return quantity_left;
    }

    public void setQuantity_left(String quantity_left) {
        this.quantity_left = quantity_left;
    }

    public String getQuantity_right() {
        return quantity_right;
    }

    public void setQuantity_right(String quantity_right) {
        this.quantity_right = quantity_right;
    }

    public String getScrap_left() {
        return scrap_left;
    }

    public void setScrap_left(String scrap_left) {
        this.scrap_left = scrap_left;
    }

    public String getScrap_right() {
        return scrap_right;
    }

    public void setScrap_right(String scrap_right) {
        this.scrap_right = scrap_right;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Date getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Date shiftStart) {
        this.shiftStart = shiftStart;
    }

    public Date getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(Date timeNow) {
        this.timeNow = timeNow;
    }

    public String getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(String numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public String getPlannedNumberOfWorkers() {
        return plannedNumberOfWorkers;
    }

    public void setPlannedNumberOfWorkers(String plannedNumberOfWorkers) {
        this.plannedNumberOfWorkers = plannedNumberOfWorkers;
    }
    
    

    public Date getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public String getTotalTime() {
        return totalTime;
    }
    
    public int getTotalTime_int() {
        try{
            return Integer.parseInt(totalTime);
        }catch(Exception e){
            return 0;
        }
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getDowntimeDurationUnplanned() {
        return downtimeDurationUnplanned;
    }
    
    public int getDowntimeDurationUnplanned_int() {
        try{
            return Integer.parseInt(downtimeDurationUnplanned);
        }catch(Exception e){
            return 0;
        }
    }
    
    public void setDowntimeDuration(String downtimeDurationUnplanned) {
        this.downtimeDurationUnplanned = downtimeDurationUnplanned;
    }

    public String getNumberOfPcsInCycle() {
        return numberOfPcsInCycle;
    }

    public void setNumberOfPcsInCycle(String numberOfPcsInCycle) {
        this.numberOfPcsInCycle = numberOfPcsInCycle;
    }

    public String getDowntimeRate() {
        return downtimeRate;
    }
    
    public String getDowntimeRate_StringPercentage() {
        return String.format("%.2f", getDowntimeRate_double());
    }
    
    public double getDowntimeRate_double() {
        try{
            return Double.parseDouble(downtimeRate.replace(',','.'));
        }catch(Exception e){
            return 0.0;
        }
    }

    public void setDowntimeRate(String downtimeRate) {
        this.downtimeRate = downtimeRate;
    }

    public String getScrapRate() {
        return scrapRate;
    }

    public void setScrapRate(String scrapRate) {
        this.scrapRate = scrapRate;
    }
    
    public double getScrapRate_double() {
        try{
            return Double.parseDouble(scrapRate.replace(',','.'));
        }catch(Exception e){
            return 0.0;
        }
    }
    
    public String getScrapRate_StringPercentage() {
        return String.format("%.2f", getScrapRate_double());
    }
    
    
    
    

    public String getPlannedQuantity() {
        return plannedQuantity;
    }

    public void setPlannedQuantity(String plannedQuantity) {
        this.plannedQuantity = plannedQuantity;
    }

    public int getQuantity_left_int() {
        try{
            return Integer.parseInt(quantity_left);
        }catch(Exception e){
            return 0;
        }
        
    }


    public int getQuantity_right_int() {
        try{
            return Integer.parseInt(quantity_right);
        }catch(Exception e){
            return 0;
        }
    }

    public int getScrap_left_int() {
        try{
            return Integer.parseInt(scrap_left);
        }catch(Exception e){
            return 0;
        }
    }

    public int getScrap_right_int() {
        try{
            return Integer.parseInt(scrap_right);
        }catch(Exception e){
            return 0;
        }
    }

    public int getNumberOfWorkers_int(){
        try{
            return Integer.parseInt(numberOfWorkers);
        }catch(Exception e){
            return 0;
        }
    }

    public String getMaterialLeft() {
        return materialLeft;
    }

    public void setMaterialLeft(String materialLeft) {
        this.materialLeft = materialLeft;
    }

    public String getMaterialRight() {
        return materialRight;
    }

    public void setMaterialRight(String materialRight) {
        this.materialRight = materialRight;
    }

    public String getDowntimeDurationPlanned() {
        return downtimeDurationPlanned;
    }

    public void setDowntimeDurationPlanned(String downtimeDurationPlanned) {
        this.downtimeDurationPlanned = downtimeDurationPlanned;
    }

    public String getEffectiveMinutes() {
        return effectiveMinutes;
    }

    public void setEffectiveMinutes(String effectiveMinutes) {
        this.effectiveMinutes = effectiveMinutes;
    }

    public String getGoodGoal() {
        return goodGoal;
    }

    public void setGoodGoal(String goodGoal) {
        this.goodGoal = goodGoal;
    }

    public String getProductivity() {
        return productivity;
    }
    
    public String getProductivityPercentageString() {
        //return df.format(getProductivity_double());
        return String.format("%.2f", getProductivity_double());
    }
    
    DecimalFormat df = new DecimalFormat("#.00");
    public double getProductivity_double() {
        try{
            return Double.parseDouble(productivity.replace(',','.'));
        }catch(Exception e){
            return 0.0;
        }
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getMatLeftDesc() {
        return matLeftDesc;
    }

    public void setMatLeftDesc(String matLeftDesc) {
        this.matLeftDesc = matLeftDesc;
    }

    public String getMatRightDesc() {
        return matRightDesc;
    }

    public void setMatRightDesc(String matRightDesc) {
        this.matRightDesc = matRightDesc;
    }

    public String getMaterialLeftOrder() {
        return materialLeftOrder;
    }

    public void setMaterialLeftOrder(String materialLeftOrder) {
        this.materialLeftOrder = materialLeftOrder;
    }

    public String getMaterialRightOrder() {
        return materialRightOrder;
    }

    public void setMaterialRightOrder(String materialRightOrder) {
        this.materialRightOrder = materialRightOrder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getHallLocation(){
        if(this.location.equals(" ") || this.location.length() <= 1){
            return "undefined";
        }else{
        return this.location.split("-")[0];
        }
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

    public String getP9() {
        return p9;
    }

    public void setP9(String p9) {
        this.p9 = p9;
    }

    public String getP10() {
        return p10;
    }

    public void setP10(String p10) {
        this.p10 = p10;
    }
    
    public int getP1Int(){
        try{
            return Integer.parseInt(p1);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP2Int(){
        try{
            return Integer.parseInt(p2);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP3Int(){
        try{
            return Integer.parseInt(p3);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP4Int(){
        try{
            return Integer.parseInt(p4);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP5Int(){
        try{
            return Integer.parseInt(p5);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP6Int(){
        try{
            return Integer.parseInt(p6);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP7Int(){
        try{
            return Integer.parseInt(p7);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP8Int(){
        try{
            return Integer.parseInt(p8);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP9Int(){
        try{
            return Integer.parseInt(p9);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
    public int getP10Int(){
        try{
            return Integer.parseInt(p10);
        }catch(NumberFormatException e){
            return 0;
        }
    }

    public String getIndowntime() {
        System.out.println("Sem v getIndowntime vracam:"+indowntime);
        return indowntime;
    }

    public void setIndowntime(String indowntime) {
        this.indowntime = indowntime;
    }
    
    
    
}
