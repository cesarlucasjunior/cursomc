package com.cursojava.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.cursojava.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	// método que será responsável por enviar um e-mail para quem fez o pedido.
	
	void sendEmail(SimpleMailMessage mensagem);
	//Responsavel por enviar de fato o email com as configurações necessárias - operação fim.
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage mensagem);

}
