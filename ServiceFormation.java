package service;

import Entity.myformation;
import helpers.DbConnect;
import intService.IService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceFormation implements IService<myformation> {
    Connection cnx ;

    public ServiceFormation() {
        cnx = DbConnect.getConnect();
    }

    @Override
    public void add(myformation t) throws SQLException {

        Statement st = cnx.createStatement();
        String query ="insert into myformation (id,libelle,description,date,type,image)values(NULL, '"+t.getLibelle()+"', '"+ t.getDescription() +"', '"+t.getDate() +"','"+t.getType()+"','"+t.getImage()+"')";
                //"INSERT INTO `tactor` (`id`, `name`, `born`, `description`, `image`) VALUES (NULL, '"+t.getName()+"', '"+ t.getBorn() +"', '"+t.getDescription() +"','"+t.getImage()+"');";
        st.executeUpdate(query);

    }

    @Override
    public List<myformation> read() throws SQLException {
        List<myformation> ls = new ArrayList<myformation>();
        Statement st = cnx.createStatement();
        String req = "select * from myformation order by id";
        ResultSet rs = st.executeQuery(req);
        while(rs.next()){
            Integer id = rs.getInt("id");
            String libelle = rs.getString("libelle");
         //   LocalDate born = rs.getDate("born").toLocalDate();
            String description=rs.getString("description");
            String date = rs.getString("date");
            String type =rs.getString("type");
            String image = rs.getString("image");
            myformation p = new myformation(id, libelle, description,date,type,image);
            ls.add(p);
        }

        return ls;
    }

    @Override
    public void update(myformation t) throws SQLException {

        Statement st = cnx.createStatement();
        String query = "UPDATE `myformation` SET `libelle` = '"+ t.getLibelle() +"',`description` = '"+ t.getDescription() +"', `date` = '"+t.getDate() + "', `type` = '"+t.getType() + "', `image` = '"+t.getImage() + "' WHERE `myformation`.`id` = "+ t.getId()+";";
        st.executeUpdate(query);
    }

    @Override
    public void delete(Long id) throws SQLException {

        Statement st = cnx.createStatement();
        String query = "DELETE FROM `myformation` WHERE `myformation`.`id` = '"+id+"'";
        st.executeUpdate(query);

    }
}
