package com.grupo1.alojapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AlojamientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlojamientoApplication.class, args);
	}
}