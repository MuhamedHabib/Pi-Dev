/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import Entites.Events;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceEvent;

/**
 * FXML Controller class
 *
 * @author user
 */
public class InterfaceEventMembreController implements Initializable {

    @FXML
    private TableView<Events> LAffiche;
    @FXML
    private TableColumn<Events, Integer> colevent;
    @FXML
    private TableColumn<Events, Integer> colidf;
    @FXML
    private TableColumn<Events, LocalDate> coldate;
    @FXML
    private TableColumn<Events, String> collibelle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ServiceEvent p = new ServiceEvent();
            List<Events> list = p.AfficherEventsUser();
            ObservableList<Events> list1 = FXCollections.observableArrayList();
            
            list1.addAll(list);
            
            LAffiche.setItems(list1);
            
            colevent .setCellValueFactory(new PropertyValueFactory<>("Id_event"));
            
            colidf.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
            
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_event"));
            
            collibelle .setCellValueFactory(new PropertyValueFactory<>("libelle"));
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceEventMembreController.class.getName()).log(Level.SEVERE, null, ex);
        }
       


        
        
    }    
    
}
