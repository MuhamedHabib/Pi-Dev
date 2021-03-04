package service;

import Entites.Planning;
import Interservice.IServicePlanning;

import Utils.Maconnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ComputerT
 */
public class ServicePlanning implements IServicePlanning{
 Connection cnx;

    public ServicePlanning() {
        cnx = Maconnexion.getInstance().getConnection();
    }
 
 
    @Override
    public void AddPlanning(Planning p) {
    
        try{
         Statement stm= cnx.createStatement();
     
         String req="INSERT INTO planning( id_p, date_creation, libelle,id_formation) VALUES ('"+p.getId_p()+"','"+p.getDate_creation()+"','"+p.getLibelle()+"','"+p.getId_formation()+"')";
        
         stm.executeUpdate(req);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }



    }

    @Override
    public List<Planning> AfficherPlanning()  throws SQLException{
        
    
     
         Statement stm =cnx.createStatement();
    
    String query= "select * from planning ";
      ResultSet rst =stm.executeQuery(query);
      
      List<Planning>Planning =new ArrayList<>();
   while(rst.next())
   {
       Planning p =new Planning ();
       p.setId_p(rst.getInt("id_p"));
          p.setDate_creation(rst.getDate("date_creation").toLocalDate());
       p.setLibelle("Libelle");
       p.setId_formation(rst.getInt("id_formation")); 

    
      
       Planning.add(p);
       
   }
     
     return Planning;
    }

    @Override
    public void updatePlanning(Planning p) {
              try{
         Statement stm= cnx.createStatement();
     
         String query="UPDATE planning SET `id_p`='"+p.getId_p()+"', `id_formation`='"+p.getId_formation()+"', `date_creation`='"+p.getDate_creation()+"', `libelle`='"+p.getLibelle()+"'" ;
            System.out.println(query); 
         stm.executeUpdate(query);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }
    }
      @Override
    public void deletePlanning(int id) {
        try{
         Statement stm= cnx.createStatement();
     
         String query= "DELETE FROM planning WHERE `id_p`='"+id+"'";
            System.out.println(query); 
         stm.execute(query);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }
    }


    
}


