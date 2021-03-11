package service;

import Entites.Events;

import Utils.Maconnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interservice.IServiceEvent;
import javafx.fxml.FXML;

/**
 *
 * @author ComputerT
 */
public class ServiceEvent implements IServiceEvent {

    Connection cnx;

    public ServiceEvent() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void AddEvent(Events p) {

        try {
            Statement stm = cnx.createStatement();
            String req = "INSERT INTO events( id_event, id_formation,libelle,date_event) VALUES ('" + p.getId_event() + "','" + p.getId_formation() + "','" + p.getLibelle() + "','" + p.getDate_event() + "')";
            stm.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Events> AfficherEvent() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from events ";
        ResultSet rst = stm.executeQuery(query);
        List<Events> Event = new ArrayList<>();
        while (rst.next()) {
            Events p = new Events();
            p.setId_event(rst.getInt("id_event"));
            p.setId_formation(rst.getInt("id_formation"));
            p.setDate_event(rst.getDate("date_event").toLocalDate());
            p.setLibelle(rst.getString("Libelle"));
            Event.add(p);
        }
        return Event;

    }

    @Override
    public void deleteEvent(int id) {
        try {
            Statement stm = cnx.createStatement();
            String query = "DELETE FROM events WHERE `id_event`='" + id + "'";
            System.out.println(query);
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateEvent(Events p) {
        try {
            Statement stm = cnx.createStatement();

            String query = "UPDATE events SET `id_event`='" + p.getId_event() + "', `id_formation`='" + p.getId_formation() + "', `date_event`='" + p.getDate_event() + "', `libelle`='" + p.getLibelle() + "' WHERE `id_event`='" + p.getId_event() + "' ";
            ;
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet AfficherEvents(String keyword) {
        try {
            String query = "SELECT * FROM events WHERE id_event LIKE '%" + keyword + "%'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ResultSet consulterToutEvents() {
        try {
            String query = "SELECT * FROM events";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;

        }
    }

    @Override
    public List<Events> AfficherEventsUser() throws SQLException {
     
           Statement stm = cnx.createStatement();
        String query = "select * from events ";
        ResultSet rst = stm.executeQuery(query);
        List<Events> Event = new ArrayList<>();
        while (rst.next()) {
            Events p = new Events();
            p.setId_event(rst.getInt("id_event"));
            p.setId_formation(rst.getInt("id_formation"));
            p.setDate_event(rst.getDate("date_event").toLocalDate());
            p.setLibelle(rst.getString("Libelle"));
            Event.add(p);
        }
        return Event;
        
    

}
}
