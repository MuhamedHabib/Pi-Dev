package controller;

import Entites.test;
import Entites.tester;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class membretestController implements Initializable {

    @FXML
    private TableView<test> tv;



    @FXML
    private TableColumn<test, Integer> colid;

    @FXML
    private TableColumn<test, String> colcat;

    @FXML
    private TableColumn<test, String> colf;

    @FXML
    private TableColumn<test, Integer> cold;

    @FXML
    private TableColumn<test, DateTimeFormatter> coldate;

    @FXML
    private TableView<tester> tv1;

    @FXML
    private TableColumn<tester, Integer> id;

    @FXML
    private TableColumn<tester, String> test1;

    @FXML
    private TableColumn<tester, Integer> user;

    @FXML
    private TableColumn<tester, DateTimeFormatter> date;
    @FXML
    private TableColumn<tester, Integer> note;


    @FXML
    private TextField cat;


    @FXML
    private javafx.scene.control.Button btnhom;

    // txtDO = new DatePicker();

    PreparedStatement preparedStatement;
    Connection connection;
    public membretestController() {

    }






    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showtest();
        showtest1();


    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","");
            return  conn;


        } catch (SQLException ex) {
            System.out.println(("Error: "+ex.getMessage()));
            return  null;

        }
    }

    public ObservableList<test> gettestlist()
    {
        ObservableList<test> testlist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM test";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            test t;
            while(rs.next()){
                t=new test(rs.getInt("id_test"), rs.getString("categories"),rs.getString("test"),rs.getInt("duree"), rs.getDate("date_creation"));
                testlist.add(t);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testlist;


    }

    public ObservableList<tester> gettestlist1()
    {
        ObservableList<tester> testlist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM tester";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            tester t;
            while(rs.next()){
                t=new tester(rs.getInt("id"), rs.getInt("id_test"),rs.getInt("id_user"),rs.getDate("date"), rs.getInt("note"));
                testlist.add(t);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testlist;


    }



    public void showtest(){
        ObservableList<test> list = gettestlist();
       // colid.setCellValueFactory(new PropertyValueFactory<test, Integer>("id_test"));
        colcat.setCellValueFactory(new PropertyValueFactory<test,String>("categories"));
        colf.setCellValueFactory(new PropertyValueFactory<test,String>("test"));
        cold.setCellValueFactory(new PropertyValueFactory<test, Integer>("duree"));
        coldate.setCellValueFactory(new PropertyValueFactory<test, DateTimeFormatter>("d"));


        tv.setItems(list);


    }

    public void showtest1(){
        ObservableList<tester> list = gettestlist1();
        id.setCellValueFactory(new PropertyValueFactory<tester, Integer>("id"));
        test1.setCellValueFactory(new PropertyValueFactory<tester,String>("formation"));

        user.setCellValueFactory(new PropertyValueFactory<tester, Integer>("id_user"));
        date.setCellValueFactory(new PropertyValueFactory<tester, DateTimeFormatter>("date"));
        note.setCellValueFactory(new PropertyValueFactory<tester, Integer>("note"));


        tv1.setItems(list);


    }


    public void handelMouseAction(MouseEvent mouseEvent) {
        test t = tv.getSelectionModel().getSelectedItem();
       // System.out.println("id_test" + t.getId_test());txtid.setText(String.valueOf(t.getId_test()));
        System.out.println("categories"+t.getCategories());cat.setText(t.getCategories());
        //System.out.println("test==  "+t.getTest());txtfile.setText(t.getTest());
        //System.out.println("duree"+t.getDuree());txtduree.setText(String.valueOf(t.getDuree()));
        //System.out.println("date_creation"+t.getD());
        // String strdate = dateFormat.format(t.getD());
        //txtDO.setText(String.);



    }





    public void tester(ActionEvent event) throws URISyntaxException, IOException, SQLException {
        test t = tv.getSelectionModel().getSelectedItem();
        Desktop d = Desktop.getDesktop();
        d.browse(new URI(t.getTest()));
        int id,idt,idu=2;
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String queryy1="SELECT * FROM test WHERE id_test LIKE '"+t.getId_test()+"'";
        Statement st2 = getConnection().createStatement();
        ResultSet rs2 = st2.executeQuery(queryy1);
        System.out.println("33333333333333");
        String formation = null;
        while(rs2.next())
        {
           formation= rs2.getString("formation");
        }

        idt=t.getId_test();
        System.out.println(idt);
        String query = "INSERT INTO `tester` (`id`, `id_test`, `foramtion`, `id_user`, `date`, `note`) VALUES (NULL, '"+idt+"', '"+formation+"', '"+idu+"', '"+time.format(now)+"', NULL)";
                 //     String dd = "INSERT INTO `tester` (`id`, `id_test`, `id_user`, `date`, `note`) VALUES (NULL, '"+idt+"', '"+idu+"', '"+time.format(now)+"', NULL)";
        executeQuery(query);




    }







    public void handleSearch(KeyEvent keyEvent) {
        initFilter();
        showtest();
        initFilter();


    }

    ObservableList<test> dataList = FXCollections.observableArrayList();





   private void initFilter() {


        cat.textProperty().addListener(new InvalidationListener() {


            @Override

            public void invalidated(Observable o) {

                if(cat.textProperty().get().isEmpty()) {

                    tv.setItems(tv.getItems());

                    return;

                }

                ObservableList<test> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<test, ?>> cols = tv.getColumns();

                for(int i=0; i<tv.getItems().size(); i++) {



                    for(int j=0; j<cols.size(); j++) {

                        TableColumn col = cols.get(j);

                        String cellValue = col.getCellData(tv.getItems().get(i)).toString();

                        cellValue = cellValue.toLowerCase();

                        if(cellValue.contains(cat.textProperty().get().toLowerCase())) {

                            tableItems.add(tv.getItems().get(i));

                            break;

                        }

                    }


                }

                tv.setItems(tableItems);

            }

        });

    }

    public void score()
    {
        test t = tv.getSelectionModel().getSelectedItem();

        File f;

        String path = "C:\\Users\\nader\\Downloads\\2.csv";
        String line="";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine())!= null){
                String [] val = line.split(",");
                System.out.println("time:"+ val[0]+"score"+val[3]);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mt(ActionEvent event) {
    }



    public void handel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/evamembre.fxml"));
        Stage window =(Stage) btnhom.getScene().getWindow();
        window.setScene(new Scene(root,750,500));


    }
}
