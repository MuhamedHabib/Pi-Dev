package services;

import Entites.evaluation;
import intservice.IService;
import utils.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceEvaluation implements IService<evaluation> {



    Connection cnx;
    public ServiceEvaluation()
    {
        cnx= Database.getInstance().getConn();
    }

    @Override
    public void add(evaluation e) throws SQLException {
        Statement st = cnx.createStatement();
        String req =" insert into evaluation (id_evaluation,categories, date_creation ) values (" +e.getId_evaluation()+ ", '" +e.getCategories()+"', '"+e.getD()+" ' )";
        st.executeUpdate(req);

    }

    @Override
    public List<evaluation> read() throws SQLException {
        List<evaluation> ls = new ArrayList<evaluation>();
        Statement st = cnx.createStatement();
        String req = "select * from evaluation";
        ResultSet rs = st.executeQuery(req);

        while(rs.next()){
            int id_evaluation = rs.getInt(1);
            String categories = rs.getString(2);
            Date d = rs.getDate(3);
            int id_user = rs.getInt(4);
            int id_formation = rs.getInt(5);

            evaluation e = new evaluation(id_evaluation, categories, d, id_user,id_formation);
            ls.add(e);

        }

        return ls;

    }



    @Override
    public void update(evaluation e) throws SQLException {
        PreparedStatement pt = cnx.prepareStatement("update evaluation set date_creation = ? where id_evaluation = ? ");
        LocalDate d =LocalDate.now();
        pt.setDate(1, java.sql.Date.valueOf(d));
        pt.setInt(2, e.getId_evaluation());
        pt.executeUpdate();

    }

    @Override
    public void delete(evaluation e) throws SQLException {
        PreparedStatement pt = cnx.prepareStatement("delete from evaluation where id_evaluation = ?");
        pt.setInt(1, e.getId_evaluation());
        pt.executeUpdate();

    }
}
