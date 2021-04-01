package Service;

import Entities.Message;
import Entities.Reclamation;
import Services.Iservicemessage;
import utils.Maconnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements Iservicemessage{
    Connection cnx;



    public ServiceMessage() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Addmsg(Message m ) {
        String var="aucune";
        try {
            Statement st = cnx.createStatement();
            String query = "insert into message (message, record, reponse) values ('" +m.getMessage()+"', '"+m.getRecord()+"','"+var+"' )";
            System.out.println(query);

            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> Affichermsg() throws SQLException {
        Statement st = cnx.createStatement();
        List<Message> Message = new ArrayList<>();
        String query = "select * from message ";
        ResultSet rst = st.executeQuery(query);
        while (rst.next()) {
            Message m = new Message();
            m.setId_message(rst.getInt(1));
            m.setDate_creation(rst.getDate(2));
            m.setId_user(rst.getInt(3));
            m.setMessage(rst.getString(4));
            m.setReponse(rst.getString(5));
            m.setRecord(rst.getString(6));


            Message.add(m);
        }
        return Message;
    }

    @Override
    public List<Entities.Message> AffichermsgUser() throws SQLException {
        Statement st = cnx.createStatement();
        List<Message> Message = new ArrayList<>();
        String query = "select * from message ";
        ResultSet rst = st.executeQuery(query);
        while (rst.next()) {
            Message m = new Message();
            m.setId_message(rst.getInt(1));
            m.setDate_creation(rst.getDate(2));
            m.setMessage(rst.getString(4));
            m.setReponse(rst.getString(5));
            m.setRecord(rst.getString(6));
            Message.add(m);
        }
        return Message;
    }



    @Override
    public void Updatemsg(Message m)  {
        PreparedStatement pt = null;

        try {
            pt = cnx.prepareStatement("update message SET reponse =? where id_message =? ");
            pt.setString(1, m.getReponse());
            pt.setInt(2, m.getId_message());
            pt.executeUpdate();} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
    @Override
    public Message findbyId(int id_message) {
        Message m = new Message();
        try {
            PreparedStatement pt = cnx.prepareStatement("select * from message where id_message =? ");
            pt.setInt(1,id_message);
            ResultSet rst = pt.executeQuery();

            while (rst.next()) {

                m.setId_message(rst.getInt(1));
                m.setDate_creation(rst.getDate(2));
                m.setId_user(rst.getInt(3));
                m.setMessage(rst.getString(4));
                m.setRecord(rst.getString(6));
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }

        return m;
    }



}
