package sample;

import Entities.Reclamation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML

    private Button Buttonid;

    @FXML
    private Button Modifier;

    @FXML
    private TextField tfréclamation;
    @FXML
    private TableView<Reclamation> Reclamations;
    @FXML
    private Label label;

    private Connection connection;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ServiceReclamation sr = new ServiceReclamation();

        TableColumn<Reclamation, String> idRec = new TableColumn<>("Id reclamation");
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        TableColumn<Reclamation, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Reclamation, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Reclamation, String> text = new TableColumn<>("Reclamation");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));

        TableColumn modCol = new TableColumn("Modifier");
        modCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));

        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryModifier
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            final Button modifier = new Button("Modifier");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    modifier.setOnAction(event -> {
                                        Parent loader;
                                        try {
                                            loader = FXMLLoader.load(getClass().getResource("ModifierRec.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                                            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                                            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                                            app_stage.setScene(scene); //This sets the scene as scene

                                            app_stage.show(); // this shows the scene
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    });

                                    setGraphic(modifier);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        modCol.setCellFactory(cellFactoryModifier);
        TableColumn delCol = new TableColumn("Supprimer");
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            final Button delete = new Button("delete");


                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    delete.setOnAction(event -> {
                                        Reclamation meet = getTableView().getItems().get(getIndex());
                                        sr.DeleteRec(meet);
                                        System.out.println(meet);
                                    });

                                    setGraphic(delete);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        delCol.setCellFactory(cellFactoryDelete);


        Reclamations.getColumns().add(idRec);
        Reclamations.getColumns().add(idDate);
        Reclamations.getColumns().add(User);
        Reclamations.getColumns().add(text);
        Reclamations.getColumns().add(modCol);
        Reclamations.getColumns().add(delCol);


        List<Reclamation> list = null;
        try {
            list = sr.AfficherRec();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Reclamation Reclamation : list) {
            Reclamations.getItems().add(Reclamation);
        }

    }


    @FXML
    public void AjouterRec(ActionEvent event) {
        ServiceReclamation sr = new ServiceReclamation();
        Reclamation r = new Reclamation();
        r.setText(tfréclamation.getText());

        sr.AddRec(r);


    }



}

