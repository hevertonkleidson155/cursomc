package com.heverton.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.heverton.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationEmail(Pedido obj);
}
