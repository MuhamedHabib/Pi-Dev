package Entity;

import java.util.Date;

public class formationhd {
  private int id_formation;
    private String libelle_formation,description;
    private String date_creation;


    public int getId_formation() {
        return id_formation;
    }
    public String getLibelle_formation() {
        return libelle_formation;
    }
    public String getDescription() {
        return description;
    }
    public String getDate_creation() {
        return date_creation;
    }
    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }
    public void setLibelle_formation(String libelle_formation) {
        this.libelle_formation = libelle_formation;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }


    public formationhd(int id_formation, String libelle_formation, String description, String date_creation){
        this.id_formation=id_formation;
        this.libelle_formation=libelle_formation;
        this.description=description;
        this.date_creation=date_creation;
    };

    @Override
    public String toString() {
        return "formationhd{" +
                "id_formation=" + id_formation +
                ", libelle_formation='" + libelle_formation + '\'' +
                ", description='" + description + '\'' +
                ", date_creation=" + date_creation +
                '}';
    }

}


