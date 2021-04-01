package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;



public class Home {


    @FXML
    private Button btn;

    @FXML
    private Button btnq;



    public void test(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/testfxml1.fxml"));
        Stage  window =(Stage) btn.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }

    public void quiz(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/quizfxml1.fxml"));
        Stage  window =(Stage) btnq.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}
