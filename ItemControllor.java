package Controllers;

import Entity.FileFormation;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ItemControllor {
    public javafx.scene.control.Label labelN,labelDate ;
    @FXML
    private HBox hbox;
    @FXML
    private ImageView imageViewId;
    @FXML
    private Label labelNB;
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8"};

    private String FilePath;
    @FXML
    void clickImagess(MouseEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(FilePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
System.out.println(FilePath);
    }
    public void setData(FileFormation f){
        this.FilePath=f.getPthFile();
        Image image = new Image("file:///"+f.getPathImage());
        hbox.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)]+";"+
                "-fx-background-radius:15;"+
                "-fx-effect: dropShadow (three-pass-box,rgba(0,0,0,0.1), 10 , 0 , 0 , 10);");
        imageViewId.setImage(image);
        labelN.setText(f.getDescription());
        labelDate.setText(f.getDate());

    }
}
