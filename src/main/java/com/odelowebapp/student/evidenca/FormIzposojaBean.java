/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.student.evidenca;

import com.odelowebapp.HR.entity.VCodeksUsersAktualniZaposleni;
import com.odelowebapp.HR.facade.HRvcodeksusersFacade;
import com.odelowebapp.entity.ZebraPrintingBean;
import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.student.evidenca.entity.ASSLocation;
import com.odelowebapp.student.evidenca.entity.ASSLog;
import com.odelowebapp.student.evidenca.entity.ASSStatus;
import com.odelowebapp.student.evidenca.entity.ASSType;
import com.odelowebapp.student.evidenca.entity.ASS_Q_IzposojenoSredstvo;
import com.odelowebapp.student.evidenca.facade.ASSAssetFacade;
import com.odelowebapp.student.evidenca.facade.ASSLogFacade;
import com.odelowebapp.student.evidenca.facade.ASSStatusFacade;
import com.odelowebapp.student.evidenca.facade.ASSTypeFacade;
import com.odelowebapp.usermanagement.controller.HomeController;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.PrimeFaces;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class FormIzposojaBean implements Serializable {

    @EJB
    private ASSLogFacade assLogFacade;
    @EJB
    private ASSAssetFacade assAssetFacade;
    @EJB
    private ASSStatusFacade assStatusFacade;
    @EJB
    private HRvcodeksusersFacade usersEJB;
    @EJB
    private ASSTypeFacade assTypeFacade;

    @Inject
    private HomeController homeController;

    private ASSAsset selectedAsset;
    private ASSLog trenutniLog;

    private VCodeksUsersAktualniZaposleni zaposleniKiVraca;

    private List<ASSStatus> mozneOperacije = new ArrayList();
    private List<VCodeksUsersAktualniZaposleni> aktualniZaposleni;
    private List<ASS_Q_IzposojenoSredstvo> izposojeno = new ArrayList();

    private ASSType kompletZaRazdiranje;
    private ASSType kompletZaTiskanje;

    //za tiskanje kode kompleta
    private String prejemnikKompleta;
    private String vsebinaqrkode;

    ASS_Q_IzposojenoSredstvo selectedAssetToReturn;
    ASSAsset selectedAssetRazdiranje;
    
    private ASSLocation selectedLocationToReturn;
    private ASSAsset selectedLocationToReturnAsset;

    private String kodaKompletaQRparameter;

    PrimeFaces current;

    @PostConstruct
    public void init() {
        System.out.println("Sem v formIzposojaBean() init");

        kodaKompletaQRparameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("QRparameter");
        System.out.println("QR parameter:" + kodaKompletaQRparameter);

        current = PrimeFaces.current();
        mozneOperacije = assStatusFacade.findAllActiveStatuses();
        aktualniZaposleni = usersEJB.zaposleniZaAndroidAplikacijo();
    }

    public void tiskajKodoZebra() {
        System.out.println("Sem v tiskajKodoZebra() z prejemnika:" + prejemnikKompleta);
        ZebraPrintingBean zpb = new ZebraPrintingBean();

        zpb.tiskajEtiketo("TSCTA200", prejemnikKompleta, kompletZaTiskanje.getIDType() + "", "http://10.36.64.16:7070/osloWebApp/faces/common/evidenca/it/indexPublic.xhtml?QRparameter=");
    }

    public void prejemnikKompletaChange() {
        System.out.println("Sem v prejemnikKompletaChange:" + prejemnikKompleta);
    }

    public void manipulacijaSredstevOpen() {
        System.out.println("Sem v manipulacijaSredstevOpen, izbrano je sredstvo:" + selectedAsset.getAssetDescription());
        trenutniLog = new ASSLog();
        current.executeScript("PF('dlg1').show()");
    }

    public void shraniEnoManipulacijo() {
        System.out.println("sem v shraniEnoManipulacijo()");
        trenutniLog.setIDLogAsset(selectedAsset);
        trenutniLog.setLogTimestamp(new Date());

        trenutniLog.setLogQuantity(trenutniLog.getLogQuantity() * trenutniLog.getIDLogStatus().getStatusFactor());

//        if(trenutniLog.getIDLogStatus().getIDStatus() == 1 || trenutniLog.getIDLogStatus().getIDStatus() == 3){
//            System.out.println("Stanje v MINUS");
//            trenutniLog.setLogQuantity(trenutniLog.getLogQuantity()*-1);
//        }else{
//            System.out.println("Stanje v PLUS");
//        }
        assLogFacade.create(trenutniLog);

        //počistimo objekte in zapremo modalno okno
        selectedAsset = null;
        trenutniLog = new ASSLog();
        current.executeScript("PF('dlg1').hide()");
    }

    public void shraniEnoManipulacijoKomplet(ASSType asstype) {
        try {
            System.out.println("sem v shraniEnoManipulacijoKomplet(). V kompletu je artiklov:" + asstype.getASSAssetList().size());

            ASSLog kopija = (ASSLog) SerializationUtils.clone(trenutniLog);

            for (ASSAsset asset : asstype.getASSAssetList()) {
                System.out.println("Shraniti moram artikel iz kompleta:" + asset.getAssetDescription());

                trenutniLog.setIDLogAsset(asset.getIDAssetParent());
                trenutniLog.setLogTimestamp(new Date());

                System.out.println("asset log list size:" + asset.getASSLogList().size());
                System.out.println("first log id in list:" + asset.getASSLogList().get(0).getIDLog());
                System.out.println("kopija status factor:" + kopija.getIDLogStatus().getStatusFactor());

                trenutniLog.setLogQuantity(asset.getASSLogList().get(0).getLogQuantity() * kopija.getIDLogStatus().getStatusFactor());
                trenutniLog.setLogComment(kopija.getLogComment());
                trenutniLog.setLogExternalID(kopija.getLogExternalID());
                trenutniLog.setIDLogStatus(kopija.getIDLogStatus());

                //potrebno je še pobrisati komplet, in v trenutniLog nastaviti asset starša
                assLogFacade.create(trenutniLog);

                assAssetFacade.remove(asset);

                List<ASSLog> findIzdanoVKompletDescription = assLogFacade.findIzdanoVKompletDescription(asstype.getIDType());
                System.out.println("LOGOV Z KOMENTARJEM Izdanovkomplet" + asstype.getIDType() + ":" + findIzdanoVKompletDescription.size());
                for (ASSLog l : findIzdanoVKompletDescription) {
                    assLogFacade.remove(l);
                }

                //počistimo objekte in zapremo modalno okno
                selectedAsset = null;
                trenutniLog = new ASSLog();

            }

            System.out.println("Brišem ASSType:" + asstype.getIDType());
            assTypeFacade.remove(asstype);

            current.executeScript("PF('dlg3').hide()");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            System.out.println("Napaka:" + ex);
        }
    }

    public void izdajaKompletaOpen(ASSType asstype) {
        System.out.println("Sem v izdajaKompletaOpen(), izdajam komplet:" + asstype.getTypeDescription());
        trenutniLog = new ASSLog();
        current.executeScript("PF('dlg3').show()");
    }

    public void razdiranjeKompletaOpen(ASSType asstype) {
        System.out.println("Sem v razdiranjeKompletaOpen(), razdiram komplet:" + asstype.getTypeDescription() + " v kompletu je artiklov:" + asstype.getASSAssetList().size());
        trenutniLog = new ASSLog();
        kompletZaRazdiranje = asstype;
        current.executeScript("PF('dlg4').show()");
    }

    public void natisniKodoOpen(ASSType asstype) {
        System.out.println("Sem v natisniKodoOpen(), za komplet:" + asstype.getTypeDescription());
        trenutniLog = new ASSLog();
        kompletZaTiskanje = asstype;
        prejemnikKompleta = "";
        current.executeScript("PF('dlg5').show()");
    }

    public void izbranaOsebaKiVraca() {
        System.out.println("Sem v izbranaOsebaKiVraca, oseba:" + zaposleniKiVraca.getId());
        //izposojeno = assLogFacade.findWhatIsIzposojeno(zaposleniKiVraca.getId()+"");
        izposojeno = assLogFacade.findWhatIsIzposojenoNoTS(zaposleniKiVraca.getId() + "");
        //System.out.println("VrNjEnO: "+izposojeno.size());
        for (ASS_Q_IzposojenoSredstvo sr : izposojeno) {
            sr.setSredstvo(assAssetFacade.find(sr.getAssetID()));
        }
    }

    public void returnAction() {
        System.out.println("Sem v returnAction. Returning asset: " + selectedAssetToReturn.getSredstvo().getAssetDescription());

        ASSLog log = new ASSLog();
        log.setIDLogAsset(selectedAssetToReturn.getSredstvo());

        System.out.println("Sem v returnAction vmes");

        ASSStatus statusVracilo = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Vrnitev".equals(stat.getStatusDescription())).findAny().orElse(null);
        log.setIDLogStatus(statusVracilo);
        log.setLogComment("vracilo preko obrazca");

        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) selectedAssetToReturn.getSuma());
        log.setLogExternalID(selectedAssetToReturn.getPerson());
        log.setLogQuantity(Math.abs((int) selectedAssetToReturn.getSuma()));
        log.setLogTimestamp(new Date());

        assLogFacade.create(log);
        System.out.println("Sem v returnAction na koncu");
        //zaposleniKiVraca = new VCodeksUsersAktualniZaposleni();

        //izposojeno = new ArrayList();
        izposojeno = assLogFacade.findWhatIsIzposojenoNoTS(zaposleniKiVraca.getId() + "");
        for (ASS_Q_IzposojenoSredstvo sr : izposojeno) {
            sr.setSredstvo(assAssetFacade.find(sr.getAssetID()));
        }

        //Zaprem dialog
        //current.executeScript("PF('dlg2').hide()");
    }
    
    private List<ASSAsset> assetsReturningMultipleLocations;
    public void returnDrugamAction() {
        System.out.println("Sem v returnDrugamAction. Returning asset: " + selectedAssetToReturn.getSredstvo().getAssetDescription());

        
        System.out.println("Sem v returnAction vmes");
        
        //String lokacijastarsa = selectedAssetToReturn.getSredstvo().getIDAssetParent().getIDAssetLocation().getLocationDescription();     
        //System.out.println("Lokacija starša iz objekta:"+lokacijastarsa);
        
        assetsReturningMultipleLocations = assAssetFacade.findAllAlternateLocations(selectedAssetToReturn.getSredstvo());
        
        for(ASSAsset a : assetsReturningMultipleLocations){
            System.out.println("FOUND: "+a.getIDAsset()+" LOCATION:"+a.getIDAssetLocation().getLocationDescription());
        }

        ASSStatus statusVracilo = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Vrnitev".equals(stat.getStatusDescription())).findAny().orElse(null);

        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) selectedAssetToReturn.getSuma());

        System.out.println("Sem v returnDrugamAction na koncu");

        izposojeno = assLogFacade.findWhatIsIzposojenoNoTS(zaposleniKiVraca.getId() + "");
        for (ASS_Q_IzposojenoSredstvo sr : izposojeno) {
            sr.setSredstvo(assAssetFacade.find(sr.getAssetID()));
        }

        current.executeScript("PF('dlg6').show()");
        //Zaprem dialog
        //current.executeScript("PF('dlg2').hide()");
    }
    
    public void returnOnDifferentLocationAction(){
        System.out.println("Sem v returnOnDifferentLocationAction()");
        System.out.println("Vračam asset:"+selectedAssetToReturn.getSredstvo().getIDAsset() +"lokacija originalna:"+selectedAssetToReturn.getSredstvo().getIDAssetLocation().getLocationDescription());
        System.out.println("Vračam na lokacijo:"+selectedLocationToReturn.getLocationDescription());
        
        //vrni po starem - tja od koder je bilo prvotno vzeto
        returnAction();
        
        //USTVARI LOG od koder vzamem
        ASSStatus statusVzamem = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Premik iz lokacije".equals(stat.getStatusDescription())).findAny().orElse(null);
        ASSLog log = new ASSLog();
        log.setIDLogAsset(selectedAssetToReturn.getSredstvo());
        log.setIDLogStatus(statusVzamem);
        log.setLogComment("Premik na drugo lokacijo");
        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) selectedAssetToReturn.getSuma());
        //log.setLogExternalID(selectedAssetToReturn.getPerson());
        log.setLogQuantity(((int) selectedAssetToReturn.getSuma()));
        log.setLogTimestamp(new Date());
        assLogFacade.create(log);
        
        
        
        //USTVARI LOG kamor dam
        ASSStatus statusDam = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Premik na lokacijo".equals(stat.getStatusDescription())).findAny().orElse(null);
        ASSLog log2 = new ASSLog();
        
        //tukaj mora pridit nov asset
        log2.setIDLogAsset(selectedLocationToReturnAsset);
        
        log2.setIDLogStatus(statusDam);
        log2.setLogComment("Prejeto iz druge lokacije");
        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) selectedAssetToReturn.getSuma());
        //log.setLogExternalID(selectedAssetToReturn.getPerson());
        log2.setLogQuantity(Math.abs((int) selectedAssetToReturn.getSuma()));
        log2.setLogTimestamp(new Date());
        assLogFacade.create(log2);
        
        current.executeScript("PF('dlg6').hide()");
        
