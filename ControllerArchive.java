package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerArchive implements Initializable {

    @FXML
    private TableView<Reclamation> archive;

    final ObservableList<Reclamation> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField recherche;
    @FXML
    private Button annuler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceReclamation sr = new ServiceReclamation();

        TableColumn<Reclamation, String> idRec = new TableColumn<>("Id reclamation");
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        TableColumn<Reclamation, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Reclamation, String> idDate = new TableColumn<>("Date creation");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Reclamation, String> idDate_valisation = new TableColumn<>("date validation");
        idDate_valisation.setCellValueFactory(new PropertyValueFactory<>("date_validation"));

        TableColumn<Reclamation, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Reclamation, String> sujet = new TableColumn<>("Sujet");
        sujet.setCellValueFactory(new PropertyValueFactory<>("object"));


        TableColumn<Reclamation, String> text = new TableColumn<>("Reclamation");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));



        TableColumn delCol = new TableColumn("supprimer");
        delCol.setCellValueFactory(new PropertyValueFactory<>(""));
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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Confirmation ");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Vous voulez vraiment supprimer cette réclamation ?");
                                        Optional<ButtonType> action = alert.showAndWait();
                                        if (action.get() == ButtonType.OK)
                                        {  sr.DeleteRec(meet);
                                            Notifications n = Notifications.create()
                                                    .title("Succes")
                                                    .text("Reclamation supprimée avec succés")
                                                    .graphic(null)
                                                    .position(Pos.TOP_CENTER);
                                            //  .hideAfter(Duration.seconds(3));
                                            n.showInformation(); }
                                            dataList.clear();
                                        try {
                                            dataList.addAll(sr.AfficherArchive());
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



    delCol.setCellFactory(cellFactoryDelete);
        archive.getColumns().add(idRec);
        archive.getColumns().add(idDate);
        archive.getColumns().add(type);
        archive.getColumns().add(User);
        archive.getColumns().add(text);
        archive.getColumns().add(sujet);
        archive.getColumns().add(idDate_valisation);
        archive.getColumns().add(delCol);

        List<Reclamation> list = null;
        try {
            list = sr.AfficherArchive();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Reclamation Reclamation : list) {
            archive.getItems().add(Reclamation);
        }
        try {
            dataList.addAll(sr.AfficherArchive());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        archive.getItems().addAll(dataList);
    // Wrap the ObservableList in a FilteredList (initially display all data).
    FilteredList<Reclamation> filteredData = new FilteredList<>(dataList, b -> true);

    // 2. Set the filter Predicate whenever the filter changes.
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(rec -> {
            // If filter text is empty, display all persons.
            System.out.println(rec);
            if (newValue == null || newValue.isEmpty()) { return true; }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (rec.getText().toLowerCase().contains(lowerCaseFilter)) { return true; // Filter matches id.
            } else { return false;  }
        });
    });
    SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(archive.comparatorProperty());
        archive.setItems(sortedData);


        annuler.setOnAction(event -> {
            Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("../GUI/Admin.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

                app_stage.setScene(scene); //This sets the scene as scene

                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

    }

    }

