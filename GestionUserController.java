package gui;

import Entites.personne;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.ServicePersonne;
import utils.database;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GestionUserController implements Initializable {

    public ImageView userImage;
    @FXML
    private TextField txtId;

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
    private Button ChooseFile;



    private Image image;
    String imagePath = null;

    @FXML
    private CheckBox txtCheck;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    personne formation = null;
    String query=null;

    public GestionUserController() {
        con = database.getConn();
    }
    final ObservableList<personne> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicePersonne sp = new ServicePersonne();
        personne p = new personne();

      //  showPersonne();

        txtStatus.getItems().addAll("Admin", "Membre");
        txtStatus.getSelectionModel().select("Membre");

        TableColumn Colid = new TableColumn("id_user ");
        Colid.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn ColNom = new TableColumn("nom ");
        ColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn ColPrenom = new TableColumn("Prenom ");
        ColPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn ColEmail = new TableColumn("Email");
        ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn ColMdp = new TableColumn("Mot de passe");
        ColMdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));

        TableColumn ColDate = new TableColumn("Date");
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));

        TableColumn ColTele = new TableColumn("Telephone");
        ColTele.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn ColStatus = new TableColumn("Status");
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn ColIamge = new TableColumn("Image");
        ColIamge.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn ColCheck = new TableColumn("Etat");
        ColCheck.setCellValueFactory(new PropertyValueFactory<>("etat"));

        Callback<TableColumn<personne, String>, TableCell<personne, String>> cellFactoryModifier
                = //
                new Callback<TableColumn<personne, String>, TableCell<personne, String>>() {
                    @Override
                    public TableCell call(final TableColumn<personne, String> param) {
                        final TableCell<personne, String> cell = new TableCell<personne, String>() {
                            final CheckBox Etat = new CheckBox("Bannir");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    if (Etat.isSelected()) {
                                        p.getEtat(Etat.getText());
                                    } else {
                                        p.setEtat(null);
                                        // or "" instead of null, depending on what you need
                                    }

                                    setGraphic(Etat);
                                    setText(null);
                                    }
                            }
                        };
                        return cell;
                    }
                };




        ColCheck.setCellFactory(cellFactoryModifier);


        tblData.getColumns().add(Colid);
        tblData.getColumns().add(ColNom);
        tblData.getColumns().add(ColPrenom);
        tblData.getColumns().add(ColEmail);
        tblData.getColumns().add(ColMdp);
        tblData.getColumns().add(ColDate);
        tblData.getColumns().add(ColTele);
        tblData.getColumns().add(ColStatus);
        tblData.getColumns().add(ColIamge);
        tblData.getColumns().add(ColCheck);





        List<personne> list = null;
        try {
            list = sp.read();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (personne personne : list) {
            tblData.getItems().add(personne);
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


    public void modifier(javafx.event.ActionEvent event) {
        String query = "UPDATE personne SET nom = '"+txtFirstname.getText()+"', prenom = '"+txtLastname.getText()+"', email = '"+txtEmail.getText()+"',mdp = '"+txtMdp.getText()+"', date_naissance = '"+txtDOB.getValue().toString()+ "',telephone = '"+txtTelephone.getText()+"',status = '"+txtStatus.getValue()+"',image = '"+imagePath+"',etat='"+txtCheck.isSelected()+"' WHERE id_user = "+txtId.getText()+" ";
        executeQuery(query);
        clearFields();

    }

  /*  @FXML
    private void HandleEvents1(MouseEvent event) {
        //check if not empty

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
                p1.setImage(imagePath);
                p1.setEtat(check);
                sp.add(p1);
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Added Successfully");
                clearFields();

            }catch (Exception e){
                System.out.println(e.getMessage());
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText(e.getMessage());
            }
        }
    }*/
    private void clearFields() {
        txtId.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();
        txtMdp.clear();
        txtDOB.setValue(null);
        txtTelephone.clear();
        txtStatus.getSelectionModel().clearAndSelect(1);
    }


    @FXML
    private void HandleEvents(MouseEvent event) {

        personne q = tblData.getSelectionModel().getSelectedItem();
        System.out.println("id_user" + q.getId_user());txtId.setText(String.valueOf(q.getId_user()));
        System.out.println("nom" + q.getNom());txtFirstname.setText(String.valueOf(q.getNom()));
        System.out.println("prenom" + q.getPrenom());txtLastname.setText(String.valueOf(q.getPrenom()));
        System.out.println("email" + q.getEmail());txtEmail.setText(String.valueOf(q.getEmail()));
        System.out.println("mdp" + q.getMdp());txtMdp.setText(String.valueOf(q.getMdp()));
        System.out.println("telephone" + q.getTelephone());txtTelephone.setText(String.valueOf(q.getTelephone()));
        System.out.println("status" + q.getStatus());txtStatus.getSelectionModel().select(1);
        System.out.println("etat" + q.getEtat());txtCheck.isSelected();

        Image image = new Image("file:///"+tblData.getSelectionModel().getSelectedItem().getImage());
        userImage.setImage(image);
        imagePath=tblData.getSelectionModel().getSelectedItem().getImage();




    }



    public String ChooseFile(ActionEvent event) throws IOException {

        FileChooser fc = new FileChooser();


        fc.setInitialDirectory(new java.io.File("C:\\Users\\Ala Hamed\\Desktop\\AuthentificationV1.2\\src\\image"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg"));

        java.io.File f = fc.showOpenDialog(null);
        if(f != null)
        {
            System.out.println(f);
        }
        imagePath=f.getPath();
        imagePath =imagePath.replace("\\","\\\\");
        return f.getName();


    }


  /*  @FXML
    void refresh() {
        try {
            getPersonneList();

            query = "SELECT * FROM personne";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            List<personne> ls = null;

            while (resultSet.next()){
                ls.add(new personne(
                        resultSet.getInt("id_user"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mdp"),
                        resultSet.getString("telephone"),
                        resultSet.getString("status"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("image")


                        ));
                tblData.setItems((ObservableList<personne>) ls);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionUserController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }*/
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
