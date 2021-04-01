package Entity;

public class FileFormation {
    private String pathImage;
   private String pthFile;
   private int id;
   private String description;
   private  String date;
   private String extension;

    public FileFormation() {
    }

    public FileFormation(int id, String description, String date,String pathImage, String pthFile,String extension ) {
        this.id = id;
        this.description=description;
        this.date=date;
        this.pathImage = pathImage;
        this.pthFile = pthFile;
        this.extension=extension;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPathImage() {
        return pathImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPthFile() {
        return pthFile;
    }

    public void setPthFile(String pthFile) {
        this.pthFile = pthFile;
    }

    @Override
    public String toString() {
        return "FileFormation{" +
                "pathImage='" + pathImage + '\'' +
                ", pthFile='" + pthFile + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
