package com.swith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SwithApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwithApplication.class, args);
	}

}
