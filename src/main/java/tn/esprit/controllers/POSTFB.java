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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import tn.esprit.services.PostServices;
import tn.esprit.services.Reactions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class POSTFB {

    @FXML
    private Label auteurL;
    @FXML
    private Label contenuL;
    @FXML
    private ImageView imageP;
    @FXML
    private Label titreL;
    private Post post;
    private long startTime = 0;
    private Reactions currentReaction;
    @FXML
    private Label ReactionName;
    @FXML
    private ImageView imgReaction;
    public void setData(Post post) {
        // Attempt to load the image and handle possible errors.
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        this.post = post;
        String imagePath = convertToFileSystemPath(post.getImage());
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                InputStream is = getClass().getResourceAsStream(imagePath);
                if (is != null) {
                    Image image = new Image(is);
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
        nbreactions.setText(String.valueOf(post.getTotalReactions()));
        currentReaction = Reactions.NON;
        if (post == null) {
            throw new IllegalArgumentException("Le post ne peut pas etre null");
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
            }
        } catch (Exception e) {
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
    public String convertToFileSystemPath(String imagePath) {
        if (imagePath != null && imagePath.startsWith("/Images/")) {
            return imagePath; // Si le chemin est déjà relatif au dossier "Images", le retourner tel quel
        } else {
            return "/Images/" + imagePath; // Sinon, préfixez le chemin avec "/Images/"
        }
    }
    @FXML
    void CommentP(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/commentaireforum.fxml"));
            Parent root = loader.load();
            CommentaireForum controller = loader.getController();
            controller.setdata(post);
            auteurL.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private ImageView imageviewangry;
    @FXML
    private ImageView imageviewlike;
    @FXML
    private ImageView imgviewlove;
    @FXML
    private HBox likecontainer;
    @FXML
    private Label nbreactions;
    @FXML
    private HBox reactionsContainer;
    public void onLikeContainerPressed(javafx.scene.input.MouseEvent mouseEvent) {
        startTime = System.currentTimeMillis();
    }
    public void onLikeContainerMouseReleased(javafx.scene.input.MouseEvent mouseEvent) {
        if (System.currentTimeMillis() - startTime > 500) {
            reactionsContainer.setVisible(true);
        }
    }
    private void setReaction(Reactions reactions) {
        Image image = new Image(getClass().getResourceAsStream(reactions.getImgSrc()));
        imgReaction.setImage(image);
        ReactionName.setText(reactions.getName());
        ReactionName.setTextFill(Color.web(reactions.getColor()));

        if (currentReaction == Reactions.NON) {
            post.setTotalReactions(post.getTotalReactions() + 1);
        }
        currentReaction = reactions;
        if (currentReaction == Reactions.NON) {
            post.setTotalReactions(post.getTotalReactions() - 1);
        }
        PostServices postServices = new PostServices();
        nbreactions.setText(String.valueOf(post.getTotalReactions()));
    }
    public void onReactionImgPressed(javafx.scene.input.MouseEvent me) {
        switch (((ImageView) me.getSource()).getId()) {
            case "imgviewlove":
                setReaction(Reactions.LOVE);
                break;
            case "imageviewangry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
    }
}


