package com.cursojava.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursojava.cursomc.services.DBService;
import com.cursojava.cursomc.services.EmailService;
import com.cursojava.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestProfileConfig {
	
	@Autowired
	private DBService dbService;
	
	
	@Bean
	public boolean dbService() throws ParseException {
		
		dbService.instatiateTestDatabase();
		
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
