package com.cursojava.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.cursojava.cursomc.services.DBService;
import com.cursojava.cursomc.services.EmailService;
import com.cursojava.cursomc.services.SmtpService;

@Configuration
@Profile("dev")
public class DevProfileConfig {

	@Autowired
	private DBService dbService;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	
	@Bean
	public boolean dbService() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;			
		}
		
		dbService.instatiateTestDatabase();
		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpService();
	}

}
