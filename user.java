package Entites;

public class user {
    int id_user;
    String Email;
    String status;


    public user() {
    }

    public user(int id_user, String email, String status) {
        this.id_user = id_user;
        Email = email;
        this.status = status;
    }


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "user{" +
                "id_user=" + id_user +
                ", Email='" + Email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
