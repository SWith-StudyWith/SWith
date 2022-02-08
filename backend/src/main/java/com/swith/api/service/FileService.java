package com.swith.api.service;

import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    List<com.swith.db.entity.File> getStudyFileList(Study study);
    void uploadStudyFiles(Member member, Study study, List<MultipartFile> files) throws IOException;
    Map<String, ByteArrayResource> downloadStudyFile(Study study, long fileId) throws IOException;
    void deleteStudyFile(long fileId) throws IOException;
    String upload(MultipartFile multipartFile, String storagePath, String url, String type) throws IOException;
    void deleteFile(String filePath);
    void uploadFile(File file, String filePath, String contentType) throws IOException;
    ByteArrayResource downloadFile(String filePath) throws IOException;
    File convertMultipartToFile(MultipartFile multipartFile) throws IOException;
}
