package tn.esprit.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Comment;
import tn.esprit.services.BadWordServices;
import tn.esprit.services.CommentServices;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
public class NewComment implements Initializable{
    ObservableList<Comment> CommentList = FXCollections.observableArrayList();
    CommentServices comment = new CommentServices();
    @FXML
    private TableView<Comment> Comment_tableView;
    @FXML
    private TextArea ta_comment;
    private void clearFields() {
    }
    private void showAlert(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommentList = FXCollections.observableArrayList();
        Comment_tableView.setItems(CommentList);
        TableColumn<Comment, String> comment_col_contenu = new TableColumn<>("contenu") ;
        TableColumn<Comment, Date> comment_col_date= new TableColumn<>("PublicationDate");
        comment_col_contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        comment_col_date.setCellValueFactory(new PropertyValueFactory<>("PublicationDate"));
        Comment_tableView.getColumns().addAll( comment_col_contenu ,comment_col_date);
        refreshTable();
    }
    private void refreshTable() {
        CommentServices commentServices = new CommentServices();
        List<Comment> commentList = commentServices.getAll();
        Comment_tableView.setItems(FXCollections.observableArrayList(commentList));  }
    public void AddComment(ActionEvent event) {
        try {
            String Contenu= ta_comment.getText();
            if (Contenu.isEmpty() ) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill the content ");
                return;
            }
             boolean resultat = BadWordServices.containsBadWords(Contenu);
            if (resultat == false) {
            Comment newcomment = new Comment();
            newcomment.setContenu(Contenu);
            CommentServices newcommentService = new CommentServices();
            newcommentService.add(newcomment, newcomment.getId());
            // Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
            int newId = newcomment.getId();
            CommentList.add(newcomment);
            newcomment.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté
            refreshTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Comment added successfully.");
            clearFields(); }
            else
            {showAlert(Alert.AlertType.WARNING, "Warning", "The content contains forbidden words.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
        }
    }
    public void UpdateComment(ActionEvent event) {
        Comment selectedComment = Comment_tableView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            try {
                String Content = ta_comment.getText();
                if ( Content.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please fill the content");
                    return;
                }
                selectedComment.setContenu(Content);
                CommentServices commentServices= new CommentServices();
                commentServices.update(selectedComment);
                refreshTable();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Post updated successfully.");
                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the post.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a post to update.");
        }
    }
    public void DComment(ActionEvent event) {
        Comment selectedComment = Comment_tableView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            try {
                CommentServices postComment = new CommentServices();
                postComment.delete(selectedComment);
                CommentList.remove(selectedComment); // Supprimez également la publication de votre liste observable
                refreshTable();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Post deleted successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the post.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a post to delete.");
        }
    }
}

