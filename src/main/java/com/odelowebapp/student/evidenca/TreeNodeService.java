package com.odelowebapp.student.evidenca;

import com.odelowebapp.student.evidenca.entity.ASSAsset;
import com.odelowebapp.student.evidenca.entity.ASSLocation;
import com.odelowebapp.student.evidenca.entity.ASSType;
import com.odelowebapp.student.evidenca.facade.ASSAssetFacade;
import com.odelowebapp.student.evidenca.facade.ASSLocationFacade;
import com.odelowebapp.student.evidenca.facade.ASSTypeFacade;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class TreeNodeService {

    @EJB
    private ASSTypeFacade assTypeFacade;
    private ASSTypeFacade getTypeFacade() {
        return assTypeFacade;
    }

    @EJB
    private ASSAssetFacade assAssetFacade;
    private ASSAssetFacade getAssAssetFacade() {
        return assAssetFacade;
    }
    
    @EJB
    private ASSLocationFacade locationFacade;
    public ASSLocationFacade getLocationFacade() {
        return locationFacade;
    }
    

    public TreeNode<ASSType> createAssTypeCategory(){
        TreeNode<ASSType> root =  new DefaultTreeNode<>(new ASSType(), null);

        //Asset types (category)
        List<ASSType> sez = getTypeFacade().findAllActiveParents();

        for (ASSType assType : sez) {
            TreeNode typeNode = new DefaultTreeNode(assType, root);

            List<ASSType> assetTypeList = getTypeFacade().findAllActiveChildren(assType);

            try{
                for (ASSType assetType : assetTypeList) {
                    TreeNode assetNode = new DefaultTreeNode("assetType", assetType, typeNode);
                    //dean
                    List<ASSType> assetTypeList2 = getTypeFacade().findAllActiveChildren(assetType);
                    for (ASSType assetType2 : assetTypeList2) {
                        TreeNode assetNode2 = new DefaultTreeNode("assetType", assetType2, assetNode);
                        
                        //Dean novo gnezdenje
                        List<ASSType> assetTypeList3 = getTypeFacade().findAllActiveChildren(assetType2);
                        for (ASSType assetType3 : assetTypeList3) {
                            TreeNode assetNode3 = new DefaultTreeNode("assetType", assetType3, assetNode2);
                        }
                        
                    }
                    
                }
            }
            catch (NullPointerException e){
                System.out.println("No children for type: " + assType.getIDType() + "; " + assType.getTypeDescription());
            }

        }

        return root;
    }
    
    public TreeNode<ASSLocation> createLocationTypeCategory(){
        TreeNode<ASSLocation> root =  new DefaultTreeNode<>(new ASSLocation(), null);
        
        List<ASSLocation> sez = getLocationFacade().findAllActiveParents();

        for (ASSLocation assLocation : sez) {
            TreeNode typeNode = new DefaultTreeNode(assLocation, root);

            List<ASSLocation> assetLocationList = getLocationFacade().findAllActiveChildren(assLocation);

            try{
                for (ASSLocation assetLoc : assetLocationList) {
                    TreeNode assetNode = new DefaultTreeNode("assetLocation", assetLoc, typeNode);
                    //dean
                    List<ASSLocation> assetLocationList2 = getLocationFacade().findAllActiveChildren(assetLoc);
                    for (ASSLocation assetLoc2 : assetLocationList2) {
                        TreeNode assetNode2 = new DefaultTreeNode("assetLocation", assetLoc2, assetNode);
                        
                        //Dean novo gnezdenje
                        List<ASSLocation> assetLocationList3 = getLocationFacade().findAllActiveChildren(assetLoc2);
                        for (ASSLocation assetLoc3 : assetLocationList3) {
                            TreeNode assetNode3 = new DefaultTreeNode("assetLocation", assetLoc3, assetNode2);
                        }
                        
                    }
                    
                }
            }
            catch (NullPointerException e){
                System.out.println("No children for type: " + assLocation.getIDLocation() + "; " + assLocation.getLocationDescription());
            }

        }
        return root;
    }
    
    
}
