package com.swith.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.swith.config.FirebaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class FirebaseUtil {

    @Autowired
    private FirebaseConfig beanFirebaseConfig;
    private static FirebaseConfig firebaseConfig;
    public static Storage firebaseStorage;

    @PostConstruct
    public void init() throws IOException {
        firebaseConfig = beanFirebaseConfig;
        firebaseStorage = initializeFirebaseStorage();
    }

    public static Storage initializeFirebaseStorage() throws IOException {
        FirebaseConfig config = new FirebaseConfig();
        config.setType(firebaseConfig.getType());
        config.setProject_id(firebaseConfig.getProject_id());
        config.setPrivate_key_id(firebaseConfig.getPrivate_key_id());
        config.setPrivate_key(firebaseConfig.getPrivate_key());
        config.setClient_email(firebaseConfig.getClient_email());
        config.setClient_id(firebaseConfig.getClient_id());
        config.setAuth_uri(firebaseConfig.getAuth_uri());
        config.setToken_uri(firebaseConfig.getToken_uri());
        config.setAuth_provider_x509_cert_url(firebaseConfig.getAuth_provider_x509_cert_url());
        config.setClient_x509_cert_url(firebaseConfig.getClient_x509_cert_url());
        // Object를 Json String으로 변환
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(config);
        InputStream inputStream = IOUtils.toInputStream(jsonString, StandardCharsets.UTF_8);
        log.debug("initializeFirebaseStorage - InputStream: {}", inputStream);
        // Firebase Storage
        return StorageOptions.newBuilder()
                .setProjectId(firebaseConfig.getProject_id())
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build().getService();
    }

    public static String convertFilePathToUrl(String filePath, String altParam) throws UnsupportedEncodingException {
        filePath = filePath.replaceAll("/", URLEncoder.encode("/", "UTF-8"));
        String url = firebaseConfig.getBase_url() + filePath;
        if (altParam.equals("media")) url = url + firebaseConfig.getContent_type_param() + firebaseConfig.getMedia_type();
        else throw new UnsupportedEncodingException();
        log.debug("convertFilePathToUrl - URL: {}", url);
        return url;
    }

    public static String convertUrlToFilePath(String url) throws UnsupportedEncodingException {
        url = url.replace(firebaseConfig.getBase_url(), "");
        url = url.substring(0, url.indexOf(firebaseConfig.getContent_type_param()));
        log.debug("convertUrlToFilePath - File Path: {}", url.replaceAll(URLEncoder.encode("/", "UTF-8"), "/"));
        return url.replaceAll(URLEncoder.encode("/", "UTF-8"), "/");
    }

}
