package Controllers;



import Entity.FileFormation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import service.ServiceFormation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlateformeUI implements Initializable {

    @FXML
    private Button goToHome;

    @FXML
    private HBox hbox,hboxfirst ;
    public void btnHome(ActionEvent event) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/interfaceFormation.fxml"));
        Stage window =(Stage) goToHome.getScene().getWindow();
        window.setScene(new Scene(root, 1500, 1200));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<FileFormation> ls=new ArrayList<FileFormation>();
        List<FileFormation> lss=new ArrayList<>();
        ServiceFormation s=new ServiceFormation();
        try {
            ls= s.readData();
            lss=s.readLastData();
            try {
                for (int i = 0; i < ls.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/gui/item.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ItemControllor itemControllor = fxmlLoader.getController();
                    itemControllor.setData(ls.get(i));
                    hbox.getChildren().add(cardBox);
                    hbox.setPrefWidth(409 * i);
                }
                for(int j=0;j<lss.size();j++){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/gui/item.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ItemControllor itemControllor = fxmlLoader.getController();
                    itemControllor.setData(ls.get(j));
                    hboxfirst.getChildren().add(cardBox);
                    hboxfirst.setPrefWidth(409 * j);

                }




            } catch (IOException ex) {
                Logger.getLogger(PlateformeUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
