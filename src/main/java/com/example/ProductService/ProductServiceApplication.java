package com.example.ProductService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

	private static final Logger logger = LogManager.getLogger(ProductController.class);
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
		logger.info("Application started. It's show time !!");

	}

}
