package sample;

import Entities.Reclamation;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ProgressBarTableCell;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class Controller implements Initializable {

    @FXML
    private Button Buttonid;
    @FXML
    private Button Modifier;
    @FXML
    private TextField tfréclamation;
    @FXML
    public TableView<Reclamation> Reclamations;
    @FXML
    private Label label;
    @FXML
    private TextField recherche;
    private Connection connection;
    private String statut;
    private TextField tfréclamation2;
    final ObservableList<Reclamation> dataList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceReclamation sr = new ServiceReclamation();

        TableColumn<Reclamation, String> idRec = new TableColumn<>("Id reclamation");
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        TableColumn<Reclamation, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Reclamation, String> text = new TableColumn<>("Reclamation");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));

        TableColumn<Reclamation, String> statut = new TableColumn<>("statut");
        statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        statut.setPrefWidth(150);

        TableColumn<Reclamation, Double> progressCol = new TableColumn("Avancement");
        progressCol.setCellValueFactory(new PropertyValueFactory<Reclamation, Double>(
                ""));
        progressCol.setCellFactory(ProgressBarTableCell.<Reclamation> forTableColumn());
        Callback<TableColumn<Reclamation, Double>, TableCell<Reclamation, Double>> cellFactoryProgress =
                new Callback<TableColumn<Reclamation, Double>, TableCell<Reclamation, Double>>() {
                    @Override
                    public TableCell<Reclamation, Double> call(TableColumn<Reclamation, Double> reclamationStringTableColumn) {
                        final TableCell<Reclamation, Double> cell = new TableCell<Reclamation, Double>() {
                            ProgressBar progress= new ProgressBar(0);

                            @Override
                            public void updateItem(Double item, boolean empty)
                                  {
                                super.updateItem(item, empty);
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    String text = Reclamation.getStatut();
                                   if  (text.equals("en cours de traitement")) { progress.setProgress(0.5); }
                                   else if ( text.equals("validée")) { progress.setProgress(1);}
                                   else { progress.setProgress(0);}
                                    setGraphic(progress);
                                    setText(null); }
                                  }
                        };
                        return cell; }
                };

        progressCol.setCellFactory(cellFactoryProgress);

      /*  TableColumn<Reclamation, String> Operation = new TableColumn<>("Operation");
        text.setCellValueFactory(new PropertyValueFactory<>("Operation"));
*/
        TableColumn modCol = new TableColumn("Modifier");
        modCol.setCellValueFactory(new PropertyValueFactory<>(""));

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
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    modifier.setDisable(false);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(java.time.LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
                                    long diffDays = diff.toDays();

                                    if(diffDays>0.1){
                                        modifier.setDisable(true);
                                        if(!Reclamation.getStatut().equals("validée"))
                                        {
                                            Reclamation.setStatut("en cours de traitement");
                                            sr.UpdateRecStatut(Reclamation);
                                        }
                                    }
                                    modifier.setOnAction(event -> {
                                        try {
                                            FXMLLoader loader = new FXMLLoader(
                                                    getClass().getResource(
                                                            "ModifierRec.fxml"
                                                    )
                                            );

                                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                                            stage.setScene(
                                                    new Scene(loader.load())
                                            );

                                            ControllerModifier controller = loader.getController();
                                            controller.initData(Reclamation.getId_reclamation());

                                            stage.show();
                                        } catch (IOException ex) {
                                        }
                                    });
                                    setGraphic(modifier);
                                    setText(null); }
                            }
                        };
                        return cell;
                    }
                };

        modCol.setCellFactory(cellFactoryModifier);
        TableColumn delCol = new TableColumn("Supprimer");
        delCol.setCellValueFactory(new PropertyValueFactory<>(""));
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
                                if (empty) { setGraphic(null);setText(null); }
                                else {
                                    delete.setDisable(false);
                                    Reclamation Reclamation = getTableView().getItems().get(getIndex());
                                    LocalDate d1 = LocalDate.parse(java.time.LocalDate.now().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    LocalDate d2 = LocalDate.parse(Reclamation.getDate_creation().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
                                    Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
                                    long diffDays = diff.toDays();

                                    if(diffDays>0.1){
                                        delete.setDisable(true);
                                        if(!Reclamation.getStatut().equals("validée"))
                                         {
                                            Reclamation.setStatut("en cours de traitement");
                                            sr.UpdateRecStatut(Reclamation);
                                        }
                                    }


                                    delete.setOnAction(event -> {
                                        Reclamation meet = getTableView().getItems().get(getIndex());
                                        sr.DeleteRec(meet);
                                        dataList.clear();
                                        try {
                                            dataList.addAll(sr.AfficherRecUser());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        System.out.println(meet); });
                                    setGraphic(delete);
                                    setText(null); }
                            }
                        };
                        return cell;
                    }
                };

        delCol.setCellFactory(cellFactoryDelete);
      //  Operation.getColumns().addAll(modCol, delCol);
        Reclamations.getColumns().add(idRec);
        Reclamations.getColumns().add(idDate);
        Reclamations.getColumns().add(text);
        Reclamations.getColumns().add(statut);
        Reclamations.getColumns().add(progressCol);
        Reclamations.getColumns().add(modCol);
        Reclamations.getColumns().add(delCol);

        List<Reclamation> list = null;
        try { list = sr.AfficherRecUser();System.out.println(list); }
        catch (SQLException e) { e.printStackTrace(); }
        for (Reclamation Reclamation : list)
        { Reclamations.getItems().add(Reclamation); }


        try { dataList.addAll(sr.AfficherRecUser()); }
        catch (SQLException e) { e.printStackTrace(); }

        Reclamations.getItems().addAll(dataList);
        FilteredList<Reclamation> filteredData = new FilteredList<>(dataList, b -> true);
           recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(rec -> {
                System.out.println(rec);
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();

                if (rec.getText().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else { return false;  }
            });
        });
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Reclamations.comparatorProperty());
        Reclamations.setItems(sortedData);
    }
    @FXML
    public void AjouterRec(ActionEvent event) throws SQLException {
        ServiceReclamation sr = new ServiceReclamation();
        Reclamation r = new Reclamation();
        r.setText(tfréclamation.getText());
        sr.AddRec(r);
        init();
        dataList.clear();
        try {
            dataList.addAll(sr.AfficherRec());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void init() throws SQLException { tfréclamation.setText("");Buttonid.setDisable(false); }


}