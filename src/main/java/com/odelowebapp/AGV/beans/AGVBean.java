/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.AGV.beans;

import com.odelowebapp.AGV.entity.*;
import com.odelowebapp.AGV.facade.*;
import com.odelowebapp.CODEKS.entity.Users;
import com.odelowebapp.CODEKS.facade.UsersFacade;
import org.apache.commons.lang3.SerializationUtils;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dematjasic
 */
@Named("agvBean")
@ViewScoped
public class AGVBean implements Serializable {

    private String placeIdFromURLparam = "";
    private String rfidKey = "";
    private String testField = "";
    private int quantity;

    private AGVplace place;

    @EJB
    private AGVplaceFacade placeFacade;
    @EJB
    private AGVcatalogFacade catalogFacade;
    @EJB
    private AGVorderFacade orderFacade;
    @EJB
    private AGVorderTypeFacade orderTypeFacade;
    @EJB
    private AGVstatusFacade statusFacade;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private AGVstatusLogFacade logFacade;

    PrimeFaces current;
    private List<AGVcatalog> catalogsForPlace; //to bo verjetno šlo stran, zbirka vseh katalogov, spodaj ločeno na dva dela

    private List<AGVcatalog> catalogWhatICanOrder;
    private List<AGVcatalog> catalogWhatICanSupply;
    private List<AGVorder> listOfOrders = new ArrayList();
    private List<AGVorder> listOfOrdersToSupply = new ArrayList();
    //private AGVmaterial selectedMaterial;
    private String selectedMaterial;
    private AGVcatalog selectedCatalog;

    Map<String, List<AGVcatalog>> catalogWhatICanOrderTEST2;
    private List<AGVorder> selectedOrdersToSupply;
    private AGVorder selectedOrder;
    
    //for order splitting
    private double partialNumberICanRealize;
    private String reasonForSplitting;
    //end for order splitting
    //order cancelation
    AGVorder selectedOrderForCancelation;
    //end order cancelation
    
    private boolean printOrdersSelectionEnabled = false;
    private AGVplace selectedDestinationForPrintingOrders;
    private List<AGVplace> placesISupplyTo;
    
    private List<AGVorder> kosaricaNabrano = new ArrayList();
    
    private String orderComment;
    private String orderPriority = "normalno";

    @Inject
    @Push(channel = "AGVchannel_growls")
    private PushContext push;

    @Inject
    @Push(channel = "AGVchannel")
    private PushContext AGVchannel;
    
    boolean socketEnabled = false;

    @PostConstruct
    public void init() {
        System.out.println("Sem v init() SessionInfo");
        placeIdFromURLparam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("placeId");

        current = PrimeFaces.current();
        System.out.println("PARAM:" + placeIdFromURLparam);
        if (placeIdFromURLparam != null) {
            place = placeFacade.find(Integer.parseInt(placeIdFromURLparam));
            
            if(place == null){
                System.out.println("PLACE JE NULL");
                //place = placeFacade.find(Integer.parseInt("1"));
                socketEnabled = false;
                current.executeScript("PF('dlg3').show()");
            }else{
                System.out.println("Najden place:"+place.getPlaceId());
                socketEnabled = true;
            }
            
            
            
//            catalogsForPlace = catalogFacade.findAllCatalogsForPlace(place);
            catalogWhatICanOrder = catalogFacade.findWhatICanOrderTEST(place);
            catalogWhatICanSupply = catalogFacade.findWhatICanSupply(place);
            catalogsForPlace = new ArrayList();
            listOfOrders = orderFacade.findOrdersByMe(place);

            //delalo prej
            //listOfOrdersToSupply = orderFacade.findOrdersForMe(place);
            
            listOfOrdersToSupply = orderFacade.findRelevantOrdersForMe(place);

            catalogWhatICanOrderTEST2 = catalogFacade.findWhatICanOrderTEST2(place);
            
            reasonForSplitting = "";
            
            placesISupplyTo = orderFacade.findAllPlacesISupplyTo(place);
            //catalogWhatICanOrder = new ArrayList();
            //catalogWhatICanSupply = new ArrayList();
            //NEXT LINE OPENS LOGIN WITH RFID KEY DIALOG
            //current.executeScript("PF('dlg1').show()");
            //Send welcome message from server to client
            //sendPushMessageToSpecificPlace("Welcome place "+place.getPlaceName()+" to odelo AGV system", place);
            //
        } else {
            System.out.println("Manjka URL parameter");
            //THIS DIALOG WARNS USER THAT THERE IS NO URL PARAMETER SET
            current.executeScript("PF('dlg2').show()");
        }

    }

