package com.odelowebapp.student.evidenca;

import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.student.evidenca.entity.ASSLocation;
import com.odelowebapp.student.evidenca.entity.ASSLog;
import com.odelowebapp.student.evidenca.entity.ASSStatus;
import com.odelowebapp.student.evidenca.entity.ASSType;
import com.odelowebapp.student.evidenca.facade.ASSAssetFacade;
import com.odelowebapp.student.evidenca.facade.ASSLocationFacade;
import com.odelowebapp.student.evidenca.facade.ASSLogFacade;
import com.odelowebapp.student.evidenca.facade.ASSStatusFacade;
import com.odelowebapp.student.evidenca.facade.ASSTypeFacade;
import java.io.IOException;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

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
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.PrimeFaces;

@Named("EventsView")
@ViewScoped
public class EventsView implements Serializable {

    private TreeNode root;
    private TreeNode rootLocation;

    private TreeNode selectedNode;
    private TreeNode selectedNodeLocation;
    private Set<String> lokacijezafilter = new TreeSet<>();

    //Edit form selection
    private ASSAsset selected;
    //Tree menu selection
    private ASSType selectedAssType = null;
    //List table view display
    private List<ASSAsset> assAssetTableList = new ArrayList();

    private List<ASSAsset> kosaricaNabrano = new ArrayList();

    private ASSLocation selectedAssLocation = null;

    @EJB
    private ASSAssetFacade assAssetFacade;
    @EJB
    private ASSTypeFacade assTypeFacade;
    @EJB
    private ASSLogFacade assLogFacade;
    @EJB
    private ASSStatusFacade assStatusFacade;
    @EJB
    private ASSLocationFacade assLocationFacade;

    @Inject
    private TreeNodeService service;

    private String kodaKompletaQRparameter;

    PrimeFaces current;

    @PostConstruct
    public void init() {

        kodaKompletaQRparameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("QRparameter");
        System.out.println("QR parameter:" + kodaKompletaQRparameter);

        if (kodaKompletaQRparameter != null) {
            System.out.println("je parameter");
            int kodakompleta = Integer.parseInt(kodaKompletaQRparameter);
            prikaziPodatkeIzQRkode(kodakompleta);

        } else {
            System.out.println("ni parametra");
        }

        current = PrimeFaces.current();
        root = service.createAssTypeCategory();
        rootLocation = service.createLocationTypeCategory();
    }

