package com.app_bancaria.my_bnl_application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Bnl API",
				version = "1.0",
				description = "API documentation for bnl app"
		)
)
@SpringBootApplication
public class MyBnlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBnlApplication.class, args);
	}

}
