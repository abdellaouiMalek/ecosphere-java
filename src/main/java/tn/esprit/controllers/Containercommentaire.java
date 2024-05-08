package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Comment;
import tn.esprit.services.CommentServices;

import java.util.Optional;

public class Containercommentaire {

        @FXML
        private Label ContenuC;
        private Comment commentaire;
        public void setData(Comment commentaire){
            if (commentaire ==null){
                throw new IllegalArgumentException("Comment cannot be null");
            }
            this.commentaire=commentaire;
            ContenuC.setText(commentaire.getContenu());
            if(commentaire == null){
                throw new IllegalArgumentException("Le commentaire ne peut pas etre null");
            }
        }
        @FXML
        void deleteC(ActionEvent event) {
            try {
                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirmez la suppression");
                alert.setContentText("Voulez-vous vraiment supprimer ce commentaire?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si l'utilisateur confirme la suppression, supprimer le post
                    CommentServices cs =new CommentServices();
                    cs.delete(commentaire); // Utilisez votre service pour supprimer le post
                    showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Commentaire supprimé avec succès!");
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
        void updateC(ActionEvent event) {
            try{
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/UpdateComment.fxml"));
                Parent editCommentView = loader.load();
                UpdateComment editCommentController = loader.getController();
                editCommentController.initData(commentaire);
                Scene editScene = new Scene(editCommentView);
                Stage window = new Stage();
                window.setScene(editScene);
                window.setTitle("Modifier le commentaire");
                window.showAndWait();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du post", e.getMessage());
            }
        }

    }


