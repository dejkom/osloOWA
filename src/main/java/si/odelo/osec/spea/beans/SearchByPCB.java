/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.odelo.osec.spea.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class SearchByPCB implements Serializable{
    
    private String PCBin;
    private String extractedFromPCB;
    private String info;
    
    @PostConstruct
    public void init() {
        System.out.println("Sem v SearchByPCB Beanu");
    }
    
    public void extractFromPCB(){
        System.out.println("Sem v extractFromPCB");
        info="";
        try{
            extractedFromPCB = PCBin.substring(10, 25);
        }catch(Exception e){
            extractedFromPCB = "";
            info = "Ne morem opraviti substringa(10,25)";
        }
    }

    public String getPCBin() {
        return PCBin;
    }

    public void setPCBin(String PCBin) {
        this.PCBin = PCBin;
    }

    public String getExtractedFromPCB() {
        return extractedFromPCB;
    }

    public void setExtractedFromPCB(String extractedFromPCB) {
        this.extractedFromPCB = extractedFromPCB;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
    
}
