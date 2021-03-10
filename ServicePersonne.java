package services;

import Entites.*;
import Intservice.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import utils.*;
import java.util.logging.Level;
import java.util.logging.Logger;


 public class ServicePersonne implements IService<personne>{
        Connection cnx ;
        public ServicePersonne() {
            cnx = database.getInstance().getConn();
        }

        @Override
        public void add(personne p) throws SQLException{
            Statement st = cnx.createStatement();
            //    String req = "insert into personne values ("+p.getId()+" , " +p.getNom()+ ", " +p.getPrenom() +")";
            String req =" insert into personne ( nom , prenom , email , mdp , date_naissance , telephone , status ) values('"+p.getNom()+" ' , '"+p.getPrenom() +"' , '" +p.getEmail()+ "' , '" +p.getMdp()+ "' , '" +p.getDate_naissance()+ "' , '" +p.getTelephone("telephone")+ "' , '" +p.getStatus()+ "' )";
            System.out.println(req);
            st.executeUpdate(req);

        }

       @Override
        public List<personne> read() throws SQLException{
           List<personne> ls;
           ls = new ArrayList<personne>();
           Statement st = cnx.createStatement();
           String req = "select * from personne";
           ResultSet rs = st.executeQuery(req);

            while(rs.next()){

                personne p =new personne ();
                p.setId_user(rs.getInt("id_user"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setMdp(rs.getString("mdp"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.getTelephone(rs.getString("telephone"));
                p.getStatus(rs.getString("status"));


            }
            return ls;
        }



        @Override
        public void update(personne p) throws SQLException {
            PreparedStatement pt = cnx.prepareStatement("update personne set nom = ?,prenom = ?,email = ?,mdp = ?,date_naissance = ?,telephone = ?,status = ? where id_user = ? ");
            pt.setInt(1,p.getId_user());
            pt.setString(2, p.getNom());
            pt.setString(3, p.getPrenom());
            pt.setString(4, p.getEmail());
            pt.setString(5, p.getMdp());
            pt.setString(6, p.getDate_naissance());
            pt.setString(7, p.getTelephone());
            pt.setString(8, p.getStatus());

            pt.executeUpdate();
        }

        @Override
        public void delete(personne p) throws SQLException {
            PreparedStatement pt = cnx.prepareStatement("delete from personne where id_user = ?");
            pt.setInt(1, p.getId_user());
            pt.executeUpdate();    }

    }

