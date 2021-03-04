/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author user
 */
public class Planning {

    private int id_p;
    private LocalDate date_creation;
    private int id_formation;
    private String libelle;

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

   

   

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    
    
     public Planning() {
        
    }

    public Planning(int id_p, LocalDate date_creation, int id_formation, String libelle) {
        this.id_p = id_p;
        this.date_creation = date_creation;
        this.id_formation = id_formation;
        this.libelle = libelle;
    }
 

    @Override
    public String toString() {
        return "Planning{" + "id_p=" + id_p + ", date_creation=" + date_creation + ", id_formation=" + id_formation + ", libelle=" + libelle + '}';
    }
     
}