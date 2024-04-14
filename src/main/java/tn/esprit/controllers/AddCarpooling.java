package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;


public class AddCarpooling {
    @FXML
    private TextField arrivalDate;

    @FXML
    private TextField departure;

    @FXML
    private TextField departureDate;

    @FXML
    private TextField destination;

    @FXML
    private TextField price;

    @FXML
    private TextField time;

    private final CarpoolingService carpoolingService = new CarpoolingService();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @FXML
    void add(ActionEvent event) {
        if (validateInput()) {
            try {
                String departureText = departure.getText();
                String destinationText = destination.getText();
                Date departureDateValue = dateFormat.parse(departureDate.getText());
                Date arrivalDateValue = dateFormat.parse(arrivalDate.getText());
                Time timeValue = new Time(timeFormat.parse(time.getText()).getTime());
                double priceValue = Double.parseDouble(price.getText());

                Carpooling carpooling = new Carpooling(departureText, destinationText, departureDateValue, arrivalDateValue, timeValue, priceValue);
                carpoolingService.add(carpooling);
            } catch (ParseException | NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                System.out.println("Error parsing input: " + ex.getMessage());
                // Optionally, provide feedback to the user through UI elements
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Validation failed.");
        }
    }

    private boolean validateInput() {
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(departureDate.getText());
            dateFormat.parse(arrivalDate.getText());
            timeFormat.setLenient(false);
            timeFormat.parse(time.getText());
        } catch (ParseException ex) {
            return false; // Parsing failed, invalid format
        }
        try {
            Double.parseDouble(price.getText());
        } catch (NumberFormatException ex) {
            return false; // Non-numeric price
        }
        return true; // All inputs are valid
    }
}
