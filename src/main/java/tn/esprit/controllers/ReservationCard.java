package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

    private int reservationID;
    private int carpoolingID;
    private int userID;

    private MyReservations mainController;

    @FXML
    void cancel(MouseEvent event) {
        Reservation reservation = new Reservation(reservationID);
        reservation.setUserID(userID);
        reservation.setCarpoolingID(carpoolingID);
        mainController.showCancellationConfirmation(reservation);
    }

    public void setMainController(MyReservations mainController) {
        this.mainController = mainController;
    }

    public void setReservation(Reservation reservation, int reservationID, int userID, int carpoolingID) throws SQLException {
        this.reservationID = reservationID;
        System.out.println(" the reservation id" + reservationID);
        this.userID = userID;
        System.out.println(" the user id" + userID);
        this.carpoolingID = carpoolingID;
        System.out.println(" the carpooling id" + carpoolingID);

        CarpoolingService carpoolingService = new CarpoolingService();
        Carpooling carpooling = carpoolingService.getById(carpoolingID);

        if (carpooling != null) {
            departureR.setText(carpooling.getDeparture());
            destinationR.setText(carpooling.getDestination());
            priceR.setText(String.valueOf(carpooling.getPrice()));
            timeR.setText(String.valueOf(carpooling.getTime()));
        }
    }
}
