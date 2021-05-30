package sample;

import edu.ufp.inf.lp2_aed2.projeto.Comeco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static edu.ufp.inf.lp2_aed2.projeto.Comeco.comeco_main;
import sample.Controller.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Projeto");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.setOnShowing(event -> {
            comeco_main();
            //startController();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}