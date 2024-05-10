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
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingSearchService;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyCarpooling {
    @FXML
    private TilePane carpoolingsContainer;

    @FXML
    private TextField departure;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField destination;

    @FXML
    private ImageView icon;

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


    private CarpoolingService carpoolingService;
    private boolean checkLoginStatus(User user) {
        return user != null && user.getId() != 0;
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
        void max(ActionEvent event) {
            RadioButton selectedRadioButton = (RadioButton) event.getSource();
            if (selectedRadioButton.getId().equals("sortMaxButton")) {
                if (sortpriceButton.isSelected()) {
                    List<Carpooling> filteredResults = searchResults.stream()
                            .filter(carpooling -> carpooling.getSeat() <= 2)
                            .sorted(Comparator.comparingDouble(Carpooling::getPrice))
                            .collect(Collectors.toList());
                    displayCarpoolings(filteredResults);
                }
                else if  (sortTimeButton.isSelected()){
                    List<Carpooling> filteredResults = searchResults.stream()
                            .filter(carpooling -> carpooling.getSeat() <= 2)
                            .sorted(Comparator.comparing(Carpooling::getTime))
                            .collect(Collectors.toList());
                    displayCarpoolings(filteredResults);
                }
                else {
                    List<Carpooling> filteredResults = searchResults.stream()
                            .filter(carpooling -> carpooling.getSeat() <= 2)
                            .collect(Collectors.toList());
                    displayCarpoolings(filteredResults);
                }
            }
    }

        @FXML
        void navigationBack(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }



    @FXML
    void search(ActionEvent event) throws ParseException {
        CarpoolingSearchService searchService = new CarpoolingSearchService();
        searchService.search(event, departure, destination, departureDate);
    }

    @FXML
    void sortPrice(ActionEvent event) {
        searchResults.sort(Comparator.comparingDouble(Carpooling::getPrice));
        displayCarpoolings(searchResults);
    }
    @FXML
    void sortTime(ActionEvent event) {
        searchResults.sort(Comparator.comparing(Carpooling::getTime));
        displayCarpoolings(searchResults);
    }

    @FXML
    private void initialize() {
        this.carpoolingService = new CarpoolingService();
        loadUserCarpoolings();
    }

    private void loadUserCarpoolings() {
        int userId = SessionUser.getLoggedUser().getId();

        try {
            List<Carpooling> userCarpoolings = carpoolingService.getCarpoolingsByUserId(userId);

            displayCarpoolings(userCarpoolings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayCarpoolings(List<Carpooling> carpoolings) {
        this.searchResults=carpoolings;
        carpoolingsContainer.getChildren().clear();
        for (Carpooling carpooling : carpoolings) {
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

    private int extractCarpoolingId(Carpooling carpooling) {
        if (carpooling != null) {
            return carpooling.getId();
        } else {
            return 0;
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
        }    }

}
