/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import Entites.Planning;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.ServicePlanning;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXML2Controller implements Initializable {

    @FXML
    private TextField txp;
    @FXML
    private TextField txe;
    @FXML
    private TextField txuser;
    @FXML
    private DatePicker date;
    @FXML
    private Button btnajouter;
    @FXML
    private Button txfupdate;
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
    @FXML
    private TableColumn<Planning, Planning> colAction;
    @FXML
    private AnchorPane LAffiche;
    @FXML
    private Button txfplanning;


   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterPlanning(ActionEvent event) {
        ServicePlanning sp = new ServicePlanning();
        Planning p = new Planning();
       
        int id_planning = Integer.parseInt(txe.getText());
        p.setId_planning(id_planning);
        
         int id_event = Integer.parseInt(txp.getText());
        p.setId_event(id_event);
        
        
          p.setDate_creation(date.getValue());
        
        
        int id_user = Integer.parseInt(txuser.getText());
        p.setId_user(id_user);
      
        sp.AddPlanning(p);
         txe.setText("");
        date.setValue(null);
        txp.setText("");
        txuser.setText("");
        
        
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
        
        LAffiche1.setItems(list1);
       
     cole.setCellValueFactory(new PropertyValueFactory<>("Id_event"));

         coluser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        colp .setCellValueFactory(new PropertyValueFactory<>("id_planning"));
         
        coldate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

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
                            p.deletePlanning(planning.getId_planning());
                             
                            txe.setText("");
                            date.setValue(null);
                            txp.setText("");
                            txuser.setText("");
                        }
                    });
                                
                    
                    LAffiche1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Planning>() {
                        @Override
                        public void changed(ObservableValue<? extends Planning> observable, Planning oldValue, Planning newValue) {
                            if (LAffiche1.getSelectionModel().getSelectedItem() != null) {
                                 txe.setText(String.valueOf(LAffiche1.getSelectionModel().getSelectedItem().getId_event()));
                                txuser.setText(String.valueOf(LAffiche1.getSelectionModel().getSelectedItem().getId_user()));
                                txp.setText(String.valueOf(LAffiche1.getSelectionModel().getSelectedItem().getId_planning()));

                                date.setValue(LAffiche1.getSelectionModel().getSelectedItem().getDate_creation());


                                txfupdate.setDisable(false);
                                btnajouter.setDisable(true);
             
                  }
                        }
                        
                    });
                    
                }
            };
        });
        
               
    }
      @FXML
    private void UpdatePlanning(ActionEvent event) {
        
        
           ServicePlanning sp = new ServicePlanning();
        Planning p = new Planning();
          int id_planning = Integer.parseInt(txp.getText());
        p.setId_planning(id_planning);
        int id_event = Integer.parseInt(txe.getText());
        p.setId_event(id_event);
        p.setDate_creation(date.getValue());
        
        
        int id_user = Integer.parseInt(txuser.getText());
        p.setId_user(id_user);
        
        sp.updatePlanning(p);
        txe.setText("");
        date.setValue(null);
        txp.setText("");
        txuser.setText("");
    }

    @FXML
    private void ImprimerPlanning(ActionEvent event) {
    PrinterJob job = PrinterJob.createPrinterJob();

     boolean printed = job.printPage(LAffiche1);

     if (printed) {
      job.endJob();
     }
    }

   }
 

    
       
        
     
    


             
               
    

