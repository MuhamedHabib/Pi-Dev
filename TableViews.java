package sample;

import Entity.*;
import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
@FXML
    private Label lbid;
    @FXML
    private Label lbnom;
    @FXML
    private Label lbprenom;

    public void setLbid(String id) {
        this.lbid.setText(id);
    }

    public void setLbnom(String nom) {
        this.lbnom.setText(nom);
    }

    public void setLbprenom(String prenom) {
        this.lbprenom .setText(prenom);
    }
*/

public class TableViews implements Initializable {

    @FXML
    private TableView<formationhd> TableFormation;

    @FXML
    private TableColumn<formationhd, String> idCol;

    @FXML
    private TableColumn<formationhd, String> libelleCol;

    @FXML
    private TableColumn<formationhd, String> descCol;

    @FXML
    private TableColumn<formationhd, String> dateColm;

    @FXML
    private TableColumn<formationhd, String> editCol;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    formationhd formation = null ;

    ObservableList<formationhd> formationList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();

    }


    

    

    @FXML
    void print(MouseEvent event) {

    }

    @FXML
     void refresh() {
        try {
            formationList.clear();

            query = "SELECT * FROM `formationhd`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                formationList.add(new formationhd(
                        resultSet.getInt("id_formation"),
                        resultSet.getString("libelle_formation"),
                        resultSet.getString("description"),
                        resultSet.getString("date_creation")

            ));
                TableFormation.setItems(formationList);



            }


        } catch (SQLException ex) {
            Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

  /*  public void setIdCol(String id_formation) {
        this.idCol.setText(id_formation);
    }
    public void setLibelleCol(String libelle_formation) {
        this.libelleCol.setText(libelle_formation);
    }
    public void setDescCol(String description) {
        this.descCol.setText(description);
    }
    public void setDateColm(String date_creation) {
        this.dateColm.setText(date_creation);
    }
*/



    private void loadDate() {

        connection = DbConnect.getConnect();
        refresh();


        idCol.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
        libelleCol.setCellValueFactory(new PropertyValueFactory<>("libelle_formation"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColm.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

    }

    public void close(javafx.scene.input.MouseEvent mouseEvent) {
    }

    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("add.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
/*
public class FXMLPersonneController implements Initializable {

    @FXML
    private TextField txfid;
    @FXML
    private TextField txfnom;
    @FXML
    private TextField txfprenom;
    @FXML
    private Button btnajouter;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ajouterpersonne(ActionEvent event) {
        try {
            Personne p = new Personne(Integer.parseInt(txfid.getText()), txfnom.getText(), txfprenom.getText());

            ServicePersonne sp = new ServicePersonne();

            sp.add(p);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLResultat.fxml"));

            Parent root = loader.load();

            FXMLResultatController pc = loader.getController();

            pc.setLbid(txfid.getText());
            pc.setLbnom(p.getNom());
            pc.setLbprenom(txfprenom.getText());

            txfid.getScene().setRoot(root);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLPersonneController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPersonneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
*/