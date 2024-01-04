/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRODUCTION.beans;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.imgscalr.Scalr;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author dematjasic
 */
@Named("prodDisplayAdministratorBean")
@ViewScoped
public class ProdDisplayAdministratorBean implements Serializable {

    @Inject
    @Push(channel = "PROIDisplaysChannel")
    private PushContext PROIDisplaysChannel;

    @Inject
    @Push(channel = "PROIDisplaysChannelRefresh")
    private PushContext PROIDisplaysChannelRefresh;

    @Inject
    @Push(channel = "PROIDisplaysChannelMessage")
    private PushContext PROIDisplaysChannelMessage;

    PrimeFaces current;

    private String vehicle;
    private String licenceplate;

    private UploadedFile file;
    BufferedImage resizedImage;
    String base64image;

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() ProdDisplayAdministratorBean");
    }

    public void refreshPage() {
        System.out.println("Sem v refresPage()");
        String exampleJSON = "{\"message\": \"Refresh has been initiated from Administrator\", \"severity\": \"info\" }";
        PROIDisplaysChannelRefresh.send(exampleJSON);
    }

    public void showMessageOnAllScreens() {
        System.out.println("Sem v showMessageOnAllScreens");
        String exampleJSON = "{\"message\": \"This is message for all screens\", \"severity\": \"info\", \"action\": \"show\" }";
        PROIDisplaysChannelMessage.send(exampleJSON);
        //current.executeScript("PF('dlg2').show()");
    }

    public void showMessageOnAllScreens(String carmodel, String licenceplate) {
        System.out.println("Sem v showMessageOnAllScreens, carmodel:" + carmodel + ", licenceplate:" + licenceplate);
        String exampleJSON = "{\"message\": \"Lastnika vozila " + carmodel + " z registrsko številko " + licenceplate + " prosimo da umakne svoje vozilo.\", \"severity\": \"info\", \"action\": \"show\",\"image\":\""+base64image+"\" }";
        PROIDisplaysChannelMessage.send(exampleJSON);
        //current.executeScript("PF('dlg2').show()");
    }

    public void hideMessageOnAllScreens() {
        System.out.println("Sem v hideMessageOnAllScreens");
        String exampleJSON = "{\"message\": \"Refresh has been initiated from Administrator\", \"severity\": \"info\", \"action\": \"hide\" }";
        PROIDisplaysChannelMessage.send(exampleJSON);
        //current.executeScript("PF('dlg2').hide()");
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            String fileName = event.getFile().getFileName();
            //Get file extension.
            String extension = "png";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1).toLowerCase();
            }

            String encodedImage = java.util.Base64.getEncoder().encodeToString(event.getFile().getContent());
            this.base64image = String.format("data:image/%s;base64, %s", extension, encodedImage);
    
        } catch (Exception ex) {
            Logger.getLogger(ProdDisplayAdministratorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getLicenceplate() {
        return licenceplate;
    }

    public void setLicenceplate(String licenceplate) {
        this.licenceplate = licenceplate;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public BufferedImage getResizedImage() {
        return resizedImage;
    }

    public void setResizedImage(BufferedImage resizedImage) {
        this.resizedImage = resizedImage;
    }

    public String getBase64image() {
        return base64image;
    }

    public void setBase64image(String base64image) {
        this.base64image = base64image;
    }

}
