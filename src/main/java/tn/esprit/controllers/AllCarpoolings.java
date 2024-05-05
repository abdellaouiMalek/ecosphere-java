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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingSearchService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AllCarpoolings {
    @FXML
    private AnchorPane carpoolingsContainer;
    @FXML
    private Button sortPrice;
    @FXML
    private TextField departure;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField destination;

    List<Carpooling> searchResults;

    public void displaySearchResults(List<Carpooling> searchResults) {
        this.searchResults = searchResults;
        carpoolingsContainer.getChildren().clear();
        double cardX = 10.0; // Initial X position for the first card
        double cardY = 10.0; // Initial Y position for the first row
        int cardsPerRow = 3;
        int cardCount = 0;

        for (Carpooling carpooling : searchResults) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingCard.fxml"));
                Parent card = loader.load();

                CarpoolingCard controller = loader.getController();
                controller.setCarpooling(carpooling);

                AnchorPane.setTopAnchor(card, cardY);
                AnchorPane.setLeftAnchor(card, cardX);

                carpoolingsContainer.getChildren().add(card);

                // Add event handler to navigate to carpooling details screen
                card.setOnMouseClicked(event -> {
                    int carpoolingId = extractCarpoolingId(carpooling);
                    navigateToCarpoolingDetails(carpoolingId);
                });
                cardCount++;
                if (cardCount % cardsPerRow == 0) {
                    // Move to the next row
                    cardY += 140.0; // Adjust the vertical spacing as needed
                    cardX = 10.0; // Reset X position for the next row
                } else {
                    // Move to the next column
                    cardX += 210.0; // Adjust the horizontal spacing as needed
                }
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
        Collections.sort(searchResults, Comparator.comparingDouble(Carpooling::getPrice));
        displaySearchResults(searchResults);
    }
    @FXML
    void sortTime(ActionEvent event) {
        Collections.sort(searchResults, Comparator.comparing(Carpooling::getTime));
        displaySearchResults(searchResults);
    }

    @FXML
    void addNavigation(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/addCarpooling.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void max(ActionEvent event) {

    }
    }

