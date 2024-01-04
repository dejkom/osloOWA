/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.entity;

import com.odelowebapp.portal.beans.FileAttachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.file.UploadedFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dean
 */
@Entity
@Table(name = "HRCourse")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HRCourse.findAll", query = "SELECT h FROM HRCourse h"),
    @NamedQuery(name = "HRCourse.findByCourseID", query = "SELECT h FROM HRCourse h WHERE h.courseID = :courseID"),
    @NamedQuery(name = "HRCourse.findByCourseTitle", query = "SELECT h FROM HRCourse h WHERE h.courseTitle = :courseTitle"),
    @NamedQuery(name = "HRCourse.findByCourseDescription", query = "SELECT h FROM HRCourse h WHERE h.courseDescription = :courseDescription"),
    @NamedQuery(name = "HRCourse.findByDuration", query = "SELECT h FROM HRCourse h WHERE h.duration = :duration"),
    @NamedQuery(name = "HRCourse.findByPrimaryInstructors", query = "SELECT h FROM HRCourse h WHERE h.primaryInstructors = :primaryInstructors")})
public class HRCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseID")
    private Integer courseID;
    @Size(max = 100)
    @Column(name = "courseTitle")
    private String courseTitle;
    @Size(max = 2147483647)
    @Column(name = "courseDescription")
    private String courseDescription;
    @Column(name = "duration")
    private Integer duration;
    @Size(max = 2147483647)
    @Column(name = "primaryInstructors")
    private String primaryInstructors;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "courseID")
    private List<HRCourseOffering> hRCourseOfferingList;
    @OneToMany(mappedBy = "courseID")
    private List<HRJobHRCourse> hRJobHRCourseList;
    @OneToMany(mappedBy = "courseID")
    private List<HRCourseHRCourseInstructor> hRCourseHRCourseInstructorList;
    @JoinColumn(name = "courseTypeID", referencedColumnName = "courseTypeID")
    @ManyToOne
    private HRCourseType courseTypeID;
    @Size(max = 2147483647)
    @Column(name = "courseNotes")
    private String courseNotes;
    @Column(name = "lastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    
    @Column(name = "selfRegistration")
    private boolean selfRegistration;
    @Column(name = "educationAssesmentRequired")
    private boolean educationAssesmentRequired;
    @Column(name = "performanceEvaluationRequired")
    private boolean performanceEvaluationRequired;
    
    @Column(name = "qsTopic")
    private boolean qsTopic;

//    @PostLoad
//    public void postload() {
//        System.out.println("Sem v PostLoad, ne delam ničesar");
//        try {
//            //findAttachmentsForCourse(courseID);
//        } catch (Exception e) {
//        }
//    }
//    

    @Transient
    private UploadedFile file;
    @Transient
    private List<PrimefacesUploadedFile> files = new ArrayList<PrimefacesUploadedFile>();
    @Transient
    private List<File> filesfromdisc;
    @Transient
    private List<FileAttachment> fileattachments = new ArrayList();

    @PostPersist
    public void postpersist() {
        System.out.println("Sem v postpersist");
        System.out.println("CourseID:" + courseID);
        saveAttachmentsToDirectory(courseID);
    }

    @PostUpdate
    public void postupdate() {
        System.out.println("Sem v postupdate");
        System.out.println("CourseID:" + courseID);
        saveAttachmentsToDirectory(courseID);
    }

    public void saveAttachmentsToDirectory(int courseID) {
        System.out.println("Sem v saveAttachmentsToDirectory courseID:" + courseID + " stevilo:" + files.size());
        System.out.println("Za tem bom iteriral čez priloge ki jih moram shraniti na disk...");
        for (PrimefacesUploadedFile f : files) {
            try {
                
                if(f.isAllreadySaved()){
                    
                }else{
                    System.out.println("V direktorij moram shraniti:" + f.getFileName());
                Path folder = Paths.get("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");
                Files.createDirectories(folder);
                folder = Paths.get("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");

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

    public void findAttachmentsForCourse(Integer courseID) {
//        Path folder = Paths.get("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");
//        File directory = new File("C:\\WebAppFiles\\CourseAttachments\\" + courseID + "");
//        File[] directoryListing = directory.listFiles();        
//        for(File child:directoryListing){
//            System.out.println("F:"+child.getAbsolutePath());
//        }       
        fileattachments.clear();

        files.clear();

        try {
            filesfromdisc = (List<File>) FileUtils.listFiles(new File("C:\\WebAppFiles\\CourseAttachments\\" + courseID + ""), null, true);
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

    public HRCourse() {
    }

    public HRCourse(Integer courseID) {
        this.courseID = courseID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPrimaryInstructors() {
        return primaryInstructors;
    }

    public void setPrimaryInstructors(String primaryInstructors) {
        this.primaryInstructors = primaryInstructors;
    }

    //@XmlTransient
    public List<HRCourseOffering> getHRCourseOfferingList() {
        return hRCourseOfferingList;
    }

    public void setHRCourseOfferingList(List<HRCourseOffering> hRCourseOfferingList) {
        this.hRCourseOfferingList = hRCourseOfferingList;
    }

    //@XmlTransient
    public List<HRJobHRCourse> getHRJobHRCourseList() {
        return hRJobHRCourseList;
    }

    public void setHRJobHRCourseList(List<HRJobHRCourse> hRJobHRCourseList) {
        this.hRJobHRCourseList = hRJobHRCourseList;
    }

    //@XmlTransient
    public List<HRCourseHRCourseInstructor> getHRCourseHRCourseInstructorList() {
        return hRCourseHRCourseInstructorList;
    }

    public void setHRCourseHRCourseInstructorList(List<HRCourseHRCourseInstructor> hRCourseHRCourseInstructorList) {
        this.hRCourseHRCourseInstructorList = hRCourseHRCourseInstructorList;
    }

    public HRCourseType getCourseTypeID() {
        return courseTypeID;
    }

    public void setCourseTypeID(HRCourseType courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseID != null ? courseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HRCourse)) {
            return false;
        }
        HRCourse other = (HRCourse) object;
        if ((this.courseID == null && other.courseID != null) || (this.courseID != null && !this.courseID.equals(other.courseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "si.odelo.HR.entity.HRCourse[ courseID=" + courseID + " ]";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public List<FileAttachment> getFileattachments() {
        return fileattachments;
    }

    public void setFileattachments(List<FileAttachment> fileattachments) {
        this.fileattachments = fileattachments;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isSelfRegistration() {
        return selfRegistration;
    }

    public void setSelfRegistration(boolean selfRegistration) {
        this.selfRegistration = selfRegistration;
    }

    public boolean isEducationAssesmentRequired() {
        return educationAssesmentRequired;
    }

    public void setEducationAssesmentRequired(boolean educationAssesmentRequired) {
        this.educationAssesmentRequired = educationAssesmentRequired;
    }

    public boolean isPerformanceEvaluationRequired() {
        return performanceEvaluationRequired;
    }

    public void setPerformanceEvaluationRequired(boolean performanceEvaluationRequired) {
        this.performanceEvaluationRequired = performanceEvaluationRequired;
    }

    public boolean isQsTopic() {
        return qsTopic;
    }

    public void setQsTopic(boolean qsTopic) {
        this.qsTopic = qsTopic;
    }
    
    

}
