/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.portal.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author dematjasic
 */
public class FileAttachment implements Serializable {

    private String itemFileAttachmentSrc;
    private String thumbnailImageSrc;
    private String alt;
    private String title;
    private Date creationdate;

    public FileAttachment() {
    }

    public FileAttachment(String itemFileAttachmentSrc, String thumbnailImageSrc, String alt, String title) {
        this.itemFileAttachmentSrc = itemFileAttachmentSrc;
        this.thumbnailImageSrc = thumbnailImageSrc;
        this.alt = alt;
        this.title = title;
    }

    public String getItemFileAttachmentSrc() {
        return itemFileAttachmentSrc;
    }

    public void setItemFileAttachmentSrc(String itemFileAttachmentSrc) {
        this.itemFileAttachmentSrc = itemFileAttachmentSrc;
    }

    public String getThumbnailImageSrc() {
        //return thumbnailImageSrc;
        String fext = itemFileAttachmentSrc.substring(itemFileAttachmentSrc.lastIndexOf(".") + 1);
        System.out.println("Koncnica:"+fext);
        return fext.toLowerCase();
    }
    
    public String getFileName(){
        return itemFileAttachmentSrc.substring(0, itemFileAttachmentSrc.lastIndexOf("/"));
    }

    public void setThumbnailImageSrc(String thumbnailImageSrc) {
        this.thumbnailImageSrc = thumbnailImageSrc;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    
}
