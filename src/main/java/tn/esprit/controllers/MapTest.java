package tn.esprit.controllers;

import javafx.concurrent.Worker;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import java.net.URL;
import java.util.ResourceBundle;

public class MapTest implements Initializable {

    @FXML
    private WebView mapView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a SwingNode to hold the JMapViewer
        SwingNode swingNode = new SwingNode();

        // Create a JMapViewer instance
        JMapViewer mapViewer = new JMapViewer();

        // Set the initial position and zoom level
        Coordinate tunis = new Coordinate(36.8065, 10.1815);
        mapViewer.setDisplayPosition(tunis, 10);

        // Add tile source
        mapViewer.setTileSource(new OsmTileSource.Mapnik());

        // Add the mapViewer to the SwingNode
        swingNode.setContent(mapViewer);

        // Add the SwingNode to the JavaFX scene
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        ((AnchorPane) mapView.getParent()).getChildren().add(swingNode);

        // Load JavaScript code from the leaflet.html resource
        WebEngine webEngine = mapView.getEngine();
        URL htmlResource = getClass().getResource("/leaflet.html");
        if (htmlResource != null) {
            webEngine.load(htmlResource.toExternalForm());
        } else {
            System.err.println("Error: leaflet.html resource not found");
        }

        // Add listener for WebView errors
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.err.println("WebView load error: " + webEngine.getLocation());
            }
        });

        // Add listener for JavaScript errors
        webEngine.setOnError(event -> {
            System.err.println("JavaScript Error: " + event.getMessage());
        });
    }
}