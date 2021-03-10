package Controllers;

import Entity.myformation;
import helpers.DbConnect;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Button;

public class FXMLDocumentControlle implements Initializable {


    private File file;
    private FileInputStream fin;
    public AnchorPane anchorpane;
    @FXML
    private TableView<myformation> table_users;

    @FXML
    private TableColumn<myformation, Integer> col_id;

    @FXML
    private TableColumn<myformation, String> col_username;

    @FXML
    private TableColumn<myformation, String> col_password;

    @FXML
    private TableColumn<myformation, String> col_email;

    @FXML
    private TableColumn<myformation, String> col_type;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_type;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField filterField;

    public ImageView imageView;
    private Image image;

    @FXML
     private Button toaddfile;

    @FXML
    private Button showRelatedFiles;

    ObservableList<myformation> listM;
    ObservableList<myformation> dataList;



    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void Add_users (){
        conn = DbConnect.getConnect();
        String sql = "insert into myformation (libelle,description,date,type)values(?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_type.getText());
            /*********************************INSERTED**********************/

            /*fin =new FileInputStream(file);
            setBinaryStream(5,(InputStream)fin,(int)file.length());*/
            /*********************************INSERTED**********************/

            pst.execute();

            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    //////// methode select users ///////
    @FXML
    void getSelected (MouseEvent event){
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_username.setText(col_username.getCellData(index).toString());
        txt_password.setText(col_password.getCellData(index).toString());
        txt_email.setText(col_email.getCellData(index).toString());
        txt_type.setText(col_type.getCellData(index).toString());

    }

    public void Edit (){
        try {
            conn =  DbConnect.getConnect();
            String value1 = txt_id.getText();
            String value2 = txt_username.getText();
            String value3 = txt_password.getText();
            String value4 = txt_email.getText();
            String value5 = txt_type.getText();

          /*  fin =new FileInputStream(file);
            pst.setBinaryStream(5,(InputStream)fin,(int)file.length() ); */

            String sql = "update myformation set id= '"+value1+"',libelle= '"+value2+"',description= '"+
                    value3+"',date= '"+value4+"',type= '"+value5+"' where id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
        conn = DbConnect.getConnect();
        String sql = "delete from myformation where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<myformation,Integer>("id"));
        col_username.setCellValueFactory(new PropertyValueFactory<myformation,String>("Libelle"));
        col_password.setCellValueFactory(new PropertyValueFactory<myformation,String>("description"));
        col_email.setCellValueFactory(new PropertyValueFactory<myformation,String>("date"));
        col_type.setCellValueFactory(new PropertyValueFactory<myformation,String>("type"));

        listM = DbConnect.getDatausers();
        table_users.setItems(listM);
    }




    @FXML
    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<myformation,Integer>("id"));
        col_username.setCellValueFactory(new PropertyValueFactory<myformation,String>("Libelle"));
        col_password.setCellValueFactory(new PropertyValueFactory<myformation,String>("description"));
        col_email.setCellValueFactory(new PropertyValueFactory<myformation,String>("date"));
        col_type.setCellValueFactory(new PropertyValueFactory<myformation,String>("type"));

        dataList = DbConnect.getDatausers();
        table_users.setItems(dataList);
        FilteredList<myformation> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getLibelle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches username
                } else if (person.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }else if (person.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches password
                }
                else if (String.valueOf(person.getDate()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches email

                else
                    return false; // Does not match.
            });
        });
        SortedList<myformation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        search_user();
        // Code Source in description
    }

    public void handleButtonAction(ActionEvent event) throws IOException {

        Stage stage = (Stage)anchorpane.getScene().getWindow();

        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        File file = fc.showOpenDialog(stage);

        BufferedImage bf;
        try {
            bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            imageView.setImage(image);
            FileInputStream fin = new FileInputStream(file);
            int len = (int)file.length();
          /*  Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/address","root","kevo");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO photos(photo)VALUES(?)");
            ps.setBinaryStream(1,fin,len);
            int status = ps.executeUpdate();
            if(status>0)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Information dialog");
                alert.setContentText("Photo saved successfully");
                alert.showAndWait();

            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog.");
                alert.setHeaderText("Error Information");
                alert.showAndWait();
            }*/
            //  conn.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControlle.class.getName()).log(Level.SEVERE, null, ex);
            //  } catch (ClassNotFoundException ex) {
            //  Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            //  } catch (SQLException ex) {
            //  Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
     /*   FileChooser fileChooser =new FileChooser();
        fileChooser.setTitle("Open File Dialog");
        Stage stage =(Stage)anchorpane.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
          //  Desktop desktop =Desktop.getDesktop();
          //  desktop.open(file);
          //  image=new Image(file.toURI().toString(),100,120,true,true);
            imageView=new ImageView(image);
          //  imageView.setFitWidth(100);
          //  imageView.setFitHeight(120);
            //imageView.setPreserveRatio(true);
        }*/

    }

    public void multipleFileChooserAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple file dialog");
        Stage stage = (Stage)anchorpane.getScene().getWindow();

        java.util.List<File> list=fileChooser.showOpenMultipleDialog(stage);

        if(list != null)
        {
            for(File file: list)
            {
                Desktop desktop =Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addAttachment( ) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/tableView.fxml"));

        Stage window =(Stage) toaddfile.getScene().getWindow();
        window.setScene(new Scene(root, 1000, 750));
    }

    public void showHandleBtn(ActionEvent event) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/plateforme.fxml"));

        Stage window =(Stage) showRelatedFiles.getScene().getWindow();
        window.setScene(new Scene(root, 750, 500));
    }
}

