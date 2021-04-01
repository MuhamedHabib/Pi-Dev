package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import utils.javaMail;

import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControllerAdmin implements Initializable {

    static Stage stageAffichageUnique;

    @FXML
    private Button tfsupprimer;
    @FXML
    private HBox hbox;
    @FXML
    private TableView<Reclamation> Reclamations;
    private Connection connection;
    @FXML
    private Button valider;
    @FXML
    private javafx.scene.control.Button archive;
    @FXML
    private TextField recherche;
   // @FXML
   // private ComboBox<String> typeRecherche;
    private static ComboBox<String> typeRechercheStatus;
   // ObservableList<String> listeTypeRecherche = FXCollections.observableArrayList("Tout","statut");
    ObservableList<Reclamation> dataList = FXCollections.observableArrayList();
    static Reclamation selectionedReclamation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceReclamation sr = new ServiceReclamation();
        stageAffichageUnique = new Stage();

/*
        typeRecherche.setItems(listeTypeRecherche);
        typeRecherche.setValue("Tout");
*/
        TableColumn<Reclamation, String> idRec = new TableColumn<>("Id reclamation");
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        TableColumn<Reclamation, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));


        TableColumn<Reclamation, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Reclamation, String> idDate_valisation = new TableColumn<>("date validation");
        idDate_valisation.setCellValueFactory(new PropertyValueFactory<>("date_validation"));

        TableColumn<Reclamation, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Reclamation, String> text = new TableColumn<>("Reclamation");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));

        TableColumn<Reclamation, String> sujet = new TableColumn<>("Sujet");
        sujet.setCellValueFactory(new PropertyValueFactory<>("object"));

        TableColumn<Reclamation, String> statut = new TableColumn<>("statut");
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        statut.setPrefWidth(135);
        statut.setResizable(false);

        TableColumn<Reclamation, String> screenshotColumn = new TableColumn<>("Image");
