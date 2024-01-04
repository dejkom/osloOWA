/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author dematjasic
 * Ta Razred ima dodano polje allreadySaved, uporablja se pri HR izobraževanjih (datoteke preberemo iz mape in jih poflagiramo z allreadysaved=true, ko uploadamo nove damo allreadysaved=false
 * To upoštevamo pri shranjevanju, da ne nalagamo istih datotek večkrat)
 */
public class PrimefacesUploadedFile implements UploadedFile {

    private final byte [] data;

    private final String filename;

    private final String contentType;
    
    private final boolean allreadySaved;

    public PrimefacesUploadedFile(byte [] data, String filename, String contentType, boolean allreadySaved) {
        this.data = data;
        this.filename = filename;
        this.contentType = contentType;
        this.allreadySaved = allreadySaved;
    }

    public boolean isAllreadySaved() {
        return allreadySaved;
    }

    public PrimefacesUploadedFile(UploadedFile file) {
        this.data = file.getContent();
        this.filename = file.getFileName();
        this.contentType = file.getContentType();
        this.allreadySaved = false;
    }
    
    

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

    @Override
    public byte[] getContent() {
        return data;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public long getSize() {
        return data.length;
    }

    @Override
    public void write(String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        }
    }

    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
