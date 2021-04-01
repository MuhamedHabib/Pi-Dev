package Entites;

public class formation {
    int id_formation;
    String categories;


    public formation() {
    }

    public formation(int id_formation, String categories) {
        this.id_formation = id_formation;
        this.categories = categories;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }


    @Override
    public String toString() {
        return "formation{" +
                "id_formation=" + id_formation +
                ", categories='" + categories + '\'' +
                '}';
    }


}
