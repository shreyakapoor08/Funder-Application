package com.asdc.funderbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
	    "com.asdc.funderbackend",
	    "com.asdc.funderbackend.config"
	})
@EnableJpaRepositories
public class FunderBackendApplication {	

	public static void main(String[] args) {
		SpringApplication.run(FunderBackendApplication.class, args);
	}

}
