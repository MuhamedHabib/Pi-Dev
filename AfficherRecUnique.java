package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;
import org.w3c.dom.Document;
import utils.javaMail;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;


public class AfficherRecUnique implements Initializable {
    @FXML
    private TextField tfréclamation;
    private int id_reclamation;

    @FXML
    private ComboBox type;
    @FXML
    private ComboBox statut;
    @FXML
    private ImageView screenshotView;
    @FXML
    private Button image;

    @FXML
    private Button valider;
    @FXML
    private Button Imprimer;
    @FXML
    private TextField object;
    private String path;
    private ServiceReclamation service;


    ObservableList<String> listStatut = FXCollections.observableArrayList("en cours de traitement", "validée");
    ObservableList<String> listStatut2 = FXCollections.observableArrayList("validée");
    ObservableList<String> listStatut3 = FXCollections.observableArrayList("");

    static Reclamation selectionedReclamation;
    ObservableList<Reclamation> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField NomTXFLD;
    @FXML
    private TextField PrenomTXFLD;
    @FXML
    private TextField EmailTXFLD;
    @FXML
    private TextField TelTXFLD;
    @FXML
    private AnchorPane detail;
    private TableView<Reclamation> Reclamations;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = new ServiceReclamation();

        NomTXFLD.setDisable(true);
        PrenomTXFLD.setDisable(true);
        EmailTXFLD.setDisable(true);
        TelTXFLD.setDisable(true);
        type.getItems().add("Service technique");
        type.getItems().add("Contenu");
        type.setOnAction(event -> {
            System.out.println("Selected:" + type.getValue().toString());
            System.out.println("All:" + type.getItems().toString());
        });

        statut.getItems().add("en attente");
        statut.getItems().add("en cours de traitement");
        statut.getItems().add("validée");
        statut.setOnAction(event -> {
            System.out.println("Selected:" + statut.getValue().toString());
            System.out.println("All:" + statut.getItems().toString());
        });


        tfréclamation.setText((ControllerAdmin.selectionedReclamation.getText()));
        object.setText(ControllerAdmin.selectionedReclamation.getObject());
        type.setValue(ControllerAdmin.selectionedReclamation.getType());
        statut.setValue(ControllerAdmin.selectionedReclamation.getStatut());
    //   screenshotView.setImage(new Image(ControllerAdmin.selectionedReclamation.getScreenshot().toString()));
        screenshotView.setFitHeight(100);
        screenshotView.setFitWidth(100);
        path = new File(ControllerAdmin.selectionedReclamation.getScreenshot()).getName();

        image.setText(path);

        if (statut.getValue().equals("en attente"))
        { statut.setDisable(true); valider.setDisable(true);}

       if (statut.getValue().equals("en cours de traitement")) {
           statut.setItems(listStatut);
        }
       else  { statut.setDisable(true); valider.setDisable(true);}
        screenshotView.setOnMouseClicked((MouseEvent event2)
                -> {
            if (event2.getClickCount() >= 1) {
                //  if (AfficherReclamationController.selectionedReclamation.getScreenshot() != null)

                Stage window = new Stage();
                window.setMinWidth(250);
                ImageView imagevPOPUP = new ImageView(new Image(ControllerAdmin.selectionedReclamation.getScreenshot()));
                imagevPOPUP.setFitHeight(576);
                imagevPOPUP.setFitWidth(1024);

                VBox layout = new VBox(10);
                layout.getChildren().addAll(imagevPOPUP);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout);
                window.setScene(scene);
                window.show();

            }


        });

    }

    @FXML

    public void Imprimer(javafx.event.ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window window = ((Node) event.getSource()).getScene().getWindow();
            job.showPrintDialog(window); // Window must be your main Stage
            job.printPage(detail);
            job.endJob();
        }
    }
    void initData(int id) {
        id_reclamation = id;
    }


    public void valider(ActionEvent event) {

       Reclamation r = ControllerAdmin.selectionedReclamation;

        System.out.println(r);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de validation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez vous valider la réclamation  ");
        ButtonType buttonTypeValider = new ButtonType("valider");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeValider, buttonTypeCancel);

        ImageView icon = new ImageView("images/question.jpg");
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeValider) {
            r.setStatut("validée");
            valider.setDisable(true);
            r.setDate_validation(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            service.UpdateRecStatut(r);

            Notifications n = Notifications.create()
                    .title("Succes")
                    .text("Reclamation validée avec succés et client notifé")
                    .graphic(null)
                    .position(Pos.TOP_CENTER);
            //  .hideAfter(Duration.seconds(3));
            n.showInformation();
            dataList.clear();
            try {
                dataList.addAll(service.AfficherRec());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String Object = "Traitement de réclamation numéro " + r.getId_reclamation() + "";
                String Corps = "Merci de l'intérêt que vous portez à notre plateforme \n Bonjour \n Nous venons vous informer que votre réclamation numéro " + r.getId_reclamation() + " crée le " + r.getDate_validation() + " est bien traitée. \n Vous pouvez consulter l'avancement dans votre partie d'affichage sur le profil de HelpDesk. ";
                javaMail.sendMail("inovvat@gmail.com", Object, Corps);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(r); // ... user chose OK
        }

    }
}

