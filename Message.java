package Entities;

import java.sql.Date;

public class Message {
    private int id_message;
    private Date date_creation;
    private int id_user;
    private String message;
    private String reponse;
    private String record;

    public Message() {
    }

    public Message(int id_message, Date date_creation, int id_user, String message, String reponse, String record) {
        this.id_message = id_message;
        this.date_creation = date_creation;
        this.id_user = id_user;
        this.message = message;
        this.reponse = reponse;
        this.record = record;
    }

    public Message(int id_message) {
        this.id_message = id_message;
    }

    public Message(String message) {

        this.message=message;    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id_message=" + id_message +
                ", date_creation=" + date_creation +
                ", id_user=" + id_user +
                ", message='" + message + '\'' +
                ", reponse='" + reponse + '\'' +
                '}';
    }
}
