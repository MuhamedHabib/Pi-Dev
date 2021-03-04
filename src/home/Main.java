package home;


import javafx.application.Application;
import Entites.evaluation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.ServiceEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        root = FXMLLoader.load(getClass().getResource("src/gui/FXMLEvaluation.fxml"));
        primaryStage.setTitle("HelpDesk");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private static String url ="jdbc:mysql://localhost:3306/helpdesk";
    private static String user ="root";
    private static String pwd ="";

    public static void main(String[] args)  {
        launch(args);

        evaluation e1 = new evaluation(81, "BackEnd");

        ServiceEvaluation se = new ServiceEvaluation();
        try{
           se.add(e1);

            System.out.println("---------ADD mrigle------------");
            List<evaluation> l = new ArrayList<evaluation>();
            l = se.read();
            for( evaluation e : l){
                System.out.println("--------------------");
                System.out.println(e.toString());

            }

            System.out.println("---------display mrigle------------");

            se.update(e1);

            System.out.println(se.read());



            se.delete(e1);
            System.out.println(se.read());
            System.out.println(" ------------- delete mrigle -------");



        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
        }

    }
}
