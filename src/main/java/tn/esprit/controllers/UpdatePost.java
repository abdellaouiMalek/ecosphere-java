package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tn.esprit.models.Post;
import tn.esprit.services.PostServices;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
public class UpdatePost {
    @FXML
    private TextField auteurTF;
    @FXML
    private TextArea contenuTF;
    @FXML
    private TextField titreTF;
    private Post selectedPost;
    private final PostServices ps = new PostServices();
    private String imagePath;
    public void initData(Post post)
    {
        this.selectedPost = post;
        //Afficher les donnees du post dans les champs corespendantes
        auteurTF.setText(post.getAuteur());
        titreTF.setText(post.getTitle());
        contenuTF.setText(post.getContent());
    }
    @FXML
    void modifierP(ActionEvent event) {
        selectedPost.setAuteur(auteurTF.getText());
        selectedPost.setTitle(titreTF.getText());
        selectedPost.setContent(contenuTF.getText());
        if(imagePath != null && !imagePath.isEmpty())
        {
            selectedPost.setImage(imagePath);
        }
        ps.update(selectedPost);
        showAlert(Alert.AlertType.INFORMATION,"Modification réussie",null,"Les modifications ont été enregistrées avec succés");

    }
    // Fonction utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Définir le répertoire cible dans les ressources
            String targetDirectory = "src/main/resources/Images/";
            try {
                // Créer le répertoire cible s'il n'existe pas
                File directory = new File(targetDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                // Copier le fichier sélectionné vers le répertoire cible
                Path sourcePath = selectedFile.toPath();
                Path targetPath = new File(targetDirectory + selectedFile.getName()).toPath();
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                // Mettre à jour le chemin de l'image avec le chemin relatif du fichier dans les ressources
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Listdespostes.fxml"));
            auteurTF.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
