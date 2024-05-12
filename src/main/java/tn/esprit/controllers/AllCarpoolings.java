package tn.esprit.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.Carpooling;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingSearchService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class AllCarpoolings {
    @FXML
    private TilePane carpoolingsContainer;
    @FXML
    private Button sortPrice;
    @FXML
    private TextField departure;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField destination;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private RadioButton sortMaxButton;

    @FXML
    private RadioButton sortTimeButton;

    @FXML
    private RadioButton sortpriceButton;
    @FXML
    private MenuItem loginItem;

    @FXML
    private MenuItem reservationItem;
    @FXML
    private MenuItem carpoolingItem;
    List<Carpooling> searchResults;

    public void displaySearchResults(List<Carpooling> searchResults) {
        this.searchResults = searchResults;
        carpoolingsContainer.getChildren().clear();

        for (Carpooling carpooling : searchResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingCard.fxml"));
                Parent card = loader.load();

                CarpoolingCard controller = loader.getController();
                controller.setCarpooling(carpooling);
                carpoolingsContainer.setHgap(20); // Set horizontal gap to 20 pixels
                carpoolingsContainer.setVgap(20); // Set vertical gap to 20 pixels

                carpoolingsContainer.getChildren().add(card);

                card.setOnMouseClicked(event -> {
                    int carpoolingId = extractCarpoolingId(carpooling);
                    navigateToCarpoolingDetails(carpoolingId);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void navigateToCarpoolingDetails(int carpoolingId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
            Parent root = loader.load();

            CarpoolingDetails controller = loader.getController();
            controller.getID(carpoolingId);
            controller.setSearchResults(searchResults);

            Stage stage = (Stage) carpoolingsContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int extractCarpoolingId(Carpooling carpooling) {
        if (carpooling != null) {
            return carpooling.getId();
        } else {
            return 0;
        }
    }

    @FXML
    void search(ActionEvent event) throws ParseException {
        CarpoolingSearchService searchService = new CarpoolingSearchService();
        searchService.search(event, departure, destination, departureDate);
    }

    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void sortPrice(ActionEvent event) {
        searchResults.sort(Comparator.comparingDouble(Carpooling::getPrice));
        displaySearchResults(searchResults);
    }
    @FXML
    void sortTime(ActionEvent event) {
        searchResults.sort(Comparator.comparing(Carpooling::getTime));
        displaySearchResults(searchResults);
    }

    @FXML
    void max(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        if (selectedRadioButton.getId().equals("sortMaxButton")) {
            if (sortpriceButton.isSelected()) {
                List<Carpooling> filteredResults = searchResults.stream()
                        .filter(carpooling -> carpooling.getSeat() <= 2)
                        .sorted(Comparator.comparingDouble(Carpooling::getPrice))
                        .collect(Collectors.toList());
                displaySearchResults(filteredResults);
            }
            else if  (sortTimeButton.isSelected()){
                List<Carpooling> filteredResults = searchResults.stream()
                        .filter(carpooling -> carpooling.getSeat() <= 2)
                        .sorted(Comparator.comparing(Carpooling::getTime))
                        .collect(Collectors.toList());
                displaySearchResults(filteredResults);
            }
            else {
                List<Carpooling> filteredResults = searchResults.stream()
                        .filter(carpooling -> carpooling.getSeat() <= 2)
                        .collect(Collectors.toList());
                displaySearchResults(filteredResults);
            }
        }
    }

  private boolean checkLoginStatus(User user) {
      return user != null && user.getId() != 0;
  }

    @FXML
    private void initialize() {
        updateMenuItemsVisibility();
    }
    private void updateMenuItemsVisibility() {
        boolean isLoggedIn = checkLoginStatus(SessionUser.getLoggedUser());
        reservationItem.setVisible(isLoggedIn);
        carpoolingItem.setVisible(isLoggedIn);
        loginItem.setVisible(!isLoggedIn);
    }


    @FXML
    void addNavigation(MouseEvent event) throws IOException {
        User loggedUser = SessionUser.getLoggedUser();
        if (checkLoginStatus(loggedUser)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/addDeparture.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please log in to add a new carpooling.");

            ButtonType loginButton = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(loginButton, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == loginButton) {
                FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent loginRoot = loginLoader.load();

                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(loginRoot);
            }
        }
    }
    @FXML
    void navigateToMyCarpoolings(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/carpooling/myCarpooling.fxml")));
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
    @FXML
    void navigateToReservation(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/carpooling/myReservations.fxml")));
        Scene currentScene = menuItem.getParentPopup().getOwnerWindow().getScene();
        currentScene.setRoot(root);
    }

}

