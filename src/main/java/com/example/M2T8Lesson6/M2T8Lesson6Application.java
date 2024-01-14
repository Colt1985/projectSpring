package com.example.M2T8Lesson6;

import com.example.M2T8Lesson6.services.BashParser;
import com.example.M2T8Lesson6.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class M2T8Lesson6Application implements CommandLineRunner {

	@Autowired
	QuoteService service;

	public static void main(String[] args) {
		SpringApplication.run(M2T8Lesson6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	var index = service.getIndex();
		System.out.println(index);
	}

}
