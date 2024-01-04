/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRODUCTION.beans;

import com.odelowebapp.PRODUCTION.entity.LineProdDisplay;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author dematjasic
 */
@Named("prodDisplaySingleWorkcenterBean")
@ViewScoped
public class ProdDisplaySingleWorkcenterBean implements Serializable {

    @EJB
    ProdDisplayDataGetterBean dataBean;

    PrimeFaces current;
    private LineProdDisplay selectedWorkcenterDisplay;
    private String workcenterIn;

    private MeterGaugeChartModel meterGaugeRealizacija;
    private MeterGaugeChartModel meterGaugeZastoji;
    private MeterGaugeChartModel meterGaugeIzmet;

    private BarChartModel barModel;

    private String zelenOkvircek = "#6edb8b";
    private String rumenOkvircek = "#ebed4e";
    private String rdecOkvircek = "#f75454";
    private String sivOkvircek = "#a6a6a6";

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() ProdDisplaySingleWorkcenterBean");
        current = PrimeFaces.current();
        try {
            workcenterIn = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("wc");
        } catch (Exception e) {
            workcenterIn = null;
        }
        System.out.println("Selected workcenter:" + workcenterIn);
        if (workcenterIn != null) {
            selectedWorkcenterDisplay = dataBean.getEmployees().stream().filter(o -> o.getWorkcenter().equals(workcenterIn)).collect(Collectors.toList()).get(0);
            createMeterGaugeModels();
            createBarChartModel();
        }
        //createMeterGaugeModels();
        //createBarChartModel();
    }

    public Date getDateNow() {
        return new Date();
    }

    public void setSelectedWC(LineProdDisplay in) {
        System.out.println("Sem v setSelectedWC, selected: " + in.workcenter);
        selectedWorkcenterDisplay = in;
    }

    public void onRowSelect() {
        System.out.println("Sem v onRowSelect");
        System.out.println("Selected workcenter:" + selectedWorkcenterDisplay.workcenter);
        createMeterGaugeModels();
        createBarChartModel();
        current.executeScript("PF('dlg3').show()");
    }

    public void refreshPageFromWebsocket() {
        System.out.println("Sem v refreshPageFromWebsocket(), sedaj moram osvežiti grafe");
        meterGaugeRealizacija.setValue(selectedWorkcenterDisplay.getProductivity_double());
        meterGaugeZastoji.setValue(selectedWorkcenterDisplay.getDowntimeDurationUnplanned_int());
        meterGaugeIzmet.setValue(selectedWorkcenterDisplay.getScrapRate_double() / 100);
        //current.ajax().update("form:wpcontainer");
        selectedWorkcenterDisplay = dataBean.getEmployees().stream().filter(o -> o.getWorkcenter().equals(workcenterIn)).collect(Collectors.toList()).get(0);
    }

    int result;
    int result2;
    int result3;

    public void testirajGaugeRandomData() {
        System.out.println("Sem v testirajGaugeRandomData");
//        Random r = new Random();
//        int low = 10;
//        int high = 100;
//        
//        result = r.nextInt(high-low) + low;
//        result2 = r.nextInt(480-low) + low;
//        result3 = r.nextInt(high-low) + low;
//        
//        meterGaugeRealizacija.setValue(result);
//        
//        meterGaugeZastoji.setValue(result2);
//        
//        meterGaugeIzmet.setValue(result3);
//        
//        selectedWorkcenterDisplay.setProductivity(result+"");
//        selectedWorkcenterDisplay.setQuantity_left(selectedWorkcenterDisplay.getQuantity_left_int()+1+"");
    }
    
    private void createBarChartModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("My First Dataset");
        
        Date now = new Date();
        Date date2 = new Date(2023, 3, 15); // April 15, 2023

        List<Number> values = new ArrayList<>();
        if(!selectedWorkcenterDisplay.getP1().equals("NN")) {
            values.add(selectedWorkcenterDisplay.getP1Int());
            values.add(selectedWorkcenterDisplay.getP2Int());
            values.add(selectedWorkcenterDisplay.getP3Int());
            values.add(selectedWorkcenterDisplay.getP4Int());
            values.add(selectedWorkcenterDisplay.getP5Int());
            values.add(selectedWorkcenterDisplay.getP6Int());
            values.add(selectedWorkcenterDisplay.getP7Int());
            values.add(selectedWorkcenterDisplay.getP8Int());
            values.add(selectedWorkcenterDisplay.getP9Int());
            values.add(selectedWorkcenterDisplay.getP10Int());
        } else {
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
        }

        barDataSet.setData(values);
        barDataSet.setLabel("");

        List<String> bgColor = new ArrayList<>();
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP1Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP2Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP3Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP4Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP5Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP6Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP7Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP8Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP9Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP10Int()));

        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP1Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP2Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP3Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP4Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP5Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP6Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP7Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP8Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP9Int()));
        bgColor.add(getBarvaRGB_Realizacija(selectedWorkcenterDisplay.getP10Int()));
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);
        
        
        List<String> labels = new ArrayList<>();
        
        HashMap<String, String> timeMap = generateTimeMap();
        for(String s : timeMap.values()){
            System.out.println("VALLUES TO MAP:"+s);
            labels.add(s);
        }

        
