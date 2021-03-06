 package com.cursojava.cursomc.services;


import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger log = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mensagem) {
		
		log.info("Simulando envio de e-mail...");
		log.info(mensagem.toString());
		log.info("E-mail enviado!");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage mensagem) {
		log.info("Simulando envio de e-mail HTML...");
		log.info(mensagem.toString());
		log.info("E-mail enviado!");
		
	}

}
