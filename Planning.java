/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.time.LocalDate;

/**
 *
 * @author user
 */
public class Planning {
     
      private int id_planning;
         private int id_event;
       private LocalDate date_creation;
      private int id_user;

    public int getId_planning() {
        return id_planning;
    }

    public void setId_planning(int id_planning) {
        this.id_planning = id_planning;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Planning() {
    }

    public Planning(int id_planning, int id_event, LocalDate date_creation, int id_user) {
        this.id_planning = id_planning;
        this.id_event = id_event;
        this.date_creation = date_creation;
        this.id_user = id_user;
    }
      
      
   
    
}
