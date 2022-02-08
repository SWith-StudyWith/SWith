package com.swith.api.service;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.swith.common.util.FirebaseUtil;
import com.swith.config.FirebaseConfig;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import com.swith.db.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.util.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FirebaseConfig firebaseConfig;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<com.swith.db.entity.File> getStudyFileList(Study study) {
        return fileRepository.findByStudy(study);
    }

    @Override
    public void uploadStudyFiles(Member member, Study study, List<MultipartFile> files) throws IOException {
        for (MultipartFile multipartFile:files) {
            com.swith.db.entity.File file = com.swith.db.entity.File.builder()
                    .originName(multipartFile.getOriginalFilename())
                    .fileUrl(this.upload(multipartFile, firebaseConfig.getFile_storage_path(), null, "media"))
                    .fileSize(multipartFile.getSize())
                    .member(member)
                    .study(study)
                    .build();
            fileRepository.save(file);
        }
    }

    @Override
    public Map<String, ByteArrayResource> downloadStudyFile(Study study, long fileId) throws IOException {
        com.swith.db.entity.File file = fileRepository.findByStudyAndFileId(study, fileId).orElse(null);
        if (file == null) return null;
        Map<String, ByteArrayResource> map = new HashMap<>();
        map.put(file.getOriginName(), this.downloadFile(FirebaseUtil.convertUrlToFilePath(file.getFileUrl())));
        return map;
    }

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
    public ByteArrayResource downloadFile(String filePath) throws IOException {
        Storage storage = FirebaseUtil.firebaseStorage;
        Blob blob = storage.get(BlobId.of(firebaseConfig.getBucket_name(), filePath));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        log.debug("downloadFile - success");
        byte[] content = null;
        content = IOUtils.toByteArray(inputStream);
        return new ByteArrayResource(content);
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
