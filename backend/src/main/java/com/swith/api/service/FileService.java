package com.swith.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    String upload(MultipartFile multipartFile, String storagePath, String url, String type) throws IOException;
    void deleteFile(String filePath);
    void uploadFile(File file, String filePath, String contentType) throws IOException;
    File convertMultipartToFile(MultipartFile multipartFile) throws IOException;
}
