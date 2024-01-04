/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.entity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class ZebraPrintingBean implements Serializable {

//    private String printdata = "CT~~CD,~CC^~CT~\n"
//            + "^XA\n"
//            + "~TA000\n"
//            + "~JSN\n"
//            + "^LT0\n"
//            + "^MNW\n"
//            + "^MTT\n"
//            + "^PON\n"
//            + "^PMN\n"
//            + "^LH0,0\n"
//            + "^JMA\n"
//            + "^PR8,8\n"
//            + "~SD15\n"
//            + "^JUS\n"
//            + "^LRN\n"
//            + "^CI27\n"
//            + "^PA0,1,1,0\n"
//            + "^XZ\n"
//            + "^XA\n"
//            + "^MMT\n"
//            + "^PW320\n"
//            + "^LL176\n"
//            + "^LS0\n"
//            + "^FT27,42^A0N,28,28^FH\\^CI28^FDKomplet:LL_IMEKOMPLETA^FS^CI27\n"
//            + "^FT27,69^A0N,18,18^FH\\^CI28^FDza:^FS^CI27\n"
//            + "^FT53,69^A0N,18,18^FH\\^CI28^FDLL_PREJEMNIK^FS^CI27\n"
//            + "^FT26,174^BQN,2,4\n"
//            + "^FH\\^FDLA,LL_VSEBINAKODE^FS\n"
//            + "^PQ1,0,1,Y\n"
//            + "^XZ";
    private String printdata = "CT~~CD,~CC^~CT~\n"
            + "^XA\n"
            + "~TA000\n"
            + "~JSN\n"
            + "^LT0\n"
            + "^MNW\n"
            + "^MTT\n"
            + "^PON\n"
            + "^PMN\n"
            + "^LH0,0\n"
            + "^JMA\n"
            + "^PR8,8\n"
            + "~SD15\n"
            + "^JUS\n"
            + "^LRN\n"
            + "^CI27\n"
            + "^PA0,1,1,0\n"
            + "^XZ\n"
            + "^XA\n"
            + "^MMT\n"
            + "^PW320\n"
            + "^LL176\n"
            + "^LS0\n"
            + "^FT204,26^A0N,14,15^FH\\^CI28^FDKomplet: LL_IMEKOMPLETA^FS^CI27\n"
            + "^FT209,49^A0N,13,13^FH\\^CI28^FDLL_PREJEMNIK^FS^CI27\n"
            + "^FT15,175^BQN,2,4\n"
            + "^FH\\^FDLA,LL_VSEBINAKODE^FS\n"
            + "^PQ1,0,1,Y\n"
            + "^XZ";

    public void tiskajEtiketo(String printername, String prejemnik, String imekompleta, String vsebinakode) {
        System.out.println("Sem v tiskajEtiketo");
        System.out.println("Printername:" + printername);
        System.out.println("Prejemnik:" + prejemnik);
        System.out.println("Imekompleta:" + imekompleta);
        System.out.println("Vsebinakode:" + vsebinakode);

        try {
            System.out.println("Sedaj bi moral zaceti tiskati");

            printdata = printdata.replace("LL_IMEKOMPLETA", imekompleta);
            printdata = printdata.replace("LL_VSEBINAKODE", vsebinakode + "" + imekompleta);
            printdata = printdata.replace("LL_PREJEMNIK", prejemnik);

            System.out.println("COMMANDS:" + printdata);

            PrintService psZebra = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

            for (int i = 0; i < services.length; i++) {

                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                sPrinterName = ((PrinterName) attr).getValue();
                System.out.println("Printer name:" + sPrinterName);
                if (sPrinterName.contains(printername)) {
                    psZebra = services[i];
                    break;
                }
            }

            if (psZebra == null) {
                System.out.println("Zebra printer which contains:" + printername + "in it's name is not found.");
                return;
            }
            DocPrintJob job = psZebra.createPrintJob();

            String commands = printdata;

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);
        } catch (PrintException ex) {
            System.out.println("Napaka:" + ex);
        }

    }

}
