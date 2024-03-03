package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.controller.UserController;
import com.asdc.funderbackend.service.FirebaseFileStorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Firebase file storage service.
 */
@Service
public class FirebaseFileStorageServiceImpl implements FirebaseFileStorageService{
	
    private final String SDKFILENAME = "funder-asdc-firebase-adminsdk-bjd1i-36077f273c.json"; 
    private Storage storage;

    /**
     * The Resource loader.
     */
    @Autowired
    ResourceLoader resourceLoader;

    /**
     * Init.
     *
     * @param event the event
     */
    @EventListener
    public void init(ApplicationReadyEvent event) {
        // initalizing firebase connection
        try {
            String filePath = new ClassPathResource(SDKFILENAME).getFile().getAbsolutePath();
            File file = ResourceUtils.getFile(filePath);
            
            try (InputStream in = new FileInputStream(file)) {
                storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .setProjectId("funder-asdc")
                        .build()
                        .getService();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException{

        // saving images into firebase and return image URL.
    	String firebaseURL = "https://firebasestorage.googleapis.com/v0/b/";

        String imageName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of("funder-asdc.appspot.com", imageName);
        
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        
        storage.create(blobInfo, file.getBytes());
       
        String accessURL = firebaseURL + "funder-asdc.appspot.com" +
                "/o/" + imageName + "?alt=media&token=" + imageName;
        
        return accessURL;

    }
    
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
