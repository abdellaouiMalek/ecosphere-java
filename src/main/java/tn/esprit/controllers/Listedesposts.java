package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
import tn.esprit.services.PostServices;
public class Listedesposts implements Initializable {
    @FXML
    private VBox postContainer;
    @FXML
    private ScrollPane ScrollPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PostServices ps = new PostServices();
        List<Post> listPost = ps.getAll();
        try{
            for(Post post : listPost)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL postFXMLURL=getClass().getResource("/POSTFB.fxml");
                if (postFXMLURL == null){
                    throw new IllegalStateException("Cannot find Containerpost.fxml.Make sure that this file is placed correctly");
                }
                fxmlLoader.setLocation(postFXMLURL);
                VBox postBox = fxmlLoader.load();
                POSTFB postController=fxmlLoader.getController();
                postController.setData(post);
                postContainer.getChildren().add(postBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ajouterP(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouternouveaupost.fxml"));
            Scene scene = new Scene(root);
            // Obtenir la scène actuelle à partir de n'importe quel nœud de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Obtenir la fenêtre principale à partir de la scène actuelle
            Stage primaryStage = (Stage) currentScene.getWindow();
            // Définir la nouvelle scène dans la fenêtre principale
            primaryStage.setScene(scene);
            // Afficher la nouvelle fenêtre
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


