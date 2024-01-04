package com.odelowebapp.HR.beans;

/**
 *
 * @author dematjasic
 */
public class JantarPINConvert {

    public static void main(String [] args){
//        System.out.println("Mitja Mastnak:");
//        pretvoriKljucekVJantarObliko("4533032212");
//        System.out.println("Dean:");
//        pretvoriKljucekVJantarObliko("326426579737");
//        System.out.println("Seba:");
//        pretvoriKljucekVJantarObliko("00326426542953");
//        System.out.println("MitjaŠtadler-BOVIAN:");
//        pretvoriKljucekVJantarObliko("4532913825");
//        System.out.println("DEANPRIVAT-BOVIAN:");
//        pretvoriKljucekVJantarObliko("115974215157");
//        System.out.println("Domen TEST:");
//        pretvoriKljucekVJantarObliko("00004570923205");
//        System.out.println("Domen TEST2:");
//        pretvoriKljucekVJantarObliko("4444614565");
//        System.out.println("Znidarko");
//        pretvoriKljucekVJantarObliko("00004570923205");
//        pretvoriKljucekVJantarObliko("00004570923206");
//        pretvoriKljucekVJantarObliko("0001887644");
        
        pretvoriKljucekVJantarObliko("0014425018");
                
    }
    //326426579737 - dean
    //4533032212 - mitja m
    //326426542953 - seba

    public JantarPINConvert() {
    }
    
    
    
    //To metodo pokljičemo od zunaj -> pretvori String iz ključka v format ki je zapisan v Jantar bazi
    public static String pretvoriKljucekVJantarObliko(String fromKeyReader) {
        long prebranolong = Long.parseLong(fromKeyReader);
        System.out.println("Long:" + prebranolong);
        String binaryString = Long.toBinaryString(prebranolong);
        System.out.println("Binary String:" + binaryString);
        String binaryStringNov = "000000000000000000000000"+binaryString;
        String odrezanBinary = binaryStringNov.substring(binaryStringNov.length() - 24);
        System.out.println("odrezan:" + odrezanBinary);
        String prviNiz = odrezanBinary.substring(0, 8);
        String drugiNiz = odrezanBinary.substring(8, 16);
        String tretjiNiz = odrezanBinary.substring(16, 24);
        String rotiranBinary = rotateString(prviNiz) + rotateString(drugiNiz) + rotateString(tretjiNiz);
        long novaVrednost = Long.parseLong(rotiranBinary, 2);
        System.out.println("JANTAR KODA UPORABNIKA:"+novaVrednost);
        return novaVrednost+"";
    }
    
    public String pretvoriKljucekVJantarOblikoPublic(String fromKeyReader) {
        long prebranolong = Long.parseLong(fromKeyReader);
        //System.out.println("Long:" + prebranolong);
        String binaryString = Long.toBinaryString(prebranolong);
        //System.out.println("Binary String:" + binaryString);
        String binaryStringNov = "000000000000000000000000"+binaryString;
        String odrezanBinary = binaryStringNov.substring(binaryStringNov.length() - 24);
        //System.out.println("odrezan:" + odrezanBinary);
        String prviNiz = odrezanBinary.substring(0, 8);
        String drugiNiz = odrezanBinary.substring(8, 16);
        String tretjiNiz = odrezanBinary.substring(16, 24);
        String rotiranBinary = rotateString(prviNiz) + rotateString(drugiNiz) + rotateString(tretjiNiz);
        long novaVrednost = Long.parseLong(rotiranBinary, 2);
        //System.out.println("JANTAR KODA UPORABNIKA:"+novaVrednost);
        return novaVrednost+"";
    }

    public static String rotateString(String input) {
        String output = "";
        //System.out.println("Input length:" + input.length());
        for (int i = input.length(); i > 0; i--) {
            output = output + input.charAt(i - 1);
        }
        return output;
    }
    
    
    
//    //TEST
//    public static String pretvoriKljucekVJantarObliko2(String fromKeyReader) {
//        //long prebranolong = Long.parseLong(fromKeyReader);
//        System.out.println("String:" + fromKeyReader);
//        String binaryString = fromKeyReader.
//        System.out.println("Binary String:" + binaryString);
//        String odrezanBinary = binaryString.substring(binaryString.length() - 24);
//        System.out.println("odrezan:" + odrezanBinary);
//        String prviNiz = odrezanBinary.substring(0, 8);
//        String drugiNiz = odrezanBinary.substring(8, 16);
//        String tretjiNiz = odrezanBinary.substring(16, 24);
//        String rotiranBinary = rotateString(prviNiz) + rotateString(drugiNiz) + rotateString(tretjiNiz);
//        long novaVrednost = Long.parseLong(rotiranBinary, 2);
//        System.out.println("JANTAR KODA UPORABNIKA:"+novaVrednost);
//        return novaVrednost+"";
//    }
    
    
    
    
}
