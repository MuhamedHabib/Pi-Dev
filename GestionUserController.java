package gui;

import Entites.personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import services.ServicePersonne;
import utils.database;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class GestionUserController implements Initializable {

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private ComboBox<String> txtStatus;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TextField txtMdp;

    @FXML
    private Button btnUpdate;


    @FXML
    private Label lblStatus;

    @FXML
    private TableView<personne> tblData;
    @FXML
    private TableColumn<personne, Integer> Colid;

    @FXML
    private TableColumn<personne, String> ColNom;

    @FXML
    private TableColumn<personne, String> ColPrenom;

    @FXML
    private TableColumn<personne, String> ColEmail;

    @FXML
    private TableColumn<personne, String> ColMdp;

    @FXML
    private TableColumn<personne, String> ColDate;

    @FXML
    private TableColumn<personne, String> ColTele;

    @FXML
    private TableColumn<personne, String> ColStatus;





    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public GestionUserController() {
        con = database.getConn();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        showPersonne();
        txtStatus.getItems().addAll("Admin", "Membre");
        txtStatus.getSelectionModel().select("Membre");
    }


    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();
        txtMdp.clear();
        txtTelephone.clear();
    }

    public ObservableList<personne> getPersonneList(){
      ObservableList<personne> PersonneList = FXCollections.observableArrayList();
      Connection conn = database.getConn();
      String query = "SELECT * FROM personne";
      Statement st;
      ResultSet rs;

      try{
          st = conn.createStatement();
          rs = st.executeQuery(query);
          personne personne;
          while(rs.next()){
               personne = new personne(rs.getInt("id_user"),rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("mdp"),rs.getString("date_naissance"),rs.getString("telephone"),rs.getString("status"));
               PersonneList.add(personne);
          }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return PersonneList;



    }



    public void showPersonne(){
      ObservableList<personne> list = getPersonneList();

      Colid.setCellValueFactory(new PropertyValueFactory<personne, Integer>("id_user"));
      ColNom.setCellValueFactory(new PropertyValueFactory<personne, String>("nom"));
      ColPrenom.setCellValueFactory(new PropertyValueFactory<personne, String>("prenom"));
      ColEmail.setCellValueFactory(new PropertyValueFactory<personne, String>("email"));
      ColMdp.setCellValueFactory(new PropertyValueFactory<personne, String>("mdp"));
      ColDate.setCellValueFactory(new PropertyValueFactory<personne, String>("date_naissance"));
      ColTele.setCellValueFactory(new PropertyValueFactory<personne, String>("telephone"));
      ColStatus.setCellValueFactory(new PropertyValueFactory<personne, String>("status"));
      tblData.setItems(list);

    }

    @FXML
    private void HandleEvents(MouseEvent event) {
        int index = -1;
        index = tblData.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txtFirstname.setText(ColNom.getCellData(index).toString());
        txtLastname.setText(ColPrenom.getCellData(index).toString());
        txtEmail.setText(ColEmail.getCellData(index).toString());
        txtMdp.setText(ColMdp.getCellData(index).toString());
        txtTelephone.setText(ColTele.getCellData(index).toString());
        txtDOB.setValue(LocalDate.parse(String.valueOf(ColDate.getCellData(index).toString())));
        txtStatus.setValue(ColStatus.getCellData(index).toString());

    }

    @FXML
    private void HandleEvents1(MouseEvent event){
        if (txtEmail.getText().isEmpty() || txtFirstname.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDOB.getValue().equals(null) || txtMdp.getText().isEmpty() || txtTelephone.getText().isEmpty() ) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
            ServicePersonne sp= new ServicePersonne();
            try{
                personne p1= new personne();
                p1.setNom(txtFirstname.getText());
                p1.setPrenom(txtLastname.getText());
                p1.setEmail(txtEmail.getText());
                p1.setMdp(txtMdp.getText());
                p1.setDate_naissance(txtDOB.getValue().toString());
                p1.setTelephone(txtTelephone.getText());
                p1.setStatus(txtStatus.getValue().toString());
                sp.update(p1);
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Update Successfully");
                clearFields();
                showPersonne();

            }catch (Exception e){
                System.out.println(e.getMessage());
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText(e.getMessage());
            }
        }
    }





    private void executeQuery(String query) {
      Connection conn = database.getConn();
      Statement st;
       try{
           st = conn.createStatement();
           st.executeUpdate(query);
       }catch(Exception ex){
          ex.printStackTrace();
       }
    }


    }


































































 /*     ColAction.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(column.getValue()));
        ColAction.setCellFactory(column -> { return new TableCell<personne, personne>() {

            private final Button deleteButton = new Button("supprimer");

            @Override
            protected void updateItem(personne per, boolean empty) {
                super.updateItem(per, empty);
                if (per == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                ServicePersonne p = new ServicePersonne();
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                                             @Override
                                             public void handle(ActionEvent event) {

                                             }
                                         });

       tblData.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<personne>() {

                                                                           @Override
                                                                           public void changed(ObservableValue<? extends personne> observable, personne oldValue, personne newValue) {

                                                                               if (tblData.getSelectionModel().getSelectedItem() != null) {
                                                                                   txtFirstname.setText(String.valueOf(tblData.getSelectionModel().getSelectedItem().getNom()));
                                                                                   txtLastname.setText(String.valueOf(tblData.getSelectionModel().getSelectedItem().getPrenom()));
                                                                                   txtEmail.setText(String.valueOf(tblData.getSelectionModel().getSelectedItem().getEmail()));
                                                                                   txtMdp.setText(String.valueOf(tblData.getSelectionModel().getSelectedItem().getMdp()));
                                                                                   txtDOB.setValue(LocalDate.parse(String.valueOf(tblData.getSelectionModel().getSelectedItem().getDate_naissance())));
                                                                                   txtTelephone.setText(String.valueOf(tblData.getSelectionModel().getSelectedItem().getTelephone()));
                                                                                   txtStatus.setValue(String.valueOf(tblData.getSelectionModel().getSelectedItem().getStatus()));
                                                                               }
                                                                           }
    }*/

    /*      private ObservableList<ObservableList> data;
    private void fetColumnList() {
        try {
            Statement st = con.createStatement();
            String req = "select * from personne";
            ResultSet rs = st.executeQuery(req);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            String req = "select * from personne";
            rs = con.createStatement().executeQuery(req);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }*/