//        labels.add("1");
//        labels.add("2");
//        labels.add("3");
//        labels.add("4");
//        labels.add("5");
//        labels.add("6");
//        labels.add("7");
//        labels.add("8");
//        labels.add("9");
//        labels.add("10");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        //linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart");
//        options.setTitle(title);
        Legend legend = new Legend();
        legend.setDisplay(false);
//        legend.setPosition("top");
//        LegendLabel legendLabels = new LegendLabel();
//        legendLabels.setFontStyle("italic");
//        legendLabels.setFontColor("#2980B9");
//        legendLabels.setFontSize(24);
//        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(10);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }

    public BarChartModel createBarChartModelForParameter(LineProdDisplay linein) {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        LineProdDisplay passedWorkcenterDisplay = linein;
        
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("My First Dataset");
        
        Date now = new Date();
        Date date2 = new Date(2023, 3, 15); // April 15, 2023

        List<Number> values = new ArrayList<>();
        if(!passedWorkcenterDisplay.getP1().equals("NN")) {
            values.add(passedWorkcenterDisplay.getP1Int());
            values.add(passedWorkcenterDisplay.getP2Int());
            values.add(passedWorkcenterDisplay.getP3Int());
            values.add(passedWorkcenterDisplay.getP4Int());
            values.add(passedWorkcenterDisplay.getP5Int());
            values.add(passedWorkcenterDisplay.getP6Int());
            values.add(passedWorkcenterDisplay.getP7Int());
            values.add(passedWorkcenterDisplay.getP8Int());
            values.add(passedWorkcenterDisplay.getP9Int());
            values.add(passedWorkcenterDisplay.getP10Int());
        } else {
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
        }

        barDataSet.setData(values);
        barDataSet.setLabel("");

        List<String> bgColor = new ArrayList<>();
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP1Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP2Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP3Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP4Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP5Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP6Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP7Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP8Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP9Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP10Int()));

        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP1Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP2Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP3Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP4Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP5Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP6Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP7Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP8Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP9Int()));
        bgColor.add(getBarvaRGB_Realizacija(passedWorkcenterDisplay.getP10Int()));
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);
        
        
        List<String> labels = new ArrayList<>();
        
        HashMap<String, String> timeMap = generateTimeMap();
        for(String s : timeMap.values()){
            System.out.println("VALLUES TO MAP:"+s);
            labels.add(s);
        }

        
//        labels.add("1");
//        labels.add("2");
//        labels.add("3");
//        labels.add("4");
//        labels.add("5");
//        labels.add("6");
//        labels.add("7");
//        labels.add("8");
//        labels.add("9");
//        labels.add("10");
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        //linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart");
//        options.setTitle(title);
        Legend legend = new Legend();
        legend.setDisplay(false);
