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
import tn.esprit.services.PostServices;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Listedesposts implements Initializable {
    @FXML
    private VBox postContainer;
    @FXML
    private void ajouterP(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajouternouveaupost.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PostServices ps = new PostServices();
        List<Post> listPost = ps.getAll();
        try {
            for (Post post : listPost) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/POSTFB.fxml"));
                VBox postBox = fxmlLoader.load();
                POSTFB postController = fxmlLoader.getController();
                postController.setData(post);
                postContainer.getChildren().add(postBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
