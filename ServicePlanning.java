/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entites.Events;
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
 * @author user
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
     
         String req="INSERT INTO planning( id_planning, id_event,date_creation,id_user) VALUES ('"+p.getId_planning()+"','"+p.getId_event()+"','"+p.getDate_creation()+"','"+p.getId_user()+"')";
        
         stm.executeUpdate(req);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }
       
    }

    @Override
    public List<Planning> AfficherPlanning() throws SQLException {
        
        
         Statement stm =cnx.createStatement();
    
    String query= "select * from planning ";
      ResultSet rst =stm.executeQuery(query);
      
      List<Planning>Planning =new ArrayList<>();
   while(rst.next())
   {
       Planning p =new Planning ();
       p.setId_event(rst.getInt("id_event"));
       p.setId_planning(rst.getInt("id_planning"));
         p.setDate_creation(rst.getDate("date_creation").toLocalDate());
            p.setId_user(rst.getInt("id_user"));
     
       Planning.add(p);
       
   }
     
     return Planning;
        
       
    }
    
    
     @Override
    public void updatePlanning(Planning p) {
         
   try{
         Statement stm= cnx.createStatement();
     
         String query= "UPDATE planning SET `id_planning`='"+p.getId_planning()+"', `id_event`='"+p.getId_event()+"', `date_creation`='"+p.getDate_creation()+"', `id_user`='"+p.getId_user()+"' where id_planning='"+p.getId_planning()+"' ";
         
        
         stm.executeUpdate(query);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }
    }

    
    
    
    
    
    
    @Override
    public void deletePlanning(int id) {
       try{
         Statement stm= cnx.createStatement();
     
         String query= "DELETE FROM planning WHERE `id_planning`='"+id+"'";
            System.out.println(query); 
         stm.execute(query);
          }catch (SQLException ex) {
         Logger.getLogger(ServicePlanning.class.getName()).log(Level.SEVERE, null, ex);
                  }
    }
        
    }

   











       
    

 


