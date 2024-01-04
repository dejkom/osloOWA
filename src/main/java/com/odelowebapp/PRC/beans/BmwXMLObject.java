/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.odelowebapp.PRC.XML.entity.Contract;
import com.odelowebapp.PRC.XML.entity.ContractChanges;
import static com.odelowebapp.PRC.XML.entity.Item.round;
import com.odelowebapp.PRC.entity.PRCbase;
import com.odelowebapp.PRC.entity.PRCdata;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author dematjasic
 */
public class BmwXMLObject {

    private String filename;
    private File file;
    private Document document;
    private boolean dbEntriesExist;
    private List<PRCbase> basePricesFromDB;
    private List<PRCbase> basePricesFromDBCurentlyActive;
    private List<PRCdata> prcdataFromDB;
    private List<PRCdata> prcdataFromDBCurentlyActive;
    private PRCbase newBasePrice;
    private PRCdata newPRCData;
    private Contract parsedXML;
    private File pdfFile;
    
    private String productFromXMLContent;
    
    private boolean isZbirnik = false;
    
    private ContractChanges fromZbirnik;

    private String rowColorStatus;

    public BmwXMLObject() {
    }

    public BmwXMLObject(String filename, File file, Document document) {
        this.filename = filename;
        this.file = file;
        this.document = document;
        this.newBasePrice = new PRCbase();
        this.newPRCData = new PRCdata();
        parseXML();
    }

