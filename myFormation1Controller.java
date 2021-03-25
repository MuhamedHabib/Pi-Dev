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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ServiceFormation;

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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class myFormation1Controller implements Initializable {


    String imagePath = null;

    private File file;
    private FileInputStream fin;
    public AnchorPane anchorpane;
    @FXML
    private TableView<myformation> table_users;

    @FXML
    private TableColumn<myformation, Integer> col_id;

    @FXML
    private TableColumn<myformation, String> col_libelle;

    @FXML
    private TableColumn<myformation, String> col_description;

    @FXML
    private TableColumn<myformation, String> col_date;

    @FXML
    private TableColumn<myformation, String> col_type;
    @FXML
    private TableColumn<myformation, String> imgCol;


    @FXML
    private TextField txt_libelle;

    @FXML
    private TextField text_description;

    @FXML
    private TextField txt_date;

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


    public void Add_users () throws SQLException {
    /**********************************removed*************************************/
     /*   conn = DbConnect.getConnect();
        String sql = "insert into myformation (libelle,description,date,type,image)values(?,?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_type.getText());
            pst.setString(5, imagePath);


            pst.execute();

            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } */
        /**********************************removed*************************************/
        /**********************************added*************************************/

        if(txt_libelle.getText().isEmpty() || text_description.getText().isEmpty() ||
                txt_date.getText().isEmpty()|| txt_type.getText().isEmpty() || imagePath== null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insert Failed, information missing");
            alert.show();
        }
        else{
            myformation t = new myformation(null, txt_libelle.getText(),
                    text_description.getText(),txt_date.getText(),txt_type.getText(),imagePath);
            ServiceFormation st= new ServiceFormation();
            st.add(t);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ajout succes");
            alert.show();
            UpdateTable();
            search_user();
        }
        /**********************************added*************************************/

    }


    //////// methode select users ///////
    @FXML
    void getSelected (MouseEvent event){
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_libelle.setText(col_libelle.getCellData(index).toString());
        text_description.setText(col_description.getCellData(index).toString());
        txt_date.setText(col_date.getCellData(index).toString());
        /*
        * Image image = new Image("file:///"+TableActors.getSelectionModel().getSelectedItem().getImage());
        imageView.setImage(image);
        imagePath=TableActors.getSelectionModel().getSelectedItem().getImage();*/
        txt_type.setText(col_type.getCellData(index).toString());

        /**********************************/
        Image image = new Image("file:///"+table_users.getSelectionModel().getSelectedItem().getImage());
        imageView.setImage(image);
        imagePath=table_users.getSelectionModel().getSelectedItem().getImage();

        /*********************************************/

    }

    public void Edit () throws SQLException {

        if(txt_libelle.getText().isEmpty()
                || text_description.getText().isEmpty()
                || txt_date.getText().isEmpty()
                || txt_type.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Update Failed, information missing");
            alert.show();
        }
        else{myformation t = new myformation(table_users.getSelectionModel().getSelectedItem().getId(), txt_libelle.getText(),text_description.getText(),txt_date.getText(),txt_type.getText(),imagePath);
            ServiceFormation st= new ServiceFormation();
            st.update(t);
            UpdateTable();
            search_user();}
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Update succes");
        alert.show();
     /*   try {
            conn =  DbConnect.getConnect();
            String value1 = txt_id.getText();
            String value2 = txt_username.getText();
            String value3 = txt_password.getText();
            String value4 = txt_email.getText();
            String value5 = txt_type.getText();
            String value6 = imagePath;

            String sql = "update myformation set id= '"+value1+"',libelle= '"+value2+"',description= '"+
                    value3+"',date= '"+value4+"',type= '"+value5+"',image= '"+value6+"' where id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }*/
    }

    public void Delete() throws SQLException {
     /*   conn = DbConnect.getConnect();
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
        }*/

        ServiceFormation st= new ServiceFormation();
        st.delete((long) table_users.getSelectionModel().getSelectedItem().getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Delete Success");
        alert.show();
        UpdateTable();
        search_user();
    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<myformation,Integer>("id"));
        col_libelle.setCellValueFactory(new PropertyValueFactory<myformation,String>("Libelle"));
        col_description.setCellValueFactory(new PropertyValueFactory<myformation,String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<myformation,String>("date"));
        col_type.setCellValueFactory(new PropertyValueFactory<myformation,String>("type"));

        imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        listM = DbConnect.getDatausers();
        table_users.setItems(listM);
    }




    @FXML
    void search_user() {
        col_id.setCellValueFactory(new PropertyValueFactory<myformation,Integer>("id"));
        col_libelle.setCellValueFactory(new PropertyValueFactory<myformation,String>("Libelle"));
        col_description.setCellValueFactory(new PropertyValueFactory<myformation,String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<myformation,String>("date"));
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

    @FXML
    String Filechooser() {

        FileChooser fc = new FileChooser();


        fc.setInitialDirectory(new File("C:\\ESPRIT\\Esprit\\helpd\\src\\resources"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpeg"));
        File f = fc.showOpenDialog(null);
        if(f != null)
        {
            System.out.println(f);
        }
        imagePath=f.getPath();
       imagePath =imagePath.replace("\\","\\\\");
        return f.getName();
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/helpdesk","root","");
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
            Logger.getLogger(myFormation1Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/tableViewFile2.fxml"));

        Stage window =(Stage) toaddfile.getScene().getWindow();
        window.setScene(new Scene(root, 1000, 750));
    }

    public void showHandleBtn(ActionEvent event) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("../gui/plateformeUI.fxml"));

        Stage window =(Stage) showRelatedFiles.getScene().getWindow();
        window.setScene(new Scene(root, 750, 500));
    }
}

