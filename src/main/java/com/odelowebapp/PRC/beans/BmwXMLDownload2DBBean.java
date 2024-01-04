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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.odelowebapp.PRC.XML.entity.Conditions;
import com.odelowebapp.PRC.XML.entity.Contract;
import com.odelowebapp.PRC.XML.entity.ContractChanges;
import com.odelowebapp.PRC.entity.PRCContract;
import com.odelowebapp.PRC.entity.PRCContractdetail;
import com.odelowebapp.PRC.facade.PRCContractFacade;
import com.odelowebapp.PRC.facade.PRCContractdetailFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class BmwXMLDownload2DBBean implements Serializable {

    private Date from;
    private Date to;
    private String dateFromString;
    private String dateToString;

    final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    final DateFormat dateFormat2 = new SimpleDateFormat("ddMMyyyy");

    private HashMap<String, Contract> contractsHM = new HashMap<String, Contract>();
    private HashMap<String, File> pdfsHM = new HashMap<String, File>();

    @EJB
    PRCContractFacade contractFacade;
    @EJB
    PRCContractdetailFacade contractdetailFacade;

    String folderNameFromDate;

    @PostConstruct
    public void init() {
        System.out.println("Sem v init()");
        folderNameFromDate = dateFormat2.format(new Date());
        
        //ustvarim direktorij z današnjim datumo
        // Create a File object representing the directory
        File directory = new File("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate);

        // Check if the directory already exists
        if (!directory.exists()) {
            // Create the directory and its parent directories if they don't exist
            boolean created = directory.mkdirs();

            if (created) {
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Failed to create the directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
        //
        
        //readAllXMLFilesInFolder(folderNameFromDate + "\\");
        tryToParseAllXMLFilesInFolder(folderNameFromDate + "\\");
        readAllXMLFilesInFolderNov(folderNameFromDate + "\\");
    }

    public void testAction() {
        System.out.println("Sem v testAction()");
        dateFromString = dateFormat.format(from);
        dateToString = dateFormat.format(to);
        System.out.println("From:" + dateFromString + " To:" + dateToString);

        getXMLFilesRequests("");
    }

    public void tryToParseAllXMLFilesInFolder(String path) {
        System.out.println("Sem v tryToParseAllXMLFilesInFolder(). This will split Zbirnik to multiple singles");

        try {
            // Specify the path of the directory containing XML files
            File dir = new File("C:\\WebAppFiles\\BMWXML2\\" + path);

            // Get all files in the directory
            File[] files = dir.listFiles();

            // Iterate over all files in the directory
            for (File file : files) {
                // Check if file is an XML file
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    Contract parsedContract = parseXMLreturnContract(file);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in tryToParseAllXMLFilesInFolder:" + e);
        }

    }

    public void readAllXMLFilesInFolderNov(String path) {
        System.out.println("Sem v readAllXMLFilesInFolderNov(). This will save data from XML to database and move them to inDB directory");

        try {
            // Specify the path of the directory containing XML files
            File dir = new File("C:\\WebAppFiles\\BMWXML2\\" + path);

            // Get all files in the directory
            File[] files = dir.listFiles();

            // Iterate over all files in the directory
            for (File file : files) {
                // Check if file is an XML file
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    Contract parsedContract = parseXMLreturnContract(file);

                    String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());

                    String readXMLasString = readXMLasString(file);
                    //System.out.println(readXMLasString.length() + " XML vsebina:" + readXMLasString);
                    String cleanedXml = readXMLasString.replaceAll("<\\?xml[^>]+\\?>", "");
                    //System.out.println(readXMLasString.length() + " XML vsebina cleaned:" + cleanedXml);

                    //shranim v bazo
                    PRCContract contract = new PRCContract();
                    contract.setIdent(parsedContract.item.product);
                    contract.setDateRetrieved(new Date());
                    Date ammd;
                    try{
                       ammd = dateFormat.parse(parsedContract.header.amendmentDate);
                    }catch(Exception e){
                       ammd=null;
                    }
                    contract.setAmendmentDate(ammd);
                    contract.setOrderDate(dateFormat.parse(parsedContract.header.orderDate));
                    String pdfpath = dir.getPath() + "\\" + fileNameWithOutExt + ".pdf";
                    contract.setPdfpath(pdfpath);
                    contract.setStatus("Not confirmed"); // POTREBNO DOLOCITI KAJ BO TU
                    
                    //System.out.println("---NOVI PODATKI---:"+parsedContract.item.demand_loc.get(0).demandlocation);
                    
                    contract.setDemandLoc(parsedContract.item.demand_loc.get(0).demandlocation +" - "+ parsedContract.item.demand_loc.get(0).deliverylocation+"");
                    contract.setDeliveryLoc(parsedContract.item.demand_loc.get(0).deliverylocation+"");
                    contract.setProductLoc(parsedContract.item.demand_loc.get(0).productlocation+"");
                    contract.setIncoterm(parsedContract.item.demand_loc.get(0).incoterm);
                    contract.setIncotermLoc(parsedContract.item.demand_loc.get(0).incotermlocation);
                    
                    contract.setContractNumber(parsedContract.header.contractNo);
                    contract.setXml(cleanedXml);

                    contractFacade.create(contract);
                    
                    

                    if(parsedContract.item.conditions == null){
                        System.out.println("---------NI POSTAVK---------");
                    }else{
                        
                    
                    for(Conditions con : parsedContract.item.conditions){
                        //System.out.println("Sem v loopu");
                        PRCContractdetail prcd = new PRCContractdetail();
                        prcd.setPogId(contract);
                        
                        if(con.type.equals("Residual transf.cost")){
                            prcd.setType("RUKL");
                        }else{
                            prcd.setType(con.type);
                        }
                        
                        prcd.setValue(con.value);
                        
                        try{
                            String validityStart = con.validityStart;
                            Date dateValidityStart = dateFormat.parse(validityStart);
                            prcd.setDetValidFrom(dateValidityStart);
                        }catch(Exception e){
                            System.out.println("Can't parse date (validity Start) from XML");
                        }
                        
                        double frmln, frmlw;
                        try{                            
                            frmln = convertStringToDouble(con.frmlnotation);
                            
                        }catch(Exception e){
                            frmln=0.0;
                        }
                        
                        try{
                            frmlw = convertStringToDouble(con.frmlweight);
                        }catch(Exception e){
                            frmlw=0.0;
                        }
                        
                        prcd.setFrmlNotation(frmln);
                        prcd.setFrmlWeight(frmlw);
                        prcd.setStatus("Unconfirmed"); // dolociti kaj pride tu notri
                        contractdetailFacade.create(prcd);
                    }
                    }

                    // Create the directory if it doesn't exist
                    Path destinationDirectory = Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\inDB\\" + file.getName()).getParent();
                    try {
                        Files.createDirectories(destinationDirectory);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Files.move(file.toPath(), Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\inDB\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                }
            }

        } catch (Exception e) {
            //System.out.println("Exception in readAllXMLFilesInFolderNov:" + e);
            e.printStackTrace(System.out);
        }

    }
    
    private double convertStringToDouble(String s){
        DecimalFormat format = new DecimalFormat();
        format.setGroupingUsed(true);
        format.setGroupingSize(3);
        format.setMaximumFractionDigits(5);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);

        Number number = 0;
        try {
            number = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double d = number.doubleValue();
        return d;
    }

    private String readXMLasString(File xmlFile) {
        Reader fileReader = null;
        BufferedReader bufReader = null;
        try {
            fileReader = new FileReader(xmlFile);
            bufReader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();

            while (line != null) {
                sb.append(line).append("\n");
                line = bufReader.readLine();
            }

            String xml2String = sb.toString();
            return xml2String;

        } catch (IOException ex) {
            Logger.getLogger(BmwXMLDownload2DBBean.class.getName()).log(Level.SEVERE, null, ex);
            return "CANT READ";
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BmwXMLDownload2DBBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Contract parseXMLreturnContract(File file) {
        Contract parsedXML = new Contract();
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
            parseXMLZbirnikReturnContracts(file);
        } catch (IOException ex) {
            System.out.println("EXCEPTION PARSING XML IOE:" + ex);
        }
        return parsedXML;
    }

    private List<Contract> parseXMLZbirnikReturnContracts(File file) {
        System.out.println("Sem v parseXMLZbirnikReturnContracts()");
        List<Contract> forreturn = new ArrayList();
        try {
            String xmlData = new String(Files.readAllBytes(file.getAbsoluteFile().toPath()), StandardCharsets.UTF_8);
            //System.out.println("xmldata:" + xmlData);
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); //turn off everything
            xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // only use fields

            ContractChanges fromZbirnik = xmlMapper.readValue(xmlData, ContractChanges.class);
            System.out.println("Iz zbirnika nastalo dokumentov:" + fromZbirnik.getContracts().size());
            boolean isZbirnik = true;

            String[] split = file.getName().split("-");
            //System.out.println("ZACETEK:" + split[0]);

            for (Contract c : fromZbirnik.getContracts()) {
                String filename = "C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\Z_" + split[0] + "_" + c.getHeader().getContractNo() + ".xml";
                xmlMapper.writeValue(new File(filename), c);
                forreturn.add(c);
            }

            System.out.println("Premikam:" + file.getPath());

            // Create the directory if it doesn't exist
            Path destinationDirectory = Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\Zbirniki\\" + file.getName()).getParent();
            try {
                Files.createDirectories(destinationDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Files.move(file.toPath(), Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\Zbirniki\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (JsonProcessingException ex) {
            try {
                //Če zbirnika še vedno ni mogoče parsati je verjetno pokvarjena datoteka (ni zbirnik)
                System.out.println("EXCEPTION PARSING XML ZBIRNIK:" + ex);
                // Create the directory if it doesn't exist
                Path destinationDirectory = Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\Pokvarjeni\\" + file.getName()).getParent();
                try {
                    Files.createDirectories(destinationDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Files.move(file.toPath(), Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\Pokvarjeni\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex1) {
                Logger.getLogger(BmwXMLDownload2DBBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            System.out.println("EXCEPTION PARSING XML ZBIRNIK IOE:" + ex);
        }
        return forreturn;
    }

    //pridobimo ident
    public String getProductFromXMLContent(Document document) {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("PRODUCT");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    //pridobimo stevilko pogodbe
    public String getContractNumberFromXMLContent(Document document) {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("CONTRACT_NO");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    //pridobimo ammendment date
    public String getAmendmentDateFromXMLContent(Document document) {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("AMENDMENT_DATE");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    //pridobimo order date
    public String getOrderDateFromXMLContent(Document document) {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("ORDER_DATE");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    //pridobimo activity text
    public String getActivityFromXMLContent(Document document) {
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("ACTIVITY_TEXT");

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                return element.getTextContent();
            }
        }

        return "No value in XML!";
    }

    public void getXMLFilesRequests(String path) {
        System.out.println("Sem v getXMLFilesRequests()");

        try {

            System.out.println("Startam in grem na: https://auth.bmw.com/auth/XUI/?realm=/internetb2x&goto=https%3A%2F%2Fauth.bmw.com%3A443%2Fauth%2Foauth2%2Frealms%2Froot%2Frealms%2Finternetb2x%2Fauthorize%3Fstate%3DkN195JRnD56-KC8rNftT8UFK3T8%26nonce%3DnBzRcX_2IPG-qypH%26realm%3$");

            String firstURL = "https://auth.bmw.com/auth/XUI/?realm=/internetb2x&goto=https%253A%252F%252Fauth.bmw.com%253A443%252Fauth%252Foauth2%252Frealms%252Froot%252Frealms%252Finternetb2x%252Fauthorize%253Fstate%253DkN195JRnD56-KC8rNftT8UFK3T8%2526nonce%253DnBzRcX_2IPG-qypH%2526realm%253$";
            String secondURL = "https://auth.bmw.com/auth/json/realms/root/realms/internetb2x/authenticate?goto=https%253A%252F%252Fauth.bmw.com%253A443%252Fauth%252Foauth2%252Frealms%252Froot%252Frealms%252Finternetb2x%252Fauthorize%253Fstate%253DnzaMwbvJE0UKcIIddb8NB1aYGTY%2526nonc$";
            String fourthURL = "https://ebest-gf5-prod.bmw.com/ebest/controller.do?curlang=en";
            String fifthURL = "https://ebest-gf5-prod.bmw.com:443/agent/cdsso-oauth2";
            String sixthURL = "https://ebest-gf5-prod.bmw.com/ebest/initAction.do?curlang=en";
            String seventhURL = "https://ebest-gf5-prod.bmw.com/ebest/showOrdersAction.do";
            String username = "Dean.Matjasic";
            String password = "dejko0310#";

            // Create a CookieManager with the CookiePolicy
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);

            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .cookieHandler(cookieManager)
                    .build();

            URI firstUri = new URI(firstURL);
            URI secondUri = new URI(secondURL);
            URI fourthUri = new URI(fourthURL);
            URI fifthUri = new URI(fifthURL);
            URI sixthUri = new URI(sixthURL);
            URI seventhUri = new URI(seventhURL);

            // creating the request
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(firstUri.toASCIIString()))
                    .build();

            HttpResponse<String> firstResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println("First response:"+firstResponse.body());

            HttpRequest request2 = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(secondUri.toASCIIString()))
                    .build();

            HttpResponse<String> secondResponse = client.send(request2, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Second response:"+secondResponse.body());

            Gson gson = new Gson();

            JsonObject fromJson = gson.fromJson(secondResponse.body(), JsonObject.class);
            System.out.println("authId iz JSONA:" + fromJson.get("authId").getAsString());

            JsonArray callbacks = fromJson.getAsJsonArray("callbacks");
            String passFieldTest = callbacks.get(2).getAsJsonObject().get("input").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
            String unameFieldTest = callbacks.get(1).getAsJsonObject().get("input").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();

            callbacks.get(2).getAsJsonObject().get("input").getAsJsonArray().get(0).getAsJsonObject().addProperty("value", password);
            callbacks.get(1).getAsJsonObject().get("input").getAsJsonArray().get(0).getAsJsonObject().addProperty("value", username);

            System.out.println("PassfieldTest:" + passFieldTest);
            System.out.println("unamefieldTest:" + unameFieldTest);

            // convert JsonObject back to JSON string
            String updatedJsonString = gson.toJson(fromJson);
//            System.out.println("UPDATET JSON STRING: "+updatedJsonString);

            HttpRequest request3 = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(updatedJsonString))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(secondUri.toASCIIString()))
                    .build();

            HttpResponse<String> thirdResponse = client.send(request3, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Third response:"+thirdResponse.body());            

            HttpRequest request4 = HttpRequest.newBuilder()
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(fourthUri.toASCIIString()))
                    //.header("Cookie", String.join("; ", cookies))
                    .build();

            HttpResponse<String> fourthResponse = client.send(request4, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Fourth response:"+fourthResponse.body());

            String[] splitted = fourthResponse.body().split("\"");

            System.out.println("splitted:" + splitted[27]);
            System.out.println("splitted:" + splitted[33]);

            Map<Object, Object> data = new HashMap();
            data.put("id_token", splitted[27]);
            data.put("state", splitted[33]);

            JsonObject dataj = new JsonObject();
            dataj.addProperty("state", splitted[33]);
            dataj.addProperty("id_token", splitted[27]);
            System.out.println("JSON Data for auth:" + gson.toJson(dataj));

            String dd = "{'state':'" + splitted[33] + "','id_token':'" + splitted[27] + "'}";
            String ee = "id_token=" + splitted[27] + "&state=" + splitted[33] + "";
            System.out.println("DD:" + dd);
            System.out.println("EE:" + ee);

            HttpRequest request5 = HttpRequest.newBuilder()
                    .timeout(Duration.ofSeconds(20))
                    .uri(URI.create(fifthUri.toASCIIString()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(ee))
                    .build();

            HttpResponse<String> fifthResponse = client.send(request5, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Fifth response:"+fifthResponse.body()+ "5RstatusCode:"+fifthResponse.statusCode());

            HttpResponse<String> fourthResponse2 = client.send(request4, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Fourth response2:"+fourthResponse2.body());

            HttpRequest request6 = HttpRequest.newBuilder()
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(sixthUri.toASCIIString()))
                    .build();

            HttpResponse<String> sixthResponse = client.send(request6, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Sixth response:"+sixthResponse.body());

            //String parametriStari = "orderNr=&dateFrom=01.01.2022&dateTo=01.01.2025&articleNr=&buyerNr=&articleDesc=&name=&orderKind=0&uniqueKey=&orderConfirmationFlag=0&downloadStatus=0&amTerminFlag=0&newSupplierNr=";
            String parametriStari = "orderNr=&dateFrom=" + dateFromString + "&dateTo=" + dateToString + "&articleNr=&buyerNr=&articleDesc=&name=&orderKind=0&uniqueKey=&orderConfirmationFlag=0&downloadStatus=0&amTerminFlag=0&newSupplierNr=";

            System.out.println("PARAMETRISTARI:" + parametriStari);

            HttpRequest request72 = HttpRequest.newBuilder()
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(seventhURL))
                    .build();

            HttpResponse<String> seventh2Response = client.send(request72, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Seventh2 response:"+seventh2Response.body());
//            System.out.println("----------------------------------------------------------------------------");

            HttpRequest request7 = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(parametriStari))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .timeout(Duration.ofSeconds(10))
                    .uri(URI.create(seventhURL))
                    .build();

            HttpResponse<String> seventhResponse = client.send(request7, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Seventh response:"+seventhResponse.body());
//            System.out.println("----------------------------------------------------------------------------");

            for (int i = 1; i < 10; i++) {
                String queryURL = "https://ebest-gf5-prod.bmw.com/ebest/pagination.do?currentPage=" + i + "&orderConfirmationFlag=0&downloadStatus=0&amTerminFlag=0&dateFrom=" + dateFromString + "&dateTo=" + dateToString + "&articleDesc=&name=&orderKind=0&buyerNr=&articleNr$";
                System.out.println("v FOR loopu grem na URL: " + queryURL);

                HttpRequest request8 = HttpRequest.newBuilder()
                        .GET()
                        .timeout(Duration.ofSeconds(10))
                        .uri(URI.create(queryURL))
                        .build();

                HttpResponse<String> eightResponse = client.send(request8, HttpResponse.BodyHandlers.ofString());

                String[] splittedresponse = eightResponse.body().split("<a href=\"/ebest/showPositions.do");
                for (int j = 1; j < splittedresponse.length; j++) {
                    String id = splittedresponse[j].split("</a>")[0].split("=")[1].split("\"")[0];
                    String contractnumber = 'B' + splittedresponse[j].split("</a>")[0].split(">")[1];
                    String version = splittedresponse[j].split("<!-- Amendment Number Fix Start -->")[1].split("<!--")[0].strip();
                    String unique_number = splittedresponse[j].split("</td>")[4].split(">")[1].split(">")[0].replace("&nbsp;", "").strip();
                    String fileName = "" + contractnumber + "-" + version + "-" + unique_number + ".xml"; //example file name: BF3R196H-005-0009NZYW.xml
                    String fileNamePdf = "" + contractnumber + "-" + version + "-" + unique_number + ".pdf";
                    System.out.println("FILE:" + fileName);

                    HttpRequest request9 = HttpRequest.newBuilder()
                            .GET()
                            .timeout(Duration.ofSeconds(10))
                            .uri(URI.create("https://ebest-gf5-prod.bmw.com/ebest/downloadXml.do?index=" + id))
                            .build();

                    HttpResponse<String> ninethResponse = client.send(request9, HttpResponse.BodyHandlers.ofString());

                    HttpRequest request10 = HttpRequest.newBuilder()
                            .GET()
                            .timeout(Duration.ofSeconds(10))
                            .uri(URI.create("https://ebest-gf5-prod.bmw.com/ebest/downloadXmlStream.do"))
                            .build();

                    HttpResponse<InputStream> tenthResponse = client.send(request10, HttpResponse.BodyHandlers.ofInputStream());
//                    System.out.println("Tenth response:"+tenthResponse.body());

                    Files.copy(tenthResponse.body(), Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File downloaded: " + fileName);

                    //Prenos pdf-jev
                    HttpRequest request11 = HttpRequest.newBuilder()
                            .GET()
                            .timeout(Duration.ofSeconds(10))
                            .uri(URI.create("https://ebest-gf5-prod.bmw.com/ebest/previewPdf.do?index=" + id))
                            .build();

                    HttpResponse<InputStream> eleventhResponse = client.send(request11, HttpResponse.BodyHandlers.ofInputStream());
                    Files.copy(eleventhResponse.body(), Paths.get("C:\\WebAppFiles\\BMWXML2\\" + folderNameFromDate + "\\" + fileNamePdf), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("pdf downloaded: " + fileNamePdf);
                    //end Prenos pdf-jev

                }

            }

        } catch (IOException ex) {
            System.out.println("EXCEPTION:" + ex);
        } catch (InterruptedException ex) {
            System.out.println("EXCEPTION:" + ex);
        } catch (URISyntaxException ex) {
            System.out.println("EXCEPTION:" + ex);
        }
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
    
    
    
    
    //NEUPORABLJENO
    
    public void readAllXMLFilesInFolder(String path) {
        System.out.println("Sem v readAllXMLFilesInFolder(), here I'm also reading pdf files");

        try {
            // Specify the path of the directory containing XML files
            File dir = new File("C:\\WebAppFiles\\BMWXML2\\" + path);

            // Get all files in the directory
            File[] files = dir.listFiles();

            // Create a DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Iterate over all files in the directory
            for (File file : files) {
                // Check if file is an XML file
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                    System.out.println("Sem v XML razdelku, dodjanje v Map. File:" + fileNameWithOutExt + ".xml");
                    // Parse the XML file and get the Document object
                    Document doc = db.parse(file);

                    String productFromXMLContent = getProductFromXMLContent(doc);
                    System.out.println("Product from XML content:" + productFromXMLContent + " " + productFromXMLContent.length());

                    String contractNumberFromXMLContent = getContractNumberFromXMLContent(doc);
                    System.out.println("Contract Number from XML content:" + contractNumberFromXMLContent + " " + contractNumberFromXMLContent.length());

                    String amendmentDateFromXMLContent = getAmendmentDateFromXMLContent(doc);
                    System.out.println("Amendment date from XML content:" + amendmentDateFromXMLContent + " " + amendmentDateFromXMLContent.length());

                    String orderDateFromXMLContent = getOrderDateFromXMLContent(doc);
                    System.out.println("Order date from XML content:" + orderDateFromXMLContent + " " + orderDateFromXMLContent.length());

                    String readXMLasString = readXMLasString(file);
                    System.out.println(readXMLasString.length() + " XML vsebina:" + readXMLasString);
                    String cleanedXml = readXMLasString.replaceAll("<\\?xml[^>]+\\?>", "");
                    System.out.println(readXMLasString.length() + " XML vsebina cleaned:" + cleanedXml);

                    String activityFromXMLContent = getActivityFromXMLContent(doc);
                    System.out.println("Activity text from XML content:" + activityFromXMLContent + " " + activityFromXMLContent.length());

                    contractsHM.put(fileNameWithOutExt, parseXMLreturnContract(file));

                    //shranim v bazo
                    PRCContract contract = new PRCContract();
                    contract.setIdent(productFromXMLContent);
                    contract.setDateRetrieved(new Date());
                    contract.setAmendmentDate(dateFormat.parse(amendmentDateFromXMLContent));
                    contract.setOrderDate(dateFormat.parse(orderDateFromXMLContent));
                    String pdfpath = dir.getPath() + "\\" + fileNameWithOutExt + ".pdf";
                    contract.setPdfpath(pdfpath);
                    contract.setStatus(activityFromXMLContent);
                    contract.setContractNumber(contractNumberFromXMLContent);
                    contract.setXml(cleanedXml);

                    contractFacade.create(contract);
                    //

                } else if (file.isFile() && file.getName().endsWith(".pdf")) {
                    String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                    System.out.println("Sem v PDF razdelku, dodajanje v Map. File:" + fileNameWithOutExt + ".pdf");
                    System.out.println("Sem v PDF razdelku, polna pot bi naj bila:" + dir.getPath() + "\\" + fileNameWithOutExt + ".pdf");
                    pdfsHM.put(fileNameWithOutExt, file);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
    
    
    
    //neuporabljeno
    
    
    
    

}
