package tn.esprit.controllers.addCarpooling;


import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import tn.esprit.controllers.AddCarpooling;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class AddTime implements Initializable{

    @FXML
    private TextField destination;

    @FXML
    private WebView mapView;

    private String departureValue;

    @FXML
    void addDestinationButton(ActionEvent event) {
        String destinationValue = destination.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/addCarpooling.fxml"));
        try {
            Parent root = loader.load();
            AddCarpooling controller = loader.getController();
            controller.setDepartureAndDestination(departureValue, destinationValue); // Pass both departure and destination values

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window if needed
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }

    }

    public void setDeparture(String departureValue) {
        this.departureValue = departureValue;
        System.out.println(departureValue);
    }

    public String getDepartureValue() {
        return departureValue;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.getEngine().setOnAlert(event -> System.out.println(event.getData()));
        mapView.getEngine().getLoadWorker().exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("JavaScript error occurred: " + newValue.getMessage());
            }
        });
        mapView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.out.println("Failed to load content: " + mapView.getEngine().getLocation());
                System.out.println("Error: " + mapView.getEngine().getLoadWorker().getException().getMessage());
            }
        });

        mapView.getEngine().load(Objects.requireNonNull(getClass().getResource("/leaflet.html")).toExternalForm());

        // Expose Java object to JavaScript
        mapView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) mapView.getEngine().executeScript("window");
                window.setMember("javaBridge", new AddDeparture.JavaBridge(destination));
            }
        });
    }


    // Java class to bridge communication with JavaScript
    public static class JavaBridge {
        private final TextField destination;

        public JavaBridge(TextField destination) {
            this.destination = destination;
        }

        public void showCity(String city) {
            System.out.println("City: " + city);
            Platform.runLater(() -> destination.setText(city)); // Update TextField on JavaFX Application Thread
        }
    }
}



