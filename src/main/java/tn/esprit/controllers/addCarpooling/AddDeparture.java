package tn.esprit.controllers.addCarpooling;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddDeparture implements Initializable {

    @FXML
    private TextField departure;

    @FXML
    private ImageView icon;

    @FXML
    private WebView mapView;


    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/search.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private String departureValue;


    @FXML
    void addDepartureButton(ActionEvent event) {
            String departureValue = departure.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/addDestination.fxml"));
            try {
                Parent root = loader.load();
                AddDestination controller = loader.getController();
                controller.setDeparture(departureValue);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close the current window if needed
                ((Stage) departure.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
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
                window.setMember("javaBridge", new AddDeparture.JavaBridge(departure));
            }
        });
    }

    // Java class to bridge communication with JavaScript
    public static class JavaBridge {
        private final TextField departure;

        public JavaBridge(TextField departure) {
            this.departure = departure;
        }

        public void showCity(String city) {
            System.out.println("City: " + city);
            Platform.runLater(() -> departure.setText(city)); // Update TextField on JavaFX Application Thread
        }
    }
}
