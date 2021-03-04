package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import com.mysql.cj.conf.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAdmin implements Initializable {
    @FXML
    private Button tfsupprimer;

    private TableView<Reclamation> Reclamations;
    private ServiceReclamation sr;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*

        ServiceReclamation sr = new ServiceReclamation();

        TableColumn<Reclamation, String> idRec = new TableColumn<>("Id reclamation");
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        TableColumn<Reclamation, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Reclamation, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Reclamation, String> text = new TableColumn<>("Reclamation");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));

        TableColumn modCol = new TableColumn("Action");
        TableColumn delCol = new TableColumn();
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            final javafx.scene.control.Button delete = new javafx.scene.control.Button("delete");

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

        */
    }

        }








