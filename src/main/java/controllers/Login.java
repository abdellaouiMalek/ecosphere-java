package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.controllers.Home;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;


public class Login {
    @FXML
    private Button createacc;

    @FXML
    private Label ctrlEmail;

    @FXML
    private Label ctrlPwd;

    @FXML
    private Hyperlink hlforgotpass;

    @FXML
    private Button login_but;

    @FXML
    private Label pass_label;

    @FXML
    private TextField tfmail;

    @FXML
    private PasswordField tfpass;

    UserService us = new UserService();

    @FXML
    void sendToregister(ActionEvent event) {
        try {
            // Load Register.fxml using ClassLoader
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Register.fxml"));
            Parent root = loader.load();

            // Replace the current scene with the new scene loaded from Register.fxml
            createacc.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println("Error loading Register.fxml: " + ex.getMessage());
        }
    }

    @FXML
    void clickLogin(ActionEvent event) {


        // Validate input fields
        if (tfmail.getText().isEmpty()) {
            ctrlEmail.setText("email est incorrect");

        }
        if (tfpass.getText().isEmpty()) {
            ctrlPwd.setText("mot de passe est incorrect");
        }


        try {
            // Attempt to log in using UserService
            User loggedInUser = us.login(tfmail.getText(), tfpass.getText());
            if (loggedInUser != null) {
                System.out.println("Login successful!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
                Parent root = loader.load();

                Home homeController = loader.getController();
                homeController.setLoggedInUser(loggedInUser); // Pass the logged-in user to HomeController
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Display login failure message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password.");
                alert.showAndWait();
                System.out.println("Login failed: Invalid credentials");
            }
        } catch (Exception ex) {
            // Display error message in case of exception
            System.out.println("Error during login: " + ex.getMessage());
        }


    }
}
