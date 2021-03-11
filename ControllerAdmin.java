package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.awt.Button;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerAdmin implements Initializable {


    @FXML
    private Button tfsupprimer;

    @FXML
    private TableView<Reclamation> Reclamations;
    private Connection connection;

    @FXML
    private Button valider;

    @FXML
    private TextField recherche;
    final ObservableList<Reclamation> dataList = FXCollections.observableArrayList();



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

        TableColumn<Reclamation, String> statut = new TableColumn<>("statut");
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        statut.setPrefWidth(135);
        statut.setResizable(false);

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
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    String text = Reclamation.getStatut();
                                    if (text.equals("en cours de traitement")) { progress.setProgress(0.5); }
                                        else if (text.equals("validée")) { progress.setProgress(1); }
                                        else { progress.setProgress(0);}
                                    setGraphic(progress);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        progressCol.setCellFactory(cellFactoryProgress);

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
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    delete.setDisable(true);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());

                                    long diffDays = diff.toDays();
                                    System.out.println(diffDays);
                                    if (diffDays > 0.1 && !Reclamation.getStatut().equals("validée")) { delete.setDisable(false);
                                        Reclamation.setStatut("en cours de traitement");

                                        delete.setOnAction(event -> {
                                            Reclamation meet = getTableView().getItems().get(getIndex());
                                            sr.DeleteRec(meet);
                                            dataList.clear();
                                            try {
                                                dataList.addAll(sr.AfficherRecUser());
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }); }
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
                    public TableCell<Entities.Reclamation, String> call(TableColumn<Entities.Reclamation, String> reclamationStringTableColumn) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
                            final javafx.scene.control.Button valider = new javafx.scene.control.Button("Valider");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    valider.setDisable(true);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
                                    long diffDays = diff.toDays();
                                    System.out.println("diff: "+diffDays);

                                    if(diffDays>0.1){
                                        valider.setDisable(false);
                                       // if(!Reclamation.getStatut().equals("validée"))
                                        //{
                                            Reclamation.setStatut("en cours de traitement");
                                            sr.UpdateRecStatut(Reclamation);
                                      //  }
                                        //valider.setDisable(false);
                                       // Reclamation.setStatut("validée");
                                        valider.setOnAction(event -> {
                                            Reclamation meet = getTableView().getItems().get(getIndex());
                                            meet.setStatut("validée");
                                            sr.UpdateRecStatut(meet);
                                            valider.setDisable(true);
                                            dataList.clear();
                                            try {
                                                dataList.addAll(sr.AfficherRec());
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            System.out.println(Reclamation); });
                                    }
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
        Reclamations.getColumns().add(User);
        Reclamations.getColumns().add(text);
        Reclamations.getColumns().add(statut);
        Reclamations.getColumns().add(progressCol);
        Reclamations.getColumns().add(delCol);
        Reclamations.getColumns().add(traitement);

        List<Reclamation> list = null;
        try { list = sr.AfficherRec();}
        catch (SQLException e) { e.printStackTrace(); }
        for (Reclamation Reclamation : list) { Reclamations.getItems().add(Reclamation); }
        final ObservableList<Reclamation> dataList = FXCollections.observableArrayList();

        try { dataList.addAll(sr.AfficherRec());
            System.out.println(dataList);}
        catch (SQLException e1) { e1.printStackTrace(); }

        Reclamations.getItems().addAll(dataList);
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
        sortedData.comparatorProperty().bind(Reclamations.comparatorProperty());
        Reclamations.setItems(sortedData);
    }

    }