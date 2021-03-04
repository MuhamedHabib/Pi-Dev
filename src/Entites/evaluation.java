package Entites;


import java.time.LocalDate;
import java.util.Date;

public class evaluation {
    public evaluation() {
    }

    private int id_evaluation;

    public evaluation(int id_evaluation, String categories, java.sql.Date date_creation) {
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public evaluation(int id_evaluation, String categories) {
        this.id_evaluation = id_evaluation;
        this.categories = categories;
    }

    private String categories;
    private int id_user;
    private int id_formation;
    LocalDate d=LocalDate.now();

    public evaluation(int id_evaluation,String categories, Date d, int id_user, int id_formation) {
    }

    public evaluation(int id_evaluation) {
        this.id_evaluation = id_evaluation;
    }

    public LocalDate getD() {
        return d;
    }

    public void setD(LocalDate d) {
        this.d = d;
    }



    public evaluation(int id_evaluation, int id_user, int id_formation, LocalDate d) {
        this.id_evaluation = id_evaluation;
        this.id_user = id_user;
        this.id_formation = id_formation;
        this.d = d;
    }

    public int getId_evaluation() {
        return id_evaluation;
    }

    public void setId_evaluation(int id_evaluation) {
        this.id_evaluation = id_evaluation;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }



    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "evaluation{" +
                "id_evaluation=" + id_evaluation +",Categories="+ categories + ", id_user=" + id_user + ", id_formation=" + id_formation + ", d=" + d + '}';
    }
}
