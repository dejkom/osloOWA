/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRODUCTION.beans;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dematjasic
 */
@Named("prodDisplayOnLayoutCresnjevciBean")
@ViewScoped
public class ProdDisplayOnLayoutCresnjevciBean implements Serializable {

    @EJB
    ProdDisplayDataGetterBean dataBean;

//    jsonStaticEmpty  = "{\n"
//            + "   \"attrs\":{\n"
//            + "      \"width\":1920,\n"
//            + "      \"height\":743\n"
//            + "   },\n"
//            + "   \"className\":\"Stage\",\n"
//            + "   \"children\":[\n"
//            + "      {\n"
//            + "         \"attrs\":{\n"
//            + "            \n"
//            + "         },\n"
//            + "         \"className\":\"Layer\",\n"
//            + "         \"children\":[\n"
//            + "            {\n"
//            + "               \"attrs\":{\n"
//            + "                  \"rotateEnabled\":false\n"
//            + "               },\n"
//            + "               \"className\":\"Transformer\"\n"
//            + "            },\n"
//            + "            {\n"
//            + "               \"attrs\":{\n"
//            + "                  \"fill\":\"rgba(0,0,255,0.5)\",\n"
//            + "                  \"visible\":false,\n"
//            + "                  \"x\":1008,\n"
//            + "                  \"y\":295.203125\n"
//            + "               },\n"
//            + "               \"className\":\"Rect\"\n"
//            + "            }\n"
//            + "         ]\n"
//            + "      }\n"
//            + "   ]\n"
//            + "}";
    //private String jsonStatic = "{\"attrs\":{\"width\":1920,\"height\":743},\"className\":\"Stage\",\"children\":[{\"attrs\":{},\"className\":\"Layer\",\"children\":[{\"attrs\":{\"rotateEnabled\":false},\"className\":\"Transformer\"},{\"attrs\":{\"fill\":\"rgba(0,0,255,0.5)\",\"visible\":false,\"x\":1008,\"y\":295.203125},\"className\":\"Rect\"},{\"attrs\":{\"x\":1240.4851870465052,\"y\":110.48518704650567,\"width\":150,\"height\":100,\"draggable\":true,\"opacity\":0.7,\"name\":\"group\",\"id\":\"30010001\",\"scaleX\":0.48518704650546135,\"scaleY\":0.48518704650546135},\"className\":\"Group\",\"children\":[{\"attrs\":{\"width\":150,\"height\":100,\"fill\":\"#DADADA\",\"stroke\":\"black\",\"name\":\"rect\",\"id\":\"30010001\"},\"className\":\"Rect\"},{\"attrs\":{\"text\":\"30010001\",\"fontSize\":24,\"fontFamily\":\"Calibri\",\"fill\":\"#000\",\"width\":150,\"height\":100,\"padding\":5,\"align\":\"center\",\"verticalAlign\":\"middle\",\"listening\":false},\"className\":\"Text\"}]},{\"attrs\":{\"x\":1240.4755261343528,\"y\":164.47552613435298,\"width\":150,\"height\":100,\"draggable\":true,\"opacity\":0.7,\"name\":\"group\",\"id\":\"30020001\",\"scaleX\":0.47552613435295865,\"scaleY\":0.4755261343529592},\"className\":\"Group\",\"children\":[{\"attrs\":{\"width\":150,\"height\":100,\"fill\":\"#DADADA\",\"stroke\":\"black\",\"name\":\"rect\",\"id\":\"30020001\"},\"className\":\"Rect\"},{\"attrs\":{\"text\":\"30020001\",\"fontSize\":24,\"fontFamily\":\"Calibri\",\"fill\":\"#000\",\"width\":150,\"height\":100,\"padding\":5,\"align\":\"center\",\"verticalAlign\":\"middle\",\"listening\":false},\"className\":\"Text\"}]},{\"attrs\":{\"x\":1129.4131398239103,\"y\":368.43527895506725,\"width\":150,\"height\":100,\"draggable\":true,\"opacity\":0.7,\"name\":\"group\",\"id\":\"11020003\",\"scaleX\":0.5204427826230947,\"scaleY\":0.5204427826230946},\"className\":\"Group\",\"children\":[{\"attrs\":{\"width\":150,\"height\":100,\"fill\":\"#DADADA\",\"stroke\":\"black\",\"name\":\"rect\",\"id\":\"11020003\"},\"className\":\"Rect\"},{\"attrs\":{\"text\":\"11020003\",\"fontSize\":24,\"fontFamily\":\"Calibri\",\"fill\":\"#000\",\"width\":150,\"height\":100,\"padding\":5,\"align\":\"center\",\"verticalAlign\":\"middle\",\"listening\":false},\"className\":\"Text\"}]}]}]}";
    private String stWorkcentra;
    private String jsonStatic = "{\n"
            + "   \"attrs\":{\n"
            + "      \"width\":1920,\n"
            + "      \"height\":743\n"
            + "   },\n"
            + "   \"className\":\"Stage\",\n"
            + "   \"children\":[\n"
            + "      {\n"
            + "         \"attrs\":{\n"
            + "            \n"
            + "         },\n"
            + "         \"className\":\"Layer\",\n"
            + "         \"children\":[\n"
            + "            {\n"
            + "               \"attrs\":{\n"
            + "                  \"rotateEnabled\":false\n"
            + "               },\n"
            + "               \"className\":\"Transformer\"\n"
            + "            },\n"
            + "            {\n"
            + "               \"attrs\":{\n"
            + "                  \"fill\":\"rgba(0,0,255,0.5)\",\n"
            + "                  \"visible\":false,\n"
            + "                  \"x\":1008,\n"
            + "                  \"y\":295.203125\n"
            + "               },\n"
            + "               \"className\":\"Rect\"\n"
            + "            }\n"
            + "         ]\n"
            + "      }\n"
            + "   ]\n"
            + "}";

