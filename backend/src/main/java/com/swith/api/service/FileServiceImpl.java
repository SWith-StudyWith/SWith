package com.swith.api.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.swith.common.util.FirebaseUtil;
import com.swith.config.FirebaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public String upload(MultipartFile multipartFile, String storagePath, String url, String type) throws IOException {
        // String extension = "." + FilenameUtils.getExtension(multipartFile.getName());
        String fileName = UUID.randomUUID() + "_" + new Date().getTime();
        // if (!extension.equals(".")) fileName += extension;
        String filePath = storagePath + fileName;
        File file = this.convertMultipartToFile(multipartFile);
        this.uploadFile(file, filePath, multipartFile.getContentType());
        if (url != null) this.deleteFile(FirebaseUtil.convertUrlToFilePath(url));
        return FirebaseUtil.convertFilePathToUrl(filePath, type);
    }

    @Override
    public void deleteFile(String filePath) {
        Storage storage = FirebaseUtil.firebaseStorage;
        storage.delete(firebaseConfig.getBucket_name(), filePath);
    }

    @Override
    public void uploadFile(File file, String filePath, String contentType) throws IOException {
        BlobId blobId = BlobId.of(firebaseConfig.getBucket_name(), filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        Storage storage = FirebaseUtil.firebaseStorage;
        log.debug("uploadFile - storage: {}", storage);
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
    }

    @Override
    public File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = new File("temp");
        // multipartFile.transferTo(tempFile);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return tempFile;
    }

}
