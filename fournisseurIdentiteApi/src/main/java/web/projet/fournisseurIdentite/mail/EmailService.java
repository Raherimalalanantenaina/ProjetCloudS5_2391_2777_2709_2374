package web.projet.fournisseurIdentite.mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailService {
    private final Session session;

    public EmailService(EmailConfig config) {
        this.session = Session.getInstance(config.getSMTPProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });
    }

    public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperty("mail.smtp.user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html");
        Transport.send(message);
    }

    
}
