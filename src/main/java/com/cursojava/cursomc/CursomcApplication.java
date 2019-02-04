package com.cursojava.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		//TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
		SpringApplication.run(CursomcApplication.class, args);
	}

	public void run(String... args) throws Exception {		
	}
}