    public void growlMessageAllAction() {
        System.out.println("sem v clockAction");
        String exampleJSON = "{\"message\": \"This is example message\", \"severity\": \"Warn\", \"recipient\":\"0\", \"sender\":\"0\" }";
        push.send(exampleJSON);
    }

    public void growlMessageSpecificPlaceAction(int channelnumber, int sender) {
        System.out.println("sem v clockAction");
        String exampleJSON = "{\"message\": \"This is example message\", \"severity\": \"info\", \"recipient\":\"" + channelnumber + "\", \"sender\":\""+sender+"\" }";
        push.send(exampleJSON, channelnumber);
    }

    public void pushAGVchannelAction(int channelnumber, int sender) {
        System.out.println("sem v pushAGVchannelAction; Seelcted channel number:" + channelnumber);

        String exampleJSON = "{\"message\": \"This is example message\", \"severity\": \"info\", \"recipient\":\"" + channelnumber + "\", \"sender\":\""+sender+"\" }";

        AGVchannel.send(exampleJSON, channelnumber);
    }

    public void sendPushMessageToSpecificPlace(Object message, AGVplace place) {
        System.out.println("sem v sendPushMessageToSpecificPlace, sending message to place:" + place.getPlaceName());
        AGVchannel.send(message, place.getPlaceId());
    }

    public void remoteCommandToSupply() {
        System.out.println("Sem v remoteCommandToSupply, zdaj bi moral refreshati datatable");
        //listOfOrdersToSupply = orderFacade.findOrdersForMe(place);
        listOfOrdersToSupply = orderFacade.findRelevantOrdersForMe(place);
    }

    public void remoteCommandOrdered() {
        System.out.println("Sem v remoteCommandOrdered, zdaj bi moral refreshati datatable");
        listOfOrders = orderFacade.findOrdersByMe(place);
    }

    public void testMethod() {
        System.out.println("Sem v testMethod, nahajam se na postaji:" + placeIdFromURLparam + " userRFID:" + rfidKey + " testin:" + testField);
        current.executeScript("PF('dlg1').hide()");
    }

    public void handleLogin() {
        System.out.println("Sem v handleLogin, entered parameter:" + rfidKey + " testin:" + testField);
    }

    public void handleLoginAndOrdering() {
        System.out.println("Sem v handleLoginAndOrdering, entered parameter:" + rfidKey + " testin:" + testField);
        Users user = usersFacade.findUserByCard(rfidKey);
        orderAllFromKosarica(user);
        kosaricaNabrano.clear();
        current.executeScript("PF('dlg1').hide()");
    }
    
    public void showCartPopup(){
        System.out.println("Sem v showCartPopup");
        System.out.println("Items in kosarica:"+kosaricaNabrano.size());
        current.executeScript("PF('dlg7').show()");
    }

