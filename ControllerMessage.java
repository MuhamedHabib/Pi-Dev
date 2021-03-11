package sample;

import Entities.Message;
import Entities.Reclamation;
import Entities.formation;
import Service.ServiceMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public class ControllerMessage implements Initializable {
    @FXML
    private TextArea tfmessage;
    @FXML
    private Button envoyer;
    ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceMessage sm = new ServiceMessage();
        }

    @FXML
    public void Ajoutermsg(ActionEvent event) {
        ServiceMessage sm = new ServiceMessage();
        Message m = new Message();
        m.setMessage(tfmessage.getText());
        sm.Addmsg(m);
        System.out.println(m);
        init();

    }

    public void init()
    {  tfmessage.setText("");
    }
}
