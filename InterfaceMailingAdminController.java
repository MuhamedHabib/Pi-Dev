/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * FXML Controller class
 *
 * @author user
 */
public class InterfaceMailingAdminController implements Initializable {

    @FXML
    private TextField txfa;
    @FXML
    private TextField txfde;
    @FXML
    private TextArea txmessage;
    @FXML
    private TextField txobjet;
    @FXML
    private TextField txfpassword;
    @FXML
    private Label SentBoollValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void EnvoyerEmail(ActionEvent event) {
        sendEmail();
    }
     public void sendEmail(){
        String to = txfa.getText();
        String from = txfde.getText();
        String host = "chadhamouelhi14@gmail.com";
        final String username = txfde.getText();
        final String password = txfpassword.getText();

        //setup mail server

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "580");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{

            //create mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject(txobjet.getText());
            m.setText(txmessage.getText());

            //send mail

            Transport.send(m);
            SentBoollValue.setVisible(true);
            System.out.println("Message sent!");

        }   catch (MessagingException e){
            e.printStackTrace();
        }

    }

    
}
