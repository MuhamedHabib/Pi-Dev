package Controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class PlateformeUI {
    @FXML
    private Button goToHome;
    public void btnHome(ActionEvent event) throws Exception {





        Parent root  = FXMLLoader.load(getClass().getResource("../gui/interfaceFormation.fxml"));

        Stage window =(Stage) goToHome.getScene().getWindow();
        window.setScene(new Scene(root, 1500, 1200));
    }
}
