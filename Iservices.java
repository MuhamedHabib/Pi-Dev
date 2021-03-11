package Services;

import Entities.Reclamation;

import java.sql.SQLException;
import java.util.List;

public interface Iservices {

    public void AddRec(Reclamation r);
    public List<Reclamation> AfficherRec() throws SQLException;
    public List<Reclamation> AfficherRecUser() throws SQLException;
    public void UpdateRec(Reclamation r) throws SQLException;
    public void DeleteRec(Reclamation r);
    public Reclamation findbyId(int id_reclamation);
}
