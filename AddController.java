package Controllers;

import Entity.File;
import helpers.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    String imagePath = null;


    @FXML
    void CleanB(MouseEvent event) {

    }

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    File f = null;
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


        

        if (id.isEmpty() || file.isEmpty() || date_creation.isEmpty()||imagePath== null ) {
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

     /*   if (update == false) {

            query = "INSERT INTO `File`( `id`, `file`, `date_creation`) VALUES (?,?,?)";

        }else{
            query = "UPDATE `File` SET "
                    + "`id`=?,"
                    + "`file`=?,"
                    + "`date_creation`= ? WHERE id_file = '"+formationId+"'";
        }*/
        if (update == false) {

            query = "INSERT INTO `File`( `id`, `file`, `date_creation`, `myfile`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `File` SET "
                    + "`id`=?,"
                    + "`file`=?,"
                    + "`date_creation`= ?,"
                    +"`myfile`= ? WHERE id_file = '"+formationId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, liblField.getText());
            preparedStatement.setString(2, descrF.getText());
            preparedStatement.setString(3, String.valueOf(dateF.getValue()));
            preparedStatement.setString(4, imagePath);


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

    public String handleButtonAction(ActionEvent event) throws IOException {



        FileChooser fc = new FileChooser();


        fc.setInitialDirectory(new java.io.File("C:\\ESPRIT\\Esprit\\helpd\\src\\resources"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("pdf Files", "*.pdf"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("text Files", "*.txt"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("video Files", "*.mp4"));

        java.io.File f = fc.showOpenDialog(null);
        if(f != null)
        {
            System.out.println(f);
        }
        imagePath=f.getPath();
        imagePath =imagePath.replace("\\","\\\\");
        return f.getName();


    }
    void setTextField(int id_file, String id, String file, LocalDate toLocalDate, String myfile) {

        formationId = id_file;
        liblField.setText(id);
        descrF.setText(file);
        dateF.setValue(toLocalDate);

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

