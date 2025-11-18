package com.example.KinetoWebsite.Service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Properties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service

public class EmailService {
    private String host;
    private String port;
    private String username;
    private String password;
    private String adminEmail;
    private boolean auth;


    public void sendAdminNotification(String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", String.valueOf(auth));
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminEmail)); // Send to ADMIN
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Admin notification sent successfully to: " + adminEmail);

        } catch (Exception e) {
            System.err.println("Failed to send admin notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //html send email method
    public void sendHtmlNotification(String subject, String htmlBody){
        try{
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", String.valueOf(auth));
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                }
            });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminEmail));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("HTML notification sent successfully to: " + adminEmail);
        } catch (Exception e){
            System.err.println("Failed to send HTML notification " + e.getMessage());
            e.printStackTrace();
        }
    }
}
