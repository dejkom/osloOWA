/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.IT.beans;

import java.io.File;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class StandardITEquipmentBean implements Serializable {

    final String directoryPath = "\\\\sapsdcfs\\Data\\IT\\28. Ponudbe\\Standard";
    
    private Map<String, FileContainer> fileContainers;
    private String selectedObjectForPdf="";

    @PostConstruct
    public void init() {
        System.out.println("Sem v StandardITEquipmentBean init()");

        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            fileContainers = new HashMap<>();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

                        if ("pdf".equalsIgnoreCase(fileExtension)) {
                            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                            fileContainers.putIfAbsent(baseName, new FileContainer());
                            fileContainers.get(baseName).addPDFFile(file);
                        } else if ("xml".equalsIgnoreCase(fileExtension)) {
                            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                            fileContainers.putIfAbsent(baseName, new FileContainer());
                            fileContainers.get(baseName).addXMLFile(file);
                        }
                    }
                }

                // Print or process the grouped files
//                for (Map.Entry<String, FileContainer> entry : fileContainers.entrySet()) {
//                    System.out.println("Files with base name: " + entry.getKey());
//                    FileContainer container = entry.getValue();
//                    System.out.println("PDF files: " + container.getPDFFiles());
//                    System.out.println("XML files: " + container.getXMLFiles());
//                }
                
            } else {
                System.out.println("No files found in the directory.");
            }
        } else {
            System.out.println("Specified directory does not exist.");
        }

    }
    
    
    
    public FileContainer getSpecificFiles(String keyname){
        return fileContainers.get(keyname);
    }

    public Map<String, FileContainer> getFileContainers() {
        return fileContainers;
    }

    public void setFileContainers(Map<String, FileContainer> fileContainers) {
        this.fileContainers = fileContainers;
    }

    public String getSelectedObjectForPdf() {
        return selectedObjectForPdf;
    }

    public void setSelectedObjectForPdf(String selectedObjectForPdf) {
        this.selectedObjectForPdf = selectedObjectForPdf;
    }
    
    
    
    
    

}
