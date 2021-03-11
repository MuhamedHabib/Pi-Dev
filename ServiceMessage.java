package Service;

import Entities.Message;
import Entities.Reclamation;
import Services.Iservicemessage;
import Services.Iservices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Maconnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements Iservicemessage {
    Connection cnx;

    public ServiceMessage() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Addmsg(Message m ) {
        try {
            Statement st = cnx.createStatement();
            String query = "insert into message (message) values ('" +m.getMessage()+"')";
            System.out.println(query);

            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    static ObservableList<Message> Message = FXCollections.observableArrayList();

    @Override
    public List<Message> Affichermsg() throws SQLException {


            Statement st = cnx.createStatement();


           // List<Message> Message = new ArrayList<>();
            String query = "select * from message ";
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
                Message m = new Message();
                m.setId_message(rst.getInt(1));
                m.setDate_creation(rst.getTimestamp(2));
                    m.setId_user(rst.getInt(3));
                m.setMessage(rst.getString(4));

                Message.add(m);
            }
            return Message;

        }


    @Override
    public void Updatemsg(Message m) throws SQLException {

    }

    @Override
    public void Deletemsg(Message m) {
        try {
            PreparedStatement pt = cnx.prepareStatement("delete from message where id_message =?");
            pt.setInt(1, m.getId_message());
            pt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

}
