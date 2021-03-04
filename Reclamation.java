package Entities;

import java.sql.Timestamp;

public class Reclamation {

    private int id_reclamation;
    private Timestamp date_creation;
    private int id_user;
    private String text;


    public Reclamation(){};

    public Reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }


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

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Réclamation{" +
                "id_reclamation=" + id_reclamation +
                ", date_creation=" + date_creation +
                ", id_user=" + id_user +
                '}';
    }
}

