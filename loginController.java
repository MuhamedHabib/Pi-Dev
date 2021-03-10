package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import utils.*;
import javafx.scene.control.*;
import javafx.scene.control.TextField;





public class loginController implements Initializable {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public loginController() {
        con = database.getInstance().getConn();

    }


    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Label btnForgot;

    @FXML
    private Button btnFB;

    @FXML
    private Button btnSignup;

    @FXML
    private Label lblErrors;



    @FXML
        public void handleButtonAction(MouseEvent event) {

            if (event.getSource() == btnSignin) {
                //login here
                if (logIn().equals("Success")) {
                    try {
                        //add you loading or delays - ;-)
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        //stage.setMaximized(true);
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Profil.fxml")));
                        stage.setTitle("Gestion User");
                        stage.setScene(scene);
                        stage.show();
                         } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }

    @FXML
    public void handleButtonSignup(MouseEvent event) {
        if (event.getSource() == btnSignup){
            try {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Formulaire.fxml")));
                stage.setTitle("Add User");
                stage.setScene(scene);
                stage.show();
                } catch (IOException ex){
                System.err.println(ex.getMessage());

            }
        }
    }


        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // TODO
            if (con == null) {
                lblErrors.setTextFill(Color.TOMATO);
                lblErrors.setText("Server Error : Check");
            } else {
                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Server is up : Good to go");
            }
        }



        //we gonna use string to check for status
        private String logIn() {
            String status = "Success";
            String email = txtUsername.getText();
            String password = txtPassword.getText();
            if(email.isEmpty() || password.isEmpty()) {
                setLblError(Color.TOMATO, "Empty credentials");
                status = "Error";
            } else {
                //query
                String sql = "SELECT * FROM personne Where email = ? and mdp = ?";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        setLblError(Color.TOMATO, "Enter Correct Email/Password");
                        status = "Error";
                    } else {
                        setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    }
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    status = "Exception";
                }
            }

            return status;
        }

    private void setLblError(Color color, String text) {
            lblErrors.setTextFill(color);
            lblErrors.setText(text);
            System.out.println(text);
        }
    }
