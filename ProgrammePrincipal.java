package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ProgrammePrincipal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            stage.initStyle(StageStyle.DECORATED);
            stage.setMaximized(false);
            stage.setTitle("Authentification !");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }



}
