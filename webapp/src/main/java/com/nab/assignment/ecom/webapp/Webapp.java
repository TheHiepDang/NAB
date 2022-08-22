package com.nab.assignment.ecom.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
public class Webapp {

	public static void main(String[] args) {
		SpringApplication.run(Webapp.class, args);
	}

}
