package sample;

import Entities.Message;
import Entities.Reclamation;
import Service.ServiceMessage;
import com.sun.javafx.charts.Legend;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ControlleurAdminMessage implements Initializable {
    @FXML
    private TableView<Message> Messages;
    @FXML
    private TextField recherche;
    final ObservableList<Message> dataList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceMessage sm = new ServiceMessage();

        TableColumn<Message, String> idmsg = new TableColumn<>("Id message ");
        idmsg.setCellValueFactory(new PropertyValueFactory<>("id_message"));

        TableColumn<Message, String> idDate = new TableColumn<>("Date création");
        idDate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        TableColumn<Message, String> User = new TableColumn<>("User");
        User.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<Message, String> message = new TableColumn<>("message");
        message.setCellValueFactory(new PropertyValueFactory<>("message"));


        TableColumn delCol = new TableColumn("Supprimer");
        delCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Message, String>, TableCell<Message, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Message, String>, TableCell<Message, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Message, String> param) {
                        final TableCell<Message, String> cell = new TableCell<Message, String>() {

                            final Button delete = new Button("delete");


                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    delete.setOnAction(event -> {
                                        Message meet = getTableView().getItems().get(getIndex());
                                        sm.Deletemsg(meet);
                                       /* dataList.clear();
                                        try {
                                            dataList.addAll(sm.Affichermsg());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        } */
                                        System.out.println(meet);
                                    });

                                    setGraphic(delete);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        delCol.setCellFactory(cellFactoryDelete);


        Messages.getColumns().add(idmsg);
        Messages.getColumns().add(idDate);
        Messages.getColumns().add(User);
        Messages.getColumns().add(message);
        Messages.getColumns().add(delCol);


        List<Message> list = null;
        try {
            list = sm.Affichermsg();
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Message Message : list) {
            Messages.getItems().add(Message);
        }
/*
        try { dataList.addAll(sm.Affichermsg()); }
        catch (SQLException e) { e.printStackTrace(); }

        Messages.getItems().addAll(dataList);
        FilteredList<Message> filteredData = new FilteredList<>(dataList, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(m -> {
                System.out.println(m);
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();

                if (m.getMessage().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else { return false;  }
            });
        });
        SortedList<Message> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Messages.comparatorProperty());
        Messages.setItems(sortedData);
*/
    }

}
