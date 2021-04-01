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
     private ResultSet rs;
     private Statement st;
     PreparedStatement pstmt = null;
     private int id_user;



     public ServicePersonne() {
            cnx = database.getInstance().getConn();
        }

        @Override
        public void add(personne p) throws SQLException{
            Statement st = cnx.createStatement();
            String req =" insert into personne ( nom , prenom , email , mdp , date_naissance , telephone , status , image ) values('"+p.getNom()+" ' , '"+p.getPrenom() +"' , '" +p.getEmail()+ "' , '" +p.getMdp()+ "' , '" +p.getDate_naissance()+ "' , '" +p.getTelephone("telephone")+ "' , '" +p.getStatus()+ "','"+p.getImage()+"' )";
            System.out.println(req);
            st.executeUpdate(req);

        }

       @Override
        public List<personne> read() throws SQLException{
           List<personne> ls;
           ls = new ArrayList<personne>();
           Statement st = cnx.createStatement();
           String req = "select * from personne Where status='Membre'";
           ResultSet rs = st.executeQuery(req);

            while(rs.next()){

                personne p =new personne ();
                p.setId_user(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setPrenom(rs.getString(3));
                p.setEmail(rs.getString(4));
                p.setMdp(rs.getString(5));
                p.setDate_naissance(rs.getString(6));
                p.setTelephone(rs.getString(7));
                p.setStatus(rs.getString(8));
                p.setImage(rs.getString(9));
                p.setEtat(rs.getString(10));
                ls.add(p);
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
            pt.executeUpdate();
        }
     public void Supp(personne user) {
         String requete = "delete from personne where id_user = '"+user.getId_user()+"'";
         try {
             st = cnx.createStatement();
             st.executeUpdate(requete);
             System.out.println("Compte Supprimé");
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }







     @Override
     public personne findById(int id_user) {
         String query = "select * from personne where id_user= ? ";
         personne student = null;
         try {
             pstmt = cnx.prepareStatement(query);
             pstmt.setInt(1, id_user);
             rs = pstmt.executeQuery();
             while (rs.next()) {
                 student = new personne(rs.getInt(1),
                         rs.getString(2),
                         rs.getString(3),
                         rs.getString(4),
                         rs.getString(5),
                         rs.getString(6),
                         rs.getString(7),
                         rs.getString(8),
                         rs.getString(9),
                         rs.getString(10)
                         );
             }
         } catch (SQLException ex) {
             Logger.getLogger(ServicePersonne.class.getName()).log(Level.SEVERE, null, ex);
         }
         return student;
     }


     public personne findByEmail(String Email) {
         String query = "select * from personne where email= ? ";
         personne student = null;
         try {
             pstmt = cnx.prepareStatement(query);
             pstmt.setString(1, Email);
             rs = pstmt.executeQuery();
             while (rs.next()) {
                 student = new personne(rs.getInt(1),
                         rs.getString(2),
                         rs.getString(3),
                         rs.getString(4),
                         rs.getString(5),
                         rs.getString(6),
                         rs.getString(7),
                         rs.getString(8),
                         rs.getString(9),
                         rs.getString(10)
                 );
             }
         } catch (SQLException ex) {
             Logger.getLogger(ServicePersonne.class.getName()).log(Level.SEVERE, null, ex);
         }
         return student;
     }

     public int geIdbyUsername(String email) throws SQLException {

         /* CurrentUser cu = CurrentUser.CurrentUser(); */
         int id = 0;
         try {
             String request = "SELECT id_user FROM personne where email='" + email + "'";
             Statement s = cnx.createStatement();
             ResultSet result = s.executeQuery(request);
             while (result.next()) {
                 id = result.getInt("id_user");
             }

         } catch (SQLException ex) {
             System.out.println(ex);
         }
         return id;
     }



     public void updatePassword(personne user) {
         String requete = "UPDATE personne SET mdp='"+user.getMdp()+"' WHERE id_user='"+user.getId_user()+"'";
         try {
             st = cnx.createStatement();
             st.executeUpdate(requete);
             System.out.println("mot de passe modifié");
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }


     public void updateNom(personne user) {
         String requete = "UPDATE personne SET nom='" + user.getNom() + "',prenom='"+user.getPrenom()+"' WHERE id_user='"+user.getId_user()+"'";
         try {
             st = cnx.createStatement();
             st.executeUpdate(requete);
             System.out.println("Nom modifié");
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    }

