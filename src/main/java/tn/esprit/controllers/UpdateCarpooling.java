package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class UpdateCarpooling {
    @FXML
    private DatePicker arrivalDate;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField departure;

    @FXML
    private Label departureLabel;

    @FXML
    private Label departureLabel1;

    @FXML
    private Label departureLabel2;

    @FXML
    private Label departureLabel3;

    @FXML
    private Label departureLabel4;

    @FXML
    private Label departureLabel5;

    @FXML
    private TextField destination;

    @FXML
    private TextField seat;



    @FXML
    private TextField price;

    @FXML
    private TextField time;

    @FXML
    private ImageView icon;
    private final CarpoolingService carpoolingService = new CarpoolingService();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private int carpoolingId;


    public void getID(int carpoolingId) {
        this.carpoolingId = carpoolingId;
    }

    @FXML
    void update(ActionEvent event) {
        try {
            String departureText = departure.getText();
            String destinationText = destination.getText();
            LocalDate departureDateValue = departureDate.getValue();
            LocalDate arrivalDateValue = arrivalDate.getValue();
            Date departureDate = java.sql.Date.valueOf(departureDateValue);
            Date arrivalDate = java.sql.Date.valueOf(arrivalDateValue);
            Time timeValue = new Time(timeFormat.parse(time.getText()).getTime());
            double priceValue = Double.parseDouble(price.getText());
            int seatValue = Integer.parseInt(seat.getText());
            System.out.println(" fl update carpooling " + carpoolingId);
            Carpooling carp = new Carpooling();
            carp.setId(carpoolingId);
            carp.setDeparture(departureText);
            carp.setDestination(destinationText);
            carp.setPrice(priceValue);
            carp.setTime(timeValue);
            carp.setArrivalDate(arrivalDate);
            carp.setDepartureDate(departureDate);
            carp.setSeat(seatValue);

            carpoolingService.update(carp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Carpooling updated successfully!");
            alert.showAndWait();

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
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error updating carpooling: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
        Parent root = loader.load();
        CarpoolingDetails controller = loader.getController();
        controller.getID(carpoolingId);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}