package org.training.campus.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LuxCampus17Application {
	
	public static final String DB_SCHEMA_NAME = "blog";

	public static void main(String[] args) {
		SpringApplication.run(LuxCampus17Application.class, args);
	}

}
