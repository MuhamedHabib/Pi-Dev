package utils;


import javafx.beans.binding.ObjectExpression;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;

public class javaMail {
    public static void sendMail(String receveursList, String Object, String Corps) {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String MonEmail = "inovvat@gmail.com";
        String password = "InnovaTech123";

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new javax.mail.PasswordAuthentication(MonEmail, password);
            }

        });

        javax.mail.Message message = prepareMessage(session,MonEmail,receveursList, Object, Corps
        );

        try {
            javax.mail.Transport.send(message);
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(javaMail.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.err.println("Message envoyé avec succès");
    }

    private static javax.mail.Message prepareMessage(Session session, String email, String receveursList, String Object, String Corps)
    {
        javax.mail.Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(email));

            message.setSubject(Object);
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receveursList));
            message.setText(Corps);

            return message;
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(javaMail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}




