package com.vti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		System.out.println("Buoi 16 Advacce web security & JWT");
		
		SpringApplication.run(Application.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry
				.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("GET", "PUT", "POST", "DELETE");
			}
		};
		
	}
}
