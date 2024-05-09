package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingSearchService;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Search {
    @FXML
    private TextField departure;

    @FXML
    private TextField destination;

    @FXML
    private DatePicker departureDate;
    private final CarpoolingSearchService searchService = new CarpoolingSearchService();

    @FXML
    void search(ActionEvent event) throws ParseException {
        CarpoolingSearchService searchService = new CarpoolingSearchService();
        searchService.search(event, departure, destination, departureDate);
    }

    @FXML
    private ImageView icon;

    @FXML
    void navigationBack(MouseEvent event) {

    }



    @FXML
    void waitlistNavigation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/updateCarpooling.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void reservationNavigation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/allReservations.fxml"));
        Parent root = loader.load();

        Reservations controller = loader.getController();

        List<Reservation> reservations = new ReservationService().getAll();
        controller.displayReservations(reservations);

        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    }

