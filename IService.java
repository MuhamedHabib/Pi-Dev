package Services;
import java.sql.SQLException;
import java.util.List;



public interface IService <T> {


    public  void add (T t) throws SQLException;
    List<T> read() throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;



}
