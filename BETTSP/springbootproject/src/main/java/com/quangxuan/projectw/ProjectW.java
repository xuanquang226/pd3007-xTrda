package com.quangxuan.projectw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(value = { "controller", "services", "data", "config", "security", "utils" })
@EntityScan(basePackages = { "data.entities" })
@EnableJpaRepositories(basePackages = { "data.repositories" })
@OpenAPIDefinition(info = @Info(title = "Library Apis", version = "1.0", description = "Library Management Apis"))
public class ProjectW {

	public static void main(String[] args) {
		SpringApplication.run(ProjectW.class, args);
		// Configurable conflict with flyway
		// ConfigurableApplicationContext context = new
		// AnnotationConfigApplicationContext("com.quangxuan.springakafka");
		System.out.println("trangdepgai");
	}
}
