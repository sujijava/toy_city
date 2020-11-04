package ca.sheridancollege.mail;

import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ca.sheridancollege.beans.Toy;

@Component
public class EmailServiceImpl {

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
	
	@Autowired
	private TemplateEngine templateEngine;

	public void sendMailWithThymeleaf(String username, String messageBody, 
			String footer, String to, String subject, ArrayList<Toy> toys)
			throws MessagingException {

		// Prepare the evaluation context
		final Context ctx = new Context();
		ctx.setVariable("username", username);
		ctx.setVariable("message", messageBody);
		ctx.setVariable("footer", footer);
		ctx.setVariable("toys", toys); 
		//name, message, footer: 
		//the same names from 'editTemplate.html' 
		
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
		final MimeMessageHelper message = 
				new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
		message.setSubject(subject);
		message.setFrom("prog32758.suji@gmail.com");
		message.setTo(to);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine
				.process("/worker/editTemplate.html", ctx);
		message.setText(htmlContent, true); // true = isHtml

		// Send mail
		this.emailSender.send(mimeMessage);
	}

}
