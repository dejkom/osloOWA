/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import com.odelowebapp.portal.beans.FileAttachment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.file.UploadedFile;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dean
 */
@Entity
@Table(name = "HRCourseOffering")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourseOffering.findAll", query = "SELECT h FROM HRCourseOffering h"),
    @NamedQuery(name = "HRCourseOffering.findByCourseOfferingID", query = "SELECT h FROM HRCourseOffering h WHERE h.courseOfferingID = :courseOfferingID"),
    @NamedQuery(name = "HRCourseOffering.findByCreator", query = "SELECT h FROM HRCourseOffering h WHERE h.creator = :creator"),
    @NamedQuery(name = "HRCourseOffering.findByCourseDate", query = "SELECT h FROM HRCourseOffering h WHERE h.courseDate = :courseDate"),
    @NamedQuery(name = "HRCourseOffering.findByMeetingroomID", query = "SELECT h FROM HRCourseOffering h WHERE h.meetingroomID = :meetingroomID"),
    @NamedQuery(name = "HRCourseOffering.findByActive", query = "SELECT h FROM HRCourseOffering h WHERE h.active = :active"),
    @NamedQuery(name = "HRCourseOffering.findByDateCreated", query = "SELECT h FROM HRCourseOffering h WHERE h.dateCreated = :dateCreated")})
