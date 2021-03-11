package Service;

import Entities.Reclamation;
import Services.Iservices;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import utils.Maconnexion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceReclamation implements Iservices {

    @FXML
    private  TextField idRec ;
    Connection cnx;
    private Connection connection;
    private JTextArea tblData;

    @FXML
    private TextField tfréclamation2;
    private Reclamation text;

    public TableView<Reclamation> Reclamations;


    public ServiceReclamation() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void AddRec(Reclamation r) {
        try {
            String statut = "en attente";
            Statement st = cnx.createStatement();
            String query = "insert into reclamation (text , statut) values ('" +r.getText()+"' , '"+statut+"' )";
            System.out.println(query);
            st.executeUpdate(query);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reclamation> AfficherRec() throws SQLException {

        Statement st = cnx.createStatement();


        List<Reclamation> Reclamation = new ArrayList<>();
        String query = "select * from reclamation ";
        ResultSet rst = st.executeQuery(query);
        while (rst.next()) {
            Reclamation rec = new Reclamation();
            rec.setId_reclamation(rst.getInt(1));
            rec.setDate_creation(rst.getDate(2));
            rec.setId_user(rst.getInt(3));
            rec.setText(rst.getString(4));
            rec.setStatut(rst.getString(5));

            Reclamation.add(rec);
        }
        return Reclamation;

    }

    @Override
    public List<Reclamation> AfficherRecUser() throws SQLException{

        Statement st = cnx.createStatement();


        List<Reclamation> Reclamation = new ArrayList<>();
        String query = "select * from reclamation ";
        ResultSet rst = st.executeQuery(query);
        while (rst.next()) {
            Reclamation rec = new Reclamation();
            rec.setId_reclamation(rst.getInt(1));
            rec.setDate_creation(rst.getDate(2));
            rec.setText(rst.getString(4));
            rec.setStatut(rst.getString(5));

            Reclamation.add(rec);
        }
        return Reclamation;
    }


    @Override
    public Reclamation findbyId(int id_reclamation) {
       Reclamation rec = new Reclamation();
        try {
            PreparedStatement pt = cnx.prepareStatement("select * from reclamation where id_reclamation =? ");
            pt.setInt(1,id_reclamation);
            ResultSet rst = pt.executeQuery();

            while (rst.next()) {

                rec.setId_reclamation(rst.getInt(1));
                rec.setDate_creation(rst.getDate(2));
                rec.setId_user(rst.getInt(3));
                rec.setText(rst.getString(4));
                rec.setStatut(rst.getString(5));
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }

        return rec;
    }


    public void UpdateRec(Reclamation r){

        PreparedStatement pt = null;
        try {

            pt = cnx.prepareStatement("update reclamation SET text =?  where id_reclamation =? ");
            pt.setString(1, r.getText());
            pt.setInt(2, r.getId_reclamation());

            pt.executeUpdate();} catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void UpdateRecStatut(Reclamation r){

        PreparedStatement pt = null;
        try {

            pt = cnx.prepareStatement("update reclamation SET statut =?  where id_reclamation =? ");
            pt.setString(1, r.getStatut());
            pt.setInt(2, r.getId_reclamation());

            pt.executeUpdate();} catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void DeleteRec(Reclamation r) {

        try {
            PreparedStatement pt = cnx.prepareStatement("delete from reclamation where id_reclamation =?");
            pt.setInt(1, r.getId_reclamation());
            System.out.println(r.getId_reclamation());

            pt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }



}