    public void onRowSelect(SelectEvent<AGVorder> event) {
        System.out.println("Selected Order variable:" + selectedOrder.getOrderId());
        System.out.println("Selected Order event:" + String.valueOf(event.getObject().getOrderId()));

        if (selectedOrder.getStatusId().getStatusName().contains("Submitted")) {
            AGVstatus nextstatus = statusFacade.findNextStatus(selectedOrder.getStatusId());
            System.out.println("NEXT Status:" + nextstatus.getStatusName());
                        
            //for logging
                AGVstatusLog log = new AGVstatusLog();
                Date now = new Date();
                log.setTimestamp(now);
                log.setStatus(nextstatus.getStatusName());
                log.setPerson(place.getPlaceName());
                log.setOrderId(selectedOrder);
                log.setMessage("Order status changed "+selectedOrder.getStatusId().getStatusName()+" --> "+nextstatus.getStatusName());
                System.out.println("SEDAJ BI MORAL SHRANITI LOG");
                logFacade.create(log);
            //end logging 
            
            selectedOrder.setStatusId(nextstatus);
            selectedOrder.setModifiedTimestamp(now);
            orderFacade.edit(selectedOrder);
            current.executeScript("PF('orderDialog').show()");
            System.out.println("Pošljem push na kanal:" + selectedOrder.getPlaceTo().getPlaceId());
            pushAGVchannelAction(selectedOrder.getPlaceTo().getPlaceId(), selectedOrder.getPlaceFrom().getPlaceId());
        }
        if (selectedOrder.getStatusId().getStatusName().contains("Processing")) {
            AGVstatus nextstatus = statusFacade.findNextStatus(selectedOrder.getStatusId());
            selectedOrder.setNextStaus(nextstatus);
            current.executeScript("PF('orderDialog').show()");
        } else {
            System.out.println("Izbranemu orderju ne morem spremeniti statusa (ni v submitted statusu)");
            FacesMessage msg = new FacesMessage("Warning", "You can't change status of order that is in " + selectedOrder.getStatusId().getStatusName() + " status.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public String findNextStatus(AGVstatus current) {
        return statusFacade.findNextStatus(current).getStatusName();
    }

    //metoda brez parametra bo zbrisana
    public void orderAllFromKosarica() {
        System.out.println("Sem v orderAllFromKosarica(). You are ordering this number of items:" + listOfOrders.size());
        for (AGVorder order : listOfOrders) {
            if (order.getStatusId().getStatusOrder() == 0) {
                System.out.println("ORDER je v PENDING statusu, lahko ga shranim");
                AGVorderType orderType = orderTypeFacade.findByOrderName("manual order");
                System.out.println("Vrnjen orderType:" + orderType.getOrderName());
                List<AGVstatus> availablestatuses = statusFacade.findStatusesForOrderType(orderType);
                System.out.println("Statusov za izbrani OrderType:" + availablestatuses.size());
                AGVstatus nexStat = statusFacade.findNextStatus(availablestatuses.get(0));
                System.out.println("NEXT STATUS:" + nexStat.getStatusName());
                order.setStatusId(nexStat);
                order.setModifiedTimestamp(new Date());
                orderFacade.create(order);
            } else {
                System.out.println("ORDER ni v PENDING statusu, preskočim shranjevanje");
            }
        }
    }

    public void orderAllFromKosarica(Users user) {
        System.out.println("Sem v orderAllFromKosarica(). You are ordering this number of items:" + listOfOrders.size() + " with user:" + user.getCard());
        boolean needPushRequest = false;
        for (AGVorder order : listOfOrders) {
            if (order.getStatusId().getStatusOrder() == 0) {
                //potrebno za log
                Date now = new Date();
                AGVstatus naslednji = statusFacade.findNextStatus(order.getStatusId());
                //potrebno za log end
                
                System.out.println("ORDER je v PENDING statusu, lahko ga shranim");
                needPushRequest = true;
                AGVorderType orderType = orderTypeFacade.findByOrderName("manual order");
                System.out.println("Vrnjen orderType:" + orderType.getOrderName());
                List<AGVstatus> availablestatuses = statusFacade.findStatusesForOrderType(orderType);
                System.out.println("Statusov za izbrani OrderType:" + availablestatuses.size());
                AGVstatus nexStat = statusFacade.findNextStatus(availablestatuses.get(0));
                System.out.println("NEXT STATUS:" + nexStat.getStatusName());
                order.setStatusId(nexStat);
                order.setModifiedTimestamp(new Date());
                order.setUserCard(user.getCard());
                order.setUserExternalId(user.getExternalId());
                order.setUserFLName(user.getFirstname() + " " + user.getLastname());

                orderFacade.create(order);
                System.out.println("placeTo:"+order.getPlaceTo().getPlaceId()+" placeFrom:"+order.getPlaceFrom().getPlaceId());
                pushAGVchannelAction(order.getPlaceTo().getPlaceId(), order.getPlaceFrom().getPlaceId());
                
                //write log
                AGVstatusLog log = new AGVstatusLog();
                
                log.setTimestamp(now);
                log.setStatus(naslednji.getStatusName());
                log.setPerson(place.getPlaceName());
                log.setOrderId(order);
                log.setMessage("Order status changed "+order.getStatusId().getStatusName()+" --> "+naslednji.getStatusName());
                logFacade.create(log);
                //end log

            } else {
                System.out.println("ORDER ni v PENDING statusu, preskočim shranjevanje");
            }
        }
//        if(needPushRequest){
//            pushAGVchannelAction();
//        }
    }

    public void orderAllFromKosaricaWithLogin() {
        System.out.println("Sem v orderAllFromKosaricaWithLogin(). You are ordering this number of items:" + listOfOrders.size());
        current.executeScript("PF('dlg1').show()");
    }

    public void testirajOrderByMe() {
        System.out.println("sem v testirajOrderByMe()");
        listOfOrders = orderFacade.findOrdersByMe(place);
    }

    public void discardAllKosarica() {
        System.out.println("Sem v discardAllKosarica()");
        listOfOrders.clear();
    }

    public void orderButtonAction() {
        System.out.println("Sem v orderButtonAction, quantity:" + quantity + " material:" + selectedMaterial);
        AGVorder order = new AGVorder();
        order.setCreatedTimestamp(new Date());
        //order.setOrderId(quantity);
        order.setMaterialId(selectedMaterial);
        order.setQuantity((double) quantity);
        order.setPlaceFrom(selectedCatalog.getPlaceFrom());
        order.setPlaceTo(place);
        //dodan material unit 6.12.2021
        order.setMaterialUnit(selectedCatalog.getMaterialUnit());
        //

        //AGVorderType ordertypemanual = orderTypeFacade.find(1);
        AGVorderType ordertypefromArtikel = selectedCatalog.getOrderTypeId();
        order.setOrderTypeId(ordertypefromArtikel);

        AGVstatus status1 = statusFacade.find(1);
        order.setStatusId(status1);
        
        order.setPriority(orderPriority);
        order.setComment(orderComment);

        try {
            listOfOrders.add(order);
            kosaricaNabrano.add(order);
            orderComment="";
            orderPriority="normalno";
            current.executeScript("PF('productDialog').hide()");
        } catch (Exception e) {
            System.out.println("Napaka pri kreiranju orderja. Napaka: " + e);
        }

    }
    
    public void orderOrCancelOrderAction(String action){
        if (action.equals("order")) {
            System.out.println("Sem v order");
            Date now = new Date();
            
            AGVstatus naslednji = statusFacade.findNextStatus(selectedOrder.getStatusId());
            
            //preverimo če je vpisan razlog splittanja
            if(reasonForSplitting.isEmpty()){
                System.out.println("To ni splittanje, ni vnešenega razloga");
            }else{
                System.out.println("Gre za splittanje, vnešen je razlog:"+reasonForSplitting);
                AGVstatusLog logs = new AGVstatusLog();
                logs.setTimestamp(now);
                logs.setStatus("OrderSplitted");
                logs.setPerson(place.getPlaceName());
                logs.setOrderId(selectedOrder);
                logs.setMessage("Order has been splitted with reason:"+reasonForSplitting);
                logFacade.create(logs);
                
                //test, dodamo log še na drugo stran razmerja
                selectedOrder.getAGVstatusLogList().add(logs);
                //
                
                reasonForSplitting=null;
            }
            //
            
            AGVstatusLog log = new AGVstatusLog();
            log.setTimestamp(now);
            log.setStatus(naslednji.getStatusName());
            log.setPerson(place.getPlaceName());
            log.setOrderId(selectedOrder);
            log.setMessage("Order status changed "+selectedOrder.getStatusId().getStatusName()+" --> "+naslednji.getStatusName());
                       
            selectedOrder.setStatusId(naslednji);
            selectedOrder.setModifiedTimestamp(now);
            
            System.out.println("Before saving/editing order quantity:"+selectedOrder.getQuantity()+" and ID:"+selectedOrder.getOrderId());
            
            orderFacade.edit(selectedOrder);
            
            logFacade.create(log);
            
            System.out.println("Pošiljam push na kanal:"+selectedOrder.getPlaceTo().getPlaceId());
            pushAGVchannelAction(selectedOrder.getPlaceTo().getPlaceId(), selectedOrder.getPlaceFrom().getPlaceId());
            
            current.executeScript("PF('orderDialog').hide()");
            
        } else if (action.equals("reject")) {
            System.out.println("Sem v rejectu");
            Date now = new Date();
            
            AGVstatus canceled = statusFacade.findCanceledStatus(selectedOrder.getStatusId());
            
            AGVstatusLog log = new AGVstatusLog();
            log.setTimestamp(now);
            log.setStatus(canceled.getStatusName());
            log.setPerson(place.getPlaceName());
            log.setOrderId(selectedOrder);
            log.setMessage("Order status changed "+selectedOrder.getStatusId().getStatusName()+" --> "+canceled.getStatusName());
                       
            selectedOrder.setStatusId(canceled);
            selectedOrder.setModifiedTimestamp(now);
            
            orderFacade.edit(selectedOrder);
            
            logFacade.create(log);
            
            System.out.println("Pošiljam push na kanal:"+selectedOrder.getPlaceTo().getPlaceId());
            pushAGVchannelAction(selectedOrder.getPlaceTo().getPlaceId(), selectedOrder.getPlaceFrom().getPlaceId());
            
            current.executeScript("PF('orderDialog').hide()");
            
        } else if (action.equals("partially")){
            System.out.println("Sem v partially realize order");
            current.executeScript("PF('orderDialog').hide()");
            current.executeScript("PF('prorderDialog').show()");
            
        }
        //delalo prej
        //listOfOrdersToSupply = orderFacade.findOrdersForMe(place);
        
        listOfOrdersToSupply = orderFacade.findRelevantOrdersForMe(place);
        
    }
    
    //delno deluje, ne naredi prave kopije, začasno zakomentiram 25.11.2021
//    public void partialyRealizeOrder(){
//        System.out.println("Sem v partialyRealizeOrder()");
//        System.out.println("I will realize:"+partialNumberICanRealize+" articles");
//        double newQuantity = selectedOrder.getQuantity()-partialNumberICanRealize;
//        System.out.println("I will create new order for:"+newQuantity+" articles");
//        
//        //new order
//        AGVorder neworder = selectedOrder;
//        neworder.setOrderId(null);
//        neworder.setQuantity(newQuantity);
//        Date now = new Date();
//        neworder.setCreatedTimestamp(now);
//        neworder.setModifiedTimestamp(now);        
//        neworder.setStatusId(statusFacade.findStatusesForOrderType(neworder.getOrderTypeId()).get(0));
//        orderFacade.create(neworder);
//        //end new order
//        
//        selectedOrder.setQuantity(partialNumberICanRealize);
//        orderOrCancelOrderAction("order");
//        current.executeScript("PF('prorderDialog').hide()");
//    }
    
    public void partialyRealizeOrder(){
        System.out.println("Sem v partialyRealizeOrder()");
        System.out.println("I will realize:"+partialNumberICanRealize+" articles");
        double newQuantity = selectedOrder.getQuantity()-partialNumberICanRealize;
        System.out.println("I will create new order for:"+newQuantity+" articles");
        Date now = new Date();
        //new order
        AGVorder neworder = SerializationUtils.clone(selectedOrder);
        neworder.setOrderId(null);
        neworder.setQuantity(newQuantity);
        neworder.setCreatedTimestamp(now);
        neworder.setModifiedTimestamp(now);
        orderFacade.create(neworder);
        //end new order
        
        selectedOrder.setQuantity(partialNumberICanRealize);
        orderOrCancelOrderAction("order");
        current.executeScript("PF('prorderDialog').hide()");
    }
    
    public void printAvailableMaterialsForOrdering(){
        System.out.println("Sem v printAvailableMaterialsForOrdering()");
        current.executeScript("PF('ex').show()");
    }
    
    public void printAvailableMaterialsForSupplying(){
        System.out.println("Sem v printAvailableMaterialsForSupplying()");
        current.executeScript("PF('ex2').show()");
    }
    
    public void printActualOrdersList(){
        System.out.println("Sem v printActualOrdersList()");
        //current.executeScript("PF('ex3').show()");
        current.executeScript("PF('printOrdersDialog').show()");
    }
    
    public void printOrdersDialogSelection(String what){
        System.out.println("printOrdersDialogSelection:"+what);
        if(what.equals("all")){
            printOrdersSelectionEnabled = false;
            current.executeScript("PF('ex3').show()");
        }else if(what.equals("selection")){
            printOrdersSelectionEnabled = true;
            if(selectedDestinationForPrintingOrders == null){
                
            }else{
                System.out.println("Natisniti moram seznam za placeTo:"+selectedDestinationForPrintingOrders.getPlaceName());
                current.executeScript("PF('ex3').show()");
            }
        }
    }
    
    public void orderforcancelationonRowSelect(){
        System.out.println("Sem v orderforcancelationonRowSelect");
        
        if(selectedOrderForCancelation.getOrderId() == null){
            System.out.println("Izbranega lahko izbrisem, nima orderID-ja");
            current.executeScript("PF('dataChangeDlg').show()");
        }else{
            //System.out.println("Selected element:"+selectedOrderForCancelation.getOrderId()+" material:"+selectedOrderForCancelation.getMaterialId());
            System.out.println("Izbranega ne smem izbrisati, je že shranjen in ima orderID");
//            System.out.println("Poskusil bom izpisati seznam statusov");
//            List<AGVstatusLog> agVstatusLogList = selectedOrderForCancelation.getAGVstatusLogList();
//            for(AGVstatusLog stat : agVstatusLogList){
//                System.out.println("StatID:"+stat.getStatusLogId()+" status:"+stat.getStatus());
//            }
        }
    }
    
    public void removeOrderFromBasket(){
        System.out.println("Sem v removeOrderFromBasket. Odstranjujem material:"+selectedOrderForCancelation.getMaterialId()+" quan:"+selectedOrderForCancelation.getQuantity());
        boolean remresult = listOfOrders.remove(selectedOrderForCancelation);
        boolean remresult2 = kosaricaNabrano.remove(selectedOrderForCancelation);
        
//        Iterator<AGVorder> iterator = listOfOrders.iterator();
//        while (iterator.hasNext()) {
//            AGVorder ord = iterator.next();
//            if(selectedOrderForCancelation.equals(ord)) {
//                System.out.println("equals - "+ord.getMaterialId()+"|"+ord.getQuantity()+" - "+selectedOrderForCancelation.getMaterialId()+"|"+selectedOrderForCancelation.getQuantity());
//                iterator.remove();
//            }else{
//                System.out.println("not equal");
//            }
//        }
        
        System.out.println("List contained element:"+remresult+" kosarica contained element:"+remresult2);
    }
    
    public void orderforcancelationonRowUnselect(){
        System.out.println("Sem v orderforcancelationonRowUnselect");
    }

    public String getPlaceIdFromURLparam() {
        return placeIdFromURLparam;
    }

    public void setPlaceIdFromURLparam(String placeIdFromURLparam) {
        this.placeIdFromURLparam = placeIdFromURLparam;
    }

    public AGVplace getPlace() {
        return place;
    }

    public void setPlace(AGVplace place) {
        this.place = place;
    }

    public String getRfidKey() {
        return rfidKey;
    }

    public void setRfidKey(String rfidKey) {
        this.rfidKey = rfidKey;
    }

    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
    }

    public List<AGVcatalog> getCatalogsForPlace() {
        return catalogsForPlace;
    }

    public void setCatalogsForPlace(List<AGVcatalog> catalogsForPlace) {
        this.catalogsForPlace = catalogsForPlace;
    }

    public List<AGVcatalog> getCatalogWhatICanOrder() {
        return catalogWhatICanOrder;
    }

    public void setCatalogWhatICanOrder(List<AGVcatalog> catalogWhatICanOrder) {
        this.catalogWhatICanOrder = catalogWhatICanOrder;
    }

    public List<AGVcatalog> getCatalogWhatICanSupply() {
        return catalogWhatICanSupply;
    }

    public void setCatalogWhatICanSupply(List<AGVcatalog> catalogWhatICanSupply) {
        this.catalogWhatICanSupply = catalogWhatICanSupply;
    }

    public String getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(String selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public List<AGVorder> getListOfOrdersToSupply() {
        return listOfOrdersToSupply;
    }

    public void setListOfOrdersToSupply(List<AGVorder> listOfOrdersToSupply) {
        this.listOfOrdersToSupply = listOfOrdersToSupply;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<AGVorder> getListOfOrders() {
        return listOfOrders;
    }

    public void setListOfOrders(List<AGVorder> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public AGVcatalog getSelectedCatalog() {
        return selectedCatalog;
    }

    public void setSelectedCatalog(AGVcatalog selectedCatalog) {
        this.selectedCatalog = selectedCatalog;
    }

    public Map<String, List<AGVcatalog>> getCatalogWhatICanOrderTEST2() {
        return catalogWhatICanOrderTEST2;
    }

    public void setCatalogWhatICanOrderTEST2(Map<String, List<AGVcatalog>> catalogWhatICanOrderTEST2) {
        this.catalogWhatICanOrderTEST2 = catalogWhatICanOrderTEST2;
    }

    public List<AGVorder> getSelectedOrdersToSupply() {
        return selectedOrdersToSupply;
    }

    public void setSelectedOrdersToSupply(List<AGVorder> selectedOrdersToSupply) {
        this.selectedOrdersToSupply = selectedOrdersToSupply;
    }

    public AGVorder getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(AGVorder selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public double getPartialNumberICanRealize() {
        return partialNumberICanRealize;
    }

    public void setPartialNumberICanRealize(double partialNumberICanRealize) {
        this.partialNumberICanRealize = partialNumberICanRealize;
    }

    public AGVorder getSelectedOrderForCancelation() {
        return selectedOrderForCancelation;
    }

    public void setSelectedOrderForCancelation(AGVorder selectedOrderForCancelation) {
        this.selectedOrderForCancelation = selectedOrderForCancelation;
    }

    public String getReasonForSplitting() {
        return reasonForSplitting;
    }

    public void setReasonForSplitting(String reasonForSplitting) {
        this.reasonForSplitting = reasonForSplitting;
    }

    public boolean isPrintOrdersSelectionEnabled() {
        return printOrdersSelectionEnabled;
    }

    public void setPrintOrdersSelectionEnabled(boolean printOrdersSelectionEnabled) {
        this.printOrdersSelectionEnabled = printOrdersSelectionEnabled;
    }

    public AGVplace getSelectedDestinationForPrintingOrders() {
        return selectedDestinationForPrintingOrders;
    }

    public void setSelectedDestinationForPrintingOrders(AGVplace selectedDestinationForPrintingOrders) {
        this.selectedDestinationForPrintingOrders = selectedDestinationForPrintingOrders;
    }

    public List<AGVplace> getPlacesISupplyTo() {
        return placesISupplyTo;
    }

    public void setPlacesISupplyTo(List<AGVplace> placesISupplyTo) {
        this.placesISupplyTo = placesISupplyTo;
    }

    public List<AGVorder> getKosaricaNabrano() {
        return kosaricaNabrano;
    }

    public void setKosaricaNabrano(List<AGVorder> kosaricaNabrano) {
        this.kosaricaNabrano = kosaricaNabrano;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
    }

    public boolean isSocketEnabled() {
        return socketEnabled;
    }

    public void setSocketEnabled(boolean socketEnabled) {
        this.socketEnabled = socketEnabled;
    }
    
    
    

}
