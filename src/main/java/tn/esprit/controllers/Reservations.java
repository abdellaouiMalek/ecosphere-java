package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import tn.esprit.models.Reservation;
import tn.esprit.models.User;
import tn.esprit.models.Waitlist;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;
import tn.esprit.services.WaitlistService;
import tn.esprit.util.EmailService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reservations {
    @FXML
    private ListView<String> reservationsListView;

    private final ReservationService reservationService = new ReservationService();
    private final EmailService emailService = new EmailService();
    private final UserService userService = new UserService();

    public void displayReservations(List<Reservation> reservations) {
        reservationsListView.getItems().clear();
        for (Reservation reservation : reservations) {
            String displayText = "Reservation ID: " + reservation.getId() +
                    ", User ID: " + reservation.getUserID() +
                    ", Carpooling ID: " + reservation.getCarpoolingID();
            reservationsListView.getItems().add(displayText);
        }
    }

    public void initialize() {
        reservationsListView.setOnMouseClicked(event -> {
            List<Reservation> clickedReservation = reservationService.getAll(); // Fetch the latest reservations
            int selectedIndex = reservationsListView.getSelectionModel().getSelectedIndex();
            Reservation selectedReservation = clickedReservation.get(selectedIndex);
            if (selectedReservation != null) {
                showCancellationConfirmation(selectedReservation);
            }
        });
    }

    private void showCancellationConfirmation(Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancellation Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to cancel your reservation?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            try {
                reservationService.delete(reservation);
                showCancellationSuccessMessage();
                updateReservationsListView();
                WaitlistService waitlistService = new WaitlistService();
                Waitlist firstUser = waitlistService.getFirstUserOnWaitlist(reservation.getCarpoolingID());
                if (firstUser != null) {
                    System.out.println("First user on waitlist: " + firstUser.getUserID());
                    int id = firstUser.getUserID();
                    String userEmail = userService.getUserEmailById(id);
                    System.out.println("First user on waitlist: " + userEmail);

                    if (userEmail != null) {
                        emailService.sendEmail(userEmail, "Carpooling Reservation Available", "A spot is available in the carpooling you are waitlisted for. Would you like to join?");
                        System.out.println("Email sent successfully to: " + userEmail);
                    } else {
                        System.err.println("Failed to retrieve user email for ID: " + id);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void showCancellationSuccessMessage() {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Cancellation Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Your reservation has been canceled.");
        confirmationAlert.showAndWait();
    }

    private void updateReservationsListView() {
        List<Reservation> updatedReservations = reservationService.getAll();
        List<String> reservationStrings = updatedReservations.stream()
                .map(Reservation::toString)
                .collect(Collectors.toList());
        reservationsListView.setItems(FXCollections.observableArrayList(reservationStrings));
    }
}
