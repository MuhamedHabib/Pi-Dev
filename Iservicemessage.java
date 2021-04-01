package Services;

import Entities.Message;

import java.sql.SQLException;
import java.util.List;

public interface Iservicemessage  {
    public void Addmsg(Message m);
    public List<Message> Affichermsg() throws SQLException;
    public List<Message> AffichermsgUser() throws SQLException;
    public void Updatemsg(Message m) throws SQLException;
    public void Deletemsg(Message m);
    public Message findbyId(int id_message);

}
