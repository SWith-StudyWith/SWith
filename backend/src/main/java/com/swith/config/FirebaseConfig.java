package com.swith.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseConfig {
    @JsonIgnore
    private String bucket_name;
    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;
    @JsonIgnore
    private String base_url;
    @JsonIgnore
    private String profile_storage_path;
    @JsonIgnore
    private String study_storage_path;
    @JsonIgnore
    private String content_type_param;
    @JsonIgnore
    private String media_type;
}
