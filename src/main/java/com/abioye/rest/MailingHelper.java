package com.abioye.rest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public final class MailingHelper {

  private static final String EMAIL_FROM = "noreply@yahoo.com";

  private MailingHelper() {
  }

  public static void sendVerifiedEmail(final JavaMailSender emailSender, final String to) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setText("Congrats! You have been verified.");
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setSubject("Congrats! You have been verified.");
    emailSender.send(message);
  }

  public static void sendDeactivatedEmail(final JavaMailSender emailSender, String to) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setText("Your account has been deactivated.");
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setSubject("Your account has been deactivated.");
    emailSender.send(message);
  }

  public static void sendRegisteredEmail(final JavaMailSender emailSender, final long id, final String to)
      throws MessagingException {
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