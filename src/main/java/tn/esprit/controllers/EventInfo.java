package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tn.esprit.models.Event;
import tn.esprit.models.EventRating;
import tn.esprit.models.EventRegistrations;
import tn.esprit.services.EventService;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.time.LocalTime;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;

public class EventInfo implements Initializable {

    @FXML
    private Text AddressText;

    @FXML
    private Text LocationText;

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

    @FXML
    private Button intrestedid;

    @FXML
    private Rating rating;

    @FXML
    private Button Submitratingid;

    @FXML
    private ProgressBar progressBar5;
    @FXML
    private Label ratingCount5;

    @FXML
    private ProgressBar progressBar4;
    @FXML
    private Label ratingCount4;

    @FXML
    private ProgressBar progressBar3;
    @FXML
    private Label ratingCount3;

    @FXML
    private ProgressBar progressBar2;
    @FXML
    private Label ratingCount2;

    @FXML
    private ProgressBar progressBar1;
    @FXML
    private Label ratingCount1;


    EventService es = new EventService();
    private Event eventInfoStore;
    private boolean isInterested = false;
    private int eventId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sPane.setFitToWidth(true);

        updateInterestButton();
        initializeRatings(eventId);

    }

    // Method to populate event information
    void sendEvent(Event eventInfo) {
        eventInfoStore = eventInfo;
        eventId = eventInfo.getId();
        titleText.setText(eventInfo.getEventName());
        catText.setText(eventInfo.getCategory().getName());
        startDateText.setText(String.valueOf(eventInfo.getDate()));
        TimeText.setText(String.valueOf(eventInfo.getTime()));
        LocationText.setText(eventInfo.getLocation());
        AddressText.setText((eventInfo.getAddress()));
        descText.setText(eventInfo.getDescription());

        File imageFile = new File(eventInfo.getImage());
        Image image = new Image(imageFile.toURI().toString());
        afficheIv.setImage(image);

        // Update interest status based on the current user
        try {
            // Assuming userId = 1 for the current user
            int userId = 1;
            isInterested = es.isInterested(eventInfo.getId(), userId);
        } catch (SQLException e) {
            System.out.println("Error checking interest in the event: " + e.getMessage());
        }

        // Update the button text based on the interest status
        updateInterestButton();

        // Initialize ratings for this event
        initializeRatings(eventId);
    }

    private void updateInterestButton() {
        if (isInterested) {
            intrestedid.setText("Cancel interest");
        } else {
            intrestedid.setText("☆I'm interested");
        }
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
        }
    }

    @FXML
    void submitRating(ActionEvent event) {
        if (eventInfoStore != null) {
            // Retrieve the selected rating from the star rating component
            int ratingValue = (int) rating.getRating();

            try {
                EventRating eventRating = new EventRating(eventInfoStore.getId(), ratingValue);
                EventService eventService = new EventService();
                eventService.addEventRating(eventRating);

                reloadCurrentScene();

            } catch (SQLException e) {
                // Handle database errors
                System.out.println("Error submitting rating: " + e.getMessage());
            }
        }
    }

    private void reloadCurrentScene() {
        try {
            // Load the same FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventInfo.fxml"));
            Parent root = loader.load();

            EventInfo controller = loader.getController();
            controller.sendEvent(eventInfoStore);

            // Get the current stage and set the scene with the new root
            Stage stage = (Stage) Submitratingid.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error reloading scene: " + ex.getMessage());
        }
    }

    private void initializeRatings(int eventId) {
        try {
            // Retrieve rating counts for the specified event ID
            Map<Integer, Integer> ratingMap = es.getRatingCounts(eventId);

            // Update progress bars and labels for each rating category
            updateProgressBar(progressBar5, ratingCount5, ratingMap.getOrDefault(5, 0));
            updateProgressBar(progressBar4, ratingCount4, ratingMap.getOrDefault(4, 0));
            updateProgressBar(progressBar3, ratingCount3, ratingMap.getOrDefault(3, 0));
            updateProgressBar(progressBar2, ratingCount2, ratingMap.getOrDefault(2, 0));
            updateProgressBar(progressBar1, ratingCount1, ratingMap.getOrDefault(1, 0));

        } catch (SQLException e) {
            System.out.println("Error fetching ratings: " + e.getMessage());
        }
    }

    private void updateProgressBar(ProgressBar progressBar, Label ratingCountLabel, int count) {
        // Assuming the maximum possible count (total ratings) for the progress bar
        int maxTotalRatings = 10;

        // Calculate progress value based on the count and total ratings
        double progress = (double) count / maxTotalRatings;

        // Update the progress bar with the calculated progress value
        progressBar.setProgress(progress);

        // Update the label to display the count of ratings
        ratingCountLabel.setText(String.valueOf(count));
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
        }
    }

    // Event handler for registering interest in the event
    @FXML
    void intrested(ActionEvent event) {
        try {
            int userId = 1;

            if (isInterested) {
                // Cancel interest
                es.cancelInterest(eventInfoStore.getId(), userId);
                isInterested = false;
                showCancelInterestPopup(); // Display cancellation success popup
            } else {
                // Register interest
                Date currentDate = new Date();
                Time currentTime = Time.valueOf(LocalDateTime.now().toLocalTime());
                EventRegistrations registration = new EventRegistrations(currentDate, currentTime, "Interested");
                registration.setId(eventInfoStore.getId());
                es.saveRegistration(registration);
                isInterested = true;
                showRegisterInterestPopup(); // Display registration success popup
            }

            // Update the button text based on the new interest status
            updateInterestButton();

        } catch (SQLException e) {
            System.out.println("Error updating interest in the event: " + e.getMessage());
        }
    }

    private void showRegisterInterestPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Interest Registered");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully registered interest in this event!");
        alert.showAndWait();
    }

    private void showCancelInterestPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Interest Canceled");
        alert.setHeaderText(null);
        alert.setContentText("You have canceled your interest in this event.");
        alert.showAndWait();
    }

}
