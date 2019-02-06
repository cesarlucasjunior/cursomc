package com.cursojava.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.cursojava.cursomc.domain.Pedido;

//classe responsável por implementar o email service a fim de que as classes concretas a termine
public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		//Como temos que chamar o sendEmail precisamos prepara-lo, que no caso é criar um SimpleMailMessage
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage ms = new SimpleMailMessage();
		//O SimpleMailMessage possui a estrutura de email que deverá ser instanciada.
		ms.setTo(pedido.getCliente().getEmail());
		ms.setFrom(sender);
		ms.setSubject("Seu pedido foi confirmado! Código - " + pedido.getId());
		ms.setSentDate(new Date(System.currentTimeMillis()));
		ms.setText(pedido.toString());
		return ms;
	}
}
