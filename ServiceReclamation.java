package Service;

import Entities.Reclamation;
import Services.Iservices;
import javafx.fxml.FXML;
import utils.Maconnexion;

import javax.swing.*;
import javax.swing.table.TableColumn;
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

    public ServiceReclamation() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void AddRec(Reclamation r) {
        try {
            Statement st = cnx.createStatement();
            String query = "insert into reclamation (text) values ('" +r.getText()+"')";
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
        String query = "select * from reclamation";
        ResultSet rst = st.executeQuery(query);
        while (rst.next()) {
            Reclamation rec = new Reclamation();
            rec.setId_reclamation(rst.getInt(1));
            rec.setDate_creation(rst.getTimestamp(2));
            int idUser = rst.getInt(3);
            if(idUser!=0)
                rec.setId_user(rst.getInt(3));
            rec.setText(rst.getString(4));

            Reclamation.add(rec);
        }
        return Reclamation;

    }



    public void UpdateRec(Reclamation r){
        PreparedStatement pt = null;
        try {
            pt = cnx.prepareStatement("update reclamation set text ='"+tfréclamation2.getText()+"'  where id_reclamation = '"+idRec.getText()+"'");
            pt.setString(4, r.getText());
            pt.executeUpdate();} catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void DeleteRec(Reclamation r) {

        try {
            PreparedStatement pt = cnx.prepareStatement("delete from reclamation where id_reclamation =?");
            pt.setInt(1, r.getId_reclamation());
            pt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }


    @Override
    public void Rechercher(Reclamation r) {
    }



}

