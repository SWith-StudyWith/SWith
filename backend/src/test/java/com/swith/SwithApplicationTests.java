package com.swith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.swith.config.FirebaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest
class SwithApplicationTests {

	@Autowired
	private FirebaseConfig firebaseConfig;

	public StorageOptions storageOptions;

	@Test
	void contextLoads() {
	}

	@Test
	void firebaseTest() throws IOException, CloneNotSupportedException {
		log.debug("FirebaseConfig - {}", firebaseConfig);

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
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(config);
		InputStream inputStream = IOUtils.toInputStream(jsonString, StandardCharsets.UTF_8);
		this.storageOptions = StorageOptions.newBuilder()
				.setProjectId(firebaseConfig.getProject_id())
				.setCredentials(GoogleCredentials.fromStream(inputStream)).build();

		log.debug("storageOptions: {}", storageOptions);
	}

}
