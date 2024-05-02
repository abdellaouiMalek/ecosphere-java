package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import tn.esprit.services.PostServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ContainerPost {

    @FXML
    private Label auteurL;

    @FXML
    private Label contenuL;

    @FXML
    private ImageView imageP;

    @FXML
    private Label titreL;
    private Post post;
    public void setData(Post post) {
        // Attempt to load the image and handle possible errors.
        if (post ==null){
            throw new IllegalArgumentException("Post cannot be null");
        }
        this.post=post;
        String imagePath = convertToFileSystemPath(post.getImage());
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                InputStream inputstream = getClass().getResourceAsStream(imagePath);
                if (inputstream != null) {
                    Image image = new Image(inputstream);
                    imageP.setImage(image);
                } else {
                    System.out.println("Image non trouvée : " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("Chemin d'image null ou vide");
        }
        auteurL.setText(post.getAuteur());
        titreL.setText(post.getTitle());
        contenuL.setText(post.getContent());

        if(post == null){
            throw new IllegalArgumentException("Le post ne peut pas etre null");
        }
    }
    public String convertToFileSystemPath(String imagePath) {
        if (imagePath != null && imagePath.startsWith("Images/")) {
            return imagePath;
        } else {
            return "Images/" + imagePath; // Sinon, préfixez le chemin avec "/Images/"
        }
    }
    @FXML
    void UpdateP(ActionEvent event) {
        try {
            // Charger la vue de modification du post
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdatePost.fxml"));
            Parent editPostView = loader.load();
            // Récupérer le contrôleur de la vue de modification
            UpdatePost editPostController = loader.getController();
            // Passer le post à modifier au contrôleur de la vue de modification
            editPostController.initData(post);
            // Afficher la vue de modification dans une nouvelle fenêtre
            Scene editScene = new Scene(editPostView);
            Stage window = new Stage();
            window.setScene(editScene);
            window.setTitle("Modifier le post");
            window.showAndWait();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du post", e.getMessage());
        }
    }
    @FXML
    void DeleteP(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Voulez-vous vraiment supprimer ce post?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PostServices ps = new PostServices();
                ps.delete(post); // Utilisez votre service pour supprimer le post
                showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Post supprimé avec succès!");
                // Si vous souhaitez effectuer une action supplémentaire après la suppression, vous pouvez le faire ici
            }
        } catch (Exception e) {
            // Gérer toute exception qui pourrait survenir pendant la suppression
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du post", e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void CommentP(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/commentaireforum.fxml"));
            auteurL.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
