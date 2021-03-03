package sample;

import Entity.formationhd;
import helpers.DbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddController implements Initializable {

    public TextField descrF;
    @FXML
    private TextField liblField;

 

    @FXML
    private DatePicker dateF;

    @FXML
    void AddB(MouseEvent event) {

    }

    @FXML
    void CleanB(MouseEvent event) {

    }

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    formationhd f = null;
    private boolean update;
    int formationId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void AddB(javafx.scene.input.MouseEvent mouseEvent) {
        connection = DbConnect.getConnect();
        String libelle_formation = liblField.getText();

        String description = descrF.getText();
        String date_creation = String.valueOf(dateF.getValue());


        if (libelle_formation.isEmpty() || description.isEmpty() || date_creation.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            CleanB();

        }
    }


    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `formationhd`( `libelle_formation`, `description`, `date_creation`) VALUES (?,?,?)";

        }else{
            query = "UPDATE `formationhd` SET "
                    + "`description`=?,"
                    + "`date_creation`=?,"
                    + "`email`= ? WHERE id_formation = '"+formationId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, liblField.getText());
            preparedStatement.setString(2, descrF.getText());
            preparedStatement.setString(3, String.valueOf(dateF.getValue()));


            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void CleanB() {
        liblField.setText(null);
        descrF.setText(null);
        dateF.setValue(null);
    }
}

