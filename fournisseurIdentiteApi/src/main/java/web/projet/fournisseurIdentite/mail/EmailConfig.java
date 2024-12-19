package web.projet.fournisseurIdentite.mail;

import java.util.Properties;

public class EmailConfig {
    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;

    public EmailConfig(String smtpHost, int smtpPort, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
    }

    public Properties getSMTPProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        return props;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}