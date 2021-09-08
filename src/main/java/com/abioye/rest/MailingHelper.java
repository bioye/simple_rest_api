package com.abioye.rest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailingHelper {

  private static final String EMAIL_FROM = "noreply@yahoo.com";

  private MailingHelper() {
  }

  public static void sendVerifiedEmail(JavaMailSender emailSender, String to) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText("Congrats! You have been verified.");
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setSubject("Congrats! You have been verified.");
    emailSender.send(message);
  }

  public static void sendDeactivatedEmail(JavaMailSender emailSender, String to) throws MessagingException {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText("Your account has been deactivated.");
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setSubject("Your account has been deactivated.");
    emailSender.send(message);
  }

  public static void sendRegisteredEmail(JavaMailSender emailSender, long id, String to) throws MessagingException {
    String html = "<a href=\"http://localhost:8080/api/users/" + id + "/verify\">Verify Me</a>";
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    helper.setText(html, true);
    helper.setFrom(EMAIL_FROM);
    helper.setTo(to);
    helper.setSubject("Kindly verify your email address.");
    emailSender.send(mimeMessage);
  }
}