//        ASSLog log = new ASSLog();
//        log.setIDLogAsset(selectedAssetToReturn.getSredstvo());
//        ASSStatus statusVracilo = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Vrnitev".equals(stat.getStatusDescription())).findAny().orElse(null);
//        log.setIDLogStatus(statusVracilo);
//        log.setLogComment("vracilo preko obrazca");
//        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) selectedAssetToReturn.getSuma());
//        log.setLogExternalID(selectedAssetToReturn.getPerson());
//        log.setLogQuantity(Math.abs((int) selectedAssetToReturn.getSuma()));
//        log.setLogTimestamp(new Date());
//        assLogFacade.create(log);
        
        
        
    }

    public void returnActionRazdiranje() {
        System.out.println("Sem v returnActionRazdiranje. Returning asset: " + selectedAssetRazdiranje.getAssetDescription());
        System.out.println("Sem v returnActionRazdiranje. Returning asset idloglist size: " + selectedAssetRazdiranje.getASSLogList().size());

        ASSLog log = new ASSLog();
        log.setIDLogAsset(selectedAssetRazdiranje.getIDAssetParent());

        System.out.println("Sem v returnAction vmes");

        ASSStatus statusVracilo = assStatusFacade.findAllActiveStatuses().stream().filter(stat -> "Vrnitev".equals(stat.getStatusDescription())).findAny().orElse(null);
        log.setIDLogStatus(statusVracilo);
        log.setLogComment("razdiranje kompleta");

        System.out.println("User ki je prijavljen v Aplikacijo: " + homeController.getCodeksid() + " količina vračila:" + (int) findSumLogQuantity(selectedAssetRazdiranje));
        log.setLogExternalID(homeController.getCodeksid());
        log.setLogQuantity(Math.abs((int) findSumLogQuantity(selectedAssetRazdiranje)));
        log.setLogTimestamp(new Date());

        assLogFacade.create(log);
        System.out.println("Sem v returnAction na koncu");

        for (ASSLog logstar : selectedAssetRazdiranje.getASSLogList()) {
            assLogFacade.remove(logstar);
        }

        assAssetFacade.remove(selectedAssetRazdiranje);

        //zaposleniKiVraca = new VCodeksUsersAktualniZaposleni();       
        //izposojeno = new ArrayList();
//        izposojeno = assLogFacade.findWhatIsIzposojenoNoTS(zaposleniKiVraca.getId() + "");
//        for (ASS_Q_IzposojenoSredstvo sr : izposojeno) {
//            sr.setSredstvo(assAssetFacade.find(sr.getAssetID()));
//        }
        //Zaprem dialog
        //current.executeScript("PF('dlg2').hide()");
    }

    public void brisiKompletRazdiranje() {
        try {
            System.out.println("Sem v brisiKompletRazdiranje");
            
            List<ASSLog> findIzdanoVKompletDescription = assLogFacade.findIzdanoVKompletDescription(kompletZaRazdiranje.getIDType());
            System.out.println("LOGOV Z KOMENTARJEM Izdanovkomplet" + kompletZaRazdiranje.getIDType() + ":" + findIzdanoVKompletDescription.size());
            for (ASSLog l : findIzdanoVKompletDescription) {
                assLogFacade.remove(l);
            }
            
            assTypeFacade.remove(kompletZaRazdiranje);
            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            System.out.println("Napaka:"+ex);
        }
    }

    public int findSumLogQuantity(ASSAsset assAsset) {
        return assLogFacade.findSumLogQuantity(assAsset).intValue();
    }

    public ASSAsset getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(ASSAsset selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    public ASSLog getTrenutniLog() {
        return trenutniLog;
    }

    public void setTrenutniLog(ASSLog trenutniLog) {
        this.trenutniLog = trenutniLog;
    }

    public List<ASSStatus> getMozneOperacije() {
        return mozneOperacije;
    }

    public void setMozneOperacije(List<ASSStatus> mozneOperacije) {
        this.mozneOperacije = mozneOperacije;
    }

    public List<VCodeksUsersAktualniZaposleni> getAktualniZaposleni() {
        return aktualniZaposleni;
    }

    public void setAktualniZaposleni(List<VCodeksUsersAktualniZaposleni> aktualniZaposleni) {
        this.aktualniZaposleni = aktualniZaposleni;
    }

    public VCodeksUsersAktualniZaposleni getZaposleniKiVraca() {
        return zaposleniKiVraca;
    }

    public void setZaposleniKiVraca(VCodeksUsersAktualniZaposleni zaposleniKiVraca) {
        this.zaposleniKiVraca = zaposleniKiVraca;
    }

    public List<ASS_Q_IzposojenoSredstvo> getIzposojeno() {
        return izposojeno;
    }

    public void setIzposojeno(List<ASS_Q_IzposojenoSredstvo> izposojeno) {
        this.izposojeno = izposojeno;
    }

    public ASS_Q_IzposojenoSredstvo getSelectedAssetToReturn() {
        return selectedAssetToReturn;
    }

    public void setSelectedAssetToReturn(ASS_Q_IzposojenoSredstvo selectedAssetToReturn) {
        this.selectedAssetToReturn = selectedAssetToReturn;
    }

    public ASSType getKompletZaRazdiranje() {
        return kompletZaRazdiranje;
    }

    public void setKompletZaRazdiranje(ASSType kompletZaRazdiranje) {
        this.kompletZaRazdiranje = kompletZaRazdiranje;
    }

    public ASSType getKompletZaTiskanje() {
        return kompletZaTiskanje;
    }

    public void setKompletZaTiskanje(ASSType kompletZaTiskanje) {
        this.kompletZaTiskanje = kompletZaTiskanje;
    }

    public String getPrejemnikKompleta() {
        return prejemnikKompleta;
    }

    public void setPrejemnikKompleta(String prejemnikKompleta) {
        this.prejemnikKompleta = prejemnikKompleta;
    }

    public String getVsebinaqrkode() {
        return vsebinaqrkode;
    }

    public void setVsebinaqrkode(String vsebinaqrkode) {
        this.vsebinaqrkode = vsebinaqrkode;
    }

    public ASSAsset getSelectedAssetRazdiranje() {
        return selectedAssetRazdiranje;
    }

    public void setSelectedAssetRazdiranje(ASSAsset selectedAssetRazdiranje) {
        this.selectedAssetRazdiranje = selectedAssetRazdiranje;
    }

    public List<ASSAsset> getAssetsReturningMultipleLocations() {
        return assetsReturningMultipleLocations;
    }

    public void setAssetsReturningMultipleLocations(List<ASSAsset> assetsReturningMultipleLocations) {
        this.assetsReturningMultipleLocations = assetsReturningMultipleLocations;
    }

    public ASSLocation getSelectedLocationToReturn() {
        return selectedLocationToReturn;
    }

    public void setSelectedLocationToReturn(ASSLocation selectedLocationToReturn) {
        this.selectedLocationToReturn = selectedLocationToReturn;
    }

    public ASSAsset getSelectedLocationToReturnAsset() {
        return selectedLocationToReturnAsset;
    }

    public void setSelectedLocationToReturnAsset(ASSAsset selectedLocationToReturnAsset) {
        this.selectedLocationToReturnAsset = selectedLocationToReturnAsset;
    }

    
}
