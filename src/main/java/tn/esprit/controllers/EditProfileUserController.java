package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import tn.esprit.models.SessionUser;
import tn.esprit.services.UserService;

import java.io.IOException;

public class EditProfileUserController {

    @FXML
    private Button ToHomePage;

    @FXML
    private TextField adrField;

    @FXML
    private Circle big_circle;

    @FXML
    private TextField cinField;

    @FXML
    private TextField emailField;

    @FXML
    private Text err1;

    @FXML
    private Text err2;

    @FXML
    private Text err3;

    @FXML
    private Text err4;

    @FXML
    private ComboBox<?> genreField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telField;


    @FXML
    void browseImage(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        nomField.setText(null);
        prenomField.setText(null);
        emailField.setText(null);

        telField.setText(null);
    }

    @FXML
    void navigateToHomePage(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Profile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            emailField.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Replace the current scene with the new scene loaded from Register.fxml
    }

    @FXML
    void navigateToTransactions(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {


        UserService us = new UserService();
        us.update(SessionUser.loggedUser);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("update  success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    @FXML
    void initialize() {

        nomField.setText(SessionUser.loggedUser.getFirst_name());
        prenomField.setText(SessionUser.loggedUser.getLast_name());
        emailField.setText(SessionUser.loggedUser.getEmail());

        telField.setText(SessionUser.loggedUser.getPhone_number());


    }

}
