package tn.esprit.test;

import com.stripe.Stripe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Stripe.apiKey = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("login");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    }

