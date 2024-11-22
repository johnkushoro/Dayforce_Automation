
package com.totallygroup.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final Logger logger = LogManager.getLogger(EmailSender.class);

    private static final boolean isEmailEnabled = false;

    public static void sendEmail(String toEmail, String subject, String body, String attachmentPath) {
        if (!isEmailEnabled) {
            logger.info("Email sending is disabled.");
            return;
        }

        String emailFrom = Config.getProperty("emailFrom");
        String emailPassword = Config.getProperty("emailPassword");
        String smtpHost = Config.getProperty("smtpHost");
        String smtpPort = Config.getProperty("smtpPort");
        String smtpAuth = Config.getProperty("smtpAuth");
        String smtpStartTls = Config.getProperty("smtpStartTls");

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", smtpStartTls);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, emailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachmentPath);
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            Transport.send(message);
            logger.info("Email sent successfully.");
        } catch (Exception e) {
            logger.error("Failed to send email: {}", e.getMessage(), e);
        }
    }
}

