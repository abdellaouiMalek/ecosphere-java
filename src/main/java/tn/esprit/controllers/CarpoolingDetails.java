package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.Waitlist;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;
import tn.esprit.services.WaitlistService;
import tn.esprit.util.SmsService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class CarpoolingDetails {

    @FXML
    private Label date;

    @FXML
    private Label departure;

    @FXML
    private Label destination;

    @FXML
    private Label price;
    @FXML
    private ImageView icon;

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

            if (carpooling != null) {
                destination.setText(carpooling.getDestination());
                departure.setText(carpooling.getDeparture());
                String priceText = String.valueOf(carpooling.getPrice());
                price.setText(priceText + " DT");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String departureDateString = dateFormat.format(carpooling.getDepartureDate());
                date.setText(departureDateString);
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
            alert.setContentText("Carpooling deleted successfully!");
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

    @FXML
    void reservation(ActionEvent event) {
        int userId = 1;
        int maxReservationsAllowed = 3;
        ReservationService reservationService = new ReservationService();
        UserService userService = new UserService();
        WaitlistService waitlistService = new WaitlistService();

        try {
            int currentReservations = reservationService.getReservationCountForCarpooling(carpoolingId);
            if (currentReservations >= maxReservationsAllowed) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Waitlist Confirmation");
                alert.setHeaderText("Maximum reservations reached");
                alert.setContentText("Would you like to be put on the waitlist?");
                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    Waitlist waitlist = new Waitlist();
                    waitlist.setUserID(userId);
                    waitlist.setCarpoolingID(carpoolingId);
                    waitlistService.add(waitlist);
                    Alert waitlistAlert = new Alert(Alert.AlertType.INFORMATION);
                    waitlistAlert.setTitle("Waitlist Confirmation");
                    waitlistAlert.setHeaderText(null);
                    waitlistAlert.setContentText("You have been added to the waitlist.");
                    waitlistAlert.showAndWait();
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
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creating reservation: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void updateNavigation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/updateCarpooling.fxml"));
        Parent root = loader.load();

        UpdateCarpooling controller = loader.getController();
        controller.getID(carpoolingId);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
