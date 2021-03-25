package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InterfaceFormation {

    @FXML
    private Button btnScene1;

    @FXML
    private Button btnScene2;

    @FXML
    void handleBtn1( ) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/myFormation1.fxml"));

        Stage window =(Stage) btnScene1.getScene().getWindow();
        window.setScene(new Scene(root, 1500, 1300));

    }

    @FXML
    public void handleBtn2(ActionEvent event) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/plateformeUI.fxml"));

        Stage window =(Stage) btnScene2.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
/* --module-path "C:\Program Files\Java\openjfx-15.0.1_windows-x64_bin-sdk\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml */