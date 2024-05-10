package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.User;
import tn.esprit.models.Waitlist;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;
import tn.esprit.services.WaitlistService;
import tn.esprit.util.EmailService;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservationCard {

    @FXML
    private Label departureR;

    @FXML
    private Label destinationR;

    @FXML
    private Label priceR;

    @FXML
    private Label timeR;

    private final ReservationService reservationService = new ReservationService();
    private final EmailService emailService = new EmailService();
    private final UserService userService = new UserService();
    private final CarpoolingService carpoolingService = new CarpoolingService();
    private int reservationID;

    @FXML
    void cancel(MouseEvent event) {
        Reservation reservation = new Reservation(reservationID);
        showCancellationConfirmation(reservation);
    }

    public void setReservation(Reservation reservation , int reservationID) throws SQLException {
        int carpoolingID = reservation.getCarpoolingID();
        this.reservationID = reservationID;

        CarpoolingService carpoolingService = new CarpoolingService();
        Carpooling carpooling = carpoolingService.getById(carpoolingID);
        System.out.println("fl service"+carpooling);

        if (carpooling != null) {
            departureR.setText(carpooling.getDeparture());
            destinationR.setText( carpooling.getDestination());
            priceR.setText(String.valueOf(carpooling.getPrice()));
            timeR.setText(String.valueOf(carpooling.getTime()));
        }
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
            System.out.println("dkhal le if");
            try {
                reservationService.delete(reservation);
                showCancellationSuccessMessage();
               // updateReservationsListView();
                WaitlistService waitlistService = new WaitlistService();
                Waitlist firstUser = waitlistService.getFirstUserOnWaitlist(reservation.getCarpoolingID());
                System.out.println(firstUser);
                if (firstUser != null) {
                    System.out.println("First user on waitlist: " + firstUser.getUserID());
                    int id = firstUser.getUserID();
                    String userEmail = userService.getUserEmailById(id);
                    System.out.println("First user on waitlist: " + userEmail);
                    Carpooling carpooling = carpoolingService.getById(reservation.getCarpoolingID());

                    if (userEmail != null) {
                        String departure = carpooling.getDeparture();
                        String destination = carpooling.getDestination();
                        String date = String.valueOf(carpooling.getArrivalDate());
                        String emailBody = "A spot is available in the carpooling you are waitlisted for, from " + departure + " to " + destination + " the " + date;
                        emailBody += " <a href='" + id + "&carpoolingId=" + reservation.getCarpoolingID() + "'>Would you like to join?</a>";

                        emailService.sendEmail(userEmail, "Carpooling Reservation Available", emailBody);
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


}
