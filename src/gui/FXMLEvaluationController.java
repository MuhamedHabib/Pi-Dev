package gui;

import Entites.evaluation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLEvaluationController implements Initializable {

    @FXML
    public TextField tfid;
    @FXML
    public TextField tfcat;
    @FXML
    public TableView<evaluation> tv;
    @FXML
    public TableColumn<evaluation, Integer> culid;
    @FXML
    public TableColumn<evaluation, String> culcat;
   /* @FXML
    public TableColumn<?, ?> culf;*/
    @FXML
    public TableColumn<evaluation, LocalDate> culdate;
    @FXML
    public Button btnajout;
    @FXML
    public Button btnmodifier;
    @FXML
    public Button btnsupp;


    @FXML
    private void handleButtonAction(ActionEvent event) {

        if(event.getSource() == btnajout){ajouterevaluation();
        }else if(event.getSource() == btnmodifier){
            modifier();
        }else if(event.getSource() == btnsupp){supprimer();}

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showeva();

    }

    public Connection getConnection()
    {
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/helpdesk","root","");
            return conn;

        } catch (SQLException ex) {
           System.out.println("Error: "+ex.getMessage());
           return null;
        }
    }

    public ObservableList<evaluation> getevalist()
    {
        ObservableList<evaluation> evalist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM EVALUATION";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            evaluation e;
            while(rs.next()){
                e=new evaluation(rs.getInt("id_evaluation"), rs.getString("categories"),rs.getDate("date_creation"));
                evalist.add(e);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return evalist;


    }



    public void showeva()
    {
        ObservableList<evaluation> list = getevalist();
        culid.setCellValueFactory(new PropertyValueFactory<evaluation, Integer>("id_evaluation"));
        culcat.setCellValueFactory(new PropertyValueFactory<evaluation, String>("categories"));
        culdate.setCellValueFactory(new PropertyValueFactory<evaluation, LocalDate>("date_creation"));
        tv.setItems(list);


    }





    private void ajouterevaluation()
    {
        String query = " insert into evaluation (id_evaluation,categories ) values ("+tfid.getText()+ ","+tfcat.getText() + ")" ;
        executeQuery(query);
        showeva();

    }

    private void modifier(){
        String query = "UPDATE EVALUATION SET  categories = '"+tfcat.getText() + "WHERE id_evaluation = "+tfid.getText() + "";
        executeQuery(query);
        showeva();
    }
    private void supprimer()
    {
        String query = "DELETE FROM EVALUATION WHERE id_evaluation ="+tfid.getText() + "";
        executeQuery(query);
        showeva();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void handleButtonAction(javafx.event.ActionEvent event) {
        if(event.getSource() == btnajout){ajouterevaluation();
        }
    }
}
