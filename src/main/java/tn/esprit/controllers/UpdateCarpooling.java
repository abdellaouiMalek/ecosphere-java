package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateCarpooling {
    @FXML
    private TextField arrivalDate;

    @FXML
    private TextField departure;

    @FXML
    private TextField departureDate;

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
    private Label id;

    @FXML
    private TextField price;

    @FXML
    private TextField time;

    private final CarpoolingService carpoolingService = new CarpoolingService();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private int carpoolingId;

    public void getID(int carpoolingId) {
        this.carpoolingId = carpoolingId;
        System.out.println("f update" + carpoolingId);
        id.setText(String.valueOf(carpoolingId));
    }

    @FXML
    void update(ActionEvent event) {
        try {
            System.out.println("wost update" + carpoolingId);
            String departureText = departure.getText();
            String destinationText = destination.getText();
            Date departureDateValue = dateFormat.parse(departureDate.getText());
            Date arrivalDateValue = dateFormat.parse(arrivalDate.getText());
            Time timeValue = new Time(timeFormat.parse(time.getText()).getTime());
            double priceValue = Double.parseDouble(price.getText());

            Carpooling carpooling = new Carpooling(carpoolingId,departureText, destinationText, departureDateValue, arrivalDateValue, timeValue, priceValue);

            carpoolingService.update(carpooling);
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
        } catch ( IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding carpooling: " + e.getMessage());
            alert.showAndWait();
        }
    }
    }

