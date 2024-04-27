package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;

import java.io.IOException;
import java.util.List;


public class AllCarpoolings {
    @FXML
    private ListView<String> carpoolingsListView;
    private List<Carpooling> searchResults;
    @FXML
    private ImageView icon;


    public void displaySearchResults(List<Carpooling> searchResults) {
        this.searchResults = searchResults;
        carpoolingsListView.getItems().clear();
        for (Carpooling carpooling : searchResults) {
            String displayText = "ID: " + carpooling.getId() + ", Departure: " + carpooling.getDeparture() +
                    ", Destination: " + carpooling.getDestination() + ", Arrival Date: " + carpooling.getArrivalDate();
            carpoolingsListView.getItems().add(displayText);
        }
    }

    public void initialize() {
        carpoolingsListView.setOnMouseClicked(event -> {
            String selectedItem = carpoolingsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int selectedIndex = carpoolingsListView.getSelectionModel().getSelectedIndex();
                Carpooling selectedCarpooling = searchResults.get(selectedIndex);
                int carpoolingId = extractCarpoolingId(selectedCarpooling);
                if (carpoolingId != -1) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
                        Parent root = loader.load();

                        CarpoolingDetails controller = loader.getController();
                        controller.getID(carpoolingId);

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

        AddCarpooling controller = loader.getController();

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
