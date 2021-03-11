package Services;

import Entities.Message;
import Entities.Reclamation;

import java.sql.SQLException;
import java.util.List;

public interface Iservicemessage  {
    public void Addmsg(Message m);
    public List<Message> Affichermsg() throws SQLException;
    public void Updatemsg(Message m) throws SQLException;
    public void Deletemsg(Message m);
}