//        screenshotColumn.setMinWidth(100);
        screenshotColumn.setCellValueFactory(new PropertyValueFactory<>("screenshot"));
        screenshotColumn.setStyle("-fx-alignment: CENTER;");
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryImage
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    String path;

                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            ImageView screenshotView = new ImageView();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    String dir = System.getProperty("user.dir");//get project source path
                                    File dest = new File(dir + "\\images\\" + Reclamation.getScreenshot());//add the full path /ressources + file name
                                    System.out.println(dest.getAbsolutePath());
                                    try {
                                        screenshotView.setImage(new Image(dest.toURI().toURL().toString()));

                                        screenshotView.setFitWidth(40);
                                        screenshotView.setFitHeight(40);
                                        setGraphic(screenshotView);
                                        setText(null);
                                    }    catch (MalformedURLException e) {
                                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE,null,e);                                    }

                                }
                            }
                        };

                        cell.setOnMouseClicked((MouseEvent event2)
                                -> {
                            if (event2.getClickCount() == 1) {
                                if (Reclamations.getSelectionModel().getSelectedItem() != null && !Reclamations.getSelectionModel().getSelectedItem().getScreenshot().contains("null")) {
                                    Stage window = new Stage();

                                    window.setMinWidth(250);
                                    ImageView imagevPOPUP = new ImageView(new Image(Reclamations.getSelectionModel().getSelectedItem().getScreenshot()));
                                    imagevPOPUP.setFitHeight(576);
                                    imagevPOPUP.setFitWidth(1024);
                                    VBox layout = new VBox(10);
                                    layout.getChildren().addAll(imagevPOPUP);
                                    layout.setAlignment(Pos.CENTER);
                                    //Display window and wait for it to be closed before returning
                                    Scene scene = new Scene(layout);
                                    window.setScene(scene);
                                    window.show();

                                }
                            }

                        });

                        return cell;
                    }
                };

        screenshotColumn.setCellFactory(cellFactoryImage);
        TableColumn<Reclamation, Double> progressCol = new TableColumn("Avancement");
        progressCol.setCellValueFactory(new PropertyValueFactory<Reclamation, Double>(
                ""));
        progressCol.setCellFactory(ProgressBarTableCell.<Reclamation>forTableColumn());
        Callback<TableColumn<Reclamation, Double>, TableCell<Reclamation, Double>> cellFactoryProgress =
                new Callback<TableColumn<Reclamation, Double>, TableCell<Reclamation, Double>>() {
                    @Override
                    public TableCell<Reclamation, Double> call(TableColumn<Reclamation, Double> reclamationStringTableColumn) {
                        final TableCell<Reclamation, Double> cell = new TableCell<Reclamation, Double>() {
                            ProgressBar progress = new ProgressBar(0);

                            @Override
                            public void updateItem(Double item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    String text = Reclamation.getStatut();
                                    if (text.equals("en cours de traitement")) {
                                        progress.setProgress(0.5);
                                    } else if (text.equals("validée")) {
                                        progress.setProgress(1);
                                    } else {
                                        progress.setProgress(0);
                                    }
                                    setGraphic(progress);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        progressCol.setCellFactory(cellFactoryProgress);
        TableColumn delCol = new TableColumn("Suppression");
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
                                    delete.setDisable(true);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(java.time.LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
                                    long diffDays = diff.toDays();

                                    if (diffDays > 0.1 && !Reclamation.getStatut().equals("validée")) {
                                        delete.setDisable(false);                                        //delete.setDisable(true);
                                        if (!Reclamation.getStatut().equals("validée")) {
                                            Reclamation.setStatut("en cours de traitement");
                                            sr.UpdateRecStatut(Reclamation);
                                        }
                                    }


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
                                                    .text("Reclamation supprimée avec succes")
                                                    .graphic(null)
                                                    .position(Pos.TOP_CENTER);
                                            //  .hideAfter(Duration.seconds(3));
                                            n.showInformation(); }
                                        dataList.clear();
                                        try {
                                            dataList.addAll(sr.AfficherRec());
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
        TableColumn traitement = new TableColumn("Traitement");
        traitement.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryTraitement
                = //
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                            final javafx.scene.control.Button valider = new javafx.scene.control.Button("Valider");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    valider.setDisable(true);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
                                    long diffDays = diff.toDays();
                                    System.out.println("diff: " + diffDays);

                                    if (diffDays > 0.1) {
                                        valider.setDisable(false);
                                        if (Reclamation.getStatut().equals("validée")) {
                                            valider.setDisable(true);
                                        }
                                    }

                                    valider.setOnAction(event -> {
                                        selectionedReclamation = getTableView().getItems().get(getIndex());
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Confirmation de validation");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Voulez vous valider la réclamation numéro "+selectionedReclamation.getId_reclamation()+" ? ");
                                        ButtonType buttonTypeValider = new ButtonType("valider");
                                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                                        alert.getButtonTypes().setAll(buttonTypeValider, buttonTypeCancel);

                                        ImageView icon = new ImageView("images/question.jpg");
                                        icon.setFitHeight(48);
                                        icon.setFitWidth(48);
                                        alert.getDialogPane().setGraphic(icon);
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == buttonTypeValider){
                                            selectionedReclamation.setStatut("validée");
                                            valider.setDisable(true);
                                            selectionedReclamation.setDate_validation(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                                            sr.UpdateRecStatut(selectionedReclamation);

                                            Notifications n = Notifications.create()
                                                    .title("Succes")
                                                    .text("Reclamation valid&e avec succés et client notifé")
                                                    .graphic(null)
                                                    .position(Pos.TOP_CENTER);
                                            //  .hideAfter(Duration.seconds(3));
                                            n.showInformation();
                                            dataList.clear();
                                            try {
                                                dataList.addAll(sr.AfficherRec());
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                String Object = "Traitement de réclamation numéro "+selectionedReclamation.getId_reclamation()+"";
                                                String Corps = "Merci de l'intérêt que vous portez à notre plateforme \n Bonjour \n Nous venons vous informer que votre réclamation numéro "+selectionedReclamation.getId_reclamation()+" crée le "+selectionedReclamation.getDate_validation()+" est bien traitée. \n Vous pouvez consulter l'avancement dans votre partie d'affichage sur le profil de HelpDesk. ";
                                                javaMail.sendMail("inovvat@gmail.com", Object, Corps);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            System.out.println(selectionedReclamation); // ... user chose OK
                                        }


                                    });
                                    setGraphic(valider);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }

                };


        traitement.setCellFactory(cellFactoryTraitement);
        Reclamations.getColumns().add(idRec);
        Reclamations.getColumns().add(idDate);
        Reclamations.getColumns().add(idDate_valisation);
        Reclamations.getColumns().add(User);
        Reclamations.getColumns().add(type);
        Reclamations.getColumns().add(text);
        Reclamations.getColumns().add(sujet);
        Reclamations.getColumns().add(screenshotColumn);
        Reclamations.getColumns().add(statut);
        Reclamations.getColumns().add(progressCol);
        Reclamations.getColumns().add(delCol);
        Reclamations.getColumns().add(traitement);



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


        try {
            dataList.addAll(sr.AfficherRec());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Reclamations.getItems().addAll(dataList);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rec -> {
                // If filter text is empty, display all persons.
                System.out.println(rec);
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (rec.getText().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id.
                } else {
                    return false;
                }
            });
        });
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Reclamations.comparatorProperty());
        Reclamations.setItems(sortedData);


        archive.setOnAction(event -> {
            Parent loader;
            try {
                loader = FXMLLoader.load(getClass().getResource("../GUI/archive.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

                Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                app_stage.setScene(scene); //This sets the scene as scene
                app_stage.show(); // this shows the scene
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        Reclamations.setOnMouseClicked((MouseEvent event2)
                -> {
            if (event2.getClickCount() >= 2) {
               // if (Reclamations.getSelectionModel().getSelectedItem() != null) {
                 selectionedReclamation= Reclamations.getSelectionModel().getSelectedItem();

                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../GUI/AfficherRecUnique.fxml"));
                        Scene scene = new Scene(root);
                        stageAffichageUnique.setScene(scene);
                        stageAffichageUnique.show();

                    } catch (IOException ex) {
                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }

               // }
            }
        });
        Tooltip t2 = new Tooltip("Modifier la réclamation en cliquant 2 fois");
        Reclamations.setTooltip(t2);
    }

public void retourButton(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../GUI/Home.fxml"
                )
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        stage.setScene(
                new Scene(loader.load())
        );

        Home controller = loader.getController();

        stage.show();
    } catch (IOException ex) {
    }
}
}