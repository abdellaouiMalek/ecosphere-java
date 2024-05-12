package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.*;
import tn.esprit.models.Waitlist;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;
import tn.esprit.services.WaitlistService;
import tn.esprit.util.SmsService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class AvailableCarpooling {
    @FXML
    private Button cancelButton;

    @FXML
    private Label date;

    @FXML
    private Label departure;

    @FXML
    private Label destination;

    @FXML
    private ImageView icon;

    @FXML
    private Label price;

    @FXML
    private Button reservationButton;

    @FXML
    private Label seat;

    @FXML
    private ImageView userPic;

    @FXML
    private Label username;

    private final CarpoolingService carpoolingService = new CarpoolingService();
    private int carpoolingId;

    public void getID(int carpoolingId) {
        this.carpoolingId = carpoolingId;
        System.out.println("f get id mta details " + carpoolingId);
        getDetails();
    }

    private void getDetails() {
        try {
            System.out.println(carpoolingId);
            Carpooling carpooling = carpoolingService.getById(carpoolingId);
            System.out.println(carpooling);
            int userId = carpooling.getUserID();
            System.out.println(userId);
            UserService userService = new UserService();
            User user = userService.getById(userId);
            System.out.println(user);

            if (carpooling != null) {
                destination.setText(carpooling.getDestination());
                departure.setText(carpooling.getDeparture());
                String priceText = String.valueOf(carpooling.getPrice());
                price.setText(priceText + " DT");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String departureDateString = dateFormat.format(carpooling.getDepartureDate());
                date.setText(departureDateString);
                username.setText(user.getFirst_name()+" "+user.getLast_name());
                seat.setText(String.valueOf(carpooling.getSeat()));
                String pictureUrl = user.getPicture();
                if (pictureUrl != null && !pictureUrl.isEmpty()) {
                    try {
                        // Check if the URL starts with a recognized protocol
                        if (pictureUrl.startsWith("http")) {
                            // Load the image from the URL
                            Image image = new Image(pictureUrl);
                            userPic.setImage(image);
                        } else {
                            // If not, assume it's a local file path
                            File file = new File(pictureUrl);
                            Image image = new Image(file.toURI().toString());
                            userPic.setImage(image);
                        }

                        // Set the dimensions of the ImageView
                        userPic.setFitWidth(50); // Set your desired width
                        userPic.setFitHeight(50); // Set your desired height

                        // Remove the preserveRatio attribute to avoid stretching the image
                        userPic.setPreserveRatio(false);

                        // Clip the ImageView to a Circle shape
                        Circle clip = new Circle();
                        clip.setCenterX(userPic.getFitWidth() / 2);
                        clip.setCenterY(userPic.getFitHeight() / 2);
                        clip.setRadius(userPic.getFitWidth() / 2);
                        userPic.setClip(clip);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle image loading error
                    }
                }

            } else {
                destination.setText("Carpooling not found" + carpoolingId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            destination.setText("Error fetching carpooling: " + e.getMessage());
        }
    }
    @FXML
    public void navigationBack(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/myReservations.fxml"));
        Parent root = loader.load();

        MyReservations controller = loader.getController();

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    private boolean checkLoginStatus(User user) {
        return user != null && user.getId() != 0;
    }
    @FXML
    public void reservation(ActionEvent actionEvent) throws IOException {
        User loggedUser = SessionUser.getLoggedUser();

        if (checkLoginStatus(loggedUser)) {
            int userId = loggedUser.getId();
            ReservationService reservationService = new ReservationService();
            UserService userService = new UserService();
            WaitlistService waitlistService = new WaitlistService();
            try {
                int currentReservations = reservationService.getReservationCountForCarpooling(carpoolingId);
                int carpoolingSeats = carpoolingService.getCarpoolingSeats(carpoolingId);

                if (currentReservations >= carpoolingSeats) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Waitlist Confirmation");
                    alert.setHeaderText("Maximum reservations reached");
                    alert.setContentText("Would you like to be put on the waitlist?");
                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(yesButton, noButton);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == yesButton) {
                        tn.esprit.models.Waitlist waitlist = new Waitlist();
                        waitlist.setUserID(userId);
                        waitlist.setCarpoolingID(carpoolingId);
                        waitlistService.add(waitlist);

                        Alert waitlistAlert = new Alert(Alert.AlertType.INFORMATION);
                        waitlistAlert.setTitle("Waitlist Confirmation");
                        waitlistAlert.setHeaderText(null);
                        waitlistAlert.setContentText("You have been added to the waitlist.");
                        waitlistAlert.showAndWait();
                        userService.deleteNotification(userId, carpoolingId);
                        return;
                    } else {
                        return;
                    }
                }
                Reservation reservation = new Reservation(userId, carpoolingId);
                reservationService.add(reservation);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Reservation successful!");
                alert.showAndWait();

                String userPhoneNumber = userService.getUserPhoneNumber(userId);
                String message = "Your reservation was successful!";
                SmsService.sendReservationConfirmationSMS(userPhoneNumber, message);
                userService.deleteNotification(userId, carpoolingId);
                userService.deleteWaitlist(carpoolingId,userId);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error creating reservation: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please log in to make reservation.");

            ButtonType loginButton = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(loginButton, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == loginButton) {
                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent loginRoot = loginLoader.load();

                Scene currentScene = ((Node) actionEvent.getSource()).getScene();
                currentScene.setRoot(loginRoot);
            }
        }
    }


    @FXML
    public void cancel(ActionEvent actionEvent) {
        UserService userService = new UserService();
        User loggedUser = SessionUser.getLoggedUser();
        int userId = loggedUser.getId();
        userService.deleteNotification(userId, carpoolingId);
        userService.deleteWaitlist(carpoolingId, userId);

        // Display an alert with an "Okay" button
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation Canceled");
        alert.setHeaderText(null);
        alert.setContentText("Your reservation has been canceled.");
        ButtonType okayButton = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okayButton);
        alert.showAndWait();

        navigateToSearchInterface();
    }

    private void navigateToSearchInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
