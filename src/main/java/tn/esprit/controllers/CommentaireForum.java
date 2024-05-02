package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Comment;
import tn.esprit.services.CommentServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CommentaireForum implements Initializable {

    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private VBox commentContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommentServices cs = new CommentServices();
        List<Comment> listComment = cs.getAll();
        try{
            for(Comment commentaire :listComment) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL commentFXMLURL = getClass().getResource("/Containercommentaire.fxml");
                if (commentFXMLURL == null) {
                    throw new IllegalStateException("Cannot find it");
                }
                fxmlLoader.setLocation(commentFXMLURL);
                VBox commentBox = fxmlLoader.load();
                Containercommentaire commentController = fxmlLoader.getController();
                commentController.setData(commentaire);
                commentContainer.getChildren().add(commentBox);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void ajouterC(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouternouveaucommentaire.fxml"));
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