    public void izdajaKompletaOpen() {
        System.out.println("Sem v izdajaKompletaOpen(), izdajam komplet:");
        current.executeScript("PF('dlg3').show()");
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getRootLocation() {
        return rootLocation;
    }

    public TreeNode getSelectedNodeLocation() {
        return selectedNodeLocation;
    }

    public void setSelectedNodeLocation(TreeNode selectedNodeLocation) {
        this.selectedNodeLocation = selectedNodeLocation;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        System.out.println("Sem v onNodeExpand");
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        System.out.println("Sem v onNodeCollapse");
    }

    public void onNodeLocationExpand(NodeExpandEvent event) {
        System.out.println("Sem v onNodeLocationExpand");
    }

    public void onNodeLocationCollapse(NodeCollapseEvent event) {
        System.out.println("Sem v onNodeLocationCollapse");
    }

    public Set<String> getLokacijezafilter() {
        return lokacijezafilter;
    }

    public void setLokacijezafilter(Set<String> lokacijezafilter) {
        this.lokacijezafilter = lokacijezafilter;
    }

    public void onNodeSelect(NodeSelectEvent event) {

        if (event.getTreeNode().getData().getClass().equals(ASSType.class)) {
            selectedAssType = (ASSType) event.getTreeNode().getData();
            System.out.println("Izbran asset:" + selectedAssType.getIDType());

            //Load data in table view
            //assAssetTableList = getAssAssetFacade().findAllOfType(selectedAssType);
            assAssetTableList.clear();
            assAssetTableList = getAssAssetFacade().findAllOfType(selectedAssType);
            //for testing all children
            List<ASSType> allc = selectedAssType.getAllChildren();

            for (ASSType child : allc) {
                System.out.println("Child type ID: " + child.getIDType());
                assAssetTableList.addAll(getAssAssetFacade().findAllOfType(child));
            }
            //lokacijezafilter = assAssetTableList.stream().flatMap(e -> e.getIDAssetLocation().getASSLocationList().stream()).collect(Collectors.toList());
            lokacijezafilter = assAssetTableList.stream().map(asset -> asset.getIDAssetLocation().getLocationDescription()).collect(Collectors.toSet());
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Category", selectedAssType.getTypeDescription());
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void prikaziPodatkeIzQRkode(int asstypeid) {
        selectedAssType = assTypeFacade.find(asstypeid);
        System.out.println("Izbran asset:" + selectedAssType.getIDType());

        //Load data in table view
        //assAssetTableList = getAssAssetFacade().findAllOfType(selectedAssType);
        assAssetTableList.clear();
        assAssetTableList = getAssAssetFacade().findAllOfType(selectedAssType);
        //for testing all children
        List<ASSType> allc = selectedAssType.getAllChildren();

        for (ASSType child : allc) {
            System.out.println("Child type ID: " + child.getIDType());
            assAssetTableList.addAll(getAssAssetFacade().findAllOfType(child));
        }
        //lokacijezafilter = assAssetTableList.stream().flatMap(e -> e.getIDAssetLocation().getASSLocationList().stream()).collect(Collectors.toList());
        lokacijezafilter = assAssetTableList.stream().map(asset -> asset.getIDAssetLocation().getLocationDescription()).collect(Collectors.toSet());
    }

    public void onNodeLocationSelect(NodeSelectEvent event) {

        if (event.getTreeNode().getData().getClass().equals(ASSLocation.class)) {
            selectedAssLocation = (ASSLocation) event.getTreeNode().getData();
            System.out.println("Izbran location:" + selectedAssLocation.getIDLocation());

            //Load data in table view
            //assAssetTableList = getAssAssetFacade().findAllOfType(selectedAssType);
            assAssetTableList.clear();
            assAssetTableList = getAssAssetFacade().findAllOfLocation(selectedAssLocation);
            //for testing all children
            List<ASSLocation> allc = selectedAssLocation.getAllChildren();

            for (ASSLocation child : allc) {
                System.out.println("Child type ID: " + child.getIDLocation());
                assAssetTableList.addAll(getAssAssetFacade().findAllOfLocation(child));
            }
            //lokacijezafilter = assAssetTableList.stream().flatMap(e -> e.getIDAssetLocation().getASSLocationList().stream()).collect(Collectors.toList());
            lokacijezafilter = assAssetTableList.stream().map(asset -> asset.getIDAssetLocation().getLocationDescription()).collect(Collectors.toSet());
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Location", selectedAssLocation.getLocationDescription());
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeLocationUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void pripraviKompletIzKosarice() {
        try {
            System.out.println("Sem v pripraviKompletIzKosarice(), v košarici je artiklov:" + kosaricaNabrano.size());

            //pripravimo novo kategorijo pod kompletom
            ASSType kompletStars = assTypeFacade.find(40);
            ASSType novSinKompleta = new ASSType();
            novSinKompleta.setIDTypeParent(kompletStars);
            novSinKompleta.setTypeDescription("Komplet");
            assTypeFacade.create(novSinKompleta);
            ASSType poiskanNovSinKompleta = assTypeFacade.find(novSinKompleta.getIDType());
            poiskanNovSinKompleta.setTypeDescription(poiskanNovSinKompleta.getTypeDescription() + " " + poiskanNovSinKompleta.getIDType());
            poiskanNovSinKompleta.setTypeDisable(false);
            poiskanNovSinKompleta.setTypeReturn(false);
            assTypeFacade.edit(poiskanNovSinKompleta);

            for (ASSAsset asset : kosaricaNabrano) {
                System.out.println("FOR ZANKA, obdelujem asset:" + asset.getIDAsset());

                ASSLog logizdanovkomplet = new ASSLog();
                logizdanovkomplet.setIDLogAsset(asset);
                logizdanovkomplet.setLogTimestamp(new Date());
                logizdanovkomplet.setLogComment("Izdano v komplet:" + poiskanNovSinKompleta.getIDType());
                ASSStatus statuskomplet = assStatusFacade.find(7);
                logizdanovkomplet.setIDLogStatus(statuskomplet);
                logizdanovkomplet.setLogQuantity(statuskomplet.getStatusFactor() * asset.getKolicinaKosarica());
                assLogFacade.create(logizdanovkomplet);

                ASSAsset newasset = (ASSAsset) SerializationUtils.clone(asset);
                System.out.println("NEWASSETID pred nulliranjem idja:" + newasset.getIDAsset());
                newasset.setIDAsset(null);
                ASSLocation lokacijazakomplete = assLocationFacade.find(9);
                newasset.setIDAssetLocation(lokacijazakomplete);
                newasset.setIDAssetType(poiskanNovSinKompleta);
                newasset.setASSLogList(null);
                System.out.println("Nastavljam starša:" + asset.getIDAsset() + " na nov kloniran asset");
                newasset.setIDAssetParent(asset); //dodano da najdemo od kod izvira
                assAssetFacade.create(newasset);

                ASSLog prejovkomplet = new ASSLog();
                prejovkomplet.setIDLogAsset(newasset);
                prejovkomplet.setLogTimestamp(new Date());
                prejovkomplet.setLogComment("Prejeto v komplet:" + poiskanNovSinKompleta.getIDType());
                ASSStatus statuskomplet2 = assStatusFacade.find(8);
                prejovkomplet.setIDLogStatus(statuskomplet2);
                prejovkomplet.setLogQuantity(statuskomplet2.getStatusFactor() * asset.getKolicinaKosarica());
                assLogFacade.create(prejovkomplet);

            }
            //to je uporabno če mi uspe ugotoviti kako skriti košarico iz backingbeana, do takrat refresham page
//        root = service.createAssTypeCategory();
//        rootLocation = service.createLocationTypeCategory();
//        kosaricaNabrano.clear();

            assTypeFacade.evictCache();

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            System.out.println("Napaka:"+ex);
        }

    }

    public void dodajVKosarico(ASSAsset asset) {
        System.out.println("Sem v dodajVKosarico(), dodajam asset:" + asset.getAssetDescription());
        asset.setKolicinaKosarica(1);
        kosaricaNabrano.add(asset);
    }

    public void setService(TreeNodeService service) {
        this.service = service;
    }

    public ASSAsset getSelected() {
        return selected;
    }

    public void setSelected(ASSAsset selected) {
        this.selected = selected;
    }

    private ASSAssetFacade getAssAssetFacade() {
        return assAssetFacade;
    }

    public void updateAssAsset() {
        getAssAssetFacade().updateAssAsset(selected);
    }

    public void markAssAssetDeleted() {
        getAssAssetFacade().markAssAssetDeleted(selected);
    }

    public ASSType getSelectedAssType() {
        return selectedAssType;
    }

    public void setSelectedAssType(ASSType assType) {
        this.selectedAssType = assType;
    }

    private ASSTypeFacade getAssTypeFacade() {
        return assTypeFacade;
    }

    public List<ASSType> getAllParents() {
        return getAssTypeFacade().findAllActiveParents();
    }

    public List<ASSAsset> getAssAssetTableList() {
        return assAssetTableList;
    }

    public void setAssAssetTableList(List<ASSAsset> list) {
        this.assAssetTableList = list;
    }

    private ASSLogFacade getAssLogFacade() {
        return assLogFacade;
    }

    public int findSumLogQuantity(ASSAsset assAsset) {
        return getAssLogFacade().findSumLogQuantity(assAsset).intValue();
    }

    public List<ASSAsset> getKosaricaNabrano() {
        return kosaricaNabrano;
    }

    public void setKosaricaNabrano(List<ASSAsset> kosaricaNabrano) {
        this.kosaricaNabrano = kosaricaNabrano;
    }

}
