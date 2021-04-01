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
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class membrequizController implements Initializable {

    @FXML
    private TableView<quiz> tvq;

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
    private TextField txtfile;

    @FXML
    private TextField txtduree;

    @FXML
    private DatePicker txtDO;

    @FXML
    private Button btnajout;

    @FXML
    private Button btnhom;


    @FXML
    private Button btnmod;

    @FXML
    private Button btnsupp;
    // txtDO = new DatePicker();

    PreparedStatement preparedStatement;
    Connection connection;

    public membrequizController() {

    }



    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showtest();


    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
            return conn;


        } catch (SQLException ex) {
            System.out.println(("Error: " + ex.getMessage()));
            return null;

        }
    }

    public ObservableList<quiz> gettestlist() {
        ObservableList<quiz> testlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM quiz";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            quiz t;
            while (rs.next()) {
                t = new quiz(rs.getInt("id_quiz"), rs.getString("categories"), rs.getString("quiz"), rs.getInt("duree"), rs.getDate("date_creation"));
                testlist.add(t);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testlist;


    }

    public ObservableList<quiz> gettestlist1() {
        ObservableList<quiz> testlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM quiz where categories LIKE '" + txtcat.getText() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            quiz t;
            while (rs.next()) {
                t = new quiz(rs.getInt("id_quiz"), rs.getString("categories"), rs.getString("quiz"), rs.getInt("duree"), rs.getDate("date_creation"));
                testlist.add(t);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testlist;


    }


    public void showtest() {
        ObservableList<quiz> list = gettestlist();
        colid.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("id_quiz"));
        colcat.setCellValueFactory(new PropertyValueFactory<quiz, String>("categories"));
        colf.setCellValueFactory(new PropertyValueFactory<quiz, String>("quiz"));
        cold.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("duree"));
        coldate.setCellValueFactory(new PropertyValueFactory<quiz, DateTimeFormatter>("d"));


        tvq.setItems(list);


    }

    public void showtest1() {
        ObservableList<quiz> list = gettestlist1();
        colid.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("id_test"));
        colcat.setCellValueFactory(new PropertyValueFactory<quiz, String>("categories"));
        colf.setCellValueFactory(new PropertyValueFactory<quiz, String>("quiz"));
        cold.setCellValueFactory(new PropertyValueFactory<quiz, Integer>("duree"));
        coldate.setCellValueFactory(new PropertyValueFactory<quiz, DateTimeFormatter>("d"));


        tvq.setItems(list);


    }


    public void handelMouseAction(MouseEvent mouseEvent) {
        quiz t = tvq.getSelectionModel().getSelectedItem();
        // System.out.println("id_test" + t.getId_test());txtid.setText(String.valueOf(t.getId_test()));
        System.out.println("categories" + t.getCategories());
        txtcat.setText(t.getCategories());
        //System.out.println("test==  "+t.getTest());txtfile.setText(t.getTest());
        //System.out.println("duree"+t.getDuree());txtduree.setText(String.valueOf(t.getDuree()));
        //System.out.println("date_creation"+t.getD());
        // String strdate = dateFormat.format(t.getD());
        //txtDO.setText(String.);


    }


    public void tester(ActionEvent event) throws URISyntaxException, IOException {
        quiz q = tvq.getSelectionModel().getSelectedItem();
        Desktop d = Desktop.getDesktop();
        d.browse(new URI(q.getQuiz()));
        int id,idt,idu=2;
        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        idt=q.getId_quiz();
        System.out.println(idt);
        String query = "INSERT INTO `tester` (`id`, `id_test`, `id_user`, `date`, `note`) VALUES (NULL, '"+idt+"', '"+idu+"', '"+time.format(now)+"', NULL)";
        //     String dd = "INSERT INTO `tester` (`id`, `id_test`, `id_user`, `date`, `note`) VALUES (NULL, '"+idt+"', '"+idu+"', '"+time.format(now)+"', NULL)";
        executeQuery(query);



    }




    private void initFilter() {


        txtcat.textProperty().addListener(new InvalidationListener() {


            @Override

            public void invalidated(Observable o) {

                if (txtcat.textProperty().get().isEmpty()) {

                    tvq.setItems(tvq.getItems());

                    return;

                }

                ObservableList<quiz> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<quiz, ?>> cols = tvq.getColumns();

                for (int i = 0; i < tvq.getItems().size(); i++) {


                    for (int j = 0; j < cols.size(); j++) {

                        TableColumn col = cols.get(j);

                        String cellValue = col.getCellData(tvq.getItems().get(i)).toString();

                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(txtcat.textProperty().get().toLowerCase())) {

                            tableItems.add(tvq.getItems().get(i));

                            break;

                        }

                    }


                }

                tvq.setItems(tableItems);

            }

        });

    }

    public void handelsearch(KeyEvent keyEvent) {
        initFilter();
        showtest();
    }

    public void handel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/evamembre.fxml"));
        Stage window =(Stage) btnhom.getScene().getWindow();
        window.setScene(new Scene(root,750,500));


    }


    public void btnhom(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/evamembre.fxml"));
        Stage window =(Stage) btnhom.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
}