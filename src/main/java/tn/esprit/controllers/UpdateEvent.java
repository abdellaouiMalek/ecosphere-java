package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class UpdateEvent {

    @FXML
    private Button EvReturnBtn;

    @FXML
    private TextField addressid;

    @FXML
    private ChoiceBox<?> categoryid;

    @FXML
    private TextArea descid;

    @FXML
    private ImageView imagePreview;

    @FXML
    private TextField locationid;

    @FXML
    private TextField objid;

    @FXML
    private DatePicker startDateDp;

    @FXML
    private TextField timeid;

    @FXML
    private TextField titleid;

    @FXML
    private Button updateBtn;

    @FXML
    private Button uploadImgBtn;

    private Event selectedEvent;
    private EventService eventService;
    private String imagePath;





    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;

    }



    @FXML
    void Updateevent(ActionEvent event) {
        if (selectedEvent == null) {
            showAlert("Error", "No event selected for update.");
            return;
        }

        // Validate and update the event details
        String title = titleid.getText().trim();
        String address = addressid.getText().trim();
        LocalDate startDate = startDateDp.getValue();
        String location = locationid.getText().trim();
        String objective = objid.getText().trim();
        String timeStr = timeid.getText().trim();

        if (title.isEmpty() || address.isEmpty() || startDate == null || location.isEmpty() || objective.isEmpty() || timeStr.isEmpty()) {
            showAlert("Error", "Please fill out all fields.");
            return;
        }

        try {
            // Convert time string to java.sql.Time
            java.sql.Time time = java.sql.Time.valueOf(timeStr);

            // Update the event object with new details
            selectedEvent.setEventName(title);
            selectedEvent.setAddress(address);
            selectedEvent.setDate(java.sql.Date.valueOf(startDate));
            selectedEvent.setLocation(location);
            selectedEvent.setObjective(objective);
            selectedEvent.setTime(time);
            selectedEvent.setImage(imagePath);

            // Call the EventService to update the event
            eventService = new EventService();
            eventService.update(selectedEvent);

            // Show success message
            showAlert("Success", "Event updated successfully!");




        } catch (IllegalArgumentException e) {
            showAlert("Error", "Invalid time format. Please use HH:mm:ss format.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void ReturnEv(ActionEvent actionEvent) {
        try {
            // Load the EventInfo.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventInfo.fxml"));
            Parent root = loader.load();

            // Access the controller of the EventInfo view
            EventInfo controller = loader.getController();

            // Pass the updated event back to EventInfo
            controller.sendEvent(selectedEvent);

            // Set up the stage to display the EventInfo scene
            Stage stage = (Stage) EvReturnBtn.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error loading EventInfo.fxml: " + ex.getMessage());
        }
    }


    public void onUploadButtonClick(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageName = selectedFile.getName();
            imagePath = "src/assets/images/" + imageName;
            File imageFile = new File(imagePath);

            try {
                // Create directories if they don't exist
                imageFile.getParentFile().mkdirs();

                // Copy selected file to destination directory
                copyFile(selectedFile, imageFile);

                Image image = new Image(imageFile.toURI().toString());
                if (image.isError()) {
                    System.out.println("Error loading image: " + image.getException().getMessage());
                    // Handle error (e.g., display an error message)
                } else {
                    imagePreview.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());

            }

        }
    }
    private void copyFile(File sourceFile, File destFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

    }
}
