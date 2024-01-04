/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.IT.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class OdeloAIBean implements Serializable {

    private String voiceParameter = "Microsoft Lado";
    private String contentParameter = "To je besedilo ki ga bo prebral govorec Lado.";
    private String pathSaveParameter = "C:\\WebAppFiles\\Sources\\sounds\\porocilo";
    
    private String createdFilePath = "";
    // http://localhost:7070/osloWebApp/sounds/porocilo.wav
    // C:\\WebAppFiles\\Sources\\sounds\\porocilo

    @PostConstruct
    public void init() {
        System.out.println("Sem v OdeloAIBean init()");
    }

    public void buttonAction() {
        System.out.println("Sem v buttonAction(), voiceParameter:" + voiceParameter + " , contentParameter:" + contentParameter);
        try {

            LocalDateTime currentDate = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String formattedDate = currentDate.format(formatter);

            pathSaveParameter = pathSaveParameter + formattedDate + ".wav";
            
            createdFilePath = "http://localhost:7070/osloWebApp/sounds/porocilo"+formattedDate+".wav";

            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Temp\\tts2.exe", voiceParameter, pathSaveParameter, contentParameter);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode;
            try {
                exitCode = process.waitFor();
                System.out.println("Exited with code: " + exitCode);
                pathSaveParameter = "C:\\WebAppFiles\\Sources\\sounds\\porocilo";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Napaka v buttonAction():" + e.getMessage());
        }
    }

    public String getCreatedFilePath() {
        return createdFilePath;
    }

    public void setCreatedFilePath(String createdFilePath) {
        this.createdFilePath = createdFilePath;
    }
    
    

    public String getVoiceParameter() {
        return voiceParameter;
    }

    public void setVoiceParameter(String voiceParameter) {
        this.voiceParameter = voiceParameter;
    }

    public String getContentParameter() {
        return contentParameter;
    }

    public void setContentParameter(String contentParameter) {
        this.contentParameter = contentParameter;
    }

    public String getLanguageParameter() {
        return pathSaveParameter;
    }

    public void setLanguageParameter(String pathSaveParameter) {
        this.pathSaveParameter = pathSaveParameter;
    }

}
