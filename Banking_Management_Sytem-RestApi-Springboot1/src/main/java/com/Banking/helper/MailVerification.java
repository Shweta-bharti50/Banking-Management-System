package com.Banking.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Banking.entity.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailVerification 
{
	@Autowired
    JavaMailSender mailSender;
	
	public void sendMail(Customer customer)
	{
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
		
		try {
			helper.setFrom("shwetabharti737@gmail.com");
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			helper.setTo(customer.getEmail());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			helper.setSubject("Mail Verification");
		} 
		catch (MessagingException e) {
		
			e.printStackTrace();
		}
		
		try {
			helper.setText("Your otp for email verification is "+customer.getOtp());
		} 
		catch (MessagingException e) {
			
			e.printStackTrace();
		}	
		mailSender.send(mimeMessage);  
	}
}
