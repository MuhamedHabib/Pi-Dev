package controller;


import Entites.quiz;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class quizController implements Initializable {

    @FXML
    private TableView<quiz> tv;

    @FXML
    private TableColumn<quiz, Integer> colid;

    @FXML
    private TableColumn<quiz, String> colcat;

    @FXML
    private TableColumn<quiz, String> colf;

    @FXML
    private TableColumn<quiz, Integer> cold;

    @FXML
    private TableColumn<quiz, DateTimeFormatter> coldate;

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
    private Button btnquiz;



    @FXML
    private Button btnmod;

    @FXML
    private Button btnsupp;
   // txtDO = new DatePicker();

    PreparedStatement preparedStatement;
    Connection connection;
    public quizController() {

    }


    @FXML
   void ajouter(ActionEvent event) {
        String s ="current_timestamp()";
        Date date = null;

        String query = "INSERT INTO quiz(id_quiz,categories,quiz,duree,date_creation) VALUES ("+txtid.getText()+",'" + txtcat.getText()+"','"+txtfile.getText()+"','"+txtduree.getText()+"','"+txtDO.getValue().toString()+ "')";
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
        String query = "UPDATE quiz SET categories = '"+txtcat.getText()+"', quiz = '"+txtfile.getText()+"', duree = '"+txtduree.getText()+"', date_creation = '"+txtDO.getValue().toString()+ "' WHERE id_quiz = "+txtid.getText()+"";
        executeQuery(query);
        showtest();


    }

    @FXML
    void supprimer(ActionEvent event) {
        String query= "DELETE FROM quiz WHERE id_quiz ="+txtid.getText()+"";
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

    public ObservableList<quiz> gettestlist()
    {
        ObservableList<quiz> testlist= FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM quiz";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            quiz t;
            while(rs.next()){
                t=new quiz(rs.getInt("id_quiz"), rs.getString("categories"),rs.getString("quiz"),rs.getInt("duree"), rs.getDate("date_creation"));
                testlist.add(t);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testlist;


    }


    public void showtest(){
        ObservableList<quiz> list = gettestlist();
        colid.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("id_quiz"));
        colcat.setCellValueFactory(new PropertyValueFactory<quiz,String>("categories"));
        colf.setCellValueFactory(new PropertyValueFactory<quiz,String>("quiz"));
        cold.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("duree"));
        coldate.setCellValueFactory(new PropertyValueFactory<quiz, DateTimeFormatter>("d"));


        tv.setItems(list);


    }


    public void handelMouseAction(MouseEvent mouseEvent) {
        quiz q = tv.getSelectionModel().getSelectedItem();
        System.out.println("id_quiz" + q.getId_quiz());txtid.setText(String.valueOf(q.getId_quiz()));
        System.out.println("categories"+q.getCategories());txtcat.setText(q.getCategories());
        System.out.println("test==  "+q.getQuiz());txtfile.setText(q.getQuiz());
        System.out.println("duree"+q.getDuree());txtduree.setText(String.valueOf(q.getDuree()));
        System.out.println("date_creation"+q.getD());
       // String strdate = dateFormat.format(t.getD());
        //txtDO.setText(String.);



    }



    public void tester(ActionEvent event) throws URISyntaxException, IOException {
        quiz q = tv.getSelectionModel().getSelectedItem();
        Desktop d = Desktop.getDesktop();
        d.browse(new URI(q.getQuiz()));



    }
    public void resultat(ActionEvent event)throws IOException, SQLException {
        String f = null;
        int n,i;
        String path="";
        FileChooser fc =new FileChooser();
        File sl = fc.showOpenDialog(null);
        File r;
        if(sl != null)
        {
            r = new File(String.valueOf(sl));
            String p = r.getAbsolutePath();
            path = p ;
            System.out.println(path);
            String query = "UPDATE quiz SET resultat = '"+path+"', quiz = '"+txtfile.getText()+"', duree = '"+txtduree.getText()+"', date_creation = '"+txtDO.getValue().toString()+ "' WHERE id_quiz = "+txtid.getText()+"";
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
                        String query1 = "UPDATE quizzer SET note ='" + n + "' WHERE id_user =" + rs2.getString("id_user") + " AND id_quiz= '"+i+"';";
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

                ObservableList<quiz> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<quiz, ?>> cols = tv.getColumns();

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


    public void homequiz(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/testfxml1.fxml"));
        Stage window =(Stage) btnquiz.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}
