package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.User;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.UserService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class UpdateCarpooling {
    @FXML
    private DatePicker arrivalDate;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField departure;

    @FXML
    private TextField destination;

    @FXML
    private TextField seat;

    @FXML
    private TextField price;

    @FXML
    private ImageView userPic;

    @FXML
    private Label username;

    @FXML
    private TextField time;

    @FXML
    private ImageView icon;
    private final CarpoolingService carpoolingService = new CarpoolingService();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private int carpoolingId;


    public void getID(int carpoolingId) {
        this.carpoolingId = carpoolingId;
    }

    public void initialize(int carpoolingId) {
        this.carpoolingId = carpoolingId;
        try {
            Carpooling carpooling = carpoolingService.getById(carpoolingId);
            System.out.println("carpooooling initialize: " + carpooling);

            if (carpooling != null) {
                departure.setText(carpooling.getDeparture());
                destination.setText(carpooling.getDestination());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (carpooling.getDepartureDate() != null) {
                    String departureDateString = dateFormat.format(carpooling.getDepartureDate());
                    departureDate.setValue(LocalDate.parse(departureDateString));
                }

                price.setText(String.valueOf(carpooling.getPrice()));
                seat.setText(String.valueOf(carpooling.getSeat()));
                int userId = carpooling.getUserID();
                UserService userService = new UserService();
                User user = userService.getById(userId);
                username.setText(user.getFirst_name() + " " + user.getLast_name());
                String pictureUrl = user.getPicture();
                if (pictureUrl != null && !pictureUrl.isEmpty()) {
                    try {
                        // Check if the URL starts with a recognized protocol
                        if (pictureUrl.startsWith("http")) {
                            // Load the image from the URL
                            Image image = new Image(pictureUrl);
                            userPic.setImage(image);
                        } else {
                            // If not, assume it's a local file path
                            File file = new File(pictureUrl);
                            Image image = new Image(file.toURI().toString());
                            userPic.setImage(image);
                        }

                        // Set the dimensions of the ImageView
                        userPic.setFitWidth(50); // Set your desired width
                        userPic.setFitHeight(50); // Set your desired height

                        // Remove the preserveRatio attribute to avoid stretching the image
                        userPic.setPreserveRatio(false);

                        // Clip the ImageView to a Circle shape
                        Circle clip = new Circle();
                        clip.setCenterX(userPic.getFitWidth() / 2);
                        clip.setCenterY(userPic.getFitHeight() / 2);
                        clip.setRadius(userPic.getFitWidth() / 2);
                        userPic.setClip(clip);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle image loading error
                    }
                } else {
                    System.out.println("Carpooling not found for ID: " + carpoolingId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void update(ActionEvent event) {
        try {
            String departureText = departure.getText();
            String destinationText = destination.getText();
            LocalDate departureDateValue = departureDate.getValue();
            Date departureDate = java.sql.Date.valueOf(departureDateValue);
            double priceValue = Double.parseDouble(price.getText());
            int seatValue = Integer.parseInt(seat.getText());
            System.out.println(" fl update carpooling " + carpoolingId);
            Carpooling carp = new Carpooling();
            carp.setId(carpoolingId);
            carp.setDeparture(departureText);
            carp.setDestination(destinationText);
            carp.setPrice(priceValue);
            carp.setDepartureDate(departureDate);
            carp.setSeat(seatValue);

            carpoolingService.update(carp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Carpooling updated successfully!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
            Parent root = loader.load();

            CarpoolingDetails controller = loader.getController();
            controller.getID(carpoolingId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error updating carpooling: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void navigationBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/carpoolingDetails.fxml"));
        Parent root = loader.load();
        CarpoolingDetails controller = loader.getController();
        controller.getID(carpoolingId);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}