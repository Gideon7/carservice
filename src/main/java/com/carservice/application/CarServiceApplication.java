package com.carservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}
	
	public WebMvcConfigurer corsConfigurer() { 
		   return new WebMvcConfigurer() {
		      @Override
		      public void addCorsMappings(CorsRegistry registry) {
		         registry.addMapping("/**").allowedHeaders("*");
		      }    
		   };
		}
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}

}
