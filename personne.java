package Entites;


import java.sql.Date;

public class personne {


    private int id_user;
    private String nom, prenom,email,mdp,status,telephone,image,etat;
    private String date_naissance;

    public personne(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public personne(int id_user, String nom, String prenom, String email, String mdp, String status, String telephone, String image, String date_naissance) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.telephone = telephone;
        this.image = image;
        this.date_naissance = date_naissance;
    }

    public personne(int id_user) {
        this.id_user = id_user;
    }

    public personne(int id_user, String nom, String prenom, String email, String mdp, String telephone, String status, Date date_naissance, String image, String etat) {

    }

    public personne(int id_user, String nom, String prenom, String mdp) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    public personne(int id_user, String nom, String prenom, String mdp, String image) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.image = image;
    }

    public String getEtat(String text) {
        return etat;
    }

    public String getEtat() { return etat;  }


    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getId_user() { return id_user; }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() { return nom;  }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public String getMdp() { return mdp; }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getDate_naissance() {
        return date_naissance;
    }
    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getTelephone(String telephone) {   return this.telephone; }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void getStatus(String status) {
    }


    public personne() {

    }

    public personne(int id_user, String nom, String prenom, String email, String mdp, String status, String telephone, String date_naissance,String image,String etat) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.telephone = telephone;
        this.date_naissance = date_naissance;
        this.image = image;
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "personne{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", status='" + status + '\'' +
                ", telephone='" + telephone + '\'' +
                ", image='" + image + '\'' +
                ", etat='" + etat + '\'' +
                ", date_naissance='" + date_naissance + '\'' +
                '}';
    }
}