package sample;

import Entities.Message;
import Service.ServiceMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlleurAdminMessage implements Initializable {
    static Stage stageAffichageUnique;
    @FXML
    private TableView<Message> Messages;
    @FXML
    private TextField recherche;
    ObservableList<Message> dataList = FXCollections.observableArrayList();
    SortedList<Message> sortedData;
    FilteredList<Message> filteredData;
    static Message selectionedMessage;

    ServiceMessage sm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sm = new ServiceMessage();
        stageAffichageUnique = new Stage();

        TableColumn<Message, String> idmsg = new TableColumn<>("Id message ");
        idmsg.setCellValueFactory(new PropertyValueFactory<>("id_message"));

        TableColumn<Message, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Message, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Message, String> message = new TableColumn<>("message");
        message.setCellValueFactory(new PropertyValueFactory<>("message"));

        TableColumn delCol = new TableColumn("Supprimer");
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Message, String>, TableCell<Message, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Message, String>, TableCell<Message, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Message, String> param) {
                        final TableCell<Message, String> cell = new TableCell<Message, String>() {

                            final Button delete = new Button("delete");


                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    delete.setOnAction(event -> {
                                        Message meet = getTableView().getItems().get(getIndex());
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Confirmation ");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Vous voulez vraiment supprimer ce message ?");
                                        Optional<ButtonType> action = alert.showAndWait();
                                        if (action.get() == ButtonType.OK)
                                        {  sm.Deletemsg(meet);
                                            Notifications n = Notifications.create()
                                                    .title("Succes")
                                                    .text("message supprimée avec succes")
                                                    .graphic(null)
                                                    .position(Pos.TOP_CENTER);
                                            //  .hideAfter(Duration.seconds(3));
                                            n.showInformation(); }
                                            dataList.clear();
                                        try {
                                            dataList.addAll(sm.Affichermsg());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
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

        TableColumn reponsebouton = new TableColumn("réponse");

        reponsebouton.setCellValueFactory(new PropertyValueFactory<>("reponse"));

        delCol.setCellFactory(cellFactoryDelete);
        Messages.getColumns().add(idmsg);
        Messages.getColumns().add(idDate);
        Messages.getColumns().add(User);
        Messages.getColumns().add(message);
        Messages.getColumns().add(reponsebouton);
        Messages.getColumns().add(delCol);




        List<Message> list = null;
        try {
            list = sm.Affichermsg();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Message Message : list) {
            Messages.getItems().add(Message);
        }

        try { dataList.addAll(sm.Affichermsg()); }
        catch (SQLException e) { e.printStackTrace(); }

        Messages.getItems().addAll(dataList);
        filteredData = new FilteredList<>(dataList, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(msg -> {
                System.out.println(msg);
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();

                if (msg.getMessage().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else { return false;  }
            });
        });
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Messages.comparatorProperty());
        Messages.setItems(sortedData);



        Messages.setOnMouseClicked((MouseEvent event2)
                -> {
            if (event2.getClickCount() >= 2) {
                    selectionedMessage= Messages.getSelectionModel().getSelectedItem();

                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "../GUI/AfficherMsgUnique.fxml"
                                )
                        );

                        Stage stage = new Stage(); //this accesses the window.
                        stage.setScene(
                                new Scene(loader.load())
                        );
                        AfficherMsgUnique controller = loader.getController();
                        controller.initData(selectionedMessage.getId_message());

                        stage.show();

                    } catch (IOException ex) {
                        Logger.getLogger(ControlleurAdminMessage.class.getName()).log(Level.SEVERE, null, ex);
                    }


            }
        });
    }

    public void refreshList(){
            System.out.println("refresh");

                dataList.clear();
            sortedData.removeAll(dataList);
            filteredData.removeAll(dataList);
            dataList = FXCollections.observableArrayList();
            System.out.println(dataList);
            System.out.println(sortedData);
            System.out.println(filteredData);
            Messages.setItems(dataList);
            Messages.refresh();


    }


    public void retourButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../GUI/HomeAdmin.fxml"
                    )
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            stage.setScene(
                    new Scene(loader.load())
            );

            HomeAdmin controller = loader.getController();

            stage.show();
        } catch (IOException ex) {
        }
    }
}
