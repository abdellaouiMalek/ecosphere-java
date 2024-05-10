package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MyReservations {
    @FXML
    private MenuItem carpoolingItem;

    @FXML
    private ImageView icon;

    @FXML
    private MenuItem loginItem;

    @FXML
    private MenuItem reservationItem;

    @FXML
    private TilePane reservationsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void addNavigation(MouseEvent event) {

    }

    @FXML
    void navigateToLogin(ActionEvent event) {

    }

    @FXML
    void navigateToMyCarpoolings(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/carpooling/myCarpooling.fxml")));
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }

    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private final ReservationService reservationService = new ReservationService();
    List<Reservation> reservations;

    public void initialize() {
        try {
            User loggedUser = SessionUser.getLoggedUser();
            int userID = loggedUser.getId();
            List<Reservation> reservations = reservationService.getReservationsForCurrentUser(userID);
            System.out.println(reservations);
            displayReservations(reservations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int extractID(Reservation reservation) {
        if (reservation != null) {
            return reservation.getId();
        } else {
            return 0;
        }
    }
    public void displayReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        reservationsContainer.getChildren().clear();

        for (Reservation reservation : reservations) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/reservationCard.fxml"));
                Parent card = loader.load();

                ReservationCard controller = loader.getController();
                int reservationID = extractID(reservation);
                controller.setReservation(reservation, reservationID);

                reservationsContainer.setHgap(20);
                reservationsContainer.setVgap(20);
                reservationsContainer.getChildren().add(card);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
