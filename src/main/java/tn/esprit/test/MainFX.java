package tn.esprit.test;

import com.stripe.Stripe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage  stage) throws Exception {
        try {
            // Définir votre clé API Stripe ici
            Stripe.apiKey = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";
            // Charger la vue FXML depuis le fichier MyTest.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/ObjectView.fxml"));

            // Créer une scène avec la racine chargée depuis le FXML
            Scene scene = new Scene(root);

            // Définir la scène et les paramètres de la fenêtre principale
            stage.setTitle("Sharing-Hub");
            stage.setMinWidth(1100);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Gérer les erreurs de chargement de la vue FXML
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch(args);
    }
}
