package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.models.Post;
import tn.esprit.services.PostServices;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class AjouterBlog implements Initializable {
    ObservableList<Post> PostList = FXCollections.observableArrayList();
    PostServices post= new PostServices();

    @FXML
    private TableView<Post> Post_tableView;
    @FXML
    private Button BlogPost_btn;
    @FXML
    private ImageView Post_imageView;

    @FXML
    private AnchorPane add_POST;

    @FXML
    private TextField tf_auteur;

    @FXML
    private TextArea ta_content;

    @FXML
    private Button menu_btn;

   @FXML
    private AnchorPane post_ImageView;

    @FXML
    private AnchorPane post_form;

    @FXML
    private TextField tf_title;

    @FXML
    private Label username;
    public void initialize(URL url, ResourceBundle rb) {
        PostList = FXCollections.observableArrayList();
        Post_tableView.setItems(PostList);
        TableColumn<Post, String> post_col_auteur = new TableColumn<>("Auteur") ;
        TableColumn<Post, String> post_col_title = new TableColumn<>("Title") ;
        TableColumn<Post,String> post_col_contenu = new TableColumn<>("Contenu");
        post_col_auteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        post_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        post_col_contenu.setCellFactory(col -> new ContentCell());
        post_col_contenu.setCellValueFactory(new PropertyValueFactory<>("content"));
        // Add columns to the TableView
        Post_tableView.getColumns().addAll( post_col_auteur,post_col_title, post_col_contenu);
        // Set preferred widths for columns
        refreshTable();
        post_col_auteur.setPrefWidth(100);
        post_col_title.setPrefWidth(100);
        post_col_contenu.setPrefWidth(100);
    }
    @FXML
    void addpost() {
            try {
                String Auteur =tf_auteur.getText();
                String Title = tf_title.getText();
                String Content = ta_content.getText();
                if (Title.isEmpty() || Content.isEmpty() || Auteur.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields ");
                    return;
                }
                Post newpost = new Post();
                newpost.setAuteur(Auteur);
                newpost.setTitle(Title);
                newpost.setContent(Content);
                PostServices newpostService = new PostServices();
                newpostService.add(newpost);
                // Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
                int newId = newpost.getId();
                PostList.add(newpost);
                newpost.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté
                refreshTable();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Publication added successfully.");
                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
            }
        }
    private void refreshTable() {
        PostServices postServices = new PostServices();
        List<Post> postList = postServices.getAll();
        Post_tableView.setItems(FXCollections.observableArrayList(postList));  }
    private void showAlert(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    private void clearFields() {
        tf_title.clear();
        ta_content.clear();
        tf_auteur.clear();
    }
    @FXML
    void updatepost() {
            Post selectedPost = Post_tableView.getSelectionModel().getSelectedItem();
            if (selectedPost != null) {
                try {
                    String Auteur =tf_auteur.getText();
                    String Title = tf_title.getText();
                    String Content = ta_content.getText();

                    if (Title.isEmpty() || Auteur.isEmpty() || Content.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                        return;
                    }
                    selectedPost.setAuteur(Auteur);
                    selectedPost.setTitle(Title);
                    selectedPost.setContent(Content);

                    PostServices post= new PostServices();
                    post.update(selectedPost);
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

    public void deletepost() { Post selectedPost = Post_tableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            try {
                PostServices postServices = new PostServices();
                postServices.delete(selectedPost);
                PostList.remove(selectedPost); // Supprimez également la publication de votre liste observable
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

    public class ContentCell extends TableCell<Post, String> {
        private final TextArea textArea;

        public ContentCell() {
            textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            setGraphic(textArea);
        }
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                textArea.clear(); // Efface le contenu du TextArea si la cellule est vide
            } else {
                textArea.setText(item); // Définit le texte du TextArea avec le contenu
            }
        }


    }

    }


