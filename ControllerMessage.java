package sample;

import Entities.Message;
import Service.ServiceMessage;

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
import org.controlsfx.control.Notifications;
import utils.JavaSoundRecorder;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControllerMessage implements Initializable {
    @FXML
    private TextArea tfmessage;
    @FXML
    private Button envoyer;
    @FXML
    private Button ajouterRec;
    @FXML
    private TableView<Message> Messages;
    @FXML
    private ToggleButton Record;

    @FXML
    private TextField recherche;
    @FXML
    private Label alerte;
    final ObservableList<Message> dataList = FXCollections.observableArrayList();
    private JavaSoundRecorder javaSoundRecorder;
    private String wavfile;
    static Message selectionedMessage;
    static Stage stageAffichageUnique;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceMessage sm = new ServiceMessage();
        stageAffichageUnique = new Stage();

        TableColumn<Message, String> idmsg = new TableColumn<>("Id message ");
        idmsg.setCellValueFactory(new PropertyValueFactory<>("id_message"));

        TableColumn<Message, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Message, String> message = new TableColumn<>("message");
        message.setCellValueFactory(new PropertyValueFactory<>("message"));

        TableColumn<Message, String> reponse = new TableColumn<>("réponse");
        reponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));

        Messages.getColumns().add(idmsg);
        Messages.getColumns().add(idDate);
        Messages.getColumns().add(message);
        Messages.getColumns().add(reponse);


        List<Message> list = null;
        try {
            list = sm.AffichermsgUser();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Message Message : list) {
            Messages.getItems().add(Message);
        }

        try { dataList.addAll(sm.AffichermsgUser()); }
        catch (SQLException e) { e.printStackTrace(); }

        Messages.getItems().addAll(dataList);
        FilteredList<Message> filteredData = new FilteredList<>(dataList, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(msg -> {
                System.out.println(msg);
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();

                if (msg.getMessage().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else { return false;  }
            });
        });
        SortedList<Message> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Messages.comparatorProperty());
        Messages.setItems(sortedData);

        ajouterRec.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "../GUI/reclamation.fxml")
                );
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
                stage.setScene(
                        new Scene(loader.load()));
                Controller controller = loader.getController();
                stage.show();
            } catch (IOException ex) { }
        });


        Messages.setOnMouseClicked((MouseEvent event2)
                -> {
            if (event2.getClickCount() >= 2) {
                if (Messages.getSelectionModel().getSelectedItem() != null) {
                    selectionedMessage= Messages.getSelectionModel().getSelectedItem();

                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "../GUI/AfficherMsgUniqueU.fxml"
                                )
                        );

                        Stage stage = new Stage(); //this accesses the window.
                        stage.setScene(
                                new Scene(loader.load())
                        );
                        AfficherMsgUniqueU controller = loader.getController();
                        controller.initData(selectionedMessage.getId_message());

                        stage.show();


                    } catch (IOException ex) {
                        Logger.getLogger(ControllerMessage.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        Tooltip t = new Tooltip("Afficher les détails en cliquant sur le message");
        Tooltip t2 = new Tooltip("Vous pouvez recorder votre message");
        Messages.setTooltip(t);
        Record.setTooltip(t2);
        

    }

    @FXML
    public void Ajoutermsg(ActionEvent event) {
        ServiceMessage sm = new ServiceMessage();
        Message m = new Message();
        System.out.println(m);
        if (tfmessage.getText().isEmpty())
        {
            alerte.setText("vous devez ajouter votre message");
            tfmessage.requestFocus();
            System.out.println("rien");
        }

        else if(Record.isArmed()){
            System.out.println("done");
        }

        else {
            m.setMessage(tfmessage.getText());
            m.setRecord(wavfile);
            sm.Addmsg(m);
            alerte.setVisible(false);
            System.out.println("envoyé");
            Notifications n = Notifications.create()
                    .title("Succès")
                    .text("Message envoyé  avec succès")
                    .graphic(null)
                    .position(Pos.TOP_CENTER);
            // .hideAfter(Duration.ofSeconds(3));
            n.showInformation();
            init();
            dataList.clear();
            try {
                dataList.addAll(sm.AffichermsgUser());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void init()
    {  tfmessage.setText("");
    }
    @FXML
    private void Record(ActionEvent event) {
        if (Record.isSelected()) {
            System.out.println("on");
            javaSoundRecorder = new JavaSoundRecorder();
            Thread thread = new Thread(javaSoundRecorder);
            thread.start();
        } else {
            System.out.println("off");
            wavfile = javaSoundRecorder.finish();
            javaSoundRecorder.cancel();
        }
    }


}
