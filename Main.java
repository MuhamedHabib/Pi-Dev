package sample;

import Entities.Message;
import Entities.Reclamation;
import Service.ServiceMessage;
import Service.ServiceReclamation;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage Stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Stage.setTitle("Reclamation");
        Stage.setScene(new Scene(root, 750, 550));
        Stage.show();
        }

    public static void main(String[] args) {
        launch(args);


    }
}