    private String jsonForSaving;

    private Map<String, String> map;

    private String mapJSON;

    @PostConstruct
    public void init() {
        System.out.println("Sem v ProdDisplayOnLayoutBean init()");
        //map = dataBean.getMapZaLayoutColors();
        //System.out.println("v Mapu imam vrstic:" + map.size());
        readJSONfile();
    }

    public void savingJSONComplete() {
        System.out.println("Sem v savingJSONComplete()");
        System.out.println("Shranjujem JSON:" + jsonForSaving);
        //Write JSON file
        try (FileWriter file = new FileWriter("Q:\\OEELayoutCres.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonForSaving);
            file.flush();

        } catch (IOException e) {
            System.out.println("NAPAKA shranjevanje JSON:" + e);
        }
    }

    public void readJSONfile() {
        try {
            Path fileName = Path.of("Q:\\OEELayoutCres.json");
            String content = Files.readString(fileName);
            System.out.println("Prebrano:" + content);
            jsonStatic = content;
            map = dataBean.getMapZaLayoutColors();
        } catch (Exception ex) {
            System.out.println("NAPAKA branje JSONA:" + ex);
            //jsonStatic = "{}";
        }

    }

    public void saveJSONButton() {
        System.out.println("Sem v saveJSONButton()");
    }

    public void loadJSONButton() {
        System.out.println("Sem v loadJSONButton()");
    }

    public String getJsonStatic() {
        return jsonStatic;
    }

    public void setJsonStatic(String jsonStatic) {
        this.jsonStatic = jsonStatic;
    }

    public String getStWorkcentra() {
        return stWorkcentra;
    }

    public void setStWorkcentra(String stWorkcentra) {
        this.stWorkcentra = stWorkcentra;
    }

    public String getMapJSON() {
        return new Gson().toJson(map);
    }

    public void setMapJSON(String mapJSON) {
        this.mapJSON = mapJSON;
    }

    public String getJsonForSaving() {
        return jsonForSaving;
    }

    public void setJsonForSaving(String jsonForSaving) {
        this.jsonForSaving = jsonForSaving;
    }

}
