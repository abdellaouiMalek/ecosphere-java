package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFX extends Application {

    @Override
    public void start(Stage stage)throws Exception{

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/History.fxml"));

        Scene scene = new Scene(loader.load());
        //scene.setRoot(loader.load());
        stage.setTitle("Sharing-Hub ");
        stage.setMinWidth(1100);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}

