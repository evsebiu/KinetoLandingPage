package com.example.KinetoWebsite;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Community Health Medical System API",
                version = "1.0",
                description = "API for Community Health Medical System"
        )
)

@SpringBootApplication
public class KinetoWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinetoWebsiteApplication.class, args);
	}

}
