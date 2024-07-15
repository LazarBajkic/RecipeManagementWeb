package com.bajkic.RecipeManagement;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeManagementApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		SpringApplication.run(RecipeManagementApplication.class, args);
	}

}
