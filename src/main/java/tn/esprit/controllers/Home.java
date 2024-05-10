package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import tn.esprit.models.User;
import javafx.scene.Node;


import java.io.IOException;

public class Home {

    @FXML
    private Button Loginbtn;

    @FXML
    private Label username;

    @FXML
    void Loginbtn(ActionEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) Loginbtn.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error loading Login.fxml: " + ex.getMessage());
        }



    }

    public void setLoggedInUser(User user) {
        if (user != null) {
            // Display the logged-in user's name
            String fullName = user.getFirst_name() + " " + user.getLast_name();
            username.setText(fullName);
        }
    }

    public void BrowseEvents(MouseEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading AllEvents.fxml: " + ex.getMessage());
        }
    }

    public void Carpooling(MouseEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading .fxml: " + ex.getMessage());
        }
    }

    public void Blog(MouseEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Listdespostes.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading .fxml: " + ex.getMessage());
        }
    }

    public void sharinghub(MouseEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyTest.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading .fxml: " + ex.getMessage());
        }
    }
}
