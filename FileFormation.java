package Entity;

public class FileFormation {
    public FileFormation() {
    }

    public myformation getFormation() {
        return formation;
    }

    @Override
    public String toString() {
        return "FileFormation{" +
                "formation=" + formation +
                ", fileId=" + fileId +
                '}';
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFormation(myformation formation) {
        this.formation = formation;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public FileFormation(myformation formation, Integer fileId) {
        this.formation = formation;
        this.fileId = fileId;
    }

    private myformation formation;
    private Integer fileId;
}
