package Entities;

import java.sql.Timestamp;

public class Message {
    private int id_message;
    private Timestamp date_creation;
    private int id_user;
    private String message;

    public Message() {
    }

    public Message(int id_message) {
        this.id_message = id_message;
    }

    public Message(String message) {

        this.message=message;    }


    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
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



    @Override
    public String toString() {
        return "Message{" +
                "id_message=" + id_message +
                ", date_creation=" + date_creation +
                ", id_user=" + id_user +
                ", message='" + message + '\'' +
                '}';
    }
}
