package com.odelowebapp.sign.jsfclasses;

import com.odelowebapp.entity.SIGNPresentation;
import com.odelowebapp.entity.SIGNSlide;
import com.odelowebapp.entity.SIGNTagType;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil;
import com.odelowebapp.sign.jsfclasses.util.JsfUtil.PersistAction;
import com.odelowebapp.sign.sessionbeans.SIGNPresentationFacade;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.commons.io.FilenameUtils;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.rendering.ImageType;

@Named("sIGNPresentationController")
@ViewScoped
public class SIGNPresentationController implements Serializable {

    @EJB
    private com.odelowebapp.sign.sessionbeans.SIGNPresentationFacade ejbFacade;

    @EJB
    private com.odelowebapp.sign.sessionbeans.SIGNSlideFacade ejbSlideFacade;

    private List<SIGNSlide> slideList = new ArrayList<>();
    private UploadedFile file;
    private List<SIGNPresentation> items = null;
    private SIGNPresentation selected;
    private List<SIGNPresentation> selectedList = null;
    private SIGNSlide selectedSlide;
    private List<UploadedFile> uploadeFileList = new ArrayList<>();
    private UploadedFiles files;
    private List<SIGNSlide> slideTestList = null;

    public SIGNPresentationController() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "----- in SIGNPresentationController CLASS -----");
    }

    public SIGNPresentation getSelected() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get selected");
        return selected;
    }

    public void setSelected(SIGNPresentation selected) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set selected");
        this.selected = selected;
    }

    public List<SIGNPresentation> getSelectedList() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get selectedList");
        return selectedList;
    }

    public void setSelectedList(List<SIGNPresentation> selectedList) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set selectedList");
        if (selectedList.size() == 1) {
            selected = selectedList.get(0);
        }
        this.selectedList = selectedList;
    }

    public SIGNSlide getSelectedSlide() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get selectedSlide");
        return selectedSlide;
    }

    public void setSelectedSlide(SIGNSlide selectedSlide) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set selectedSlide");
        this.selectedSlide = selectedSlide;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SIGNPresentationFacade getFacade() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get ejbPresentationFacade");
        return ejbFacade;
    }

    public UploadedFile getFile() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get uploaded file");
        return file;
    }

    public void setFile(UploadedFile file) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set uploaded file");
        this.file = file;
    }

    public UploadedFiles getFiles() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get uploaded files");
        return files;
    }

    public void setFiles(UploadedFiles files) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set uploaded files");
        this.files = files;
    }

    public SIGNPresentation prepareCreate() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in prepareCreate()");
        selected = new SIGNPresentation();
        initializeEmbeddableKey();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "return selected");
        return selected;
    }

    public List<SIGNPresentation> getItems() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In getItems()");
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<SIGNPresentation> items) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set items");
        this.items = items;
    }

    ////@RequiresPermissions("hr:*")
    public void createSlide(String fileName, Path path) throws java.net.SocketException, UnknownHostException, FileNotFoundException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in createSlide()");
        int timeOnDisplay = 10000;
        SIGNTagType tagTypeObj = new SIGNTagType();
        //check if hte file to be uploaded is of exceptible type and if it is a video or image
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "checking the type of file");
        String tagType = FilenameUtils.getExtension(fileName).toLowerCase();
        if (tagType.equals("mp4") || tagType.equals("wma") || tagType.equals("wmv")) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "the file is a video");
            timeOnDisplay = -1000;
            tagTypeObj = new SIGNTagType(2);
        } else if (tagType.equals("jpg") || tagType.equals("png") || tagType.equals("gif") || tagType.equals("jpeg") || tagType.equals("bmp") || tagType.equals("webm")) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "the file is an image");
            tagTypeObj = new SIGNTagType(1);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "add tagType to slide");

        //generate url
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get server ip");
        String ip;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        }
        //create new url for image path in server
        String newUrl = "http://" + path.toString().replace("C:", ip + ":7070").replace("\\", "/").replace("WebAppFiles/Sources", "osloWebApp");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "slide sourceUrl: {0}", newUrl);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "make slide");
        //make slide object
        SIGNSlide slide = new SIGNSlide(newUrl, (timeOnDisplay / 1000), fileName, tagTypeObj, selected);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "calling create(slide) of ejbSlideFacade");
        
        int index =  selected.getSIGNSlideListOrdered().size();
        slide.setPosition(index);
        
        ejbSlideFacade.create(slide);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "add slide to slide list");
        selected.getSIGNSlideList().add(slide);
        //DOPISAL DEAN primozic problem
        ejbFacade.edit(selected);
    }

    ////@RequiresPermissions("hr:*")
    public void extractImagesFromPDF(InputStream input, String stringPath, String fileName) throws IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in extractImagesFromPDF");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "open PDF file");
        try (PDDocument document = PDDocument.load(input)) {
            //PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "loop for listing through the PDFs pages");
            for (int pageNum = 0; pageNum < document.getNumberOfPages(); pageNum++) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "make a PDF page an image");
                //get image from certain pdf page
                //BufferedImage bufferImage = pdfRenderer.renderImage(pageNum); // <-- delalo prej
                BufferedImage bufferImage = pdfRenderer.renderImageWithDPI(pageNum, 300);
                //PDPage page = catalog.getPages().get(pageNum);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferImage, "png", os);

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "save image to inputStream");
                InputStream imgInputStream = new ByteArrayInputStream(os.toByteArray());
                //generate file path
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "generate the file name");
                String newFileName = fileName.replace(".pdf", "");
                newFileName = newFileName + "_pdf" + "_image_" + (pageNum + 1) + ".png";
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "generate the file path");
                Path path = Paths.get(stringPath + newFileName);
                //save image
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "save file to {0}", path.toString());
                Files.copy(imgInputStream, path, StandardCopyOption.REPLACE_EXISTING);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "call createSlide(fileName, path)");
                createSlide(newFileName, path);
            }
        }
    }

    ////@RequiresPermissions("hr:*")
    public void uploadFile(UploadedFile f, String stringPath) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in uploadFile(f, stringPath)");
        try {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "convert uploadfile to InputStream");
            //get file input stream
            InputStream input = f.getInputStream();
            //get file name
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get file name");
            String fileName = f.getFileName();
            //get file extension
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "check if file is a PDF");
            if (!"pdf".equals(FilenameUtils.getExtension(fileName))) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "not a PDF");
                //creat a file path
                Path path = Paths.get(stringPath + fileName);
                //copy file to the wanted dir
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Start file upload ({0})", path.toString());
                Files.copy(input, path, StandardCopyOption.REPLACE_EXISTING);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Create slide");
                createSlide(fileName, path);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "file is an PDF");
                extractImagesFromPDF(input, stringPath, fileName);
            }
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in create()");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "call presist() - for create");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNPresentationCreated"));
        slideList.clear();
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in update()");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "call presist() - for update");
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNPresentationUpdated"));
        setUpdatedAtPresentation();
        slideList.clear();
    }

    public void destroy() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in destroy()");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "call presist() - for delete");
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleSIGN").getString("SIGNPresentationDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    ////@RequiresPermissions("hr:*")
    private void persist(PersistAction persistAction, String successMessage) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in perist(persistAction, successMessage)");
        if (selected != null) {
            boolean error = false;
            String errorMessage = "";
            setEmbeddableKeys();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
                if (persistAction == PersistAction.CREATE) {
                    //if(selected.getSIGNSlideList() != null && uploadeFileList != null && !selected.getSIGNSlideList().isEmpty() && !uploadeFileList.isEmpty()){
                    //test dean
                    System.out.println("selected.getSIGNSlideList():"+selected.getSIGNSlideList());
                    System.out.println("uploadeFileList size:"+uploadeFileList.size());
                    System.out.println("selected.getName():"+selected.getName());
                    
                    //
                    if(uploadeFileList != null && !uploadeFileList.isEmpty()){
                        selected.setDate(Date.from(date.toInstant()));
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "presistAction - create");
                        //genarate new path for presentation
                        String path = "C:\\WebAppFiles\\Sources\\Presentations\\" + sdf.format(Date.from(date.toInstant())) + "_" + selected.getName() + "\\";
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "path generated: {0}", path);
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "create directory for generated path");
                        new File(path).mkdir();

                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "create presentation with ejbPresentationFacade");
                        getFacade().create(selected);

                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "upload added fles in a loop");
                        for (UploadedFile uFile : uploadeFileList) {
                            uploadFile(uFile, path);
                        }
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "check if slides were created");
                        //dopisal dean PRIMOŽIČ refresh problem
                        getFacade().edit(selected);
                    }
                    else {
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "make error message");
                        error = true;
                        errorMessage = "Datoteke je potrebno naložiti";
                    }
                } else if (persistAction != PersistAction.DELETE) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "presistAction - edit");
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "updating presentationSlides in loop ");
                    for (SIGNSlide slide : selected.getSIGNSlideListOrdered()) {
                        ejbSlideFacade.edit(slide);
                    }
                    getFacade().edit(selected);
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "remove presentation with id {0}", selected.getIdPresentation());
                    for (SIGNSlide var : selected.getSIGNSlideListOrdered()) {
                        ejbSlideFacade.remove(var);
                    }
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "remove selected presentation with ejbPresentationFacde");
                    
                    getFacade().remove(selected);
                }
                refreshData();
                if (error == true) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set JsfUtil error message");
                    JsfUtil.addErrorMessage(errorMessage);
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "set JsfUtil success message");
                    JsfUtil.addSuccessMessage(successMessage);
                }
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Napaka", msg);
                    JsfUtil.addErrorMessage(ex, msg);
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, msg);
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "ERROR");
            }
        }
    }

    ////@RequiresPermissions("hr:*")
    public SIGNPresentation getItem(int id) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In getItem(id)");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get all presentation with ejbPresentationFacade");
        for (SIGNPresentation var : getFacade().findAll()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In for loop");
            if (var.getIdPresentation() == id) {
                return var;
            }
        }
        return null;
    }

    ////@RequiresPermissions("hr:*")
    public void onRowReorder(ReorderEvent event) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "onRowReorder(event)");
        int fromIndex = event.getFromIndex();
        int toIndex = event.getToIndex();

        SIGNSlide obj1 = findSlideByPosition(fromIndex);
        SIGNSlide obj2 = findSlideByPosition(toIndex);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "switch elements positions");
        obj1.setPosition(toIndex);
        obj2.setPosition(fromIndex);
        selected.getSIGNSlideListOrdered().set(obj1.getPosition(), obj1);
        selected.getSIGNSlideListOrdered().set(obj2.getPosition(), obj2);
        
        for (SIGNSlide var : selected.getSIGNSlideListOrdered()) {
            ejbSlideFacade.edit(var);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "presesntation slide {0}, fileName: {1}, position: {2}, timeOnDisplay: {3}", new Object[]{var.getIdSlide(), var.getFileName(), var.getPosition(), var.getTimeOnDisplay()});
        }
        setUpdatedAtPresentation();
        refreshData();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "position: {0} timeOnDisplay: {1}, fileName: {2}", new Object[]{obj1.getPosition(), obj1.getTimeOnDisplay(), obj1.getFileName()});
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "position: {0} timeOnDisplay: {1}, fileName: {2}", new Object[]{obj2.getPosition(), obj2.getTimeOnDisplay(), obj2.getFileName()});
    }

    ////@RequiresPermissions("hr:*")
    public void handleFileUploadPresentation(FileUploadEvent event) throws SocketException, UnknownHostException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in handleFileUploadPresentation(event)");
        UploadedFile f = event.getFile();
        System.out.println("handleFileUploadPresentation() --> file name:"+f.getFileName());
        uploadeFileList.add(f);
    }

    ////@RequiresPermissions("hr:*")
    public void handleFileUploadAddSlide(FileUploadEvent event) throws SocketException, UnknownHostException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in handleFileUploadAddSlide(event)");
        file = event.getFile();
        if (file != null) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "get server ip address");
            String ip;
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            }
            String remove = "http://" + ip + ":7070/osloWebApp";
            String path;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "check if presentation has slides");
            if (selected.getSIGNSlideListOrdered().size() > 0) {
                String parsedPath = selected.getSIGNSlideListOrdered().get(0).getSourceUrl().replace(remove, "").replace("/", "\\");
                path = "C:\\WebAppFiles\\Sources\\" + FilenameUtils.getPath(parsedPath);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "genereated path: {0}", path);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
                path = "C:\\WebAppFiles\\Sources\\Presentations\\" + sdf.format(Date.from(date.toInstant())) + "_" + selected.getName() + "\\";
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "create dir with path: {0}", path);
                new File(path).mkdir();
            }
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Started uploadFile to path {0}", path);
            uploadFile(file, path);
            
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Check if slideList is == 1, actual size = {0}", slideList.size());
            if (slideList.size() == 1) {
                refreshData();
                slideList.clear();
            }
            setUpdatedAtPresentation();
        }
    }

    ////@RequiresPermissions("hr:*")
    public void deleteSlide() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In deleteSlide()");
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Checking if the selected element is not null");
                
        if (selectedSlide != null) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Removing slide from presentation");
            selected.getSIGNSlideListOrdered().remove(selectedSlide);
            ejbSlideFacade.remove(selectedSlide);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Starting a loop for updating positions of getSIGNSlideTestList");
            for (int i = 0; i < selected.getSIGNSlideListOrdered().size(); i++) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Setting the getSIGNSlideTestList position to {0}", i);
                selected.getSIGNSlideListOrdered().get(i).setPosition(i);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating the getSIGNSlideTestList with ID {0}", selected.getSIGNSlideListOrdered().get(i).getIdSlide());
                ejbSlideFacade.edit(selected.getSIGNSlideListOrdered().get(i));
            }
        }
        setUpdatedAtPresentation();
    }

    ////@RequiresPermissions("hr:*")
    private SIGNSlide findSlideByPosition(int position) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in findSlideByPosition(position)");
        for (SIGNSlide var : selected.getSIGNSlideListOrdered()) {
            if (var.getPosition() == position) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "return found");
                return var;
            }
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "presentationSlide not found");
        return null;
    }

    //@RequiresPermissions("hr:*")
    public SIGNPresentation getSIGNPresentation(java.lang.Integer id) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in getSIGNPresentation(id)");
        return getFacade().find(id);
    }

    //@RequiresPermissions("hr:*")
    public List<SIGNPresentation> getItemsAvailableSelectMany() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "in getItemsAvailableSelectMany()");
        return getFacade().findAll();
    }

    //@RequiresPermissions("hr:*")
    public List<SIGNPresentation> getItemsAvailableSelectOne() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "getItemsAvailableSelectOne()");
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SIGNPresentation.class)
    public static class SIGNPresentationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SIGNPresentationController controller = (SIGNPresentationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sIGNPresentationController");
            return controller.getSIGNPresentation(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SIGNPresentation) {
                SIGNPresentation o = (SIGNPresentation) object;
                return getStringKey(o.getIdPresentation());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SIGNPresentation.class.getName()});
                return null;
            }
        }
    }
    
    private void refreshData(){
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Refresing data!");
        items = null;
        selected = null;
//        if(selected.getIdPresentation() != null)
//            selected = getItem(selected.getIdPresentation());
    }
    
    private void setUpdatedAtPresentation(){
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        selected.setUpdatedAt(Date.from(date.toInstant()));
        getFacade().edit(selected);
    }
}
