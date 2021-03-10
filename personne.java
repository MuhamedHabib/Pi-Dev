package Entites;



public class personne {
    private int id_user;
    private String nom, prenom,email,mdp,status,telephone;
    private String date_naissance;

    public int getId_user() { return id_user; }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() { return nom;  }
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

    public personne(int id_user, String nom, String prenom, String email, String mdp, String status, String telephone, String date_naissance) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.status = status;
        this.telephone = telephone;
        this.date_naissance = date_naissance;
    }

    @Override
    public String toString() {
        return "personne{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", telephone=" + telephone +
                ", date_naissance=" + date_naissance +
                ", status='" + status + '\'' +
                '}';
    }


}