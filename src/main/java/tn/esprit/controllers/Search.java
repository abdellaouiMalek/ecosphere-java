package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingSearchService;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Search {

    @FXML
    private TextField departure;

    @FXML
    private TextField destination;

    @FXML
    private DatePicker departureDate;
    @FXML
    private MenuItem loginItem;

    @FXML
    private MenuItem reservationItem;
    @FXML
    private MenuItem carpoolingItem;

    public Circle notificationDot;
    public MenuItem waitlist;
    public MenuButton menuButton;
    private final CarpoolingSearchService searchService = new CarpoolingSearchService();
    private final UserService userService = new UserService();

    private boolean checkLoginStatus(User user) {
        return user != null && user.getId() != 0;
    }

    @FXML
    private void initialize() {
        updateMenuItemsVisibility();
        checkAndDisplayNotifications();
    }

    private void updateMenuItemsVisibility() {
        boolean isLoggedIn = checkLoginStatus(SessionUser.getLoggedUser());
        reservationItem.setVisible(isLoggedIn);
        carpoolingItem.setVisible(isLoggedIn);
        loginItem.setVisible(!isLoggedIn);
        waitlist.setVisible(!isLoggedIn);
    }

    private void checkAndDisplayNotifications() {
        User loggedUser = SessionUser.getLoggedUser();
        if (loggedUser != null) {
            boolean hasNotifications = userService.checkPendingNotifications(loggedUser);
            if (hasNotifications) {
                waitlist.setVisible(true);
                notificationDot.setVisible(true);
                Notifications.create().title("Carpooling Available").text("The carpooling you are waitlisted for is now available.").show();
            }
            menuButton.setGraphic(new HBox(notificationDot));
        }
    }

    @FXML
    void search(ActionEvent event) throws ParseException {
        CarpoolingSearchService searchService = new CarpoolingSearchService();
        searchService.search(event, departure, destination, departureDate);
    }

    @FXML
    private ImageView icon;

    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void navigateToCarpoolings(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/carpooling/myCarpooling.fxml")));
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }



    public void navigateToReservation(ActionEvent actionEvent) throws IOException {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/carpooling/myReservations.fxml")));
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }


    @FXML
    void navigateToLogin(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }

    public void navigateToCarpoolingDetails(ActionEvent actionEvent) throws IOException {
        CarpoolingService carpoolingService = new CarpoolingService();
        User loggedUser = SessionUser.getLoggedUser();
        int userID = loggedUser.getId();
        System.out.println("user id f navigation" +userID);
System.out.println(userService.getByUserId(userID));
        int carpoolingID = userService.getByUserId(userID);

        System.out.println("carpoling id f navigation" +carpoolingID);
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/availableCarpooling.fxml"));
        Parent root = loader.load();
        AvailableCarpooling controller = loader.getController();
        controller.getID(carpoolingID); // Pass the carpooling ID to the controller
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }
}



