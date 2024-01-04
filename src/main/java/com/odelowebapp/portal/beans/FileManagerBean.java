/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import org.primefaces.event.FileUploadEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.StreamedContent;


import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class FileManagerBean implements Serializable {

    private String rootDirectory = "C:\\WebAppFiles\\BMWXML2\\";
    private String currentDirectory = "C:\\WebAppFiles\\BMWXML2\\";
    private String newName;
    private File selectedFile;

    private StreamedContent fileContent;
    private boolean isImage;
    private boolean isXML;
    
    private String newDirName;

    public List<File> getFiles() {
        File folder = new File(currentDirectory);
        return Arrays.asList(folder.listFiles());
    }

    public void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void renameFile() {
        try {
            File renamedFile = new File(selectedFile.getParent(), newName + getFileExtension(selectedFile));
            Files.move(selectedFile.toPath(), renamedFile.toPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleFileOrDirectory(File file) {
        if (file.isDirectory()) {
            setCurrentDirectory(file.getAbsolutePath());
        } else {
            openFile(file);
        }
    }

    public String getModifiedPath(File file) {
        String absolutePath = file.getAbsolutePath();
        String modifiedPath = absolutePath.replaceFirst("^C:\\\\WebAppFiles", "");
        return modifiedPath.replace('\\', '/');
    }

    public void openFile(File file) {
        selectedFile = file;
        isImage = FilenameUtils.getExtension(file.getName()).toLowerCase().equals("png")
                || FilenameUtils.getExtension(file.getName()).toLowerCase().equals("jpg")
                || FilenameUtils.getExtension(file.getName()).toLowerCase().equals("jpeg")
                || FilenameUtils.getExtension(file.getName()).toLowerCase().equals("gif");
        
        isXML = FilenameUtils.getExtension(file.getName()).toLowerCase().equals("xml");
    }

    public void goBack() {
        File current = new File(currentDirectory);
        currentDirectory = current.getParent();
    }

    public void createDirectory(String directoryName) {
        try {
            Files.createDirectory(Paths.get(currentDirectory, directoryName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            Files.write(Paths.get(currentDirectory, event.getFile().getFileName()), event.getFile().getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
    
    public String getXmlContent(File file) {
        try {
            String xml = new String(Files.readAllBytes(file.toPath()));
//            return xmlContent;
             // Parse the XML into a document object
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xml)));

        // Set up the transformer to indent the XML
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // Transform the XML into a pretty-printed string
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        return result.getWriter().toString();
                

        } catch (IOException ex) {
            Logger.getLogger(FileManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(FileManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(FileManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(FileManagerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public StreamedContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(StreamedContent fileContent) {
        this.fileContent = fileContent;
    }

    public boolean isIsImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

    public boolean isIsXML() {
        return isXML;
    }

    public void setIsXML(boolean isXML) {
        this.isXML = isXML;
    }

    public String getNewDirName() {
        return newDirName;
    }

    public void setNewDirName(String newDirName) {
        this.newDirName = newDirName;
    }
    
    

}
