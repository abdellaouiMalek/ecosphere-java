package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.Waitlist;
import tn.esprit.services.ReservationService;
import tn.esprit.services.WaitlistService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reservations {
    @FXML
    private ListView<String> reservationsListView;

    private final ReservationService reservationService = new ReservationService();

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
                updateReservationsListView(); // Update the ListView with the latest data

                // Call getFirstUserOnWaitlist method after successful cancellation
                WaitlistService waitlistService = new WaitlistService();
                Waitlist firstUser = waitlistService.getFirstUserOnWaitlist(reservation.getCarpoolingID());
                if (firstUser != null) {
                    // Handle the first user on waitlist, for example, notify them or perform any other action
                    System.out.println("First user on waitlist: " + firstUser.getUserID());
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
