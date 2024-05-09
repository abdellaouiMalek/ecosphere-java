package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddCarpooling {

    @FXML
    private DatePicker departureDate;


    @FXML
    private TextField price;

    @FXML
    private TextField time;

    @FXML
    private ImageView icon;

    @FXML
    private TextField seat;
    private String departureValue;
    private String destinationValue;

    private final CarpoolingService carpoolingService = new CarpoolingService();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    User loggedUser = SessionUser.getLoggedUser();
    int loggedId = loggedUser.getId();

    @FXML
    void add(ActionEvent event) {
        if (validateInput()) {
            try {

                LocalDate departureDateValue = departureDate.getValue();
                Date departureDate = java.sql.Date.valueOf(departureDateValue);
                Time timeValue = new Time(timeFormat.parse(time.getText()).getTime());
                double priceValue = Double.parseDouble(price.getText());
                int seatValue = Integer.parseInt(seat.getText());

                Carpooling carpooling = new Carpooling(loggedId,departureValue, destinationValue, departureDate, timeValue, priceValue,seatValue);

                carpoolingService.add(carpooling);

                int carpoolingId = carpooling.getId();
                Image iconImage = new Image(getClass().getResourceAsStream("/carpooling/back.png"));

                icon.setImage(iconImage);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
                Parent root = loader.load();

                CarpoolingDetails controller = loader.getController();
                controller.getID(carpoolingId);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (ParseException | NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                System.out.println("Error parsing input: " + ex.getMessage());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error adding carpooling: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("Validation failed.");
        }
    }

    private boolean validateInput() {
        try {
            timeFormat.setLenient(false);
            timeFormat.parse(time.getText());
        } catch (ParseException ex) {
            return false;
        }

        try {
            Double.parseDouble(price.getText());
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }


    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setDepartureAndDestination(String departureValue, String destinationValue) {
        this.departureValue = departureValue;
        System.out.println(departureValue);
  this.destinationValue=destinationValue;
        System.out.println(destinationValue);
}
}
