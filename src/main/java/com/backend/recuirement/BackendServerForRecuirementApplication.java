package com.backend.recuirement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendServerForRecuirementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServerForRecuirementApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper objectMapper()
	{
		return new ObjectMapper();
	}



}
