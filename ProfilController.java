package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Entites.*;
import javafx.scene.paint.Color;
import services.*;
import utils.*;

public class ProfilController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private ComboBox<String> txtStatus;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtMdp;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtStatus.getItems().addAll("Admin", "Membre");
        txtStatus.getSelectionModel().select("Membre");

        ServicePersonne sp = new ServicePersonne();
        try {
            personne p1 = new personne();
            txtFirstname.setText(p1.getNom());
            txtLastname.setText(p1.getPrenom());
            txtEmail.setText(p1.getEmail());
            txtMdp.setText(p1.getMdp());
            txtDOB.setValue(LocalDate.parse(p1.getDate_naissance()));
            txtTelephone.setText(p1.getTelephone());
            txtStatus.setValue(p1.getStatus());

        } catch (Exception e){
            System.out.println(e.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(e.getMessage());
        }
    }
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ProfilController() {
        con = database.getConn();
    }




    @FXML
    private void HandleEvents1(javafx.scene.input.MouseEvent event){

            ServicePersonne sp= new ServicePersonne();
            try{
                personne p1= new personne();
                p1.setNom(txtFirstname.getText());
                p1.setPrenom(txtLastname.getText());
                p1.setEmail(txtEmail.getText());
                p1.setMdp(txtMdp.getText());
                p1.setDate_naissance(txtDOB.getValue().toString());
                p1.setTelephone(txtTelephone.getText());
                p1.setStatus(txtStatus.getValue().toString());
                sp.update(p1);
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Update Successfully");

            }catch (Exception e){
                System.out.println(e.getMessage());
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText(e.getMessage());
            }
        }



}
