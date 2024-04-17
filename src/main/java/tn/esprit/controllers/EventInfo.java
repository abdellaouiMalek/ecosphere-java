package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EventInfo implements Initializable {

    @FXML
    private Text AddressText;

    @FXML
    private Text TimeText;

    @FXML
    private ImageView afficheIv;

    @FXML
    private Button backBtn;

    @FXML
    private Text catText;

    @FXML
    private AnchorPane commentPane;

    @FXML
    private Button deleteBtn;

    @FXML
    private Text descText;

    @FXML
    private Text hostNameText;

    @FXML
    private ScrollPane sPane;

    @FXML
    private Text startDateText;

    @FXML
    private Text titleText;

    @FXML
    private Button updateBtn;

    EventService es = new EventService();
    private Event eventInfoStore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sPane.setFitToWidth(true);
    }

    // Method to populate event information
    void sendEvent(Event eventInfo) {
        eventInfoStore = eventInfo;
        titleText.setText(eventInfo.getEventName());
        catText.setText(eventInfo.getCategory().getName());
        startDateText.setText(String.valueOf(eventInfo.getDate()));
        descText.setText(eventInfo.getDescription());

        File imageFile = new File(eventInfo.getImage());
        Image image = new Image(imageFile.toURI().toString());
        afficheIv.setImage(image);

    }

    // Event handler for deleting an event
    @FXML
    void deleteEvent(ActionEvent event) {
        try {
            // Call the EventService to delete the eventInfoStore
            es.delete(eventInfoStore);

            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the scene with the new root
            Stage stage = (Stage) deleteBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (SQLException | IOException ex) {
            System.out.println("Error deleting event: " + ex.getMessage());
            // Handle the exception appropriately (e.g., show an error message)
        }
    }

    // Event handler for updating an event
    @FXML
    void updateEvent(ActionEvent event) {

}

    // Event handler for navigating back to the events list
    @FXML
    void goBackToList(ActionEvent event) {
        try {
            // Load the previous scene or FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error loading PreviousScene.fxml: " + ex.getMessage());
        }    }
}
