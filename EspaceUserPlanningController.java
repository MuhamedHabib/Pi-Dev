/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import Entites.Planning;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.ServicePlanning;

/**
 * FXML Controller class
 *
 * @author user
 */
public class EspaceUserPlanningController implements Initializable {

  
    @FXML
    private TableView<Planning> LAffiche1;
    @FXML
    private TableColumn<Planning, Integer> colp;
    @FXML
    private TableColumn<Planning, Integer> cole;
    @FXML
    private TableColumn<Planning, LocalDate> coldate;
    @FXML
    private TableColumn<Planning, Integer> coluser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            ServicePlanning p = new ServicePlanning();
            List<Planning> list = p.AfficherPlanning();
            ObservableList<Planning> list1 = FXCollections.observableArrayList();
            
            list1.addAll(list);
            
            LAffiche1.setItems(list1);
            
            cole.setCellValueFactory(new PropertyValueFactory<>("Id_event"));
            
            coluser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colp .setCellValueFactory(new PropertyValueFactory<>("id_planning"));
            
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        } catch (SQLException ex) {
            Logger.getLogger(EspaceUserPlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }    
    
}
