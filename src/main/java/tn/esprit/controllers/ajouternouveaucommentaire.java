package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentServices;
import tn.esprit.services.PostServices;
public class ajouternouveaucommentaire {
    @FXML
    private TextArea contenuTA;
    PostServices ps = new PostServices();
    private int idpost;
    CommentServices cs =new CommentServices();
    @FXML
    void ajouterCommentaire(ActionEvent event) {
        // Création d'un nouvel objet Comment avec les données du formulaire
        Comment nouveauCommentaire = new Comment();
        nouveauCommentaire.setContenu(contenuTA.getText());
        CommentServices commentService = new CommentServices();
        nouveauCommentaire.setIdpost(idpost); // Définition de l'ID du post associé
        commentService.add(nouveauCommentaire, nouveauCommentaire.getIdpost());
        // Fermeture de la fenêtre après l'ajout du commentaire
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
    @FXML
    void naviguer(ActionEvent event){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/commentaireforum.fxml"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
public void initialize (Post post  ) {
        this.idpost=post.getId();
} }
