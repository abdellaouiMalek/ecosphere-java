package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import tn.esprit.services.PostServices;
import java.net.URL;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
public class AfficherPost implements Initializable {
    @FXML
    private ListView<Post> listViewPost;
    private PostServices ps = new PostServices();
    private void showAlert(Alert.AlertType aType, String ti, String head, String cont) {
        Alert alert = new Alert(aType);
        alert.setTitle(ti);
        alert.setHeaderText(head);
        alert.setContentText(cont);
        alert.showAndWait();
    }
    @FXML
    public void supprimerPost(ActionEvent event) {
        PostServices ps = new PostServices();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmez la suppression ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ps.delete(listViewPost.getSelectionModel().getSelectedItem());
            listViewPost.getItems().remove(listViewPost.getSelectionModel().getSelectedItem());
            showSuccessMessage("Post supprimé avec succes!");
        } else {
            alert.close();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void modifierPost(ActionEvent event) {
        Post selectedPost = listViewPost.getSelectionModel().getSelectedItem();
        if(selectedPost ==null)
        {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun post sélectionné", "Veuillez sélectionner un post à modifier.");
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdatePost.fxml"));
            Parent edit = loader.load();
            UpdatePost editPost = loader.getController();
            editPost.initData(selectedPost);
            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(800); window.setMaxHeight(800); window.setMinHeight(800);
            window.setWidth(800); window.setMaxWidth(800); window.setMinWidth(800);
            window.setTitle("Ecosphere| Modifier un post");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            ObservableList<Post> posts = FXCollections.observableArrayList(ps.getAll());
            listViewPost.setItems(posts);
    }
}

