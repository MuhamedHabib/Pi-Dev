package sample;

import Entities.Reclamation;
import Service.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;


public class ControllerModifier implements Initializable {
    private int id_reclamation;

    Connection cnx;

    private ServiceReclamation service;
    public TableView<Reclamation> Reclamations;

    private int idRec;

    @FXML
    private Button modifier;
    @FXML
    private ComboBox type;
    @FXML
    private TextArea tfréclamation2;
    @FXML
    private Button annuler;
    @FXML
    private Label alerte;
    @FXML
    private ImageView screenshotView;
    private String path2;
    @FXML
    private Button image;
    private String path;

    @FXML
    private TextField object;
    File selectedFile;
    static Reclamation selectionedReclamation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        service = new ServiceReclamation();

        type.setValue(Controller.selectionedReclamation.getType());
        object.setText(Controller.selectionedReclamation.getObject());
        tfréclamation2.setText(Controller.selectionedReclamation.getText());

//        screenshotView.setImage(new Image(Controller.selectionedReclamation.getScreenshot()));
        screenshotView.setFitHeight(100);
        screenshotView.setFitWidth(100);
        path=new File(Controller.selectionedReclamation.getScreenshot()).getName();
        image.setText(path);

        type.getItems().add("Service technique");
        type.getItems().add("Contenu");
        type.setOnAction(event -> {
            System.out.println("Selected:" + type.getValue().toString());
            System.out.println("All:" + type.getItems().toString());
        });
        annuler.setOnAction(event -> {
        Parent loader;
        try {
            loader = FXMLLoader.load(getClass().getResource("../GUI/reclamation.fxml")); //Creates a Parent called loader and assign it as ScReen2.FXML

            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"

            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            app_stage.setScene(scene); //This sets the scene as scene

            app_stage.show(); // this shows the scene
        } catch (IOException ex) { ex.printStackTrace(); }

    });
        screenshotView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        screenshotView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path2 = null;
                    for (File file : db.getFiles()) {
                        path2 = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath()="C:\Users\X\Desktop\ScreenShot.6.png"
                        screenshotView.setImage(new Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);
                        image.setText(path2);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        screenshotView.setImage(new Image("file:C:\\Users\\Mariem.DESKTOP-L3DPUNQ\\IdeaProjects\\untitled\\src\\images\\drag-drop-upload-1.gif"));

    }

    @FXML
    private void image(ActionEvent event) throws MalformedURLException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            path2 = selectedFile.getName();
//                path = selectedFile.toURI().toURL().toExternalForm();
            screenshotView.setImage(new Image(selectedFile.toURI().toURL().toString()));
            screenshotView.setFitHeight(150);
            screenshotView.setFitWidth(250);
            image.setText(path2);

        }

    }

    void initData(int id) {
        id_reclamation = id;
    }

    public void ModifierRec(ActionEvent event) {
       Reclamation r =Controller.selectionedReclamation;
        System.out.println(r);
        if (tfréclamation2.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez ajouter votre réclamation ");

            alert.showAndWait();
            tfréclamation2.requestFocus();
        }
        else {
            r.setText(tfréclamation2.getText());
            r.setObject(object.getText());
            r.setType(type.getValue().toString());
           // r.setScreenshot(image.getText());
        service.UpdateRec(r);
            Notifications n = Notifications.create()
                    .title("Succès")
                    .text("Reclamation modifiée avec succès")
                    .graphic(null)
                    .position(Pos.TOP_CENTER);
            // .hideAfter(Duration.ofSeconds(3));
            n.showInformation();

        init();
    }

    }

    public void init()
    {  tfréclamation2.setText("");object.setText("");}




}



