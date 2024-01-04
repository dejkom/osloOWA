/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PUR.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odelowebapp.PUR.entity.RequestEBV;
import com.odelowebapp.PUR.entity.RequestLine;
import com.odelowebapp.PUR.entity.ResponseEBV;
import com.odelowebapp.PUR.entity.ResponseEBVLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author dematjasic
 */
@Named("ebvPriceCheckBean")
@ViewScoped
public class EbvPriceCheckBean implements Serializable {

    private String partnumber_in;
    private List<String> partnumbers_in;
    private String responseMessage;
    
    private ResponseEBV responseEBV;

    @PostConstruct
    public void init() {
        System.out.println("Sem v EbvPriceCheckBean init()");
    }

    public void callAPIButtonAction() {
        try {
            System.out.println("Sem v callAPIButtonAction()");
            
            RequestEBV rh = new RequestEBV();
            List<RequestLine> requestlines = new ArrayList();
            
            for (String s : partnumbers_in) {
                RequestLine rl = new RequestLine();
                rl.setPartNumber(s);
                requestlines.add(rl);
            }
            
            RequestLine[] requestLineArray = requestlines.toArray(new RequestLine[requestlines.size()]);
            rh.setRequestLines(requestLineArray);
            
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            
            String jsonForApi = gson.toJson(rh);
            System.out.println("JSON for API: " + jsonForApi);
            
            //
            URL url = new URL("https://emeab2bgatewayprd.avnet.eu:8883/rest/CustomerAPI.v010006.REST.getPriceAndQty");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Set Basic Authentication
            String userCredentials = "ODELO-SLOVENIJA_EBV:PHEWPM5Xjh";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
            conn.setRequestProperty("Authorization", basicAuth);
            
            // Set POST and JSON
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Write JSON body to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(jsonForApi.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            // Read the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            
            responseMessage = beautifyJson(response.toString());
            
            //responseEBV = gson.fromJson(responseMessage, ResponseEBV.class);
            
            ObjectMapper mapper = new ObjectMapper();
            responseEBV = mapper.readValue(responseMessage, ResponseEBV.class);
            List<ResponseEBVLine> requestOutList = responseEBV.getResponseLines();

            for (ResponseEBVLine requestOut : requestOutList) {
                System.out.println("Part Number: " + requestOut.getPartNumber());
                System.out.println("Unit of Measure: " + requestOut.getUnitOfMeasure());
                System.out.println("Manufacturer name out: " + requestOut.getManufacturerNameOut());
                // Print other properties as needed
                System.out.println();
            }
            
            
            conn.disconnect();
            //
        } catch (MalformedURLException ex) {
            Logger.getLogger(EbvPriceCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EbvPriceCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static String beautifyJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonNode = objectMapper.readValue(json, Object.class);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(jsonNode);
        } catch (Exception e) {
            // Handle any exceptions appropriately
            e.printStackTrace();
        }
        return json; // Return the original JSON string if an error occurs
    }

    public String getPartnumber_in() {
        return partnumber_in;
    }

    public void setPartnumber_in(String partnumber_in) {
        this.partnumber_in = partnumber_in;
    }

    public List<String> getPartnumbers_in() {
        return partnumbers_in;
    }

    public void setPartnumbers_in(List<String> partnumbers_in) {
        this.partnumbers_in = partnumbers_in;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseEBV getResponseEBV() {
        return responseEBV;
    }

    public void setResponseEBV(ResponseEBV responseEBV) {
        this.responseEBV = responseEBV;
    }

    
}
