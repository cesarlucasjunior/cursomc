package com.cursojava.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cursojava.cursomc.domain.Pedido;

//classe responsável por implementar o email service a fim de que as classes concretas a termine
public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplatePedido(Pedido pedido){
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mime, true);
		
		mimeHelper.setTo(pedido.getCliente().getEmail());
		mimeHelper.setFrom(sender);
		mimeHelper.setSubject("Pedido confirmado! Código - " + pedido.getId());
		mimeHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeHelper.setText(htmlFromTemplatePedido(pedido), true);
		
		return mime;
	}
}
