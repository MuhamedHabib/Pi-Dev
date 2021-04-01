/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entites;

import java.util.Date;

/**
 *
 * @author JAIDI
 */
public class test {
    private int id_test;
    private String categories;
    private String test ;
    private int duree;
    private Date d ;
 //   LocalDate d=LocalDate.now();

    public test() {
    }

    public test(int id_test, String categories, String test, int duree, Date d) {
        this.id_test = id_test;
        this.categories = categories;
        this.test = test;
        this.duree = duree;
        this.d = d;
    }




    @Override
    public String toString() {
        return "test{" +
                "id_test=" + id_test +
                ", categorie='" + categories +
                ", test='" + test  +
                ", duree=" + duree +
                ", d=" + d +
                '}';
    }











    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }




    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

   public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }



  /* public LocalDate getD() {
       return d;
   }

    public void setD(LocalDate d) {
        this.d = d;
    }
*/








}
