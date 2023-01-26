package com.terraco.terracoDaCida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.terraco.terracoDaCida")
@SpringBootApplication
public class TerracoDaCidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerracoDaCidaApplication.class, args);
	}

}
