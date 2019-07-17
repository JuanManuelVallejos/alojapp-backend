package com.grupo1.alojapp;

import com.grupo1.alojapp.Configuration.FileStorageProperties;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class AlojamientoApplication {

	public static void main(String[] args) {
        BasicConfigurator.configure();
		SpringApplication.run(AlojamientoApplication.class, args);
	}
}