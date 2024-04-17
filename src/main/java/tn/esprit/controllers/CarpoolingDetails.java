package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CarpoolingDetails  {

    @FXML
    private Label destination;
    private final CarpoolingService carpoolingService = new CarpoolingService();
    private int carpoolingId;

    public void getID(int carpoolingId) {
        this.carpoolingId = carpoolingId;
        System.out.println("f init data"+ carpoolingId);
        getDetails();
    }

    private void getDetails() {
        try {
            System.out.println(carpoolingId);
            Carpooling carpooling = carpoolingService.getById(carpoolingId);

            if (carpooling != null) {
                destination.setText(carpooling.getDestination());
            } else {
                destination.setText("Carpooling not found" + carpoolingId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            destination.setText("Error fetching carpooling: " + e.getMessage());
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            carpoolingService.delete(new Carpooling(carpoolingId));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Carpooling deleted successfully!" );
            alert.showAndWait();

            Stage stage = (Stage) destination.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error deleting carpooling: " + e.getMessage());
            alert.showAndWait();
        }
    }



    // Method to navigate to the "allCarpoolings" interface
    private void navigateToAllCarpoolingsInterface() {
        try {
            // Load the FXML file for the "allCarpoolings" interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/allCarpoolings.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the current window
            Stage stage = (Stage) destination.getScene().getWindow();

            // Set the scene and show the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle any errors that occur during navigation
            e.printStackTrace();
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error navigating to allCarpoolings interface: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
