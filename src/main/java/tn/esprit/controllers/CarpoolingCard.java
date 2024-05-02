package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;

public class CarpoolingCard {
    @FXML
    private Label departureLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label dateLabel;

    public void setCarpooling(Carpooling carpooling) {
        departureLabel.setText("Departure: " + carpooling.getDeparture());
        destinationLabel.setText("Destination: " + carpooling.getDestination());
        dateLabel.setText("Date: " + carpooling.getArrivalDate());
    }

}

