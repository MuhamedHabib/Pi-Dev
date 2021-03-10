package Entity;

import java.sql.Date;

public class formationhd {
  private int id_file;

    private String file, id;
    private java.sql.Date date_creation;

    public formationhd(int id_file, String id, String file, Date date_creation) {
        this.id_file = id_file;
        this.id = id;
        this.file = file;
        this.date_creation = date_creation;
    }

    @Override
    public String toString() {
        return "formationhd{" +
                "id_file=" + id_file +
                ", id=" + id +
                ", file='" + file + '\'' +
                ", date_creation=" + date_creation +
                '}';
    }

    public int getId_file() {
        return id_file;
    }

    public void setId_file(int id_file) {
        this.id_file = id_file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }
}


