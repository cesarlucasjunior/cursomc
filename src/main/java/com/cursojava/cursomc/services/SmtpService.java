package com.cursojava.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpService extends AbstractEmailService{

	@Autowired
	private MailSender mailSender;
	
	private static final Logger log = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage mensagem) {
		log.info("Enviando e-mail...");
		mailSender.send(mensagem);
		log.info("E-mail enviado!");

	}

}
