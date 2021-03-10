package Controllers;

import Entity.formationhd;
import helpers.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddController implements Initializable {

   // public Button fileChooser;
    public AnchorPane anchorpane;

    public TextField descrF;
    @FXML
    private TextField liblField;
    @FXML
    private DatePicker dateF;



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
        String id = liblField.getText();

        String file = descrF.getText();
        String date_creation = String.valueOf(dateF.getValue());


        

        if (id.isEmpty() || file.isEmpty() || date_creation.isEmpty() ) {
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

            query = "INSERT INTO `formationhd`( `id`, `file`, `date_creation`) VALUES (?,?,?)";

        }else{
            query = "UPDATE `formationhd` SET "
                    + "`id`=?,"
                    + "`file`=?,"
                    + "`date_creation`= ? WHERE id_file = '"+formationId+"'";
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

    void setUpdate(boolean b) {
        this.update = b;

    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        FileChooser fileChooser =new FileChooser();
        fileChooser.setTitle("Open File Dialog");
       Stage stage =(Stage)anchorpane.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            Desktop desktop =Desktop.getDesktop();
            desktop.open(file);
        }




    }

}

/*void setTextField(int id_formation , String description, LocalDate toLocalDate, String adress, String email) {

        formationtId = id;
        nameFld.setText(name);
        birthFld.setValue(toLocalDate);
        adressFld.setText(adress);
        emailFld.setText(name);

    }
    void setUpdate(boolean b) {
        this.update = b;

    }*/
/*  void setTextField(int id_formation, String libelle_formation,String description, LocalDate toLocalDate) {

        formationId = id_formation;
        liblField.setText(libelle_formation);
        descrF.setText(description);
        dateF.setValue(toLocalDate);

    }*/
