package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.models.Waitlist;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;
import tn.esprit.services.WaitlistService;
import tn.esprit.util.EmailService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MyReservations {
    public MenuButton menuButton;
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
    private Circle notificationDot;

    private final ReservationService reservationService = new ReservationService();
    private final EmailService emailService = new EmailService();
    private final UserService userService = new UserService();
    private final CarpoolingService carpoolingService = new CarpoolingService();

    List<Reservation> reservations;

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
   /* private void navigateToNotificationsPage(int carpoolingId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
            Parent root = loader.load();

            CarpoolingDetails controller = loader.getController();
            controller.getID(carpoolingId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

  /*  private void displayNotification(String message, int carpoolingId) {
        Notifications.create()
                .title("New Notification")
                .text(message)
                .onAction(event -> navigateToNotificationsPage(carpoolingId))
                .show();
    }*/
    public void initialize() {
        try {
            User loggedUser = SessionUser.getLoggedUser();
            int userID = loggedUser.getId();
            List<Reservation> reservations = reservationService.getReservationsForCurrentUser(userID);
            System.out.println("In initialize: " + reservations);
            displayReservations(reservations);

           /* for (Reservation reservation : reservations) {
                notifyWaitlistedUsers(reservation);
            }*/
            boolean hasNotifications = userService.checkPendingNotifications(loggedUser);
            /*if (hasNotifications) {
                notificationDot.setVisible(true);
                Notifications.create().title("Carpooling Available").text("The carpooling you are waitlisted for is now available.").show();
            }
            menuButton.setGraphic(new HBox(notificationDot)); */
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
                controller.setMainController(this);
                int carpoolingID = reservation.getCarpoolingID();
                int userID = reservation.getUserID();

                controller.setReservation(reservation, reservationID, userID, carpoolingID);

                reservationsContainer.setHgap(20);
                reservationsContainer.setVgap(20);
                reservationsContainer.getChildren().add(card);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        displayReservations(reservations);
    }

    void showCancellationConfirmation(Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancellation Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to cancel your reservation?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("Reservation object: " + reservation); // Debug statement

        if (result.isPresent() && result.get() == yesButton) {
            try {
                int carpoolingID = reservation.getCarpoolingID();
                System.out.println("Carpooling ID: " + carpoolingID); // Debug statement

                reservationService.delete(reservation);
                showCancellationSuccessMessage();

                notifyWaitlistedUsers(reservation);

                // Refresh reservations display
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void notifyWaitlistedUsers(Reservation reservation) throws SQLException {
        WaitlistService waitlistService = new WaitlistService();
        Waitlist firstUser = waitlistService.getFirstUserOnWaitlist(reservation.getCarpoolingID());
        if (firstUser != null) {
            int id = firstUser.getUserID();
            String userEmail = userService.getUserEmailById(id);
            Carpooling carpooling = carpoolingService.getById(reservation.getCarpoolingID());
            int carpoolingID = carpooling.getId();

            if (userEmail != null && carpooling != null) {
                String departure = carpooling.getDeparture();
                String destination = carpooling.getDestination();
                String date = String.valueOf(carpooling.getArrivalDate());
                String emailBody = "A spot is available in the carpooling you are waitlisted for, from "
                        + departure + " to " + destination + " the " + date;
                emailBody += " <a href='" + id + "&carpoolingId=" + reservation.getCarpoolingID() + "'>Would you like to join?</a>";

                emailService.sendEmail(userEmail, "Carpooling Reservation Available", emailBody);
                System.out.println("Email sent successfully to: " + userEmail);

                String notificationMessage = "A spot is available in a carpooling you are waitlisted for.";
                userService.addInAppNotification(id,carpoolingID,notificationMessage);
            } else {
                System.err.println("Failed to retrieve user email or carpooling information.");
            }
        } else {
            System.out.println("No users on the waitlist for this carpooling.");
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
