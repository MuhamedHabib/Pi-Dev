package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Entites.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import services.*;
import utils.*;


public class ProfilController implements Initializable {

        @FXML
        private Button btnUpdate;

        @FXML
        private TextField lblnom;

        @FXML
        private TextField lblprenom;

        @FXML
        public ImageView imagePro;

        @FXML
        private Label lblStatus;

        @FXML
        private Label lblStatus1;

        @FXML
        private Button btnUpdateNom;

        @FXML
        private TextField txtMdp;

        @FXML
        private TextField txtMdp1;

        @FXML
        private Button logout;

        @FXML
        private Button btnSupp;
        @FXML
        private Label lblid;

    String imagePath = null;

    private int userId;
    private ServicePersonne service = new ServicePersonne();
    private UserSession usr;
    public ProfilController() {
        con = database.getConn();
    }
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private static int id_user;
    private static String nom;
    private static String prenom;
    private static String email;
    private static String mdp;
    private static String date_naissance;
    private static String telephone;
    private static String image;
    private static String status;


    @Override
       public void initialize(URL location, ResourceBundle resources) {
        lblnom.setText(nom);
        lblprenom.setText(prenom);
        txtMdp.setText(mdp);
        Image image1 = new Image("file:///"+image);
        imagePro.setImage(image1);
        imagePath=image;
       }

    public void getAdh(int id_user1, String nom1, String prenom1, String email1, String mdp1, String date_naissance1, String telephone1, String status1,String image1) {
        try {
            id_user =id_user1;
            nom = nom1;
            prenom = prenom1;
            email = email1;
            mdp = mdp1;
            date_naissance = date_naissance1;
            telephone = telephone1;
            status = status1;
            image = image1;

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @FXML
    private void passwordUpdate(ActionEvent event) {

        if( !txtMdp.getText().isEmpty() && !txtMdp1.getText().isEmpty())
        {
            if (txtMdp.getText().equals(txtMdp1.getText()))
            {
                personne user = service.findById(id_user);
                user.setMdp(txtMdp.getText());
                service.updatePassword(user);
                lblStatus.setText("mot de passe modifié !");
            }
            else
            {
                lblStatus.setText("Erreur : Les deux mots de passe ne sont pas identiques !");
            }

        }

    }

        @FXML
        void Supprimer(MouseEvent event) {
            personne user = service.findById(id_user);
            service.Supp(user);

        }


    @FXML
    private void logout(ActionEvent event) {
        if (event.getSource() == logout){
            try {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("login.fxml")));
                stage.setTitle("Authentification !");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex){
                System.err.println(ex.getMessage());

            }
        }
    }

    @FXML
    private void updateNom(ActionEvent event) {

        personne user = service.findById(id_user);

        user.setNom(lblnom.getText());
        user.setPrenom(lblprenom.getText());
        service.updateNom(user);

    }



}