    public Date getFileCreationDate() {
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
            Date newDate = new Date(creationTime.toMillis());
            return newDate;
        } catch (IOException ex) {
            // handle exception
            return null;
        }
    }

    public String getCurentlyActiveBasePrice() {
        if (basePricesFromDBCurentlyActive.isEmpty()) {
            rowColorStatus = "warning";
            return "Base price ni nastavljen"; //ni base priceov v bazi            
        } else {
            if (basePricesFromDBCurentlyActive.size() > 1) {
                rowColorStatus = "critical";
                return "Napaka - datumi se prekrivajo";
            }
            return "" + basePricesFromDBCurentlyActive.get(0).getValue();
        }
    }

    public double getRIKCalculatedFromConditionsInDB() {
        double result = 0;
        for (PRCdata c : prcdataFromDBCurentlyActive) {
            if (c.getType().equals("Basispreis") || c.getType().contains("Residual") || c.getType().contains("VERP") || c.getType().contains("LVVV")) {

            } else {
                double prebrano = c.getValue();
                result = result + prebrano;
            }
        }
        //return result;
        return round(result, 2);
    }
    
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getCurentlyActivePrcData(String type) {
        if (prcdataFromDBCurentlyActive.isEmpty()) {
            //rowColorStatus = "warning";
            return "Ni podatka v bazi"; //ni podatka v bazi            
        } else {
            for (PRCdata p : prcdataFromDBCurentlyActive) {
                if (p.getType().equals(type)) {
                    return p.getValue() + "";
                }

            }
            //return ""+prcdataFromDBCurentlyActive.get(0).getValue();  
            return "Ni pod";
        }
    }

    private void parseXML() {
        try {
            String xmlData = new String(Files.readAllBytes(file.getAbsoluteFile().toPath()), StandardCharsets.UTF_8);
            //System.out.println("xmldata:" + xmlData);
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); //turn off everything
            xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // only use fields
            parsedXML = xmlMapper.readValue(xmlData, Contract.class);
            //System.out.println("ContractNo iz objekta:" + parsedXML.getHeader().getContractNo());
        } catch (JsonProcessingException ex) {
            System.out.println("EXCEPTION PARSING XML:" + ex);
            parseXMLZbirnik();
        } catch (IOException ex) {
            System.out.println("EXCEPTION PARSING XML IOE:" + ex);
        }
    }
    
    private void parseXMLZbirnik(){
        System.out.println("Sem v parseXMLZbirnik()");
        try {
            String xmlData = new String(Files.readAllBytes(file.getAbsoluteFile().toPath()), StandardCharsets.UTF_8);
            //System.out.println("xmldata:" + xmlData);
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); //turn off everything
            xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // only use fields

            fromZbirnik = xmlMapper.readValue(xmlData, ContractChanges.class);
            System.out.println("Iz zbirnika size:" + fromZbirnik.getContracts().size());
            isZbirnik = true;
            
            String[] split = file.getName().split("-");
            System.out.println("ZACETEK:"+split[0]);
            
            for(Contract c : fromZbirnik.getContracts()){
//                String xmlString = xmlMapper.writeValueAsString(c);
                String filename = "C:\\WebAppFiles\\BMWXML\\Z_"+split[0]+"_"+c.getHeader().getContractNo()+".xml";
                xmlMapper.writeValue(new File(filename), c);
            }
            
            System.out.println("Premikam:"+file.getPath());
            Files.move(file.toPath(), Paths.get("C:\\WebAppFiles\\BMWXML\\Zbirniki\\"+file.getName()),StandardCopyOption.REPLACE_EXISTING);
            
        } catch (JsonProcessingException ex) {
            System.out.println("EXCEPTION PARSING XML ZBIRNIK:" + ex);
        } catch (IOException ex) {
            System.out.println("EXCEPTION PARSING XML ZBIRNIK IOE:" + ex);
        }
    }

    public String getProductFromXMLContent() {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("PRODUCT");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
//                System.out.println("Element Name : " + element.getNodeName());
//                System.out.println("Element Value : " + element.getTextContent());
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    public void setProductFromXMLContent(String productFromXMLContent) {
        this.productFromXMLContent = productFromXMLContent;
    }
    


    public String getAIFromProduct() {
        String product = getProductFromXMLContent();
        return product.substring(product.length() - 2);
    }

    public String getBasePriceFromXMLContent() {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("VALUE");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
//                System.out.println("Element Name : " + element.getNodeName());
//                System.out.println("Element Value : " + element.getTextContent());
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isDbEntriesExist() {
        return dbEntriesExist;
    }

    public void setDbEntriesExist(boolean dbEntriesExist) {
        this.dbEntriesExist = dbEntriesExist;
    }

    public List<PRCbase> getBasePricesFromDB() {
        return basePricesFromDB;
    }

    public void setBasePricesFromDB(List<PRCbase> basePricesFromDB) {
        this.basePricesFromDB = basePricesFromDB;
    }

    public PRCbase getNewBasePrice() {
        return newBasePrice;
    }

    public void setNewBasePrice(PRCbase newBasePrice) {
        this.newBasePrice = newBasePrice;
    }
    
    public Contract isParsedXML() {
        return parsedXML;
    }

    public Contract getParsedXML() {
        return parsedXML;
    }

    public void setParsedXML(Contract parsedXML) {
        this.parsedXML = parsedXML;
    }

    public List<PRCbase> getBasePricesFromDBCurentlyActive() {
        return basePricesFromDBCurentlyActive;
    }

    public void setBasePricesFromDBCurentlyActive(List<PRCbase> basePricesFromDBCurentlyActive) {
        this.basePricesFromDBCurentlyActive = basePricesFromDBCurentlyActive;
    }

    public String getRowColorStatus() {
        return rowColorStatus;
    }

    public void setRowColorStatus(String rowColorStatus) {
        this.rowColorStatus = rowColorStatus;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public List<PRCdata> getPrcdataFromDB() {
        return prcdataFromDB;
    }

    public void setPrcdataFromDB(List<PRCdata> prcdataFromDB) {
        this.prcdataFromDB = prcdataFromDB;
    }

    public List<PRCdata> getPrcdataFromDBCurentlyActive() {
        return prcdataFromDBCurentlyActive;
    }

    public void setPrcdataFromDBCurentlyActive(List<PRCdata> prcdataFromDBCurentlyActive) {
        this.prcdataFromDBCurentlyActive = prcdataFromDBCurentlyActive;
    }

    public PRCdata getNewPRCData() {
        return newPRCData;
    }

    public void setNewPRCData(PRCdata newPRCData) {
        this.newPRCData = newPRCData;
    }

    public boolean isIsZbirnik() {
        return isZbirnik;
    }

    public void setIsZbirnik(boolean isZbirnik) {
        this.isZbirnik = isZbirnik;
    }

    //Za potrebe lazy filteringa ker ne podpira gnezdenih objektov oz. ne znam nagruntati getterja
    public String getDescriptionFiltering(){
        return parsedXML.getItem().getDescription();
    }
    
    public void setDescriptionFiltering(String desc){
        
    }
    
    public String getContractNumberFiltering(){
        return parsedXML.getHeader().getContractNo();
    }
    
    public void setContractNumberFiltering(String s){
        
    }
    
    // xmlObject.parsedXML.item.basisPriceCondition.validityStartDate
    public Date getValidFromDateFiltering(){
        return parsedXML.getItem().getBasisPriceCondition().getValidityStartDate();
    }
    
    public void setValidFromDateFiltering(Date d){
        
    }
    
    public Date getValidToDateFiltering(){
        return parsedXML.getItem().getBasisPriceCondition().getValidityEndDate();
    }
    
    public void setValidToDateFiltering(Date d){
        
    }
    
    // END Za potrebe lazy filteringa ker ne podpira gnezdenih objektov oz. ne znam nagruntati getterja
    
}
