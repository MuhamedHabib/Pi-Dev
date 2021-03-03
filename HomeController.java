/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import utils.ConnectionUtil;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker txtDOB;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<String>  txtStatus ;
    @FXML
    Label lblStatus;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtMdp;

    @FXML
    TableView tblData;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {



        txtStatus.getItems().addAll("Admin", "Membre");
        txtStatus.getSelectionModel().select("Membre");
        fetColumnList();
        fetRowList();

    }

    @FXML
    private void HandleEvents(MouseEvent event) {
        //check if not empty
        if (txtEmail.getText().isEmpty() || txtFirstname.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDOB.getValue().equals(null) || txtMdp.getText().isEmpty() || txtTelephone.getText().isEmpty() ) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
            saveData();
        }

    }

    @FXML
    private void HandleEventsDelete(MouseEvent event) {
        //check if not empty
        if (txtFirstname.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        } else {
            deletedata();
        }

    }

    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();
        txtMdp.clear();
        txtTelephone.clear();

    }

    private String saveData() {

        try {
            String st = "INSERT INTO personne ( nom, prenom, email, mdp, date_naissance, telephone, status) VALUES (?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtFirstname.getText());
            preparedStatement.setString(2, txtLastname.getText());
            preparedStatement.setString(3, txtEmail.getText());
            preparedStatement.setString(4, txtMdp.getText());
            preparedStatement.setString(5, txtDOB.getValue().toString());
            preparedStatement.setString(6, txtTelephone.getText());
            preparedStatement.setString(7, txtStatus.getValue().toString());
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }


    @FXML
    public void deletedata() {
        connection = ConnectionUtil.conDB();
        String sql = "delete from personne where nom = ? ";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,txtFirstname.getText());
            preparedStatement.execute();

            fetRowList();
            lblStatus.setText("Dlete Successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    public void Update (ActionEvent event) {

        try{
            connection = ConnectionUtil.conDB();
            String value1 = txtFirstname.getText();
            String value2 = txtLastname.getText();
            String value3 = txtEmail.getText();
            String value4 = txtMdp.getText();
            String value5 = txtDOB.getValue().toString();
            String value6 = txtTelephone.getText();
            String value7 = txtStatus.getValue().toString();
            String sql = "update personne set nom= '"+value1+"',prenom= '"+value2+"',email= '"+value3+"' ,mdp= '"+value4+"',date_naissance= '"+value5+"',telephone= '"+value6+"',status='"+value7+"' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            lblStatus.setText("Update Successfully");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }




    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from personne";

    //only fetch columns
    private void fetColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
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

    //fetches rows and data from the list
    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);

            }

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }




}
