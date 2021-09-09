package com.abioye.rest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public final class MailingHelper {

  @Autowired
  private static JavaMailSender emailSender;

  private static final String EMAIL_FROM = "noreply@yahoo.com";

  private MailingHelper() {
  }

  public static void sendMail(String to, String action) {
    final SimpleMailMessage message = new SimpleMailMessage();
    String mailText = "Your account has been " + action + ".";
    message.setText(mailText);
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setSubject(mailText);
    emailSender.send(message);
  }

  public static void sendRegisteredEmail(final long id, final String to) throws MessagingException {
    final MimeMessage mimeMessage = emailSender.createMimeMessage();
    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    final String html = "<a href=\"http://localhost:8080/api/users/" + id + "/verify\">Verify Me</a>";
    helper.setText(html, true);
    helper.setFrom(EMAIL_FROM);
    helper.setTo(to);
    helper.setSubject("Kindly verify your email address.");
    emailSender.send(mimeMessage);
  }
}