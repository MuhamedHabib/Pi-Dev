/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import Entites.Planning;
import service.ServicePlanning;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class FXMLController implements Initializable {

    @FXML
    private TextField txfidp;
    @FXML
    private TextField txfidf;
    @FXML
    private Button addBtn;
    @FXML
    private TableView<Planning> LAffiche;
    
    @FXML
    private DatePicker date;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField tlib;
    @FXML
    private TableColumn<Planning, Integer> colidp;
    @FXML
    private TableColumn<Planning,Integer> colidf;
    @FXML
    private TableColumn<Planning, LocalDate> coldate;
    @FXML
    private TableColumn<Planning, String> collibelle;
    @FXML
    private TableColumn<Planning, Planning> colAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }

    @FXML
    private void AjouterPlanning(ActionEvent event) throws SQLException {

        ServicePlanning sp = new ServicePlanning();
        Planning p = new Planning();
        int id_p = Integer.parseInt(txfidp.getText());
        p.setId_p(id_p);
        int id_formation = Integer.parseInt(txfidf.getText());
        p.setId_formation(id_formation);

        p.setDate_creation(date.getValue());
    
        p.setLibelle(tlib.getText());
        sp.AddPlanning(p);
        init();
    }

    @FXML
    private void AfficherPlanning(ActionEvent event) throws SQLException {

        display();
    }

 
    public void display() throws SQLException {
        ServicePlanning p = new ServicePlanning();
        List<Planning> list = p.AfficherPlanning();
        ObservableList<Planning> list1 = FXCollections.observableArrayList();
        
        list1.addAll(list);
        
        LAffiche.setItems(list1);
       
     colidp .setCellValueFactory(new PropertyValueFactory<>("Id_p"));

         colidf.setCellValueFactory(new PropertyValueFactory<>("id_formation"));

        coldate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

       collibelle .setCellValueFactory(new PropertyValueFactory<>("libelle"));
        
       
        colAction.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue()));
        colAction.setCellFactory(column -> {
            return new TableCell<Planning, Planning>() {
                private final Button deleteButton = new Button("supprimer");
                
                @Override
                protected void updateItem(Planning planning, boolean empty) {
                    super.updateItem(planning, empty);
                    if (planning == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(deleteButton);
                    ServicePlanning p = new ServicePlanning();
                    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            p.deletePlanning(planning.getId_p());
                            try {
                                init();
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    
                    LAffiche.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Planning>() {
                        @Override
                        public void changed(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue) {
                            if (LAffiche.getSelectionModel().getSelectedItem() != null) {
                                txfidp.setText(String.valueOf(LAffiche.getSelectionModel().getSelectedItem().getId_p()));
                                txfidf.setText(String.valueOf(LAffiche.getSelectionModel().getSelectedItem().getId_formation()));
                                date.setValue(LAffiche.getSelectionModel().getSelectedItem().getDate_creation());
                         
                              tlib.setText(String.valueOf(LAffiche.getSelectionModel().getSelectedItem().getLibelle()));
                                
                               
                                updateBtn.setDisable(false);
                                addBtn.setDisable(true);
                            }
                        }
                        
                    });
                    
                }
            };
        });
    }



  
    
    @FXML
    private void btnUpdate(ActionEvent event) throws SQLException {
        ServicePlanning sp = new ServicePlanning();
        Planning p = new Planning();
        int id_p = Integer.parseInt(txfidp.getText());
        p.setId_p(id_p);
        int id_formation = Integer.parseInt(txfidf.getText());
        p.setId_formation(id_formation);

        p.setDate_creation(date.getValue());
        p.setLibelle(tlib.getText());
        sp.updatePlanning(p);
        init();
    }

    @FXML
    private void btnInit(ActionEvent event) throws SQLException {

        init();

    }

    public void init() throws SQLException {
        txfidp.setText("");
        date.setValue(null);
        txfidf.setText("");
        tlib.setText("");
        updateBtn.setDisable(true);
        addBtn.setDisable(false);
        display();
    }

}
  





