package com.restaurante01.api_restaurante;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiRestauranteApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiRestauranteApplication.class, args);
		System.out.println("Swagger Doc e Teste de API: http://localhost:8080/swagger-ui/index.html");
	}
}
