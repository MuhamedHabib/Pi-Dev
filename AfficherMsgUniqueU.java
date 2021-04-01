package sample;

import Entities.Message;
import Service.ServiceMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherMsgUniqueU implements Initializable {
    private ServiceMessage service;

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
    static Message selectionedMessage;
    private File wavFile;
    private SourceDataLine sourceLine;
    @FXML
    private ToggleButton Record;
    private final int BUFFER_SIZE=12800;
    private int id_message;
    @FXML
    private TextArea reponse;
    @FXML
    private Button afficherrep;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = new ServiceMessage();

        NomTXFLD.setDisable(true);
        PrenomTXFLD.setDisable(true);
        EmailTXFLD.setDisable(true);
        TelTXFLD.setDisable(true);

        tfmessage.setText((ControllerMessage.selectionedMessage.getMessage()));
        reponse.setVisible(false);
        if(ControllerMessage.selectionedMessage.getReponse().equals("aucune"))
        { afficherrep.setDisable(true);}
    }
    void initData(int id) {
        id_message = id;
        if(service.findbyId(id_message).getRecord().equals("null")) Record.setDisable(true);
    }

    @FXML
    private void Record(ActionEvent event) throws UnsupportedAudioFileException {
        String dir = System.getProperty("user.dir");//get project source path
        wavFile = new File(dir + "\\resources\\" + ControllerMessage.selectionedMessage.getRecord());//add the full path /ressources + file name
        System.out.println(wavFile.getPath());
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(wavFile);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AfficherMsgUniqueU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AfficherMsgUniqueU.class.getName()).log(Level.SEVERE, null, ex);
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

    public void Afficherrep(ActionEvent event) {
        reponse.setVisible(true);
        reponse.setText((ControllerMessage.selectionedMessage.getReponse()));
    }
}
