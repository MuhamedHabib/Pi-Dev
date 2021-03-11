package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Reclamation implements Runnable {

    private int id_reclamation;
    private Date date_creation;
    private int id_user;
    private String text;
    private String statut;


    public Reclamation(){};

    public Reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public Reclamation(int id_reclamation, String text) {
        this.id_reclamation = id_reclamation;
        this.text = text;
    }

    public Reclamation(int id_reclamation, Date date_creation, int id_user, String text, String statut) {
        this.id_reclamation = id_reclamation;
        this.date_creation = date_creation;
        this.id_user = id_user;
        this.text = text;
 this.statut = statut;    }

    public Reclamation(String text) {

this.text=text;    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }


   // public StringProperty statutProperty() { return statut; }
    public String getStatut() {
        return statut;
    }


    public void setStatut(String statut) {
        this.statut=statut;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", date_creation=" + date_creation +
                ", id_user=" + id_user +
                ", text='" + text + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    @Override
    public void run() {

    }
}

