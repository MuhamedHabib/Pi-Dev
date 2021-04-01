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
public class quiz {
    private int id_quiz;
    private String categories;
    private String quiz;
    private int duree;
    private Date d ;
 //   LocalDate d=LocalDate.now();

    public quiz() {
    }

    public quiz(int id_quiz, String categories, String quiz, int duree, Date d) {
        this.id_quiz = id_quiz;
        this.categories = categories;
        this.quiz = quiz;
        this.duree = duree;
        this.d = d;
    }





    @Override
    public String toString() {
        return "test{" +
                "id_quiz=" + id_quiz +
                ", categorie='" + categories +
                ", test='" + quiz +
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




    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
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
