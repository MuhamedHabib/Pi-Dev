/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpDesk;

import Entites.Events;
import service.ServiceEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;



public class FXMLController implements Initializable {

    @FXML
    private TextField txfidf;
    @FXML
    private Button addBtn;
    @FXML
    private TableView<Events> LAffiche;
    
    @FXML
    private DatePicker date;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField tlib;
   
    @FXML
    private TableColumn<Events,Integer> colidf;
    @FXML
    private TableColumn<Events, LocalDate> coldate;
    @FXML
    private TableColumn<Events, String> collibelle;
    @FXML
    private TableColumn<Events, Events> colAction;
    @FXML
    private TableColumn<Events, Integer> colevent;
    @FXML
    private TextField txfevent;
    @FXML
    private TextField filterField;
 
   
 
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preferences userPreferences = Preferences.userRoot();
        String role = userPreferences.get("role", null);
        String nom = userPreferences.get("nom", null);
        String prenom = userPreferences.get("prenom", null);
      // nomPrenomLabel.setText(prenom +" - "+nom+" : "+role);
        this.refreshTableNormal();

        this.filterField.textProperty().addListener((obs, ov, nv) -> {
            System.out.println(nv.toString());
            if (nv.length() != 0) {
                refreshTableFiltred(nv);
            } else {
                this.refreshTableNormal();
            }
        });

    }

    public List<Events> fetchAll() {
        try {
            ServiceEvent sp = new ServiceEvent();
            ResultSet data = sp.consulterToutEvents();
            List l = new LinkedList();
            while (data.next()) {
                Events e= new Events();
                e.setId_event(data.getInt("id_event"));
                e.setId_formation(data.getInt("id_formation"));
              
                e.setDate_event(data.getDate("date_event").toLocalDate());
                e.setLibelle(data.getString("libelle"));
                l.add(e);
            }
            return l;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Events> fetchAlternate(String keyword) {
        try {
            ServiceEvent sp= new ServiceEvent();
            ResultSet data = sp.AfficherEvents(keyword);
            List l= new LinkedList();
          
                 while (data.next()) {
                Events e= new Events();
                e.setId_event(data.getInt("id_event"));
                e.setId_formation(data.getInt("id_formation"));
              
                e.setDate_event(data.getDate("date_event").toLocalDate());
                e.setLibelle(data.getString("libelle"));
                l.add(e);
                
                
                
                
            }
            return l;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void refreshTableFiltred(String keyword) {
        try {
            colevent.setCellValueFactory(new PropertyValueFactory<>("id_event"));
            colidf.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_event"));
          
            collibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            LAffiche.getItems().setAll(fetchAlternate(keyword));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void refreshTableNormal() {
        try {
            colevent.setCellValueFactory(new PropertyValueFactory<>("id_event"));
            colidf.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_event"));
          
            collibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
           LAffiche .getItems().setAll(fetchAll());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        


    @FXML
    private void AjouterEvent(ActionEvent event) throws SQLException {

        ServiceEvent sp = new ServiceEvent();
        Events p = new Events();
        int id_event = Integer.parseInt(txfevent.getText());
        p.setId_event(id_event);
        int id_formation = Integer.parseInt(txfidf.getText());
          p.setId_formation(id_formation);
      
       p.setLibelle(tlib.getText());
        p.setDate_event(date.getValue());

        sp.AddEvent(p);
        init();
    }

    @FXML
    private void AfficherEvent(ActionEvent event) throws SQLException {
        
        display();
    }

 
    public void display() throws SQLException {
        ServiceEvent p = new ServiceEvent();
        List<Events> list = p.AfficherEvent();
        ObservableList<Events> list1 = FXCollections.observableArrayList();
        
        list1.addAll(list);
        
        LAffiche.setItems(list1);
       
    colevent .setCellValueFactory(new PropertyValueFactory<>("Id_event"));

         colidf.setCellValueFactory(new PropertyValueFactory<>("id_formation"));

        coldate.setCellValueFactory(new PropertyValueFactory<>("date_event"));

       collibelle .setCellValueFactory(new PropertyValueFactory<>("libelle"));
        
       
        colAction.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue()));
        colAction.setCellFactory(column -> {
            return new TableCell<Events, Events>() {
                private final Button deleteButton = new Button("supprimer");
                
                @Override
                protected void updateItem(Events events , boolean empty) {
                    super.updateItem(events, empty);
                    if (events== null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(deleteButton);
                    ServiceEvent p = new ServiceEvent();
                    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                           p.deleteEvent(events.getId_event());
                             
                            try {
                                init();
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                         }
                        }
                    });
                    
                    LAffiche.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Events>() {
                        @Override
                        public void changed(ObservableValue<? extends Events> observable, Events oldValue, Events newValue) {
                            if (LAffiche.getSelectionModel().getSelectedItem() != null) {
                               txfevent .setText(String.valueOf(LAffiche.getSelectionModel().getSelectedItem().getId_event()));
                                txfidf.setText(String.valueOf(LAffiche.getSelectionModel().getSelectedItem().getId_formation()));
                                date.setValue(LAffiche.getSelectionModel().getSelectedItem().getDate_event());
                         
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
        ServiceEvent sp = new ServiceEvent();
        Events p = new Events();
        int id_event = Integer.parseInt(txfevent.getText());
        p.setId_event(id_event);
          p.setDate_event(date.getValue());
        int id_formation = Integer.parseInt(txfidf.getText());
        p.setId_formation(id_formation);

      
        p.setLibelle(tlib.getText());
        sp.updateEvent(p);
        init();
    }


    @FXML
    private void btnInit(ActionEvent event) throws SQLException {

        init();

    }

    public void init() throws SQLException {
        txfevent.setText("");
        date.setValue(null);
        txfidf.setText("");
        tlib.setText("");
        updateBtn.setDisable(true);
        addBtn.setDisable(false);
        display();
    }

 
         
         
          
      
       
        
    }


    
                



