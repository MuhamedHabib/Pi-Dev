package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Entity.*;
import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.sql.*;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViews implements Initializable {

    @FXML
    private TableView<formationhd> TableFormation;

    @FXML
    private TableColumn<formationhd, String> idFile;//id_file

    @FXML
    private TableColumn<formationhd, String> id_formation;//id

    @FXML
    private TableColumn<formationhd, String> file;//file blob(contenu)

    @FXML
    private TableColumn<formationhd, String> dateColm;//date_creation

    @FXML
    private TableColumn<formationhd, String> editCol;

    @FXML
    private Button addFileB;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    formationhd formation = null ;
/********************//***********************/
  //  int index = -1;

/********************//***********************/

    ObservableList<formationhd> formationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();

    }


    

    

    @FXML
    void print(MouseEvent event) {

    }

/***********************************************REFRECH*************************************************************/
    @FXML
     void refresh() {
        try {
            formationList.clear();

            query = "SELECT * FROM `formationhd`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                formationList.add(new formationhd(
                        resultSet.getInt("id_file"),
                        resultSet.getString("id"),
                        resultSet.getString("file"),
                        resultSet.getDate("date_creation")

            ));
                TableFormation.setItems(formationList);



            }


        } catch (SQLException ex) {
            Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
/***********************************************REFRECH*************************************************************/




    private void loadDate() {

        connection = DbConnect.getConnect();
        refresh();


        idFile.setCellValueFactory(new PropertyValueFactory<>("id_file"));
        id_formation.setCellValueFactory(new PropertyValueFactory<>("id"));
        file.setCellValueFactory(new PropertyValueFactory<>("file"));
        dateColm.setCellValueFactory(new PropertyValueFactory<>("date_creation"));




    }
/******************************************************************************************/
    public void close(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
/*********************************************************************************************/
//ci dessous pour faire le call ADD scene
    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../gui/add.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*********************************************************************************************************/
    public void print(javafx.scene.input.MouseEvent mouseEvent) {
    }
/*************************************************************************************************/
    public void addFile() throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/sample.fxml"));

        Stage window =(Stage) addFileB.getScene().getWindow();
        window.setScene(new Scene(root, 1500, 1300));
    }

/*******************************************************************************************************/


}
 /*  @FXML
    void Edit() {

            try {
                connection = DbConnect.getConnect();
                String value1 = myiId.getText();
                String value2 = libellefld.getText();
                String value3 = descriptiffld.getText();
                String value4 = datefld.getText();
                String sql = "update users set formation_id= '"+value1+"',libelle_formation= '"+value2+"',description= '"+
                        value3+"',date_creation= '"+value4+"' where user_id='"+value1+"' ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "Update");
               UpdateTable();
                search_user();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }


    public void UpdateTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<formationhd,String>("id_formation"));
        libelleCol.setCellValueFactory(new PropertyValueFactory<formationhd,String>("libelle_formation"));
        descCol.setCellValueFactory(new PropertyValueFactory<formationhd,String>("description"));
        dateColm.setCellValueFactory(new PropertyValueFactory<formationhd,String>("date_creation"));


        formationList = DbConnect.getDatausers();
        TableFormation.setItems(formationList);
    }


    private void search_user() {

    }/*
    @FXML
    void getSelected (MouseEvent event){
        index = formationhd.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        myiId.setText(idCol.getCellData(index).toString());
        libellefld.setText(libelleCol.getCellData(index).toString());
        descriptiffld.setText(descCol.getCellData(index).toString());
        datefld.setText(dateColm.getCellData(index).toString());

    }*/
 /*   @FXML
    void getSelected (MouseEvent event, AddController e){
        index = TableFormation.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }

        txt_id.setText(col_id.getCellData(index).toString());
        txt_username.setText(col_username.getCellData(index).toString());
        txt_password.setText(col_password.getCellData(index).toString());
        txt_email.setText(col_email.getCellData(index).toString());
        txt_type.setText(col_type.getCellData(index).toString());

    } */
//*************************************upppppppddateeee********************/
  /* editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

        formation = TableFormation.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("add.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddController addStudentController = loader.getController();
        addStudentController.setUpdate(true);
        addStudentController.setTextField(
                formation.getId(),
                formation.getLibelle_formation(),
                formation.getDescription(),
                formation.getDate_creation().toLocalDate());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();




    }); */
/*************************************upppppppddateeee********************/






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
/*
        //add cell of button edit
        Callback<TableColumn<formationhd, String>, TableCell<formationhd, String>> cellFoctory = (TableColumn<formationhd, String> param) -> {
            // make cell containing buttons
            final TableCell<formationhd, String> cell = new TableCell<formationhd, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            try {
                                formation = TableFormation.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `student` WHERE id  ="+formation.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });
                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            student = studentsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/tableView/addStudent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddStudentController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(student.getId(), student.getName(),
                                    student.getBirth().toLocalDate(),student.getAdress(), student.getEmail());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
*/
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
/*****************************newtest************/
//add cell of button edit
      /* Callback<TableColumn<formationhd, String>, TableCell<formationhd, String>> cellFoctory = (TableColumn<formationhd, String> param) -> {
            // make cell containing buttons
            final TableCell<formationhd, String> cell = new TableCell<formationhd, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                     super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {



                         // FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        // FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        Button deleteIcon = new Button();
                        javafx.scene.control.Button editIcon = new Button();

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            try {
                                formation = TableFormation.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `formationhd` WHERE id  ="+formation.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refresh();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });
                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            formation = TableFormation.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("add.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViews.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(
                                    formation.getId(),
                                    formation.getLibelle_formation(),
                                    formation.getDescription(),
                                    formation.getDate_creation().toLocalDate());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        }; */

/*****************************new************/