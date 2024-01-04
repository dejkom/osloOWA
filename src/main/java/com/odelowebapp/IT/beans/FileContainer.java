/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.IT.beans;

/**
 *
 * @author dematjasic
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class FileContainer {

    private List<File> pdfFiles = new ArrayList<>();
    private List<File> xmlFiles = new ArrayList<>();

    public void addPDFFile(File file) {
        pdfFiles.add(file);
    }

    public void addXMLFile(File file) {
        xmlFiles.add(file);
    }

    public List<File> getPDFFiles() {
        return pdfFiles;
    }

    public List<File> getXMLFiles() {
        return xmlFiles;
    }

    public String extractContentFromXML(String what) {
        System.out.println("Sem v extractContentFromXML:" + what);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFiles.get(0));

            // Assuming the title is in a <title> tag
            NodeList titleNodes = doc.getElementsByTagName(what);
            if (titleNodes.getLength() > 0) {
                return titleNodes.item(0).getTextContent();
            }
        } catch (Exception e) {
            System.out.println("Napaka branja iz XML:" + e);
            return "Manjka XML";
        }

        return "N/A"; // Return a default value if extraction fails
    }
}
