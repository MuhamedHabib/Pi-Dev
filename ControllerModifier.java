package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channel;
import java.util.ResourceBundle;

public class ControllerModifier implements Initializable {
    private int id_reclamation;

    @FXML
    private Button modifier;
    @FXML
    private TextField tfréclamation2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    void initData(int id) {
        id_reclamation = id;
    }


    public void ModifierRec(ActionEvent event) {

        ServiceReclamation sr = new ServiceReclamation();
        Reclamation r = new Reclamation();
        r.setText(tfréclamation2.getText());
        sr.UpdateRec(r);
    }





}



