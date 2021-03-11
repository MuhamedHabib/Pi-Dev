package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;


public class ControllerModifier implements Initializable {
    private int id_reclamation;

    Connection cnx;

    private ServiceReclamation service;
    public TableView<Reclamation> Reclamations;

    private int idRec;

    @FXML
    private Button modifier;
    @FXML
    private TextField tfréclamation2;
    @FXML
    private Button annuler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        service = new ServiceReclamation();


        annuler.setOnAction(event -> {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("reclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) { ex.printStackTrace(); }

    }); }

    void initData(int id) {
        id_reclamation = id;
    }

    public void ModifierRec(ActionEvent event) {
       Reclamation r = service.findbyId(id_reclamation) ;
        System.out.println(r);
        r.setText(tfréclamation2.getText());
        service.UpdateRec(r);
        init();
    }

    public void init()
    {  tfréclamation2.setText("");
        modifier.setDisable(false);
    }




}



