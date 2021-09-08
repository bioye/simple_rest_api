package com.abioye.rest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailClient {

    public void sendSimpleMessage(JavaMailSender emailSender,
      String to, String html) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(html, true); 
        helper.setFrom("noreply@yahoo.com");
        helper.setTo(to); 
        helper.setSubject("Kindly verify your email address."); 
        emailSender.send(mimeMessage);
    }
}