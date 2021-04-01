package sample;

import Entities.Message;
import Entities.Reclamation;
import Service.ServiceMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.management.relation.RoleList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherMsgUnique implements Initializable {
    private ServiceMessage service;
    @FXML
    private ToggleButton Record;
    @FXML
    private TextField NomTXFLD;
    @FXML
    private TextField PrenomTXFLD;
    @FXML
    private TextField EmailTXFLD;
    @FXML
    private TextField TelTXFLD;
    @FXML
    private TextArea tfmessage;
    @FXML
    private Button repondre;
    @FXML
    private Button envoyer;
    @FXML
    private TextArea reponse;
    static Message selectionedMessage;
    private int id_message;
    final ObservableList<Message> dataList = FXCollections.observableArrayList();
    private File wavFile;
    private SourceDataLine sourceLine;
    private final int BUFFER_SIZE=12800;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = new ServiceMessage();

        NomTXFLD.setDisable(true);
        PrenomTXFLD.setDisable(true);
        EmailTXFLD.setDisable(true);
        TelTXFLD.setDisable(true);

        tfmessage.setText((ControlleurAdminMessage.selectionedMessage.getMessage()));

        reponse.setVisible(false);
        envoyer.setVisible(false);

        if(ControlleurAdminMessage.selectionedMessage.getReponse().equals("aucune"))
        { repondre.setDisable(false);}
        else {repondre.setDisable(true);}
    }

    public void Repondre(ActionEvent event) {
        reponse.setVisible(true);
        envoyer.setVisible(true);

    }

    public void Envoyer(ActionEvent event) {
        System.out.println(id_message);
        Message m = service.findbyId(id_message) ;
        System.out.println(m);
        m.setReponse(reponse.getText());
        service.Updatemsg(m);
        Platform.runLater(() -> {
        try{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../GUI/MessageAdmin.fxml"
                )
        );
        Stage s = new Stage(); //this accesses the window.
        s.setScene(
                new Scene(loader.load())
        );



                ControlleurAdminMessage controller = loader.getController();
                controller.refreshList();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.

            //stage.hide();
    } catch (
    IOException ex) {
        Logger.getLogger(ControlleurAdminMessage.class.getName()).log(Level.SEVERE, null, ex);
    }

        });


    }

    void initData(int id) {
        id_message = id;
        if(service.findbyId(id_message).getRecord().equals("null")) Record.setDisable(true);
    }

            @FXML
        private void Record (ActionEvent event) throws UnsupportedAudioFileException {
        String dir = System.getProperty("user.dir");//get project source path
        wavFile = new File(dir + "\\resources\\" + ControlleurAdminMessage.selectionedMessage.getRecord());//add the full path /ressources + file name
        System.out.println(wavFile.getPath());
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(wavFile);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AfficherMsgUnique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AfficherMsgUnique.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = stream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();

    }


}
