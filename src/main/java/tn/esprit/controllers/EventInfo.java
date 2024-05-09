package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tn.esprit.models.*;
import tn.esprit.services.EventService;
import javafx.scene.control.Alert.AlertType;
import tn.esprit.services.UserService;
import tn.esprit.util.EmailService;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    private Button nbinterested;

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

    @FXML
    private Label avgrating;

    @FXML
    private TableView<Event> eventTable;

    private Event selectedEvent;



    EventService es = new EventService();
    UserService us = new UserService();
    private Event eventInfoStore;
    private boolean isInterested = false;
    private int eventId;
    private EmailService emailService = new EmailService();

    User loggedUser = SessionUser.getLoggedUser();
    int loggedId = loggedUser.getId();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sPane.setFitToWidth(true);
        updateInterestButton();
        initializeRatings(eventId);

    }

    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;

    }



    // Method to populate event information
    void sendEvent(Event eventInfo) {
        eventInfoStore = eventInfo;
        System.out.println(eventInfo);
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

        int userId = eventInfo.getUserId();
        System.out.println(userId);
        User user = us.getById(userId);

        if (user != null) {
            String userName = user.getFirst_name() + " " + user.getLast_name();
            hostNameText.setText(userName);
        } else {
            hostNameText.setText("Unknown User");
        }

        try {
            isInterested = es.isInterested(eventInfo.getId(), userId);
        } catch (SQLException e) {
            System.out.println("Error checking interest in the event: " + e.getMessage());
        }

        updateInterestButton();

        initializeRatings(eventId);

        calculateAndDisplayAverageRating(eventInfo.getId());

        setSelectedEvent(eventInfo);

        updateInterestedCount();
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
            User loggedUser = SessionUser.getLoggedUser();
            if (loggedUser != null && loggedUser.getId() == eventInfoStore.getUserId()) {
                // Logged-in user is the owner, proceed with deletion
                es.delete(eventInfoStore);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) deleteBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                // User is not authorized to delete this event, show alert
                showAlert("Authorization Error", "Unauthorized Action",
                        "You are not authorized to delete this event.");
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Error deleting event: " + ex.getMessage());
        }
    }

    @FXML
    void updateEvent(ActionEvent event) {
        try {
            // Check if the logged-in user is the owner of the event
            User loggedUser = SessionUser.getLoggedUser();
            if (loggedUser != null && loggedUser.getId() == eventInfoStore.getUserId()) {
                // Logged-in user is the owner, proceed to update
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
                Parent root = loader.load();

                UpdateEvent controller = loader.getController();
                controller.setSelectedEvent(eventInfoStore);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // User is not authorized to update this event, show alert
                showAlert("Authorization Error", "Unauthorized Action",
                        "You are not authorized to update this event.");
            }
        } catch (IOException ex) {
            System.out.println("Error loading UpdateEvent.fxml: " + ex.getMessage());
        }
    }
    // Helper method to show an alert dialog
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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

    private void calculateAndDisplayAverageRating(int eventId) {
        try {
            // Retrieve average rating for the specified event ID using EventService
            double averageRating = es.calculateAverageRating(eventId);

            // Display the average rating in the avgrating label
            avgrating.setText(String.format("%.1f", averageRating));

        } catch (SQLException e) {
            System.out.println("Error calculating average rating: " + e.getMessage());
        }
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
            int userId = loggedUser.getId();
            System.out.println(userId);
            String recipientEmail = "wardiaziz2507@gmail.com";
            String subject = "Interest in Event";
            String body = "Thank you for your interest in our event.";

            if (isInterested) {
                // Cancel interest
                es.cancelInterest(eventInfoStore.getId(), userId);
                isInterested = false;
                showCancelInterestPopup();
            } else {
                // Register interest
                Date currentDate = new Date();
                Time currentTime = Time.valueOf(LocalDateTime.now().toLocalTime());
                EventRegistrations registration = new EventRegistrations(currentDate, currentTime, "Interested");
                registration.setId(eventInfoStore.getId());
                registration.setUserId(loggedId);
                es.saveRegistration(registration);
                isInterested = true;

                showRegisterInterestPopup(); // Display registration success popup

                // Compose email body with event details
                body = "<h2>Thank you for your interest in our event!</h2>"
                        + "<p><strong>Event Details:</strong></p>"
                        + "<ul>"
                        + "<li><strong>Name:</strong> " + eventInfoStore.getEventName() + "</li>"
                        + "<li><strong>Date:</strong> " + eventInfoStore.getDate() + "</li>"
                        + "<li><strong>Description:</strong> " + eventInfoStore.getDescription() + "</li>"
                        + "</ul>"
                        + "<p><img src='C:/Users/aziz/IdeaProjects/ecosphere-java/src/main/resources/logo.png'></p>";

                // Send email
                emailService.sendEmail(recipientEmail, subject, body);
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

    // Method to update the interested count display
    private void updateInterestedCount() {
        if (selectedEvent != null) {
            int eventId = selectedEvent.getId(); // Assuming you have a method to get the event ID

            try {
                EventService eventService = new EventService(); // Create a new instance of EventService
                int interestedCount = eventService.getInterestedUserCount(eventId); // Get the interested user count

                // Update the UI element with the interested count
                nbinterested.setText(String.valueOf(interestedCount));
            } catch (SQLException e) {
                System.out.println("Error retrieving interested count: " + e.getMessage());
            }
        }
    }


}
