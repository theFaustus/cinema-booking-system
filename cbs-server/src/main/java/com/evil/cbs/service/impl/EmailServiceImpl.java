package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Email;
import com.evil.cbs.service.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

  @Override
  public void sendEmail(Email email) {
    try {
      sendEmailWithAttachments(email);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void sendEmailWithAttachments(Email email) throws Exception {
    Message msg = createMessage(email);
    Transport.send(msg);
  }

  private Session createSession(Email email) {
    Properties properties = createMailProperties(email);
    Authenticator auth = new Authenticator() {
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email.getFromEmail(), email.getPassword());
      }
    };
    return Session.getInstance(properties, auth);
  }

  private Message createMessage(Email email) throws Exception {
    Session session = createSession(email);
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(email.getFromEmail()));
    InternetAddress[] toAddresses = { new InternetAddress(email.getToEmail()) };
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(email.getSubject());
    msg.setSentDate(new Date());
    Multipart multipart = createMultipart(email);
    addAttachments(email, multipart);
    msg.setContent(multipart);
    return msg;
  }

  private Multipart createMultipart(Email email) throws MessagingException {
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(email.getMessage(), "text/html");
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    return multipart;
  }

  private void addAttachments(Email email, Multipart multipart) throws MessagingException {
    for (String filePath : email.getAttachedFiles()) {
      MimeBodyPart attachPart = new MimeBodyPart();
      try {
        attachPart.attachFile(filePath);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      multipart.addBodyPart(attachPart);
    }
  }

  private Properties createMailProperties(Email email) {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", email.getHost());
    properties.put("mail.smtp.port", email.getPort());
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.user", email.getFromEmail());
    properties.put("mail.password", email.getPassword());
    return properties;
  }
}
