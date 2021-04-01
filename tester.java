package Entites;

import java.sql.Date;
import java.time.LocalDate;

public class tester {
    int id;
    int id_test;
    int id_user;
    LocalDate d;
    int note;


    public tester() {
    }

    public tester(int id, int id_test, int id_user, LocalDate d, int note) {
        this.id = id;
        this.id_test = id_test;
        this.id_user = id_user;
        this.d = d;
        this.note = note;
    }

    public tester(int id, int id_test, int id_user, Date d, int duree) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public LocalDate getD() {
        return d;
    }

    public void setD(LocalDate d) {
        this.d = d;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
