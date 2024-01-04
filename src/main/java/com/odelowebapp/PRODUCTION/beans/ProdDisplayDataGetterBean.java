/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRODUCTION.beans;

import com.odelowebapp.PRODUCTION.entity.LineProdDisplay;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.lang.System.in;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;
import org.apache.commons.io.input.BOMInputStream;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;

/**
 *
 * @author dematjasic
 */
@Named("prodDisplayDataGetterBean")
@Singleton
public class ProdDisplayDataGetterBean {

    private Date timeOfInitialization;
    private Date timeOfDataReload;

    private List<List<String>> records = new ArrayList<List<String>>();
    private List<LineProdDisplay> employees = new ArrayList();
    private List<String> distinctLocationsHalls;

    private Map<String, List<LineProdDisplay>> map = new HashMap();

    private Map<String, String> mapZaLayoutColors = new HashMap();

    Reader reader;
    Path path;

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() ProdDisplayDataGetterBean. This should be SINGLETON");
        timeOfInitialization = new Date();
        readCSV2();
    }

    //Ne uporabljaj te metode, napačno prebere CSV zaradi encodinga. Uporabi metodo readCSV2()
    public void readCSV() {
        System.out.println("Sem v readCSV");

        try {

            path = Paths.get("Q:\\linesShift.csv");
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            employees = new CsvToBeanBuilder(reader)
                    .withType(LineProdDisplay.class)
                    .withSeparator('|')
                    .build()
                    .parse();

            reader.close();

            System.out.println("Number of rows read from CSV:" + employees.size());

        } catch (Exception ex) {
            System.out.println("Napaka readCSV():" + ex);
        }

        timeOfDataReload = new Date();
    }

    public void readCSV2() {
        FileInputStream fis = null;
        try {
            System.out.println("Sem v readCSV2");
            fis = new FileInputStream("Q:\\linesShift.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(new BOMInputStream(fis), StandardCharsets.UTF_8);

            employees = new CsvToBeanBuilder(inputStreamReader)
                    .withType(LineProdDisplay.class)
                    .withSeparator('|')
                    .build()
                    .parse();

            inputStreamReader.close();

            distinctLocationsHalls = ListIterate.distinct(employees, HashingStrategies.fromFunction(LineProdDisplay::getHallLocation)).collect(LineProdDisplay::getHallLocation);
            for (String s : distinctLocationsHalls) {
                System.out.println("LocationHall from list:" + s);
            }

            map = employees.stream().collect(Collectors.groupingBy(LineProdDisplay::getHallLocation));

            for (Map.Entry<String, List<LineProdDisplay>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " number of entries: " + entry.getValue().size());
            }

            for (LineProdDisplay vrstica : employees) {          
                try {
                    double prod = Double.parseDouble("0" +vrstica.productivity.replace(',','.'));
                    int numWorkers = Integer.parseInt("0" +vrstica.numberOfWorkers);
                    if(vrstica.getNumberOfWorkers().equals("")){
                         //sivOkvircek;
                           mapZaLayoutColors.put(vrstica.workcenter, "#DADADA");
                    }
                    else if (prod < 80) {
                        if(numWorkers < 1){
                            //sivOkvircek;
                           mapZaLayoutColors.put(vrstica.workcenter, "#DADADA");
                        }else{
                           //rdecOkvircek;
                           mapZaLayoutColors.put(vrstica.workcenter, "#f75454"); 
                        }
                        
                    } else if (prod > 90) {
                        //zelenOkvircek;
                        mapZaLayoutColors.put(vrstica.workcenter, "#6edb8b");
                    } else {
                        //rumenOkvircek;
                        mapZaLayoutColors.put(vrstica.workcenter, "#ebed4e");
                    }
                } catch (Exception e) {
                    // rdecOkvircek;
                    System.out.println("napaka:"+e);
                    mapZaLayoutColors.put(vrstica.workcenter, "#f75454");
                }
                //mapZaLayoutColors.put(vrstica.workcenter, vrstica.productivity);
            }

        } catch (Exception ex) {
            System.out.println("Napaka readCSV2():" + ex);
        }
    }

    public void closeCSV() {
        try {
            System.out.println("sem v closeCSV()");
            reader.close();
        } catch (Exception ex) {
            System.out.println("NAPAKA closeCSV():" + ex);
        }
    }

    public Date getTimeOfInitialization() {
        return timeOfInitialization;
    }

    public void setTimeOfInitialization(Date timeOfInitialization) {
        this.timeOfInitialization = timeOfInitialization;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public void setRecords(List<List<String>> records) {
        this.records = records;
    }

    public List<LineProdDisplay> getEmployees() {
        return employees;
    }

    public void setEmployees(List<LineProdDisplay> employees) {
        this.employees = employees;
    }

    public Date getTimeOfDataReload() {
        return timeOfDataReload;
    }

    public void setTimeOfDataReload(Date timeOfDataReload) {
        this.timeOfDataReload = timeOfDataReload;
    }

    public Map<String, List<LineProdDisplay>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<LineProdDisplay>> map) {
        this.map = map;
    }

    public Map<String, String> getMapZaLayoutColors() {
        return mapZaLayoutColors;
    }

    public void setMapZaLayoutColors(Map<String, String> mapZaLayoutColors) {
        this.mapZaLayoutColors = mapZaLayoutColors;
    }

}
