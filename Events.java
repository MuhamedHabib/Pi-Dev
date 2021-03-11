/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;


import java.time.LocalDate;

public class Events {

    private int id_event;
      private int id_formation;
       private String libelle;
    private LocalDate date_event;

    public LocalDate getDate_event() {
        return date_event;
    }

    public void setDate_event(LocalDate date_event) {
        this.date_event = date_event;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

   

   

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    
    
     public Events() {
         
        
    }

    public Events(int id_event, int id_formation, String libelle, LocalDate date_event) {
        this.id_event = id_event;
        this.id_formation = id_formation;
        this.libelle = libelle;
        this.date_event = date_event;
    }

    @Override
    public String toString() {
        return "Event{" + "id_event=" + id_event + ", id_formation=" + id_formation + ", libelle=" + libelle + ", date_event=" + date_event + '}';
    }
     

   
    }
     