public class HRCourseOffering implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseOfferingID")
    private Integer courseOfferingID;
    @Column(name = "creator")
    private Integer creator;
    @Column(name = "courseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date courseDate;
    @Column(name = "meetingroomID")
    private Integer meetingroomID;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Size(max = 2147483647)
    @Column(name = "meetingroomString")
    private String meetingroomString;
    @Size(max = 2147483647)
    @Column(name = "komentarIzvedbe")
    private String komentarIzvedbe;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne
    private HRCourse courseID;
    @JoinColumn(name = "instructorID", referencedColumnName = "instructorID")
    @ManyToOne
    private HRCourseInstructor instructorID;
    @OneToMany(mappedBy = "courseOfferingID")
    private List<HRCourseAttendance> hRCourseAttendanceList;

    @Column(name = "accessPIN")
    private Integer accessPIN;

    @Size(max = 2147483647)
    @Column(name = "odredbaIzmena")
    private String odredbaIzmena;
    @Column(name = "odredbaDatumOd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odredbaDatumOd;
    @Column(name = "odredbaDatumDo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odredbaDatumDo;

    @Column(name = "dateUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Column(name = "onlineCourseOffering")
    private Boolean onlineCourseOffering;

    @Size(max = 2147483647)
    @Column(name = "actualInstructorPerforming")
    private String actualInstructorPerforming;

    @Transient
    private UploadedFile file;
    @Transient
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();
    @Transient
    private List<File> filesfromdisc;
    @Transient
    private List<FileAttachment> fileattachments = new ArrayList();
    
    @Transient
    private String creatorFirstnameLastname;
    
    @ManyToOne   
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false, nullable = true)
    private VADCODEKSUsers creatorObjekt;

    public HRCourseOffering() {
    }

    @PostPersist
    public void postpersist() {
        System.out.println("Sem v postpersist");
        System.out.println("CourseOfferingID:" + courseOfferingID);
        saveAttachmentsToDirectory(courseOfferingID);
        
    }

    @PostUpdate
    public void postupdate() {
        System.out.println("Sem v postupdate");
        System.out.println("CourseOfferingID:" + courseOfferingID);
        saveAttachmentsToDirectory(courseOfferingID);
    }

    public void saveAttachmentsToDirectory(int courseOfferingID) {
        System.out.println("Sem v saveAttachmentsToDirectory courseOfferingID:" + courseOfferingID + " stevilo:" + files.size());
        System.out.println("Za tem bom iteriral čez priloge ki jih moram shraniti na disk...");
        for (PrimefacesUploadedFile f : files) {
            try {

                if (f.isAllreadySaved()) {

                } else {
                    System.out.println("V direktorij moram shraniti:" + f.getFileName());
                    Path folder = Paths.get("C:\\WebAppFiles\\OfferingAttachments\\" + courseOfferingID + "");
                    Files.createDirectories(folder);
                    folder = Paths.get("C:\\WebAppFiles\\OfferingAttachments\\" + courseOfferingID + "");

                    String filename = FilenameUtils.getBaseName(f.getFileName());
                    String extension = FilenameUtils.getExtension(f.getFileName());
                    Path ffile = Files.createTempFile(folder, filename + "-", "." + extension);

                    try (InputStream input = f.getInputStream()) {
                        Files.copy(input, ffile, StandardCopyOption.REPLACE_EXISTING);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(HRCourse.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Uploaded file successfully saved in " + file);
        }
    }

    public void findAttachmentsForCourse(Integer courseOfferingID) {
//        Path folder = Paths.get("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");
//        File directory = new File("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");
//        File[] directoryListing = directory.listFiles();        
//        for(File child:directoryListing){
//            System.out.println("F:"+child.getAbsolutePath());
//        }       
        fileattachments.clear();

        files.clear();

        try {
            filesfromdisc = (List<File>) FileUtils.listFiles(new File("C:\\WebAppFiles\\OfferingAttachments\\" + courseOfferingID + ""), null, true);
            System.out.println("filesfromdisc size:" + filesfromdisc.size());

            for (File f : filesfromdisc) {
                System.out.println("F:" + f.getAbsolutePath());
                String abspath = f.getAbsolutePath();
                String[] split = abspath.split("WebAppFiles");
                System.out.println("F2:" + split[1]);
                FileAttachment fa = new FileAttachment();
                fa.setItemFileAttachmentSrc(split[1]);
                fa.setTitle(f.getName());

                PrimefacesUploadedFile uf = new PrimefacesUploadedFile(Files.readAllBytes(f.toPath()), f.getName(), Files.probeContentType(f.toPath()), true);
                files.add(uf);

                try {
                    BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                    FileTime fileTime = attr.creationTime();
                    Date dateofc = new Date(fileTime.toMillis());
                    fa.setCreationdate(dateofc);
                } catch (IOException ex) {
                    // handle exception
                }

                fileattachments.add(fa);

            }
        } catch (Exception e) {
        }
    }

    public HRCourseOffering(Integer courseOfferingID) {
        this.courseOfferingID = courseOfferingID;
    }

    public Integer getCourseOfferingID() {
        return courseOfferingID;
    }

    public void setCourseOfferingID(Integer courseOfferingID) {
        this.courseOfferingID = courseOfferingID;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public Integer getMeetingroomID() {
        return meetingroomID;
    }

    public void setMeetingroomID(Integer meetingroomID) {
        this.meetingroomID = meetingroomID;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public HRCourse getCourseID() {
        return courseID;
    }

    public void setCourseID(HRCourse courseID) {
        this.courseID = courseID;
    }

    public HRCourseInstructor getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(HRCourseInstructor instructorID) {
        this.instructorID = instructorID;
    }

    public String getMeetingroomString() {
        return meetingroomString;
    }

    public void setMeetingroomString(String meetingroomString) {
        this.meetingroomString = meetingroomString;
    }

    public String getKomentarIzvedbe() {
        return komentarIzvedbe;
    }

    public void setKomentarIzvedbe(String komentarIzvedbe) {
        this.komentarIzvedbe = komentarIzvedbe;
    }

    public Integer getAccessPIN() {
        return accessPIN;
    }

    public void setAccessPIN(Integer accessPIN) {
        this.accessPIN = accessPIN;
    }

    public String getOdredbaIzmena() {
        return odredbaIzmena;
    }

    public void setOdredbaIzmena(String odredbaIzmena) {
        this.odredbaIzmena = odredbaIzmena;
    }

    public Date getOdredbaDatumOd() {
        return odredbaDatumOd;
    }

    public void setOdredbaDatumOd(Date odredbaDatumOd) {
        this.odredbaDatumOd = odredbaDatumOd;
    }

    public Date getOdredbaDatumDo() {
        return odredbaDatumDo;
    }

    public void setOdredbaDatumDo(Date odredbaDatumDo) {
        this.odredbaDatumDo = odredbaDatumDo;
    }

    public String getCourseDateString() {
        return (new SimpleDateFormat("dd.MM.yyyy HH:mm")).format(this.courseDate);
    }

    //@XmlTransient
    public List<HRCourseAttendance> getHRCourseAttendanceList() {
        return hRCourseAttendanceList;
    }

    public void setHRCourseAttendanceList(List<HRCourseAttendance> hRCourseAttendanceList) {
        this.hRCourseAttendanceList = hRCourseAttendanceList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<PrimefacesUploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<PrimefacesUploadedFile> files) {
        this.files = files;
    }

    public List<File> getFilesfromdisc() {
        return filesfromdisc;
    }

    public void setFilesfromdisc(List<File> filesfromdisc) {
        this.filesfromdisc = filesfromdisc;
    }

    public List<FileAttachment> getFileattachments() {
        return fileattachments;
    }

    public void setFileattachments(List<FileAttachment> fileattachments) {
        this.fileattachments = fileattachments;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Boolean getOnlineCourseOffering() {
        return onlineCourseOffering;
    }

    public void setOnlineCourseOffering(Boolean onlineCourseOffering) {
        this.onlineCourseOffering = onlineCourseOffering;
    }

    public String getActualInstructorPerforming() {
        return actualInstructorPerforming;
    }

    public void setActualInstructorPerforming(String actualInstructorPerforming) {
        this.actualInstructorPerforming = actualInstructorPerforming;
    }

    public String getCreatorFirstnameLastname() {
        return creatorFirstnameLastname;
    }

    public void setCreatorFirstnameLastname(String creatorFirstnameLastname) {
        this.creatorFirstnameLastname = creatorFirstnameLastname;
    }

    public VADCODEKSUsers getCreatorObjekt() {
        return creatorObjekt;
    }

    public void setCreatorObjekt(VADCODEKSUsers creatorObjekt) {
        this.creatorObjekt = creatorObjekt;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseOfferingID != null ? courseOfferingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourseOffering)) {
            return false;
        }
        HRCourseOffering other = (HRCourseOffering) object;
        if ((this.courseOfferingID == null && other.courseOfferingID != null) || (this.courseOfferingID != null && !this.courseOfferingID.equals(other.courseOfferingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourseOffering[ courseOfferingID=" + courseOfferingID + " ]";
    }

}
