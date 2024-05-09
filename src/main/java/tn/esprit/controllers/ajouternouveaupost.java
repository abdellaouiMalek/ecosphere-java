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
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.HateSpeech;
import tn.esprit.services.PostServices;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
public class ajouternouveaupost {
    @FXML
    private TextField auteurTF;
    @FXML
    private TextArea contenuTF;
    @FXML
    private TextField imageTF;
    @FXML
    private TextField titreTF;
    private final PostServices ps = new PostServices();
    private String imagePath;

    User loggedUser = SessionUser.getLoggedUser();
    int loggedId = loggedUser.getId();
    String username = loggedUser.getFirst_name();
    @FXML
    void AjouterP(ActionEvent event) {
        try {
            if (ps.containsBadwords(contenuTF.getText()) || ps.containsBadwords(titreTF.getText())) {
                throw new IllegalArgumentException("Le titre ou le contenu du post contient des mots inappropriés.");
            }
            boolean containsInappropriateWords = HateSpeech.containsInappropriateWords(contenuTF.getText());
            if (containsInappropriateWords) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le titre ou le contenu contient des mots inappropriés !");
                alert.showAndWait();
                return;
            }
            ps.add(new Post(titreTF.getText(), username,  contenuTF.getText(), imagePath));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le post a été ajouté avec succès !");
            alert.showAndWait();
            titreTF.clear();
            auteurTF.clear();
            contenuTF.clear();
            imagePath = null;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!!!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void naviguer() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Listdespostes.fxml"));
            auteurTF.getScene().setRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String targetDirectory = "src/main/resources/Images/";
            try {
                File directory = new File(targetDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = generateUniqueFileName(selectedFile.getName());
                Path targetPath = new File(targetDirectory + fileName).toPath();
                Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String generateUniqueFileName(String originalFileName) {
        // Append a timestamp to the original filename to make it unique
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFileName;
    }

}
