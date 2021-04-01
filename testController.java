package controller;




import Entites.test;
import Entites.user;
import javafx.application.Application;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class testController implements Initializable {

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
    private TextField txtid;

    @FXML
    private TextField txtcat;
    @FXML
    private TextField sch;

    @FXML
    private TextField txtfile;

    @FXML
    private TextField txtduree;


    @FXML
    private DatePicker txtDO;

    @FXML
    private Button btnajout;
    @FXML
    private Button btntest;



    @FXML
    private Button btnmod;

    @FXML
    private Button btnsupp;
   // txtDO = new DatePicker();

    PreparedStatement preparedStatement;
    Connection connection;
    public testController() {

    }


    @FXML
   void ajouter(ActionEvent event) {
        String s ="current_timestamp()";
        Date date = null;

        String query = "INSERT INTO TEST(id_test,categories,test,duree,date_creation) VALUES ("+txtid.getText()+",'" + txtcat.getText()+"','"+txtfile.getText()+"','"+txtduree.getText()+"','"+txtDO.getValue().toString()+ "')";
        executeQuery(query);
        showtest();

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

    @FXML
    void modifier(ActionEvent event) {
        String query = "UPDATE test SET categories = '"+txtcat.getText()+"', test = '"+txtfile.getText()+"', duree = '"+txtduree.getText()+"', date_creation = '"+txtDO.getValue().toString()+ "' WHERE id_test = "+txtid.getText()+"";
        executeQuery(query);
        showtest();


    }

    @FXML
    void supprimer(ActionEvent event) {
        String query= "DELETE FROM TEST WHERE id_test ="+txtid.getText()+"";
        executeQuery(query);
        showtest();

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showtest();
        

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


    public void showtest(){
        ObservableList<test> list = gettestlist();


        colid.setCellValueFactory(new PropertyValueFactory<test, Integer>("id_test"));
        colcat.setCellValueFactory(new PropertyValueFactory<test,String>("categories"));
        colf.setCellValueFactory(new PropertyValueFactory<test,String>("test"));
        cold.setCellValueFactory(new PropertyValueFactory<test, Integer>("duree"));
        coldate.setCellValueFactory(new PropertyValueFactory<test, DateTimeFormatter>("d"));


        tv.setItems(list);


    }


    public void handelMouseAction(MouseEvent mouseEvent) {
        test t = tv.getSelectionModel().getSelectedItem();
        System.out.println("id_test" + t.getId_test());txtid.setText(String.valueOf(t.getId_test()));
        System.out.println("categories"+t.getCategories());txtcat.setText(t.getCategories());
        System.out.println("test==  "+t.getTest());txtfile.setText(t.getTest());
        System.out.println("duree"+t.getDuree());txtduree.setText(String.valueOf(t.getDuree()));
        System.out.println("date_creation"+t.getD());
       // String strdate = dateFormat.format(t.getD());
        //txtDO.setText(String.);




    }



    public void tester(ActionEvent event) throws URISyntaxException, IOException {
        test t = tv.getSelectionModel().getSelectedItem();
        Desktop d = Desktop.getDesktop();
        d.browse(new URI(t.getTest()));



    }


    public ObservableList<user> getuser()
    {
        ObservableList<user> userlist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM user";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            user u;
            while(rs.next()){
                u=new user(rs.getInt("id_user"), rs.getString("Email"),rs.getString("Status"));
                userlist.add(u);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userlist;


    }





    public void resultat(ActionEvent event) throws IOException, SQLException {
        String f = null;
        int n,i;
        FileChooser fc =new FileChooser();
        File sl = fc.showOpenDialog(null);
        File r;
        String path="";
        if(sl != null) {
            r = new File(String.valueOf(sl));
            String p = r.getAbsolutePath();
            path = p;
            System.out.println(p);
            String query = "UPDATE test SET resultat = '" + p + "', test = '" + txtfile.getText() + "', duree = '" + txtduree.getText() + "', date_creation = '" + txtDO.getValue().toString() + "' WHERE id_test = " + txtid.getText() + "";
            executeQuery(query);
            showtest();
        }



     String line = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) {
            String[] val = line.split(",");
            boolean tst = false;
            String queryy = "SELECT * FROM `user`";
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(queryy);

                while (rs.next())
                {
                    if (rs.getString("Email").equals(val[1])) {
                        System.out.println(rs.getString("Email"));
                        //tst = true;
                        String queryy1="SELECT * FROM user WHERE Email LIKE '"+val[1]+"'";
                        Statement st2 = getConnection().createStatement();
                        ResultSet rs2 = st2.executeQuery(queryy1);
                        System.out.println("33333333333333");
                        while(rs2.next())
                        {

                            System.out.println(rs2.getString("id_user"));
                            System.out.println("5555555555");
                            if (val[2].length() < 7) {
                                n = Integer.parseInt(String.valueOf(val[2].charAt(0)));
                                System.out.println(n);
                            } else {
                                n = Integer.parseInt(String.valueOf(val[2].charAt(0))) * 10 + Integer.parseInt(String.valueOf(val[2].charAt(1)));
                                System.out.println(n);
                            }
                            if (val[3].length() < 2) {
                                i = Integer.parseInt(String.valueOf(val[3].charAt(0)));
                                System.out.println(n);
                            } else {
                                i = Integer.parseInt(String.valueOf(val[3].charAt(0))) * 10 + Integer.parseInt(String.valueOf(val[3].charAt(1)));
                                System.out.println(n);
                            }
                            System.out.println("66666666666");
                            String query1 = "UPDATE tester SET note ='" + n + "' WHERE id_user =" + rs2.getString("id_user") + " AND id_test= '"+i+"';";
                                           //UPDATE `tester` SET `note` = '15' WHERE `id_test` = 1 AND `id_user` = 1 ;
                            System.out.println("aaaaaaaaaaaa");
                            executeQuery(query1);
                            tst = true;
                            break;
                         }
                    }

                }if(!rs.next() && !tst ){System.out.println("no record of this email "+val[1]);}



        }






    }


    private void initFilter() {


        sch.textProperty().addListener(new InvalidationListener() {


            @Override

            public void invalidated(Observable o) {

                if (sch.textProperty().get().isEmpty()) {

                    tv.setItems(tv.getItems());

                    return;

                }

                ObservableList<test> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<test, ?>> cols = tv.getColumns();

                for (int i = 0; i < tv.getItems().size(); i++) {


                    for (int j = 0; j < cols.size(); j++) {

                        TableColumn col = cols.get(j);

                        String cellValue = col.getCellData(tv.getItems().get(i)).toString();

                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(sch.textProperty().get().toLowerCase())) {

                            tableItems.add(tv.getItems().get(i));

                            break;

                        }

                    }


                }

                tv.setItems(tableItems);

            }

        });

    }

    public void handelsearch(KeyEvent keyEvent) {
        initFilter();
        showtest();
    }

    public void handelhome(KeyEvent keyEvent) {
        class Main extends Application {

            @Override
            public void start(Stage primaryStage) throws Exception{
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/testfxml1.fxml"));
                primaryStage.setTitle("HelpDesk");
                primaryStage.setScene(new Scene(root, 300, 275));
                primaryStage.show();
            }


            public void main(String[] args) {
                launch(args);
            }
        }
    }

    public void rtest(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/eva.fxml"));
        Stage  window =(Stage) btntest.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}