//        legend.setPosition("top");
//        LegendLabel legendLabels = new LegendLabel();
//        legendLabels.setFontStyle("italic");
//        legendLabels.setFontColor("#2980B9");
//        legendLabels.setFontSize(24);
//        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(10);
        options.setAnimation(animation);

        barModel.setOptions(options);
        return barModel;
    }
    
    public static HashMap<String, String> generateTimeMap() {
        // Current hour of the day
        int currentHour = LocalDateTime.now().getHour();

        // HashMap to store the values
        HashMap<String, String> map = new HashMap<>();

        // Populate the HashMap
        for (int key = 10; key >= 1; key--) {
            int value = currentHour - (10 - key);
            // Adjust for negative values
            if (value < 0) {
                value += 24;
            }
            map.put(String.valueOf(key), String.valueOf(value)+":00-"+String.valueOf(value+1)+":00");
        }

        return map;
    }

    private void createMeterGaugeModels() {

        List<Number> ticks1 = new ArrayList<Number>() {
            {
                add(70);
                add(80);
                add(90);
                add(100);
            }
        };

        List<Number> ticks2 = new ArrayList<Number>() {
            {
                add(60);
                add(40);
                add(20);
                add(0);
            }
        };

        List<Number> ticks3 = new ArrayList<Number>() {
            {
                add(3);
                add(2);
                add(1);
                add(0);
            }
        };

        meterGaugeRealizacija = initMeterGaugeModelRelizacija();
        meterGaugeRealizacija.setShadow(true);
        meterGaugeRealizacija.setSeriesColors("B81D13,EFB700,008450");
        meterGaugeRealizacija.setShowTickLabels(true);
        meterGaugeRealizacija.setMax(100.0);
        meterGaugeRealizacija.setMin(70.0);
        meterGaugeRealizacija.setIntervalOuterRadius(110); //velikost barvne skale v gaugu
        meterGaugeRealizacija.setTicks(ticks1);

        meterGaugeZastoji = initMeterGaugeModelZastoji();
        meterGaugeZastoji.setShadow(true);
        meterGaugeZastoji.setSeriesColors("B81D13,EFB700,008450");
        meterGaugeZastoji.setShowTickLabels(true);
        meterGaugeZastoji.setMax(0);
        meterGaugeZastoji.setMin(-60);
        meterGaugeZastoji.setIntervalOuterRadius(110); //velikost barvne skale v gaugu
        meterGaugeZastoji.setTicks(ticks2);
        meterGaugeZastoji.setLegendRows(2);

        meterGaugeIzmet = initMeterGaugeModelIzmet();
        meterGaugeIzmet.setShadow(true);
        meterGaugeIzmet.setSeriesColors("B81D13,EFB700,008450");
        meterGaugeIzmet.setShowTickLabels(true);
        meterGaugeIzmet.setMax(0.0);
        meterGaugeIzmet.setMin(-3.0);
        meterGaugeIzmet.setIntervalOuterRadius(110); //velikost barvne skale v gaugu
        meterGaugeIzmet.setTicks(ticks3);

    }

    public String getBarva_Realizacija() {
        try {
            //System.out.println("NUMBER OF WORKERS:"+selectedWorkcenterDisplay.getNumberOfWorkers());
            if(selectedWorkcenterDisplay.getNumberOfWorkers().equals("")){
                return sivOkvircek;
            }
            else if (selectedWorkcenterDisplay.getProductivity_double() < 80) {
                return rdecOkvircek;
            } else if (selectedWorkcenterDisplay.getProductivity_double() > 90) {
                return zelenOkvircek;
            } else {
                return rumenOkvircek;
            }
        } catch (Exception e) {
            return rdecOkvircek;
        }
    }

    public String getBarvaRGB_Realizacija(int tocompare) {
        try {
            if (tocompare < 80) {
                return "rgba(247, 84, 84)";
            } else if (tocompare > 90) {
                return "rgba(110, 219, 139)";
            } else {
                return "rgba(235,237, 78)";
            }
        } catch (Exception e) {
            return "rgba(75, 192, 192)";
        }
    }

    public String getBarva_Zastoji() {
        try {
            if(selectedWorkcenterDisplay.getNumberOfWorkers().equals("")){
                return sivOkvircek;
            }
            else if (selectedWorkcenterDisplay.getDowntimeDurationUnplanned_int() > 40) {
                return rdecOkvircek;
            } else if (selectedWorkcenterDisplay.getDowntimeDurationUnplanned_int() < 20) {
                return zelenOkvircek;
            } else {
                return rumenOkvircek;
            }
        } catch (Exception e) {
            return rdecOkvircek;
        }

    }

    public String getBarva_Izmet() {
        try {
            if(selectedWorkcenterDisplay.getNumberOfWorkers().equals("")){
                return sivOkvircek;
            }
            else if (selectedWorkcenterDisplay.getScrapRate_double() > 1.0) {
                return rdecOkvircek;
            } else if (selectedWorkcenterDisplay.getScrapRate_double() < 0.9) {
                return zelenOkvircek;
            } else {
                return rumenOkvircek;
            }
        } catch (Exception e) {
            return rdecOkvircek;
        }

    }

    private MeterGaugeChartModel initMeterGaugeModelRelizacija() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(80);
                add(90);
                add(100);
            }
        };

        try {

            System.out.println("ProductiityString:" + selectedWorkcenterDisplay.productivity);
            System.out.println("ProductiityDouble:" + selectedWorkcenterDisplay.getProductivity_double());
        } catch (Exception e) {
            System.out.println("Sem v TRYCATCH initMeterGaugeModelRelizacija");
            return new MeterGaugeChartModel(0.0, intervals);
        }
        return new MeterGaugeChartModel(selectedWorkcenterDisplay.getProductivity_double(), intervals);
    }

    private MeterGaugeChartModel initMeterGaugeModelZastoji() {
        List<Number> intervals = new ArrayList<Number>() {
            {

                add(-40);
                add(-20);
                add(0);
            }
        };

        try {
            System.out.println("DowntimeRate:" + selectedWorkcenterDisplay.downtimeRate);
        } catch (Exception e) {
            System.out.println("Sem v TRYCATCH initMeterGaugeModelZastoji");
            return new MeterGaugeChartModel(0.0, intervals);
        }
        return new MeterGaugeChartModel(selectedWorkcenterDisplay.getDowntimeDurationUnplanned_int() * -1, intervals);
    }

    private MeterGaugeChartModel initMeterGaugeModelIzmet() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(-2);
                add(-1);
                add(0);
            }
        };

        try {
            System.out.println("ScrapRateDouble:" + selectedWorkcenterDisplay.getScrapRate_double());
        } catch (Exception e) {
            System.out.println("Sem v TRYCATCH initMeterGaugeModelIzmet");
            return new MeterGaugeChartModel(0.0, intervals);
        }
        return new MeterGaugeChartModel(selectedWorkcenterDisplay.getScrapRate_double() * -1, intervals);
    }

    public LineProdDisplay getSelectedWorkcenterDisplay() {
        return selectedWorkcenterDisplay;
    }

    public void setSelectedWorkcenterDisplay(LineProdDisplay selectedWorkcenterDisplay) {
        this.selectedWorkcenterDisplay = selectedWorkcenterDisplay;
    }

    public MeterGaugeChartModel getMeterGaugeModel() {
        return meterGaugeRealizacija;
    }

    public void setMeterGaugeModel(MeterGaugeChartModel meterGaugeRealizacija) {
        this.meterGaugeRealizacija = meterGaugeRealizacija;
    }

    public MeterGaugeChartModel getMeterGaugeRealizacija() {
        return meterGaugeRealizacija;
    }

    public void setMeterGaugeRealizacija(MeterGaugeChartModel meterGaugeRealizacija) {
        this.meterGaugeRealizacija = meterGaugeRealizacija;
    }

    public MeterGaugeChartModel getMeterGaugeZastoji() {
        return meterGaugeZastoji;
    }

    public void setMeterGaugeZastoji(MeterGaugeChartModel meterGaugeZastoji) {
        this.meterGaugeZastoji = meterGaugeZastoji;
    }

    public MeterGaugeChartModel getMeterGaugeIzmet() {
        return meterGaugeIzmet;
    }

    public void setMeterGaugeIzmet(MeterGaugeChartModel meterGaugeIzmet) {
        this.meterGaugeIzmet = meterGaugeIzmet;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult2() {
        return result2;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
    }

    public int getResult3() {
        return result3;
    }

    public void setResult3(int result3) {
        this.result3 = result3;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

}
