package gui;

import Entites.personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import services.ServicePersonne;
import utils.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class FormulaireController implements Initializable{

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
    private Button btnSave;

    @FXML
    private Label lblStatus;



    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public FormulaireController() {con = database.getInstance().getConn();}

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtStatus.getItems().addAll("Admin", "Membre");
        txtStatus.getSelectionModel().select("Membre");
    }


    @FXML
    private void HandleEvents(MouseEvent event) {
        //check if not empty
        if (txtEmail.getText().isEmpty() || txtFirstname.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDOB.getValue().equals(null) || txtMdp.getText().isEmpty() || txtTelephone.getText().isEmpty() ) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
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
                sp.add(p1);
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Added Successfully");
                clearFields();

            }catch (Exception e){
                System.out.println(e.getMessage());
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText(e.getMessage());
            }
        }
    }

    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();
        txtMdp.clear();
        txtTelephone.clear();
    }

}
