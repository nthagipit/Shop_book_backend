package com.gipit.bookshop_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.gipit.bookshop_backend.models")
public class BookshopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshopBackendApplication.class, args);
	}

}
