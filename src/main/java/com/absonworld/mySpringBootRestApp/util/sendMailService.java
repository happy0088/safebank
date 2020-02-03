package com.absonworld.mySpringBootRestApp.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class sendMailService {
    public boolean sendMail(String token) {

        String host = "smtp.gmail.com";
        final String user = "iambatman0077@gmail.com";//change accordingly
        final String password = "batman@0077";//change accordingly

        String to = "abhishek.k0088@gmail.com";//change accordingly
        boolean isSent = false;

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
      //x  props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Project - Safe Bank - New User Created");
            message.setText("Click on the link to verify your account "+token);

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");
            isSent = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSent;
    }
}