package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;

import java.io.IOException;
import java.util.List;


public class AllCarpoolings {
    @FXML
    private AnchorPane carpoolingsContainer; // AnchorPane to hold carpooling cards
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
            controller.setSearchResults(searchResults); // Pass the searchResults list

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
    void addNavigation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/addCarpooling.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
