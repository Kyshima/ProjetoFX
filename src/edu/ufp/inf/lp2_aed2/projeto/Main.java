package edu.ufp.inf.lp2_aed2.projeto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import static edu.ufp.inf.lp2_aed2.projeto.Comeco.comeco_main;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));

        primaryStage.setTitle("Projeto");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        comeco_main(args);
        launch(args);
    }
}
