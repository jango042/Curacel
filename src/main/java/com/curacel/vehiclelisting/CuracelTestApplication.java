package com.curacel.vehiclelisting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CuracelTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuracelTestApplication.class, args);
		System.out.println("******************SYSTEM UP****************");
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}


}
