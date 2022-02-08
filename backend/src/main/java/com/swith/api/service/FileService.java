package com.swith.api.service;

import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    List<com.swith.db.entity.File> getStudyFileList(Study study);
    void uploadStudyFiles(Member member, Study study, List<MultipartFile> files) throws IOException;
    String upload(MultipartFile multipartFile, String storagePath, String url, String type) throws IOException;
    Object download(String filePath);
    void deleteFile(String filePath);
    void uploadFile(File file, String filePath, String contentType) throws IOException;
    Object downloadFile(String filePath) throws IOException;
    File convertMultipartToFile(MultipartFile multipartFile) throws IOException;
}
