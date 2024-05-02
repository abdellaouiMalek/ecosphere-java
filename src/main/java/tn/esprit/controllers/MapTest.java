package tn.esprit.controllers;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapTest implements Initializable {

    @FXML
    private WebView mapView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = mapView.getEngine();
        webEngine.load(getClass().getResource("/leaflet.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("LoadWorker state changed from " + oldValue + " to " + newValue);
            if (newValue == Worker.State.FAILED) {
                System.err.println("WebView load error: " + webEngine.getLocation());
            }
        });

        // Listener for WebView errors
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.err.println("WebView load error: " + webEngine.getLocation());
            }
        });

        // Listener for JavaScript errors
        mapView.getEngine().setOnError(event -> {
            System.err.println("JavaScript Error: " + event.getMessage());
        });
    }
}

