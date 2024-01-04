/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.PRC.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.odelowebapp.PRC.XML.entity.BMWXmlJsonProductPair;
import com.odelowebapp.PRC.XML.entity.Conditions;
import com.odelowebapp.PRC.entity.LazyBMWXMLDataModel;
import com.odelowebapp.PRC.entity.PRCbase;
import com.odelowebapp.PRC.entity.PRCdata;
import com.odelowebapp.PRC.facade.PRCbaseFacade;
import com.odelowebapp.PRC.facade.PRCdataFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.w3c.dom.Document;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class BmwXMLDownloadBean implements Serializable {

    private Date from;
    private Date to;
    private String dateFromString;
    private String dateToString;
    private HashMap<String, BmwXMLObject> bmwxmldocuments = new HashMap<String, BmwXMLObject>();

    private BmwXMLObject selectedObject;
    private Conditions selectedConditionFromXML;

    private PRCbase selectedBasePriceForEdit;

    BMWXmlJsonProductPair[] objectsFromJSON;
    List<BMWXmlJsonProductPair> objectsFromJSONList = new ArrayList();
    List<BMWXmlJsonProductPair> foundPairs = new ArrayList();
    BMWXmlJsonProductPair newPair = new BMWXmlJsonProductPair();

    @EJB
    private PRCbaseFacade baseFacade;
    @EJB
    private PRCdataFacade dataFacade;

    final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    //test lazy
    private LazyDataModel<BmwXMLObject> lazyModel;
    //

//    @PostConstruct
//    public void init() {
//        System.out.println("Sem v BmwXMLDownloadBean init()");
//        readAllXMLFilesInFolder();
//        readPruductPairConfigfromJSON();
//        newPair.setPairName("IME NOVE GRUPE");
//    }
    @PostConstruct
    public void init() {
        System.out.println("Sem v init()");
        readAllXMLFilesInFolder();
        System.out.println("Ustvarjam lazyModel z bmwxmldocuments size:"+bmwxmldocuments.size());
        lazyModel = new LazyBMWXMLDataModel(bmwxmldocuments);
    }

    public void saveNewPairToJSON() {
        System.out.println("Sem v saveNewPairToJSON()");
        objectsFromJSONList.add(newPair);
        newPair = new BMWXmlJsonProductPair();
        newPair.setPairName("IME NOVE GRUPE");
        saveCurrentPairsToJSON();
    }

    public void removePairFromJSON(int ind) {
        System.out.println("Sem v removePairFromJSON()");
        objectsFromJSONList.remove(ind);
        saveCurrentPairsToJSON();
    }

    public void saveCurrentPairsToJSON() {
        System.out.println("Sem v saveCurrentPairsToJSON()");
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<BMWXmlJsonProductPair>>() {
            }.getType();
            String json = gson.toJson(objectsFromJSONList, type);
            writer = new FileWriter("C:/WebAppFiles/BMWXML/config.json", false);
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(BmwXMLDownloadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //DEMO metoda ki kreira TESTNE podatke
    public void saveStringToJSON() {
        FileWriter writer = null;
        try {
            System.out.println("sem v saveStringToJSON()");
            List<BMWXmlJsonProductPair> pairs = new ArrayList<>();
            BMWXmlJsonProductPair pair1 = new BMWXmlJsonProductPair();
            pair1.setPairName("Pair 1");
            pair1.setProducts(Arrays.asList("Product A", "Product B", "Product C"));
            pairs.add(pair1);
            BMWXmlJsonProductPair pair2 = new BMWXmlJsonProductPair();
            pair2.setPairName("Pair 2");
            pair2.setProducts(Arrays.asList("Product X", "Product Y", "Product Z"));
            pairs.add(pair2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<BMWXmlJsonProductPair>>() {
            }.getType();
            String json = gson.toJson(pairs, type);
//            String json = gson.toJson(pairs);
            System.out.println(json);
            writer = new FileWriter("C:/WebAppFiles/BMWXML/config.json", false);
            writer.write(json);
        } catch (IOException ex) {
            Logger.getLogger(BmwXMLDownloadBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(BmwXMLDownloadBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void readPruductPairConfigfromJSON() {
        try {
            System.out.println("Sem v readPruductPairConfigfromJSON()");
            // Create a GSON object
            Gson gson = new Gson();

            // Read the JSON file into a string
            String json = null;
//        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\WebAppFiles\\BMWXML\\demo\\config.json"))) {
//            json = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

            Path fileName = Path.of("C:\\WebAppFiles\\BMWXML\\config.json");
            String content = Files.readString(fileName);
            json = content;

            System.out.println("READED JSON:" + json.toString());

            objectsFromJSON = gson.fromJson(json.toString(), BMWXmlJsonProductPair[].class);

            for (BMWXmlJsonProductPair pair : objectsFromJSON) {
                objectsFromJSONList.add(pair);
            }

        } catch (IOException ex) {
            System.out.println("Napaka readPruductPairConfigfromJSON():" + ex);
        }

    }

    public void findRelatedProductPairs(String searchparameter) {
        foundPairs.clear();
        for (BMWXmlJsonProductPair pair : objectsFromJSON) {
            System.out.println("PairName:" + pair.getPairName() + " products size:" + pair.getProducts().size());
            System.out.println("Iščem produkt " + searchparameter);
            List<String> filterProducts = pair.filterProducts(searchparameter);
            if (filterProducts.isEmpty()) {
                //empty do nothing
            } else {
                foundPairs.add(pair);
            }

        }
        System.out.println("Found pairs:" + foundPairs.size());
        if (foundPairs.size() == 1) {
            for (String product : foundPairs.get(0).getProducts()) {
                System.out.println("Produkt ki spada zraven:" + product);
            }

        } else {
            System.out.println("Found too many pairs");
        }
    }

    public void onSelectItemAction() {
        System.out.println("sem v onSelectItemAction");
        String products = selectedObject.getProductFromXMLContent();
        selectedObject.getNewBasePrice().setBmwProduct(products);
        selectedObject.getNewBasePrice().setValue(Double.parseDouble(selectedObject.getBasePriceFromXMLContent()));
    }

    public void onSelectConditionItemAction() {
        System.out.println("sem v onSelectConditionItemAction");

        System.out.println("selectedConditionFromXML:" + selectedConditionFromXML.getType());

//        PRCdata d = new PRCdata();
//        d.setType(selectedConditionFromXML.getType());
//        d.setValue(selectedConditionFromXML.getValue());
//        d.setProduct(selectedObject.getProductFromXMLContent());
//        selectedObject.setNewPRCData(d);
        selectedObject.getNewPRCData().setType(selectedConditionFromXML.getType());
        selectedObject.getNewPRCData().setValue(selectedConditionFromXML.getValue());
        selectedObject.getNewPRCData().setProduct(selectedObject.getProductFromXMLContent());

    }

    public void saveAllConditionsAtOnceDateSelectionPopup() {
        System.out.println("Sem v saveAllConditionsAtOnceDateSelectionPopup()");
    }

    public void saveAllConditionsAtOnce() {
        System.out.println("Sem v saveAllConditionsAtOnce()");
        List<Conditions> conditionsWithoutBasePrice = selectedObject.getParsedXML().getItem().getConditionsWithoutBasePrice();

        for (Conditions c : conditionsWithoutBasePrice) {
            selectedObject.getNewPRCData().setType(c.getType());
            selectedObject.getNewPRCData().setValue(c.getValue());
            selectedObject.getNewPRCData().setProduct(selectedObject.getProductFromXMLContent());
            //selectedObject.getNewPRCData().setDateFrom(from);
            //selectedObject.getNewPRCData().setDateTo(to);
            dataFacade.create(selectedObject.getNewPRCData());
        }

        //mislim da base pricea tukaj ne rabim ponovno prebirati ker se pri urejanju conditionov ne spremeni, rabim samo za števec vseh conditionov
        //List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
        //selectedObject.setBasePricesFromDB(findAllByBmwProduct);

        List<PRCdata> findAllDataByBmwProductCurentlyActive = dataFacade.findAllByBmwProductCurentlyActive(selectedObject.getProductFromXMLContent());
        selectedObject.setPrcdataFromDBCurentlyActive(findAllDataByBmwProductCurentlyActive);
        System.out.println("Pridobil sem PRCDATA size:" + findAllDataByBmwProductCurentlyActive.size());
        
        //dodelimo še ostalim vrsticam z istim produktom
        List<BmwXMLObject> collect = bmwxmldocuments.values().stream().filter(obj -> obj.getProductFromXMLContent().equals(selectedObject.getProductFromXMLContent())).collect(Collectors.toList());
        System.out.println("Obstaja toliko vrstic ki jim je treba posodobiti podatke:"+collect.size());
        for(BmwXMLObject o : collect){
            o.setPrcdataFromDBCurentlyActive(findAllDataByBmwProductCurentlyActive);
        }
    }

    public void deleteXMLfile() {
        try {
            System.out.println("sem v deleteXMLfile");
            //selectedObject.getFile().delete();

            System.out.println("Premikam:" + selectedObject.getFile().getPath());
            Files.move(selectedObject.getFile().toPath(), Paths.get("C:\\WebAppFiles\\BMWXML\\Izbrisano\\" + selectedObject.getFile().getName()), StandardCopyOption.REPLACE_EXISTING);

            //System.out.println("Premikam:"+selectedObject.getPdfFile().getPath());
            if (selectedObject.getPdfFile() != null) {
                Files.move(selectedObject.getPdfFile().toPath(), Paths.get("C:\\WebAppFiles\\BMWXML\\Izbrisano\\" + selectedObject.getPdfFile().getName()), StandardCopyOption.REPLACE_EXISTING);
            }

            bmwxmldocuments.remove(selectedObject.getFile().getName());
        } catch (IOException ex) {
            Logger.getLogger(BmwXMLDownloadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveSelectedItemIntoDatabase() {
        System.out.println("sem v saveSelectedItemIntoDatabase new");
        baseFacade.create(selectedObject.getNewBasePrice());
        List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
        selectedObject.setBasePricesFromDB(findAllByBmwProduct);
    }

    public void saveSelectedConditionItemIntoDatabase() {
        System.out.println("sem v saveSelectedConditionItemIntoDatabase new");
        dataFacade.create(selectedObject.getNewPRCData());
        List<PRCdata> findAllByBmwProduct = dataFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
        selectedObject.setPrcdataFromDB(findAllByBmwProduct);
    }

    public void saveEditedBasePriceIntoDatabase() {
        System.out.println("sem v saveEditedBasePriceIntoDatabase");
        baseFacade.edit(selectedBasePriceForEdit);
        List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
        selectedObject.setBasePricesFromDB(findAllByBmwProduct);
    }

    public void saveEditedConditionIntoDatabase() {
        System.out.println("sem v saveEditedConditionIntoDatabase");
        //TODO
//        baseFacade.edit(selectedBasePriceForEdit);
//        List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
//        selectedObject.setBasePricesFromDB(findAllByBmwProduct);
    }

    public void deleteSelectedBasePrice() {
        System.out.println("sem v deleteSelectedBasePrice");
        baseFacade.remove(selectedBasePriceForEdit);
        List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(selectedObject.getProductFromXMLContent());
        selectedObject.setBasePricesFromDB(findAllByBmwProduct);
    }

    public void testAction() {
        System.out.println("Sem v testAction()");
        dateFromString = dateFormat.format(from);
        dateToString = dateFormat.format(to);
        System.out.println("From:" + dateFromString + " To:" + dateToString);

        getXMLFilesRequests();
        readAllXMLFilesInFolder();
    }

    public List<BmwXMLObject> getListOfBMWXMLObjects() {
        return new ArrayList<BmwXMLObject>(bmwxmldocuments.values());
    }

    public void readAllXMLFilesInFolder() {
        System.out.println("Sem v readAllXMLFilesInFolder(), here I'm also reading pdf files");
        bmwxmldocuments.clear();

        try {
            // Specify the path of the directory containing XML files
            File dir = new File("C:\\WebAppFiles\\BMWXML\\");

            // Get all files in the directory
            File[] files = dir.listFiles();

            // Create a DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Iterate over all files in the directory
            for (File file : files) {
                // Check if file is an XML file
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    // Parse the XML file and get the Document object
                    Document doc = db.parse(file);
                    BmwXMLObject bmwxmldoc = new BmwXMLObject(file.getName(), file, doc);

                    System.out.println("Ali je zbirnik? : " + bmwxmldoc.isIsZbirnik());

                    List<PRCbase> findAllByBmwProduct = baseFacade.findAllByBmwProduct(bmwxmldoc.getProductFromXMLContent());
                    List<PRCbase> findAllByBmwProductCurentlyActive = baseFacade.findAllByBmwProductCurentlyActive(bmwxmldoc.getProductFromXMLContent());
                    bmwxmldoc.setBasePricesFromDB(findAllByBmwProduct);
                    bmwxmldoc.setBasePricesFromDBCurentlyActive(findAllByBmwProductCurentlyActive);

                    List<PRCdata> findAllDataByBmwProduct = dataFacade.findAllByBmwProduct(bmwxmldoc.getProductFromXMLContent());
                    List<PRCdata> findAllDataByBmwProductCurentlyActive = dataFacade.findAllByBmwProductCurentlyActive(bmwxmldoc.getProductFromXMLContent());
                    bmwxmldoc.setPrcdataFromDB(findAllDataByBmwProduct);
                    bmwxmldoc.setPrcdataFromDBCurentlyActive(findAllDataByBmwProductCurentlyActive);

                    if (findAllByBmwProduct.isEmpty()) {
                        bmwxmldoc.setDbEntriesExist(false);
                    } else {
                        bmwxmldoc.setDbEntriesExist(true);
                    }
                    bmwxmldocuments.put(file.getName(), bmwxmldoc);
                }
            }

            //še en loop za PDF-je
            for (File file : files) {
                // Check if file is an PDF file
                if (file.isFile() && file.getName().endsWith(".pdf")) {
                    // Parse the XML file and get the Document object
                    String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                    System.out.println("Sem v PDF razdelku, dodjanje v Map. File:" + file.getName());
                    BmwXMLObject bmwxmldoc = bmwxmldocuments.get(fileNameWithOutExt + ".xml");
                    bmwxmldoc.setPdfFile(file);
                    bmwxmldocuments.put(fileNameWithOutExt + ".xml", bmwxmldoc);
                }
            }

            System.out.println("V bmwxmldocuments je bilo dodanih dokumentov:" + bmwxmldocuments.size());
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    public void getXMLFilesRequests() {
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
            String password = "420dmode#";

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
            System.out.println(updatedJsonString);

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

            for (int i = 1; i < 5; i++) {
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

                    Files.copy(tenthResponse.body(), Paths.get("C:\\WebAppFiles\\BMWXML\\" + fileName), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File downloaded: " + fileName);

                    //Prenos pdf-jev
                    HttpRequest request11 = HttpRequest.newBuilder()
                            .GET()
                            .timeout(Duration.ofSeconds(10))
                            .uri(URI.create("https://ebest-gf5-prod.bmw.com/ebest/previewPdf.do?index=" + id))
                            .build();

                    HttpResponse<InputStream> eleventhResponse = client.send(request11, HttpResponse.BodyHandlers.ofInputStream());
                    Files.copy(eleventhResponse.body(), Paths.get("C:\\WebAppFiles\\BMWXML\\" + fileNamePdf), StandardCopyOption.REPLACE_EXISTING);
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

    public String getDateFromString() {
        return dateFromString;
    }

    public void setDateFromString(String dateFromString) {
        this.dateFromString = dateFromString;
    }

    public String getDateToString() {
        return dateToString;
    }

    public void setDateToString(String dateToString) {
        this.dateToString = dateToString;
    }

    public BmwXMLObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(BmwXMLObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public PRCbase getSelectedBasePriceForEdit() {
        return selectedBasePriceForEdit;
    }

    public void setSelectedBasePriceForEdit(PRCbase selectedBasePriceForEdit) {
        this.selectedBasePriceForEdit = selectedBasePriceForEdit;
    }

    public Conditions getSelectedConditionFromXML() {
        return selectedConditionFromXML;
    }

    public void setSelectedConditionFromXML(Conditions selectedConditionFromXML) {
        this.selectedConditionFromXML = selectedConditionFromXML;
    }

    public BMWXmlJsonProductPair[] getObjectsFromJSON() {
        return objectsFromJSON;
    }

    public void setObjectsFromJSON(BMWXmlJsonProductPair[] objectsFromJSON) {
        this.objectsFromJSON = objectsFromJSON;
    }

    public List<BMWXmlJsonProductPair> getFoundPairs() {
        return foundPairs;
    }

    public void setFoundPairs(List<BMWXmlJsonProductPair> foundPairs) {
        this.foundPairs = foundPairs;
    }

    public BMWXmlJsonProductPair getNewPair() {
        return newPair;
    }

    public void setNewPair(BMWXmlJsonProductPair newPair) {
        this.newPair = newPair;
    }

    public List<BMWXmlJsonProductPair> getObjectsFromJSONList() {
        return objectsFromJSONList;
    }

    public void setObjectsFromJSONList(List<BMWXmlJsonProductPair> objectsFromJSONList) {
        this.objectsFromJSONList = objectsFromJSONList;
    }

    public LazyDataModel<BmwXMLObject> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<BmwXMLObject> lazyModel) {
        this.lazyModel = lazyModel;
    }

}
