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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Category;
import tn.esprit.models.Event;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CategoryService;
import tn.esprit.services.EventService;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AddEvent implements Initializable {

    @FXML
    private TextField titleid;

    @FXML
    private Button addBtn;

    @FXML
    private TextField addressid;

    @FXML
    private ChoiceBox<String> categoryid;

    @FXML
    private TextField locationid;

    @FXML
    private TextArea descid;

    @FXML
    private TextField timeid;

    @FXML
    private Button eventListBtn;

    @FXML
    private ImageView imagePreview;

    @FXML
    private TextField objid;

    @FXML
    private DatePicker startDateDp;

    @FXML
    private Button uploadImgBtn;

    EventService es = new EventService();
    private String imagePath;

    User loggedUser = SessionUser.getLoggedUser();
    int loggedId = loggedUser.getId();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create an instance of CategoryService (replace CategoryService with your actual service class)
        CategoryService categoryService = new CategoryService();

        try {
            // Retrieve a list of category names from the database
            List<Category> categoryNames = categoryService.getAllCategories();

            // Check if categoryNames is not empty
            if (!categoryNames.isEmpty()) {
                // Clear any existing items in the ChoiceBox
                categoryid.getItems().clear();

                // Add each category name as a separate item to the ChoiceBox
                for (Category category : categoryNames) {
                    categoryid.getItems().add(category.getName());
                }

                // Set the default value for the ChoiceBox (optional)
                categoryid.setValue(categoryNames.get(0).getName()); // Set the first category as default
            } else {
                // Handle case where no categories were retrieved
                System.out.println("No categories found in the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void onUploadButtonClick(ActionEvent event) {
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

    @FXML
    public void addEvent(ActionEvent event) {
        // Validate input fields
        if (titleid.getText().isEmpty() || categoryid.getValue() == null || descid.getText().isEmpty() ||
                startDateDp.getValue() == null || objid.getText().isEmpty() ||
                addressid.getText().isEmpty() || timeid.getText().isEmpty()) {

            // Show an alert if any required field is empty
            Alert warn = new Alert(Alert.AlertType.INFORMATION);
            warn.setTitle("Invalid Input");
            warn.setContentText("Please enter values in all fields.");
            warn.setHeaderText(null);
            warn.showAndWait();

        } else {
            try {
                // Create an Event object and set its properties
                Event e = new Event();
                e.setEventName(titleid.getText());
                e.setDescription(descid.getText());
                e.setLocation(locationid.getText());
                e.setObjective(objid.getText());
                e.setDate(java.sql.Date.valueOf(startDateDp.getValue())); // Convert LocalDate to Date
                e.setAddress(addressid.getText());
                e.setImage(imagePath);

                // Validate and set the time field
                String timeInput = timeid.getText().trim();
                if (isValidTimeFormat(timeInput)) {
                    e.setTime(java.sql.Time.valueOf(timeInput)); // Convert time string to Time object

                    // Retrieve the selected category name from the choice box
                    String selectedCategoryName = categoryid.getValue();

                    // Retrieve the corresponding Category object from the database
                    CategoryService categoryService = new CategoryService();
                    List<Category> categories = categoryService.getAllCategories();

                    Category selectedCategory = categories.stream()
                            .filter(category -> category.getName().equals(selectedCategoryName))
                            .findFirst()
                            .orElse(null);

                    if (selectedCategory != null) {
                        // Set the Category object into the Event
                        e.setCategory(selectedCategory);

                        e.setUserId(loggedId);

                        // Call the EventService to add the event to the database
                        EventService es = new EventService();
                        es.add(e);



                        // Show success message
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setContentText("Event added successfully!");
                        success.setHeaderText(null);
                        success.showAndWait();
                    } else {
                        // Handle case where selected category is not found
                        showAlert("Error", "Selected category not found.");
                    }
                } else {
                    // Handle invalid time format
                    showAlert("Error", "Invalid time format. Please use HH:mm:ss format.");
                }

            } catch (NumberFormatException | SQLException ex) {
                // Handle number format exception or SQL exception
                showAlert("Error", "Error occurred while adding the event: " + ex.getMessage());
            }
        }
    }

    // Utility method to validate time format
    private boolean isValidTimeFormat(String timeInput) {
        try {
            java.sql.Time.valueOf(timeInput);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Utility method to display an alert dialog
    private void showAlert(String title, String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setContentText(message);
        error.setHeaderText(null);
        error.showAndWait();
    }

    // Helper method to copy file
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

    @FXML
    private void goToAllEvents(ActionEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AllEvents.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) eventListBtn.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error loading AllEvents.fxml: " + ex.getMessage());
        }
    }




}




